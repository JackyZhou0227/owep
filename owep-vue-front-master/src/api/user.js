import request from '../https/request';
//登录验证
// export const loginOn= (data)=>{
//     // console.log(data)
//    return request({
//         url:'/adminUser/login',
//         method:'post',
//         data:data
//     })
// }
//登录请求 
// export const loginOn= ()=>{
//     // console.log(data)
//    return request({
//         url:'/getMenu',
//         method:'post',
//         // data:data
//     })
// }


//获取所有备份信息
export const getUserList = (data) => {
    console.log(data,"userList")
    return request({
        url: '../json/user/adminListTest.json',
        method: 'get',
    });
};
//删除多个管理员
export const deleteAllAdmin=(data)=>{
     console.log("删除多个管理员(ids)："+data.ids)
     
}

