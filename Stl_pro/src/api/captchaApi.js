import service from ".";


const captchaApi={
    getCaptcha(){
         return service.get('/captcha').then(response=>response.data)
    }
}
export default captchaApi;