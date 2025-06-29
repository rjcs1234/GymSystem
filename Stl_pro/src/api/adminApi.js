
import service from ".";

const adminApi={
    login(admin){
        return service.post('/admin/login',admin).then(response=>response.data)
    },

    add(admin){
        return service.post('/admin/add',admin).then(response=>response.data)
    },
    getLoginAdmin(){
        return service.get('/admin/getAdminInfo').then(response=>response.data)
    },

    showAdminInfo(pageInfo){
        return service.get('/admin',{params:pageInfo}).then(response=>response.data)
    },
    addAdmin(admin){
        return service.post('/admin/addAdmin',admin).then(response=>response.data)
    },

    updateAdminInfo(admin){
        return service.post('/admin/updateAdminInfo',admin).then(response=>response.data)
    },

}
export default adminApi