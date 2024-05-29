import asyncio

from ultralytics import YOLO
from collections import defaultdict
from aiohttp import ClientSession
import cv2
import numpy as np


# 图像检测器
class Detector:
    def __init__(self, model_path):
        self.model = YOLO(model_path)  # Load a model 加载YOLO模型

    def detect(self, frame):
        tracks = self.model.track(frame, persist=True, show=False)
        detected_frame = tracks[0].plot()
        return tracks, detected_frame


# 物体计数器
class Object_counter:
    def __init__(self, base_line_y):
        # track_history用于保存目标ID，以及它在各帧的目标位置坐标，这些坐标是按先后顺序存储的
        self.track_history = defaultdict(lambda: [])
        # 基准线横坐标
        self.base_line_y = base_line_y
        self.total_in = None
        self.total_out = None
        # 类计数列表,与类列表对应
        self.in_counts = [0, 0, 0, 0]
        self.out_counts = [0, 0, 0, 0]

    def count_clear(self):
        self.in_counts = [0, 0, 0, 0]
        self.out_counts = [0, 0, 0, 0]

    def get_total(self):
        self.total_in = sum(self.in_counts)
        self.total_out = sum(self.out_counts)

    def start_count(self, track_ids, boxes):
        for track_id, box in zip(track_ids, boxes.data):
            # 得到该目标矩形框的中心点坐标(x, y)
            x1, y1, x2, y2 = box[:4]
            x = (x1 + x2) / 2
            y = (y1 + y2) / 2
            # 提取出该ID的以前所有帧的目标坐标，当该ID是第一次出现时，则创建该ID的字典
            track = self.track_history[track_id]
            track.append((float(x), float(y)))  # 追加当前目标ID的坐标
            # 两帧以上时，判断前后出现的位置
            if len(track) > 1:
                _, prev_y = track[-2]  # 提取前一帧的目标纵坐标
                # 当前一帧在基准线的下面，当前帧在基准线的上面时，说明该车是从下往上运行
                if prev_y > self.base_line_y >= y:
                    print(int(box[-1]))
                    self.in_counts[int(box[-1])] += 1  # in计数加1
                # 当前一帧在基准线的上面，当前帧在基准线的下面时，说明该车是从上往下运行
                if prev_y < self.base_line_y <= y:
                    self.out_counts[int(box[-1])] += 1  # out计数加1
            # 计算总计数
        self.get_total()


# 图像标注器
class Annotator:
    def __init__(self, fps, size, base_line_y, class_list):
        self.track_history = defaultdict(lambda: [])
        self.fps = fps
        self.size = size
        self.width = size[0]
        self.height = size[1]
        self.base_line_y = base_line_y
        self.class_list = class_list
        print(self.fps, self.size)

    def annotate_base_info(self, frame, counter):
        # 绘制基准线
        cv2.line(frame, (0, self.base_line_y), (self.width, self.base_line_y),
                 color=(255, 0, 255), thickness=2, lineType=4)
        # 绘制实时计数
        cv2.putText(frame, 'total_in:' + str(counter.total_in) +
                    ' total_out:' + str(counter.total_out), (0, 30),
                    cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 0, 0), 2)
        in_info = ''
        out_info = ''
        for i, v in enumerate(self.class_list):
            in_info += v + '_in:' + str(counter.in_counts[i]) + ' '
            out_info += v + '_out:' + str(counter.out_counts[i]) + ' '
        cv2.putText(frame, in_info, (0, self.base_line_y - 10),
                    cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 2)
        cv2.putText(frame, out_info, (0, self.base_line_y + 30),
                    cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)
        return frame

    def annotate_track(self, frame, track_ids, boxes):
        # 绘制跟踪线
        for box, track_id in zip(boxes.xywh.cpu(), track_ids):
            x, y, w, h = box
            track = self.track_history[track_id]
            track.append((float(x), float(y)))
            if len(track) > 30:  # 设置轨迹线保留
                track.pop(0)
            # 将各个标注框中心点相连并绘制
            points = np.hstack(track).astype(np.int32).reshape((-1, 1, 2))
            cv2.polylines(frame, [points], isClosed=False, color=(255, 0, 127), thickness=10)
        return frame


class Requester:
    def __init__(self, ip, port, path):
        self.ip = ip
        self.port = port
        self.path = path

    async def submitData(self, data):
        async with ClientSession() as session:
            async with session.post('http://' + self.ip + ':' + self.port + self.path, json=data) as response:
                res = await response.text()
                print(res)

    async def doSubmit(self, data_list):
        tasks = [self.submitData(d) for d in data_list]
        await asyncio.wait(tasks)
