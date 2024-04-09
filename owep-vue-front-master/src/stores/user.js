import { defineStore } from 'pinia';

//存储token或者用户信息
export const useUserStore = defineStore('user', {
	state: () => {
		return {
			token: localStorage.getItem('token')?localStorage.getItem('token'):''
		};
	},
	getters: {},
	actions: {
		//修改token 并将token 存入localStorage
		setToken(data) {
			console.log("===tokenset==",data)
			this.token = data.token
			localStorage.setItem('token',data.token)
			console.log('==========token================')
		}
	}
});
