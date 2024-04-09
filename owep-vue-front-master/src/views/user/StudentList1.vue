<template>
	<div>
		<!-- <div class="container"> -->
			<!-- <div class="handle-box"> -->
                    <el-tabs type="border-card">
                      <el-tab-pane label="全部学生信息">
                         <AllStudentMsg></AllStudentMsg>
                      </el-tab-pane>
                      <el-tab-pane label="未签约的学生信息">
                         <AllUnsignedStudentMsg></AllUnsignedStudentMsg>
                      </el-tab-pane>
                    </el-tabs>
                <!-- </div> -->
		<!-- </div> -->

		
		
	</div>
</template>

<script setup lang="ts" name="studentList">
import AllStudentMsg from './AllStudentMsg.vue'
import AllUnsignedStudentMsg from './AllUnsignedStudentMsg.vue'


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
