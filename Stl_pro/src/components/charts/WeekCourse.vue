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
    charts = echarts.init(document.getElementById('weekCourse'));    
    charts.setOption(option);
    getData();
}

function getData(){
    const date = dateUtil.getMonday();
    const days = dateUtil.getWeekDays();

    chartsApi.weekCourse(date).then(result => {
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
}


// 绘制图表
let option = {
    title: {
        text: '本周课程',
        textStyle: {
            color: '#161D23',
            fontWeight: 'bold',
            fontSize: '16px',
        },
        left: '10px',
        top: '10px',
    },
    grid: {
        left: '3%',
        right: '30px',
        bottom: '3%',
        top: '40px',
        containLabel: true
    },
    tooltip: {},
    yAxis: {
        type: 'value',
        show: false,
        axisLabel: {color: '#FFF'},
    },
    xAxis: {
        axisLine: {show: false},
        axisTick: {show: false},
        axisLabel: {color: '#161D23'},
        type: 'category',
        data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],    // 显示标题
    },
    series: [
        {
            name: '用户数量',
            type: 'bar',
            data: [],   // 显示数据
            itemStyle: {
                color: '#91CC75',
            },
            barWidth: '50%',
            label: {
                show: true,
                color: '#91CC75',
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

<div id="weekCourse" style="width: 100%; height: 100%">
</div>

</template>

<style>

</style>