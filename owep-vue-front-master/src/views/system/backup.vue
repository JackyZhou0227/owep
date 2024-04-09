<template>
	<div>
		<div class="container">
			<div class="handle-box">
                开始时间：<el-date-picker v-model="startTime"
				  type="date" placeholder="开始时间" :size="size"/>
                结束时间：<el-date-picker    v-model="endTime"    type="date"
                    placeholder="结束时间"    :size="size"/>
				<el-button  style="margin-left: 10px;" :icon="Search" color="#1c84c0" @click="handleSearch">查询</el-button>
				<div class = "btn-group">
                <el-button type="danger"  @click="handleSearch">删除</el-button>
                <el-button color="#2388c5"  @click="handleSearch">备份</el-button>
				</div>
			</div>
			<el-table
                ref="multipleTableRef"
                :data="tableData"
                style="width: 100%"
                @selection-change="handleSelectionChange">
                <el-table-column  type="selection" width="30" />
				<el-table-column prop="filename" label="文件名"  ></el-table-column>
				<el-table-column prop="backtime" label="备份时间"></el-table-column>
				<el-table-column label="状态">
                    <template #default="scope">
						<el-tag
							:type="scope.row.status === '成功' ? 'success' : scope.row.status === '失败' ? 'danger' : ''">
							{{ scope.row.status }}
						</el-tag>
					</template>
                </el-table-column>
				<el-table-column label="操作" width="220" align="center" >
					<template #default="scope">
                        <div class="flex">
						<el-button    type="danger"  :icon="Delete"  @click="handleDelete(scope.$index)" v-permiss="1"></el-button>
                        <!-- <el-button text :icon="Edit" @click="handleEdit(scope.$index, scope.row)" v-permiss="15">下载</el-button> -->
						<el-button    color="#2388c5" :icon="Download"  v-permiss="1"></el-button>
					</div>
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
				<!-- <el-pagination
					background
					layout="total, prev, pager, next"
					:current-page="query.pageIndex"
					:page-size="query.pageSize"
					:total="pageTotal"
					@current-change="handlePageChange"
				></el-pagination> -->
			</div>
		</div>

		
	</div>
</template>

<script setup lang="ts" name="basetable">
import { ref, reactive } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Delete, Edit, Search, Download } from '@element-plus/icons-vue';
import { getFiles,getMenu} from '../../api/index';

const small = ref(false)
const size = ref<'default' | 'large' | 'small'>('default')
//开始时间
const startTime = ref('')
//结束时间
const  endTime  = ref('')
//分页数据
const query = reactive({
	pageIndex: 1,
	pageSize: 5
});
//表格数据
const tableData = ref();
//总页数
const pageTotal = ref(0);
const multipleTableRef = ref()
const multipleSelection = ref([])

// 获取表格数据
const getData = () => {	
    getFiles({pageCurrent:query.pageIndex,pageSize:query.pageSize}).then(res=>{
        console.log(res,"------")
        // tableData.value = res.data.
		let start  = (query.pageIndex-1)*query.pageSize;//开始位置
         let end =query.pageIndex*query.pageSize  ;//结束位置 数组的截取的时候 [start,end) suo所以不用-1
		 console.log(start,end,'开始----结束')
        tableData.value = res.data.slice(start,end);//
        pageTotal.value = res.data.length
    })
	
};
getData();
const handleSelectionChange = (val) => {
  multipleSelection.value = val
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
	getData();
};

// 删除操作
const handleDelete = (index) => {
	// 二次确认删除
	ElMessageBox.confirm('确定要删除吗？', '提示', {
		type: 'warning'
	})
		.then(() => {
			ElMessage.success('删除成功');
			tableData.value.splice(index, 1);
		})
		.catch(() => {});
};
// 查询操作
const handleSearch = () => {
	query.pageIndex = 1;
	getData();
};
//删除多个选项

//备份

// 表格编辑时弹窗和保存
// const editVisible = ref(false);
// let form = reactive({
// 	name: '',
// 	address: ''
// });
// let idx: number = -1;
// const handleEdit = (index: number, row: any) => {
// 	idx = index;
// 	form.name = row.name;
// 	form.address = row.address;
// 	editVisible.value = true;
// };
// const saveEdit = () => {
// 	editVisible.value = false;
// 	ElMessage.success(`修改第 ${idx + 1} 行成功`);
// 	tableData.value[idx].name = form.name;
// 	tableData.value[idx].address = form.address;
// };
</script>

<style scoped>
.handle-box {
	margin-bottom: 20px;
}

.handle-select {
	width: 120px;
}

.handle-input {
	width: 300px;
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
.btn-group{
	float: right;
}


</style>
