import service from '.'
const reserveApi = {
    search(pageInfo){
        return service.get('/reserve',{params:pageInfo}).then(response=>response.data)
    },
    add(reserveInfo){
        return service.post('/reserve',reserveInfo).then(response=>response.data)
    },
    edit(reserveInfo){
          return service.put('/reserve',reserveInfo).then(response=>response.data)
    }


}


export default reserveApi;