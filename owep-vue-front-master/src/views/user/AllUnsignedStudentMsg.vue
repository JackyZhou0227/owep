<template>
	<!-- <div> -->
    <!-- <div class="container"> -->
		<div class="handle-box">
			   <el-input v-model="realName" placeholder="搜索" class="handle-input mr10"></el-input>
				<!-- <el-button color="#1c84c0" :icon="Search" @click="handleSearch">查询</el-button> -->
     			<el-button-group class="ml-4">
                    <el-button type="danger"  @click="handleAllocation">批量分配班级</el-button>      
       		 </el-button-group>
			</div>
			<el-table ref="multipleTableRef"  :data="tableData"   style="width: 100%;" height="380" @selection-change="handleSelectionChange">
                <el-table-column   type="selection" width="30" />
				<el-table-column  prop="serialNumber" label="序号"  ></el-table-column>
				<el-table-column prop="name" label="姓名"></el-table-column>
                    <el-table-column prop="sex" label="性别"></el-table-column>
                <el-table-column prop="phone" label="手机"></el-table-column>
                <el-table-column prop="customerBelongs" label="客户归属"></el-table-column>
					<el-table-column label="操作" width="220" align="center" >
					<template #default="scope">
                        <el-button-group class="ml-4">
                        <!-- <el-button text :icon="Edit" @click="handleEdit(scope.$index,scope.row)" v-permiss="2"></el-button> -->
						<el-tooltip content="修改" placement="top" effect="light">
						<el-button text :icon="Edit" @click="handleEdit(scope.$index,scope.row)" v-permiss="2"></el-button>
						</el-tooltip>
						<el-tooltip content="删除" placement="top" effect="light">
						<el-button text :icon="Delete" class="red" @click="handleDelete(scope.$index)" v-permiss="2"></el-button>
					</el-tooltip>
					<el-tooltip content="分配班级" placement="top" effect="light">
                        <el-button text   v-permiss="2"  @click="assignClasses(scope.$index,scope.row)"><i class="iconfont icon-share4 blue"></i></el-button>
                    </el-tooltip>   
					</el-button-group>
					</template>
				</el-table-column>
			</el-table>
			<!-- 分页 -->
			<div class="pagination">
				<el-pagination
				:current-page="query.pageIndex"
					:page-size="query.pageSize"
					:page-sizes="[5, 10, 15, 20]"
					:small="small"
					layout="total, sizes, prev, pager, next, jumper"
					:total="pageTotal"
					@size-change="handleSizeChange"
					@current-change="handlePageChange"/>
			</div>
	<!-- </div> -->

		<!-- 编辑弹出框 -->
		<el-dialog :title="titleEdit" v-model="editVisible" width="30%" >
			<el-form label-width="70px">
				<el-form-item label="序号" disabled >
					<el-input v-model="form.serialNumber"  :disabled="isDisable" ></el-input>
				</el-form-item>
				<el-form-item label="姓名">
					<el-input v-model="form.name"></el-input>
				</el-form-item>
                <el-form-item label="性别">
                    <el-radio-group v-model="form.sex">
                        <el-radio :label="'男'">男</el-radio>
                        <el-radio :label="'女'">女</el-radio>
                    </el-radio-group>
				</el-form-item>
                <el-form-item label="手机">
					<el-input v-model="form.phone"></el-input>
				</el-form-item>
                <el-form-item label="客户归属">
					<el-input v-model="form.customerBelongs"></el-input>
				</el-form-item>
              
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="editVisible = false">关闭</el-button>
					<el-button color="#2388c5" @click="saveEdit">确 定</el-button>
				</span>
			</template>
		</el-dialog>

		<!-- 分配用户组弹出框 -->
		<el-dialog title="分配用户组" v-model="classesVisible" width="30%">
			<el-form label-width="85px">
				<el-form-item label="用户名" >
					<el-input v-model="formGroup.name" disabled></el-input>
				</el-form-item>
				<el-form-item label="分配班级" > 
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
					<el-button @click="classesVisible = false">关闭</el-button>
					<el-button color="#0EB594" @click="saveClasses">分配</el-button>
				</span>
			</template>
		</el-dialog>
	<!-- </div> -->
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Delete, Edit, Search, Plus } from '@element-plus/icons-vue';
import { getUserList,deleteAllAdmin} from '../../api/user';

//是否启用
const small = ref(false)

//结束时间
const  realName  = ref('')
//分页数据
const query = reactive({
	pageIndex: 1,
	pageSize: 5,
});

//表格数据
const tableData = ref();
//总页数
const pageTotal = ref(0);
const multipleTableRef = ref()
const state = reactive({
	multipleSelection:[]
})

// 获取表格数据  分页是数据截取  后台结合一起后  直接返回分页的后的数据
const getData = () => {
    getUserList({pageCurrent:query.pageIndex,pageSize:query.pageSize}).then(res=>{
        console.log(res,"---res---")
        // tableData.value = res.data.
		let start  = (query.pageIndex-1)*query.pageSize;//开始位置
        let end =query.pageIndex*query.pageSize  ;//结束位置 数组的截取的时候 [start,end) suo所以不用-1
        tableData.value = res.data.slice(start,end);
        // tableData.value = res.data;
        pageTotal.value = res.data.length
    })
	
};
getData();
//多选框权限
const handleSelectionChange = (val) => {
  state.multipleSelection = val
}
// 分页导航
const handleSizeChange = (val: number) => {
  // console.log(`${val} items per page`)
  query.pageSize = val;
  getData()
}
const handlePageChange = (val) => {
	query.pageIndex = val;
	console.log(val,"==")
	console.log(query.pageSize)
	getData()
};
// 删除单个操作
const handleDelete = (index) => {
	// 二次确认删除
	ElMessageBox.confirm('确定要删除吗？', '提示', {
		type: 'warning'
	})
	.then(() => {
		ElMessage.success('删除成功');
		//to 传递id 到后台 
		tableData.value.splice(index, 1);
		// 删除成功后
		// getData()
	})
	.catch(() => {});
};

// 查询操作  to... 需要自己实现与后台交互
const handleSearch = () => {
	query.pageIndex = 1;
	getData();
};

// 表格编辑时弹窗和保存
const editVisible = ref(false);
let form = reactive({
    id:'',
	serialNumber: '',
	name: '',
    sex:'',
    phone:'',
	customerBelongs:''
});

// 
let titleEdit = ref('修改')
let isDisable = ref(true)

let idx: number = -1;
const handleEdit = (index: number, row: any) => {
	idx = index;
    form.id = row.id;
	form.serialNumber= row.serialNumber;
	form.name = row.name;
    form.sex = row.sex;
    form.phone = row.phone;
    form.customerBelongs = row.customerBelongs
	editVisible.value = true;
};
//保存添加或者修改的数据 ---需要重新实现
const saveEdit = () => {
	editVisible.value = false;
	ElMessage.success(`修改第 ${idx + 1} 行成功`);
	tableData.value[idx].name = form.name;
	tableData.value[idx].serialNumber = form.serialNumber;
    tableData.value[idx].sex= form.sex;
    tableData.value[idx].phone = form.phone;
    tableData.value[idx].customerBelongs = form.customerBelongs;
};
//分配用户组的弹框
// import type Node from 'element-plus/es/components/tree/src/model/node'
const classesVisible = ref(false);
const formGroup  = reactive({
	name:''
})
const  treeRef = ref()

//分配班级回显
const assignClasses =(index,row)=>{
  console.log(index);
  formGroup.name = row.name
  classesVisible.value = true
}
//保存用户分组信息
const  saveClasses=()=>{
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
    label: '大学',
    children: [
		{
            id: 2,
            label: '萍乡学院',
			children:[
			{
				id: 3,
				label: 'JavaEE企业级开发',
				children:[
					{
						id: 4,
						label: 'Java全栈开发',
					},
					{
						id: 5,
						label: 'Java企业班级',
					}
				]
       		 },
			]
        },
		
		
	],
  }, 
]

//批量分配班级 ...to
const handleAllocation=()=>{
	//...
}




</script >
<style lang="less" scoped>
.handle-box {
	margin-bottom: 20px;  
}

.el-select {
	width: 80px;
	margin-right: 10px;
}

.handle-input {
	width: 220px;
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
.el-col {
  border-radius: 4px;
  margin-left: 10px;
}
.el-button-group{
    float: right;
}
</style>