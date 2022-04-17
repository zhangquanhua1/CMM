<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery" s
        />
      </el-form-item>
      <el-form-item label="类别" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择类别">
          <el-option
            v-for="dict in OutStockTypeS"
            :key="dict.id"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="型号" prop="model">
        <el-input
          v-model="queryParams.model"
          placeholder="请输入型号"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['inventory:manage:OutStockApply:add']"
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
          v-hasPermi="['inventory:manage:OutStockApply:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['inventory:manage:OutStockApply:import']"
        >导入
        </el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['inventory:manage:OutStockApply:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="DataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" :selectable="selectInit"  width="55" align="center"/>
      <el-table-column
        type="index"
        width="50"
      >
      </el-table-column>
      <el-table-column label="名称" align="center" prop="name"/>
      <el-table-column label="类别" align="center" prop="type">
        <template slot-scope="scope">
          <el-tag :type="'danger'" v-if="scope.row.type==1">设备</el-tag>
          <el-tag :type="'primary'" v-else-if="scope.row.type==2">零件</el-tag>
          <el-tag :type="'primary'" v-else-if="scope.row.type==4">设备-零件</el-tag>
          <el-tag :type="'primary'" v-else-if="scope.row.type==6">部件-零件</el-tag>
          <el-tag :type="'success'" v-else-if="scope.row.type==3">部件</el-tag>
          <el-tag :type="'success'" v-else-if="scope.row.type==5">设备-部件</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="型号" align="center" prop="model"/>
      <el-table-column label="数量" align="center" prop="amount"/>
      <el-table-column label="申请人" align="center" prop="applicant"/>
      <el-table-column label="申请日期" align="center" prop="applyDate">
        <template slot-scope="scope">
          <span>{{ parseTime2(scope.row.applyDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="status" :filters="[{ text: '待审核', value: 0 },
      { text: '已出库', value: 1 },{ text: '拒绝', value: 2 }]"
                       :filter-method="filterState"
      >
        <template slot-scope="scope">
          <el-tag :type="'primary'" v-if="scope.row.status==0">待审核</el-tag>
          <el-tag :type="'success'" v-else-if="scope.row.status==1">已出库</el-tag>
          <el-tag :type="'danger'" v-else>拒绝</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status==0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['inventory:manage:OutStockApply:edit']"
          >修改
          </el-button>
          <el-button
            v-if="scope.row.status==0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['inventory:manage:OutStockApply:remove']"
          >删除
          </el-button>
          <el-button
            v-if="scope.row.status==0"
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleAudit(scope.row)"
            v-hasPermi="['inventory:manage:OutStockApply:audit']"
          >审核
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
    <el-dialog :title="title" :visible.sync="open" width="1200px" class="spec-dialog" :close-on-click-modal="false"
               v-dialog-drag v-dialog-drag-width
               v-dialog-drag-height append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="名称" prop="name">
              <el-input v-model.number="form.name" placeholder="请输入名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="型号" prop="model">
              <el-input v-model="form.model" placeholder="请输入型号"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="类别" prop="type">
              <el-select v-model="form.type" placeholder="请选择类别">
                <el-option
                  v-for="dict in OutStockTypeS"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="申请发货数量" prop="amount">
              <el-input v-model.number="form.amount" placeholder="请输入申请发货数量"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="申请人" prop="applicant">
              <el-input v-model="form.applicant" placeholder="请输入申请人"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="申请日期">
              <el-date-picker
                v-model="form.applyDate"
                size="small"
                style="width: 240px"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="选择申请日期"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--    审核对话框-->
    <el-dialog :title="title" :visible.sync="openAudit" @close="getList" width="25%" class="spec-dialog" append-to-body
               :close-on-click-modal="false" v-dialog-drag v-dialog-drag-width
               v-dialog-drag-height append-to-body
    >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="是否出库" prop="auditAdvice">
          <el-radio-group v-model="form.status">
            <el-radio :label="2">拒绝出库</el-radio>
            <el-radio :label="1">出库</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAudit">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--    导入对话框-->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport"/>
            是否更新已经存在的数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                   @click="importTemplate"
          >下载模板
          </el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listOutStockApply,
  addOutStockApply,
  delOutStockApply,
  updateOutStockApply,
  WhetherOutStockApply
} from '@/api/inventory/OutStockApply'
import { addEquipmentEntry, getKitAndPartByEquipmentEntry2, updateEquipmentEntry } from '@/api/inventory/EquipmentEntry'
import { getToken } from '@/utils/auth'
import { updateKitRequire } from '@/api/towerparam/kitrequire'
import { checkRole } from '@/utils/permission'

export default {
  name: 'StockOutApply',
  dicts: ['OutStockType'],
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
      //表格数据
      DataList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        type: undefined,
        model: undefined
      },
      //增加表单
      form: {},
      openAudit: false,
      //校验规则
      rules: {
        name: [{
          required: true, message: '请输入名称', trigger: 'blur'
        }],
        type: [{ required: true, message: '类别不能为空', trigger: 'blur' }],
        amount: [{
          required: true, message: '请输入数量', trigger: 'blur'
        }, {
          type: 'number', message: '数量必须为数字', trigger: 'blur'
        }],
        model: [{
          required: true, message: '请输入型号', trigger: 'blur'
        }]
      },
      OutStockTypeS: [],

      // 导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: '',
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: 'Bearer ' + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + '/inventory/OutStockApply/importData'
      }

    }
  },
  created() {
    this.getList()
    this.getDicts('OutStockType').then(response => {
      this.OutStockTypeS = response.data
    })
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      listOutStockApply(this.queryParams).then(response => {
        this.DataList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 表单重置
    reset() {
      this.form = {
        name: undefined,
        type: undefined,
        model: undefined,
        amount: undefined,
        applicant: undefined,
        applyDate: undefined,
        status: undefined
      },
        this.resetForm('form')
    },
    // 取消按钮
    cancel() {
      this.open = false
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
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
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
      this.open = true
      this.title = '修改'
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateOutStockApply(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addOutStockApply(this.form).then(response => {
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
      const ids = row.id || this.ids
      this.$confirm('是否确认删除', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delOutStockApply(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      })
    },
    handleAudit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.openAudit = true
      this.title = '审核'
    },
    //提交审核
    submitAudit() {
      WhetherOutStockApply(this.form).then(response => {
        this.$modal.msgSuccess('审核成功')
        this.openAudit = false
        this.getList()
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('inventory/OutStockApply/export', {
        ...this.queryParams
      }, `OutStockApply_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = '出库申请导入'
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('/inventory/OutStockApply/importTemplate', {}, `OutStockApply_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert(response.msg, '导入结果', { dangerouslyUseHTMLString: true })
      this.getList()
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit()
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
  }
}
</script>
