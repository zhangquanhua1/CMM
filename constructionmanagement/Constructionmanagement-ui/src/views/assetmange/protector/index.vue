<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="型号" prop="kitName">
        <el-input
          v-model="queryParams.model"
          placeholder="请输入防坠器型号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出厂日期">
        <el-date-picker
          v-model="queryParams.productDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择出厂日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="检测时间">
        <el-date-picker
          v-model="queryParams.checkDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择检测时间"
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
          v-hasPermi="['asset:manage:protector:add']"
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
          v-hasPermi="['asset:manage:protector:remove']"
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
          v-hasPermi="['asset:manage:protector:export']"
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
      <el-table-column label="防坠器规格" align="center" prop="standard"/>
      <el-table-column label="型号" align="center" prop="model"/>
      <el-table-column label="出厂日期" align="center" prop="productDate">
        <template slot-scope="scope">
          <span>{{ parseTime2(scope.row.productDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="检测时间" align="center" prop="checkDate">
        <template slot-scope="scope">
          <span>{{ parseTime2(scope.row.checkDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['asset:manage:protector:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['asset:manage:protector:remove']"
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
        <el-form-item label="防坠器规格" prop="standard">
          <el-input v-model="form.standard" placeholder="请输入防坠器规格"/>
        </el-form-item>
        <el-form-item label="防坠器型号" prop="model">
          <el-input v-model="form.model" placeholder="请输入防坠器型号"/>
        </el-form-item>
        <el-form-item label="生产日期">
          <el-date-picker
            v-model="form.productDate"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择生产日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="检测时间">
          <el-date-picker
            v-model="form.checkDate"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择检测时间"
          ></el-date-picker>
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
import {listProtector,addProtector,delProtector,updateProtector  } from '@/api/towerparam/protector'
export default {
  name: 'protector',
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
        model: undefined,
        productDate: undefined,
        checkDate: undefined,
      },
      form: {
        standard:'',
        model:'',
        productDate:'',
        checkDate:'',
      },
      // 表单校验
      rules: {
        model: [
          { required: true, message: '防坠器型号不能为空', trigger: 'blur' }
        ],
        standard: [
          { required: true, message: '防坠器规格不能为空', trigger: 'blur' }
        ],
        productDate: [
          { required: true, message: '防坠器生产不能为空', trigger: 'blur' }
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
      listProtector(this.queryParams).then(response => {
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
        standard:'',
        model:'',
        productDate:'',
        checkDate:'',
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
      this.queryParams.checkDate =''
      this.queryParams.productDate =''
      this.queryParams.model=''
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
            updateProtector(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addProtector(this.form).then(response => {
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
        return delProtector(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('/asset/protector/export', {
        ...this.queryParams
      }, `protector_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
