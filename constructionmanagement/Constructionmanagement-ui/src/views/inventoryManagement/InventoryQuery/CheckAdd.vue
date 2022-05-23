<template>
  <div style="margin-left: 10px">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="盘点任务名称" label-width="120px" prop="name">
            <el-input v-model="form.name"  placeholder="请输入盘点任务名称"/>
          </el-form-item>
        </el-col>
<!--        <el-col :span="8">-->
<!--          <el-form-item label="盘点完成时间" label-width="120px" prop="checkDate">-->
<!--            <el-date-picker clearable-->
<!--                            v-model="form.checkDate"-->
<!--                            type="date"-->
<!--                            value-format="yyyy-MM-dd"-->
<!--                            placeholder="请选择盘点完成时间"-->
<!--            >-->
<!--            </el-date-picker>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :span="8">-->
<!--          <el-form-item label="盘点添加时间" label-width="120px" prop="addDate">-->
<!--            <el-date-picker clearable-->
<!--                            v-model="form.addDate"-->
<!--                            type="date"-->
<!--                            value-format="yyyy-MM-dd"-->
<!--                            placeholder="请选择盘点添加时间"-->
<!--            >-->
<!--            </el-date-picker>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
        <el-col :span="8">
          <el-form-item label="负责人" prop="principal">
            <el-input v-model="form.principal" placeholder="请输入负责人名称"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-descriptions>
            <el-descriptions-item label="请从下列表格选择要盘点的器械"></el-descriptions-item>
          </el-descriptions>
        </el-col>
      </el-row>
    </el-form>
    <el-form :model="queryParams" ref="queryForm" :inline="true"  label-width="100px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery" s
        />
      </el-form-item>
      <el-form-item label="类别" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类别">
          <el-option
            v-for="dict in types"
            :key="dict.id"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
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
      <el-form-item label="所属仓库" prop="belongWarehouse">
        <el-select v-model="queryParams.belongWarehouse" placeholder="请选择所属仓库">
          <el-option
            v-for="dict in warehouses"
            :key="dict.id"
            :label="dict.name"
            :value="dict.name"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
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
          <el-tag :type="'primary'" v-else-if="scope.row.type==2">配件</el-tag>
          <el-tag :type="'primary'" v-else-if="scope.row.type==4">设备-配件</el-tag>
          <el-tag :type="'primary'" v-else-if="scope.row.type==6">部件-配件</el-tag>
          <el-tag :type="'success'" v-else-if="scope.row.type==3">部件</el-tag>
          <el-tag :type="'success'" v-else-if="scope.row.type==5">设备-部件</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="型号" align="center" prop="model"/>
      <el-table-column label="数量" align="center" prop="amount"/>
      <el-table-column label="所属仓库" align="center" prop="belongWarehouse"/>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <div slot="footer" class="dialog-footer" style="margin-left: 1200px;margin-top:50px">
      <el-button type="primary" @click="submitCheck">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import {
  getAllWarehouse,
  list,
  add,
  del,
  update, outStockBySelected
} from '@/api/inventory/InventoryQuery'
import { addCheck } from '@/api/inventory/check'

export default {
  name: 'CheckAdd',
  props: {},
  data() {
    return {
      form: {},
      rules: {
        name: [{
          required: true, message: '请输入盘点名称', trigger: 'blur'
        }],
        principal:[{
          required: true, message: '请输入负责人名字', trigger: 'blur'
        }],
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
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
      types:[],
      warehouses:[]
    }
  },
  created() {
    this.getDicts('type').then(response => {
      this.types = response.data
    })
    //获取所有的仓库
    getAllWarehouse().then(response => {
      this.warehouses = response.rows
    })
    this.getList()
  },
  methods: {
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    /** 查询列表 */
    getList() {
      this.loading = true
      list(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    cancel(){
      this.$emit('cancel',false)
    },
    submitCheck(){
      this.form.ids=this.ids
      addCheck(this.form).then(response => {
        this.$modal.msgSuccess("新增成功");
        this.cancel();
      });
    }
  }
}
</script>

<style scoped>

</style>
