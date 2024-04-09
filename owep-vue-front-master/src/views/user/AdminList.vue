<template>
	<div>
		<div class="container">
			<div class="handle-box">
                用户名：<el-input v-model="username" placeholder="" class="handle-input mr10"></el-input>
                姓名：<el-input v-model="realName" placeholder="" class="handle-input mr10"></el-input>
				<el-button color="#1c84c0" :icon="Search" @click="handleSearch">查询</el-button>
                <el-button-group class="ml-4">
                    <el-button color="#0eb394"  @click="handleExport">导出</el-button>
                    <el-button color="#00a6c8"   @click="open">添加</el-button>
                    <el-button type="danger"  @click="handleAllDelete">删除</el-button>
                </el-button-group>
			</div>
			<el-table ref="multipleTableRef"  :data="tableData"   style="width: 100%;" height="380" @selection-change="handleSelectionChange">
                <el-table-column   type="selection" width="30" />
				<el-table-column  prop="userName" label="用户名"  ></el-table-column>
				<el-table-column prop="name" label="姓名"></el-table-column>
                <el-table-column prop="phone" label="手机"></el-table-column>
                <el-table-column prop="email" label="邮箱"></el-table-column>
				<el-table-column label="是否启用">
                    <template #default="scope">
                        <el-switch
                                v-model="scope.row.startUsing"
                                :active-value="'启用'"
                                :inactive-value="'不启用'"
                                active-color="#64bd63"
                                inactive-color="#c0c0c0"
                                @change="changeSwitch(scope.row.startUsing)"/>
					</template>
                </el-table-column>
                <el-table-column prop="lastLandingTime" label="最后登录时间"></el-table-column>
				<el-table-column label="操作" width="220" align="center" >
					<template #default="scope">
                        <el-button-group class="ml-4">
                        <!-- <el-button text :icon="Edit" @click="handleEdit(scope.$index,scope.row)" v-permiss="2"></el-button> -->
                        <el-tooltip content="修改" placement="top" effect="light" >
						<el-button text :icon="Edit" @click="open(scope.$index,scope.row)" v-permiss="2"></el-button>
					</el-tooltip>
					<el-tooltip content="删除" placement="top" effect="light" >
						<el-button text :icon="Delete" class="red" @click="handleDelete(scope.$index)" v-permiss="2"></el-button>
					</el-tooltip> 
					<el-tooltip content="分配用户组" placement="top" effect="light" >
                        <el-button text   v-permiss="2"  @click="groupEdit(scope.$index,scope.row)"><i class="iconfont icon-share4 blue"></i></el-button>
                    </el-tooltip>    
					</el-button-group>
					</template>
				</el-table-column>
			</el-table>
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
		</div>

		<!-- 编辑弹出框 -->
		<el-dialog :title="titleEdit" v-model="editVisible" width="30%" >
			<el-form label-width="70px">
				<el-form-item label="用户名" disabled >
					<el-input v-model="form.userName"  :disabled="isDisable" ></el-input>
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
                <el-form-item label="邮箱">
					<el-input v-model="form.email"></el-input>
				</el-form-item>
                <el-form-item label="有效期">
					<el-date-picker v-model="form.lastLandingTime" type="date" :size="size"/>
				</el-form-item>
                <el-form-item label="是否启用">
					<el-switch
                                v-model="form.startUsing"
                                :active-value="'启用'"
                                :inactive-value="'不启用'"
                                active-color="#64bd63"
                                inactive-color="#c0c0c0"/>	
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
		<el-dialog title="分配用户组" v-model="groupVisible" width="30%">
			<el-form label-width="85px">
				<el-form-item label="用户名" >
					<el-input v-model="formGroup.userName" disabled></el-input>
				</el-form-item>
				<el-form-item label="分配用户组" > 
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
					<el-button @click="groupVisible = false">关闭</el-button>
					<el-button type="success" @click="saveGroup">分配</el-button>
				</span>
			</template>
		</el-dialog>
	</div>
</template>

<script setup lang="ts" name="adminList">
import { ref, reactive } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Delete, Edit, Search, Plus } from '@element-plus/icons-vue';
import { getUserList,deleteAllAdmin} from '../../api/user';
//是否启用
const  isStart = ref(true)
const small = ref(false)
const size = ref<'default' | 'large' | 'small'>('default')
//开始时间
const username = ref('')
//结束时间
const  realName  = ref('')
//分页数据
const query = reactive({
	pageIndex: 1,
	pageSize: 5,
});
//

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
        console.log(res,"---AdminList,res---")
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


//是否启用滑块 --- 调用后台接口 修改后台数据状态  to...
const  changeSwitch=(status)=>{
    //调用后台修改接口
    console.log(status,'--要实现的--');
}

// 删除单个操作
const handleDelete = (index) => {
	// 二次确认删除
	ElMessageBox.confirm('确定要删除吗？', '提示', {
		type: 'warning'
	})
	.then(() => {
		ElMessage.success('删除成功');
		//to.... 传递id 到后台 
		tableData.value.splice(index, 1);
		// 删除成功后
		// getData()
	})
	.catch(() => {});
};
//删除多个数据  to......
const handleAllDelete =()=>{
	// console.log('删除多个')
	console.log(state.multipleSelection,'===拿到所有选择的数据====')
	if (state.multipleSelection.length==0) {
		ElMessage.error('请选择项')
		return
  	}
	// console.log(state.multipleSelection.map(item=>item.id))
	//axios删除的操作
	// deleteAllAdmin({ids:state.multipleSelection.map(item=>item.id)})

	
}

// 查询操作  to... 需要自己实现与后台交互
const handleSearch = () => {
	query.pageIndex = 1;
	getData();
};

// 表格编辑时弹窗和保存
const editVisible = ref(false);
let form = reactive({
    id:'',
	userName: '',
	name: '',
    sex:'',
    phone:'',
    email:'',
    startUsing:'',
    lastLandingTime:''
});

// 
let titleEdit = ref('修改')
let isDisable = ref(true)

const open  = (index,row)=>{//index是时间源对象
	if(row){
		isDisable.value=true
		titleEdit.value = "修改"
		handleEdit(index,row)
	}else{
		//把第一个输出改为可编辑
		isDisable.value=false
		//清空表单中的数据
		titleEdit.value = "添加"
		form.email=''
		form.name=''
		form.phone=''
		form.sex=''
		form.lastLandingTime=''
		form.userName=''
		form.startUsing=''
		editVisible.value = true	
	}
	console.log('-------测试传值-------')
	// console.log(index,row,index.target.innerText)
	// console.log(index,row)
	// console.log('================')

}
let idx: number = -1;
const handleEdit = (index: number, row: any) => {
	idx = index;
    form.id = row.id;
	form.userName= row.userName;
	form.name = row.name;
    form.sex = row.sex;
    form.phone = row.phone;
    form.email = row.email;
    form.startUsing = row.startUsing;
    form.lastLandingTime = row.lastLandingTime;
	editVisible.value = true;
};
//保存添加或者修改的数据 to---需要重新实现
const saveEdit = () => {
	editVisible.value = false;
	ElMessage.success(`修改第 ${idx + 1} 行成功`);
	tableData.value[idx].name = form.name;
	tableData.value[idx].username = form.userName;
    tableData.value[idx].email = form.email;
    tableData.value[idx].phone = form.phone;
    tableData.value[idx].startUsing = form.startUsing;
    tableData.value[idx].lastLandingTime = form.lastLandingTime;
};
//分配用户组的弹框
import type Node from 'element-plus/es/components/tree/src/model/node'
const groupVisible = ref(false);
const formGroup  = reactive({
	userName:''
})
const  treeRef = ref()

//分组回显
const groupEdit =(index,row)=>{
  console.log(index);
  formGroup.userName = row.userName
  groupVisible.value = true
	
}
//保存用户分组信息
const  saveGroup=()=>{
	//拿到被选中的用户组
	console.log(treeRef.value!.getCheckedNodes(false, false))
	//获取被选中点的id节
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
    label: '用户组',
    children: [
		{
            id: 2,
            label: '代码测试组',
        },
		{
            id: 3,
            label: '市场部',
        },
		{
            id: 4,
            label: '测试教务',
        },
		{
            id: 5,
            label: '教务处',
        },
		{
            id: 6,
            label: '教师组',
        },
		{
            id: 7,
            label: '管理员',
        },
	],
  },
  {
    id: 8,
    label: '用户组2',
    children: [
		{
            id: 9,
            label: '代码测试组',
        },
		{
            id: 10,
            label: '市场部',
        },
		{
            id: 11,
            label: '测试教务',
        },
		{
            id: 12,
            label: '教务处',
        },
		{
            id: 13,
            label: '教师组',
        },
		{
            id: 14,
            label: '管理员',
        },
	],
  },
  
]

//


//导出数据
import * as XLSX from 'xlsx';

const handleExport = () => {
	// 二次确认删除
	ElMessageBox.confirm('确定要导管理员数据吗？', '提示', {
		type: 'warning'
	})
		.then(() => {
			ElMessage.success('导出成功');
			 //导出数据的实现
			 exportXlsx()
		})
		.catch(() => {});
};

const list = [['序号','用户名', '姓名', '手机', '邮箱', '是否启用', '最后登录时间']];
const exportXlsx = () => {
	console.log('tablev',tableData.value)
    tableData.value.map((item: any, i: number) => {
        const arr: any[] = [i+1];
        arr.push(...[item.userName, item.name, item.phone, item.email, item.startUsing,item.lastLandingTime]);
        list.push(arr);
    });
    let WorkSheet = XLSX.utils.aoa_to_sheet(list);
    let new_workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(new_workbook, WorkSheet, '第一页');
    XLSX.writeFile(new_workbook, `表格.xlsx`);
};

</script>

<style scoped>
.handle-box {
	margin-bottom: 20px;  
}

.handle-select {
	width: 120px;
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

.el-button-group{
    float: right;	
	
}

</style>
