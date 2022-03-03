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

      <el-form-item label="部件类型" prop="partType">
        <el-input
          v-model="queryParams.partType"
          placeholder="请输入部件类型"
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
      <el-table-column label="审核状态" align="center" prop="state">
        <template slot-scope="scope">
          <span v-if="scope.row.state==0" >待审核</span>
          <span v-else-if="scope.row.state==1">通过</span>
          <span v-else>拒绝</span>
        </template>
      </el-table-column>
      <el-table-column label="审核意见" align="center" prop="auditAdvice">
        <template slot-scope="scope">
          <span v-if="scope.row.state==0" >待审核中</span>
          <span v-else>{{scope.row.auditAdvice}}</span>
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
          <el-button  v-if="scope.row.state!=1"
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
            icon="el-icon-delete"
            @click="handleAudit(scope.row)"
            v-hasPermi="['asset:manage:partrequire:audit']"
          >审核
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
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
        <el-form-item label="产品编号" prop="productNum">
          <el-input v-model.number="form.productNum" placeholder="请输入产品编号"/>
        </el-form-item>
        <el-form-item label="生产厂家" prop="vender">
          <el-input v-model="form.vender" placeholder="请输入生产厂家"/>
        </el-form-item>

        <el-form-item label="部件类别" prop="partType">
          <el-input v-model="form.partType" placeholder="请输入部件类别"/>
        </el-form-item>

        <el-form-item label="所属设备" prop="equipment">
          <el-input v-model="form.equipment" placeholder="请输入所属设备"/>
        </el-form-item>

        <el-form-item label="资产总计" prop="totalAssets">
          <el-input v-model.number="form.totalAssets" placeholder="请输入资产总计"/>
        </el-form-item>

        <el-form-item label="产权单位" prop="rightsUnit">
          <el-input v-model="form.rightsUnit" placeholder="请输入产权单位"/>
        </el-form-item>

        <el-form-item label="配件属性" prop="kitProperties">
          <el-input v-model="form.kitProperties" placeholder="请输入配件属性"/>
        </el-form-item>

        <el-form-item label="部件名称" prop="partName">
          <el-input v-model="form.partName" placeholder="请输入部件名称"/>
        </el-form-item>
        <el-form-item label="配件规格" prop="kitStandard">
          <el-input v-model="form.kitStandard" placeholder="请输入配件规格"/>
        </el-form-item>
        <el-form-item label="臂长" prop="brachium">
          <el-input v-model.number="form.brachium" placeholder="请输入臂长(米)"/>
        </el-form-item>
        <el-form-item label="设备型号" prop="unitType">
          <el-input v-model="form.unitType" placeholder="请输入设备型号"/>
        </el-form-item>
        <el-form-item label="计量单位" prop="measurementUnit">
          <el-input v-model="form.measurementUnit" placeholder="请输入计量单位"/>
        </el-form-item>
        <el-form-item label="标准节高度" prop="standardSectionHeight">
          <el-input v-model.number="form.standardSectionHeight" placeholder="请输入标准节高度(米)"/>
        </el-form-item>
        <el-form-item label="部件型号" prop="partModel">
          <el-input v-model="form.partModel" placeholder="请输入部件型号"/>
        </el-form-item>
        <el-form-item label="部件代码" prop="partCode">
          <el-input v-model="form.partCode" placeholder="请输入部件代码"/>
        </el-form-item>
        <el-form-item label="数量" prop="amount">
          <el-input v-model.number="form.amount" placeholder="请输入数量"/>
        </el-form-item>
        <el-form-item label="单价" prop="singlePrice">
          <el-input v-model.number="form.singlePrice" placeholder="请输入单价"/>
        </el-form-item>
        <el-form-item label="是否整机配件" prop="wholeMachineAccessories">
          <template>
            <el-radio-group v-model="form.wholeMachineAccessories">
              <el-radio :label="0">否</el-radio>
              <el-radio :label="1">是</el-radio>
            </el-radio-group>
          </template>
<!--          <el-input v-model.number="form.wholeMachineAccessories" placeholder="请输入是否整机配件"/>-->
        </el-form-item>
        <el-form-item label="需求发起人" prop="demandSponsors">
          <el-input v-model="form.demandSponsors" placeholder="请输入需求发起人"/>
        </el-form-item>
        <el-form-item label="采购原因" prop="procurementCauses">
          <el-input v-model="form.procurementCauses" placeholder="请输入采购原因"/>
        </el-form-item>
        <div
          v-for="(item, index) in paramsKit"
          :key="index">
          <el-form-item label="配件名称">
            <el-input v-model="item.kitName" placeholder="请输入配件名称"/>
          </el-form-item>
          <el-form-item label="配件单位">
            <el-input v-model="item.kitModel" placeholder="请输入配件型号"/>
          </el-form-item>
          <el-form-item label="配件数量">
            <el-input-number v-model="item.kitCount" placeholder="请输入配件数量"/>
          </el-form-item>
          <el-button size="medium" @click.prevent="removeKit(index)">删除该配件</el-button>
          <br/>
          <br/>
          <br/>
        </div>
        <el-form-item>
          <el-button @click="addKit">新增配件信息</el-button>
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
      <el-row >
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
      <el-row >
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
      <el-row >
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
      <el-row >
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
      <el-row >
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
      <el-row >
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
      <el-row >
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
      <el-row >
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
      <el-row >
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
      <el-row >
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
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              录入人部门id: {{ Detail.insertPersonDepartId }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              更新人部门id: {{ Detail.updatePersonDepartId }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
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
                {{ kit.kitName }}.
              </li>
            </ul>
          </el-col>
          <el-col :span="8" :xs="100">
            <ul class="list-group">
              <li class="list-group-item">
                {{ kit.kitModel}}.
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
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="openAudit" @close="getList" width="25%"   class="spec-dialog" append-to-body>
      <el-form ref="form" :model="form"  label-width="80px">
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
import {listPartRequire,getPartRequireKit,addPartRequire,delPartRequire,updatePartRequire } from '@/api/towerparam/partrequire'
import { updateEquipmentRequire } from '@/api/towerparam/equipmentrequire'
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
        insertDate: undefined,
      },
      openDetail: false,
      Detail:{},
      openAudit: false,
      kits:[],
      // 表单参数
      paramsKit:[{kitName:'',kitModel:'',kitCount:''}],
      form: {
        amPartRequireKits:[],
      },
      // 表单校验
      rules: {
        productNum: [
          { required: true, message: '产品编号不能为空', trigger: 'blur' },{
            type: 'number', message: '产品编号必须为数字值',trigger: 'blur'
          }
        ],
        brachium: [{
          type: 'number', message: '臂长必须为数字值',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '臂长范围在0-1000 米',
          trigger: 'blur'
        }],
        standardSectionHeight: [{
          type: 'number', message: '标准节高度',trigger: 'blur' }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '标准节高度范围在0-1000米',
          trigger: 'blur'
        }],
        amount: [{
          type: 'number', message: '数量必须为数字值',trigger: 'blur' }],
        singlePrice: [{
          type: 'number', message: '单价必须为数字值',trigger: 'blur' }],
        totalAssets: [{
          type: 'number', message: '资产总计必须为数字值',trigger: 'blur' }],
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
      listPartRequire(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail= false
      this.openAudit=false
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
        kitProperties:undefined,
        partName:undefined ,
        kitStandard:undefined ,
        brachium:undefined ,
        unitType:undefined ,
        standardSectionHeight:undefined ,
        partModel:undefined ,
        measurementUnit:undefined ,
        partCode:undefined ,
        amount:undefined ,
        singlePrice:undefined ,
        wholeMachineAccessories:undefined ,
        demandSponsors:undefined ,
        procurementCauses:undefined ,
      },
        this.paramsKit=[];
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
      getPartRequireKit(row.id).then((res) => {
        this.paramsKit=res.data;
      })
      this.form.amPartRequireKits=this.paramsKit;
      this.open = true
      this.title = '修改'
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            this.form.amPartRequireKits=this.paramsKit;
            updatePartRequire(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            this.form.amPartRequireKits=this.paramsKit;
            addPartRequire(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
        this.paramsKit=[];
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
    handleDetail(row){
      const pid= row.id || 0
      getPartRequireKit(pid).then((res) => {
        this.kits=res.data;
      })
      this.Detail = row
      this.openDetail = true
      this.title = '详情'
    },
    removeKit(index) {
      this.paramsKit.splice(index, 1)
    },
    addKit() {
      this.paramsKit.push({ kitName: '', kitModel: '',kitCount: ''})
    },
    handleAudit(row){
      this.form = row
      this.openAudit = true
      this.title = '审核'
    },
    submitAudit(){
      this.form.amPartRequireKits=[];
      updatePartRequire(this.form).then(response => {
        this.$modal.msgSuccess('修改成功')
        this.openAudit = false
        this.getList()
      })
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download()
    }
  }
}
</script>
