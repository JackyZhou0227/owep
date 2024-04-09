<template>
	  <el-aside width="250px"><v-sidebar></v-sidebar></el-aside>
  <div class="content-box" :class="{ 'content-collapse': sidebar.collapse }">
			<v-header></v-header>
			<v-tags></v-tags>
		 <div class="content">
			<router-view v-slot="{ Component }">
				<transition name="move" mode="out-in">
					<keep-alive :include="tags.nameList">
						<component :is="Component"></component>
					</keep-alive>
				</transition>
			</router-view>
		</div>
    <v-footer></v-footer>
	</div>
	
 <!-- <div class="common-layout"> -->
   <!-- <el-container> -->
      <!-- <el-aside width="250px"><v-sidebar></v-sidebar></el-aside> -->
      <!-- <el-container> -->
		<!-- <el-header ><v-header></v-header></el-header> -->
        <!-- <el-main >
			
		 <div class="content-box" :class="{ 'content-collapse': sidebar.collapse }">
			<v-header></v-header>
			<v-tags></v-tags>
		 <div class="content">
			<router-view v-slot="{ Component }">
				<transition name="move" mode="out-in">
					<keep-alive :include="tags.nameList">
						<component :is="Component"></component>
					</keep-alive>
				</transition>
			</router-view>
		</div>
		<el-footer>
		<v-footer></v-footer>
	  </el-footer>
	</div>
	
	</el-main>
	 
   
      </el-container>
    </el-container> -->
<!-- </div> -->
	 <!-- <v-header /> -->
	<!-- <v-sidebar />
	<div class="content-box" :class="{ 'content-collapse': sidebar.collapse }">
		<v-header />
		<v-tags></v-tags>
		<div class="content">
			<router-view v-slot="{ Component }">
				<transition name="move" mode="out-in">
					<keep-alive :include="tags.nameList">
						<component :is="Component"></component>
					</keep-alive>
				</transition>
			</router-view>
		</div>
		<v-footer></v-footer>
	</div>  -->
 <!-- <v-footer></v-footer> -->
</template>
<script setup>
import { useSidebarStore } from '../stores/sidebar'
import { useTagsStore } from '../stores/tags'
import vHeader from '../components/Header.vue'
import vSidebar from '../components/Asidebar.vue'
import vTags from '../components/Tags.vue'
import vFooter from '../components/Footer.vue';

const sidebar = useSidebarStore();
const tags = useTagsStore();
</script>
<style lang="less" scoped>
.el-header{
	--el-header-padding:0 0;
}
/* .el-container{
	background-color: #f3f3f4;
	
} */
.el-footer{
  //position: fixed;
  margin-bottom: -10px;;
}
</style>
