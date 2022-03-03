<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="适用设备类型" label-width="auto" prop="applicableDeviceType">
        <el-input
          v-model="queryParams.applicableDeviceType"
          placeholder="请输入适用设备类型"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
      <el-table-column label="代码" align="center" prop="partCode"/>
      <el-table-column label="型号" align="center" prop="partModel"/>
      <el-table-column label="适用设备类型" align="center" prop="applicableDeviceType"/>
      <el-table-column label="计量单位" align="center" prop="measurementUnit"/>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center"   class-name="small-padding fixed-width">
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
            icon="el-icon-edit"
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
    <el-dialog :title="title" :visible.sync="open" width="30%"  class="spec-dialog" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="部件名称" prop="partName">
          <el-input v-model="form.partName" placeholder="请输入部件名称"/>
        </el-form-item>

        <el-form-item label="部件代码" prop="partCode">
          <el-input v-model="form.partCode" placeholder="请输入部件代码"/>
        </el-form-item>

        <el-form-item label="部件型号" prop="partModel">
          <el-input v-model="form.partModel" placeholder="请输入部件型号"/>
        </el-form-item>

        <el-form-item label="计量单位" prop="measurementUnit">
          <el-input v-model="form.measurementUnit" placeholder="请输入计量单位" />
        </el-form-item>

        <el-form-item label="适用设备类型"  prop="applicableDeviceType">
          <el-input v-model.number="form.applicableDeviceType" placeholder="请输入适用设备类型"/>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注"/>
        </el-form-item>
        <div
          v-for="(item, index) in paramsKit"
          :key="index">
          <el-form-item label="配件名称">
            <el-input v-model="item.kitName" placeholder="请输入配件名称"/>
          </el-form-item>
          <el-form-item label="配件单位">
            <el-input v-model="item.unit" placeholder="请输入配件单位"/>
          </el-form-item>
          <el-form-item label="配件数量">
            <el-input-number v-model="item.kitCount" />
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
              部件名称: {{ partParamDetail.partName }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              部件代码: {{ partParamDetail.partCode }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              部件型号: {{ partParamDetail.partModel }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              计量单位: {{ partParamDetail.measurementUnit }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
          <ul class="list-group">
            <li class="list-group-item">
              适用设备类型: {{ partParamDetail.applicableDeviceType }}
            </li>
          </ul>
      </el-row>
      <el-row >
        <ul class="list-group">
          <li class="list-group-item">
            备注: {{ partParamDetail.remark }}
          </li>
        </ul>
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
      <el-row  v-for="kit in this.kits">
        <el-col :span="8" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
             {{ kit.kitName }}
            </li>
          </ul>
        </el-col>
        <el-col :span="8" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              {{ kit.unit}}
            </li>
          </ul>
        </el-col>
        <el-col :span="8" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
             {{ kit.kitCount}}
            </li>
          </ul>
        </el-col>
      </el-row>
      </div>

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
import {listPartParam,getKit,addPartParam,delPartParam,updatePartParam } from '@/api/towerparam/partparam'
export default {
  name: 'partparam',
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
        partModel: undefined,
        applicableDeviceType: undefined,
      },
      openDetail: false,
      partParamDetail:{},
      kits:[],
      // 新增配件参数数组
      paramsKit:[{kitName:'',unit:'',kitCount:''}],
      form: {
        partName:'',
        partCode:'',
        partModel:'',
        measurementUnit:'',
        applicableDeviceType:'',
        remark:'',
        amPartParamKits:[],
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
      listPartParam(this.queryParams).then(response => {
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
        partName: undefined,
        partCode: undefined,
        partModel: undefined,
        measurementUnit: undefined,
        applicableDeviceType: undefined,
        remark:undefined,
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
      getKit(row.id).then((res) => {
        this.paramsKit=res.data;
      })
      this.form.amPartParamKits=this.paramsKit;
      this.open = true
      this.title = '修改'
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            this.form.amPartParamKits=this.paramsKit;
            updatePartParam(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.paramsKit=[];
              this.getList()
            })
          } else {
            this.form.amPartParamKits=this.paramsKit;
            addPartParam(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.paramsKit=[];
              this.getList()
            })
          }
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
    handleDetail(row){
      const pid= row.id || 0
      getKit(pid).then((res) => {
        this.kits=res.data;
      })
      this.partParamDetail = row
      this.openDetail = true
      this.title = '详情'
    },
    removeKit(index) {
      this.paramsKit.splice(index, 1)
    },
    addKit() {
      this.paramsKit.push({ kitName: '', unit: '',kitCount: ''})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download()
    }
  }
}
</script>
