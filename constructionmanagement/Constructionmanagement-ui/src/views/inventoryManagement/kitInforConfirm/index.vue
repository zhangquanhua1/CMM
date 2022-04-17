<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
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
      <el-form-item label="适用部件类别"  prop="kitType">
        <el-input
          v-model="queryParams.kitType"
          placeholder="请输入适用部件类别"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="零件型号" prop="kitModel">
        <el-input
          v-model="queryParams.kitModel"
          placeholder="请输入零件型号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="零件代码" prop="kitCode">
        <el-input
          v-model="queryParams.kitCode"
          placeholder="请输入零件代码"
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
      <el-form-item label="采购日期">
        <el-date-picker
          v-model="queryParams.purchaseDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择采购日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="报废日期">
        <el-date-picker
          v-model="queryParams.retireDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择报废日期"
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
          icon="el-icon-check"
          size="mini"
          :disabled="multiple"
          @click="handleConfirmS"
          v-hasPermi="['inventory:manage:kitentry:confirm']"
        >确认
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-close"
          size="mini"
          :disabled="multiple"
          @click="handleAntiConfirmS"
          v-hasPermi="['inventory:manage:kitentry:confirm']"
        >反确认
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['inventory:manage:kitentry:export']"
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
      <el-table-column label="适用部件类别" align="center" prop="kitType"/>
      <el-table-column label="零件型号" align="center" prop="kitModel"/>
      <el-table-column label="零件代码" align="center" prop="kitCode"/>
      <el-table-column label="采购日期" align="center" prop="purchaseDate">
        <template slot-scope="scope">
          <span>{{ parseTime2(scope.row.purchaseDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报废日期" align="center" prop="retireDate">
        <template slot-scope="scope">
          <span>{{ parseTime2(scope.row.retireDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单价" align="center" prop="singlePrice"/>
      <el-table-column label="数量" align="center" prop="amount"/>
      <el-table-column label="状态" align="center" prop="status" :filters="[{ text: '未确认', value: 0 },
      { text: '确认', value: 1 },{ text: '反确认', value: 2 }]"
                       :filter-method="filterState"
      >
        <template slot-scope="scope">
          <el-tag :type="'primary'" v-if="scope.row.status==0">未确认</el-tag>
          <el-tag :type="'success'" v-else-if="scope.row.status==1">确认</el-tag>
          <el-tag :type="'danger'" v-else>反确认</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status==0"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleConfirm(scope.row)"
            v-hasPermi="['inventory:manage:kitentry:confirm']"
          >确认
          </el-button>
          <el-button
            v-if="scope.row.status==0"
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleAntiConfirm(scope.row)"
            v-hasPermi="['inventory:manage:kitentry:confirm']"
          >反确认
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-more"
            @click="handleDetail(scope.row)"
          >详情
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
      :title="title"
      size="60%"
      :visible.sync="openDetail"
      :with-header="true"
    >
      <div style="margin-left: 10px">
        <div>
          <el-collapse v-model="activeNames">
            <el-collapse-item title="零件基本信息" name="1">
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
                <el-col :span="4" class="col_title">折旧率：</el-col>
                <el-col :span="4">{{ Detail.depreciationRate != null ? Detail.depreciationRate : '-' }}%</el-col>
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
                <el-col :span="4" class="col_title">所属仓库：</el-col>
                <el-col :span="4">{{ Detail.warehouse != null ? Detail.warehouse : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">是否整机配件：</el-col>
                <el-col :span="4"
                ><span v-if="Detail.wholeMachineAccessories>0">是</span>
                  <span v-else>否</span></el-col>
                <el-col :span="4" class="col_title">报废日期：</el-col>
                <el-col :span="4">{{ Detail.retireDate != null ? parseTime2(Detail.retireDate) : '-' }}</el-col>
                <el-col :span="4" class="col_title">采购日期：</el-col>
                <el-col :span="4">{{ Detail.purchaseDate != null ? parseTime2(Detail.purchaseDate) : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">录入人：</el-col>
                <el-col :span="4">{{ Detail.insertPerson != null ? Detail.insertPerson : '-' }}</el-col>
                <el-col :span="4" class="col_title">录入时间：</el-col>
                <el-col :span="4">{{ Detail.insertDate != null ? parseTime(Detail.insertDate) : '-' }}</el-col>
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
                <el-col :span="4">{{ Detail.updateDate != null ? parseTime(Detail.updateDate) : '-' }}</el-col>
              </el-row>
            </el-collapse-item>
            <el-collapse-item title="防坠器信息" name="2">
              <el-row>
                <el-col :span="4" class="col_title">防坠器编号：</el-col>
                <el-col :span="4">{{ Detail.protectorNum != null ? Detail.protectorNum : '-' }}</el-col>
                <el-col :span="4" class="col_title">防坠器规格：</el-col>
                <el-col :span="4">{{ Detail.standard != null ? Detail.standard : '-' }}
                </el-col>
                <el-col :span="4" class="col_title">防坠器型号：</el-col>
                <el-col :span="4">{{ Detail.model != null ? Detail.model : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">出厂日期：</el-col>
                <el-col :span="4">{{ Detail.productDate != null ? parseTime2(Detail.productDate) : '-' }}</el-col>
                <el-col :span="4" class="col_title">检测日期：</el-col>
                <el-col :span="4">{{ Detail.checkDate != null ? parseTime2(Detail.checkDate) : '-' }}</el-col>
              </el-row>
            </el-collapse-item>
            <el-collapse-item title="其他信息" name="3">
              <el-row>
                <el-col :span="4" class="col_title">供应商：</el-col>
                <el-col :span="4">{{ Detail.supplierId != null ? Detail.supplierId : '-' }}</el-col>
                <el-col :span="4" class="col_title">联系地址：</el-col>
                <el-col :span="4">{{ Detail.supplierAdd != null ? Detail.supplierAdd : '-' }}</el-col>
                <el-col :span="4" class="col_title">联系电话：</el-col>
                <el-col :span="4">{{ Detail.supplierTel != null ? Detail.supplierTel : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">经办人：</el-col>
                <el-col :span="4">{{ Detail.practiName != null ? Detail.practiName : '-' }}</el-col>
                <el-col :span="4" class="col_title">经办人电话：</el-col>
                <el-col :span="4">{{ Detail.mobile != null ? Detail.mobile : '-' }}</el-col>
                <el-col :span="4" class="col_title">录入批次号：</el-col>
                <el-col :span="4">{{ Detail.batchNumber != null ? Detail.batchNumber : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">附件：</el-col>
                <el-col :span="4" v-if="Detail.enclosure!=null">
                  <el-button type="cyan" icon="el-icon-download" size="mini" @click="handleDownload(form.enclosure)">
                    点击下载
                  </el-button>
                </el-col>
                <el-col :span="4" v-else>
                  无附件
                </el-col>
              </el-row>
            </el-collapse-item>
          </el-collapse>
        </div>
        <br/> <br/> <br/>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submit" v-if="this.title=='信息确认'">{{ title }}</el-button>
          <el-button type="danger" @click="submit" v-if="this.title=='信息反确认'">{{ title }}</el-button>
          <el-button @click="cancel" v-if="this.title!='详情'">取 消</el-button>
        </div>
      </div>

    </el-drawer>
  </div>
</template>
<style lang="scss">
//.spec-dialog .el-dialog__body {
//  padding: 3px 30px;
//  height: 500px;
//  overflow: auto;
//}
</style>
<script>
import {
  kitInforConfirm,
  listKitEntry
  , kitAntiConfirm
} from '@/api/inventory/KitEntry'
import { treeselect } from '@/api/system/dept'
import { checkRole } from '@/utils/permission'

export default {
  name: 'kitInforConfirm',
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productNum: undefined,
        vender: undefined,
        kitType: undefined,
        purchaseDate: undefined,
        retireDate: undefined,
        equipment: undefined,
        kitModel: undefined,
        kitCode: undefined,
        objectValueAllEmpty: false
      },
      openDetail: false,
      Detail: {},
      protectors: [],
      //折叠面板
      activeNames: ['1'],
      // 所属部门ID字典
      depart_idOptions: [],
      //选中要确认的数据
      Items: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询列表 */
    getList() {
      if (this.objectValueAllEmpty1(this.queryParams)) {
        this.queryParams.objectValueAllEmpty = true
      }
      this.loading = true
      listKitEntry(this.queryParams).then(response => {
        this.queryParams.objectValueAllEmpty = false
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
    getDeptName(id) {
      return this.getDeptNameByID(this.depart_idOptions, id)
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail = false
      this.Detail = {}
      this.Items = []
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.queryParams.retireDate = '',
        this.queryParams.purchaseDate = '',
        this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
     this.ids = selection.map(item => item.id)
      this.Items = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 多选确认按钮操作 */
    handleConfirmS() {
      if (this.Detail.id != null){
        this.Items.push(this.Detail)
      }
      const Its = this.Items
      this.$modal.confirm('是否 确认').then(function() {
       return kitInforConfirm(Its)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('确认 成功')
      }).catch(() => {
        this.$modal.msgError('确认 取消')
      })
      this.Items = []
    },
    /** 提交按钮 */
    submit: function() {
      if (this.title == '信息确认') {
        this.handleConfirmS(this.Detail.id)
        this.Detail = {}
        this.openDetail = false

      } else if (this.title == '信息反确认') {
        this.handleAntiConfirmS(this.Detail.id)
        this.openDetail = false
        this.Detail = {}
      }
    },

    /** 多选反确认按钮操作 */
    handleAntiConfirmS() {
      if (this.Detail.id != null) this.ids.push(this.Detail.id)
      const Ids = this.ids
      this.$modal.confirm('是否 反确认').then(function() {
        return kitAntiConfirm(Ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('反确认成功')
      }).catch(() => {
        this.$modal.msgError('反确认 取消')
      })
      this.ids = []
    },
    handleDetail(row) {
      this.getTreeselect()
      this.Detail = row
      this.openDetail = true
      this.title = '详情'
    },
    /**行确认操作*/
    handleConfirm(row) {
      this.getTreeselect()
      this.Detail = JSON.parse(JSON.stringify(row))
      this.openDetail = true
      this.title = '信息确认'
    },
    /**行反确认操作*/
    handleAntiConfirm(row) {
      this.getTreeselect()
      this.Detail = JSON.parse(JSON.stringify(row))
      this.openDetail = true
      this.title = '信息反确认'
    },
    filterState(value, row) {
      return row.status === value
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/inventory/kitEntry/exportConfirm', {
        ...this.queryParams
      }, `kitEntryConfirm_${new Date().getTime()}.xlsx`)
    },

    //判断那些列可选
    selectInit(row, index) {
      var roles=["admin"]
      if(checkRole(roles))
        return true
      if (row.status != 0) {    //判断条件
        return false  //不可勾选
      } else {
        return true  //可勾选
      }
    },
  }
}
</script>
