import request from '@/http/request.ts';

const BaseURL = '/device'

export function getDeviceList(params:Object){
    return request({
        url: BaseURL+'/getDeviceList',
        method: 'get',
        params
    })
}