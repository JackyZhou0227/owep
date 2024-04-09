import request from '../https/request';
//获取所有专业方向列表
export const getMajors = (data) => {
    console.log(data)
    return request({
        url: '../json/clazz/major_table.json',
        method: 'get',
    });
};


//---------------班级列表菜单-------------
export const getClazzList = (data) => {
    console.log(data)
    return request({
        url: '../json/clazz/clazzList.json',
        method: 'get',
    });
};
export const getShowClazz = (data) => {
    console.log(data)
    return request({
        url: '../json/clazz/show_clazz.json',
        method: 'get',
    });
};
//-------学生管理弹框的数据
export const getStus = (data) => {
    console.log(data)
    return request({
        url: '../json/clazz/stu.json',
        method: 'get',
    });
};
//-------资源管理弹框的数据
export const getRes = (data) => {
    console.log(data)
    return request({
        url: '../json/clazz/res.json',
        method: 'get',
    });
};
export const getFileAdd = (data) => {
    console.log(data)
    return request({
        url: '../json/clazz/file_add.json',
        method: 'get',
    });
};
//------------班级问题菜单------------------
export const getQuestions = (data) => {
    console.log(data)
    return request({
        url: '../json/clazz/question_list.json',
        method: 'get',
    });
};
//--------------布置作业菜单----------------
export const getHomeWorks= (data) => {
    console.log(data)
    return request({
        url: '../json/clazz/homework.json',
        method: 'get',
    });
};
//
//--------------学习进度菜单----------------
export const getPres= (data) => {
    console.log(data)
    return request({
        url: '../json/clazz/pre.json',
        method: 'get',
    });
};

//