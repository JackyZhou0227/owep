<template>
	<div>
    <div class="container">
		<div class="handle-box">
			<label class="label" >专业方向：</label>
			<el-input class="handle-input" v-model="majorName"/>
			<label class="label" >所属机构：</label>
			<el-select
			  v-model="value" 
				filterable
				allow-create
			   >
				<el-option
					v-for="item in options.organizations"
					:key="item.value"
					:label="item.label"
					:value="item.value"
					/>
			</el-select>
			<label class="label" >所属分支：</label>
			<el-select
			 	v-model="value1" 
				filterable
				allow-create >
					<el-option
					v-for="item in options.branch"
					:key="item.value"
					:label="item.label"
					:value="item.value"
					/>
				</el-select>
				
				<el-button color="#0ab394" class="handle-btn" :icon="Search" @click="handleSearch">查询</el-button>
				<el-row class="top-btn">
     			<!-- <el-button-group class="ml-4"> -->
                    <el-button color="#0ab394"  @click="open">添加</el-button> 
                    <el-button color="#e35148" @click="handleAllDelete">删除</el-button>                         
       		 <!-- </el-button-group> -->
			</el-row>
			</div>
			<el-table ref="multipleTableRef" 
				:header-cell-style="{color:'#2A62BC'}"
			 	:data="tableData"   
			 	style="width: 100%;" 
			 	height="380"
                size="small"
                border
				@selection-change="handleSelectionChange">
                <el-table-column   type="selection" width="35" />
				<el-table-column  prop="name" label="专业名称"  ></el-table-column>
				<el-table-column prop="org" label="所属机构"></el-table-column>
                <el-table-column prop="branch" label="所属分支"></el-table-column>
				<el-table-column label="是否启用">
                    <template #default="scope">
                        <el-switch
                                v-model="scope.row.isUse"
                                :active-value="0"
                                :inactive-value="1"
                                active-color="#64bd63"
                                inactive-color="#c0c0c0"
                                
                                @change="changeSwitch(scope.row.isUse)"/>
					</template>
                </el-table-column>
				<el-table-column label="操作" width="220" align="center" >
					<template #default="scope">
                        <!-- <el-button-group class="ml-4"> -->
							<el-row class="right-btn">
                        <!-- <el-button text :icon="Edit" @click="handleEdit(scope.$index,scope.row)" v-permiss="2"></el-button> -->
						
						    <el-button color="#0ab394" :icon="Edit" @click="open(scope.$index,scope.row)" v-permiss="2">修改</el-button>
				            <el-button color="#e35148" :icon="Delete"  @click="handleDelete(scope.$index)" v-permiss="2">删除</el-button>
						</el-row>
						<!-- </el-button-group> -->
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
	</div>

		<!-- 修改/添加弹出框 -->
		<el-dialog :title="titleEdit" v-model="editVisible" width="30%" >
			<el-form label-width="70px">
				<el-form-item label="专业名称" disabled >
					<el-input v-model="form.name"  :disabled="isDisable" ></el-input>
				</el-form-item>
				<el-form-item label="所属机构">
                 <el-select v-model="form.org" clearable >
                    <el-option
                        v-for="item in options.organizations"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        />
			    </el-select>
				</el-form-item>
                <el-form-item label="所属分支">
                  <el-select v-model="form.branch" clearable >
					<el-option
					v-for="item in options.branch"
					:key="item.value"
					:label="item.label"
					:value="item.value"
					/>
				</el-select>
				</el-form-item>

                <el-form-item label="是否启用" v-if="titleEdit=='添加'">
					<el-switch
                                v-model="form.isUse"
                                :active-value="0"
                                :inactive-value="1"
                                active-color="#64bd63"
                                inactive-color="#c0c0c0"/>	
				</el-form-item>

                <el-form-item label="描述">
					<el-input v-model="form.description"  type="textarea" 
                    placeholder="乐码学院,JavaEE企业级开发人才培养。"></el-input>
				</el-form-item>
              
			</el-form>
			<template #footer>
				<span class="dialog-footer">
					<el-button color="#0ab394" @click="editVisible = false">关闭</el-button>
					<el-button color="#0ab394" @click="saveEdit">确 定</el-button>
				</span>
			</template>
		</el-dialog>

		<!-- </div> -->
	</div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Delete, Edit, Search, Plus } from '@element-plus/icons-vue';
import { getMajors} from '../../api/clazz' ;

const majorName = ref()
const value = ref()
const value1 = ref()

const options = reactive({
organizations:[
	{
		value: '天龙八部',
		label: '天龙八部',
 	 },
  {
    value: '神雕侠侣',
    label: '神雕侠侣',
  },
  {
    value: '仙剑奇侠传',
    label: '仙剑奇侠传',
  },
  {
    value: 'flyme',
    label: 'flyme',
  },
  ],
  branch:[{
    value: '英语',
    label: '英语',
 	 },
  {
    value: '书法',
    label: '书法',
  },
  {
    value: '钢琴',
    label: '钢琴',
  },
  {
    value: 'flyme',
    label: 'flyme',
  },],
  
}
)
	
//分页字体
const small = ref(true)
// const size = ref<'default' | 'large' | 'small'>('default')


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
    getMajors({pageCurrent:query.pageIndex,pageSize:query.pageSize}).then(res=>{
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
    console.log(status,'>>>status');
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
	name: '',
    description:'',
    org:'',
    branch:'',
    isUse:''
});

// 
let titleEdit = ref('修改')
let isDisable = ref(true)
const open  = (index,row)=>{//index是时间源对象
	if(row){
		titleEdit.value = "修改"
		handleEdit(index,row)
	}else{
		//把第一个输出改为可编辑
		isDisable.value=false
		//清空表单中的数据
		titleEdit.value = "添加";
		form.name=''
		form.org=''
		form.branch=''
		form.description=''
		editVisible.value = true	
	}
	console.log('-------测试传值-------')
	// console.log(index,row,index.target.innerText)
	// console.log(index,row)
	// console.log('================')

}
let idx = -1;
const handleEdit = (index,row) => {
	idx = index;
    form.id = row.id;
	form.name = row.name;
    form.org = row.org
    form.branch = row.branch
	editVisible.value = true;
};
//保存添加或者修改的数据 ---需要重新实现
const saveEdit = () => {
	editVisible.value = false;
	ElMessage.success(`修改第 ${idx + 1} 行成功`);
	tableData.value[idx].name = form.name;
	tableData.value[idx].org = form.org;
    tableData.value[idx].branch = form.branch;
};

</script >
<style lang="less" scoped>
.handle-box {
	margin-bottom: 20px;   
	.handle-btn{
		margin-left: 10px;
	}
}

.el-select {
	width: 200px;
	// margin-right: 10px;  
}

.handle-input {
	width: 200px;
    margin-right: 10px;
}
.label{
   display: inline-block;
   max-width:100%;
   margin: 5px;
   font-weight: 700; 
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
.top-btn{
	float: right;
	.el-button{
		margin-left: 5px;
	}
}
.right-btn{
 	justify-content:center;
	.el-button{
		height: 22px;
		width: 60px;
		margin-right: 10px;
		padding: 1px 5px;
	}
}


</style>