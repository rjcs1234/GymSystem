<script setup>

// 引入echarts的所有组件

import WeekCoach from './charts/WeekCoach.vue'
import WeekReserve from './charts/WeekReserve.vue'
import WeekCourse from './charts/WeekCourse.vue'
import WeekMember from './charts/WeekMember.vue'
import Total from './charts/Total.vue'
import CourseReserve from './charts/CourseReserve.vue'
import ReserveCount from './charts/ReserveCount.vue'
import HotCoach from './charts/HotCoach.vue'
import HotCourse from './charts/HotCourse.vue'

</script>

<template>
    <div class="container">

        <el-row >
            <el-col :span="6">
                <el-row>
                    <el-col :span="24">
                        <div style="width: 100%; height: 325px">
                            <HotCourse></HotCourse>
                        </div>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <div style="width: 100%; height: 200px">
                            <WeekMember></WeekMember>
                        </div>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <div style="width: 100%; height: 225px">
                            <HotCoach></HotCoach>
                        </div>
                    </el-col>
                </el-row>
            </el-col>
            
            <el-col :span="12">
                <el-row>
                    <el-col :span="24">
                        <div style="width: 100%; height: 150px">
                            <Total></Total>
                        </div>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <div style="width: 100%; height: 400px">
                            <CourseReserve></CourseReserve>
                        </div>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <div style="width: 100%; height: 200px">
                            <ReserveCount></ReserveCount>
                        </div>
                    </el-col>
                </el-row>
            </el-col>

            <el-col :span="6">
                <el-row>
                    <el-col :span="24">
                        <div style="width: 100%; height: 250px">
                            <WeekCourse></WeekCourse>
                        </div>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <div style="width: 100%; height: 250px">
                            <WeekReserve></WeekReserve>
                        </div>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <div style="width: 100%; height: 250px">
                            <WeekCoach></WeekCoach>
                        </div>
                    </el-col>
                </el-row>
            </el-col>

        </el-row>
    </div>
</template>

<style scoped>
.container{
    /* background-image: radial-gradient(50% 50% at 50% 70%, #545C64, #1A232A); */
    /* border: 1px solid #01093D; */
    min-height: 100%;
    background-color: #F5F5F5;

}
.container>.el-row{
    /* background-color: #FFF; */
    padding: 15px;
}
.title{
    text-align: center;
    color: #1A232A;
    /* font-weight: bold; */
    font-size: 36px;
    /* background-color: #091440; */
}
.el-col .el-col{
    background-color: #FFF;
    /* border: 1px solid #58A6FE; */
    border-radius: 5px;
}
.el-row .el-row {
    margin-bottom: 15px;
    margin-right: 15px;
}

</style>