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
      <el-form-item label="零配件名称"  label-width="auto" prop="kitName">
        <el-input
          v-model="queryParams.kitName"
          placeholder="请输入零配件名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="零配件类别" label-width="auto" prop="kitType">
        <el-input
          v-model="queryParams.kitType"
          placeholder="请输入零配件类别"
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
      <el-form-item label="配件型号" prop="kitModel">
        <el-input
          v-model="queryParams.kitModel"
          placeholder="请输入配件型号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="配件代码" prop="kitCode">
        <el-input
          v-model="queryParams.kitCode"
          placeholder="请输入配件代码"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['inventory:manage:kitentry:add']"
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
          v-hasPermi="['inventory:manage:kitentry:remove']"
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
      <el-table-column label="零配件名称" align="center" prop="kitName"/>
      <el-table-column label="产品编号" align="center" prop="productNum"/>
      <el-table-column label="生产厂家" align="center" prop="vender"/>
      <el-table-column label="零配件类别" align="center" prop="kitType"/>
      <el-table-column label="配件型号" align="center" prop="kitModel"/>
      <el-table-column label="配件代码" align="center" prop="kitCode"/>
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['inventory:manage:kitentry:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['inventory:manage:kitentry:remove']"
          >删除
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
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" :close-on-click-modal = "false" width="30%"  class="spec-dialog" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="零配件名称" prop="kitName">
          <el-input v-model="form.kitName" placeholder="请输入零配件名称"/>
        </el-form-item>
        <el-form-item label="产品编号" prop="productNum">
          <el-input v-model.number="form.productNum" placeholder="请输入产品编号"/>
        </el-form-item>
        <el-form-item label="生产厂家" prop="vender">
          <el-input v-model="form.vender" placeholder="请输入生产厂家"/>
        </el-form-item>
        <el-form-item label="零配件类别" prop="kitType">
          <el-input v-model="form.kitType" placeholder="请输入零配件类别"/>
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
        <el-form-item label="配件规格" prop="kitStandard">
          <el-input v-model="form.kitStandard" placeholder="请输入配件规格"/>
        </el-form-item>
        <el-form-item label="臂长" prop="brachium">
          <el-input v-model.number="form.brachium" placeholder="请输入臂长,单位为：米"/>
        </el-form-item>
        <el-form-item label="折旧率" prop="depreciationRate">
          <el-input v-model.number="form.depreciationRate" placeholder="请输入折旧率(%)"/>
        </el-form-item>
        <el-form-item label="设备型号" prop="unitType">
          <el-input v-model="form.unitType" placeholder="请输入设备型号"/>
        </el-form-item>
        <el-form-item label="计量单位" prop="measurementUnit">
          <el-input v-model="form.measurementUnit" placeholder="请输入计量单位"/>
        </el-form-item>
        <el-form-item label="标准节高度" prop="standardSectionHeight">
          <el-input v-model.number="form.standardSectionHeight" placeholder="请输入标准节高度（米）"/>
        </el-form-item>
        <el-form-item label="配件型号" prop="kitModel">
          <el-input v-model="form.kitModel" placeholder="请输入配件型号"/>
        </el-form-item>
        <el-form-item label="配件代码" prop="kitCode">
          <el-input v-model="form.kitCode" placeholder="请输入配件代码"/>
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
        </el-form-item>
        <el-form-item label="防坠器" prop="protectorId">
          <template>
            <el-select v-model="form.protectorId" clearable placeholder="请选择">
              <el-option
                v-for="item in this.protectors"
                :key="item.value"
                :label="item.standard"
                :value="item.id">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="采购日期">
          <el-date-picker
            v-model="form.purchaseDate"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择采购日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="报废日期">
          <el-date-picker
            v-model="form.retireDate"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择报废日期"
          ></el-date-picker>
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
              零配件名称: {{ Detail.kitName }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              产品编号: {{ Detail.productNum }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              生产厂家: {{ Detail.vender }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              零配件类别: {{ Detail.kitType }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              所属设备: {{ Detail.equipment }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              资产总计: {{ Detail.totalAssets }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              产权单位: {{ Detail.rightsUnit }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              配件属性: {{ Detail.kitProperties }}
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
              臂长: {{ Detail.brachium}} 米
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              折旧率: {{ Detail.depreciationRate}}%
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              设备型号: {{ Detail.unitType }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              计量单位: {{ Detail.measurementUnit }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              标准节高度: {{ Detail.standardSectionHeight }}米
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              配件型号: {{ Detail.kitModel }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              配件代码: {{ Detail.kitCode }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              数量: {{ Detail.amount }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              单价: {{ Detail.singlePrice }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              防坠器: {{ Detail.protectorId }}
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
              报废日期: {{ parseTime2(Detail.retireDate) }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              采购日期: {{ parseTime2(Detail.purchaseDate) }}
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
              更新时间: {{ parseTime(Detail.updateDate) }}
            </li>
          </ul>
        </el-col>
      </el-row>
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
  addKitEntry,
  delKitEntry,
  listKitEntry,
  updateKitEntry,getProtector
} from '@/api/inventory/KitEntry'
export default {
  name: 'kitEntry',
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
        vender:undefined,
        kitName:undefined,
        kitType: undefined,
        purchaseDate: undefined,
        retireDate: undefined,
        equipment: undefined,
        kitModel: undefined,
        kitCode: undefined,
        objectValueAllEmpty:false,
      },
      openDetail: false,
      Detail:{},
      protectors:[],
      form: {
      },
      // 表单校验
      rules: {
        productNum: [
          { required: true, message: '产品编号不能为空', trigger: 'blur' },{
            type: 'number', message: '产品编号必须为数字值',trigger: 'blur' }
        ],
        totalAssets: [
          { type: 'number', message: '资产总计必须为数字值',trigger: 'blur' }
        ],
        brachium: [
          { type: 'number', message: '臂长必须为数字值',trigger: 'blur' }, {
            pattern: /^(0|[1-9]\d?|10000)$/,
            message: '臂长范围在0-100',
            trigger: 'blur'
          }
        ],
        standardSectionHeight: [
          {type: 'number', message: '标准节高度必须为数字值',trigger: 'blur' }, {
            pattern: /^(0|[1-9]\d?|100)$/,
            message: '标准节高度范围在0-10000',
            trigger: 'blur'
          }
        ],
        depreciationRate: [
          {type: 'number', message: '折旧率必须为数字值',trigger: 'blur' }, {
            pattern: /^(0|[1-9]\d?|100)$/,
            message: '折旧率范围在0-100',
            trigger: 'blur'
          }
        ],
        amount: [
          {type: 'number', message: '数量必须为数字值',trigger: 'blur' }, {
            pattern: /^(0|[1-9]\d?|100)$/,
            message: '数量范围在0-1000000',
            trigger: 'blur'
          }
        ],
        singlePrice: [
          {type: 'number', message: '单价必须为数字值',trigger: 'blur' }, {
            pattern: /^(0|[1-9]\d?|100)$/,
            message: '单价范围在0-1000000',
            trigger: 'blur'
          }
        ],

      },
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询列表 */
    getList() {
      if(this.objectValueAllEmpty1(this.queryParams))
        this.queryParams.objectValueAllEmpty=true;
      this.loading = true
      listKitEntry(this.queryParams).then(response => {
        this.queryParams.objectValueAllEmpty=false;
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
        kitName: undefined,
        productNum: undefined,
        vender:undefined,
        kitType: undefined,
        equipment: undefined,
        totalAssets: undefined,
        rightsUnit: undefined,
        kitProperties:undefined,
        kitStandard:undefined ,
        brachium:undefined ,
        depreciationRate:undefined,
        unitType:undefined,
        measurementUnit:undefined,
        standardSectionHeight:undefined,
        kitModel:undefined ,
        kitCode:undefined ,
        amount:undefined ,
        singlePrice:undefined ,
        wholeMachineAccessories:undefined ,
        protectorId:undefined ,
        purchaseDate:undefined ,
        retireDate:undefined ,
      },
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
      this.queryParams.retireDate='',
        this.queryParams.purchaseDate='',
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
      getProtector().then((res) => {
        this.protectors=res.data
        console.log("this.protectors"+res.data)
      })
      this.reset()
      this.open = true
      this.title = '添加'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      getProtector().then((res) => {
        this.protectors=res.data
        console.log("this.protectors"+res.data)
      })
      this.reset()
      this.form = row
      this.open = true
      this.title = '修改'
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateKitEntry(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addKitEntry(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const postIds = row.id || this.ids
      this.$modal.confirm('是否确认删除？').then(function() {
        return delKitEntry(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    handleDetail(row){
      this.Detail = row
      this.openDetail = true
      this.title = '详情'
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download()
    }
  }
}
</script>
