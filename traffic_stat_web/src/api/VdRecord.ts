import request from '@/http/request.ts';

const BaseURL = '/record'

export function create(data:FormData){
    return request({
        url: BaseURL+'/create',
        method: 'post',
        data
    })
}

export function getStatData(params:Object){
    return request({
        url: BaseURL+'/getStatData',
        method: 'get',
        params
    })
}

export function getStatDataEveryDay(params:Object){
    return request({
        url: BaseURL+'/getStatDataEveryDay',
        method: 'get',
        params
    })
}