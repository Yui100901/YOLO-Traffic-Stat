import request from '@/http/request.ts';

const BaseURL = '/auth'

export function login(data:FormData){
    return request({
        url: BaseURL+'/login',
        method: 'post',
        data
    })
}

export function logout(data: any){
    return request({
        url: BaseURL+'/logout',
        method: 'post',
        data
    })
}

export function loginVerify(data:FormData|void){
    return request({
        url: BaseURL+'/loginVerify',
        method: 'post',
        data
    })
}
