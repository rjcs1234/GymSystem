<script setup>
import { ref, reactive, watch } from 'vue'
import { ElMessage, ElMessageBox } from "element-plus"
import reserveApi from "../api/reserveApi"

// 日期格式化函数
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${year}-${month}-${day}`
}

//分页展示数据信息
const pageInfo = reactive({
    currentPage: 1,
    pageSize: 10,
    total: 0
})
//发送请求 查询某个课程的预约信息(课程id)
const course = defineModel() //点击行的课程信息
const addReserve = ref({ courseId: course.id })
watch(course, async (newCourse, oldCourse) => {
    console.log("监听值的变换");
    addReserve.value.courseId = newCourse.id;
    loadReserves();
})

//表格展示数据的对象
const reserves = ref([])

function loadReserves() {
    //提供课程id
    pageInfo.courseId = course.value.id;
    reserveApi.search(pageInfo).then((resp) => {
        // 格式化数据中的日期字段
        reserves.value = resp.data.records.map(item => ({
            ...item,
            reserveTime: formatDate(item.reserveTime),
            commentTime: formatDate(item.commentTime)
        }))
        pageInfo.total = resp.data.total
    });
}
//获取该课程的预约信息
loadReserves();
</script>

<template>
    <div class="content">
        <el-row justify="space-between">
            <el-col :span="12">
                <el-form :model="pageInfo" :inline="true" >          
                    <el-form-item label="会员" :label-width="60">
                        <el-input v-model="pageInfo.mid" clearable placeholder="会员编号"/>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSearch"><el-icon><Search></Search></el-icon></el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
        
        <el-table :data="reserves" :stripe="true" :border="true" style="width: 100%"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="40" >
            </el-table-column>
            <el-table-column prop="id" label="ID" width="60" />
            <el-table-column prop="member.name" label="会员"  width="100"/>
            <el-table-column prop="reserveTime" label="预约时间" width="180" />
            <el-table-column prop="score" label="评分"  width="60"/>
            <el-table-column prop="comment" label="评语" />
            <el-table-column prop="commentTime" label="评价时间" width="180" />
            <el-table-column prop="status" label="状态" width="80" :formatter="fmtStatus"/>
            <el-table-column label="操作" width="220">
                <template #default="scope">
                    <el-button size="small" type="success" v-if="scope.row.status==1 || scope.row.status==3"
                        @click="handleCancel(scope.row, 2)">签到</el-button> 
                    <el-button size="small" type="warning" v-if="scope.row.status==1"
                        @click="handleCancel(scope.row, 0)">取消预约</el-button> 
                </template>
            </el-table-column>
        </el-table>
        <el-pagination class="page" v-model:current-page="pageInfo.currentPage" v-model:page-size="pageInfo.pageSize"
            :total="pageInfo.total" :page-sizes="[10, 20, 50, 100]" :background="true"
            layout="total, sizes, prev, pager, next, jumper" @change="loadReserves" />
    </div> 
</template>

<style>

</style>