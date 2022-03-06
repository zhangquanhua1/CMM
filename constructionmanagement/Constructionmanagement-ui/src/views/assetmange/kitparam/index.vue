<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="kitName">
        <el-input
          v-model="queryParams.kitName"
          placeholder="请输入配件名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="代码" prop="kitCode">
        <el-input
          v-model="queryParams.kitCode"
          placeholder="请输入配件代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="型号" prop="kitModel">
        <el-input
          v-model="queryParams.kitModel"
          placeholder="请输入配件型号"
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
          v-hasPermi="['asset:manage:kitparam:add']"
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
          v-hasPermi="['asset:manage:kitparam:remove']"
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
      <el-table-column label="代码" align="center" prop="kitCode"/>
      <el-table-column label="型号" align="center" prop="kitModel"/>
      <el-table-column label="适用配件类型" align="center" prop="applicableKitType"/>
      <el-table-column label="技术参数" align="center" prop="technicalParam"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['asset:manage:kitparam:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['asset:manage:kitparam:remove']"
          >删除
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

        <el-form-item label="零配件名称" prop="kitName">
          <el-input v-model="form.kitName" placeholder="请输入零配件名称"/>
        </el-form-item>

        <el-form-item label="代码" prop="kitCode">
          <el-input v-model="form.kitCode" placeholder="请输入零配件代码"/>
        </el-form-item>

        <el-form-item label="型号" prop="kitModel">
          <el-input v-model="form.kitModel" placeholder="请输入零配件型号"/>
        </el-form-item>

        <el-form-item label="适用配件类型" prop="applicableKitType">
          <el-input v-model="form.applicableKitType" placeholder="请输入适用配件类型"/>
        </el-form-item>

        <el-form-item label="技术参数" prop="technicalParam">
          <el-input v-model.number="form.technicalParam" placeholder="请输入技术参数"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
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
import {listKitParam,addKitParam,delKitParam,updateKitParam  } from '@/api/towerparam/kitparam'
import { delTowerMachineParam } from '@/api/towerparam/towermachineparam'
export default {
  name: 'kitparam',
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
        kitName: undefined,
        kitCode: undefined,
        kitModel: undefined,
      },
      form: {
        kitName:'',
        kitCode:'',
        kitModel:'',
        applicableKitType:'',
        technicalParam:'',
        remark:''
      },
      // 表单校验
      rules: {
        kitCode: [
          { required: true, message: '配件代码不能为空', trigger: 'blur' }
        ],
        kitName: [
          { required: true, message: '配件名称不能为空', trigger: 'blur' }
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
      this.loading = true
      listKitParam(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
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
            updateKitParam(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addKitParam(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除该配件参数？').then(function() {
        return delKitParam(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download()
    }
  }
}
</script>
