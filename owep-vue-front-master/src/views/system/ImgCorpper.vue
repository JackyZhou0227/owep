<template>
    <div style="width: 100%; height: 100%;">
      <el-row style="width: 100%; height: 100%;" gutter="24">
        <el-col :span="8">
        <!-- <VueCropper style="width: 100%; height: 100%;" ref="cropper" :img="option.img" :outputSize="option.outputSize" -->
        <VueCropper style="width: 100%; height: 100%;" ref="cropper" :img="option.img" :outputSize="option.outputSize"
          :outputType="option.outputType" :info="option.info" :canScale="option.canScale" :autoCrop="option.autoCrop"
          :autoCropWidth="option.autoCropWidth" :autoCropHeight="option.autoCropHeight" :fixedBox="option.fixedBox"
          :fixed="option.fixed" :fixedNumber="option.fixedNumber" :canMove="option.canMove" :canMoveBox="option.canMoveBox"
          :original="option.original" :centerBox="option.centerBox" :infoTrue="option.infoTrue" :full="option.full"
          :enlarge="option.enlarge" :mode="option.mode" @realTime="realTime">
        </VueCropper>
      </el-col> 
      
        <el-col :span="10">
          <div>
            <h3>图片预览：</h3>
            <div class="preview-box">
            <img :src="previews.url" :style="previews.img" />
            </div>
            <!-- <el-image :src="imgs.imgUrl" style="width: 100%;"></el-image> -->
          </div>
          <div  class="cropper-btn">
            <el-upload v-model:file-list="fileList" action="" :auto-upload="false" :show-file-list="false"
          :on-change="handleChange">
          <el-button type="primary">上传图片</el-button>
        </el-upload>
        <el-button type="success"   @click="getCropDataBase64" style="margin-left: 10px;">剪裁</el-button>
          </div>
          <div class="cropper-btn">
            <el-button type="primary" plain @click="rotateLarge">放大</el-button>
            <el-button type="primary" plain @click="rotateReduce">缩小</el-button>
            <el-button type="primary" plain @click="rotateLeft">左旋转</el-button>
            <el-button type="primary" plain @click="rotateRight">右旋转</el-button>
          </div>
            
          
        </el-col>
      </el-row>
      <!-- <el-row style="width: 100%; height: 60px; margin-top: 10px;"> -->
        <!-- 注意这里的action=""和 :auto-upload="false"，我把自动上传关了，免得点击上传图片的时候发送一次请求 -->
        <!-- <el-upload v-model:file-list="fileList" action="" :auto-upload="false" :show-file-list="false"
          :on-change="handleChange">
          <el-button type="primary">点击上传图片</el-button>
        </el-upload>
        <el-button type="primary" plain @click="rotateLeft">《=向左旋转图片</el-button>
        <el-button type="primary" plain @click="rotateRight">向有旋转图片=》</el-button>
        <el-button type="primary" @click="getCropDataBase64">获取截取的图片</el-button> -->
      <!-- </el-row> -->
  
     
      <!-- <div>
        展示截图的图片
      </div> -->
      <!-- <div style="width: 150px; height: 200px;">
          若图片只设置宽度，可以保持等比例展示图片
        <img :src="imgUrl" style="width: 100%;">
      </div> -->
    </div>
  </template>
  
<script setup lang="ts">
  let props = defineProps({
     imgUrl:{
      type:Object,
      default:{}
     }
})
const emit = defineEmits(['update:imgUrl'])
console.log(props.imgUrl,"props.imgUrl===")
//   import vueCropper from './VueCropper.ts'
  import { ref, reactive,watch } from 'vue';
  // import { ElMessage, ElMessageBox } from 'element-plus'
  import type { UploadProps, UploadFile } from 'element-plus'
  let imgs  = reactive({
    imgUrl:props.imgUrl
  }) 
  
  const cropper = ref()
  let option = reactive({
    img:props.imgUrl, // 裁剪图片的地址 url 地址, base64, blob
    outputSize: 1, // 裁剪生成图片的质量
    outputType: 'jpeg', // 裁剪生成图片的格式 jpeg, png, webp
    info: true, // 裁剪框的大小信息
    canScale: true, // 图片是否允许滚轮缩放
    autoCrop: true, // 是否默认生成截图框
    autoCropWidth: 150, // 默认生成截图框宽度
    autoCropHeight: 48, // 默认生成截图框高度
    fixedBox: false, // 固定截图框大小 不允许改变
    fixed: false, // 是否开启截图框宽高固定比例，这个如果设置为true，截图框会是固定比例缩放的，如果设置为false，则截图框的狂宽高比例就不固定了
    fixedNumber: [1, 1], // 截图框的宽高比例 [ 宽度 , 高度 ]
    canMove: true, // 上传图片是否可以移动
    canMoveBox: true, // 截图框能否拖动
    original: true, // 上传图片按照原始比例渲染
    centerBox: false, // 截图框是否被限制在图片里面
    infoTrue: true, // true 为展示真实输出图片宽高 false 展示看到的截图框宽高
    full: true, // 是否输出原图比例的截图
    enlarge: '1', // 图片根据截图框输出比例倍数
    mode: 'contain' // 图片默认渲染方式 contain , cover, 100px, 100% auto
  })


  const previews = ref({
    url: '',
    img:''
  })
 
watch(
	() => props.imgUrl,
	() => {
     option.img = props.imgUrl;
	},
	{ deep: true, immediate: true }
)


const realTime = (data: any) => {
  previews.value = data;
  console.log(data,props.imgUrl,'coppRealTime')
}


  //放大图片
  const  rotateLarge=()=>{
     cropper.value.changeScale(1)
  }
  //缩小图片
  const rotateReduce=()=>{
    cropper.value.changeScale(-1)
  }

  // 向左旋转图片
  const rotateLeft = () => {
    // 这个默认是旋转90度的，官网有说明
    // 后面会完善不要让这个一下子旋转90度的代码
    cropper.value.rotateLeft()
  }
  
  // 向右旋转
  const rotateRight = () => {
    // 这个默认也是旋转90度的，官网有说明
    // 后面会完善不要让这个一下子旋转90度的代码
    cropper.value.rotateRight()
  }
  // let imgUrl = ref('')
 

  // 获取图片
  const getCropDataBase64 = () => {
    // 这个是获取base64的图片
    cropper.value.getCropData((data: any) => {
      console.log(data,"<<<<<data")
      // do something
      // 如这里，可以获取上传base64位的图片给服务器
      // 也可以将base64位的图片转化为file文件，然后通过form表单的形式提交给后端，让后端返回一个图片的访问地址等
      // 这里就简单把截取到的图片展示一下吧，这里就暂时不上传给后端了
      // imgUrl.value = data
       imgs.imgUrl = data
      
    })
  }
 
  const fileList = ref([])
  // el-upload的change事件，具体看element-plus的官网
  const handleChange: UploadProps['onChange'] = (
    uploadFile: UploadFile
  ) => {
    // URL.createObjectURL(uploadFile.raw!)，将图片变成blob的数据，类似于blob:http://127.0.0.1:5173/dbaf505d-df43-4448-809d-8daadf3e883c
    option.img = URL.createObjectURL(uploadFile.raw!)
  }
  
  </script>
  
  <style scoped>
   .cropper-btn{
    display:inline-flex;
    margin-top: 10px;
   }

   .cropper_preview {
    margin: 0 auto;
    display: inline-block;
    /* border: 1px solid #ddd; */
  }

.preview-box {
  /* position: absolute; */
 padding-top: 10px;
 padding-bottom: 10px;
  /* transform: translate(50%, -50%); */
  width: 155px;
  height: 48px;
  /* border-radius: 50%; */
  /* border: 1px solid #ccc; */
  overflow: hidden;
}

</style>
  
  