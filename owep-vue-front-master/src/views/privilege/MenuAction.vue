<template>
	<div>
		<div class="container">
		 <div class="handle-box">
			<el-table
				v-loading="loading"
				:data="menuAction"
				highlight-current-row
				:tree-props="{
				children: 'children',
				hasChildren: 'hasChildren',
				}"
				row-key="id"
				default-expand-all
				border
				size="small"
				@row-click="onRowClick"
				:header-cell-style="{color:'#2A62BC'}"
      >
        <el-table-column label="菜单名称" min-width="200">
          <template #default="scope">
            {{ scope.row.menuName }}
          </template>
        </el-table-column>
		<el-table-column prop="url" label="菜单URL"></el-table-column>
		<el-table-column fixed="right" align="left" label="操作" width="220">
          <template #default="scope">
		
			<el-tooltip  v-if="scope.row.pid!=0"  content="预置分配行为" placement="top" effect="light" >
					<el-button text :icon="Edit" @click="allocationAction(scope.$index,scope.row)" v-permiss="2"></el-button>
			</el-tooltip>
			<span v-else>-</span>
          </template>
        </el-table-column>
		</el-table>
		</div>
		<!-- 分配行为弹出框 -->
		<el-dialog title="分配行为" v-model="roleVisible" width="30%">
			<el-form label-width="85px">
				<el-form-item label="菜单名称" >
					<el-input v-model="form.menuName" disabled></el-input>
				</el-form-item>
				<el-form-item label="包含行为" > 
					<el-tree
						ref="treeRef"
						:data="data"
						show-checkbox
						node-key="id"
						:props="defaultProps"
					/>
				</el-form-item>
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="roleVisible = false">关闭</el-button>
					<el-button color="#0EB594" @click="saveGroup">保存</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
	</div>
</template>

<script setup lang="ts" name="adminList">
import { ref, reactive } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Delete, Edit, Search, Plus } from '@element-plus/icons-vue';
import {getMenuAction} from '../../api/privlege';

// 选择表格的行菜单ID
const selectedRowMenuId = ref();
/**行点击事件 */
function onRowClick(row) {
  selectedRowMenuId.value = row.id;
}
const loading = ref(false);
const menuAction = ref([
	{
		id: 1,
		menuName: "系统配置",
		pid: 0,
		url: "系统配置",
		children:[
			{
			"id": 4,
			"pid": 1,
			"menuName": "参数信息",
			"url": "参数信息"
			},
			{
			"id": 5,
			"pid": 1,
			"menuName": "资料配置",
			"url": "资料配置"
			},
			{
			"id": 6,
			"pid": 1,
			"menuName": "数据库备份",
			"url": "数据库备份"}
		]
	},
	{
    "id": 2,
    "pid": 0,
    "menuName": "用户管理",
    "url": "用户管理",
	children:[
	{
    "id": 7,
    "pid": 2,
    "menuName": "管理员管理",
    "url": "管理员管理"
  },
  {
    "id": 8,
    "pid": 2,
    "menuName": "学生管理",
    "url": "学生管理"
  },
	]
  },
  {
    "id": 3,
    "pid": 0,
    "menuName": "权限管理",
    "url": "权限管理",
	children:[
	{
    "id": 9,
    "pid": 3,
    "menuName": "用户组管理",
    "url": "用户组管理"
  },
  {
    "id": 10,
    "pid": 3,
    "menuName": "角色管理",
    "url": "角色管理"
  },
  {
    "id": 11,
    "pid": 3,
    "menuName": "角色权限",
    "url": "角色权限"
  }
	]
  },



]);


//表格数据
const tableData = ref();
// 获取表格数据  分页是数据截取  后台结合一起后  直接返回分页的后的数据
const getData = () => {
	// loading.value = true
    getMenuAction().then(({data})=>{
        console.log(data,"---data---")
        // 数据处理
	//    data.map(item=>{
	// 		console.log('====Menuitem====',item)
			
			
	// 	})
       // tableData.value = res.data
        
    })
	
};
getData();

//分配行为的弹框
const roleVisible = ref(false);
const form  = reactive({
	menuName:''
})
const  treeRef = ref()

//分组回显
const allocationAction =(index,row)=>{
  console.log(index,"role");
  form.menuName = row.menuName
  roleVisible.value = true
	
}
//保存用户分组信息
const  saveGroup=()=>{
	//拿到被选中的用户组
	console.log(treeRef.value!.getCheckedNodes(false, false))
	//获取被选中节点的id
	console.log(treeRef.value!.getCheckedKeys(false))
   //to ...
}

//树形状控件
const defaultProps = {
  children: 'children',
  label: 'label',
}
// 分组数据目前是死的  可以同axios请求 从后台获取  
const data = [
  {
    id: 1,
    label: '包含行为',
    children: [
		{
            id: 2,
            label: '发送有邮件',
        },
		{
            id: 3,
            label: '跟进记录',
        },
		{
            id: 4,
            label: '分配客户',
        },
		{
            id: 5,
            label: '签约',
        },
		{
            id: 6,
            label: '转班记录',
        },
		{
            id: 7,
            label: '拒绝请假',
        },
		{
            id: 8,
            label: '评价',
        },
		{
            id: 9,
            label: '审批',
        },
		{
            id: 10,
            label: '批改',
        },
		{
            id: 11,
            label: '详情',
        },
	],
  },
  

  
]

</script>

<style scoped>
.handle-box {
	margin-bottom: 20px;
     
}

.handle-select {
	width: 120px;
}

.handle-input {
	width: 196px;
}
.table {
	width: 100%;
	font-size: 14px;
}
.red {
	color: #F56C6C;
}
.blue {
	color: #1296e2;
	font-weight: bold;
}
.mr10 {
	margin-right: 10px;
}
.table-td-thumb {
	display: block;
	margin: auto;
	width: 40px;
	height: 40px;
}
.cell{
    display: inline-block;
    .el-button{
        margin-left: 0px;
    }
}

.el-button-group{
    float: right;
}
</style>
