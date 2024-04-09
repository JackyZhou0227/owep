<template>
	<div class="login-wrap">
		<div class="ms-login">
			<div class="ms-title">乐码在线智慧平台</div>
			<el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
				<el-form-item prop="username">
					<el-input v-model="param.username" placeholder="username">
						<template #prepend>
							<el-button :icon="User"></el-button>
						</template>
					</el-input>
				</el-form-item>
				<el-form-item prop="password">
					<el-input
						type="password"
						placeholder="password"
						v-model="param.password"
						@keyup.enter="submitForm(login)"
					>
						<template #prepend>
							<el-button :icon="Lock"></el-button>
						</template>
					</el-input>
				</el-form-item>
				<!-- <el-card class="cover"  v-if="loginUser.data.user"> -->
					<el-card class="cover" v-if="loginUser.data.user" >
					<slide-verify
						    ref="block"
							slider-text="向右滑动->"
							accuracy=1
							@again="onAgain"
							@success="submitForm"
							@fail="onFail"
							@refresh="onRefresh"
						></slide-verify>
						<div>{{ msg }}</div>
    			</el-card>
				<div class="login-btn">
					<el-button type="primary" @click="submitForm(login)">登录</el-button>
				</div> 
				<p class="login-tips">Tips : 用户名和密码随便填。</p>
			</el-form>
			
		</div>
	</div>
</template>

<script setup>
import { ref, reactive ,onMounted} from 'vue';
import { useTagsStore } from '../stores/tags';
import { usePermissStore } from '../stores/usePermiss';
import { useRouter } from 'vue-router';
import { ElMessage,ElMessageBox} from 'element-plus';
import { Lock, User } from '@element-plus/icons-vue';
import {loginOn} from  '../api/index'
import {useUserStore} from '../stores/user'
 
// 局部注册组件，需要单独引用组件样式
// 只提供局部引用的方式，不再采用插件形式，方便按需加载，减少主包大小
import SlideVerify  from "vue3-slide-verify";
import "vue3-slide-verify/dist/style.css";

const msg = ref("");
const block = ref();

const onFail=()=>{
  msg.value = "验证不通过";
}

const onRefresh=()=>{
  // console.log("");
}
const onAgain = () => {
  msg.value = "请重试！";
  // 刷新
  block.value?.refresh();
};

let  loginUser  = reactive({
	data:{}
})

// 登录的方法 
const  loginMethod  = async()=>{
	let res = await loginOn(param)
   console.log("res.data",res.data.user)
	loginUser.data = res.data.user
	console.log(loginUser.data)
}

// onMounted(() => {
// 	 loginMethod()
// }) 
const router = useRouter();
const param = reactive({
	username: 'admin',
	password: '123456'
});
const rules= {
	username: [{required: true,message: '请输入用户名',trigger: 'blur'}],
	password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
};
const login = ref(null);
const permiss = usePermissStore();
const userStore = useUserStore();

//登录验证
const  submitForm = (formEl)=>{
	if (!formEl) return;
	formEl.validate(async (valid) => {
		console.log(valid)
		//请求后端登录接口数据
		// let res = await loginOn()
		// let res = await loginOn(param)
   		// console.log("res.---login",res)
		//测试是否能获取 authorization
		// console.log("res.--aut",res.headers.authorization)
	    // let data  = res.data
		// let token  = res.headers.authorization
		//调用userStore修改token
		// userStore.setToken({data,token})
		// if(valid&data.message==='登录成功'){
		if (valid) {
				ElMessage.success('登录成功');
				localStorage.setItem('ms_username', param.username);
				// localStorage.setItem('token',res.data.data);
				const keys = permiss.defaultList[param.username == 'admin' ? 'admin' : 'user'];
				permiss.handleSet(keys);
				localStorage.setItem('ms_keys', JSON.stringify(keys));
				router.push('/');		
		} else {
			// ElMessage.error('登录失败');
			ElMessage.error(data.message)
			return false;
		}
	});
}
const tags = useTagsStore();
// 清空所有标签
tags.clearTags();
</script>

<style scoped>
.login-wrap {
	position: relative;
	width: 100%;
	height: 100%;
	background-image: url(../assets/img/login-bg.jpg);
	background-size: 100%;
}
 .ms-title {
	width: 100%;
	line-height: 50px;
	text-align: center;
	font-size: 20px;
	color: #fff;
	border-bottom: 1px solid #ddd;
}
 .ms-login {
	position: absolute;
	left: 50%;
	top: 50%;
	width: 350px;
	margin: -190px 0 0 -175px;
	border-radius: 5px;
	background: rgba(0, 0, 0, 0.15);
	overflow: hidden;
	
}
.ms-content {
	padding: 30px 30px;
	.cover{
		/* width: fit-content; */
		height:300px;
		background-color: aliceblue;
		/* position: absolute; */
		top: 50%;
		left: 50%;
		/* transform: translate(-50%,-50%); */
		/* z-index: 1000; */
}
}
.login-btn {
	text-align: center;
}
.login-btn button {
	width: 100%;
	height: 36px;
	margin-bottom: 10px;
}
.login-tips {
	font-size: 12px;
	line-height: 30px;
	color: #fff;
} 


</style>
