import {createRouter, createWebHistory, Router} from 'vue-router'
import {loginVerify} from "@/api/Auth.ts";

const router:Router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'index',
            component:()=>import ('@/views/Index.vue'),
            meta:{
                title:'主页'
            },
        },
        {
            path: '/main',
            name: 'main',
            component:()=>import ('@/views/Main.vue'),
            meta:{
                auth:true,
                title:'展示主页'
            },
        },
    ]
})

router.beforeEach(async (to, from, next) => {
    // 判断该路由是否需要登录权限
    if (to.matched.some(record => record.meta.auth) && to.meta.auth) { // 判断该路由是否需要登录权限
        let token = localStorage.getItem('trafficStatToken');
        if (token) { // 获取当前的 token 是否存在
            await loginVerify().then((response) => {
                if (response.data.data===true){
                    console.log("登录验证通过！")
                    next()
                }else{
                    //权限不够。跳回主页
                    next({
                        path: '/'
                    })
                }
            })
                .catch((error) => {
                    console.log(error)
                    next({
                        path: '/'
                    })
                })
        } else {
            // 不存在 token，需要重新认证
            next({
                path: '/'
            })
        }
    }else {
        next();
    }
});

export default router
