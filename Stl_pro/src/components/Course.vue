<script setup>
import courseApi from '@/api/courseApi';
import { reactive, ref } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus'
import service from '@/api';
import coachApi from '@/api/coachApi';
import reserveApi  from '../api/reserveApi';

 import ReserveSign from './ReserveSign.vue' 

//JavaScript 原生 Date 对象的内置方法。将日期转换为字符串
const formatDate = (row, column) => {
  if (!row.time) return '';
  
  const date = new Date(row.time);
  return `${date.getFullYear()}-${
    (date.getMonth() + 1).toString().padStart(2, '0')
  }-${
    date.getDate().toString().padStart(2, '0')
  }`;
}


const courses = ref([]);
const coachs = ref([]);

// 分页信息
const pageInfo = reactive({
    currentPage:1,
    pageSize: 10,
    total: 0
})

// 获取课程列表
loadCourses();
loadCoachs();

// 页码和页面大小更新
function loadCourses() {
    courseApi.search(pageInfo).then(result => {
        courses.value = result.data.records;
        pageInfo.total = result.data.total;
    });
}

// 页码和页面大小更新
function loadCoachs() {
    coachApi.search().then(result => {
        coachs.value = result.data.records;
    });
}

// 添加的相关操作
const addVisible = ref(false);
const addCourse = ref({
    maxCount: 20,
    length: 90,
});
function showAdd() {
    addVisible.value = true;    // 显示添加窗口
}

function handleAdd(){
    courseApi.add(addCourse.value).then(result=>{
        if (result.code == 0) {
            ElMessage({ message: '课程创建成功！', type: 'success' })
            addVisible.value = false;
            addCourse.value = {};
        } else {
            ElMessage.error(result.msg);
        }
        // 刷新表格数据
        loadCourses();
    });
}


// 编辑的操作
const editVisible = ref(false);
const editCourse = ref({});

function showEdit(row){
    editVisible.value = true;
    //editCourse.value = Object.assign({}, row);   // 如果你有一个对象，再创建一个一样的对象？？？克隆
    //editCourse.value = {...row}; // 作用同上
    editCourse.value = JSON.parse(JSON.stringify(row));   // 深拷贝
    console.log(row);
}


function handleEdit(){
    courseApi.edit(editCourse.value).then(result=>{
        if (result.code == 0) {
            ElMessage({ message: '编辑课程成功！', type: 'success' })
            editVisible.value = false;
            editCourse.value = {};
        } else {
            ElMessage.error(result.msg);
        }
        // 刷新表格数据
        loadCourses();
    });
}

function handleRemove(course){
    ElMessageBox.confirm(
        '亲，确定要移除当前课程么?',
        '警告',
        {
            confirmButtonText: '确定',
            cancelButtonText: '不了',
            type: 'warning',
        }
    ).then(()=>{
        // 确定
        courseApi.remove(course.id).then(result=>{
            if (result.code == 0) {
                ElMessage({ message: '移除课程成功！', type: 'success' })
            } else {
                ElMessage.error(result.msg);
            }
            // 刷新表格数据
            loadCourses();
        });
    });
}

function handleAddAvatarSuccess(response, uploadFile){
     addCourse.value.photo = response.msg; 
}
function handleEditAvatarSuccess(response, uploadFile){
     editCourse.value.photo = response.msg; 
}


function getUploadURL(){
    /* return service.defaults.baseURL + '/upload'; */
}

// 解析类型显示
function fmtCoach(row, column, cellValue, index) {
    return row.coach.name;
}

function onSearch(){
    pageInfo.page = 1;
    loadCourses();
}

// 预约相关
const reserveVisible = ref(false);
function showReserve(row){
     reserveVisible.value = true;
    editCourse.value = row; 
}

</script>

<template>
    <div class="content">
        
        <el-form :model="pageInfo" :inline="true">
            <el-form-item label="课程名称" :label-width="80">
                <el-input v-model="pageInfo.name" clearable placeholder="请输入课程名字"/>
            </el-form-item>
            <el-form-item label="授课教练" :label-width="80">
                <el-select
                    v-model="pageInfo.coachId"
                    value-key="id"
                    placeholder="请选择课程教练"
                    style="width: 240px"
                    clearable
                    >
                    <el-option
                        v-for="item in coachs"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                    />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSearch"><el-icon><Search></Search></el-icon></el-button>
            </el-form-item>
        </el-form>

        <div class="bar">
            <el-button type="success" @click="showAdd" >创建课程</el-button>
        </div>
        <el-table :data="courses" :stripe="true" :border="true" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="photo" label="照片" >
                <template #default="scope">
                    <img :src="scope.row.photo" style="max-height: 60px; max-width: 120px;"/>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="课程名" />
            <el-table-column prop="coach" label="教练" :formatter="fmtCoach"/>
            <el-table-column prop="room" label="教室" />
            <el-table-column prop="time" label="时间" width="180" :formatter="formatDate" />
            <el-table-column prop="length" label="时长" width="100"/>
            <el-table-column prop="reserveCount" label="预约人数" width="100"/>
            <el-table-column prop="maxCount" label="最大人数" width="100"/>
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button size="small" type="primary"
                        @click="showReserve(scope.row)">学员</el-button> 
                    <el-button size="small" type="success"
                        @click="showEdit(scope.row)">编辑</el-button> 
                    <el-button size="small" type="danger"
                        @click="handleRemove(scope.row)">删除</el-button> 
                </template>
            </el-table-column>
        </el-table>
        <el-pagination class="page" v-model:current-page="pageInfo.currentPage" v-model:page-size="pageInfo.pageSize"
            :total="pageInfo.total" :page-sizes="[10, 20, 50, 100]" :background="true"
            layout="total, sizes, prev, pager, next, jumper" @change="loadCourses" />
    </div>

    <!-- 添加的弹窗 -->
    <el-dialog v-model="addVisible" title="创建新课程" width="600">
        <el-form :model="addCourse">
            <el-form-item label="照片" :label-width="80">
                <el-upload
                    class="avatar-uploader"
                    action="/api/file/upload"
                    :show-file-list="false"
                    :on-success="handleAddAvatarSuccess"
                >
                    <img v-if="addCourse.photo" :src="addCourse.photo" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
                </el-upload>
            </el-form-item>
            <el-form-item label="课程名称" :label-width="80">
                <el-input v-model="addCourse.name" placeholder="请输入课程名字"/>
            </el-form-item>
            <el-form-item label="授课教练" :label-width="80">
                <el-select
                    v-model="addCourse.coachId"
                    value-key="id"
                    placeholder="请选择课程教练"
                    style="width: 240px"
                    >
                    <el-option
                        v-for="item in coachs"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="上课教室" :label-width="80">
                <el-col :span="10">
                    <el-input v-model="addCourse.room" placeholder="请输入上课教室"/>
                </el-col>
                <el-col :span="4" style="text-align: center;">
                    <span class="text-gray-500">最大人数</span>
                </el-col>
                <el-col :span="8">
                    <el-input-number v-model="addCourse.maxCount" :min="10" :max="120" :step="10" placeholder="最大人数"/>
                </el-col>
            </el-form-item>
            <el-form-item label="上课时间" :label-width="80">
                <el-col :span="10">
                    <el-date-picker
                        v-model="addCourse.time"
                        editable
                        type="datetime"
                        placeholder="上课时间"
                        format="YYYY-MM-DD HH:mm:ss"
                        value-format="YYYY-MM-DD HH:mm:ss"
                        style="width:100%;"
                    /> 
                </el-col>
                <el-col :span="4" style="text-align: center;">
                    <span class="text-gray-500">课程时长</span>
                </el-col>
                <el-col :span="8">
                    <el-input-number v-model="addCourse.length" :min="10" :max="120" :step="10" placeholder="课程时长"/>
                </el-col>
                <el-col :span="2" style="text-align: center;">
                    <span class="text-gray-500">分钟</span>
                </el-col>
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
    <el-dialog v-model="editVisible" title="编辑课程" width="600">
        <el-form :model="editCourse">
            <el-form-item label="照片" :label-width="80">
                <el-upload
                    class="avatar-uploader"
                    :action="getUploadURL()"
                    :show-file-list="false"
                    :on-success="handleEditAvatarSuccess"
                >
                    <img v-if="editCourse.photo" :src="editCourse.photo" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
                </el-upload>
            </el-form-item>
            <el-form-item label="课程名称" :label-width="80">
                <el-input v-model="editCourse.name" placeholder="请输入课程名字"/>
            </el-form-item>
            <el-form-item label="授课教练" :label-width="80">
                <el-select
                    v-model="editCourse.coachId"
                    value-key="id"
                    placeholder="请选择课程教练"
                    style="width: 240px"
                    >
                    <el-option
                        v-for="item in coachs"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="上课教室" :label-width="80">
                <el-col :span="10">
                    <el-input v-model="editCourse.room" placeholder="请输入上课教室"/>
                </el-col>
                <el-col :span="4" style="text-align: center;">
                    <span class="text-gray-500">最大人数</span>
                </el-col>
                <el-col :span="8">
                    <el-input-number v-model="editCourse.maxCount" :min="10" :max="120" :step="10" placeholder="最大人数"/>
                </el-col>
            </el-form-item>
            <el-form-item label="上课时间" :label-width="80">
                <el-col :span="10">
                    <el-date-picker
                        v-model="editCourse.time"
                        editable
                        type="datetime"
                        placeholder="上课时间"
                        format="YYYY-MM-DD HH:mm:ss"
                        value-format="YYYY-MM-DD HH:mm:ss"
                        style="width:100%;"
                    /> 
                </el-col>
                <el-col :span="4" style="text-align: center;">
                    <span class="text-gray-500">课程时长</span>
                </el-col>
                <el-col :span="8">
                    <el-input-number v-model="editCourse.length" :min="10" :max="120" :step="10" placeholder="课程时长"/>
                </el-col>
                <el-col :span="2" style="text-align: center;">
                    <span class="text-gray-500">分钟</span>
                </el-col>
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
    <el-dialog v-model="reserveVisible" title="预约学员" width="60%">
        <ReserveSign v-model="editCourse"></ReserveSign>
    </el-dialog>
</template>

<style >

</style>