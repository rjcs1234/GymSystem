<script setup>
import {ref} from 'vue'
import courseApi from "../api/courseApi.js"
const courses = ref([])

function loadCourses(){
courseApi.showAll().then((res)=>{
    courses.value = res.data;
    });

}
loadCourses();

</script>


<template>
    <el-calendar>
        <template #date-cell="{ data }">
        <p class="day">
            {{ data.day.split('-').slice(2).join('-') }}
        </p>
        <div v-for="course in courses">
            <p v-if="course.time.indexOf(data.day) >= 0" class="courses">
                (<span class="reserveCount">{{ course.reserveCount }}</span>/<span class="reserveCount">{{ course.maxCount }}</span>)
                <span>{{ course.time.substring(11, 16) }}</span>-
                <span>{{ course.name }}</span>
                [<span>{{ course.coach.name }}</span>]
            </p>
        </div>
        </template>
    </el-calendar>
</template>


<style>
.el-calendar-table .el-calendar-day{
    height: auto;
    min-height: var(--el-calendar-cell-width);
}
.day{
    text-align: right;
    font-weight: bold; 
    font-size: 24px;
    background-color: #F1F1F1;
}
.courses{
    color: #1A232A;
    background-color: #FAC858; 
    font-size:14px; 
    margin-bottom: 2px;
}
.reserveCount{
    color: #12B7F5;
    font-weight: bold;
}
.current{
    color: #12B7F5;
}
</style>