
import service from ".";

const courseApi={
    search(pageInfo){
         return service.get('/course/getCourseInfo',{params:pageInfo}).then(response=>response.data)
    },
    add(course){
        return service.post('/course/addCourse',course).then(response=>response.data)
    },
    showAll(){
    return service.get('/course/all').then(response=>response.data)
   },
    

}
export default courseApi