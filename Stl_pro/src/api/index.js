import axios from "axios";
import router from "../router";
import { tokenStore } from "../store";
import { ElMessage } from "element-plus";


//创建请求对象
const service=axios.create({
    baseURL:"/api/",
    timeout:2000
})

//请求的拦截器
service.interceptors.request.use(
    request=>{
        //将状态管理库中的token数据添加到请求头中
        request.headers.token=tokenStore().token;
        return request;
    },
)

//响应拦截器
service.interceptors.response.use(
    response=>{
        if(response.headers.token){
            tokenStore().update(response.headers.token);
        }
        return response;
    },
    error=>{
        if(!error.response){
            return Promise.reject(error);
        }
        if(error.response.status==404){
            console.log("页面没有找到!")
        }else if(error.response.status==403){
            //没有权限
            ElMessage({
                type:"error",
                message:error.response.data.msg,
                offset:250
            })
        }else if(error.response.status==401){
            //没有令牌
            router.push("/login")
        }else{
            return Promise.reject(error);
        }
    }
)

export default service