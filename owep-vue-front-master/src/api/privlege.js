import request from '../https/request';
//获取 u
// export const getUserGroup= (data)=>{
//     console.log(data)
//    return request({
//         url:'/userGroup_table',
//         method:'post',
//         data:data
//     }).catch(error=>{
//         console.log(error,"===========")
//         console.log("========pp===",Promise.reject(error))
//         console.log(error.response)
//         console.log(error.response.data)
//         return error.response
//         // return Promise.reject(error)
//     })
// }

export const getUserGroup= (data)=>{
    console.log(data)
   return  request({
        url: '../json/privilege/userGroup_table_test.json',
        method: 'get',
    });
}
//获取所用的角色
export const getRoleList = (data)=>{
    console.log(data)
   return  request({
        url: '../json/privilege/roleList_table_test.json',
        method: 'get',
    });
}
//获取用户权限
//获取所用的角色
export const getRolePrivilege = (data)=>{
    console.log(data)
   return  request({
        url: '../json/privilege/rolePrivilege_table_test1.json',
        method: 'get',
    });
}
//获取菜单行为
export const getMenuAction= ()=>{
    return  request({
        url: '../json/privilege/menuAction_gridTree_test.json',
        method: 'get',
    });
}