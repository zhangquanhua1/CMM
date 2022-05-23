<template>
  <div style="margin-left: 10px">
    <el-form :model="queryParams" ref="queryForm" :inline="true"  label-width="100px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="型号" prop="model">
        <el-input
          v-model="queryParams.model"
          placeholder="请输入型号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="postList" :key="itemKey" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column
        type="index"
        width="50"
      >
      </el-table-column>
      <el-table-column label="名称" align="center" prop="name"/>
      <el-table-column label="类别" align="center" prop="type">
        <template slot-scope="scope">
          <el-tag :type="'danger'" v-if="scope.row.type==1">设备</el-tag>
          <el-tag :type="'primary'" v-else-if="scope.row.type==2">零件</el-tag>
          <el-tag :type="'primary'" v-else-if="scope.row.type==4">零件</el-tag>
          <el-tag :type="'primary'" v-else-if="scope.row.type==6">零件</el-tag>
          <el-tag :type="'success'" v-else-if="scope.row.type==3">部件</el-tag>
          <el-tag :type="'success'" v-else-if="scope.row.type==5">部件</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="型号" align="center" prop="model"/>
      <el-table-column label="数量" align="center" prop="amount"/>
      <el-table-column label="所属仓库" align="center" prop="belongWarehouse"/>
      <el-table-column label="盘点数量" align="center" class-name="small-padding fixed-width" v-if="status==0">
        <template slot-scope="scope">
          <el-input v-model="scope.row.amount2" placeholder="请输入数量"  style="width:140px"/>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-update"
            @click="confirm(scope.row)"
            style="margin-left:10px"
          >确定</el-button>
        </template>
      </el-table-column>
    </el-table>
<!--    <pagination-->
<!--      v-show="total>0"-->
<!--      :total="total"-->
<!--      :page.sync="queryParams.pageNum"-->
<!--      :limit.sync="queryParams.pageSize"-->
<!--      @pagination="getList"-->
<!--    />-->
    <div slot="footer" class="dialog-footer" style="margin-left: 1200px;margin-top:50px">
      <el-button type="primary" @click="submitForm" v-if="status==0">确认盘点</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { selectCheckById, submitCheck, updateCheck } from '@/api/inventory/check'

export default {
  name: 'detail',
  props: {
    detailId: '',
    status:''
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      postList: [],
      postList2: [],
      types: [],
      warehouses: [],
      id2:this.detailId,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name:null,
        model: null,
      },
      form: {
        list:[],
        id:''
      },
      itemKey:1,
    }
  }
  ,
  created() {
    this.getList()
  },
  methods: {
// 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },

    getList(){
      this.loading = true
      selectCheckById(this.id2).then(response => {
        this.postList = response.rows
        this.postList.forEach((e)=>{
          this.$set(e,'amount2',e.amount)
          //e.amount2=e.amount;
        })
        this.postList2=this.postList
        this.total = response.total
        this.loading = false
      })
    },
    confirm(row){
      row.amount=row.amount2
    },
    cancel(){
      this.$emit('cancel',false)
    },
    submitForm(){
      this.form.list=this.postList
      this.form.id=this.id2
      submitCheck(this.form).then(response => {
          if(response.code==200){
            this.$modal.msgSuccess('盘点成功')
          }else{
            this.$modal.msgError('盘点失败，请重试')
          }
      })
      this.$emit('cancel',false)
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.getList()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      var res=[]
      this.postList.forEach((e)=>{
         if(this.queryParams.name!=null&&this.queryParams.model!=null){
           if(this.queryParams.name==e.name&&this.queryParams.model==e.model){
             res.push(e)
           }
         }else if(this.queryParams.name!=null){
           if(this.queryParams.name==e.name){
             res.push(e)
           }
         }else if(this.queryParams.model!=null){
           if(this.queryParams.model==e.model){
             res.push(e)
           }
         }
        }
      )
      this.postList=res
    },

  },
  watch: {
    detailId(n, o) {
      this.id2=n
      this.getList()
      this.itemKey = Math.random()
    },
  }
}
</script>

<style scoped>

</style>
