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
    charts = echarts.init(document.getElementById('reserveCount'));    
    charts.setOption(option);
    getData();
}

function getData(){
    const date = dateUtil.getMonthFistDay();
    const days = dateUtil.getMonthDays();

    chartsApi.reserveCount(date).then(result => {
        const data = new Array(days.length).fill(0);

        for (let i=0; i<days.length; i++){
            for (let item of result.data){
                if (item.day == days[i]) {
                    data[i] = item.count;
                }
            }

            days[i] = days[i].substring(5);
        }

        option.xAxis.data = days;
        option.series[0].data = data;
        charts.setOption(option);
    });
}


// 绘制图表
let option = {
    title: {
        text: '预约趋势',
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
        axisLabel: {color: '#FFF'},
    },
    xAxis: {
        axisLabel: {color: '#161D23'},
        type: 'category',
        data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],    // 显示标题
    },
    series: [
        {
            name: '预约数量',
            type: 'line',
            smooth: true,
            data: [],   // 显示数据
            itemStyle: {
                color: '#73C0DE',
            },
            barWidth: '50%',
            label: {
                show: true,
                color: '#73C0DE',
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

<div id="reserveCount" style="width: 100%; height: 100%">
</div>

</template>

<style>

</style>