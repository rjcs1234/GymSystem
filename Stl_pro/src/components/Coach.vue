<script setup>
import { reactive, ref } from 'vue';
import {ElMessage,ElMessageBox} from "element-plus"
import coachApi from "../api/coachApi";



const coachs=ref([]);
const pageInfo=reactive({
    name:'',
    phone:'',
    currentPage: 1,
    pageSize: 3,
    total:0,
});


function loadCoachs(){
    coachApi.getCoachApi(pageInfo).then(resp=>{
        coachs.value=resp.data.records;
        pageInfo.total=resp.data.total;
    });
}
loadCoachs();

//添加教练
const addVisible=ref(false);
const addCoach=ref({});
function showAdd(){
    addVisible.value=true;
    addCoach.value={};
}
//处理文件上传
function handleAddAvatarSuccess(respone,uploadFile){
    addCoach.value.photo=respone.msg;
}


function handleAdd(){
    coachApi.add(addCoach.value).then(resp=>{
         if(resp.code===0){
            //添加成功
            ElMessage({
                type:'success',
                message: '添加教练成功'
            })
            addVisible.value=false;
            addCoach.value={};
        }else{

            ElMessage.error(resp.msg);
        }
        loadCoachs();
    });
}

//编辑教练信息
const editVisible=ref(false);
const editCoach=ref({});

function showEdit(row){
    editVisible.value=true;
    // 创建对象深拷贝，切断引用关系
    editCoach.value=JSON.parse(JSON.stringify(row));
}

//处理文件编辑
function handleEditAvatarSuccess(respone,uploadFile){
    editCoach.value.photo=respone.msg;

}
//提交编辑
function handleEdit(){
    coachApi.update(editCoach.value).then(resp => {
    if (resp.code === 0) {
      // 编辑成功
      ElMessage({
        type: 'success',
        message: '编辑教练成功'
      });

      editVisible.value = false;
      editCoach.value={};

    } else {
      ElMessage.error(resp.msg);
    }
    loadCoachs(); // 重新加载教练列表
  });
}

//删除教练
function handleRemove(row){
    ElMessageBox.confirm('确认删除该信息么?','警告',{
         confirmButtonText:'确认',
         cancelButtonText:'取消',
         type:'warning'
   }).then(()=>{
       coachApi.remove(row.id).then(resp=>{
            if(resp.code ===0){
                 ElMessage({
                    type:'success',
                    message: '删除教练成功'
                })
            }else{
                 ElMessage({
                    type:'error',
                    message: '删除教练失败'
                })
            }
            loadCoachs();
       });
   });

}



</script>

<template>
    <div class="content">
        <el-form :model="pageInfo" :inline="true">
            <el-form-item label="姓名" :label-width="60">
                <el-input v-model="pageInfo.name" clearable placeholder="请输入教练名字"/>
            </el-form-item>
            <el-form-item label="手机号" :label-width="60">
                <el-input v-model="pageInfo.phone" clearable placeholder="请输入教练手机"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSearch"><el-icon><Search></Search></el-icon></el-button>
            </el-form-item>
        </el-form>
        <div class="bar">
            <el-button type="success" @click="showAdd" >创建教练</el-button>
        </div>
        <el-table :data="coachs" :stripe="true" :border="true" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="photo" label="头像" >
                <template #default="scope">
                    <img :src="scope.row.photo" style="max-height: 60px; max-width: 120px;"/>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="姓名" />
            <el-table-column prop="phone" label="手机号" width="180" />
            <el-table-column prop="wechat" label="微信" />
            <el-table-column prop="recomm" label="简介" />
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button size="small" type="success"
                        @click="showEdit(scope.row)">编辑</el-button> 
                    <el-button size="small" type="danger"
                        @click="handleRemove(scope.row)">删除</el-button> 
                </template>
            </el-table-column>
        </el-table>
        <el-pagination class="page" v-model:current-page="pageInfo.currentPage" v-model:page-size="pageInfo.pageSize"
            :total="pageInfo.total" :page-sizes="[3, 5, 10, 20]" :background="true"
            layout="total, sizes, prev, pager, next, jumper" @change="loadCoachs" />
    </div>

    <!-- 添加的弹窗 -->
    <el-dialog v-model="addVisible" title="创建新教练" width="500">
        <el-form :model="addCoach">
            <el-form-item label="照片" :label-width="60">
                <el-upload
                    class="avatar-uploader"
                    name="file"
                    action="/api/file/upload"
                    :show-file-list="false"
                    :on-success="handleAddAvatarSuccess"
                >
                    <img v-if="addCoach.photo" :src="addCoach.photo" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
                </el-upload>
            </el-form-item>
            <el-form-item label="名字" :label-width="60">
                <el-input v-model="addCoach.name" placeholder="请输入教练名字"/>
            </el-form-item>
            <el-form-item label="电话" :label-width="60">
                <el-input v-model="addCoach.phone" placeholder="请输入教练电话"/>
            </el-form-item>
            <el-form-item label="微信" :label-width="60">
                <el-input v-model="addCoach.wechat" placeholder="请输入教练微信"/>
            </el-form-item>
            <el-form-item label="简介" :label-width="60">
                <el-input
                    :rows = "5"
                    maxlength="255"
                    placeholder="请输入教练简介"
                    show-word-limit
                    type="textarea"
                    v-model="addCoach.recomm"
                    autocomplete="off" />
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
    <el-dialog v-model="editVisible" title="编辑教练" width="500">
        <el-form :model="editCoach">
            <el-form-item label="照片" :label-width="60">
                <el-upload
                    class="avatar-uploader"
                    action="/api/file/upload"
                    :show-file-list="false"
                    :on-success="handleEditAvatarSuccess"
                >
                    <img v-if="editCoach.photo" :src="editCoach.photo" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
                </el-upload>
            </el-form-item>
            <el-form-item label="名字" :label-width="60">
                <el-input v-model="editCoach.name" placeholder="请输入教练名字"/>
            </el-form-item>
            <el-form-item label="电话" :label-width="60">
                <el-input v-model="editCoach.phone" placeholder="请输入教练电话"/>
            </el-form-item>
            <el-form-item label="微信" :label-width="60">
                <el-input v-model="editCoach.wechat" placeholder="请输入教练微信"/>
            </el-form-item>
            <el-form-item label="简介" :label-width="60">
                <el-input
                    :rows = "5"
                    maxlength="255"
                    placeholder="请输入教练简介"
                    show-word-limit
                    type="textarea"
                    v-model="editCoach.recomm"
                    autocomplete="off" />
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

<style >

</style>