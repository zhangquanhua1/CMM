<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="部件名称" prop="partName">
        <el-input
          v-model="queryParams.partName"
          placeholder="请输入部件名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品编号" prop="productNum">
        <el-input
          v-model="queryParams.productNum"
          placeholder="请输入产品编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产厂家" prop="vender">
        <el-input
          v-model="queryParams.vender"
          placeholder="请输入生产厂家"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="部件类别" prop="partType">
        <el-select v-model="queryParams.partType" placeholder="请选择部件类别">
          <el-option
            v-for="dict in applicableKitTypeS"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="产权单位" prop="rightsUnit">
        <el-input
          v-model="queryParams.rightsUnit"
          placeholder="请输入产权单位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备型号" prop="unitType">
        <el-input
          v-model="queryParams.unitType"
          placeholder="请输入设备型号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发起日期">
        <el-date-picker
          v-model="queryParams.insertDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择发起日期"
        ></el-date-picker>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-thumb"
          size="mini"
          @click="handleConfirmIsBuy"
          v-hasPermi="['asset:manage:partrequire:isBuy']"
        >已采购确认
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['asset:manage:partrequire:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--显示表格-->
    <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" :selectable='selectInit' width="55" align="center"/>
      <el-table-column
        type="index"
        width="50"
      >
      </el-table-column>
      <el-table-column label="部件名称" align="center" prop="partName"/>
      <el-table-column label="产品编号" align="center" prop="productNum"/>
      <el-table-column label="生产厂家" align="center" prop="vender"/>
      <el-table-column label="部件类别" align="center" prop="partType"/>
      <el-table-column label="所属设备" align="center" prop="equipment"/>
      <el-table-column label="资产总计" align="center" prop="totalAssets"/>
      <el-table-column label="产权单位" align="center" prop="rightsUnit"/>
      <el-table-column label="设备型号" align="center" prop="unitType"/>
      <el-table-column label="计量单位" align="center" prop="measurementUnit"/>
      <el-table-column label="部件型号" align="center" prop="partModel"/>
      <el-table-column label="部件代码" align="center" prop="partCode"/>
      <el-table-column label="数量" align="center" prop="amount"/>
      <el-table-column label="单价" align="center" prop="singlePrice"/>
      <el-table-column label="需求发起人" align="center" prop="demandSponsors"/>
      <el-table-column label="采购原因" align="center" prop="procurementCauses"/>
      <el-table-column label="是否已采购" align="center"  prop="isBuy" :filters="[{ text: '待采购', value: 0 },
      { text: '已采购', value: 1 }]"
                       :filter-method="filterState"
      >
        <template slot-scope="scope">
          <el-tag :type="'primary'" v-if="scope.row.isBuy==0">待采购</el-tag>
          <el-tag :type="'success'" v-else>已采购</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.isBuy==0"
                     size="mini"
                     type="text"
                     icon="el-icon-thumb"
                     @click="handleConfirmIsBuy(scope.row)"
                     v-hasPermi="['asset:manage:partrequire:isBuy']"
          >已采购确认
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-more"
            @click="handleDetail(scope.row)"
          >需求详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    <!--    详情弹窗-->
    <el-drawer
      title="需求详情"
      size="60%"
      :visible.sync="openDetail"
      :with-header="true"
    >
      <div style="margin-left: 10px">
        <PartRequireDetail :detail="this.Detail">/</PartRequireDetail>
      </div>
    </el-drawer>
  </div>
</template>
<style lang="scss">
.spec-dialog .el-dialog__body {
  padding: 3px 30px;
  height: 500px;
  overflow: auto;
}
</style>
<script>
import {
  listAudited,
  getPartRequireKit,
  confirmIsBuy,
  getKitS,
} from '@/api/towerparam/partrequire'
import { treeselect } from '@/api/system/dept'
import PartRequireDetail from '../partrequire/PartRequireDetail'
export default {
  name: 'partIsBuy',
  // dicts: ['sys_normal_disable'],\
  components: {PartRequireDetail},
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 岗位表格数据
      postList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        partName: undefined,
        productNum: undefined,
        vender: undefined,
        partType: undefined,
        unitType: undefined,
        insertDate: undefined
      },
      openDetail: false,
      Detail: {},
      // 所属部门ID字典
      depart_idOptions: [],
      // 表单校验
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      listAudited(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.depart_idOptions = response.data;
      });
    },
    //根据部门id 获取部门名
    getDeptName(id){
      return this.getDeptNameByID(this.depart_idOptions,id)
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail = false
      this.openAudit = false
      this.reset()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.queryParams.insertDate = ''
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 删除按钮操作 */
    handleConfirmIsBuy(row) {
      const postIds = row.id || this.ids
      this.$modal.confirm('是否确认已采购？').then(function() {
        return confirmIsBuy(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('确认成功')
      }).catch(() => {
      })
    },
    handleDetail(row) {
      this.Detail = row
      this.openDetail = true
      this.title = '详情'
    },
    filterState(value, row) {
      return row.isBuy === value
    },
    //判断那些列可选
    selectInit(row, index) {
      if (row.isBuy==1) {    //判断条件
        return false  //不可勾选
      } else {
        return true  //可勾选
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/asset/partrequire/exportBuy', {
        ...this.queryParams
      }, `partBuy_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
