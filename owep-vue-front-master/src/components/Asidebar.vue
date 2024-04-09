<template>
    <div class="sidebar">  
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="sidebar.collapse"
            background-color="#2f4050"
            text-color="#bfcbd9"
            active-text-color="#293846"
            unique-opened
            router
        >
        
        <!--  有内容的卡片 -->
        <div v-if="!sidebar.collapse">
        <el-row>
    <el-col>
      <el-card   class="box-card" :body-style="{ padding: '0px' }">
        <el-image style="width:90px; height: 90px" :src="avatarUrl" fit="contain" />
        <!-- <el-image
                    style="width: 90px; height: 90px"
                    :src="avatarUrl"
                    :zoom-rate="1.2"
                    :max-scale="7"
                    :min-scale="0.2"
                    :preview-src-list="srcList"
                    :initial-index="4"
                    fit="cover"/> -->
        <div style="padding: 14px;color: #fff;">
            <span>{{ user.userName }}</span>
        </div>
        <div style="padding: 14px">
         <!-- 用户名下拉菜单 -->
				<el-dropdown class="user-name" trigger="click" @command="handleCommand">
					<span class="el-dropdown-link">
						{{ user.userRole}}
						<el-icon class="el-icon--right">
							<arrow-down />
						</el-icon>
					</span>
					<template #dropdown>
						<el-dropdown-menu>
                            <el-dropdown-item command="modifyAvatar">修改头像</el-dropdown-item>
							<el-dropdown-item command="profile">个人中心</el-dropdown-item>
                            <el-dropdown-item command="contacts">联系我们</el-dropdown-item>
							<el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
						</el-dropdown-menu>
					</template>
				</el-dropdown>
        </div>
   
      </el-card>
    </el-col>
  </el-row>
</div>
    <div v-else  class="logo-element">
        <el-row>
    <el-col>
      <el-card   class="box-card01" :body-style="{ padding: '0px' }">
        <div style="padding: 14px;color: #fff;">
            <span>LeMa</span>
        </div>
      </el-card>
    </el-col>
  </el-row>

        
    </div>
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-sub-menu :index="item.index" :key="item.index" v-permiss="item.permiss">
                        <template #title>
                            <el-icon>
                                <component :is="item.icon"></component>
                            </el-icon>
                            <span>{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-sub-menu
                                v-if="subItem.subs"
                                :index="subItem.index"
                                :key="subItem.index"
                                v-permiss="item.permiss"
                            >
                                <template #title>{{ subItem.title }}</template>
                                <el-menu-item v-for="(threeItem, i) in subItem.subs" :key="i" :index="threeItem.index">
                                    {{ threeItem.title }}
                                </el-menu-item>
                            </el-sub-menu>
                            <el-menu-item v-else :index="subItem.index" v-permiss="item.permiss">
                                <el-icon>
                                   <component :is="subItem.icon"></component>
                                 </el-icon>
                                {{ subItem.title }}
                            </el-menu-item>
                        </template>
                    </el-sub-menu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index" v-permiss="item.permiss">
                        <el-icon>
                            <component :is="item.icon"></component>
                        </el-icon>
                        <template #title>{{ item.title }}</template>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script setup >
import { computed,reactive,toRefs} from 'vue';
import { useSidebarStore } from '../stores/sidebar';
import { useRoute ,useRouter} from 'vue-router';
import avatarUrl from '../assets/img/profile.jpg'
const user = reactive({
    userName:'TeacherYe',
    userRole:'超级管理员'
})
//后期修改从后台获取
const items = [
    // {
    //     icon: 'Odometer',
    //     index: '/dashboard',
    //     title: '系统首页',
    //     permiss: '1',
    // },
    {
        icon: 'HomeFilled',
        index: 'system',
        title: '系统配置',
        permiss: '1',
        subs: [
            {
                icon:"Aim",
                index: '/paramInfo',
                title: '参数信息',
                permiss: '1',
            },
            {
                icon:"Setting",
                index: '/config',
                title: '配置资料',
                permiss: '1',
            },
            {
                icon:'HelpFilled',
                index: '/backup',
                title: '数据库备份',
                permiss: '1',
            },
        ],
    },
   {
        icon: 'UserFilled',
        index: 'user',
        title: '用户管理',
        permiss: '2',
        subs: [
            {
                icon:"Aim",
                index: '/adminList',
                title: '管理员管理',
                permiss: '2',
            },
            {
                icon:"Setting",
                index: '/stuList',
                title: '用户管理',
                permiss: '2',
            },
            {
                icon:'HelpFilled',
                index: '/teacherList',
                title: '教师管理',
                permiss: '2',
            },
            {
                icon:'HelpFilled',
                index: '/advisorList',
                title: '咨询师管理',
                permiss: '2',
            },
        ],
    },
    {
        icon: 'Lock',
        index: '/permission',
        title: '权限管理',
        permiss: '3',
        subs: [
            {
                icon:"Aim",
                index: '/userGroup',
                title: '用户组管理',
                permiss: '3',
            },
            {
                icon:"Setting",
                index: '/roleList',
                title: '角色管理',
                permiss: '3',
            },
            {
                icon:'HelpFilled',
                index: '/rolePriv',
                title: '角色权限',
                permiss: '3',
            },
            {
                icon:'HelpFilled',
                index: '/menuAction',
                title: '菜单行为',
                permiss: '3',
            },
            {
                icon:'HelpFilled',
                index: '/menuList',
                title: '菜单管理',
                permiss: '3',
            },
            {
                icon:'HelpFilled',
                index: '/actionList',
                title: '行为管理',
                permiss: '3',
            },
        ],
        
    },
    {
        icon: 'Grid',
        index: 'clazz',
        title: '班级管理',
        permiss: '4',
        subs: [
            {
                icon:"Aim",
                index: '/major',
                title: '专业方向',
                permiss: '4',
            },
            {
                icon:"Setting",
                index: '/clazzList',
                title: '班级列表',
                permiss: '4',
            },
            {
                icon:'HelpFilled',
                index: '/question',
                title: '班级问题',
                permiss: '4',
            },
            {
                icon:'HelpFilled',
                index: '/homework',
                title: '布置作业',
                permiss: '4',
            },
            {
                icon:'HelpFilled',
                index: '/studyProcess',
                title: '学习进度',
                permiss: '4',
            },
        ],
    },
    {
        icon: 'Bell',
        index: 'notice',
        title: '通知公告',
        permiss: '5',
        subs: [
            {
                icon:'HelpFilled',
                index: '/noticeList',
                title: '公告管理',
                permiss: '5',
            },
            {
                icon:'HelpFilled',
                index: '/newsList',
                title: '新闻咨询',
                permiss: '5',
            },
        ],
    },
    {
        icon: 'Document',
        index: 'log',
        title: '日志管理',
        permiss: '6',
        subs:[
            {
                icon:"Aim",
                index: '/logList',
                title: '日志列表',
                permiss: '6',
            },
        ]
    },
    {
        icon: 'Share',
        index: 'organization',
        title: '组织机构',
        permiss: '7',
        subs:[
            {
                icon:"Aim",
                index: '/branchList',
                title: '组织列表',
                permiss: '7',
            },
            {
                icon:"Aim",
                index: '/orgList',
                title: '分支机构',
                permiss: '7',
            },
        ]
    },
];

const route = useRoute();
const onRoutes = computed(() => {
    return route.path;
});
const  router = useRouter()
const sidebar = useSidebarStore();

const handleCommand = (command) => {
	if (command == 'loginout') {
        console.log('--------loginout----')
		localStorage.removeItem('ms_username');
        localStorage.removeItem('token')
		router.push('/login');
	} else if (command == 'user') {
		router.push('/user');
	}
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 0px;
    bottom: 0;
    overflow-y: scroll;
    
}
.sidebar::-webkit-scrollbar {
    width: 0;
  
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
}
/* .avatar {
    background-color:#324157;
    color: #fff;
    padding-left: 20px;
    align-items: center;
    
} */

.el-dropdown-link {
	color: #fff;
	cursor: pointer;
	display: flex;
	align-items: center;
}
.el-dropdown-menu__item {
	text-align:center;
}

.box-card{
    background:url('../assets/img/header-profile.png') ;
    border: 0;
    /* padding-left: 20px; */
    padding: 33px 25px;
    width: 250px;
    height: 172px;
}
/* .logo-element {
    text-align: center;
    font-size: 18px;
    font-weight: 600;
    color: white;
    display: none;
    padding: 18px 0;
} */
.box-card01{
    text-align: center;
    background:url('../assets/img/header-profile.png') ;
    border: 0;
    height: 50px;  
    color: #fff;
    font-size: 18px;
}
.el-menu-item.is-active {
     color: white; 
}
</style>
