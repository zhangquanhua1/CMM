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
        <el-select v-model="form.partType" placeholder="请选择部件类别">
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['asset:manage:partrequire:add']"
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
          v-hasPermi="['asset:manage:partrequire:remove']"
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
      <el-table-column label="审核状态" align="center" prop="state" :filters="[{ text: '待审核', value: 0 },
      { text: '通过', value: 1 },{ text: '拒绝', value: 2 }]"
                       :filter-method="filterState"
      >
        <template slot-scope="scope">
          <el-tag :type="'primary'" v-if="scope.row.state==0">待审核</el-tag>
          <el-tag :type="'success'" v-else-if="scope.row.state==1">通过</el-tag>
          <el-tag :type="'danger'" v-else>拒绝</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="审核意见" align="center" prop="auditAdvice">
        <template slot-scope="scope">
          <span v-if="scope.row.state==0">待审核中</span>
          <span v-else>{{ scope.row.auditAdvice }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.state!=1"
                     size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleUpdate(scope.row)"
                     v-hasPermi="['asset:manage:partrequire:edit']"
          >修改
          </el-button>
          <el-button v-if="scope.row.state!=1"
                     size="mini"
                     type="text"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.row)"
                     v-hasPermi="['asset:manage:partrequire:remove']"
          >删除
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleAudit(scope.row)"
            v-hasPermi="['asset:manage:partrequire:audit']"
          >审核
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
    <el-dialog :title="title" :visible.sync="open" width="1200px" class="spec-dialog" v-dialog-drag v-dialog-drag-width v-dialog-drag-height append-to-body>
      <el-steps :active="active" finish-status="success">
        <el-step title="部件信息录入"></el-step>
        <el-step title="配件绑定"></el-step>
      </el-steps>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <div v-show="steps1">
        <el-row>
          <el-col :span="8">
            <el-form-item label="产品编号" prop="productNum">
              <el-input v-model.number="form.productNum" placeholder="请输入产品编号"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="生产厂家" prop="vender">
              <el-input v-model="form.vender" placeholder="请输入生产厂家"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="部件类别" prop="partType">
              <el-select v-model="form.partType" placeholder="请选择部件类别">
                <el-option
                  v-for="dict in applicableKitTypeS"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="所属设备" prop="equipment">
              <el-select v-model="form.equipment"  placeholder="请选择所属设备">
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
            <el-form-item label="资产总计" prop="totalAssets">
              <el-input v-model.number="form.totalAssets" placeholder="请输入资产总计"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="产权单位" prop="rightsUnit">
              <el-input v-model="form.rightsUnit" placeholder="请输入产权单位"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="配件属性" prop="kitProperties">
              <el-input v-model="form.kitProperties" placeholder="请输入配件属性"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="部件名称" prop="partName">
              <el-input v-model="form.partName" placeholder="请输入部件名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="配件规格" prop="kitStandard">
              <el-input v-model="form.kitStandard" placeholder="请输入配件规格"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="臂长" prop="brachium">
              <el-input v-model.number="form.brachium" placeholder="请输入臂长(米)"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="设备型号" prop="unitType">
              <el-select v-model="form.unitType"
                          placeholder="请选择设备型号">
                <el-option
                  v-for="dict in this.equipmentModelS"
                  :key="dict.id"
                  :label="dict.towerMachineModel"
                  :value="dict.towerMachineModel"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <input type="hidden" v-model="equipmentModel"/>
          <el-col :span="8">
            <el-form-item label="计量单位" prop="measurementUnit">
              <el-select v-model="form.measurementUnit" placeholder="请选择计量单位">
                <el-option
                  v-for="dict in measurement_unitS"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="标准节高度" prop="standardSectionHeight">
              <el-input v-model.number="form.standardSectionHeight" placeholder="请输入标准节高度(米)"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="部件型号" prop="partModel">
              <el-input v-model="form.partModel" placeholder="请输入部件型号"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="部件代码" prop="partCode">
              <el-input v-model="form.partCode" placeholder="请输入部件代码"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="数量" prop="amount">
              <el-input v-model.number="form.amount" placeholder="请输入数量"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="单价" prop="singlePrice">
              <el-input v-model.number="form.singlePrice" placeholder="请输入单价"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否整机配件" prop="wholeMachineAccessories">
              <template>
                <el-radio-group v-model="form.wholeMachineAccessories">
                  <el-radio :label="0">否</el-radio>
                  <el-radio :label="1">是</el-radio>
                </el-radio-group>
              </template>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="需求发起人" prop="demandSponsors">
              <el-input v-model="form.demandSponsors" placeholder="请输入需求发起人"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="采购原因" prop="procurementCauses">
              <el-input v-model="form.procurementCauses" placeholder="请输入采购原因"/>
            </el-form-item>
          </el-col>
        </el-row>
        </div>
        <div v-show="steps2" align="center">
          <el-transfer v-model="value"
                       :button-texts="['移除', '添加']"
                       :data="kitData" :titles="titles" filterable>
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
    <el-dialog :title="title" :visible.sync="openDetail" width="40%" class="spec-dialog" v-dialog-drag append-to-body>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              产品编号: {{ Detail.productNum }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              生产厂家: {{ Detail.vender }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              部件类别: {{ Detail.partType }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              所属设备: {{ Detail.equipment }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              资产总计: {{ Detail.totalAssets }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              产权单位: {{ Detail.rightsUnit }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              配件属性: {{ Detail.kitProperties }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              部件名称: {{ Detail.partName }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              配件规格: {{ Detail.kitStandard }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              臂长: {{ Detail.brachium }} 米
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              设备型号: {{ Detail.unitType }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              计量单位: {{ Detail.measurementUnit }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              标准节高度: {{ Detail.standardSectionHeight }} 米
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              部件型号: {{ Detail.partModel }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              部件代码: {{ Detail.partCode }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              数量: {{ Detail.amount }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              单价: {{ Detail.singlePrice }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              是否整机配件:
              <span v-if="Detail.wholeMachineAccessories>0">是</span>
              <span v-else>否</span>
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              需求发起人: {{ Detail.demandSponsors }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              采购原因: {{ Detail.procurementCauses }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              录入人: {{ Detail.insertPerson }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              录入时间: {{ parseTime(Detail.insertDate) }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              录入人部门: {{getDeptName( Detail.insertPersonDepartId)}}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              更新人部门: {{ getDeptName(Detail.updatePersonDepartId) }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              更新人: {{ Detail.updatePerson }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              录入时间: {{ parseTime(Detail.updateDate) }}
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
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="openAudit" @close="getList" width="25%" v-dialog-drag class="spec-dialog" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="是否通过" prop="auditAdvice">
          <el-radio-group v-model="form.state">
            <el-radio :label="2">否</el-radio>
            <el-radio :label="1">是</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核意见" prop="auditAdvice">
          <el-input type="textarea" autosize v-model="form.auditAdvice" placeholder="请输入审核意见"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAudit">确 定</el-button>
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
  listPartRequire,
  getPartRequireKit,
  addPartRequire,
  delPartRequire,
  updatePartRequire,
  getKitS,
  getEquipmentModel
} from '@/api/towerparam/partrequire'
import { treeselect } from '@/api/system/dept'
export default {
  name: 'partrequire',
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
        productNum: undefined,
        vender: undefined,
        partType: undefined,
        unitType: undefined,
        insertDate: undefined
      },
      openDetail: false,
      Detail: {},
      openAudit: false,
      kits: [],
      // 新增配件参数数组
      paramsKit:[{kid:'',kitCount:''}],
      form: {
        amPartRequireKits: []
      },
      //获取所选所属设备下的设备型号
      getform: {
        towerMachineType: undefined,
      },
      // 设备型号集合
      equipmentModelS:[],
      //计量单位
      measurement_unitS:[],
      //所属设备
      applicableDeviceTypeS: [],
      //部件类别
      applicableKitTypeS:[],
      active: 0,
      steps1:true,
      steps2:false,
      kitData:[],
      titles:['待选择零件','已选择零件'],
      //配件数量
      amounts:[],
      //配件id
      value:[],
      //记录已选择的部件名
      last_partName:'',
      // 所属部门ID字典
      depart_idOptions: [],
      // 表单校验
      rules: {
        productNum: [
          { required: true, message: '产品编号不能为空', trigger: 'blur' }, {
            type: 'number', message: '产品编号必须为数字值', trigger: 'blur'
          }
        ],
        brachium: [{
          type: 'number', message: '臂长必须为数字值', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '臂长范围在0-1000 米',
          trigger: 'blur'
        }],
        standardSectionHeight: [{
          type: 'number', message: '标准节高度', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '标准节高度范围在0-1000米',
          trigger: 'blur'
        }],
        amount: [{
          type: 'number', message: '数量必须为数字值', trigger: 'blur'
        }],
        singlePrice: [{
          type: 'number', message: '单价必须为数字值', trigger: 'blur'
        }],
        totalAssets: [{
          type: 'number', message: '资产总计必须为数字值', trigger: 'blur'
        }]
      }
    }
  },
  computed: {
    equipmentModel:function() {
      var result =[];
      this.getform.towerMachineType=this.form.equipment
      getEquipmentModel(this.getform).then(response => {
      this.equipmentModelS=response.rows
      });
      return result;
    }
  },
  created() {
    this.getDicts('measurement_unit').then(response => {
      this.measurement_unitS= response.data
    })
    this.getDicts('applicableDeviceType').then(response => {
      this.applicableDeviceTypeS = response.data
    })
    this.getDicts('parts_type').then(response => {
      this.applicableKitTypeS = response.data})
    this.getList()
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      listPartRequire(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 查询零配件参数列表 */
    getKitSS() {
      const _this=this
      getKitS(this.form).then(response => {
        response.data.forEach(function(item) {
          _this.kitData.push({ key: item.id, label: item.kitName })
        })
      });
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
    //获取配件的数量
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
    //构建ParamsKit对象
    buildParamsKit(){
      this.getAmounts();
      this.paramsKit=this.amounts.map((kitCount,i)=>({kitCount, kit_id: this.value[i]}))
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail = false
      this.openAudit = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        productNum: undefined,
        vender: undefined,
        partType: undefined,
        equipment: undefined,
        totalAssets: undefined,
        rightsUnit: undefined,
        kitProperties: undefined,
        partName: undefined,
        kitStandard: undefined,
        brachium: undefined,
        unitType: undefined,
        standardSectionHeight: undefined,
        partModel: undefined,
        measurementUnit: undefined,
        partCode: undefined,
        amount: undefined,
        singlePrice: undefined,
        wholeMachineAccessories: undefined,
        demandSponsors: undefined,
        procurementCauses: undefined
      },
        this.paramsKit = []
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
      getPartRequireKit(row.id).then((res) => {
        this.paramsKit = res.data
      })
      this.form.amPartRequireKits = this.paramsKit
      this.open = true
      this.title = '修改'
    },
    /** 提交按钮 */
    submitForm: function() {
      this.buildParamsKit();
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            this.form.amPartRequireKits = this.paramsKit
            updatePartRequire(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            this.form.amPartRequireKits = this.paramsKit
            addPartRequire(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
        this.value=[]
        this.amounts=[]
        this.paramsKit = []
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const postIds = row.id || this.ids
      this.$modal.confirm('是否确认删除？').then(function() {
        return delPartRequire(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    handleDetail(row) {
      this.getTreeselect()
      const pid = row.id || 0
      getPartRequireKit(pid).then((res) => {
        this.kits = res.data
      })
      this.Detail = row
      this.openDetail = true
      this.title = '详情'
    },
    handleAudit(row) {
      this.form = row
      this.openAudit = true
      this.title = '审核'
    },
    submitAudit() {
      this.form.amPartRequireKits = []
      updatePartRequire(this.form).then(response => {
        this.$modal.msgSuccess('修改成功')
        this.openAudit = false
        this.getList()
      })
    },
    filterState(value, row) {
      return row.state === value
    },
    previous() {
      this.last_partName=this.form.partType
      if (this.active-- > 2) this.active = 0;
      this.steps1=true;
      this.steps2=false;
    },
    next() {
      if(this.last_partName!=this.form.partType){
        this.kitData=[]
        this.getKitSS();
      }
      if (this.active++ > 2) this.active = 0;
      this.steps1=false;
      this.steps2=true;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download()
    }
  }
}
</script>
