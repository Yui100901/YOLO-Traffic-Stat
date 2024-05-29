import asyncio

from flask import Flask, Response
import cv2
import numpy as np
from socket import *

from processor.main import Object_counter, Detector, Annotator, Requester

app = Flask(__name__)

skt = socket(AF_INET, SOCK_DGRAM)  # 创建套接字
addr = ('0.0.0.0', 9010)
skt.bind(addr)  # 监听本机端口
skt.setblocking(True)

model_path = 'model/best.pt'
video_path = ['./video/test1.mp4', './video/test2.MOV', './video/test3.MOV',
              './video/test4.MOV', './video/test5.MOV','./video/test6.MOV']
class_list = ['bike', 'bus', 'car', 'truck']

cap0 = cv2.VideoCapture(0)
cap_form_file = cv2.VideoCapture(video_path[3])

data_submit_ip = '127.0.0.1'
data_submit_port = '9012'
data_submit_path = '/record/create'


# capture.set(cv2.CAP_PROP_FRAME_WIDTH, 960)
# capture.set(cv2.CAP_PROP_FRAME_HEIGHT, 5407)
# capture.set(cv2.CAP_PROP_FPS, 10)


def gen_frames_from_web_udp(skt):
    while True:
        # 从网络读取视频流
        data, _ = skt.recvfrom(921600)
        receive_data = np.frombuffer(data, dtype='uint8')
        receive_img = cv2.imdecode(receive_data, 1)  # 数据解码
        # 对图象进行处理

        # 编码并转换成字节流
        _, buffer = cv2.imencode('.jpg', receive_img)
        frame = buffer.tobytes()
        # 指定字节流类型image/jpeg
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')


def gen_frames_from_cv2(cap):
    fps = int(cap.get(cv2.CAP_PROP_FPS))  # 获取原视频帧率
    size = (int(cap.get(cv2.CAP_PROP_FRAME_WIDTH)), int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT)))  # 获取视频尺寸
    base_line_height = int(size[1] * 0.6)  # 基准线高度
    object_counter = Object_counter(base_line_y=base_line_height)
    detector = Detector(model_path=model_path)
    annotator = Annotator(fps=fps, size=size, base_line_y=base_line_height, class_list=class_list)
    requester = Requester(ip=data_submit_ip, port=data_submit_port, path=data_submit_path)
    frame_pass = 2 # 跳帧检测跳过量
    frame_count = 0
    while cap.isOpened():
        frame_count += 1
        # 跳帧数量
        if frame_pass != 0:
            if frame_count % frame_pass != 0:
                cap.grab()
                continue
        success, img = cap.read()
        if success:
            # 模型预测
            tracks, frame = detector.detect(img)
            frame_with_track = None
            if tracks[0].boxes.id is not None:
                track_ids = tracks[0].boxes.id.int().cpu().tolist()
                boxes = tracks[0].boxes
                # 计数
                object_counter.start_count(track_ids, boxes)
                # 绘制
                frame_with_track = annotator.annotate_track(frame, track_ids, boxes)
            if frame_with_track is not None:
                all_info_frame = annotator.annotate_base_info(frame_with_track, object_counter)
            else:
                all_info_frame = annotator.annotate_base_info(frame, object_counter)
            # cv2.imshow("车辆跟踪预测",annotated_frame)
            # 编码并转换成字节流
            _, buffer = cv2.imencode('.jpg', all_info_frame)
            bytes_frame = buffer.tobytes()
            # 按帧粒度计数
            if frame_count == fps * 3600 / frame_pass:
                data_list = []
                for i, (inV, outV) in enumerate(zip(object_counter.in_counts, object_counter.out_counts)):
                    data = {
                        "deviceId": 1,
                        "vehicleType": i,
                        "vehicleInNumber": inV,
                        "vehicleOutNumber": outV,
                        "granularity": 3600
                    }
                    # print(i, inV, outV)
                    # print(data)
                    data_list.append(data)
                asyncio.run(requester.doSubmit(data_list))
                object_counter.count_clear()
                frame_count = 0
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + bytes_frame + b'\r\n')


def save_result_from_cv2(cap):
    fps = int(cap.get(cv2.CAP_PROP_FPS))  # 获取原视频帧率
    size = (int(cap.get(cv2.CAP_PROP_FRAME_WIDTH)), int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT)))  # 获取视频尺寸
    codec = cv2.VideoWriter_fourcc(*'mp4v')  # 指定视频编码格式
    video_writer = cv2.VideoWriter('output_video.mp4', codec, fps, size)
    base_line_height = int(size[1] * 0.6)  # 基准线高度
    object_counter = Object_counter(base_line_y=base_line_height)
    detector = Detector(model_path=model_path)
    annotator = Annotator(fps=fps, size=size, base_line_y=base_line_height, class_list=class_list)
    frame_pass = 0
    frame_count = 0
    while cap.isOpened():
        frame_count += 1
        # 跳帧数量
        if frame_pass != 0:
            if frame_count % frame_pass != 0:
                cap.grab()
                continue
        success, img = cap.read()
        if success:
            # 模型预测
            tracks, frame = detector.detect(img)
            frame_with_track = None
            if tracks[0].boxes.id is not None:
                track_ids = tracks[0].boxes.id.int().cpu().tolist()
                boxes = tracks[0].boxes
                # 计数
                object_counter.start_count(track_ids, boxes)
                # 绘制
                frame_with_track = annotator.annotate_track(frame, track_ids, boxes)
            if frame_with_track is not None:
                all_info_frame = annotator.annotate_base_info(frame_with_track, object_counter)
            else:
                all_info_frame = annotator.annotate_base_info(frame, object_counter)
            cv2.imshow("车辆跟踪预测", all_info_frame)
            if cv2.waitKey(30) & 0xFF == ord('q'):
                break
            video_writer.write(all_info_frame)
        else:
            break
    cap.release()
    video_writer.release()
    cv2.destroyAllWindows()


@app.route("/")
def index():
    return "<h1>Hello World!</h1>"


# 服务器推送，使用multipart/mixed混合类型的变种--multipart/x-mixed-replace。
# 使用replace意味着每一个新数据块都会代替前一个数据块。
# 使用boundary指定边界
# 本地摄像头
@app.route('/video_feed0')
def video_feed0():
    return Response(gen_frames_from_cv2(cap0), mimetype='multipart/x-mixed-replace; boundary=frame')


# 本机视频
@app.route('/video_feed_form_file')
def video_feed_form_file():
    return Response(gen_frames_from_cv2(cap_form_file), mimetype='multipart/x-mixed-replace; boundary=frame')


@app.route('/video_feed_form_web_udp')
def video_feed_form_web_udp():
    return Response(gen_frames_from_web_udp(skt), mimetype='multipart/x-mixed-replace; boundary=frame')


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=9011)
    # save_result_from_cv2(cap_form_file)
