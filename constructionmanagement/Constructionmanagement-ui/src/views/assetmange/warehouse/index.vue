<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="仓库编码" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入仓库编码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入仓库名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入仓库地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="chargePerson">
        <el-input
          v-model="queryParams.chargePerson"
          placeholder="请输入仓库负责人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系手机" prop="contractNum">
        <el-input
          v-model="queryParams.contractNum"
          placeholder="请输入联系手机"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择仓库类型" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属部门" prop="depart_id">
        <treeselect v-model="queryParams.depart_id" :options="depart_idOptions" :show-count="true" placeholder="请选择所属部门"  clearable style="width: 185px"/>
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
          v-hasPermi="['inventory:manage:warehouse:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['inventory:manage:warehouse:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['inventory:manage:warehouse:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="storeHouseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="仓库编码" align="center" prop="code" />
      <el-table-column label="仓库名称" align="center" prop="name" />
      <el-table-column label="仓库面积" align="center" prop="area" />
      <el-table-column label="仓库地址" align="center" prop="address"  />
      <el-table-column label="仓库负责人" align="center" prop="chargePerson"  />
      <el-table-column label="联系手机" align="center" prop="contractNum"  />
      <el-table-column label="仓库类型" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="所属部门" align="center" prop="depart_id" :formatter="getDeptName"  />
      <el-table-column label="仓库状态" align="center" prop="status"   >
        <template slot-scope="scope">
          <el-tag :type="'success'" v-if="scope.row.status=='0'">可用</el-tag>
          <el-tag :type="'primary'" v-else-if="scope.row.status=='1'">维护中</el-tag>
          <el-tag :type="'danger'" v-else>已满</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['inventory:manage:warehouse:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['inventory:manage:warehouse:remove']"
          >删除</el-button>
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

    <!-- 添加或修改仓库维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" v-dialog-drag :close-on-click-modal = "false" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库编号" prop="code">
              <el-input v-model="form.code" placeholder="请输入仓库编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="仓库名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入仓库名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择仓库类型">
                <el-option
                  v-for="dict in typeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属部门" prop="depart_id">
              <treeselect v-model="form.depart_id" :options="depart_idOptions" :show-count="true" placeholder="请选择所属部门" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库面积" prop="area">
              <el-input v-model="form.area" placeholder="请输入仓库面积" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="仓库地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入仓库地址" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库负责人" prop="chargePerson">
              <el-input v-model="form.chargePerson" placeholder="请输入仓库负责人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系手机" prop="contractNum">
              <el-input v-model="form.contractNum" placeholder="请输入联系手机" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="仓库状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择仓库状态">
                <el-option
                  v-for="dict in warehouseStatus"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWarehouse, delWarehouse, addWarehouse, updateWarehouse } from "@/api/inventory/warehouse";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "warehouse",
  components: { Treeselect },
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
      // 仓库维护表格数据
      storeHouseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 仓库类型字典
      typeOptions: [],
      //仓库状态
      warehouseStatus:[],
      // 所属部门ID字典
      depart_idOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        code: null,
        name: null,
        address: null,
        chargePerson: null,
        contractNum: null,
        type: null,
        depart_id: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        code: [
          { required: true, message: '仓库编号不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '仓库名称不能为空', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '仓库类型不能为空', trigger: 'blur' }
        ],
        depart_id: [
          { required: true, message: '所属部门不能为空', trigger: 'blur' }
        ],
        contractNum:[{
          pattern:"^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$",message:"手机号格式不正确",trigger:'blur'
        }]
      }
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getDicts('warehouse_type').then(response => {
      this.typeOptions = response.data
    })
    this.getDicts('warehouseStatus').then(response => {
      this.warehouseStatus = response.data
    })
  },
  methods: {

    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.depart_idOptions = response.data;
      });
    },
    /** 查询仓库维护列表 */
    getList() {
      this.loading = true;
      listWarehouse(this.queryParams).then(response => {
        this.storeHouseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //根据部门id 获取部门名
    getDeptName(row){
      return this.getDeptNameByID(this.depart_idOptions,row.depart_id)
    },
    // 仓库类型字典翻译
    typeFormat(row) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        code: null,
        name: null,
        area: null,
        address: null,
        chargePerson: null,
        contractNum: null,
        status:null,
        type: null,
        depart_id: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加仓库维护";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
       this.reset()
       this.reset()
        this.form =JSON.parse(JSON.stringify(row))
        this.open = true;
        this.title = "修改仓库维护";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWarehouse(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWarehouse(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delWarehouse(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/asset/warehouse/export', {
        ...this.queryParams
      }, `warehouse_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
