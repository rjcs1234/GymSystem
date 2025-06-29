<script setup>
import { reactive, ref } from 'vue';
import {useRouter} from  "vue-router"
import {adminStore,tokenStore} from "../store"
import captchaApi  from  "@/api/captchaApi.js"
import {ElMessage} from "element-plus"
// 引入axios编写api
import adminApi from '../api/adminApi.js'
const admin = reactive({
    phone: '13312341234',
    password: '123',
    captcha: '',
});
// 定义存放验证码图片
const captcha = ref("")

//验证码
function loadCaptcha(){
    captchaApi.getCaptcha().then(result=>{
         captcha.value = result.data;
         admin.key = result.msg;
         console.log(admin.key);
    });
}
loadCaptcha();

// 创建路由对象
const  router = useRouter();
//登录
function onSubmit(){
    adminApi.login(admin).then(result=>{
        if(result.code ==0){
            //success
            tokenStore().update(result.msg)
            router.push("/charts")
        }else{
            ElMessage({
                type: "error",
                message: result.msg,
                offset: 250
            });
            loadCaptcha(); //加载验证码
        }
    });
}



</script>


<template>
    <div class="login-bg">
        <el-form class="form-login" :model="admin">
            <el-row>
                <el-col :span="10">
                    <img style="width: 100px" src="@/assets/logo.png" alt="中享思途" />
                </el-col>
                <el-col :span="14">
                    <h1>系统登录</h1>    
                </el-col>
            </el-row>
            <el-form-item>
                <el-input v-model="admin.phone" 
                            placeholder="请输入手机号"
                            :prefix-icon="User"
                            maxlength="16"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input v-model="admin.password" 
                            placeholder="请输入密码" 
                            show-password
                            :prefix-icon="Lock"
                            maxlength="16"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input v-model="admin.captcha" 
                            placeholder="验证码" 
                            :prefix-icon="Key"
                            maxlength="5" 
                            class="input-verCode"></el-input>
                <img :src="captcha" 
                    class="img-verCode" @click="loadCaptcha">
            </el-form-item>
            <el-form-item>
                <el-button class="btn-login" type="success" @click="onSubmit">登录</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<style scoped>

.login-bg{
    height: 100%;
    background-image: url('../assets/b1.jpg');
    background-repeat: no-repeat;
    background-position: center;
    background-attachment: fixed;
    background-size: cover;
}

.form-login{
    width: 280px;
    padding: 20px;
    position: absolute;
    top: 20%;
    left: calc(50% - 150px);
    background-color: #FFF;
    box-shadow: 10px 10px 30px #000;
}

.btn-login{
    width: 100%;
}
h1{
    text-align: center;
    font-size: 24px;
    margin-bottom: 18px;
    height: 50px;
    line-height: 50px;
}
.input-verCode{
    width: 160px;
}
.img-verCode{
    width: 80px;
    height:30px;
}
</style>