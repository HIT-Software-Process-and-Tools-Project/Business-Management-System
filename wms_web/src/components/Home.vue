<template>
    <div style="text-align: center;background-color: #f1f1f3;height: 100%;padding: 0px;margin: 0px;">
        <h1 style="font-size: 30px;">欢迎使用本系统！</h1>
        <el-descriptions  title="个人中心" :column="2" size="40" border>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-s-custom"></i>
                    账号
                </template>
                {{user.no}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    电话
                </template>
                {{user.phone}}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-location-outline"></i>
                    性别
                </template>
                <el-tag
                        :type="user.sex === '1' ? 'primary' : 'danger'"
                        disable-transitions><i :class="user.sex==1?'el-icon-male':'el-icon-female'"></i>{{user.sex==1?"男":"女"}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-tickets"></i>
                    角色
                </template>
              <el-tag
                  :type="user.roleId === 0 ? 'danger' : (user.roleId === 1 ? 'primary' : (user.roleId === 2 ? 'warning' : 'success'))"
                  disable-transitions>{{user.roleId === 0 ? '经理' :
                  (user.roleId === 1 ? '仓库管理员' :
                      (user.roleId === 2 ? '售货员' :
                          (user.roleId === 3 ? '批发客户' : '零售客户')))}}</el-tag>

            </el-descriptions-item>
        </el-descriptions>
      <h1 style="font-size: 30px;">&emsp;</h1>
      <h1 style="font-size: 25px;">个人销售统计&emsp;&emsp;</h1>
      <h2 style="font-size: 20px;" >销售总额占比&emsp;&emsp;&nbsp;&nbsp;客户数量占比&emsp;&emsp;&nbsp;&nbsp;销售数量占比&emsp;&emsp;&nbsp;&nbsp;</h2>
      <template>
        <div class="demo-progress">
          <el-progress type="circle" :percentage=form.records color="#67C23A"/>
          <el-progress type="circle" :percentage=form.customers color="#409EFF"/>
          <el-progress type="circle" :percentage=form.counts color="#E6A23C"/>
        </div>
      </template>


        <DateUtils></DateUtils>
    </div>
</template>


<script>
    import DateUtils from "./DateUtils";
    export default {
        name: "Home",
        components: {DateUtils},
        data() {

            return {
                user:{},
                form:{
                  records:10.23,
                  customers:'',
                  counts:''
                }
            }
        },
        computed:{

        },
        methods:{
            init(){
                this.user = JSON.parse(sessionStorage.getItem('CurUser'))
            },
            loadPost(){
              this.$axios.post(this.$httpUrl+'/record/percent',{id:this.user.id}).then(res=>res.data).then(res=>{
                console.log(res)
                this.form.records=res[0];
                this.form.customers=res[1];
                this.form.counts=res[2];
              })
            }
        },
        created(){
            this.init();
            this.loadPost()
        }
    }
</script>

<style scoped>
    .el-descriptions{
        width:90%;

        margin: 0 auto;
        text-align: center;
    }
    .demo-progress .el-progress--line {
      margin-bottom: 15px;
      width: 350px;
    }
    .demo-progress .el-progress--circle {
      margin-right: 50px;
    }
</style>