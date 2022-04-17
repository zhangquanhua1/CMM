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
      <el-form-item label="部件型号" prop="partModel">
        <el-input
          v-model="queryParams.partModel"
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
          icon="el-icon-check"
          size="mini"
          :disabled="multiple"
          @click="handleConfirmS"
          v-hasPermi="['inventory:manage:partEntry:confirm']"
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
          v-hasPermi="['inventory:manage:partEntry:confirm']"
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
          v-hasPermi="['inventory:manage:partEntry:export']"
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
            v-hasPermi="['inventory:manage:partEntry:confirm']"
          >确认
          </el-button>
          <el-button
            v-if="scope.row.status==0"
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleAntiConfirm(scope.row)"
            v-hasPermi="['inventory:manage:partEntry:confirm']"
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

    <el-drawer
      :title="title"
      size="60%"
      :visible.sync="openDetail"
      :with-header="true"
    >
      <div style="margin-left: 10px">
        <div>
          <el-collapse v-model="activeNames">
            <el-collapse-item v-if="this.Detail" title="部件基本信息" name="1">
              <el-row>
                <el-col :span="4" class="col_title">部件名称：</el-col>
                <el-col :span="4">{{ Detail.partName != null ? Detail.partName : '-' }}</el-col>
                <el-col :span="4" class="col_title">产品编号：</el-col>
                <el-col :span="4">{{ Detail.productNum != null ? Detail.productNum : '-' }}</el-col>
                <el-col :span="4" class="col_title">生产厂家：</el-col>
                <el-col :span="4">{{ Detail.vender != null ? Detail.vender : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">部件类别：</el-col>
                <el-col :span="4">{{ Detail.partType != null ? Detail.partType : '-' }}</el-col>
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
                <el-col :span="4">{{ Detail.brachium != null ? Detail.brachium : '-' }}米</el-col>
                <el-col :span="4" class="col_title">设备型号：</el-col>
                <el-col :span="4">{{ Detail.unitType != null ? Detail.unitType : '-' }}</el-col>
                <el-col :span="4" class="col_title">计量单位：</el-col>
                <el-col :span="4">{{ Detail.measurementUnit != null ? Detail.measurementUnit : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">标准节高度：</el-col>
                <el-col :span="4">{{
                    Detail.standardSectionHeight != null ? Detail.standardSectionHeight : '-'
                  }}米
                </el-col>
                <el-col :span="4" class="col_title">部件型号：</el-col>
                <el-col :span="4">{{ Detail.partModel != null ? Detail.partModel : '-' }}</el-col>
                <el-col :span="4" class="col_title">部件代码：</el-col>
                <el-col :span="4">{{ Detail.partCode != null ? Detail.partCode : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">数量：</el-col>
                <el-col :span="4">{{ Detail.amount != null ? Detail.amount : '-' }}</el-col>
                <el-col :span="4" class="col_title">单价：</el-col>
                <el-col :span="4">{{ Detail.singlePrice != null ? Detail.singlePrice : '-' }}</el-col>
                <el-col :span="4" class="col_title">是否整机配件：</el-col>
                <el-col :span="4" v-if="Detail.wholeMachineAccessories>0">是</el-col>
                <el-col :span="4" v-else>否</el-col>
              </el-row>

              <el-row>
                <el-col :span="4" class="col_title">需求发起人：</el-col>
                <el-col :span="4">{{ Detail.demandSponsors != null ? Detail.demandSponsors : '-' }}</el-col>
                <el-col :span="4" class="col_title">采购原因：</el-col>
                <el-col :span="4">{{ Detail.procurementCauses != null ? Detail.procurementCauses : '-' }}</el-col>
              </el-row>


            </el-collapse-item>
            <el-collapse-item v-if="this.KitsList" title="零件清单" name="2">
              <el-table
                :data="KitsList"
                style="width: 100%"
              >
                <el-table-column
                  prop="kitName"
                  label="零件名称"
                  width="180"
                >
                </el-table-column>
                <el-table-column label="零件型号" align="center" prop="kitModel">
                </el-table-column>
                <el-table-column label="零件代码" align="center" prop="kitCode">
                </el-table-column>
                <el-table-column label="技术参数" align="center" prop="technicalParam">
                </el-table-column>
                <el-table-column label="适用部件类型" align="center" prop="applicableKitType">
                </el-table-column>
                <el-table-column label="数量" align="center" prop="kitCount">
                </el-table-column>
                <el-table-column label="计量单位" align="center" prop="measurementUnit">
                </el-table-column>
              </el-table>
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
.spec-dialog .el-dialog__body {
  padding: 3px 30px;
  height: 500px;
  overflow: auto;
}
</style>
<script>
import {
  listPartEntry,
  partInforConfirm,
  partAntiConfirm, listKitByPartId
} from '@/api/inventory/PartEntry'
import { treeselect } from '@/api/system/dept'
import PartEntryDetail from '@/views/inventoryManagement/PartEntry/PartEntryDetail'
import { checkRole } from '@/utils/permission'

export default {
  name: 'PartInforConfirm',
  components: { PartEntryDetail },
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
        partName: undefined,
        partType: undefined,
        partModel: undefined
      },
      openDetail: false,
      Detail: {},
      //计量单位
      measurement_unitS: [],
      //所属设备
      applicableDeviceTypeS: [],
      //部件类别
      applicableKitTypeS: [],
      // 所属部门ID字典
      depart_idOptions: [],
      //配件清单
      KitsList: [],
      mainTableKey2: 1,
      //配件是否可编辑
      isEditKit: false,
      activeNames: ['1'],
      //可用仓库
      warehouses: [],
      Items: []
    }
  },
  created() {
    this.getDicts('measurement_unit').then(response => {
      this.measurement_unitS = response.data
    })
    this.getDicts('applicableDeviceType').then(response => {
      this.applicableDeviceTypeS = response.data
    })
    this.getDicts('parts_type').then(response => {
      this.applicableKitTypeS = response.data
    })
    this.getList()
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      listPartEntry(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
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
      this.Items = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    getKitInfo(id) {
      listKitByPartId(id).then(response => {
        this.KitsList = response.data
      })
    },
    /** 多选确认按钮操作 */
    handleConfirmS() {
      if (this.Detail.id != null) {
        this.Items.push(this.Detail)
      }
      const Its = this.Items
      this.$modal.confirm('是否 确认').then(function() {
        return partInforConfirm(Its)
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
        return partAntiConfirm(Ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('反确认成功')
      }).catch(() => {
        this.$modal.msgError('反确认 取消')
      })
      this.ids = []
    },
    /**行确认操作*/
    handleConfirm(row) {
      this.getKitInfo(row.id)
      this.Detail = JSON.parse(JSON.stringify(row))
      this.openDetail = true
      this.title = '信息确认'
    },
    /**行反确认操作*/
    handleAntiConfirm(row) {
      this.getKitInfo(row.id)
      this.Detail = JSON.parse(JSON.stringify(row))
      this.openDetail = true
      this.title = '信息反确认'
    },
    handleDetail(row) {
      this.getKitInfo(row.id)
      this.Detail = row
      this.openDetail = true
      this.title = '详情'
    },

    filterState(value, row) {
      return row.status === value
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
    /** 导出按钮操作 */
    handleExport() {
      this.download('/inventory/partEntry/exportConfirm', {
        ...this.queryParams
      }, `partEntryConfirm_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
