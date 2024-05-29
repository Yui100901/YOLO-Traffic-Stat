<template>
    <div class="container">
        <div class="main-title">
            欢迎来到交通流量数据展示平台
        </div>
        <div class="form-container">
            <div class="menu-box">
                <el-menu
                    :default-active="activeIndex"
                    class="menu-bar"
                    mode="horizontal"
                    @select="handleSelect"
                >
                    <el-menu-item index="0">登录</el-menu-item>
                    <el-menu-item index="1">注册</el-menu-item>
                    <el-menu-item index="2">找回密码</el-menu-item>
                </el-menu>
            </div>
            <div class="form-box">
                <el-form
                    :label-position="'left'"
                    label-width="100px"
                    :model="userDataForm"
                    style="max-width: 460px"
                    class="form-item-box"
                >
                    <el-form-item label="邮箱">
                        <el-input
                            v-model="userDataForm.email"
                            placeholder="请输入邮箱"
                            clearable
                            prefix-icon="Message"
                        />
                    </el-form-item>
                    <el-form-item label="昵称" v-if="activeIndex=='1'">
                        <el-input
                            v-model="userDataForm.nickname"
                            placeholder="请输昵称"
                            clearable
                            prefix-icon="User"
                        />
                    </el-form-item>
                    <el-form-item :label="activeIndex=='2'?'新密码':'密码'">
                        <el-input
                            v-model="userDataForm.password"
                            placeholder="请输入密码"
                            show-password
                            clearable
                            prefix-icon="Key"
                        />
                    </el-form-item>
                    <el-form-item label="确认密码" v-if="activeIndex!='0'">
                        <el-input
                            v-model="userDataForm.confirmPassword"
                            placeholder="请再次输入密码"
                            show-password
                            clearable
                            prefix-icon="Key"
                        />
                    </el-form-item>
                    <div class="captcha-box" v-if="activeIndex!='0'">
                        <el-form-item label="验证码">
                            <el-input
                                v-model="userDataForm.captcha"
                                placeholder="请输入验证码"
                                clearable
                                prefix-icon="Stamp"
                            />
                        </el-form-item>
                        <el-button type="primary" @click="getCaptchaCommand">发送验证码</el-button>
                    </div>
                </el-form>
            </div>
            <div class="button-box">
                <el-button type="primary" size="large" @click="loginCommand"
                           v-if="activeIndex=='0'">登录</el-button>
                <el-button type="primary" size="large" @click="registerCommand"
                           v-if="activeIndex=='1'">注册</el-button>
                <el-button type="primary" size="large" @click="retrieveCommand"
                           v-if="activeIndex=='2'">找回密码</el-button>
            </div>
        </div>

    </div>
</template>

<script lang="ts" setup>
import {login} from "@/api/Auth.ts";
import {ElMessage} from "element-plus";
import {reactive, ref} from "vue";
import {register, retrieve} from "@/api/VdUser.ts";
import {getCaptcha} from "@/api/Email.ts";
import {useRouter} from "vue-router";

const router=useRouter()

let activeIndex=ref('0')
let userDataForm=reactive({
    email: '',
    nickname:'',
    password: '',
    confirmPassword:'',
    captcha:'',
})

const getCaptchaCommand=()=>{
    const params={
        receiver:userDataForm.email,
    }
    getCaptcha(params).then(response=>{
        ElMessage.success("验证码发送成功！")
    }).catch((error)=>{
        ElMessage.error(error)
    })
}
const handleSelect=(key: string, keyPath: string[])=>{
    activeIndex.value=key
    console.log(key,keyPath)
    clearForm()
}
const clearForm=()=>{
    userDataForm.email=''
    userDataForm.nickname=''
    userDataForm.password=''
    userDataForm.confirmPassword=''
    userDataForm.captcha=''
}
const loginCommand=()=>{
    const data=new FormData()
    data.append("email",userDataForm.email)
    data.append("password",userDataForm.password)
    login(data).then(response=>{
        ElMessage.success("登录成功！")
        let loginInfo=response.data.data
        console.log(loginInfo)
        window.localStorage.setItem("trafficStatToken",loginInfo.token)
        window.localStorage.setItem("userEmail",loginInfo.email)
        window.localStorage.setItem("userNickname",loginInfo.nickname)
        clearForm()
        setTimeout(()=>{
            router.push({name:'main'})
        },3000)
    }).catch((error)=>{
        ElMessage.error(error.response.data.data)
    })
}
const registerCommand=()=>{
    if(userDataForm.password!=userDataForm.confirmPassword){
        ElMessage.error("两次输入密码不一致！")
    }
    const data=new FormData()
    data.append("email",userDataForm.email)
    data.append("nickname",userDataForm.nickname)
    data.append("password",userDataForm.password)
    data.append("captcha",userDataForm.captcha)
    register(data).then(response=>{
        ElMessage.success("注册成功！")
        clearForm()
        activeIndex.value='0'
    }).catch((error)=>{
        ElMessage.error(error.response.data.data)
    })
}
const  retrieveCommand=()=>{
    if(userDataForm.password!=userDataForm.confirmPassword){
        ElMessage.error("两次输入密码不一致！")
    }
    const data=new FormData()
    data.append("email",userDataForm.email)
    data.append("password",userDataForm.password)
    data.append("captcha",userDataForm.captcha)
    retrieve(data).then(response=>{
        ElMessage.success("找回密码成功！")
        clearForm()
        activeIndex.value='0'
    }).catch((error)=>{
        ElMessage.error(error.response.data.data)
    })
}

</script>

<style scoped>
.main-title{
    margin-bottom: 40px;
    font-size: 30px;
    font-weight: bold;
}
.sub-title{
    font-size: 20px;
    margin-bottom: 60px;
}
.container{
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
    align-items: center;
    justify-content: center;
}
.form-container{
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    height: 400px;
    width: 600px;
    background-color: aqua;
    border-radius: 30px;
    padding: 0 0 50px 0;
}
.menu-box{
    width: 100%;
}
.menu-bar{
    display: flex;
    justify-content: space-around;
    flex-direction: row;
    background-color: aqua;
    border-radius: 30px 30px 0 0;
}
.form-box{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.form-item-box{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.captcha-box{
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.button-box{
    display: flex;
    flex-direction: row;
    width: 80%;
    justify-content: space-around;
}
</style>