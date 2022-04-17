<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="零件名称" prop="kitName">
        <el-input
          v-model="queryParams.kitName"
          placeholder="请输入零件名称"
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

      <el-form-item label="所属设备" prop="equipment">
        <el-input
          v-model="queryParams.equipment"
          placeholder="请输入所属设备"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          @click="handleBuyed"
          v-hasPermi="['asset:manage:kitrequire:isBuy']"
        >确认已采购
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['asset:manage:kitrequire:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--显示表格-->
    <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" :selectable="selectInit" width="55" align="center"/>
      <el-table-column
        type="index"
        width="50"
      >
      </el-table-column>
      <el-table-column label="零件名称" align="center" prop="kitName"/>
      <el-table-column label="产品编号" align="center" prop="productNum"/>
      <el-table-column label="生产厂家" align="center" prop="vender"/>
      <!--      <el-table-column label="零件类别" align="center" prop="kitType"/>-->
      <el-table-column label="所属部件" align="center" prop="equipment"/>
      <el-table-column label="资产总计" align="center" prop="totalAssets"/>
      <el-table-column label="产权单位" align="center" prop="rightsUnit"/>
      <el-table-column label="数量" align="center" prop="amount"/>
      <el-table-column label="单价" align="center" prop="singlePrice"/>
      <el-table-column label="需求发起人" align="center" prop="demandSponsors"/>
      <el-table-column label="发起日期" align="center" prop="initDate">
        <template slot-scope="scope">
          <span>{{ parseTime2(scope.row.initDate) }}</span>
        </template>
      </el-table-column>
      <!--      <el-table-column label="审核状态" align="center" prop="state" :filters="[{ text: '待审核', value: 0 },-->
      <!--      { text: '通过', value: 1 },{ text: '拒绝', value: 2 }]"-->
      <!--                       :filter-method="filterState"-->
      <!--      >-->
      <!--        <template slot-scope="scope">-->
      <!--          <el-tag :type="'primary'" v-if="scope.row.state==0">待审核</el-tag>-->
      <!--          <el-tag :type="'success'" v-else-if="scope.row.state==1">通过</el-tag>-->
      <!--          <el-tag :type="'danger'" v-else>拒绝</el-tag>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <el-table-column label="是否已采购" align="center" prop="isBuy" :filters="[{ text: '待采购', value: 0 },
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
          <el-button
            v-if="scope.row.isBuy==0"
            size="mini"
            type="text"
            icon="el-icon-thum"
            @click="handleBuyed(scope.row)"
            v-hasPermi="['asset:manage:kitrequire:isBuy']"
          >确认已采购
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
    <!-- 添加或修改对话框 -->
    <!--    详情弹窗-->
    <el-drawer
      :title="title"
      size="40%"
      :visible.sync="openDetail"
      :with-header="true"
    >
      <div style="margin-left: 10px">
        <el-row>
          <el-col :span="4" class="col_title">零件名称：</el-col>
          <el-col :span="4">{{ Detail.kitName != null ? Detail.kitName : '-' }}</el-col>
          <el-col :span="4" class="col_title">产品编号：</el-col>
          <el-col :span="4">{{ Detail.productNum != null ? Detail.productNum : '-' }}</el-col>
          <el-col :span="4" class="col_title">生产厂家：</el-col>
          <el-col :span="4">{{ Detail.vender != null ? Detail.vender : '-' }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">适用部件类别：</el-col>
          <el-col :span="4">{{ Detail.kitType != null ? Detail.kitType : '-' }}</el-col>
          <el-col :span="4" class="col_title">所属设备：</el-col>
          <el-col :span="4">{{ Detail.equipment != null ? Detail.equipment : '-' }}</el-col>
          <el-col :span="4" class="col_title">资产总计：</el-col>
          <el-col :span="4">{{ Detail.totalAssets != null ? Detail.totalAssets : '-' }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">产权单位：</el-col>
          <el-col :span="4">{{ Detail.rightsUnit != null ? Detail.rightsUnit : '-' }}</el-col>
          <el-col :span="4" class="col_title">零件属性：</el-col>
          <el-col :span="4">{{ Detail.kitProperties != null ? Detail.kitProperties : '-' }}</el-col>
          <el-col :span="4" class="col_title">零件规格：</el-col>
          <el-col :span="4">{{ Detail.kitStandard != null ? Detail.kitStandard : '-' }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">臂长：</el-col>
          <el-col :span="4">{{ Detail.brachium != null ? Detail.brachium : '-' }} 米</el-col>

          <el-col :span="4" class="col_title">设备型号：</el-col>
          <el-col :span="4">{{ Detail.unitType != null ? Detail.unitType : '-' }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">计量单位：</el-col>
          <el-col :span="4">{{ Detail.measurementUnit != null ? Detail.measurementUnit : '-' }}</el-col>
          <el-col :span="4" class="col_title">标准节高度：</el-col>
          <el-col :span="4">{{ Detail.standardSectionHeight != null ? Detail.standardSectionHeight : '-' }}米
          </el-col>
          <el-col :span="4" class="col_title">零件型号：</el-col>
          <el-col :span="4">{{ Detail.kitModel != null ? Detail.kitModel : '-' }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">零件代码：</el-col>
          <el-col :span="4">{{ Detail.kitCode != null ? Detail.kitCode : '-' }}</el-col>
          <el-col :span="4" class="col_title">数量：</el-col>
          <el-col :span="4">{{ Detail.amount != null ? Detail.amount : '-' }}</el-col>
          <el-col :span="4" class="col_title">单价：</el-col>
          <el-col :span="4">{{ Detail.singlePrice != null ? Detail.singlePrice : '-' }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">归属仓库：</el-col>
          <el-col :span="4">{{ Detail.belongWarehouse != null ? Detail.belongWarehouse : '-' }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">是否整机配件：</el-col>
          <el-col :span="4"
          ><span v-if="Detail.wholeMachineAccessories>0">是</span>
            <span v-else>否</span></el-col>
          <el-col :span="4" class="col_title">发起日期：</el-col>
          <el-col :span="4">{{ Detail.initDate != null ? parseTime2(Detail.initDate) : '-' }}</el-col>
          <el-col :span="4" class="col_title">审核状态：</el-col>
          <el-col :span="4"><span v-if="Detail.state>0">是</span>
            <span v-else>否</span></el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">录入人：</el-col>
          <el-col :span="4">{{ Detail.insertPerson != null ? Detail.insertPerson : '-' }}</el-col>
          <el-col :span="4" class="col_title">录入时间：</el-col>
          <el-col :span="4">{{ Detail.insertDate != null ? parseTime2(Detail.insertDate) : '-' }}</el-col>
          <el-col :span="4" class="col_title">录入人部门：</el-col>
          <el-col :span="4">
            {{ Detail.insertPersonDepartId != null ? getDeptName(Detail.insertPersonDepartId) : '-' }}
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">更新人部门：</el-col>
          <el-col :span="4">
            {{ Detail.updatePersonDepartId != null ? getDeptName(Detail.updatePersonDepartId) : '-' }}
          </el-col>
          <el-col :span="4" class="col_title">更新人：</el-col>
          <el-col :span="4">{{ Detail.updatePerson != null ? Detail.updatePerson : '-' }}</el-col>
          <el-col :span="4" class="col_title">更新时间：</el-col>
          <el-col :span="4">{{ Detail.updateDate != null ? parseTime2(Detail.updateDate) : '-' }}</el-col>
        </el-row>
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
import { confirmIsBuy, listKitPass } from '@/api/towerparam/kitrequire'
import { treeselect } from '@/api/system/dept'

export default {
  name: 'kitIsBuy',
  // dicts: ['sys_normal_disable'],
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
      openAudit: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        kitName: undefined,
        productNum: undefined,
        vender: undefined,
        equipment: undefined,
        rightsUnit: undefined
      },
      openDetail: false,
      Detail: {},
      // 所属部门ID字典
      depart_idOptions: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      listKitPass(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.depart_idOptions = response.data
      })
    },
    //根据部门id 获取部门名
    getDeptName(row) {
      return this.getDeptNameByID(this.depart_idOptions, row)
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
    //详情
    handleDetail(row) {
      this.getTreeselect()
      this.Detail = row
      this.openDetail = true
      this.title = '详情'
    },
    /** 确认采购按钮操作 */
    handleBuyed(row) {
      const postIds = row.id || this.ids
      this.$modal.confirm('是否确认已采购？').then(function() {
        return confirmIsBuy(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('确认成功')
      }).catch(() => {
      })
    },
    //判断那些列可选
    selectInit(row, index) {
      if (row.isBuy == 1) {    //判断条件
        return false  //不可勾选
      } else {
        return true  //可勾选
      }
    },
    filterState(value, row) {
      return row.isBuy === value
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/asset/kitrequire/exportBuy', {
        ...this.queryParams
      }, `kitRequireBuy_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
