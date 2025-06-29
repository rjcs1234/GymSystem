import service from ".";


const chartsApi={
    hotCourse(date){
         return service.get('/charts/hotCourse').then(response=>response.data)
    },
    weekMember(date){
        return service.get('/charts/weekMember',{params:{date}}).then(response=>response.data)
    },
    hotCoach(date){
        return service.get('/charts/hotCoach',{params:{date}}).then(response=>response.data)
    },
    memberTotal(){
        return service.get('/charts/memberTotal').then(response=>response.data)
    },
    coachTotal(){
        return service.get('/charts/coachTotal').then(response=>response.data)
    },
    courseTotal(){
        return service.get('/charts/courseTotal').then(response=>response.data)
    },
    reserveTotal(){
        return service.get('/charts/reserveTotal').then(response=>response.data)
    },


    weekCoach(date){
         return service.get('/charts/weekCoach',{params:{date}}).then(response=>response.data)
    },
    weekCourse(date){
         return service.get('/charts/weekCourse',{params:{date}}).then(response=>response.data)
    },
    weekReserve(date){
         return service.get('/charts/weekReserve',{params:{date}}).then(response=>response.data)
    },


    reserveCount(date){
         return service.get('/charts/reserveCount',{params:{date}}).then(response=>response.data)
    }



    
}
export default chartsApi;