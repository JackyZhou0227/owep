import request from '../https/request';
//登录验证
export const loginOn= (data)=>{
    console.log("========getLogin==========")
   return request({
        url:'/login',
        method:'post',
        data:data
    }).catch(error=>{
        console.log(error,"===========")
        return error.response
        // return Promise.reject(error)
    })
}
export const getMenu=()=>{
    return request({
        url:'/getMenu',
        method:'get',
    })
}
// export const loginOn= (data)=>{
//     console.log("========data==========")
//    return request({
//         url:'../json/user.json',
//         method:'post',
//         data:data
//     }).catch(error=>{
//         console.log(error,"===========")
//         return error.response
//         // return Promise.reject(error)
//     })
// }
//获取所有备份信息
export const getFiles = (data) => {
    console.log(data)
    return request({
        url: '../json/system/file.json',
        method: 'get',
    });
};

