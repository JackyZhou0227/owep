import axios from 'axios';
import router from "@/router/index";
// import qs from "qs"
const service = axios.create({
    baseURL:"/api",
    timeout: 5000,//设置超时时间
    // withCredentials: true // 设置是否允许跨域传递的 cookie 携带凭证
});
// 配置请求参数传递格式，默认是JSON格式，根据服务器决定
// service.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded';
// service.defaults.transformRequest = data => qs.stringify(data)

//配置axios请求拦截 配置token 登录认证
service.interceptors.request.use(
    (config) => {
        let token = localStorage.getItem('token');
        console.log("token request",token)
        // config.headers['authorization'] = token;
        config.headers.Authorization = token
        return config;
    },
    (error) => {
        // console.log("requesrt error:",error);
        return Promise.reject(error);
    }
);

service.interceptors.response.use(
    (response) => {
        if (response.status === 200) {
            return Promise.resolve(response);
        } else {
           return Promise.reject(response);
        }
    },
    (error) => {
        if(error.response){
            switch(error.response.status) {
                // 401: 未登录
                case 401: 
                  localStorage.removeItem('token');
                  alert('连接超时，请重新登录');
                  router.push({path: '/login'});
                  break
                // 403： 登录过期
                case 403:
                  alert('登录过期，请重新登录');
                  localStorage.removeItem('token');
                  router.push({path: '/login'});
                  break
                // 404: 请求不存在
                case 404: 
                  alert('网络请求不存在');
                  break
                // 500: 服务错误
                case 500:
                  alert('网络请求有误');
                default:
                  alert('服务错误');
              }
     
        }else{
            // 配置服务器没有返回结果情况
            if(!window.navigator.onLine) {
                // 断网情况，进行断网处理
                return
            }
            console.log("response:",error);
            return Promise.reject(error);
        }
    }
);

export default service;
