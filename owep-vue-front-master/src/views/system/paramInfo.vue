<template>
  <div class="wrapper wrapper-content animated fadeInRight">
    <div class="white-bg ">
        <div class="wrapper-content">
    <div class="row">
        <div class="col-md-12">
            <h2 class="page-header">
                
                <i class="el-icon-lx-global"></i>
                更新信息
               
            </h2>
            <el-divider />
        </div>
    </div>
    <el-row>
      <el-col :span="24">
        <div >
            1、美化登录页，修复登录页bug。2、更换系统主题并修改系统主页样式，运用Bootstrap为主题并以简约为主。3、添加更多的用户交互效果。4、修复安全漏洞。5、使管理简单化。
        </div>
    </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <div >
                欢迎使用信息管理系统，当前版本号：V1.13，最后更新时间：20170407000000
        </div>
    </el-col>
    </el-row>
   
    <div class="row">
        <!-- <div class="col-md-12"> -->
            <h2 class="page-header">
                <i class="el-icon-lx-global"></i>
                服务器基本信息
            </h2>
            <el-divider />
        <!-- </div> -->
    </div>
    <!--表格-->
    <div class="row">
        <div >
            <el-table :data="tableData" stripe style="width: 100%">
            <el-table-column prop="System" label="System:Windows NT 6.1build7601"  />
            <el-table-column prop="server" label="Web server:Apache/2.2.4 (Win32) PHP/5.2.0"  />
            <el-table-column prop="version" label="JAVA Version:5.2.0" />
        </el-table>
        </div>
    </div>
    <div class="row">
        <div >
            <h2 class="page-header">
                <i class="el-icon-lx-global"></i>
                统计人数
            </h2>
            <el-divider />
        </div>
    </div>
    <div class="row ">
        <!-- <div > -->
            访问人数：3174
            <br>
            在线人数：1
        <!-- </div> -->
    </div>
        </div>
    </div>

</div>



</template>

<script setup>
// import SvgIcon from '../../components/SvgIcon.vue';
const tableData = [
 /*  {
    System: 'System:Windows NT 6.1build7601',
    server: 'Web server:Apache/2.2.4 (Win32) PHP/5.2.0',
    version: 'JAVA Version:5.2.0',
  }, */
  {
    System: 'Mysql Version:5.0.27-community-nt',
    server: 'display_errors:YES',
    version: 'Server API:APACHE2HANDLER',
  },
  {
    System: 'JAVA Safe: YES',
    server: 'Session Support:YES',
    version: 'Cookie Support:YES',
  },
  {
    System: 'Zend Optimizer Support:NO',
    server: 'eAccelerator Support:NO',
    version: 'Xcache Support:NO',
  },
]
</script >
<style  scoped>

.row{
    /* margin-right: -15px;
    margin-left: -15px; */
    margin-top: 15px;
}
.wrapper-content {
    padding:10px;
    background-color: #fff;
    box-sizing: border-box;
    color: #676a6c;
    /* overflow-y: scroll; */
}

</style>