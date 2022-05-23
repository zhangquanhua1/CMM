<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="厂家" prop="vender" label-width="68px">
        <el-input
          v-model="queryParams.vender"
          placeholder="请输入厂家名字"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备型号" prop="towerMachineModel" label-width="68px">
        <el-input
          v-model="queryParams.towerMachineModel"
          placeholder="请输入设备型号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备名称" prop="towerMachineName" label-width="68px">
        <el-input
          v-model="queryParams.towerMachineName"
          placeholder="请输入设备名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="设备类别" prop="towerMachineType">
        <el-select v-model="queryParams.towerMachineType" placeholder="请选择设备类别">
          <el-option
            v-for="dict in applicableDeviceTypeS"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="录入时间">
        <el-date-picker
          v-model="queryParams.insertDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择录入日期"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['asset:manage:towermachine:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['asset:manage:towermachine:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['asset:manage:towermachine:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--显示表格-->
    <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
<!--      <el-table-column-->
<!--        type="index"-->
<!--        width="50"-->
<!--      >-->
<!--      </el-table-column>-->
      <el-table-column label="设备名称" align="center" prop="towerMachineName"/>
      <el-table-column label="设备型号" align="center" prop="towerMachineModel"/>
      <el-table-column label="厂家" align="center" prop="vender"/>
      <el-table-column label="设备类别" align="center" prop="towerMachineType"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['asset:manage:towermachine:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['asset:manage:towermachine:remove']"
          >删除
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
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px" :close-on-click-modal="false" v-dialog-drag append-to-body>
      <el-steps :active="active" finish-status="success">
        <el-step title="设备信息录入"></el-step>
        <el-step title="部件绑定"></el-step>
      </el-steps>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <div v-show="steps1">
          <el-row>
            <el-col :span="8">
              <el-form-item label="厂家" prop="vender">
                <el-input v-model="form.vender" placeholder="请输入厂家名称"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="设备型号" prop="towerMachineModel">
                <el-input v-model="form.towerMachineModel" placeholder="请输入设备型号"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="设备名称" prop="towerMachineName">
                <el-input v-model="form.towerMachineName" placeholder="请输入设备名称"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="设备类别" prop="towerMachineType">
                <el-select v-model="form.towerMachineType" placeholder="请选择设备类别">
                  <el-option
                    v-for="dict in applicableDeviceTypeS"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="额定起重力距" prop="ratedLiftingDistance">
                <el-input v-model.number="form.ratedLiftingDistance" placeholder="请输入额定起重力距，单位为:KN/m"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="最大起重量" prop="maximumLiftingWeight">
                <el-input v-model.number="form.maximumLiftingWeight" placeholder="请输入最大起重量,单位为:T"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="最大幅度额定起重量" prop="maximumRatedWeight">
                <el-input v-model.number="form.maximumRatedWeight" placeholder="请输入最大幅度额定起重量,单位为:T"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="最大起重量工作幅度" prop="maximumStartingWeightWorkingRange">
                <el-input v-model.number="form.maximumStartingWeightWorkingRange" placeholder="请输入最大起重量工作幅度,单位为:M"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="最大工作幅度" prop="maximumRangeWork">
                <el-input v-model.number="form.maximumRangeWork" placeholder="请输入最大工作幅度,单位为:M"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="最大起升高度" prop="maximumHeightLift">
                <el-input v-model.number="form.maximumHeightLift" placeholder="请输入最大起升高度,单位为:M"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="起升速度" prop="upDownSpeed">
                <el-input v-model.number="form.upDownSpeed" placeholder="请输入起升速度,单位为: m/min"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="变幅速度" prop="variableAmplitudeSpeed">
                <el-input v-model.number="form.variableAmplitudeSpeed" placeholder="请输入变幅速度,单位为: m/min"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="回转速度" prop="swivellingSpeed">
                <el-input v-model.number="form.swivellingSpeed" placeholder="请输入回转速度,单位为: r/min"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="顶升速度" prop="upSpeed">
                <el-input v-model.number="form.upSpeed" placeholder="请输入顶升速度,单位为: m/min"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="塔机自重" prop="towerMachineWeight">
                <el-input v-model.number="form.towerMachineWeight" placeholder="请输入塔机自重,单位为: t"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="平衡重量" prop="balanceWeight">
                <el-input v-model.number="form.balanceWeight" placeholder="请输入平衡重量,单位为: t"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="工作电压" prop="workingVoltage">
                <el-input v-model.number="form.workingVoltage" placeholder="请输入工作电压,单位为: v"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="装机总容量" prop="totalInstalledCapacity">
                <el-input v-model.number="form.totalInstalledCapacity" placeholder="请输入装机总容量,单位为: KW"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="电机功率" prop="motorPower">
                <el-input v-model.number="form.motorPower" placeholder="请输入电机功率,单位为: KW"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="变频器功率" prop="frequencyConverterPower">
                <el-input v-model.number="form.frequencyConverterPower" placeholder="请输入工作电压,单位为: KW"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="标准节规格" prop="standardSectionSpecifications">
                <el-input v-model="form.standardSectionSpecifications" placeholder="请输入标准节规格"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="附墙框规格" prop="attachedWallFrameSpecifications">
                <el-input v-model="form.attachedWallFrameSpecifications" placeholder="请输入附墙框规格"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="规格归类" prop="specificationClassification">
                <el-input v-model="form.specificationClassification" placeholder="请输入规格归类"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="备注" prop="remark">
                <el-input v-model="form.remark" placeholder="请输入备注"/>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <div v-show="steps2" align="center">
          <el-transfer v-model="value" :button-texts="['移除', '添加']" :data="partData" :titles="titles" filterable>
            <div slot-scope="{ option }">
              <div style="float: left">
                {{ option.label }}
              </div>
              <div style="float: right">
                <input class="input" placeholder="数量" style="width:50px;" type="text" value="1" :id="option.key"/>
              </div>
            </div>
          </el-transfer>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button style="margin-top: 12px;" @click="previous" v-if="active>0">上一步</el-button>
        <el-button style="margin-top: 12px;" @click="next" v-if="active<1">下一步</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--    详情弹窗-->
    <el-drawer
      title="设备参数详情"
      size="60%"
      :visible.sync="openDetail"
      :with-header="true"
    >
      <div style="margin-left: 10px">
        <tmpDetail :detail="this.towerMachineParamDetail" :kits="this.kits" :parts="this.Parts">/</tmpDetail>
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
  listTowerMachineParam,
  getKitAndPart,
  delTowerMachineParam,
  addTowerMachineParam,
  updateTowerMachineParam,getUsePart
} from '@/api/towerparam/towermachineparam'
import { treeselect } from '@/api/system/dept'
import tmpDetail from './tmpDetail'
export default {
  name: 'towerMachineParam',
  // dicts: ['sys_normal_disable'],
  components: {tmpDetail},
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
        vender: undefined,
        towerMachineModel: undefined,
        towerMachineName: undefined,
        towerMachineType: undefined,
        insertDate: undefined
      },
      openDetail: false,
      towerMachineParamDetail: {},
      kits: [],
      Parts: [],
      //设备名称
      applicableDeviceTypeS: [],
      active: 0,
      steps1: true,
      steps2: false,
      titles:['待选择部件','已选择部件'],
      //获取部件
      partData: [],
      //获取添加的部件数量
      amounts: [],
      //部件id
      value: [],
      //记录已选择的设备名
      last_towerMachineName:'',
      // 表单参数
      paramsPart: [{part_id: '', partCount: '' }],
      form: {
        amTowerMachineParamkits: [],
        amTowerMachineParamParts: []
      },
      // 所属部门ID字典
      depart_idOptions: [],
      // 表单校验
      rules: {
        towerMachineModel: [
          { required: true, message: '塔机型号不能为空', trigger: 'blur' }
        ],
        towerMachineName: [
          { required: true, message: '塔机名称不能为空', trigger: 'blur' }
        ],
        towerMachineType: [
          { required: true, message: '设备类别不能为空', trigger: 'blur' }
        ],
        ratedLiftingDistance: [{
          type: 'number', message: '额定起重力距必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '额定起重力距范围在0-999',
          trigger: 'blur'
        }],
        maximumLiftingWeight: [{
          type: 'number', message: '最大起重量必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '最大起重量范围在0-999',
          trigger: 'blur'
        }],
        maximumRatedWeight: [{
          type: 'number', message: '最大幅度额定起重量必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '最大幅度额定起重量范围在0-999',
          trigger: 'blur'
        }],
        maximumStartingWeightWorkingRange: [{
          type: 'number', message: '最大起重量工作幅度必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '最大起重量工作幅度范围在0-999',
          trigger: 'blur'
        }],
        maximumRangeWork: [{
          type: 'number', message: '最大工作幅度必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '最大工作幅度范围在0-999',
          trigger: 'blur'
        }],
        maximumHeightLift: [{
          type: 'number', message: '最大起升高度必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '最大起升高度范围在0-999',
          trigger: 'blur'
        }],
        upDownSpeed: [{
          type: 'number', message: '起升速度必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '起升速度范围在0-999',
          trigger: 'blur'
        }],
        variableAmplitudeSpeed: [{
          type: 'number', message: '变幅速度必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '变幅速度范围在0-999',
          trigger: 'blur'
        }],
        swivellingSpeed: [{
          type: 'number', message: '回转速度必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '回转速度范围在0-999',
          trigger: 'blur'
        }],
        upSpeed: [{
          type: 'number', message: '顶升速度必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '顶升速度范围在0-999',
          trigger: 'blur'
        }],
        towerMachineWeight: [{
          type: 'number', message: '塔机自重必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '塔机自重范围在0-999',
          trigger: 'blur'
        }],
        balanceWeight: [{
          type: 'number', message: '平衡重量必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '平衡重量范围在0-999',
          trigger: 'blur'
        }],
        workingVoltage: [{
          type: 'number', message: '工作电压必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '工作电压范围在0-999',
          trigger: 'blur'
        }],
        totalInstalledCapacity: [{
          type: 'number', message: '装机总容量必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '装机总容量范围在0-999',
          trigger: 'blur'
        }],
        motorPower: [{
          type: 'number', message: '电机功率必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '电机功率范围在0-999',
          trigger: 'blur'
        }],
        frequencyConverterPower: [{
          type: 'number', message: '变频器功率必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '变频器功率范围在0-999',
          trigger: 'blur'
        }]
      }
    }
  },
  created() {
    this.getList()
    this.getTreeselect();
    this.getDicts('applicableDeviceType').then(response => {
      this.applicableDeviceTypeS = response.data
    })

  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      listTowerMachineParam(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询部件参数列表 */
    getPartSS() {
      const _this=this
      getUsePart(this.form).then(response => {
        response.data.forEach(function(item) {
          _this.partData.push({ key: item.id, label: item.partName })
        })
      });
    },
    //获取部件的数量
    getAmounts() {
      //获取输入框数值
      const _this = this
      _this.amounts = []
      this.value.forEach(function(id) {
        const num = document.getElementById(id).value
        if (num && num.length > 0) {
          _this.amounts.push(num)
        } else {
          _this.amounts.push(1)
        }
      })
    },
    //构建ParamPart对象
    buildParamParts(){
      this.getAmounts();
      this.paramsPart=this.amounts.map((partCount,i)=>({partCount, part_id: this.value[i]}))
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        towerMachineModel: undefined,
        towerMachineName: undefined,
        towerMachineType: undefined,
        vender: undefined,
        ratedLiftingDistance: undefined,
        maximumLiftingWeight: undefined,
        maximumRatedWeight: undefined,
        maximumStartingWeightWorkingRange: undefined,
        maximumRangeWork: undefined,
        maximumHeightLift: undefined,
        upDownSpeed: undefined,
        variableAmplitudeSpeed: undefined,
        swivellingSpeed: undefined,
        upSpeed: undefined,
        towerMachineWeight: undefined,
        balanceWeight: undefined,
        workingVoltage: undefined,
        totalInstalledCapacity: undefined,
        motorPower: undefined,
        frequencyConverterPower: undefined,
        standardSectionSpecifications: undefined,
        attachedWallFrameSpecifications: undefined,
        specificationClassification: undefined,
        remark: undefined
      },
      this.paramsPart = []
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.form = JSON.parse(JSON.stringify(row))
      this.form.amTowerMachineParamParts = this.paramsPart
      this.open = true
      this.title = '修改'
    },
    /** 提交按钮 */
    submitForm: function() {
      this.buildParamParts()
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            this.form.amTowerMachineParamParts = this.paramsPart
            updateTowerMachineParam(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            this.form.amTowerMachineParamParts = this.paramsPart
            addTowerMachineParam(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
        this.value=[]
        this.amounts=[]
        this.paramsPart = []
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const postIds = row.id || this.ids
      this.$modal.confirm('是否确认删除该塔机参数？').then(function() {
        return delTowerMachineParam(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    handleDetail(row) {
      const pid = row.id || 0
      getKitAndPart(pid).then((res) => {
        this.kits = res.data.amPartParamKits
        this.Parts = res.data.amTowerMachineParamParts
      })
      this.towerMachineParamDetail = row
      this.openDetail = true
      this.title = '详情'
    },
    previous() {
      this.last_towerMachineName=this.form.towerMachineType
      if (this.active-- > 2) this.active = 0
      this.steps1 = true
      this.steps2 = false
    },
    next() {
      if(this.last_towerMachineName!=this.form.towerMachineType){
        this.partData=[]
        this.getPartSS()
      }
      if (this.active++ > 2) this.active = 0
      this.steps1 = false
      this.steps2 = true
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('/asset/manage/export', {
        ...this.queryParams
      }, `Equipmentparam_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
