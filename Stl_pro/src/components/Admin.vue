<script setup>
import { reactive, ref } from 'vue';
import {ElMessage,ElMessageBox} from "element-plus";
import adminApi from '../api/adminApi';
import { Search } from '@element-plus/icons-vue'; // 导入 Search 图标
import { pa } from 'element-plus/es/locales.mjs';

const admins=ref([]);

const pageInfo =reactive({
    name:'',
    phone: '',
    currentPage: 1,
    pageSize: 3,
    total:0,
    search:'',
});

//展示用户信息
function loadAdmins(){

    adminApi.showAdminInfo(pageInfo).then(resp=>{
        
        admins.value=resp.data.records;
        pageInfo.total=resp.data.total;
    });
}
loadAdmins();

//c创建管理员
const addVisible=ref(false);
const addAdmin=ref({});
function showAdd(){
    addAdmin.value={};
    addVisible.value=true;
}

function handleAdd(){
    adminApi.addAdmin(addAdmin.value).then(resp=>{
        if(resp.code===0){
            //添加成功
            ElMessage({
                type:'success',
                message: '添加会员成功'
            })
            addVisible.value=false;
            addAdmin.value={};
        }else{
            ElMessage.error(resp.msg);
        
        }
        loadAdmins();
    });
}

//编辑管理员
const editVisible=ref(false);
const editAdmin=ref({});
function showEdit(row){
     // 创建对象深拷贝，切断引用关系
    editAdmin.value = JSON.parse(JSON.stringify(row));
    editVisible.value = true;
}

//更新管理员信息
function handleEdit(){
    adminApi.updateAdminInfo(editAdmin.value).then(resp=>{
        if(resp.code===0){
            ElMessage({
                type:'success',
                message: '更新管理员成功'
            })
            editVisible.value=false;
            editAdmin.value={};
        }else{
            ElMessage({
                type:'error',
                message: '更新管理员失败'
            })
        }
        loadAdmins();
    });
}

//查询
function onSearch(){
    // 清空之前的搜索条件
    pageInfo.search = '';
    
    // 优先判断name是否有值
    if (pageInfo.name !== null && pageInfo.name !== '') {
        pageInfo.search = pageInfo.name;

    } 
    // 再判断phone是否有值（当name无值时）
    else if (pageInfo.phone !== null && pageInfo.phone !== '') {
        pageInfo.search = pageInfo.phone;
    } 
    // 都无值时的处理
    else {
        ElMessage.error('未输入搜索条件');
    }
    
    console.log('最终search参数:', pageInfo.search);

    adminApi.showAdminInfo(pageInfo).then(resp=>{
        admins.value=resp.data.records;
        pageInfo.total=resp.data.total;
    });
}



</script>

<template>
    <div class="content">
        <el-form :model="pageInfo" :inline="true">
            <el-form-item label="名字" :label-width="60">
                <el-input v-model="pageInfo.name" placeholder="请输入管理员名字"/>
            </el-form-item>
            <el-form-item label="电话" :label-width="60">
                <el-input v-model="pageInfo.phone" placeholder="请输入管理员电话"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSearch"><el-icon><Search></Search></el-icon></el-button>
            </el-form-item>
        </el-form>
        <div class="bar">
            <el-button type="success" @click="showAdd" >创建管理员</el-button>
        </div>
        <el-table :data="admins" :stripe="true" :border="true" style="width: 100%">
            <el-table-column prop="id" label="ID" width="180" />
            <el-table-column prop="phone" label="手机号" width="180" />
            <el-table-column prop="name" label="姓名" />
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button size="small" type="success"
                        @click="showEdit(scope.row)">编辑</el-button> 
                </template>
            </el-table-column>
        </el-table>
        <el-pagination class="page" v-model:current-page="pageInfo.currentPage" v-model:page-size="pageInfo.pageSize"
            :total="pageInfo.total" :page-sizes="[10, 20, 50, 100]" :background="true"
            layout="total, sizes, prev, pager, next, jumper" @change="loadAdmins" />
    </div>

    <!-- 添加的弹窗 -->
    <el-dialog v-model="addVisible" title="创建新管理员" width="500">
        <el-form :model="addAdmin">
            <el-form-item label="电话" :label-width="60">
                <el-input v-model="addAdmin.phone" autocomplete="off" />
            </el-form-item>
            <el-form-item label="密码" :label-width="60">
                <el-input v-model="addAdmin.password" autocomplete="off" show-password/>
            </el-form-item>
            <el-form-item label="姓名" :label-width="60">
                <el-input v-model="addAdmin.name" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="addVisible = false">取消</el-button>
                <el-button type="primary" @click="handleAdd">
                    创建
                </el-button>
            </div>
        </template>
    </el-dialog>

    <!-- 编辑的弹窗 -->
    <el-dialog v-model="editVisible" title="编辑管理员" width="500">
        <el-form :model="editAdmin">
            <el-form-item label="电话" :label-width="60">
                <el-input v-model="editAdmin.phone" autocomplete="off" />
            </el-form-item>
            <el-form-item label="密码" :label-width="60">
                <el-input v-model="editAdmin.password" autocomplete="off" show-password/>
            </el-form-item>
            <el-form-item label="姓名" :label-width="60">
                <el-input v-model="editAdmin.name" autocomplete="off" />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="editVisible = false">取消</el-button>
                <el-button type="primary" @click="handleEdit">
                    编辑
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>

<style>

</style>