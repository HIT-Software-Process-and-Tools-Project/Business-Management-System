<template>
  <div>
    <div style="text-align: center;background-color: #f1f1f3;height: 100%;padding: 0px;margin: 0px;">
      <h1 style="font-size: 30px;">欢迎使用本系统！</h1>

      <el-descriptions title="个人中心" :column="2" size="40" border>
        <template #extra>
          <el-button size="large" type="success" @click="mod()">编辑个人信息</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-custom"></i>
            账号
          </template>
          {{ user.no }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            电话
          </template>
          {{ user.phone }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-location-outline"></i>
            性别
          </template>
          <el-tag
              :type="user.sex === '1' ? 'primary' : 'danger'"
              disable-transitions><i
              :class="user.sex==1?'el-icon-male':'el-icon-female'"></i>{{ user.sex == 1 ? "男" : "女" }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-tickets"></i>
            角色
          </template>
          <el-tag
              :type="user.roleId === 0 ? 'danger' : (user.roleId === 1 ? 'primary' : (user.roleId === 2 ? 'warning' : 'success'))"
              disable-transitions>{{
              user.roleId === 0 ? '经理' :
                  (user.roleId === 1 ? '仓库管理员' :
                      (user.roleId === 2 ? '售货员' :
                          (user.roleId === 3 ? '批发客户' : '零售客户')))
            }}
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>


      <h1 style="font-size: 30px;">&emsp;</h1>
      <h1 style="font-size: 25px;">个人销售统计&emsp;&emsp;</h1>
      <h2 style="font-size: 20px;" >销售总额占比&emsp;&emsp;&nbsp;&nbsp;客户数量占比&emsp;&emsp;&nbsp;&nbsp;销售数量占比&emsp;&emsp;&nbsp;&nbsp;</h2>
      <template>
        <div class="demo-progress">
          <el-progress type="circle" :percentage=form1.records color="#67C23A"/>
          <el-progress type="circle" :percentage=form1.customers color="#409EFF"/>
          <el-progress type="circle" :percentage=form1.counts color="#E6A23C"/>
        </div>
      </template>

      <DateUtils></DateUtils>
    </div>

    <div style="text-align: center">
      <el-dialog
          title="提示"
          :visible.sync="centerDialogVisible"
          width="30%"
          center>

        <el-form ref="form" :rules="rules" :model="user" label-width="80px">
          <el-form-item label="账号" prop="no">
            <el-col :span="20">
              <el-input v-model="user.no"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="名字" prop="name">
            <el-col :span="20">
              <el-input v-model="user.name"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-col :span="20">
              <el-input v-model="user.password"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="年龄" prop="age">
            <el-col :span="20">
              <el-input v-model="user.age"></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="user.sex">
              <el-radio label="1">男</el-radio>
              <el-radio label="0">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-col :span="20">
              <el-input v-model="user.phone"></el-input>
            </el-col>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
      </el-dialog>
    </div>
  </div>
</template>


<script>
import DateUtils from "./DateUtils";

export default {
  name: "Home",
  components: {DateUtils},
  data() {
    /*let checkAge = (rule, value, callback) => {
      if(value>150){
        callback(new Error('年龄输入过大'));
      }else{
        callback();
      }
    };
    let checkDuplicate =(rule,value,callback)=>{
      if(this.form.id){
        return callback();
      }
      this.$axios.get(this.$httpUrl+"/user/findByNo?no="+this.form.no).then(res=>res.data).then(res=>{
        if(res.code!=200){
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

          callback()
        }else{
          callback(new Error('账号已经存在'));
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
      })
    };*/
    let checkAge = (rule, value, callback) => {
      if(value>150){
        callback(new Error('年龄输入过大'));
      }else{
        callback();
      }
    };
    let checkDuplicate =(rule,value,callback)=>{
      if(this.form.id){
        return callback();
      }
      this.$axios.get(this.$httpUrl+"/user/findByNo?no="+this.form.no).then(res=>res.data).then(res=>{
        if(res.code!=200){

          callback()
        }else{
          callback(new Error('账号已经存在'));
        }
      })
    };

    return {
      user:{},
      form1:{
        records:10.23,
        customers:'',
        counts:''
      },
      tableData: [],
      pageSize:10,
      pageNum:1,
      total:0,
      name:'',
      sex:'',
      sexs:[
        {
          value: '1',
          label: '男'
        }, {
          value: '0',
          label: '女'
        }
      ],
      centerDialogVisible:false,
      form:{
        id:'',
        no:'',
        name:'',
        password:'',
        age:'',
        phone:'',
        sex:'0',
        roleId:'1'
      },
      rules: {
        no: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'},
          {validator:checkDuplicate,trigger: 'blur'}
        ],
        name: [
          {required: true, message: '请输入名字', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur'}
        ],
        age: [
          {required: true, message: '请输入年龄', trigger: 'blur'},
          {min: 1, max: 3, message: '长度在 1 到 3 个位', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,3}$/,message: '年龄必须为正整数字',trigger: "blur"},
          {validator:checkAge,trigger: 'blur'}
        ],
        phone: [
          {required: true,message: "手机号不能为空",trigger: "blur"},
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur"}
        ]
      }
    }
  },
  computed: {},
  methods: {
    init() {
      this.user = JSON.parse(sessionStorage.getItem('CurUser'))
    },
    mod() {
      this.centerDialogVisible = true
    },
    save(){
      /*this.$refs.user.validate((valid) => {
        if (valid) {
          if(this.user.id){
            this.doMod();
          }else{
            this.doSave();
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });*/
      this.doMod()
    },
    doSave(){
      this.$axios.post(this.$httpUrl+'/user/save',this.user).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){

          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
          this. resetForm()
        }else{
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }

      })
    },
    doMod(){
      this.$axios.post(this.$httpUrl+'/user/update',this.user).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){

          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.centerDialogVisible = false
          this.loadPost()
          this. resetForm()
        }else{
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }

      })
    },

    loadPost(){
      this.$axios.post(this.$httpUrl+'/record/percent',{id:this.user.id}).then(res=>res.data).then(res=>{
        console.log(res)
        this.form1.records=res[0];
        this.form1.customers=res[1];
        this.form1.counts=res[2];
      })
    }
  },
  created() {
    this.init()
    this.loadPost()
  },


}
</script>

<style scoped>
.el-descriptions {
  width: 90%;

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