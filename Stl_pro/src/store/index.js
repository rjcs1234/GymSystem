import {defineStore}  from "pinia"

//管理登录的信息
export const adminStore=defineStore("admin",{
    //状态管理库初始化
    state:()=>{
        return {};
    },

    actions:{
        //动作定义的函数
        update(admin){
            this.admin=admin;
        }
    }

});

//管理令牌的状态管理库
export const tokenStore=defineStore("token",{
    state:()=>{
        return {token:localStorage.getItem("token")}
    },

    actions:{
        update(token){
            this.token=token;
        }
    }
})