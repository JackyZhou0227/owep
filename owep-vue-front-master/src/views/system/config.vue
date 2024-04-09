<template>
  <div class="wrapper wrapper-content  animated fadeInRight">
 <el-row>
   <el-col>
     <h2 class="page-header">
       <i class="el-icon-lx-global"></i>
       基本信息
     </h2>
   </el-col>
 </el-row>
 <el-divider />
 <el-row :gutter="20">
   <el-col :xs="8" :sm="6" :md="4" :lg="3" :xl="2">
     <div class="grid-content ep-bg-purple">
       <label class="col-md-2 wrapper " style="font-weight:bold; margin-top: 5px;">系统名称:</label>
     </div></el-col>
   <el-col :xs="8" :sm="6" :md="8" :lg="9" :xl="10">
     <div class="grid-content ep-bg-purple-light">
       <el-input v-model="input1" class="w-50 m-2" placeholder="Please Input" />
     </div></el-col>
 </el-row>
 <el-row :gutter="20">
   <el-col :xs="8" :sm="6" :md="4" :lg="3" :xl="2">
     <div class="grid-content ep-bg-purple">
       <label class="col-md-2 wrapper " style="font-weight:bold; margin-top: 5px;">系统信息:</label>
     </div></el-col>
   <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="10">
     <div class="grid-content ep-bg-purple-light">
       <el-input v-model="input2" class="w-50 m-2" placeholder="Please Input" />
     </div></el-col>
 </el-row>
 <!--保存按钮-->
 <el-row :gutter="10">
   <el-col :xs="8" :sm="6" :md="4" :lg="3" :xl="1">
     <div class="row wrapper">
         <el-button color="#1c84c0"  round id="save" name="save"  @click="saveBase">保存</el-button>
     </div>
     </el-col>
     </el-row>
 <!-- <div class="form-group">
   <div class="row wrapper">
     <el-button type="primary"  round id="save" name="save" >保存</el-button>
   </div>
 </div> -->
 <el-row :gutter="20">
   <el-col :xs="8" :sm="6" :md="4" :lg="3" :xl="2">
     <div class="grid-content ep-bg-purple">
       <label class="col-md-2 wrapper " style="margin-top: 5px;">登录页Logo:</label>
     </div></el-col>
   <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="10">
     <div class="grid-content ep-bg-purple-light">
       <!-- <el-button color="#0eb594" class="w-50 m-2" @click="dialogVisible=true">图片剪裁</el-button> -->
       <el-button type="success" class="w-50 m-2" @click="imgCut(1)">图片剪裁</el-button>
     </div>
   </el-col>
 </el-row>
 <div class="row">
    <div class="col-md-2" style="background-color: #fff">
        <!-- <img src="../../static/img/getImage.png" id="loginLogo"  width="200" height="50" > -->
        <el-image style="width: 265px; height: 85px" :src="imgUrl1"  fit="scale-down"/>
     </div>
  </div>


  <el-row :gutter="20">
   <el-col :xs="8" :sm="6" :md="4" :lg="3" :xl="2">
     <div class="grid-content ep-bg-purple">
       <label class="col-md-2 wrapper " style="margin-top: 5px;">系统页Logo:</label>
     </div></el-col>
   <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="10">
     <div class="grid-content ep-bg-purple-light">
       <!-- <el-button color="#0eb594" class="w-50 m-2" @click="dialogVisible=true">图片剪裁</el-button> -->
       <el-button type="success" class="w-50 m-2" @click="imgCut(2)">图片剪裁</el-button>
     </div>
   </el-col>
 </el-row>
 <div class="row">
    <div class="col-md-2" style="background-color: #fff">
        <!-- <img src="../../static/img/getImage.png" id="loginLogo"  width="200" height="50" > -->
        <el-image style="width: 265px; height: 85px" :src="imgUrl2"  fit="scale-down"/>
     </div>
  </div>

  <el-dialog
   v-model="dialogVisible"
   title="图片剪裁"
   width="600px"
   height="367px"
   :before-close="handleClose">
 <img-corpper v-model:imgUrl="imgUrl" ></img-corpper>
 <!-- <el-divider /> -->
 <template #footer>
   <span class="dialog-footer">
     <el-button @click="dialogVisible = false">关闭</el-button>
     <el-button type="primary" @click="dialogVisible = false">
      保存
     </el-button>
   </span>
 </template>
</el-dialog>
</div>
</template>

<script setup>
import { ref,computed,reactive} from 'vue'
import imgUrl1  from '../../assets/img/getImage.png'
import imgUrl2  from '../../assets/img/sysImage.png'
const input1 = ref('苏州青英培训学校(乐码学院)')
const input2 = ref('苏州青英培训学校(乐码学院)')
import ImgCorpper from './ImgCorpper.vue';
const dialogVisible = ref(false)
let  imgUrl = ref('')
function imgCut(data){
console.log('data:',data)
if(data==1){
 imgUrl.value = imgUrl1
}else{
 imgUrl.value = imgUrl2
}
dialogVisible.value = true
}
/**保存按钮----- */
const saveBase = ()=>{
    ElMessageBox.alert(
      `<p>系统名称:${input1.value}</p> <p>版权信息:${input2.value}</p>`,
      '127.0.0.1:5173 显示', 
      {
        dangerouslyUseHTMLString:true,
        // center:true,
        confirmButtonText: '确定',
    })
}
</script>
<style lang="less" scoped>
.el-row{
margin-right: -15px;
margin-left: -15px;
margin-top: 15px;
}
.col-md-2{
width: 16.66666667%;
}
.row{
margin-right: -15px;
margin-left: -15px;
margin-top: 15px;
}
</style>