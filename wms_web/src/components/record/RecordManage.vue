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

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" style="margin-left: 5px;" @click="resetParam">重置</el-button>


    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
              border
    >
      <el-table-column prop="id" label="订单ID" width="60">
      </el-table-column>
      <el-table-column prop="iswholesale" label="订单类型" width="90">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.iswholesale == 0 ? 'danger' : 'success'"
              disable-transitions>{{scope.row.iswholesale == 0 ? '零售订单' : '批发订单'}}</el-tag>
        </template>
      </el-table-column>
<!--      <el-table-column prop="goodsname" label="物品名" width="80">-->
<!--      </el-table-column>-->
<!--      <el-table-column prop="storagename" label="仓库" width="120">-->
<!--      </el-table-column>-->
<!--      <el-table-column prop="goodstypename" label="分类" width="80">-->
<!--      </el-table-column>-->
      <el-table-column prop="username" label="客户ID" width="80">
      </el-table-column>
      <el-table-column prop="adminname" label="操作人" width="90">
      </el-table-column>
<!--      <el-table-column prop="username" label="申请人" width="90">-->
<!--      </el-table-column>-->
<!--      <el-table-column prop="count" label="数量" width="50">-->
<!--      </el-table-column>-->
      <el-table-column prop="totalprice" label="总金额" width="70">
      </el-table-column>
      <el-table-column prop="profit" label="毛利润" width="60">
      </el-table-column>
      <el-table-column prop="createtime" label="创建订单时间" width="160">
      </el-table-column>
      <el-table-column prop="remark" label="备注" width="60">
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
          <el-button type="success" style="margin-left: 5px;" size="small" @click="mod(scope.row,1)">收款</el-button>
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
      tableData: [],
      pageSize:10,
      pageNum:1,
      total:0,
      name:'',
      storage:'',
      goodstype:'',
      centerDialogVisible:false,
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
        profit:''
      },
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
        this.$axios.get(this.$httpUrl+'/record/del?id='+id).then(res=>res.data).then(res=>{
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
    loadPost(){
      this.$axios.post(this.$httpUrl+'/record/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          goodstype:this.goodstype+'',
          storage:this.storage+'',
          roleId:this.user.roleId+'',
          userId:this.user.id+''
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
    this.loadPost()

  },
}
</script>

<style scoped>

</style>