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
    charts = echarts.init(document.getElementById('hotCourse'));    
    charts.setOption(option);
    getData();
}

function getData(){
    const date = dateUtil.getMonthAgo();

    chartsApi.hotCourse(date).then(result => {

        const data = [];

        for (let item of result.data){
            data.unshift({ name: item.name.length<10?item.name:item.name.substring(0,10), value: item.count});
        }
        option.series[0].data = data;
        charts.setOption(option);
    });
}


// 绘制图表
let option = {
    title: {
        text: '人气课程 Top10',
        textStyle: {
            color: '#161D23',
            fontWeight: 'bold',
            fontSize: '16px',
        },
        left: '10px',
        top: '10px',
    },
    tooltip: {
        trigger: 'item'
    },
    series: [
        {
            name: '人气',
            type: 'pie',
            radius: ['25%', '65%'],
            center: ['50%', '60%'],
            avoidLabelOverlap: false,
            itemStyle: {
                borderRadius: 10,
                borderColor: '#fff',
                borderWidth: 2
            }, 
            emphasis: {
                label: {
                show: true,
                fontSize: 40,
                fontWeight: 'bold'
                }
            },
            data: [],
        }
    ]
}
</script>

<template>

<div id="hotCourse" style="width: 100%; height: 100%">
</div>

</template>

<style>

</style>