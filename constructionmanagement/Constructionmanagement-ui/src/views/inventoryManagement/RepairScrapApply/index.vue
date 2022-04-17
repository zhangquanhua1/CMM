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
            v-for="dict in types"
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
      <el-form-item label="申请类型" prop="applyType">
        <el-select v-model="queryParams.applyType" placeholder="请选择申请类型">
          <el-option
            v-for="dict in applyTypes"
            :key="dict.id"
            :label="dict.dictLabel"
            :value="parseInt(dict.dictValue)"
          ></el-option>
        </el-select>
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
          v-hasPermi="['asset:manage:RepairScrap:add']"
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
          v-hasPermi="['asset:manage:RepairScrap:remove']"
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
          v-hasPermi="['asset:manage:RepairScrap:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="DataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" :selectable="selectInit" width="55" align="center"/>
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
      <el-table-column label="计量单位" align="center" prop="measurementUnit"/>
      <el-table-column label="申请类型" align="center" prop="applyType">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.applyType==0">维修</el-tag>
          <el-tag :type="'danger'" v-else-if="scope.row.applyType==1">报废</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="申请人" align="center" prop="applicant"/>
      <el-table-column label="维修原因" align="center" prop="reason"/>

      <el-table-column label="申请日期" align="center" prop="applyDate">
        <template slot-scope="scope">
          <span>{{ parseTime2(scope.row.applyDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="status" :filters="[{ text: '待审核', value: 0 },
      { text: '通过', value: 1 },{ text: '拒绝', value: 2 }]"
                       :filter-method="filterState"
      >
        <template slot-scope="scope">
          <el-tag :type="'primary'" v-if="scope.row.status==0">待审核</el-tag>
          <el-tag :type="'success'" v-else-if="scope.row.status==1">通过</el-tag>
          <el-tag :type="'danger'" v-else>拒绝</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="审核意见" align="center" prop="auditAdvice"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status==0"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['asset:manage:RepairScrap:edit']"
          >修改
          </el-button>
          <el-button
            v-if="scope.row.status==0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['asset:manage:RepairScrap:remove']"
          >删除
          </el-button>
          <el-button
            v-if="scope.row.status==0"
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleAudit(scope.row)"
            v-hasPermi="['asset:manage:RepairScrap:audit']"
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
                  v-for="dict in types"
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
            <el-form-item label="申请类型" prop="applyType">
              <el-select v-model="form.applyType" placeholder="请选择申请类型">
                <el-option
                  v-for="dict in applyTypes"
                  :key="dict.id"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="数量" prop="amount">
              <el-input v-model.number="form.amount" placeholder="请输入申请发货数量"/>
            </el-form-item>
          </el-col>
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
            <el-form-item label="申请人" prop="applicant">
              <el-input v-model="form.applicant" placeholder="请输入申请人"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="申请原因" prop="reason">
              <el-input v-model="form.reason" type="textarea"
                        :autosize="{ minRows: 2, maxRows: 5}" placeholder="请输入申请原因"
              />
            </el-form-item>
          </el-col>
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
    <el-dialog :title="title" :visible.sync="openAudit" @close="getList" width="25%" v-dialog-drag class="spec-dialog"
               append-to-body
    >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="是否通过" prop="auditAdvice">
          <el-radio-group v-model="form.status">
            <el-radio :label="2">否</el-radio>
            <el-radio :label="1">是</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核意见" prop="auditAdvice">
          <el-input type="textarea" autosize v-model="form.auditAdvice" placeholder="请输入审核意见"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { addRepairScrap, delRepairScrap, listRepairScrap, updateRepairScrap } from '@/api/inventory/RepairScrapApply'
import { updatePartRequire } from '@/api/towerparam/partrequire'
import { checkRole } from '@/utils/permission'

export default {
  name: 'RepairScrapApply',
  components: {},
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
      //打开审核
      openAudit: false,
      //类型
      types: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        type: undefined,
        model: undefined,
        applyType: undefined
      },
      //申请类型
      applyTypes: [],
      //计量单位
      measurement_unitS: [],
      //增加表单
      form: {},
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
      }
    }
  },
  created() {
    this.getList()
    //获取类型
    this.getDicts('type').then(response => {
      this.types = response.data
    })
  },
  methods: {
    /** 查询仓库维护列表 */
    getList() {
      this.loading = true
      listRepairScrap(this.queryParams).then(response => {
        this.DataList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openAudit = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        name: undefined,
        type: undefined,
        model: undefined,
        applyType: undefined,
        amount: undefined,
        measurementUnit: undefined,
        applicant: undefined,
        applyDate: undefined,
        reason: undefined
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
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.repairId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.getDicts('measurement_unit').then(response => {
        this.measurement_unitS = response.data
      })
      //获取类型
      this.getDicts('ApplyTypes').then(response => {
        this.applyTypes = response.data
      })
      this.open = true
      this.title = '添加'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.getDicts('measurement_unit').then(response => {
        this.measurement_unitS = response.data
      })
      //获取类型
      this.getDicts('ApplyTypes').then(response => {
        this.applyTypes = response.data
      })
      this.form = JSON.parse(JSON.stringify(row))
      this.open = true
      this.title = '修改'
    },
    //审核按钮
    handleAudit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.openAudit = true
      this.title = '审核'
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.repairId != undefined) {
            updateRepairScrap(this.form).then(response => {
              if (this.openAudit) {
                this.$modal.msgSuccess('审核完成')
              } else {
                this.$modal.msgSuccess('修改成功')
              }
              this.openAudit = false
              this.open = false
              this.getList()
            })
          } else {
            addRepairScrap(this.form).then(response => {
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
      const ids = row.repairId || this.ids
      this.$confirm('是否确认删除', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delRepairScrap(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      })
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('/asset/RepairScrap/export', {
        ...this.queryParams
      }, `rs_${new Date().getTime()}.xlsx`)
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
