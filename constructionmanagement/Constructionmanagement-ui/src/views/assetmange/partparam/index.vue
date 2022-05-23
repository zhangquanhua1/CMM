<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="适用设备类型" label-width="auto" prop="applicableDeviceType">
        <el-select v-model="queryParams.applicableDeviceType" placeholder="请选择适用设备类型">
          <el-option
            v-for="dict in applicableDeviceTypeS"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="partName">
        <el-input
          v-model="queryParams.partName"
          placeholder="请输入部件名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="型号" prop="partModel">
        <el-input
          v-model="queryParams.partModel"
          placeholder="请输入部件型号"
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
          v-hasPermi="['asset:manage:partparam:add']"
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
          v-hasPermi="['asset:manage:partparam:remove']"
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
          v-hasPermi="['asset:manage:partparam:export']"
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
      <el-table-column label="代码" align="center" prop="partCode"/>
      <el-table-column label="型号" align="center" prop="partModel"/>
      <el-table-column label="部件类型" align="center" prop="part_type"/>
      <el-table-column label="适用设备类型" align="center" prop="applicableDeviceType"/>
      <el-table-column label="计量单位" align="center" prop="measurementUnit"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['asset:manage:partparam:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['asset:manage:partparam:remove']"
          >删除
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-more"
            @click="handleDetail(scope.row)"
          >部件详情
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
    <el-dialog :title="title" :visible.sync="open" width="800px" class="spec-dialog" :close-on-click-modal="false"
               v-dialog-drag append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">

        <el-steps :active="active" finish-status="success">
          <el-step title="添加部件"></el-step>
          <el-step title="零件绑定"></el-step>
        </el-steps>

        <div v-show="steps1">

          <el-form-item label="部件名称" prop="partName">
            <el-input v-model="form.partName" placeholder="请输入部件名称"/>
          </el-form-item>
          <el-form-item label="部件类型" prop="part_type">
            <el-select v-model="form.part_type" placeholder="请选择部件类型">
              <el-option
                v-for="dict in applicableKitTypeS"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="部件代码" prop="partCode">
            <el-input v-model="form.partCode" placeholder="请输入部件代码"/>
          </el-form-item>
          <el-form-item label="部件型号" prop="partModel">
            <el-input v-model="form.partModel" placeholder="请输入部件型号"/>
          </el-form-item>
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
          <el-form-item label="适用设备类型" prop="applicableDeviceType">
            <el-select v-model="form.applicableDeviceType" placeholder="请选择适用设备类型">
              <el-option
                v-for="dict in applicableDeviceTypeS"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" placeholder="请输入备注"/>
          </el-form-item>
        </div>
        <div v-show="steps2">
          <el-transfer v-model="value" :button-texts="['移除', '添加']" :data="kitData" :titles="titles" filterable>
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
    <!--    详情弹窗-->
    <el-drawer
      title="部件参数详情"
      size="60%"
      :visible.sync="openDetail"
      :with-header="true"
    >
      <div style="margin-left: 10px">
        <PartParamDeatil :detail="this.partParamDetail" :kits-list="this.kits">/</PartParamDeatil>
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
import { listPartParam, getKit, addPartParam, delPartParam, updatePartParam, getKitS } from '@/api/towerparam/partparam'
import PartParamDeatil from './PartParamDeatil'

export default {
  name: 'partparam',
  // dicts: ['sys_normal_disable'],
  components: { PartParamDeatil },
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
      title: '',
      // 弹出层标题
      titles: ['待选择零件', '已选择零件'],
      // 是否显示弹出层
      open: false,
      active: 0,
      steps1: true,
      steps2: false,
      kitData: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        partName: undefined,
        partModel: undefined,
        applicableDeviceType: undefined
      },
      openDetail: false,
      partParamDetail: {},
      kits: [],
      //配件数量
      amounts: [],
      //配件id
      value: [],
      //计量单位
      measurement_unitS: [],
      //适用设备类型
      applicableDeviceTypeS: [],
      //适用部件类型
      applicableKitTypeS: [],
      //记录已选择的部件名
      last_partName: '',
      // 新增配件参数数组
      paramsKit: [{ kid: '', kitCount: '' }],
      form: {
        partName: '',
        part_type: '',
        partCode: '',
        partModel: '',
        measurementUnit: '',
        applicableDeviceType: '',
        remark: '',
        amPartParamKits: []
      },
      // 表单校验
      rules: {
        partName: [
          { required: true, message: '部件名称不能为空', trigger: 'blur' }
        ],
        partCode: [
          { required: true, message: '部件代码不能为空', trigger: 'blur' }
        ],
        partModel: [
          { required: true, message: '部件型号不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getDicts('measurement_unit').then(response => {
      this.measurement_unitS = response.data
    })
    this.getDicts('applicableDeviceType').then(response => {
      this.applicableDeviceTypeS = response.data
    })
    this.getDicts('parts_type').then(response => {
      this.applicableKitTypeS = response.data
    })

  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      listPartParam(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 根据部件类型查询零配件参数列表 */
    getKitSS() {
      const _this = this
      getKitS(this.form).then(response => {
        response.data.forEach(function(item) {
          _this.kitData.push({ key: item.id, label: item.kitName })
        })
      })
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
    buildParamsKit() {
      this.getAmounts()
      this.paramsKit = this.amounts.map((kitCount, i) => ({ kitCount, kid: this.value[i] }))
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
        partName: undefined,
        partCode: undefined,
        partModel: undefined,
        measurementUnit: undefined,
        applicableDeviceType: undefined,
        remark: undefined
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
      this.form.amPartParamKits = this.paramsKit
      this.open = true
      this.title = '修改'
    },
    /** 提交按钮 */
    submitForm: function() {
      this.buildParamsKit()
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            this.form.amPartParamKits = this.paramsKit
            updatePartParam(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.paramsKit = []
              this.getList()
            })
          } else {
            this.form.amPartParamKits = this.paramsKit
            addPartParam(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.paramsKit = []
              this.getList()
            })
          }
          this.value = []
          this.amounts = []
        }
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const postIds = row.id || this.ids
      this.$modal.confirm('是否确认删除该塔机参数？').then(function() {
        return delPartParam(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    handleDetail(row) {
      const pid = row.id
      getKit(pid).then((res) => {
        this.kits = res.data
      })
      this.partParamDetail = row
      this.openDetail = true
      this.title = '详情'
    },
    previous() {
      this.last_partName = this.form.part_type
      if (this.active-- > 2) this.active = 0
      this.steps1 = true
      this.steps2 = false
    },
    next() {
      if (this.last_partName != this.form.part_type) {
        this.kitData = []
        this.getKitSS()
      }

      if (this.active++ > 2) this.active = 0
      this.steps1 = false
      this.steps2 = true
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/asset/partparam/export', {
        ...this.queryParams
      }, `partparam_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
