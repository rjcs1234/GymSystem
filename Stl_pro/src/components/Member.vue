<script setup>
import { reactive, ref } from 'vue';
import {ElMessage,ElMessageBox} from "element-plus"
import memberApi from '../api/memberApi';

//JavaScript 原生 Date 对象的内置方法。将日期转换为字符串
const formatDate = (row, column) => {
  if (!row.createTime) return '';
  
  const date = new Date(row.createTime);
  return `${date.getFullYear()}-${
    (date.getMonth() + 1).toString().padStart(2, '0')
  }-${
    date.getDate().toString().padStart(2, '0')
  }`;
}


const members=ref([]);
const pageInfo=reactive({
    currentPage: 1,
    pageSize: 3,
    total:0,
});

function loadMembers(){
    memberApi.getMemberInfo(pageInfo).then(resp=>{
        members.value=resp.data.records;
        pageInfo.total=resp.data.total;
    });
}
loadMembers();


//创建会员
const addVisible=ref(false);
const addMember=ref({});

function showAdd(){
    addVisible.value=true;
    addMember.value={};
}



//添加
function  handleAdd(){
    memberApi.add(addMember.value).then(resp=>{
        if(resp.code===0){
            //添加成功
            ElMessage({
                type:'success',
                message: '添加管理员成功'
            })
            addVisible.value=false;
            addMember.value={};
        }else{
            ElMessage({
                type:'error',
                message: '添加管理员失败'
            })
        }
        loadMembers();
    });
}

//编辑会员
const editVisible=ref(false);
const editMember=ref({});

function showEdit(row){
/*     浅拷贝
       editVisible.value=true;
       editMember.value=row; */

     // 创建对象深拷贝，切断引用关系
    editMember.value = JSON.parse(JSON.stringify(row));
    editVisible.value = true;
}

//更新会员
function handleEdit(){
    memberApi.updateInfo(editMember.value).then(resp=>{
        if(resp.code===0){
            ElMessage({
                type:'success',
                message: '更新会员成功'
            })
            editVisible.value=false;
            editMember.value={};
        }else{
            ElMessage({
                type:'error',
                message: '更新会员失败'
            })
        }
        loadMembers();
    });
}

//删除会员
function handleRemove(row){
   ElMessageBox.confirm('确认删除该信息么?','警告',{
         confirmButtonText:'确认',
         cancelButtonText:'取消',
         type:'warning'
   }).then(()=>{
       memberApi.remove(row.id).then(resp=>{
            if(resp.code ===0){
                 ElMessage({
                    type:'success',
                    message: '删除会员成功'
                })
            }else{
                 ElMessage({
                    type:'error',
                    message: '删除会员失败'
                })
            }
            loadMembers();
       });
   });
}


</script>

<template>
    <div class="content">
        <el-form :model="pageInfo" :inline="true">
            <el-form-item label="名字" :label-width="60">
                <el-input v-model="pageInfo.name" placeholder="请输入会员名字"/>
            </el-form-item>
            <el-form-item label="电话" :label-width="60">
                <el-input v-model="pageInfo.phone" placeholder="请输入会员电话"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSearch"><el-icon><Search></Search></el-icon></el-button>
            </el-form-item>
        </el-form>
        <div class="bar">
            <el-button type="success" @click="showAdd" >创建会员</el-button>
        </div>
        <el-table :data="members" :stripe="true" :border="true" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="phone" label="手机号" width="180" />
            <el-table-column prop="name" label="名字" />
            <el-table-column prop="balance" label="余额" :formatter="row=>'￥'+row.balance.toFixed(2)"/>
            <el-table-column prop="createTime" label="创建时间" :formatter="formatDate"  />
            <el-table-column label="操作" width="315">
                <template #default="scope">
                    <el-button size="small" type="primary"
                        @click="showEdit(scope.row)"><el-icon><ShoppingTrolley/></el-icon>&nbsp;消费</el-button> 
                    <el-button size="small" type="warning"
                        @click="showEdit(scope.row)"><el-icon><Money/></el-icon>&nbsp;充值</el-button> 
                    <el-button size="small" type="success"
                        @click="showEdit(scope.row)"><el-icon><edit/></el-icon>&nbsp;编辑</el-button> 
                    <el-button size="small" type="danger"
                        @click="handleRemove(scope.row)"><el-icon><delete/></el-icon>&nbsp;删除</el-button> 
                </template>
            </el-table-column>
        </el-table>
        <el-pagination class="page" v-model:current-page="pageInfo.currentPage" v-model:page-size="pageInfo.pageSize"
            :total="pageInfo.total" :page-sizes="[10, 20, 50, 100]" :background="true"
            layout="total, sizes, prev, pager, next, jumper" @change="loadMembers" />
    </div>

    <!-- 添加的弹窗 -->
    <el-dialog v-model="addVisible" title="创建新会员" width="500">
        <el-form :model="addMember">
            <el-form-item label="名字" :label-width="60">
                <el-input v-model="addMember.name" placeholder="请输入会员名字"/>
            </el-form-item>
            <el-form-item label="电话" :label-width="60">
                <el-input v-model="addMember.phone" placeholder="请输入会员电话"/>
            </el-form-item>
            <el-form-item label="密码" :label-width="60">
                <el-input v-model="addMember.password" show-password placeholder="请输入会员密码"/>
            </el-form-item>
            <el-form-item label="充值" :label-width="60">
                <el-input type="number" v-model="addMember.balance" placeholder="请输入充值金额"/>
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
    <el-dialog v-model="editVisible" title="编辑会员" width="500">
        <el-form :model="editMember">
            <el-form-item label="名字" :label-width="60">
                <el-input v-model="editMember.name" placeholder="请输入会员名字"/>
            </el-form-item>
            <el-form-item label="电话" :label-width="60">
                <el-input v-model="editMember.phone" placeholder="请输入会员电话"/>
            </el-form-item>
            <el-form-item label="余额" :label-width="60">
                <el-input v-model="editMember.balance"  placeholder="请输入余额"/>
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="editVisible = false">取消</el-button>
                <el-button type="primary" @click="handleEdit">
                    更新
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>

<style >


</style>