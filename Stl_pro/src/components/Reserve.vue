<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from "element-plus"
import reserveApi from "../api/reserveApi"
import memberApi from "../api/memberApi"
import courseApi from "../api/courseApi"

// 日期格式化函数 - 统一使用YYYY-MM-DD格式
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  
  let date = new Date(dateStr)
  
  // 处理可能的ISO格式日期（带Z或+00:00后缀）
  if (dateStr.endsWith('Z')) {
    date = new Date(dateStr.replace('Z', '+00:00'))
  } else if (dateStr.includes('+')) {
    const [datePart, timezone] = dateStr.split('+')
    date = new Date(`${datePart}+${timezone.split(':')[0]}:${timezone.split(':')[1]}`)
  }
  
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  
  return `${year}-${month}-${day}`
}

//json格式数据
const pageInfo = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

//回填表格数据
const reserves = ref([])
function loadReserves() {
  reserveApi.search(pageInfo).then((resp) => {
    // 格式化表格中的日期数据
    const formattedReserves = resp.data.records.map(reserve => {
      return {
        ...reserve,
        reserveTime: formatDate(reserve.reserveTime),
        commentTime: formatDate(reserve.commentTime),
        // 新增：格式化课程关联的时间
        course: {
          ...reserve.course,
          time: formatDate(reserve.course.time)
        }
      }
    })
    
    reserves.value = formattedReserves
    pageInfo.total = resp.data.total
  })
}
loadReserves()

//填充下拉列表
const members = ref([])
const courses = ref([])
function showMemebers() {
  memberApi.showAll().then((resp) => {
    members.value = resp.data
  })
}
function showCourses() {
  courseApi.showAll().then((resp) => {
    // 格式化课程中的日期数据
    const formattedCourses = resp.data.map(course => {
      return {
        ...course,
        time: formatDate(course.time)
      }
    })
    
    courses.value = formattedCourses
  })
}
showMemebers()
showCourses()

//添加预约信息
const addVisible = ref(false)
const addReserve = ref({})

function showAdd() {
  addVisible.value = true
}
function handleAdd() {
  reserveApi.add(addReserve.value).then((resp) => {
    if (resp.code === 0) {
      ElMessage({
        message: "添加成功",
        type: 'success'
      })
      addVisible.value = false
      addReserve.value = {}
    } else {
      ElMessage({
        message: "添加失败",
        type: 'error'
      })
    }
    loadReserves()
  })
}

//取消预约
const editReserve = ref({})
function handleCancel(row) {
  ElMessageBox.confirm('确定取消预约么?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    //确定按钮执行
    editReserve.value = JSON.parse(JSON.stringify(row))
    editReserve.value.status = 0
    reserveApi.edit(editReserve.value).then((resp) => {
      if (resp.code === 0) {
        ElMessage({
          message: "取消成功",
          type: 'success'
        })
      } else {
        ElMessage({
          message: "取消失败",
          type: 'error'
        })
      }
      loadReserves()
    })
  })
}

// 编辑预约
const editVisible = ref(false)
function showEdit(row) {
  editReserve.value = JSON.parse(JSON.stringify(row))
  editVisible.value = true
}
function handleEdit() {
  reserveApi.edit(editReserve.value).then((resp) => {
    if (resp.code === 0) {
      ElMessage({
        message: "修改成功",
        type: 'success'
      })
      editVisible.value = false
    } else {
      ElMessage({
        message: "修改失败",
        type: 'error'
      })
    }
    loadReserves()
  })
}

// 表格状态格式化函数
function fmtStatus(row, column) {
  const statusMap = {
    0: '已取消',
    1: '进行中',
    2: '已完成'
  }
  return statusMap[row.status] || row.status
}
</script>

<template>
  <div class="content">
    <el-form :model="pageInfo" :inline="true">
      <el-form-item label="会员" :label-width="60">
        <el-select
          v-model="pageInfo.memberId"
          filterable
          placeholder="请选择会员"
          style="width: 240px"
          clearable
        >
          <el-option
            v-for="item in members"
            :key="item.id"
            :label="item.name + '-' + item.phone"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课程" :label-width="60">
        <el-select
          v-model="pageInfo.courseId"
          filterable
          placeholder="请选择课程"
          style="width: 240px"
          clearable
        >
          <el-option
            v-for="item in courses"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="loadReserves"><el-icon><Search></Search></el-icon></el-button>
      </el-form-item>
    </el-form>
    <div class="bar">
      <el-button type="success" @click="showAdd">添加预约</el-button>
    </div>
    <el-table :data="reserves" :stripe="true" :border="true" style="width: 100%">
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="member.name" label="会员" width="100" />
      <el-table-column prop="course.name" label="课程" width="180" />
      <el-table-column prop="course.coach.name" label="教练" width="250" />
      <el-table-column prop="course.time" label="课程时间" width="200" />
      <el-table-column prop="course.length" label="时长" width="80" />
      <el-table-column prop="reserveTime" label="预约时间" width="200" />
      <el-table-column prop="score" label="评分" width="60" />
      <el-table-column prop="comment" label="评语" width="200" />
      <el-table-column prop="commentTime" label="评价时间" width="120" />
      <el-table-column prop="status" label="状态" width="80" :formatter="fmtStatus" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" type="warning" v-if="scope.row.status === 1" @click="handleCancel(scope.row)">取消预约</el-button>
          <el-button size="small" type="success" @click="showEdit(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="page" v-model:current-page="pageInfo.currentPage" v-model:page-size="pageInfo.pageSize"
      :total="pageInfo.total" :page-sizes="[10, 20, 50, 100]" :background="true"
      layout="total, sizes, prev, pager, next, jumper" @change="loadReserves" />
  </div>

  <!-- 添加的弹窗 -->
  <el-dialog v-model="addVisible" title="创建新预约" width="500">
    <el-form :model="addReserve">
      <el-form-item label="会员" :label-width="60">
        <el-select
          v-model="addReserve.memberId"
          filterable
          placeholder="请选择会员"
          style="width: 240px"
        >
          <el-option
            v-for="item in members"
            :key="item.id"
            :label="item.name + '-' + item.phone"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课程" :label-width="60">
        <el-select
          v-model="addReserve.courseId"
          filterable
          placeholder="请选择课程"
          style="width: 240px"
        >
          <el-option
            v-for="item in courses"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="addVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAdd">立即预约</el-button>
      </div>
    </template>
  </el-dialog>

  <!-- 编辑的弹窗 -->
  <el-dialog v-model="editVisible" title="编辑预约" width="500">
    <el-form :model="editReserve">
      <el-form-item label="会员" :label-width="60">
        <el-select
          v-model="editReserve.memberId"
          filterable
          placeholder="请选择会员"
          style="width: 240px"
        >
          <el-option
            v-for="item in members"
            :key="item.id"
            :label="item.name + '-' + item.phone"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="课程" :label-width="60">
        <el-select
          v-model="editReserve.courseId"
          filterable
          placeholder="请选择课程"
          style="width: 240px"
        >
          <el-option
            v-for="item in courses"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="editVisible = false">取消</el-button>
        <el-button type="primary" @click="handleEdit">修改预约</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<style>
</style>