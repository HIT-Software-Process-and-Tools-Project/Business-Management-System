<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="name" placeholder="请输入物品名" suffix-icon="el-icon-search" style="width: 200px;"
                @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="storage" placeholder="请选择仓库" style="margin-left: 5px;">
        <el-option
            v-for="item in storageData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="goodstype" placeholder="请选择分类" style="margin-left: 5px;">
        <el-option
            v-for="item in goodstypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="state" placeholder="请选择订单类型" style="margin-left: 5px;">
        <el-option
            v-for="item in states"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" style="margin-left: 5px;" @click="resetParam">重置</el-button>
    </div>

    <el-table :data="tableData"
              :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
              border
              show-summary
              :summary-method="getSummaries"
    >
      <el-table-column prop="id" sortable label="订单ID" width="90">
      </el-table-column>
      <el-table-column prop="iswholesale" label="订单类型" width="90">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.iswholesale == 0 ? 'danger' : 'success'"
              disable-transitions>{{scope.row.iswholesale == 0 ? '零售订单' : '批发订单'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="goodsname" label="物品名" width="80">
      </el-table-column>
<!--      <el-table-column prop="storagename" label="仓库" width="120">-->
<!--      </el-table-column>-->
<!--      <el-table-column prop="goodstypename" label="分类" width="80">-->
<!--      </el-table-column>-->
      <el-table-column prop="username" label="客户" width="80">
      </el-table-column>
      <el-table-column prop="adminname" label="操作人" width="90">
      </el-table-column>
<!--      <el-table-column prop="username" label="申请人" width="90">-->
<!--      </el-table-column>-->
      <el-table-column prop="count" label="数量" width="90">
      </el-table-column>
      <el-table-column prop="totalprice" label="总金额" width="70">
      </el-table-column>
      <el-table-column prop="profit" sortable label="毛利润" width="90">
      </el-table-column>
      <el-table-column prop="createtime" sortable label="创建订单时间" width="160">
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="200">
      </el-table-column>
      <el-table-column fixed="right" prop="state" label="状态"  width="75">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.state == 0 ? 'warning' : (scope.row.state == 1 ? 'primary' : (scope.row.state == 2 ? 'info' : (scope.row.state == 3 ? 'danger' : 'success')))"
              disable-transitions>{{scope.row.state == 0 ? '已保存' :
              (scope.row.state == 1 ? '已审核' :
                  (scope.row.state == 2 ? '已收货' :
                      (scope.row.state == 3 ? '已退货' :'已进货')))}}</el-tag>
        </template>

      </el-table-column>
      <el-table-column fixed="right" label="操作" width="330">
        <template  slot-scope="scope">
<!--          <el-button type="info" style="margin-left: 5px;" size="small" @click="mod(scope.row,0)">详情</el-button>-->
          <el-button type="primary" style="margin-left: 5px;" size="small" @click="mod(scope.row,0)">审核</el-button>
          <el-button type="success" style="margin-left: 5px;" size="small" @click="rec(scope.row,1)">收款</el-button>
          <el-button type="warning" style="margin-left: 5px;" size="small" @click="mod(scope.row,2)">退货</el-button>
          <!--                <el-button type="text" size="small" @click="del(scope.row.id)">删除</el-button>-->
          <el-button slot="reference" size="small" type="danger" style="margin-left: 5px;" @click="del(scope.row,0)">删除</el-button>
<!--            <el-popconfirm
                @click="del(scope.row,0)"
              title="确定删除吗？"
              @confirm="del(scope.row.id)"
            >

            </el-popconfirm>-->

        </template>
      </el-table-column>
    </el-table>

    <el-pagination style="text-align:right"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 20,30]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

    <el-dialog
        title="收银台"
        :visible.sync="recDialogVisible"
        width="30%"
        center>

      <el-form ref="form" :rules="rules" :model="form1" label-width="100px">

        <el-form-item label="客户应付款">
          <el-col :span="20">
            <el-input v-model="form.totalprice"
                      :disabled="true"></el-input>
          </el-col>
        </el-form-item>
<!--        <el-form-item label="客户实付款">
          <el-col :span="20">
            <el-input v-model="form1.totalprice"></el-input>
          </el-col>
        </el-form-item>-->

      </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="recDialogVisible = false">关 闭</el-button>
          <el-button type="success" @click=doRec>付 款</el-button>
          <el-button type="danger" @click=unRec>赊 账</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "RecordManage",
  data() {

    return {
      user : JSON.parse(sessionStorage.getItem('CurUser')),
      storageData:[],
      goodstypeData:[],
      stateData:[],
      states:[
        {
          value: '0',
          label: '已保存'
        }, {
          value: '1',
          label: '已审核'
        },{
          value: '2',
          label: '已收款'
        },{
          value: '3',
          label: '已退货'
        },{
          value: '4',
          label: '已进货'
        }
      ],
      tableData: [],
      pageSize:10,
      pageNum:1,
      total:0,
      name:'',
      storage:'',
      goodstype:'',
      state:'',
      centerDialogVisible:false,
      recDialogVisible:false,
      form:{
        id:'',
        iswholesale:'',
        name:'',
        storage:'',
        goodstype:'',
        count:'',
        remark:'',
        state:'',
        totalprice:'',
        profit:'',
        userid:''
      },
      form1:{
        totalprice: ''
      }
    }
  },
  methods:{
    mod(row,i){
      if(row.state!=i){
        this.$message({
          message: '不在该销售阶段',
          type: 'error'
        });
      }
      else{
        this.centerDialogVisible = true
        this.$nextTick(()=>{
          row.state=i+1
          this.form.id=row.id;
          this.form.name=row.name;
          this.form.storge=row.storge;
          this.form.goodstype=row.goodstype;
          this.form.count=row.count;
          this.form.remark=row.remark;
          this.form.state=row.state;
          this.form.totalprice=row.totalprice;
          this.form.profit=row.profit;
        })
        this.$nextTick(()=>{
          this.$axios.post(this.$httpUrl+'/record/update',this.form).then(res=>res.data).then(res=>{
            console.log(res)
            if(res.code==200){
              this.$message({
                message: '操作成功！',
                type: 'success'
              });
              this.centerDialogVisible = false
              this.loadPost()
              this.resetForm()
            }else{
              this.$message({
                message: '操作失败！',
                type: 'error'
              });
            }
          })
        })
      }
    },
    rec(row,i){
      if(row.state!=i){
        this.$message({
          message: '不在该销售阶段',
          type: 'error'
        });
      }
      else{
        //this.centerDialogVisible = true
        this.recDialogVisible=true
        this.$nextTick(()=>{
          this.form.id=row.id;
          this.form.name=row.name;
          this.form.storge=row.storge;
          this.form.goodstype=row.goodstype;
          this.form.count=row.count;
          this.form.remark=row.remark;
          this.form.state=row.state;
          this.form.totalprice=row.totalprice;
          this.form.profit=row.profit;
          this.form.userid=row.userid
        })

      }
    },
    doRec(){
      this.form.remark=this.form.totalprice/10
      this.form.state=2;
      this.$nextTick(()=>{
        this.$axios.post(this.$httpUrl+'/record/update1',this.form).then(res=>res.data).then(res=>{
          console.log(res)
          if(res.code==200){
            this.$message({
              message: '操作成功！',
              type: 'success'
            });
            this.loadPost()
            this.resetForm()
          }else{
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }
        })
      })
      this.recDialogVisible = false
    },
    unRec(){
      this.form.remark=this.form.totalprice
      if(this.form.userid==null){
        this.form.state=1;
        this.$message({
          message: '当前用户非会员，不可赊账！',
          type: 'error'
        });
        this.loadPost()
        this.resetForm()
      }
      else{
        this.form.state=2;
        this.$nextTick(()=>{
          this.$axios.post(this.$httpUrl+'/record/update2',this.form).then(res=>res.data).then(res=>{
            console.log(res)
            if(res.code==200){
              this.$message({
                message: '操作成功！',
                type: 'success'
              });
              this.loadPost()
              this.resetForm()
            }else{
              this.$message({
                message: '操作失败！',
                type: 'error'
              });
            }
          })
        })
      }
      this.recDialogVisible = false
    },
    del(id,i){
      console.log(id,i)
      if(id.state!=i){
        this.$message({
          message: '该销售阶段无法删除',
          type: 'error'
        });
      }
      else{
        this.$confirm('确定删除吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({type: 'success',message: '删除成功!'});
        }).catch(() => {
          this.$message({type: 'info',message: '已取消删除' });
        })
        this.$axios.get(this.$httpUrl+'/record/del?id='+id.id).then(res=>res.data).then(res=>{
          console.log(res)
          if(res.code==200){

            this.$message({
              message: '操作成功！',
              type: 'success'
            });
            this.loadPost()
          }else{
            this.$message({
              message: '操作失败！',
              type: 'error'
            });
          }

        })
      }

    },
    getSummaries(param) {
      const { columns, data } = param;//这里可以看出，自定义函数会传入每一列，以及数据
      const sums = [];
      columns.forEach((column, index) => {//遍历每一列
        if (index === 0) {
          sums[index] = "合计";//第一列显示 合计
          return;
        }
        if (index >= 5 && index <= 7) {
          const values = data.map(item =>//遍历每一行数据，得到相应列的所有数据形成一个新数组
              Number(item[column.property])
          );
          if (!values.every(value => isNaN(value))) {//这里是遍历得到的每一列的值，然后进行计算
            sums[index] = values.reduce((prev, curr) => {
              const value = Number(curr);
              if (!isNaN(value)) {
                return prev + curr;
              } else {
                return prev;
              }
            }, 0);
            if(index==5){
              sums[index] = sums[index]+"（个）";
            }
            else{
              sums[index] = "¥ "+sums[index];//可以在合计后的值后面加上相应的单位
            }
          } else {
            sums[index] = "";//如果列的值有一项不是数字，就显示这个自定义内容
          }
        } else {
          sums[index] = "";//其他列显示这个自定义内容
        }
      });
      return sums;//最后返回合计行的数据
    },
    formatStorage(row){
      let temp =  this.storageData.find(item=>{
        return item.id == row.storage
      })

      return temp && temp.name
    },
    formatGoodstype(row){
      let temp =  this.goodstypeData.find(item=>{
        return item.id == row.goodstype
      })

      return temp && temp.name
    },
    formatState(row){
      let temp =  this.stateData.find(item=>{
        return item.id == row.state
      })

      return temp && temp.name
    },
    resetForm() {
      this.$refs.form.resetFields();
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum=1
      this.pageSize=val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val
      this.loadPost()
    },
    resetParam(){
      this.name=''
      this.storage=''
      this.goodstype=''
      this.state=''
    },
    loadStorage(){
      this.$axios.get(this.$httpUrl+'/storage/list').then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.storageData=res.data
        }else{
          alert('获取数据失败')
        }

      })
    },
    loadGoodstype(){
      this.$axios.get(this.$httpUrl+'/goodstype/list').then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.goodstypeData=res.data
        }else{
          alert('获取数据失败')
        }

      })
    },
    loadState(){
      this.$axios.get(this.$httpUrl+'/state/list').then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.states=res.data
        }else{
          alert('获取数据失败')
        }

      })
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/record/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          goodstype:this.goodstype+'',
          storage:this.storage+'',
          roleId:this.user.roleId+'',
          userId:this.user.id+'',
          state:this.state+'',
        }
      }).then(res=>res.data).then(res=>{
        console.log(res)
        if(res.code==200){
          this.tableData=res.data
          this.total=res.total
        }else{
          alert('获取数据失败')
        }

      })
    },
  },
  beforeMount() {
    this.loadStorage()
    this.loadGoodstype()
    this.loadState()
    this.loadPost()

  },
}
</script>

<style scoped>

</style>