<script setup>

import { onMounted } from 'vue';
import echarts from '@/echarts'
import dateUtil from '@/utils/dateUtil';
import chartsApi from '@/api/chartsApi'

let charts = null;

// 加载完成后再进行
onMounted(()=>{
    // 先初始化
    initEcharts();
    // 获取数据
    getData();
});

function initEcharts(){
    charts = echarts.init(document.getElementById('courseReserve'));    
    charts.setOption(option);
    getData();
}

function getData(){
    const date = dateUtil.getMonday();
    const days = dateUtil.getWeekDays();

    chartsApi.weekCoach(date).then(result => {
        const data = new Array(7).fill(0);

        for (let i=0; i<days.length; i++){
            for (let item of result.data){
                if (item.day == days[i]) {
                    data[i] = item.count;
                }
            }
        }

        option.series[0].data = data;
        charts.setOption(option);
    });
    
    chartsApi.weekCourse(date).then(result => {
        const data = new Array(7).fill(0);

        for (let i=0; i<days.length; i++){
            for (let item of result.data){
                if (item.day == days[i]) {
                    data[i] = item.count;
                }
            }
        }

        option.series[1].data = data;
        charts.setOption(option);
    });
    
    chartsApi.weekReserve(date).then(result => {
        const data = new Array(7).fill(0);

        for (let i=0; i<days.length; i++){
            for (let item of result.data){
                if (item.day == days[i]) {
                    data[i] = item.count;
                }
            }
        }

        option.series[2].data = data;
        charts.setOption(option);
    });
}


// 绘制图表
// 绘制图表
let option = {
    title: {
        text: '课程预约人数对比图',
        textStyle: {
            color: '#161D23',
            fontWeight: 'bold',
            fontSize: '16px',
        },
        left: '10px',
        top: '10px',
    },
    legend: {
        top: '20px',
        right: '20px',
    },
    grid: {
        left: '3%',
        right: '30px',
        bottom: '3%',
        top: '60px',
        containLabel: true
    },
    tooltip: {},
    yAxis: {
        type: 'value',
    },
    xAxis: {
        axisLabel: {color: '#161D23'},
        type: 'category',
        data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],    // 显示标题
    },
    series: [
        {
            name: '带课教练',
            type: 'bar',
            data: [],   // 显示数据
            label: {
                show: true,
                color: '#50CEFF',
                position: 'top',
            },
            backgroundStyle: {
                color: 'rgba(180, 180, 180, 0.2)'
            }
        },
        {
            name: '课程数量',
            type: 'bar',
            data: [],   // 显示数据
            label: {
                show: true,
                color: '#50CEFF',
                position: 'top',
            },
            backgroundStyle: {
                color: 'rgba(180, 180, 180, 0.2)'
            }
        },
        {
            name: '预约人数',
            type: 'bar',
            data: [],   // 显示数据
            label: {
                show: true,
                color: '#50CEFF',
                position: 'top',
            },
            backgroundStyle: {
                color: 'rgba(180, 180, 180, 0.2)'
            }
        }
    ]
}


</script>

<template>

<div id="courseReserve" style="width: 100%; height: 100%">
</div>

</template>

<style>

</style>