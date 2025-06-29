import service from ".";

const coachApi={
    getCoachApi(pageInfo){
        return service.get('/coach/getCoachApi',{params:pageInfo}).then(response=>response.data)
    },
    add(coach){
        return service.post('/coach/addCoach',coach).then(response=>response.data)
    },
    remove(id){
        return service.delete('/coach/'+id).then(response=>response.data)
    },
    update(coach){
        return service.post('/coach/update',coach).then(response=>response.data)
    },
    search(){
        return service.get('/coach/getCoachApi').then(response=>response.data)
    }
    
}
export default coachApi;