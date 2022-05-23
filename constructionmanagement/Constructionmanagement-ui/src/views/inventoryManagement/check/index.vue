<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="盘点名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入盘点名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="盘点完成时间" label-width="100px" prop="checkDate">
        <el-date-picker clearable
                        v-model="queryParams.checkDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择盘点完成时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="盘点添加时间" label-width="100px" prop="addDate">
        <el-date-picker clearable
                        v-model="queryParams.addDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择盘点添加时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="负责人" prop="principal">
        <el-input
          v-model="queryParams.principal"
          placeholder="请输入负责人名字"
          clearable
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
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:check:remove']"
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
          v-hasPermi="['system:check:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="checkList" :key="itemKey" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="盘点编号" align="center" prop="id"/>
      <el-table-column label="盘点名称" align="center" prop="name"/>
      <el-table-column label="盘点完成时间" align="center" prop="checkDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.checkDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="盘点添加时间" align="center" prop="addDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.addDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="负责人" align="center" prop="principal"/>
      <el-table-column label="状态" align="center" prop="status" :filters="[{ text: '未完成', value: 0 },
      { text: '已完成', value: 1 }]"
                       :filter-method="filterState"
      >
        <template slot-scope="scope">
          <el-tag :type="'primary'" v-if="scope.row.status==0">未完成</el-tag>
          <el-tag :type="'success'" v-else-if="scope.row.status==1">已完成</el-tag>
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
            v-hasPermi="['system:check:edit']"
          >修改
          </el-button>
          <el-button
            v-if="scope.row.status==0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:check:remove']"
          >删除
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-more"
            @click="handleDetail(scope.row)"
          >盘点详情
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
    <!-- 添加或修改盘点对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="盘点名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入盘点名称"/>
        </el-form-item>
        <el-form-item label="盘点完成时间" prop="checkDate">
          <el-date-picker clearable
                          v-model="form.checkDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择盘点完成时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="盘点添加时间" prop="addDate">
          <el-date-picker clearable
                          v-model="form.addDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择盘点添加时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="负责人" prop="principal">
          <el-input v-model="form.principal" placeholder="请输入负责人名称"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--    盘点详情对话框-->
    <el-dialog :title="title" :visible.sync="openDetail" width="70%" append-to-body>
      <detail :detailId="detailId" :status="status" v-on:cancel="cancel"/>
    </el-dialog>
  </div>
</template>

<script>


import { addCheck, delCheck, getCheck, listCheck, updateCheck } from '@/api/inventory/check'
import detail from './detail'

export default {
  name: 'Check',
  components: {
    detail
  },
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
      // 盘点表格数据
      checkList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        checkDate: null,
        addDate: null,
        principal: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      openDetail: false,
      detailId: null,
      status: null,
      itemKey:1,
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询盘点列表 */
    getList() {
      this.loading = true
      listCheck(this.queryParams).then(response => {
        this.checkList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail = false
      this.getList()
      this.itemKey = Math.random()
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        checkDate: null,
        addDate: null,
        principal: null
      }
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加盘点'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getCheck(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改盘点'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCheck(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addCheck(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除盘点编号为"' + ids + '"的数据项？').then(function() {
        return delCheck(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/check/export', {
        ...this.queryParams
      }, `check_${new Date().getTime()}.xlsx`)
    },
    filterState(value, row) {
      return row.status === value
    },
    handleDetail(row) {
      this.openDetail = true
      this.detailId = row.id
      this.status = row.status
    }
  }
}
</script>
