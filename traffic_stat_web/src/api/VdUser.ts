import request from '@/http/request.ts';

const BaseURL = '/user'

export function register(data:FormData){
    return request({
        url: BaseURL+'/register',
        method: 'post',
        data
    })
}

export function retrieve(data:FormData){
    return request({
        url: BaseURL+'/retrieve',
        method: 'post',
        data
    })
}




