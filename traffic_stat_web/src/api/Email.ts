import request from '@/http/request.ts';

const BaseURL = '/email'

//验证码接口
//需要接收者邮箱
export function getCaptcha(params:Object){
    return request({
        url: BaseURL+'/getCaptcha',
        method: 'get',
        params
    })
}