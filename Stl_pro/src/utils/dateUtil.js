
const dateUtil = {
    // 本周第一天
    getMondayStr() {
        return this.formatDate(this.getMonday());
    },
    // 本周第一天
    getMonday() {
        const currentDate = new Date();
        const day = currentDate.getDay(); // 获取今天是星期几（0-6，0表示周日）
        const diff = currentDate.getDate() - day + (day === 0 ? -6 : 1); // 计算本周一的日期
        const monday = new Date(currentDate.setDate(diff));
        return monday;
    },
    // 本周的每一天
    getWeekDays(){
        const monday = this.getMonday();
        const days = [];
        for (let i=0; i<7; i++){
            const mon = new Date(monday);
            const diff = mon.getDate() + i;
            const day = new Date(mon.setDate(diff));
            days.push(this.formatDate(day));
        }
        return days;
    },
    // 本月第一天
    getMonthFistDayStr(){
        return this.formatDate(this.getMonthFistDay());
    }, 
    // 本月第一天
    getMonthFistDay(){
        const currentDate = new Date();
        const firstDay = new Date(currentDate.setDate(1));//日
        return firstDay;
    },
    getDaysInMonth(date) {
        // JavaScript中的月份是从0开始的，所以需要减1
        // 3、4、6、7、9、10、12月份有31天
        // 1、3、5、7、8、10、12月份有30天
        const year = date.getFullYear();
        const month = date.getMonth();
        // 需要考虑闰年
        if (month === 1) {
            return ((year % 4 === 0 && year % 100 !== 0) || year % 400 === 0) ? 29 : 28;
        } else {
            return [31, (((year % 4 === 0 && year % 100 !== 0) || year % 400 === 0) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month];
        }
    },
    // 本月的每一天
    getMonthDays(){
        const firstDay = this.getMonthFistDay();
        const days = [];
        for (let i=0; i<this.getDaysInMonth(firstDay); i++){
            const first = new Date(firstDay);
            const diff = first.getDate() + i;
            const day = new Date(first.setDate(diff));
            days.push(this.formatDate(day));
        }
        return days;
    },   
    // 一月前
    getMonthAgoStr(){
        return this.formatDate(this.getMonthAgo());
    },
    // 一月前
    getMonthAgo(){
        const currentDate = new Date();
        const diff = currentDate.getDate() - 30;
        const firstDay = new Date(currentDate.setDate(diff));//日
        return firstDay;
    },
    formatDate(date){
        const year = date.getFullYear();//获取当前年
        const month = String(date.getMonth() + 1).padStart(2, "0");//获取当前月
        const day = String(date.getDate()).padStart(2, "0");//获取当前日
        return `${year}-${month}-${day}`;
    },


}

export default dateUtil;


