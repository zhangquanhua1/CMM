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

      <el-form-item>
        <template>
          <el-radio v-model="queryParams.state" label="1">已审核</el-radio>
          <el-radio v-model="queryParams.state" label="0">待审核</el-radio>
        </template>
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
          v-hasPermi="['asset:manage:equipmentrequire:add']"
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
          v-hasPermi="['asset:manage:equipmentrequire:remove']"
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
            v-hasPermi="['asset:manage:equipmentrequire:edit']"
          >修改
          </el-button>
          <el-button v-if="scope.row.state!=1"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['asset:manage:equipmentrequire:remove']"
          >删除
          </el-button>
          <el-button
                     size="mini"
                     type="text"
                     icon="el-icon-delete"
                     @click="handleAudit(scope.row)"
                     v-hasPermi="['asset:manage:equipmentrequire:audit']"
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
        <el-form-item label="设备名称" prop="equipmentName">
          <el-input v-model="form.equipmentName" placeholder="请输入设备名称"/>
        </el-form-item>
        <el-form-item label="设备编号" prop="equipmentNumber">
          <el-input v-model.number="form.equipmentNumber" placeholder="请输入设备编号"/>
        </el-form-item>
        <el-form-item label="生产厂家" prop="vender">
          <el-input v-model="form.vender" placeholder="请输入生产厂家"/>
        </el-form-item>
        <el-form-item label="规格型号" prop="standardModel">
          <el-input v-model="form.standardModel" placeholder="请输入规格型号"/>
        </el-form-item>
        <el-form-item label="设备类型" prop="equipmentType">
          <el-input v-model="form.equipmentType" placeholder="请输入设备类型"/>
        </el-form-item>
        <el-form-item label="设备数量" prop="equipmentCount">
          <el-input v-model.number="form.equipmentCount" placeholder="请输入设备数量"/>
        </el-form-item>
        <el-form-item label="产权单位" prop="rightsUnit">
          <el-input v-model="form.rightsUnit" placeholder="请输入产权单位"/>
        </el-form-item>
        <el-form-item label="配件属性" prop="kitProperties">
          <el-input v-model="form.kitProperties" placeholder="请输入配件属性"/>
        </el-form-item>
        <el-form-item label="归属仓库" prop="belongWarehouse">
          <el-input v-model="form.belongWarehouse" placeholder="请输入归属仓库"/>
        </el-form-item>
        <el-form-item label="项目地址" prop="projectAddress">
          <el-input v-model.number="form.projectAddress" placeholder="请输入项目地址"/>
        </el-form-item>
        <el-form-item label="项目日期">
          <el-date-picker
            v-model="form.projectDate"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择项目日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="发起日期">
          <el-date-picker
            v-model="form.initDate"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择发起日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="需求发起人" prop="demandSponsors">
          <el-input v-model="form.demandSponsors" placeholder="请输入需求发起人"/>
        </el-form-item>
        <el-form-item label="采购原因" prop="procurementCauses">
          <el-input v-model="form.procurementCauses" placeholder="请输入采购原因"/>
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
              设备名称: {{ Detail.equipmentName }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              设备编号: {{ Detail.equipmentNumber }}
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
              规格型号: {{ Detail.standardModel }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              设备类别: {{ Detail.equipmentType }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              设备数量: {{ Detail.equipmentCount }}
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
              归属仓库: {{ Detail.belongWarehouse }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              项目地址: {{ Detail.projectAddress }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              项目日期: {{ parseTime2(Detail.projectDate) }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              发起日期: {{ parseTime2(Detail.initDate) }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              需求发起人: {{ Detail.demandSponsors }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              采购原因: {{ Detail.procurementCauses }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">审核状态：
              <span v-if="Detail.state==0">待审核</span>
              <span v-else-if="Detail.state==1" >审核通过</span>
              <span v-else>审核不通过</span>
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              审核意见:
              <span v-if="Detail.auditAdvice==null">
                待审核状态中
              </span>
              <span v-else> {{Detail.auditAdvice }}</span>
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              发起日期: {{parseTime2(Detail.initDate) }}
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
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
<!--    审核弹窗-->
    <el-dialog :title="title" :visible.sync="openAudit" width="25%" @close="getList"  class="spec-dialog" append-to-body>
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
  addEquipmentRequire,
  delEquipmentRequire,
  listEquipmentRequire,
  updateEquipmentRequire
} from '@/api/towerparam/equipmentrequire'
export default {
  name: 'equipmentTyperequire',
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
        state
      },
      openDetail: false,
      openAudit: false,
      Detail:{},
      form: {
      },
      // 表单校验
      rules: {
        equipmentNumber: [
          { required: true, message: '设备编号不能为空', trigger: 'blur' }
        ],
        equipmentCount: [{
          type: 'number', message: '设备数量必须为数字值',trigger: 'blur' }],
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
      listEquipmentRequire(this.queryParams).then(response => {
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
        vender: undefined,
        equipmentNumber: undefined,
        equipmentName: undefined,
        standardModel: undefined,
        equipmentType: undefined,
        equipmentCount: undefined,
        rightsUnit:undefined,
        belongWarehouse:undefined ,
        projectAddress:undefined ,
        projectDate:undefined ,
        initDate:undefined ,
        demandSponsors:undefined ,
        procurementCauses:undefined ,
        state:undefined ,
        auditAdvice:undefined ,
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
      this.queryParams.equipmentName=''
      this.queryParams.initDate=''
      this.queryParams.projectDate=''
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
      this.open = true
      this.title = '修改'
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateEquipmentRequire(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addEquipmentRequire(this.form).then(response => {
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
        return delEquipmentRequire(postIds)
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
    handleAudit(row){
      this.form = row
      this.openAudit = true
      this.title = '审核'
    },
    submitAudit(){
      updateEquipmentRequire(this.form).then(response => {
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
