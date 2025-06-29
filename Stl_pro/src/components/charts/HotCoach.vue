<script setup>

import { onMounted, ref } from 'vue';
import echarts from '@/echarts'
import dateUtil from '@/utils/dateUtil';
import chartsApi from '@/api/chartsApi'


let charts = null;
const scores = ref([]);

// 加载完成后再进行
onMounted(()=>{
    // 先初始化
    initEcharts();
    // 获取数据
    getData();
});

function initEcharts(){
    charts = echarts.init(document.getElementById('hotCoach'));    
    charts.setOption(option);
    getData();
}

function getData(){
    const date = dateUtil.getMonthAgo();

    chartsApi.hotCoach(date).then(result => {
        scores.value = result.data;
        const label = [];
        const data = [];

        for (let score of scores.value){
            score.count = score.count.toFixed(1);
        }

        for (let item of result.data){
            label.unshift(item.name.length<10?item.name:item.name.substring(0,10));
            data.unshift(item.count);

            
        }
        option.yAxis.data = label;
        option.series[0].data = data;
        charts.setOption(option);
    });
}


// 绘制图表
let option = {
    title: {
        text: '口碑教练 TOP5',
        textStyle: {
            color: '#161D23',
            fontWeight: 'bold',
            fontSize: '16px',
        },
        left: '10px',
        top: '10px',
    },
    grid: {
        left: '30px',
        right: '50px',
        bottom: '3%',
        top: '40px',
        containLabel: true
    },
    tooltip: {},
    xAxis: {
        type: 'value',
        show: false,
        axisLabel: {color: '#FFF'},
    },
    yAxis: {
        axisLine: {show: false},
        axisTick: {show: false},
        axisLabel: {color: '#161D23'},
        type: 'category',
        data: [],    // 显示标题
    },
    series: [
        {
            name: '用户数量',
            type: 'bar',
            data: [],   // 显示数据
            itemStyle: {
                color: '#FE6F6F',
            },
            barWidth: '50%',
            label: {
                show: true,
                color: '#FE6F6F',
                position: 'right',
            },
            backgroundStyle: {
                color: 'rgba(180, 180, 180, 0.2)'
            }
        }
    ]
}
</script>

<template>
    
    <div id="hotCoach" style="width: 100%; height: 100%; display: none;"></div>
    
    <el-row>
        <el-col :span="6">
            <div class="title">
                口碑教练榜<br>本周
            </div>
        </el-col>
        <el-col :span="18">
            <ul v-for="score in scores" class="scores">
                <li>
                    <el-rate
                        v-model="score.count"
                        disabled
                        show-score
                        text-color="#ff9900"
                        score-template="{value}分"
                    />
                </li>
                <li>{{ score.name }}</li>
            </ul>
        </el-col>
    </el-row>
    


</template>

<style scoped>
.scores{
    list-style: none;
    margin: 10px 0px;
    padding: 0px;
}
.scores::after{
    content: '';
    display: block;
    clear: both;
}
.scores li{
    float: left;
    line-height: 32px;
    padding-left: 20px;
}

.title{
    background-color: #FFDC60;
    width: 100%;
    height: 225px;
    display: flex;
    align-items: center; /* 垂直居中 */
    justify-content: center; /* 水平居中 */
    writing-mode: vertical-rl; /* 文字竖排 */
    text-align: center; /* 文字水平居中 */
    font-size: 32px;
    color: #FFF;
    font-family: 'LiSu';
}

</style>