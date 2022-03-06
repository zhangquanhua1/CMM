<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="厂家" prop="vender">
        <el-input
          v-model="queryParams.vender"
          placeholder="请输入厂家名字"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="塔机型号" prop="towerMachineModel">
        <el-input
          v-model="queryParams.towerMachineModel"
          placeholder="请输入塔机型号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="塔机名称" prop="towerMachineName">
        <el-input
          v-model="queryParams.towerMachineName"
          placeholder="请输入塔机名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="塔机类别" prop="towerMachineType">
        <el-input
          v-model="queryParams.towerMachineType"
          placeholder="请输入塔机类别"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
        width="50">
      </el-table-column>
      <el-table-column label="塔机名称" align="center" prop="towerMachineName"/>
      <el-table-column label="塔机型号" align="center" prop="towerMachineModel"/>
      <el-table-column label="厂家" align="center" prop="vender"/>
      <el-table-column label="设备类别" align="center" prop="towerMachineType"/>
      <el-table-column label="备注" align="center"/>
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
    <el-dialog :title="title" :visible.sync="open" width="30%"  class="spec-dialog" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="厂家" prop="vender">
          <el-input v-model="form.vender" placeholder="请输入厂家名称"/>
        </el-form-item>

        <el-form-item label="塔机型号" prop="towerMachineModel">
          <el-input v-model="form.towerMachineModel" placeholder="请输入塔机型号"/>
        </el-form-item>

        <el-form-item label="塔机名称" prop="towerMachineName">
          <el-input v-model="form.towerMachineName" placeholder="请输入塔机名称"/>
        </el-form-item>

        <el-form-item label="设备类别" prop="towerMachineType">
          <el-input v-model="form.towerMachineType" placeholder="请输入设备类别"/>
        </el-form-item>

        <el-form-item label="额定起重力距" prop="ratedLiftingDistance">
          <el-input v-model.number="form.ratedLiftingDistance" placeholder="请输入额定起重力距，单位为:KN/m"/>
        </el-form-item>

        <el-form-item label="最大起重量" prop="maximumLiftingWeight">
          <el-input v-model.number="form.maximumLiftingWeight" placeholder="请输入最大起重量,单位为:T"/>
        </el-form-item>

        <el-form-item label="最大幅度额定起重量" prop="maximumRatedWeight">
          <el-input v-model.number="form.maximumRatedWeight" placeholder="请输入最大幅度额定起重量,单位为:T"/>
        </el-form-item>

        <el-form-item label="最大起重量工作幅度" prop="maximumStartingWeightWorkingRange">
          <el-input v-model.number="form.maximumStartingWeightWorkingRange" placeholder="请输入最大起重量工作幅度,单位为:M"/>
        </el-form-item>
        <el-form-item label="最大工作幅度" prop="maximumRangeWork">
          <el-input v-model.number="form.maximumRangeWork" placeholder="请输入最大工作幅度,单位为:M"/>
        </el-form-item>
        <el-form-item label="最大起升高度" prop="maximumHeightLift">
          <el-input v-model.number="form.maximumHeightLift" placeholder="请输入最大起升高度,单位为:M"/>
        </el-form-item>
        <el-form-item label="起升速度" prop="upDownSpeed">
          <el-input v-model.number="form.upDownSpeed" placeholder="请输入起升速度,单位为: m/min"/>
        </el-form-item>
        <el-form-item label="变幅速度" prop="variableAmplitudeSpeed">
          <el-input v-model.number="form.variableAmplitudeSpeed" placeholder="请输入变幅速度,单位为: m/min"/>
        </el-form-item>
        <el-form-item label="回转速度" prop="swivellingSpeed">
          <el-input v-model.number="form.swivellingSpeed" placeholder="请输入回转速度,单位为: r/min"/>
        </el-form-item>
        <el-form-item label="顶升速度" prop="upSpeed">
          <el-input v-model.number="form.upSpeed" placeholder="请输入顶升速度,单位为: m/min"/>
        </el-form-item>
        <el-form-item label="塔机自重" prop="towerMachineWeight">
          <el-input v-model.number="form.towerMachineWeight" placeholder="请输入塔机自重,单位为: t"/>
        </el-form-item>
        <el-form-item label="平衡重量" prop="balanceWeight">
          <el-input v-model.number="form.balanceWeight" placeholder="请输入平衡重量,单位为: t"/>
        </el-form-item>
        <el-form-item label="工作电压" prop="workingVoltage">
          <el-input v-model.number="form.workingVoltage" placeholder="请输入工作电压,单位为: v"/>
        </el-form-item>
        <el-form-item label="装机总容量" prop="totalInstalledCapacity">
          <el-input v-model.number="form.totalInstalledCapacity" placeholder="请输入装机总容量,单位为: KW"/>
        </el-form-item>
        <el-form-item label="电机功率" prop="motorPower">
          <el-input v-model.number="form.motorPower" placeholder="请输入电机功率,单位为: KW"/>
        </el-form-item>
        <el-form-item label="变频器功率" prop="frequencyConverterPower">
          <el-input v-model.number="form.frequencyConverterPower" placeholder="请输入工作电压,单位为: KW"/>
        </el-form-item>
        <el-form-item label="标准节规格" prop="standardSectionSpecifications">
          <el-input v-model="form.standardSectionSpecifications" placeholder="请输入标准节规格"/>
        </el-form-item>
        <el-form-item label="附墙框规格" prop="attachedWallFrameSpecifications">
          <el-input v-model="form.attachedWallFrameSpecifications" placeholder="请输入附墙框规格"/>
        </el-form-item>
        <el-form-item label="规格归类" prop="specificationClassification">
          <el-input v-model="form.specificationClassification" placeholder="请输入规格归类"/>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>

        <div
          v-for="(item, index) in paramsKit"
          :key="index">
          <el-form-item label="配件名称">
          <el-input v-model="item.kitName" placeholder="请输入配件名称"/>
          </el-form-item>
          <el-form-item label="配件单位">
          <el-input v-model="item.unit" placeholder="请输入配件单位"/>
          </el-form-item>
          <el-form-item label="配件数量">
          <el-input v-model="item.kitCount" placeholder="请输入配件数量"/>
          </el-form-item>
          <el-button size="medium" @click.prevent="removeKit(index)">删除该配件</el-button>
          <br/>
          <br/>
          <br/>
        </div>
        <el-form-item>
          <el-button @click="addKit">新增配件信息</el-button>
        </el-form-item>

        <div
          v-for="(item, index) in paramsPart"
          :key="index">
          <el-form-item label="部件名称">
          <el-input v-model="item.partName" placeholder="请输入部件名称"/>
          </el-form-item>
          <el-form-item label="部件型号">
          <el-input v-model="item.partModel" placeholder="请输入部件型号"/>
          </el-form-item>
          <el-form-item label="部件类型">
          <el-input v-model="item.partType" placeholder="请输入部件类型"/>
          </el-form-item>
          <el-form-item label="部件数量">
          <el-input v-model="item.partCount" placeholder="请输入部件数量"/>
          </el-form-item>
          <el-button @click.prevent="removePart(index)" >删除该部件</el-button>
          <br/>
          <br/>
          <br/>
        </div>
        <el-form-item>
          <el-button @click="addPart">新增部件件信息</el-button>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
<!--    详情弹窗-->
    <el-dialog :title="title" :visible.sync="openDetail" width="40%"  class="spec-dialog" append-to-body>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              塔机型号: {{ towerMachineParamDetail.towerMachineModel }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              塔机名称: {{ towerMachineParamDetail.towerMachineName }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              设备类别: {{ towerMachineParamDetail.towerMachineType }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              厂家: {{ towerMachineParamDetail.vender }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              额定起重力距: {{ towerMachineParamDetail.ratedLiftingDistance }} KN/m
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              最大起重量: {{ towerMachineParamDetail.maximumLiftingWeight }} T
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              最大幅度额定起重量: {{ towerMachineParamDetail.maximumRatedWeight }} T
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              最大起重量工作幅度: {{ towerMachineParamDetail.maximumStartingWeightWorkingRange }} M
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              最大工作幅度: {{ towerMachineParamDetail.maximumRangeWork }} M
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              最大起升高度: {{ towerMachineParamDetail.maximumHeightLift }} M
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              最大幅度额定起重量: {{ towerMachineParamDetail.maximumRatedWeight }} T
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              起升速度: {{ towerMachineParamDetail.upDownSpeed }} m/min
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              变幅速度: {{ towerMachineParamDetail.variableAmplitudeSpeed }} m/min
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              回转速度: {{ towerMachineParamDetail.swivellingSpeed }} r/min
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              顶升速度: {{ towerMachineParamDetail.upSpeed }} m/min
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              塔机自重: {{ towerMachineParamDetail.towerMachineWeight }} t
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              平衡重量: {{ towerMachineParamDetail.balanceWeight }} t
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              工作电压: {{ towerMachineParamDetail.workingVoltage }} v
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              装机总容量: {{ towerMachineParamDetail.totalInstalledCapacity }} KW
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              电机功率: {{ towerMachineParamDetail.motorPower }} KW
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              变频器功率: {{ towerMachineParamDetail.frequencyConverterPower }} KW
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              标准节规格: {{ towerMachineParamDetail.standardSectionSpecifications }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              附墙框规格: {{ towerMachineParamDetail.attachedWallFrameSpecifications }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              规格归类: {{ towerMachineParamDetail.specificationClassification }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
          <ul class="list-group">
            <li class="list-group-item">
              备注: {{ towerMachineParamDetail.remark }}
            </li>
          </ul>
      </el-row>

      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              录入人: {{ towerMachineParamDetail.insertPerson }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              录入时间: {{ parseTime(towerMachineParamDetail.insertDate) }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              录入人部门id: {{ towerMachineParamDetail.insertPersonDepartId }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              更新人部门id: {{ towerMachineParamDetail.updatePersonDepartId }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              更新人: {{ towerMachineParamDetail.updatePerson }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              录入时间: {{ parseTime(towerMachineParamDetail.updateDate) }}
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
             {{ kit.kitName }}.
            </li>
          </ul>
        </el-col>
        <el-col :span="8" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
             {{ kit.unit}}.
            </li>
          </ul>
        </el-col>
        <el-col :span="8" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
             {{ kit.kitCount}}.
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
        <el-row >
          <el-col :span="6" :xs="100">
                部件名称
          </el-col>
          <el-col :span="6" :xs="100">
                部件型号
          </el-col>
          <el-col :span="6" :xs="100">
                部件类别
          </el-col>
          <el-col :span="6" :xs="100">
                数量:
          </el-col>
        </el-row>

      <el-row  v-for="part in this.Parts">
        <el-col :span="6" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
             {{ part.partName }}.
            </li>
          </ul>
        </el-col>
        <el-col :span="6" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              {{ part.partModel}}.
            </li>
          </ul>
        </el-col>
        <el-col :span="6" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              {{ part.partType}}.
            </li>
          </ul>
        </el-col>
        <el-col :span="6" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              {{ part.partCount}}.
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
import { listTowerMachineParam,getKitAndPart, delTowerMachineParam, addTowerMachineParam, updateTowerMachineParam } from '@/api/towerparam/towermachineparam'
export default {
  name: 'towerMachineParam',
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
        towerMachineModel: undefined,
        towerMachineName: undefined,
        towerMachineType: undefined,
        insertDate: undefined
      },
      openDetail: false,
      towerMachineParamDetail:{},
      kits:[],
      Parts:[],
      // 表单参数
      paramsKit:[{kitName:'',unit:'',kitCount:''}],
      paramsPart:[{partName:'',partModel:'',partType:'',partCount:''}],
      form: {
        amTowerMachineParamkits:[],
        amTowerMachineParamParts:[]
      },
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
            type: 'number', message: '额定起重力距必须为数字值',trigger: 'blur' }, {
            pattern: /^(0|[1-9]\d?|10000)$/,
            message: '额定起重力距范围在0-10000',
            trigger: 'blur'
          }],
        maximumLiftingWeight: [{
          type: 'number', message: '最大起重量必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '最大起重量范围在0-1000',
          trigger: 'blur'
        }],
        maximumRatedWeight: [{
          type: 'number', message: '最大幅度额定起重量必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '最大幅度额定起重量范围在0-1000',
          trigger: 'blur'
        }],
        maximumStartingWeightWorkingRange: [{
          type: 'number', message: '最大起重量工作幅度必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '最大起重量工作幅度范围在0-1000',
          trigger: 'blur'
        }],
        maximumRangeWork: [{
          type: 'number', message: '最大工作幅度必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '最大工作幅度范围在0-1000',
          trigger: 'blur'
        }],
        maximumHeightLift: [{
          type: 'number', message: '最大起升高度必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|10000)$/,
          message: '最大起升高度范围在0-10000',
          trigger: 'blur'
        }],
        upDownSpeed: [{
          type: 'number', message: '起升速度必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '起升速度范围在0-1000',
          trigger: 'blur'
        }],
        variableAmplitudeSpeed: [{
          type: 'number', message: '变幅速度必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '变幅速度范围在0-1000',
          trigger: 'blur'
        }],
        swivellingSpeed: [{
          type: 'number', message: '回转速度必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '回转速度范围在0-1000',
          trigger: 'blur'
        }],
        upSpeed: [{
          type: 'number', message: '顶升速度必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '顶升速度范围在0-1000',
          trigger: 'blur'
        }],
        towerMachineWeight: [{
          type: 'number', message: '塔机自重必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '塔机自重范围在0-1000',
          trigger: 'blur'
        }],
        balanceWeight: [{
          type: 'number', message: '平衡重量必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '平衡重量范围在0-1000',
          trigger: 'blur'
        }],
        workingVoltage: [{
          type: 'number', message: '工作电压必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '工作电压范围在0-1000',
          trigger: 'blur'
        }],
        totalInstalledCapacity: [{
          type: 'number', message: '装机总容量必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '装机总容量范围在0-1000',
          trigger: 'blur'
        }],
        motorPower: [{
          type: 'number', message: '电机功率必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '电机功率范围在0-1000',
          trigger: 'blur'
        }],
        frequencyConverterPower: [{
          type: 'number', message: '变频器功率必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '变频器功率范围在0-1000',
          trigger: 'blur'
        }],
      },
    }
  },
  created() {
    this.getList()
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
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail= false
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
        maximumRatedWeight:undefined,
        maximumStartingWeightWorkingRange:undefined ,
        maximumRangeWork:undefined ,
        maximumHeightLift:undefined ,
        upDownSpeed:undefined ,
        variableAmplitudeSpeed:undefined ,
        swivellingSpeed:undefined ,
        upSpeed:undefined ,
        towerMachineWeight:undefined ,
        balanceWeight:undefined ,
        workingVoltage:undefined ,
        totalInstalledCapacity:undefined ,
        motorPower:undefined ,
        frequencyConverterPower:undefined ,
        standardSectionSpecifications:undefined ,
        attachedWallFrameSpecifications:undefined ,
        specificationClassification:undefined ,
        remark:undefined ,
      },
        this.paramsKit=[];
        this.paramsPart=[];
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
      this.queryParams.insertDate=''
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
        this.form = row
        getKitAndPart(row.id).then((res) => {
        this.paramsKit=res.data.amTowerMachineParamkits;
        this.paramsPart=res.data.amTowerMachineParamParts;
      })
        this.form.amTowerMachineParamkits=this.paramsKit;
        this.form.amTowerMachineParamParts=this.paramsPart;
        this.open = true
        this.title = '修改'
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            this.form.amTowerMachineParamkits=this.paramsKit;
            this.form.amTowerMachineParamParts=this.paramsPart;
            updateTowerMachineParam(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            this.form.amTowerMachineParamkits=this.paramsKit;
            this.form.amTowerMachineParamParts=this.paramsPart;
            addTowerMachineParam(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
        this.paramsKit=[];
        this.paramsPart=[];
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
    handleDetail(row){
      const pid= row.id || 0
      getKitAndPart(pid).then((res) => {
         this.kits=res.data.amTowerMachineParamkits;
         this.Parts=res.data.amTowerMachineParamParts;
      })
      this.towerMachineParamDetail = row
      this.openDetail = true
      this.title = '详情'
    },
    removeKit(index) {
        this.paramsKit.splice(index, 1)
    },
    addKit() {
      this.paramsKit.push({ kitName: '', unit: '',kitCount: ''})
    },
    removePart(index) {
      this.paramsPart.splice(index, 1)
    },
    addPart() {
      this.paramsPart.push({partName:'',partModel:'',partType:'',partCount:''})
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download()
    }
  }
}
</script>
