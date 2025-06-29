<script setup>
import { ref } from 'vue';
import chartsApi from '@/api/chartsApi'


const memberCount = ref(0);
const coachCount = ref(0);
const courseCount = ref(0);
const reserveCount = ref(0);

loadData();

function loadData(){
    
    chartsApi.memberTotal().then(result=>{
    
        memberCount.value = result.data.count;
    });
    chartsApi.coachTotal().then(result=>{
        coachCount.value = result.data.count;
    });
    chartsApi.courseTotal().then(result=>{
        courseCount.value = result.data.count;
    });
    chartsApi.reserveTotal().then(result=>{
        reserveCount.value = result.data.count;
    });
}

</script>

<template>
    <el-row>
        <el-col :span="6">
            <div class="title">会员数量</div>
            <div class="count mem">{{ memberCount }}</div>
        </el-col>
        <el-col :span="6">
            <div class="title">教练数量</div>
            <div class="count coa">{{ coachCount }}</div>
        </el-col>
        <el-col :span="6">
            <div class="title">课程数量</div>
            <div class="count cou">{{ courseCount }}</div>
        </el-col>
        <el-col :span="6">
            <div class="title">预约数量</div>
            <div class="count res">{{ reserveCount }}</div>
        </el-col>
    </el-row>
</template>

<style scoped>
.title {
    text-align: center;
    height: 40px;
    line-height: 40px;
}
.count{
    margin: auto;
    width: 100px;
    height: 100px;
    border: 15px solid #49BCF7;
    border-radius: 50px;
    text-align: center;
    line-height: 70px;
    font-size: 30px;
}
.mem{
    border-color: #49BCF7;
    color: #49BCF7;
}
.coa{
    border-color: #91CC75;
    color: #91CC75;
}
.cou{
    border-color: #FAC858;
    color: #FAC858;
}
.res{
    border-color: #5470C6;
    color: #5470C6;
}
</style>