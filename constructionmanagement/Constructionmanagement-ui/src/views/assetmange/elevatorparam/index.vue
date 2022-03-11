<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="厂家" prop="vender">
        <el-input
          v-model="queryParams.vender"
          placeholder="请输入生产厂家"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="录入日期">
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
          v-hasPermi="['asset:manage:elevatorparam:add']"
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
          v-hasPermi="['asset:manage:elevatorparam:remove']"
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
          v-hasPermi="['system:post:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--显示表格-->
    <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column
        type="index"
        width="50"
      >
      </el-table-column>
      <el-table-column label="生产厂家" align="center" prop="vender"/>
      <el-table-column label="吊笼重量(kg)" align="center" prop="cageWeight"/>
      <el-table-column label="标准节重量(kg)" align="center" prop="standardSectionWeight"/>
      <el-table-column label="最大高度(m)" align="center" prop="maxHeight"/>
      <el-table-column label="额定载荷(kg)" align="center" prop="specifiedLoad"/>
      <el-table-column label="额定乘员(人)" align="center" prop="ratedOccupant"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['asset:manage:elevatorparam:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['asset:manage:elevatorparam:remove']"
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
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-steps :active="active" finish-status="success">
        <el-step title="基本信息录入"></el-step>
        <el-step title="部件绑定"></el-step>
      </el-steps>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <div v-show="steps1">
          <el-row>
            <el-col :span="8">
              <el-form-item label="厂家" prop="vender">
                <el-input v-model="form.vender" placeholder="请输入厂家名称"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="底笼尺寸" prop="bottomCageSize">
                <el-input v-model="form.bottomCageSize" placeholder="请输入底笼尺寸（长、宽、高）,单位为：m"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="吊笼内空尺寸" prop="cageSize">
                <el-input v-model="form.cageSize" placeholder="请输入吊笼内空尺寸（长、宽、高）,单位为：m"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="吊笼重量" prop="cageWeight">
                <el-input v-model.number="form.cageWeight" placeholder="请输入吊笼重量,单位为：kg"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="标准节" prop="standardKnot">
                <el-input v-model="form.standardKnot" placeholder="请输入标准节（长、宽、高）,单位为：m"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="标准节重量" prop="standardSectionWeight">
                <el-input v-model.number="form.standardSectionWeight" placeholder="请输入标准节重量,单位为:kg"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="最大高度" prop="maxHeight">
                <el-input v-model.number="form.maxHeight" placeholder="请输入最大高度,单位为:m"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="额定载荷" prop="specifiedLoad">
                <el-input v-model.number="form.specifiedLoad" placeholder="请输入额定载荷,单位为:kg"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="额定乘员" prop="ratedOccupant">
                <el-input v-model.number="form.ratedOccupant" placeholder="请输入额定乘员,单位为:人"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="起升速度" prop="upDownSpeed">
                <el-input v-model.number="form.upDownSpeed" placeholder="请输入起升速度,单位为:m/min"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="总功率" prop="totalPower">
                <el-input v-model.number="form.totalPower" placeholder="请输入起升速度,单位为: m/min"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="额定电压" prop="ratedVoltage">
                <el-input v-model.number="form.ratedVoltage" placeholder="请输入额定电压,单位为: v"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="额定电流" prop="ratedCurrent">
                <el-input v-model.number="form.ratedCurrent" placeholder="请输入额定电流,单位为: A"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="减速机速比" prop="reduceSpeedRatio">
                <el-input v-model="form.reduceSpeedRatio" placeholder="请输入减速机速比"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="限速器制动力矩" prop="speedLimiterBrakeTorque">
                <el-input v-model.number="form.speedLimiterBrakeTorque" placeholder="请输入限速器制动力矩,单位为:KN"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="制动速度" prop="retroSpeed">
                <el-input v-model.number="form.retroSpeed" placeholder="请输入制动速度,单位为: m/min"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="防坠器" prop="protectorId">
                <template>
                  <el-select v-model="form.protectorId" clearable placeholder="请选择">
                    <el-option
                      v-for="item in this.protectors"
                      :key="item.value"
                      :label="item.standard"
                      :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </template>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="附着要求" prop="attachmentRequirements">
            <el-input v-model="form.attachmentRequirements" placeholder="请输入附着要求"/>
          </el-form-item>

          <el-form-item label="基础要求" prop="basicRequirements">
            <el-input v-model="form.basicRequirements" placeholder="请输入基础要求"/>
          </el-form-item>
          <el-form-item label="供电要求" prop="supplyPowerRequirements">
            <el-input v-model="form.supplyPowerRequirements" placeholder="请输入供电要求"/>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" placeholder="请输入备注"/>
          </el-form-item>
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
    <el-dialog :title="title" :visible.sync="openDetail" width="40%" class="spec-dialog" append-to-body>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              生产厂家: {{ elevatorParamDetail.vender }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              底笼尺寸: {{ elevatorParamDetail.bottomCageSize }} m
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              吊笼内空尺寸: {{ elevatorParamDetail.cageSize }} m
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              吊笼重量: {{ elevatorParamDetail.cageWeight }} kg
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              标准节: {{ elevatorParamDetail.standardKnot }} m
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              标准节重量: {{ elevatorParamDetail.standardSectionWeight }} kg
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              最大高度: {{ elevatorParamDetail.maxHeight }} m
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              额定载荷: {{ elevatorParamDetail.specifiedLoad }} kg
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              额定乘员: {{ elevatorParamDetail.ratedOccupant }} 人
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              起升速度: {{ elevatorParamDetail.upDownSpeed }} m/min
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              总功率: {{ elevatorParamDetail.totalPower }} KW
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              额定电压: {{ elevatorParamDetail.ratedVoltage }} v
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              额定电流: {{ elevatorParamDetail.ratedCurrent }} A
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              减速机速比: {{ elevatorParamDetail.reduceSpeedRatio }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              限速器制动力矩: {{ elevatorParamDetail.speedLimiterBrakeTorque }} KN
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              制动速度: {{ elevatorParamDetail.retroSpeed }} m/min
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              防坠器: {{ elevatorParamDetail.protectorId }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              附着要求: {{ elevatorParamDetail.attachmentRequirements }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              基础要求: {{ elevatorParamDetail.basicRequirements }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              供电要求: {{ elevatorParamDetail.supplyPowerRequirements }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <ul class="list-group">
          <li class="list-group-item">
            备注: {{ elevatorParamDetail.remark }}
          </li>
        </ul>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              录入人: {{ elevatorParamDetail.insertPerson }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              录入时间: {{ parseTime(elevatorParamDetail.insertDate) }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              录入人部门: {{getDeptName(elevatorParamDetail.insertPersonDepartId)  }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item" >
              更新人部门: {{ getDeptName(elevatorParamDetail.updatePersonDepartId) }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              更新人: {{ elevatorParamDetail.updatePerson }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              录入时间: {{ parseTime(elevatorParamDetail.updateDate) }}
            </li>
          </ul>
        </el-col>
      </el-row>

      <el-row v-if="kits==undefined||kits.length==0">
        <ul class="list-group">
          <li class="list-group-item">
            配件 ：暂时无配件信息
          </li>
        </ul>
      </el-row>
      <div v-else>
        <br/> <br/>
        <el-col :span="8" :xs="100">
          配件名称
        </el-col>
        <el-col :span="8" :xs="100">
          单位
        </el-col>
        <el-col :span="8" :xs="100">
          数量
        </el-col>
        <el-row v-for="kit in this.kits">
          <el-col :span="8" :xs="100">
            <ul class="list-group">
              <li class="list-group-item">
                {{ kit.amKitParam.kitName }}.
              </li>
            </ul>
          </el-col>
          <el-col :span="8" :xs="100">
            <ul class="list-group">
              <li class="list-group-item">
                {{ kit.amKitParam.measurementUnit }}.
              </li>
            </ul>
          </el-col>
          <el-col :span="8" :xs="100">
            <ul class="list-group">
              <li class="list-group-item">
                {{ kit.kitCount }}.
              </li>
            </ul>
          </el-col>
        </el-row>
      </div>
      <el-row v-if="Parts==undefined||Parts.length==0">
        <ul class="list-group">
          <li class="list-group-item">
            部件 ：暂时无部件信息
          </li>
        </ul>
      </el-row>
      <div v-else>
        <br/><br/>
        <el-row>
          <el-col :span="6" :xs="100">
            部件名称
          </el-col>
          <el-col :span="6" :xs="100">
            部件型号
          </el-col>
<!--          <el-col :span="6" :xs="100">-->
<!--            部件类别-->
<!--          </el-col>-->
          <el-col :span="6" :xs="100">
            数量:
          </el-col>
        </el-row>
        <el-row v-for="part in this.Parts">
          <el-col :span="6" :xs="100">
            <ul class="list-group">
              <li class="list-group-item">
                {{ part.amPartParam.partName }}.
              </li>
            </ul>
          </el-col>
          <el-col :span="6" :xs="100">
            <ul class="list-group">
              <li class="list-group-item">
                {{ part.amPartParam.partModel }}.
              </li>
            </ul>
          </el-col>
<!--          <el-col :span="6" :xs="100">-->
<!--            <ul class="list-group">-->
<!--              <li class="list-group-item">-->
<!--                {{ part.partType }}.-->
<!--              </li>-->
<!--            </ul>-->
<!--          </el-col>-->
          <el-col :span="6" :xs="100">
            <ul class="list-group">
              <li class="list-group-item">
                {{ part.partCount }}.
              </li>
            </ul>
          </el-col>
        </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
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
  listElevatorParam,
  getElevatorKitAndPart,
  addElevatorParam,
  delElevatorParam,
  updateElevatorParam,
  getProtector
} from '@/api/towerparam/elevatorparam'
import { getUsePart } from '@/api/towerparam/towermachineparam'
import { treeselect } from '@/api/system/dept'

export default {
  name: 'elevatorparam',
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
        vender: undefined,
        insertDate: undefined
      },
      //是否加载过部件
      load_parts:false,
      openDetail: false,
      elevatorParamDetail: {},
      kits: [],
      Parts: [],
      protectors: [],
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
        amElevatorParamKits: [],
        amElevatorParamParts: []
      },
      // 所属部门ID字典
      depart_idOptions: [],
      // 表单校验
      rules: {
        cageWeight: [{
          type: 'number', message: '吊笼重量必须为数字值', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|10000)$/,
          message: '吊笼重量范围在0-10000',
          trigger: 'blur'
        }],
        standardSectionWeight: [{
          type: 'number', message: '标准节重量必须为数字值', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '标准节重量范围在0-1000',
          trigger: 'blur'
        }],
        maxHeight: [{
          type: 'number', message: '最大高度必须为数字值', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '最大高度范围在0-1000',
          trigger: 'blur'
        }],
        specifiedLoad: [{
          type: 'number', message: '额定载荷必须为数字值', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '额定载荷范围在0-1000',
          trigger: 'blur'
        }],
        ratedOccupant: [{
          type: 'number', message: '额定乘员必须为数字值', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '额定乘员范围在0-1000',
          trigger: 'blur'
        }],
        upDownSpeed: [{
          type: 'number', message: '起升速度必须为数字值', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|10000)$/,
          message: '起升速度范围在0-10000',
          trigger: 'blur'
        }],
        totalPower: [{
          type: 'number', message: '总功率必须为数字值', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '总功率范围在0-1000',
          trigger: 'blur'
        }],
        ratedVoltage: [{
          type: 'number', message: '额定电压必须为数字值', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '额定电压范围在0-1000',
          trigger: 'blur'
        }],
        ratedCurrent: [{
          type: 'number', message: '额定电流必须为数字值', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '额定电流范围在0-1000',
          trigger: 'blur'
        }],
        speedLimiterBrakeTorque: [{
          type: 'number', message: '限速器制动力矩必须为数字值', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '限速器制动力矩范围在0-1000',
          trigger: 'blur'
        }],
        retroSpeed: [{
          type: 'number', message: '制动速度必须为数字值', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '制动速度范围在0-1000',
          trigger: 'blur'
        }]
      }
    }
  },
  created() {
    this.getTreeselect()
    this.getList()
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      listElevatorParam(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询部件参数列表 */
    getPartSS() {
      const _this=this
      getUsePart(null).then(response => {
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
        vender: undefined,
        bottomCageSize: undefined,
        cageSize: undefined,
        cageWeight: undefined,
        standardKnot: undefined,
        standardSectionWeight: undefined,
        maxHeight: undefined,
        specifiedLoad: undefined,
        ratedOccupant: undefined,
        upDownSpeed: undefined,
        totalPower: undefined,
        ratedVoltage: undefined,
        ratedCurrent: undefined,
        reduceSpeedRatio: undefined,
        speedLimiterBrakeTorque: undefined,
        retroSpeed: undefined,
        protectorId: undefined,
        attachmentRequirements: undefined,
        basicRequirements: undefined,
        supplyPowerRequirements: undefined,
        remark: undefined
      },
        this.paramsKit = []
      this.paramsPart = []
      this.resetForm('form')
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
    /** 新增按钮操作 */
    handleAdd() {
      getProtector().then((res) => {
        this.protectors = res.data
        console.log('this.protectors' + res.data)
      })
      this.reset()
      this.open = true
      this.title = '添加'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      getProtector().then((res) => {
        this.protectors = res.data
        console.log('this.protectors' + res.data)
      })

      this.reset()
      this.form = row
      getElevatorKitAndPart(row.id).then((res) => {
        this.paramsKit = res.data.amElevatorParamKits
        this.paramsPart = res.data.amElevatorParamParts
      })
      this.open = true
      this.title = '修改'
    },
    /** 提交按钮 */
    submitForm: function() {
      this.buildParamParts()
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            this.form.amElevatorParamParts = this.paramsPart
            updateElevatorParam(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            this.form.amElevatorParamParts = this.paramsPart
            addElevatorParam(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
        this.value=[]
        this.amounts=[]
        this.paramsPart = []
        this.load_parts=false
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const postIds = row.id || this.ids
      this.$modal.confirm('是否确认删除？').then(function() {
        return delElevatorParam(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    handleDetail(row) {
      const pid = row.id || 0
      getElevatorKitAndPart(pid).then((res) => {
        this.kits = res.data.amPartParamKits
        this.Parts = res.data.amElevatorParamParts
      })
      this.elevatorParamDetail = row
      this.openDetail = true
      this.title = '详情'
    },
    previous() {
      if (this.active-- > 2) this.active = 0
      this.steps1 = true
      this.steps2 = false
    },
    next() {
      if(!this.load_parts) {
        this.getPartSS()
        this.load_parts=true
      }
      if (this.active++ > 2) this.active = 0
      this.steps1 = false
      this.steps2 = true
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download()
    }
  }
}
</script>
