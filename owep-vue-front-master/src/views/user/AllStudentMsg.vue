<template>
	<!-- <div> -->
    <!-- <div class="container"> -->
		<div class="handle-box">
			<el-select v-model="value" clearable placeholder="班级">
				<el-option
					v-for="item in options.classes"
					:key="item.value"
					:label="item.label"
					:value="item.value"
					/>
			</el-select>
			<el-input v-model="name" placeholder="姓名"
			style="width: 150px; margin-right: 8px;"></el-input>
			<!-- <el-select v-model="value" clearable placeholder="姓名">
					<el-option
					v-for="item in options.names"
					:key="item.value"
					:label="item.label"
					:value="item.value"
					/>
				</el-select> -->
				<el-select v-model="value" clearable placeholder="学校">
					<el-option
					v-for="item in options.schools"
					:key="item.value"
					:label="item.label"
					:value="item.value"
					/>
				</el-select>
				<!-- <el-select v-model="value" clearable placeholder="学院">
					<el-option
					v-for="item in options.colleges"
					:key="item.value"
					:label="item.label"
					:value="item.value"
					/>
				</el-select>
				<el-date-picker
					v-model="startTime"
					type="datetime"
					placeholder="起始时间"
				/>
			   <span>到</span>
				<el-date-picker
					v-model="endTime"
					type="datetime"
					placeholder="结束时间"
				/> -->
				<el-button color="#1c84c0" :icon="Search" @click="handleSearch">查询</el-button>
     			<el-button-group class="ml-4">
                    <el-button color="#e35148" @click="handleAllDelete">删除</el-button>
                    <el-button color="#2388c5"  @click="open">添加</el-button>
                    <el-button color="#0EB394"  @click="handleExport">导出</el-button>
                    <el-button type="warning" @click="handleExport">导入</el-button>      
       		 </el-button-group>
			</div>
			<el-table ref="multipleTableRef"  :data="tableData"   style="width: 100%;" height="380" @selection-change="handleSelectionChange">
                <el-table-column   type="selection" width="30" />
				<el-table-column  prop="stuId" label="学号"  ></el-table-column>
				<el-table-column prop="name" label="姓名"></el-table-column>
                    <el-table-column prop="sex" label="性别"></el-table-column>
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
						<el-tooltip content="修改" placement="top" effect="light">
						<el-button text :icon="Edit" @click="open(scope.$index,scope.row)" v-permiss="2"></el-button>
						</el-tooltip>
						<el-tooltip content="删除" placement="top" effect="light">
						<el-button text :icon="Delete" class="red" @click="handleDelete(scope.$index)" v-permiss="2"></el-button>
					</el-tooltip>
					<el-tooltip content="分配班级" placement="top" effect="light">
                        <el-button text   v-permiss="2"  @click="groupEdit(scope.$index,scope.row)"><i class="iconfont icon-share4 blue"></i></el-button>
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
				<el-form-item label="学号" disabled  v-show="isShow">
					<el-input v-model="form.stuId"  :disabled="isDisable" ></el-input>
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
		<el-dialog title="分配班级" v-model="clsVisible" width="30%">
			<el-form label-width="85px">
				<el-form-item label="学生姓名" >
					<el-input v-model="formGroup.userName" disabled></el-input>
				</el-form-item>
				<el-tabs
						v-model="activeName"
						type="card"
						class="demo-tabs"
						@tab-click="handleClick"
					>
						<el-tab-pane label="分配班级" name="first">
							<el-form-item label="分配班级" > 
								<el-tree
									ref="treeRef"
									:data="data"
									show-checkbox
									node-key="id"
									:props="defaultProps"
								/>
							</el-form-item>
						</el-tab-pane>
						<el-tab-pane label="学生转班" name="second">
							<el-form-item label="所在班级">
								<el-input
									v-model="formGroup.clazz"
									class="input-with-select">
									<template #append>
									<el-select v-model="formGroup.toClazz"  style="width: 13px;;">
										<el-option
											v-for="item in options.classes"
											:key="item.value"
											:label="item.label"
											:value="item.value" />
									</el-select>
								</template>
									</el-input>
							</el-form-item>
							<el-form-item label="转到班级">
								<el-input v-model="formGroup.toClazz" >
								<template #append>
										<el-select v-model="formGroup.toClazz"  style="width: 13px;">
										<el-option
											v-for="item in options.classes"
											:key="item.value"
											:label="item.label"
											:value="item.value" />
									</el-select>
								
									
								</template>
								</el-input>
								
							</el-form-item>
							<el-form-item label="相关描述">
								<el-input v-model="formGroup.description"  type="textarea"></el-input>
							</el-form-item>
						</el-tab-pane>
  				</el-tabs>
				
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button @click="clsVisible = false">关闭</el-button>
					<el-button color="#0EB594" @click="saveGroup">保存</el-button>
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

const value = ref('')
const  name = ref()
const options = reactive({
	classes:[
	{
		value: '17软件1',
		label: '17软件1',
 	 },
  {
    value: '17软件2',
    label: '17软件2',
  },
  {
    value: '18软件1',
    label: '18软件1',
  },
  {
    value: '20软件1',
    label: '20软件2',
  },
  ],
  names:[{
    value: '张三',
    label: '张三',
 	 },
  {
    value: '李四',
    label: '李四',
  },
  {
    value: '王五',
    label: '王五',
  },
  {
    value: '赵六',
    label: '赵六',
  },],
  schools:[{
    value: '萍乡学院',
    label: '萍乡学院',
 	 },
  {
    value: '南昌大学',
    label: '南昌大学',
  },
  {
    value: '九江学院',
    label: '九江学院',
  },
  {
    value: '苏州大学',
    label: '苏州大学',
  },],
  colleges:[{
    value: '南京大学',
    label: '南京大学',
 	 },
  {
    value: '17软件2',
    label: '17软件2',
  },
  {
    value: '18软件1',
    label: '18软件1',
  },
  {
    value: '20软件1',
    label: '20软件2',
  },],
}
)
	
  

//
const  startTime = ref('')
const endTime = ref('')

//是否启用
const small = ref(false)
const size = ref<'default' | 'large' | 'small'>('default')

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


//是够启用调用后台接口
const  changeSwitch=(status)=>{
    //调用后台修改接口
    console.log(status,'');
}

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
	stuId:'',
    sex:'',
    phone:'',
    email:'',
    startUsing:'',
    lastLandingTime:''
});

// 
let titleEdit = ref('修改')
let isDisable = ref(true)
const isShow = ref(true)
const open  = (index,row)=>{//index是事件源对象
	if(row){
		titleEdit.value = "修改"
		isDisable.value=true
		isShow.value=true
		handleEdit(index,row)
	}else{
		//把第一个输出改为可编辑
		isDisable.value=false
		isShow.value= false
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
	form.stuId = row.stuId;
    form.sex = row.sex;
    form.phone = row.phone;
    form.email = row.email;
    form.startUsing = row.startUsing;
    form.lastLandingTime = row.lastLandingTime;
	editVisible.value = true;
};
//保存添加或者修改的数据 ---需要重新实现
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
//分配班级的的弹框
// import type Node from 'element-plus/es/components/tree/src/model/node'
const clsVisible = ref(false);
const formGroup  = reactive({
	userName:'',
	clazz:'',
	toClazz:'',
	description:''

})
const  treeRef = ref()

//分班回显
const groupEdit =(index,row)=>{
  console.log(index);
  formGroup.userName = row.userName
  clsVisible.value = true
	
}


import type { TabsPaneContext } from 'element-plus'
const activeName = ref('first')//获取活跃的
const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event,'ssss')

}

//保存分班/转班信息
const  saveGroup=()=>{
	//通过activeName活跃的选型 来确定保存的是分班信息信息 还是转班信息
	console.log(activeName.value,"活跃的选项");
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
	]
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

const list = [['序号','学号', '姓名','性别', '手机', '邮箱', '是否启用', '最后登录时间']];
const exportXlsx = () => {
	console.log('tablev',tableData.value)
    tableData.value.map((item: any, i: number) => {
        const arr: any[] = [i+1];
        arr.push(...[item.stuId,item.name, item.sex, item.phone, item.email, item.startUsing,item.lastLandingTime]);
        list.push(arr);
    });
    let WorkSheet = XLSX.utils.aoa_to_sheet(list);
    let new_workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(new_workbook, WorkSheet, '第一页');
    XLSX.writeFile(new_workbook, `表格.xlsx`);
};

//导入
const importXlsx = () => {
	console.log('===数据导入==========')
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
	width: 200px;
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