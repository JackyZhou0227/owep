import { defineStore } from 'pinia';
//侧边导航的收缩功能
export const useSidebarStore = defineStore('sidebar', {
	state: () => {
		return {
			collapse: false
		};
	},
	getters: {},
	actions: {
		handleCollapse() {
			this.collapse = !this.collapse;
		}
	}
});
