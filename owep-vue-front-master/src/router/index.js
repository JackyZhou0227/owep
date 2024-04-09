import { createRouter,  createWebHashHistory } from 'vue-router'
import { usePermissStore } from '../stores/usePermiss';

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path:'/',
      redirect:'/dashboard'
    },
    {
      path: '/',
      name: 'home',
      component: () => import('../views/Home.vue'),
      children:[
        {
          path:'/dashboard',
          name:'dashboard',
          meta:{
            title:'系统首页',
            permiss:'1',
          },
          component:()=>import('../views/system/paramInfo.vue')  
        },
        {
          path:'/paramInfo',
          name:'paramInfo',
          meta:{
            title:'参数信息',
            permiss:'1',
          },
          component:()=>import('../views/system/paramInfo.vue')  
        },
        {
          path:'/config',
          name:'config',
          meta:{
            title:'配置信息',
            permiss:'1',
          },
          component:()=>import('../views/system/config.vue')  
        },
        {
          path:'/backup',
          name:'backup',
          meta:{
            title:'数据库备份',
            permiss:'1',
          },
          component:()=>import('../views/system/backup.vue')  
        },
        {
          path:'/adminList',
          name:'adminList',
          meta:{
            title:'管理员管理',
            permiss:'2',
          },
          component:()=>import('../views/user/AdminList.vue')  
        },
        {
            path:'/stuList',
            name:'stuList',
            meta:{
              title:'用户管理',
              permiss:'2'
            },
            component:()=>import('../views/user/StudentList1.vue')
        },
        {
          path:'/teacherList',
          name:'teacherList',
          meta:{
            title:'教师管理',
            permiss:'2'
          },
          component:()=>import('../views/user/TeacherList.vue')
          },
          {
            path:'/advisorList',
            name:'advisorList',
            meta:{
              title:'咨询师管理',
              permiss:'2'
            },
            component:()=>import('../views/user/AdvisorList.vue')
        },

        {
          path:'/userGroup',
          name:'userGroup',
          meta:{
            title:'用户组管理',
            permiss:'3',
          },
          component:()=>import('../views/privilege/UserGroup.vue')  
        },
        {
          path:'/roleList',
          name:'roleList',
          meta:{
            title:'角色管理',
            permiss:'3',
          },
          component:()=>import('../views/privilege/RoleList.vue')  
        },
        {
          path:'/rolePriv',
          name:'rolePriv',
          meta:{
            title:'角色权限',
            permiss:'3',
          },
          component:()=>import('../views/privilege/RolePriv.vue')  
        },
        {
          path:'/menuList',
          name:'menuList',
          meta:{
            title:'菜单管理',
            permiss:'3',
          },
          component:()=>import('../views/privilege/MenuList.vue')  
        },
        {
          path:'/menuAction',
          name:'menuAction',
          meta:{
            title:'菜单行为',
            permiss:'3',
          },
          component:()=>import('../views/privilege/MenuAction.vue')  
        },
        {
          path:'/actionList',
          name:'actionList',
          meta:{
            title:'行为管理',
            permiss:'3',
          },
          component:()=>import('../views/privilege/actionList.vue')  
        },
        {
          path:'/major',
          name:'major',
          meta:{
            title:'专业方向',
            permiss:'4',
          },
          component:()=>import('../views/clazz/Major.vue')  
        },
        {
          path:'/clazzList',
          name:'clazzList',
          meta:{
            title:'班级列表',
            permiss:'4',
          },
          component:()=>import('../views/clazz/ClazzList.vue')  
        },
        {
          path:'/question',
          name:'question',
          meta:{
            title:'班级问题',
            permiss:'4',
          },
          component:()=>import('../views/clazz/Question.vue')  
        },
        {
          path:'/homework',
          name:'homework',
          meta:{
            title:'班级问题',
            permiss:'4',
          },
          component:()=>import('../views/clazz/Homework.vue')  
        },
        {
          path:'/studyProcess',
          name:'studyProcess',
          meta:{
            title:'班级问题',
            permiss:'4',
          },
          component:()=>import('../views/clazz/StudyProcess.vue')  
        },
        
        {
          path:'/noticeList',
          name:'noticeList',
          meta:{
            title:'公告管理',
            permiss:'5',
          },
          component:()=>import('../views/notice/NoticeList.vue')  
        },
        {
          path:'/newsList',
          name:'newsList',
          meta:{
            title:'公告管理',
            permiss:'5',
          },
          component:()=>import('../views/notice/NewsList.vue')  
        },
        
        {
          path:'/logList',
          name:'logList',
          meta:{
            title:'日志列表',
            permiss:'6',
          },
          component:()=>import('../views/log/LogList.vue')  
        },
        {
          path:'/branchList',
          name:'branchList',
          meta:{
            title:'分支机构',
            permiss:'7',
          },
          component:()=>import('../views/organization/BranchList.vue')  
        },
        {
          path:'/orgList',
          name:'orgList',
          meta:{
            title:'机构列表',
            permiss:'7',
          },
          component:()=>import('../views/organization/OrgList.vue')  
        },
       
        
      ]
    },
    {
      path: '/login',
      name: 'login',
      meta: {
          title: '登录',
      },
      component: () => import(/* webpackChunkName: "login" */ '../views/Login.vue'),
  },
  {
      path: '/403',
      name: '403',
      meta: {
          title: '没有权限',
      },
      component: () => import(/* webpackChunkName: "403" */ '../views/403.vue'),
  },
  ]
})
router.beforeEach((to, from, next) => {
  document.title = `乐码在线智慧平台 - ${to.meta.title}`;
  const role = localStorage.getItem('ms_username');
  // let token  = localStorage.getItem('token')
  const permiss = usePermissStore();
  // console.log(permiss.key,'aa')
  if (!role && to.path !== '/login') {
      next('/login');
  } else if (to.meta.permiss && !permiss.key.includes(to.meta.permiss)) {
      // 如果没有权限，则进入403
      next('/403');
  // }else if(token==null||token===''){
  //   next('/login')
  } else {
      next();
  }
});


export default router
