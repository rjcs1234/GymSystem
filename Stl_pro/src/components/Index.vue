<script setup>
import { reactive, ref } from 'vue';
import router from '@/router'
import { adminStore, tokenStore }  from '../store';
import adminApi from '@/api/adminApi';

const activeIndex = ref('1')

// 取出当前登录用户的信息
const loginAdmin = ref({
    name: "未登录",
});


// 菜单  用户管理， 分类管理， 商品管理
const menuData = ref([
    {name: '预约管理', icon: 'GobletFull', children: [
        {name: '预约列表', icon: 'GobletSquareFull', path: "/reserve"},
        {name: '数据统计', icon: 'TrendCharts', path: "/charts"},
    ]},
    {name: '课程管理', icon: 'Notebook', children: [
        {name: '课程列表', icon: 'Document', path: "/course"},
        {name: '课程日历', icon: 'Calendar', path: "/courseCalendar"},
    ]},
    {name: '会员管理', icon: 'User', children: [
        {name: '会员列表', icon: 'UserFilled', path: "/member"},
    ]},
    {name: '教练管理', icon: 'Avatar', children: [
        {name: '教练列表', icon: 'List', path: "coach"},
    ]},
    {name: '管理员管理', icon: 'Lock', children: [
        {name: '管理员列表', icon: 'Setting', path: "admin"},
    ]},
]);

function getLoginAdmin(){
    adminApi.getLoginAdmin().then(result=>{
        if (result.code == 0) {
            loginAdmin.value = result.data;
        } else {
            router.push('/login')
        }
    });
}
getLoginAdmin();

// 用户下拉菜单的点击
function handleSelect(key, keyPath){
    if (key == 'logout'){
        // 用户退出
        logout();
    }
}
// 左侧菜单动作
function handleMenu(key){
    if (key){
        router.push(key);
    }
}
// 退出 
function logout(){
    // 清除Token
    tokenStore().update(null);
    adminStore().update(null);

    // 跳转到登录
    router.push('/login')
}

// 左侧菜单展开折叠
const isCollapse = ref(false);
</script>

<template>
    <div class="common-layout">
        <el-container>
            <el-aside :width="1">
                
                <div style="height: 60px;line-height: 60px; text-align: right; color: #FFF; font-size: 20px; margin-right: 24px;">
                    <el-icon @click="isCollapse=!isCollapse">
                        <Expand v-if="isCollapse"></Expand>
                        <Fold v-else></Fold>
                    </el-icon> 
                </div>

                <el-menu active-text-color="#ffd04b" background-color="#0c1d40" class="el-menu-vertical-demo"
                    default-active="/charts" text-color="#fff" :collapse="isCollapse"
                    @select="handleMenu">
                    <!-- 动态生成菜单 -->
                    <el-sub-menu v-for="(menu, index) in menuData" :index="index.toString()">
                        <template #title>
                            <component
                                class="icons"
                                :is="menu.icon" 
                                style="width: 1em; height: 1em; margin-right: 8px" >
                            </component>
                            <span>{{ menu.name }}</span>
                        </template>
                        <el-menu-item v-for="(child, ind) in menu.children" :index="child.path">
                                <el-icon><component 
                                    :is="child.icon">
                                </component></el-icon>
                                <span>{{ child.name }}</span>
                        </el-menu-item>
                    </el-sub-menu>

                </el-menu>
            </el-aside>
            <el-container>
                <el-header>
                    <el-menu :default-active="activeIndex" 
                        class="el-menu-demo" 
                        mode="horizontal" 
                        :ellipsis="false"
                        background-color="#f3f7ff"
                        text-color="#393939" 
                        active-text-color="#ffd04b"
                        @select="handleSelect">

                        <el-menu-item index="0">
                            <img style="height: 80%" src="@/assets/logo.png" alt="中享思途" />
                            <span style="padding-left: 50px; font-size: 24px; color:#393939;">思泰隆-健身房课程预约系统(STL-gym)</span>
                        </el-menu-item>
                        <div class="flex-grow" />                        
                        <el-sub-menu index="1">
                            <template #title>
                                <el-icon><Stamp></Stamp></el-icon>
                                {{ loginAdmin?loginAdmin.name:'没登录' }}
                            </template>
                            <el-menu-item index="1-1">修改信息</el-menu-item>
                            <el-menu-item index="1-2">重置密码</el-menu-item>
                            <el-menu-item index="logout">退出
                                <el-icon><SwitchButton></SwitchButton></el-icon>
                            </el-menu-item>
                        </el-sub-menu>
                        
                    </el-menu>
                </el-header>
                <el-main>
                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>
  
  

<style>
.flex-grow {
  flex-grow: 1;
}

.common-layout,
.el-container {
    height: 100%;
}

.el-header {
    --el-header-padding: 0;
    --el-header-height: 80px;
    box-sizing: border-box;
    flex-shrink: 0;
    height: var(--el-header-height);
    padding: var(--el-header-padding);
}

.el-menu--horizontal {
    height: 80px;
}

.el-aside {
    background-color: #0c1d40;
}

.el-main{
    padding: 0px;
}

.el-menu{
    border-width: 0px;
}
.el-menu-item{
    font-size: 16px;
}
.el-menu-demo{
    box-shadow: 0 2px 4px rgba(0,0,0,0.25);
}

.el-sub-menu__title{
    font-size: 16px;
}

</style>