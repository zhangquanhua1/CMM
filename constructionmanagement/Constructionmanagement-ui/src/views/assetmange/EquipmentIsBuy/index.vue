<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备名称" prop="equipmentTypeName">
        <el-input
          v-model="queryParams.equipmentName"
          placeholder="请输入设备名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备编号" prop="equipmentNumber">
        <el-input
          v-model="queryParams.equipmentNumber"
          placeholder="请输入设备编号"
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

      <el-form-item label="设备类型" prop="equipmentType">
        <el-input
          v-model="queryParams.equipmentType"
          placeholder="请输入设备类型"
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
      <el-form-item label="归属仓库" prop="belongWarehouse">
        <el-input
          v-model="queryParams.belongWarehouse"
          placeholder="请输入归属仓库"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目地址" prop="projectAddress">
        <el-input
          v-model="queryParams.projectAddress"
          placeholder="请输入项目地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目日期">
        <el-date-picker
          v-model="queryParams.projectDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择项目日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="发起日期">
        <el-date-picker
          v-model="queryParams.initDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择发起日期"
        ></el-date-picker>
      </el-form-item>
      <!--      <el-form-item>-->
      <!--        <template>-->
      <!--          <el-radio v-model="queryParams.state" label="1">已审核</el-radio>-->
      <!--          <el-radio v-model="queryParams.state" label="0">待审核</el-radio>-->
      <!--        </template>-->
      <!--      </el-form-item>-->

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
          v-hasPermi="['asset:manage:equipmentrequire:isBuy']"
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
          v-hasPermi="['system:post:export']"
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
      <el-table-column label="设备名称" align="center" prop="equipmentName"/>
      <el-table-column label="设备编号" align="center" prop="equipmentNumber"/>
      <el-table-column label="生产厂家" align="center" prop="vender"/>
      <el-table-column label="规格型号" align="center" prop="standardModel"/>
      <el-table-column label="设备类型" align="center" prop="equipmentType"/>
      <el-table-column label="设备数量" align="center" prop="equipmentCount"/>
      <el-table-column label="产权单位" align="center" prop="rightsUnit"/>
      <el-table-column label="归属仓库" align="center" prop="belongWarehouse"/>
      <el-table-column label="项目地址" align="center" prop="projectAddress"/>
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
                     v-hasPermi="['asset:manage:equipmentrequire:isBuy']"
          >已采购确认
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-more"
            @click="handleDetail(scope.row)"
          >设备详情
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
      title="设备详情"
      size="60%"
      :visible.sync="drawer"
      :with-header="true"
    >
      <div style="margin-left: 10px">
        <el-collapse v-model="activeNames" accordion>
          <el-collapse-item title="设备信息" name="1">
            <el-row>
              <el-col :span="4" class="col_title">设备名称：</el-col>
              <el-col :span="4">{{ Detail.equipmentName != null ? Detail.equipmentName : '-' }}</el-col>
              <el-col :span="4" class="col_title">设备编号：</el-col>
              <el-col :span="4">{{ Detail.equipmentNumber != null ? Detail.equipmentNumber : '-' }}</el-col>
              <el-col :span="4" class="col_title">生产厂家：</el-col>
              <el-col :span="4">{{ Detail.vender != null ? Detail.vender : '-' }}</el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title">规格型号：</el-col>
              <el-col :span="4">{{ Detail.standardModel != null ? Detail.standardModel : '-' }}</el-col>
              <el-col :span="4" class="col_title">设备类别：</el-col>
              <el-col :span="4">{{ Detail.equipmentType != null ? Detail.equipmentType : '-' }}</el-col>
              <el-col :span="4" class="col_title">设备数量：</el-col>
              <el-col :span="4">{{ Detail.equipmentCount != null ? Detail.equipmentCount : '-' }}</el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title">产权单位：</el-col>
              <el-col :span="4">{{ Detail.rightsUnit != null ? Detail.rightsUnit : '-' }}</el-col>
              <el-col :span="4" class="col_title">归属仓库：</el-col>
              <el-col :span="4">{{ Detail.belongWarehouse != null ? Detail.belongWarehouse : '-' }}</el-col>
              <el-col :span="4" class="col_title">项目地址：</el-col>
              <el-col :span="4">{{ Detail.projectAddress != null ? Detail.projectAddress : '-' }}</el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title">项目日期：</el-col>
              <el-col :span="4">{{ Detail.projectDate != null ? parseTime2(Detail.projectDate) : '-' }}</el-col>
              <el-col :span="4" class="col_title">发起日期：</el-col>
              <el-col :span="4">{{ Detail.initDate != null ? parseTime2(Detail.initDate) : '-' }}</el-col>
              <el-col :span="4" class="col_title">需求发起人：</el-col>
              <el-col :span="4">{{ Detail.demandSponsors != null ? Detail.demandSponsors : '-' }}</el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title">采购原因：</el-col>
              <el-col :span="4">{{ Detail.procurementCauses != null ? Detail.procurementCauses : '-' }}</el-col>
              <el-col :span="4" class="col_title">审核状态：</el-col>
              <el-col :span="4">
                <span v-if="Detail.state==0">待审核</span>
                <span v-else-if="Detail.state==1">审核通过</span>
                <span v-else>审核不通过</span>
              </el-col>
              <el-col :span="4" class="col_title"> 审核意见:</el-col>
              <el-col :span="4">
                  <span v-if="Detail.auditAdvice==null">
                待审核状态中
              </span>
                <span v-else> {{ Detail.auditAdvice }}</span>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="4" class="col_title">发起日期：</el-col>
              <el-col :span="4">{{ Detail.initDate != null ? parseTime2(Detail.initDate) : '-' }}</el-col>
              <el-col :span="4" class="col_title">录入人：</el-col>
              <el-col :span="4">
                {{ Detail.insertPerson != null ? Detail.insertPerson : '-' }}
              </el-col>
              <el-col :span="4" class="col_title"> 录入时间:</el-col>
              <el-col :span="4">
                {{ Detail.insertDate != null ? parseTime(Detail.insertDate) : '-' }}
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title">录入人部门：</el-col>
              <el-col :span="4">
                {{ Detail.insertPersonDepartId != null ? getDeptName(Detail.insertPersonDepartId) : '-' }}
              </el-col>
              <el-col :span="4" class="col_title">更新人部门：</el-col>
              <el-col :span="4">
                {{ Detail.updatePersonDepartId != null ? getDeptName(Detail.updatePersonDepartId) : '-' }}
              </el-col>
              <el-col :span="4" class="col_title"> 更新人:</el-col>
              <el-col :span="4">
                {{ Detail.updatePerson != null ? Detail.updatePerson : '-' }}
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title">录入时间：</el-col>
              <el-col :span="4">{{ Detail.updateDate != null ? parseTime(Detail.updateDate) : '-' }}</el-col>
            </el-row>
          </el-collapse-item>
          <el-collapse-item title="设备参数" name="2">
            <div v-if="EquipmentDetail!=null&&EquipmentDetail!=undefined">
              <el-row>
                <el-col :span="4" class="col_title">设备名称：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.towerMachineName != null ? EquipmentDetail.towerMachineName : '-' }}
                </el-col>
                <el-col :span="4" class="col_title">生产厂家：</el-col>
                <el-col :span="4">{{ EquipmentDetail.vender != null ? EquipmentDetail.vender : '-' }}</el-col>
                <el-col :span="4" class="col_title">设备型号：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.towerMachineModel != null ? EquipmentDetail.towerMachineModel : '-' }}
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">额定起重力距：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.ratedLiftingDistance != null ? EquipmentDetail.ratedLiftingDistance : '-' }} KN/m
                </el-col>
                <el-col :span="4" class="col_title">最大起重量：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.maximumLiftingWeight != null ? EquipmentDetail.maximumLiftingWeight : '-' }} T
                </el-col>
                <el-col :span="4" class="col_title">最大幅度额定起重量：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.maximumRatedWeight != null ? EquipmentDetail.maximumRatedWeight : '-' }} T
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">最大起重量工作幅度：</el-col>
                <el-col :span="4">
                  {{
                    EquipmentDetail.maximumStartingWeightWorkingRange != null ? EquipmentDetail.maximumStartingWeightWorkingRange : '-'
                  }}
                  M
                </el-col>
                <el-col :span="4" class="col_title">最大工作幅度：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.maximumRangeWork != null ? EquipmentDetail.maximumRangeWork : '-' }} M
                </el-col>
                <el-col :span="4" class="col_title">最大起升高度：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.maximumHeightLift != null ? EquipmentDetail.maximumHeightLift : '-' }} M
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">起升速度：</el-col>
                <el-col :span="4">{{ EquipmentDetail.upDownSpeed != null ? EquipmentDetail.upDownSpeed : '-' }} m/min
                </el-col>
                <el-col :span="4" class="col_title">变幅速度：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.variableAmplitudeSpeed != null ? EquipmentDetail.variableAmplitudeSpeed : '-' }}
                  m/min
                </el-col>
                <el-col :span="4" class="col_title">回转速度：</el-col>
                <el-col :span="4">{{ EquipmentDetail.swivellingSpeed != null ? EquipmentDetail.swivellingSpeed : '-' }}
                  r/min
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">顶升速度：</el-col>
                <el-col :span="4">{{ EquipmentDetail.upSpeed != null ? EquipmentDetail.upSpeed : '-' }} m/min</el-col>
                <el-col :span="4" class="col_title">塔机自重：</el-col>
                <el-col :span="4">{{ EquipmentDetail.balanceWeight != null ? EquipmentDetail.balanceWeight : '-' }} t
                </el-col>
                <el-col :span="4" class="col_title">平衡重量：</el-col>
                <el-col :span="4">{{ EquipmentDetail.balanceWeight != null ? EquipmentDetail.balanceWeight : '-' }} t
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">工作电压：</el-col>
                <el-col :span="4">{{ EquipmentDetail.workingVoltage != null ? EquipmentDetail.workingVoltage : '-' }}
                  v
                </el-col>
                <el-col :span="4" class="col_title">装机总容量：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.totalInstalledCapacity != null ? EquipmentDetail.totalInstalledCapacity : '-' }} KW
                </el-col>
                <el-col :span="4" class="col_title">电机功率：</el-col>
                <el-col :span="4">{{ EquipmentDetail.motorPower != null ? EquipmentDetail.motorPower : '-' }} KW
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">变频器功率：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.frequencyConverterPower != null ? EquipmentDetail.frequencyConverterPower : '-' }}
                  KW
                </el-col>
                <el-col :span="4" class="col_title">标准节规格：</el-col>
                <el-col :span="4">
                  {{
                    EquipmentDetail.standardSectionSpecifications != null ? EquipmentDetail.standardSectionSpecifications : '-'
                  }}
                </el-col>
                <el-col :span="4" class="col_title">附墙框规格：</el-col>
                <el-col :span="4">
                  {{
                    EquipmentDetail.attachedWallFrameSpecifications != null ? EquipmentDetail.attachedWallFrameSpecifications : '-'
                  }}
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">备注：</el-col>
                <el-col :span="20">{{ EquipmentDetail.remark != null ? EquipmentDetail.remark : '-' }}</el-col>
              </el-row>
            </div>
            <div v-else>无该设备的参数信息</div>
          </el-collapse-item>
          <el-collapse-item title="部件清单" name="3">
            <el-table
              :data="DetailParts"
              style="width: 100%"
            >
              <el-table-column
                prop="partName"
                label="部件名称"
                width="180"
              >
              </el-table-column>
              <el-table-column label="部件型号" align="center" prop="partModel">
              </el-table-column>
              <el-table-column label="部件代码" align="center" prop="partCode">
              </el-table-column>
              <el-table-column label="部件类型" align="center" prop="partType">
              </el-table-column>
              <el-table-column label="适用设备类型" align="center" prop="applicableDeviceType">
              </el-table-column>
              <el-table-column label="数量" align="center" prop="partCount">
              </el-table-column>
              <el-table-column label="计量单位" align="center" prop="measurementUnit">
              </el-table-column>
            </el-table>
          </el-collapse-item>
          <el-collapse-item title="零件清单" name="4">
            <el-table
              :data="DetailKits"
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

    </el-drawer>

  </div>
</template>
<style lang="scss">
.spec-dialog .el-dialog__body {
  padding: 3px 30px;
  height: 500px;
  overflow: auto;
}

.status-icon {
  width: 8px;
  height: 8px;
  border-radius: 1px;
  margin-right: 5px;
  position: relative;
  left: 32px;
  bottom: -15px;
}

.icon1 {
  background-color: #19be6b;
}

.icon0 {
  background-color: #eb194d;
}
</style>
<script>
import {
  confirmIsBuy,
  getEquipmentParam,
  getEquipmentKitAndPart,
  listPss

} from '@/api/towerparam/equipmentrequire'
import { treeselect } from '@/api/system/dept'

export default {
  name: 'equipmentIsBuy',
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
        equipmentNumber: undefined,
        equipmentName: undefined,
        vender: undefined,
        equipmentType: undefined,
        rightsUnit: undefined,
        belongWarehouse: undefined,
        projectAddress: undefined,
        projectDate: undefined,
        initDate: undefined,
        state: undefined
      },
      openDetail: false,
      openAudit: false,
      Detail: {},
      //添加时选择的设备类别的设备参数
      EquipmentDetail: { id: '' },
      //可用仓库
      warehouses: [],
      //设备类别
      applicableDeviceTypeS: [],
      // 所属部门ID字典
      depart_idOptions: [],
      activeNames: ['1'],
      //部件清单
      EquipmentPartsList: [],
      //零件清单
      EquipmentKitsList: [],
      equipmentId: -1,
      //计量单位
      measurement_unitS: [],
      //适用部件类型
      applicableKitTypeS: [],
      //详情部件
      DetailParts: [],
      //详情零件
      DetailKits: [],
      //详情页抽屉展示
      drawer: false,
      openUpdate: false,
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      listPss(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail = false
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.queryParams.equipmentName = ''
      this.queryParams.initDate = ''
      this.queryParams.projectDate = ''
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 确认采购按钮操作 */
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
    //设备需求详情
    handleDetail(row) {
      this.getTreeselect()
      this.Detail = row
      getEquipmentKitAndPart(row.id).then((res) => {
        this.DetailKits = res.data.amEquipmentRequireKits
        this.DetailParts = res.data.amEquipmentRequireParts
      })
      getEquipmentParam(row.standardModel, row.equipmentType).then(response => {
        this.EquipmentDetail = response.data
      })
      this.drawer = true
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
      this.download('/asset/equipmentrequire/exportBuy', {
        ...this.queryParams
      }, `equipmentBuy_${new Date().getTime()}.xlsx`)
    }
  },
}
</script>
