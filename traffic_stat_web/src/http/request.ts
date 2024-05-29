import axios from 'axios';

const ip = window.localStorage.getItem('ip')
const address = window.localStorage.getItem('address')

const service = axios.create({
    baseURL: 'http://127.0.0.1:9012/',//本地开发
    // baseURL: '',//服务器部署地址
    timeout: 30000,
    headers:{
        "ip":`${ip}`,
        "address":`${encodeURIComponent(address)}`
    }
})

//请求拦截
service.interceptors.request.use(
    (config) => {
        let token = window.localStorage.getItem('trafficStatToken')
        // do something before request is sent
        if (token) {
            // let each request carry token
            // ['X-Token'] is a custom headers key
            // please modify it according to the actual situation
            config.headers['token'] = token
        }
        return config

    }
    // },
    // error => {
    //   // do something with request error
    //   console.log(error) // for debug
    //   return Promise.reject(error)
    // }
)

// 响应拦截
// axios.interceptors.response.use(
//     (response) => {
//         if (response.status&&response.status==200){
//             return response
//         }
// }, error => {
//     // // 在请求错误时要做的事儿
//     // // 该返回的数据则是axios.catch(err)中接收的数据
//     // return Promise.reject(err)
//     ElMessage.error(error.response.data)
// })

export default service