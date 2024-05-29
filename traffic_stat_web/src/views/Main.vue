<template>
  <div class="container">
      <div class="menu-box">
          <el-menu
                  :default-active="activeIndex"
                  class="menu-bar"
                  mode="horizontal"
                  @select="handleSelect"
          >
              <el-menu-item index="0">主页</el-menu-item>
              <el-menu-item index="1">统计图</el-menu-item>
              <el-menu-item index="2">统计表</el-menu-item>
              <el-menu-item index="3">实时视频</el-menu-item>
          </el-menu>
      </div>
      <div>
          <div>
              <div>
                  欢迎{{loginUser}}！
                  <el-button type="danger" @click="executeLogout">
                    退出登录
                  </el-button>
              </div>
              <div>

              </div>
          </div>
          <div class="device-selector" v-if="activeIndex==0||
          activeIndex==1||activeIndex==2">
              选择设备：
              <el-select v-model="selectedDevice" placeholder="请选择设备"
                         style="width: 240px"
                         @change="selectedDeviceChanged">
                  <el-option
                      v-for="item in deviceList"
                      :key="item.value"
                      :label="item.name"
                      :value="item.value"
                  />
              </el-select>
              <div class="device-info">
                  IP地址：{{ deviceList[selectedDevice].ip}}
              </div>
              <div class="device-info">
                  地址：{{ deviceList[selectedDevice].address}}
              </div>
          </div>
      </div>
      <div class="content-box" v-show="activeIndex==0">
          <div id="last-day-pie-chart">
          </div>
         <div id="last-week-pie-chart">
         </div>
          <div id="last-month-pie-chart">
          </div>
      </div>
      <div class="content-box" v-show="activeIndex==1">
          <div id="last-month-bar-chart"></div>
          <div id="last-month-line-chart"></div>
      </div>
      <div class="content-box" v-show="activeIndex==2">
          近30日统计数据表
          <el-table :data="tableData" style="width: 100%" height="600">
              <el-table-column fixed prop="date" label="日期" width="150" />
              <el-table-column prop="all_total" label="日车流总量" width="150" />
              <el-table-column prop="bike_in" label="两轮车入总量" width="150" />
              <el-table-column prop="bike_out" label="两轮车出总量" width="150" />
              <el-table-column prop="bus_in" label="公共汽车入总量" width="150" />
              <el-table-column prop="bus_out" label="公共汽车出总量" width="150" />
              <el-table-column prop="car_in" label="汽车入总量" width="150" />
              <el-table-column prop="car_out" label="汽车出总量" width="150" />
              <el-table-column prop="truck_in" label="卡车入总量" width="150" />
              <el-table-column prop="truck_out" label="卡车出总量" width="150" />
          </el-table>
      </div>
      <div class="content-box" v-show="activeIndex==3">
          <div class="video-list">
              <div class="video-box" v-for="item in webVideoPathList">
                  <div>视频</div>
                  <img class="image-box" :src="item" alt="暂无相关视频画面！">
              </div>
          </div>
      </div>
  </div>
</template>

<script lang="ts" setup>
import {nextTick, onMounted, reactive, ref} from "vue";
import * as echarts from "echarts";
import {ElMessage} from "element-plus";
import {getStatData, getStatDataEveryDay} from "@/api/VdRecord.ts";
import {getDeviceList} from "@/api/VdDevice.ts";
import {logout} from "@/api/Auth.ts";
import {useRouter} from "vue-router";
const router=useRouter()
let activeIndex=ref('0')
let loginUser=ref('')
const webVideoPathList=ref(
    ['http://127.0.0.1:9011/video_feed0',
        'http://127.0.0.1:9011/video_feed_form_file']
)

let executeLogout=()=>{
    logout(null).then(response=>{
        ElMessage.success("退出登录成功！")
        window.localStorage.removeItem("trafficStatToken")
        window.localStorage.removeItem("userEmail")
        window.localStorage.removeItem("userNickname")
        let logoutData=response.data.data
        setTimeout(()=>{
            router.push({name:'index'})
        },3000)
        console.log(logoutData)
    }).catch((error)=>{
        ElMessage.error(error)
    })
}

let deviceList=reactive([
    {
        name: "所有设备",
        value: 0,
        ip:'/',
        address:'/'
    }
])
let selectedDevice=ref(0)
const selectedDeviceChanged=(value)=>{
    console.log(value)
    if (value==0){
        renderData(null)
    }else {
        renderData(value)
    }

}

let dayPieOption=reactive({
    title: {
        text: '近一天占比饼图',
        subtext: 'Proportion',
        left: 'center'
    },
    tooltip: {
        trigger: 'item'
    },
    legend: {
        orient: 'vertical',
        left: 'left'
    },
    series: [
        {
            name: 'Direction',
            type: 'pie',
            radius: '50%',
            center: ['25%', '50%'],
            data: [
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        },
        {
            name: 'Type',
            type: 'pie',
            radius: '50%',
            center: ['75%', '50%'],
            data: [
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
})

let weekPieOption=reactive({
    title: {
        text: '近一周占比饼图',
        subtext: 'Proportion',
        left: 'center'
    },
    tooltip: {
        trigger: 'item'
    },
    legend: {
        orient: 'vertical',
        left: 'left'
    },
    series: [
        {
            name: 'Direction',
            type: 'pie',
            radius: '50%',
            center: ['25%', '50%'],
            data: [
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        },
        {
            name: 'Type',
            type: 'pie',
            radius: '50%',
            center: ['75%', '50%'],
            data: [
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
})

let monthPieOption=reactive({
    title: {
        text: '近一个月占比饼图',
        subtext: 'Proportion',
        left: 'center'
    },
    tooltip: {
        trigger: 'item'
    },
    legend: {
        orient: 'vertical',
        left: 'left'
    },
    series: [
        {
            name: 'Direction',
            type: 'pie',
            radius: '50%',
            center: ['25%', '50%'],
            data: [
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        },
        {
            name: 'Type',
            type: 'pie',
            radius: '50%',
            center: ['75%', '50%'],
            data: [
            ],
            emphasis: {
                itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
        }
    ]
})

const monthBarOption = {
    title: {
        text: '近一月每日总量统计图',
        subtext: 'Total',
        left: 'center'
    },
    tooltip: {
        trigger: 'item',
        axisPointer: {
            type: 'shadow'
        }
    },
    xAxis: {
        data: []
    },
    yAxis: {
        type: 'log'
    },
    legend:{
        orient: 'vertical',
        left: 'left',
        data: ['bike_in', 'bike_out', 'bus_in','bus_out',
            'car_in', 'car_out','truck_in', 'truck_out']
    },
    series: [
        {
            name: 'bike_in',
            type: 'bar',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
        {
            name: 'bike_out',
            type: 'bar',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
        {
            name: 'bus_in',
            type: 'bar',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
        {
            name: 'bus_out',
            type: 'bar',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
        {
            name: 'car_in',
            type: 'bar',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
        {
            name: 'car_out',
            type: 'bar',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
        {
            name: 'truck_in',
            type: 'bar',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
        {
            name: 'truck_out',
            type: 'bar',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
    ]
};

const monthLineOption = {
    title: {
        text: '近一月每日总量统计图',
        subtext: 'Total',
        left: 'center'
    },
    tooltip: {
        trigger: 'item',
        axisPointer: {
            type: 'shadow'
        }
    },
    xAxis: {
        data: []
    },
    yAxis: {
        type: 'log'
    },
    legend:{
        orient: 'vertical',
        left: 'left',
        data: ['bike_in', 'bike_out', 'bus_in','bus_out',
            'car_in', 'car_out','truck_in', 'truck_out']
    },
    series: [
        {
            name: 'bike_in',
            type: 'line',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
        {
            name: 'bike_out',
            type: 'line',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
        {
            name: 'bus_in',
            type: 'line',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
        {
            name: 'bus_out',
            type: 'line',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
        {
            name: 'car_in',
            type: 'line',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
        {
            name: 'car_out',
            type: 'line',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
        {
            name: 'truck_in',
            type: 'line',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
        {
            name: 'truck_out',
            type: 'line',
            emphasis: {
                focus: 'series'
            },
            data: []
        },
    ]
};

let tableData = reactive([])

const handleSelect=(key: string, keyPath: string[])=>{
    activeIndex.value=key
    nextTick(()=>{
        drawPieDayChart()
        drawPieWeekChart()
        drawPieMonthChart()
        drawBarMonthChart()
        drawLineMonthChart()
    })
    console.log(key,keyPath)
}

const drawPieDayChart=()=>{
    let chart = echarts.init(
        document.getElementById('last-day-pie-chart')
    );
    chart.setOption(dayPieOption)
    chart.resize()
}

const drawPieWeekChart=()=>{
    let chart = echarts.init(
        document.getElementById('last-week-pie-chart')
    );
    chart.setOption(weekPieOption)
    chart.resize()
}

const drawPieMonthChart=()=>{
    let chart = echarts.init(
        document.getElementById('last-month-pie-chart')
    );
    chart.setOption(monthPieOption)
    chart.resize()
}

const drawBarMonthChart=()=>{
    let chart = echarts.init(
        document.getElementById('last-month-bar-chart')
    );
    chart.setOption(monthBarOption)
    chart.resize()
}

const drawLineMonthChart=()=>{
    let chart = echarts.init(
        document.getElementById('last-month-line-chart')
    );
    chart.setOption(monthLineOption)
    chart.resize()
}

const getDeviceListData=()=>{
    getDeviceList({
        name:null,
        ip:null,
        address:null,
    }).then(response=>{
        let deviceListData=response.data.data
        for (let i = 0; i < deviceListData.length; i++) {
            let deviceData={
                name:deviceListData[i].name,
                value:deviceListData[i].id,
                ip:deviceListData[i].ip,
                address:deviceListData[i].address
            }
            deviceList.push(deviceData)
        }
        console.log(deviceListData)
    }).catch((error)=>{
        ElMessage.error(error)
    })
}

const renderData=(device)=>{
    getStatData({
        timeInterval:1,
        deviceId:device,
        vehicleType:null,
        direction:null,
        vehicleNumber:null
    }).then(response=>{
        let statData=response.data.data
        let dayDirectionSeriesData=[
            { value: statData.inTotal, name: '入流量'},
            { value: statData.outTotal, name: '出流量'}
        ]
        let dayTypeSeriesData=[
            { value: statData.bikeTotal, name: 'Bike'},
            { value: statData.busTotal, name: 'Bus'},
            { value: statData.carTotal, name: 'Car'},
            { value: statData.truckTotal, name: 'Truck'}
        ]
        dayPieOption.series[0].data=dayDirectionSeriesData
        dayPieOption.series[1].data=dayTypeSeriesData
        console.log(statData)
        nextTick(() => {
            drawPieDayChart()
        });
    }).catch((error)=>{
        ElMessage.error(error)
    })
    getStatData({
        timeInterval:7,
        deviceId:device,
        vehicleType:null,
        direction:null,
        vehicleNumber:null
    }).then(response=>{
        let statData=response.data.data
        let weekDirectionSeriesData=[
            { value: statData.inTotal, name: '入流量'},
            { value: statData.outTotal, name: '出流量'}
        ]
        let weekTypeSeriesData=[
            { value: statData.bikeTotal, name: 'Bike'},
            { value: statData.busTotal, name: 'Bus'},
            { value: statData.carTotal, name: 'Car'},
            { value: statData.truckTotal, name: 'Truck'}
        ]
        weekPieOption.series[0].data=weekDirectionSeriesData
        weekPieOption.series[1].data=weekTypeSeriesData
        console.log(statData)
        nextTick(() => {
            drawPieWeekChart()
        });
    }).catch((error)=>{
        ElMessage.error(error)
    })
    getStatData({
        timeInterval:30,
        deviceId:device,
        vehicleType:null,
        direction:null,
        vehicleNumber:null
    }).then(response=>{
        let statData=response.data.data
        let monthDirectionSeriesData=[
            { value: statData.inTotal, name: '入流量'},
            { value: statData.outTotal, name: '出流量'}
        ]
        let monthTypeSeriesData=[
            { value: statData.bikeTotal, name: 'Bike'},
            { value: statData.busTotal, name: 'Bus'},
            { value: statData.carTotal, name: 'Car'},
            { value: statData.truckTotal, name: 'Truck'}
        ]
        monthPieOption.series[0].data=monthDirectionSeriesData
        monthPieOption.series[1].data=monthTypeSeriesData
        console.log(statData)
        nextTick(() => {
            drawPieMonthChart()
        });
    }).catch((error)=>{
        ElMessage.error(error)
    })
    getStatDataEveryDay({
        timeInterval:31,
        deviceId:device,
        vehicleType:null,
        direction:null,
        vehicleNumber:null
    }).then(response=>{
        let statDataEveryDay=response.data.data
        console.log(statDataEveryDay)
        let dayX=[]
        let bikeIn=[]
        let busIn=[]
        let carIn=[]
        let truckIn=[]
        let bikeOut=[]
        let busOut=[]
        let carOut=[]
        let truckOut=[]
        let dayKeyList = Object.keys(statDataEveryDay).sort();//对键进行排序
        tableData=[]
        for(let i = 0; i < dayKeyList.length; i++) {
            if(i==0){
                continue//去掉第一天的值
            }
            let key =dayKeyList[i]
            let value = statDataEveryDay[dayKeyList[i]]; //取出该键的值
            //构造图表数据
            dayX.push(key)
            bikeIn.push(value.bikeInTotal)
            busIn.push(value.busInTotal)
            carIn.push(value.carInTotal)
            truckIn.push(value.truckOutTotal)
            bikeOut.push(value.bikeOutTotal)
            busOut.push(value.busOutTotal)
            carOut.push(value.carOutTotal)
            truckOut.push(value.truckOutTotal)
            //构造表格数据
            tableData.push({
                date:key,
                all_total:value.allTotal,
                bike_in:value.bikeInTotal,
                bike_out:value.bikeOutTotal,
                bus_in:value.busInTotal,
                bus_out:value.busOutTotal,
                car_in:value.carInTotal,
                car_out:value.carOutTotal,
                truck_in:value.truckInTotal,
                truck_out:value.truckOutTotal,
            })
        }
        console.log(tableData)
        monthBarOption.xAxis.data=dayX
        monthBarOption.series[0].data=bikeIn
        monthBarOption.series[1].data=bikeOut
        monthBarOption.series[2].data=busIn
        monthBarOption.series[3].data=busOut
        monthBarOption.series[4].data=carIn
        monthBarOption.series[5].data=carOut
        monthBarOption.series[6].data=truckIn
        monthBarOption.series[7].data=truckOut
        monthLineOption.xAxis.data=dayX
        monthLineOption.series[0].data=bikeIn
        monthLineOption.series[1].data=bikeOut
        monthLineOption.series[2].data=busIn
        monthLineOption.series[3].data=busOut
        monthLineOption.series[4].data=carIn
        monthLineOption.series[5].data=carOut
        monthLineOption.series[6].data=truckIn
        monthLineOption.series[7].data=truckOut
        nextTick(() => {
            drawBarMonthChart()
            drawLineMonthChart()
        });
    }).catch((error)=>{
        ElMessage.error(error)
    })
}

onMounted(()=>{
    getDeviceListData()
    loginUser.value=window.localStorage.getItem("userNickname")
    renderData(null)
})

</script>

<style scoped>
.container{
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100vh;
    align-items: center;
    justify-content: space-between;
}
.menu-box{
    width: 100%;
}
.menu-bar{
    display: flex;
    justify-content: space-around;
    flex-direction: row;
    background-color: aqua;
}
.content-box{
    width: 100%;
    height: 100%;
    margin-top: 30px;
}
.el-dropdown-link {
    cursor: pointer;
    color: var(--el-color-primary);
    display: flex;
    align-items: center;
}
#last-day-pie-chart{
    width: 100%;
    height: 100%;
}
#last-week-pie-chart{
    width: 100%;
    height: 100%;
}
#last-month-pie-chart{
    width: 100%;
    height: 100%;
}
#last-month-bar-chart{
    width: 100%;
    height: 100%;
}
#last-month-line-chart{
    width: 1280px;
    height: 720px;
}
.video-list{
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}
.video-box{
    width: 80%;
    height: 80%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}
</style>