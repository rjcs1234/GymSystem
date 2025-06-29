import service from ".";

const memberApi ={
    getMemberInfo(pageInfo){
        return service.get('/member',{params:pageInfo}).then(response=>response.data)
    },
    add(member){
        return service.post('/member',member).then(response=>response.data)
 
    },
    updateInfo(member){
        return service.put('/member',member).then(response=>response.data)
    },
    remove(id){
        return service.delete('/member/'+id).then(response=>response.data)
    },
    showAll(){
        return service.get("/member/all").then(response=>response.data)
    },
}

export default memberApi;