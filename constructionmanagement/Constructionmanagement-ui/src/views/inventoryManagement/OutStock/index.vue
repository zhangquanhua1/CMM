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
      <el-form-item label="所属仓库" prop="belongWarehouse">
        <el-select v-model="queryParams.belongWarehouse" placeholder="请选择所属仓库">
          <el-option
            v-for="dict in warehouses"
            :key="dict.id"
            :label="dict.name"
            :value="dict.name"
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
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['inventory:manage:StockOutRecord:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['inventory:manage:StockOutRecord:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
      <el-table v-loading="loading" :data="DataList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
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
        <el-table-column label="出库数量" align="center" prop="amount"/>
        <el-table-column label="所属仓库" align="center" prop="belongWarehouse"/>
        <el-table-column label="出库日期" align="center" prop="begindate">
          <template slot-scope="scope">
            <span>{{ parseTime2(scope.row.begindate) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['inventory:manage:StockOutRecord:remove']"
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

    <!--    //详情抽屉-->
    <el-drawer
      :title="title"
      size="40%"
      :visible.sync="openDetail"
      :with-header="true"
    >
      <kitDetail :id="this.selectId" v-if="this.selectType==2"/>
      <equipmentDetail :id="this.selectId" v-else-if="this.selectType==1"/>
      <EquipmentKitDetail :id="this.selectId" v-else-if="this.selectType==4"/>
      <EquipmentPartDetail :id="this.selectId" v-else-if="this.selectType==5"/>
      <partDetail :id="this.selectId" v-else-if="this.selectType==3"></partDetail>
      <PartKitDetail :id="this.selectId" v-else-if="this.selectType==6"></PartKitDetail>
      <div align="center" v-else>暂无更详细的信息</div>
    </el-drawer>
  </div>
</template>

<script>
import { getAllWarehouse } from '@/api/inventory/InventoryQuery'
import { delOutStockRecord, exportOutStockRecord, listOutStockRecord } from '@/api/inventory/OutStockRecord'
import kitDetail from '@/views/inventoryManagement/InventoryQuery/kitDetail'
import equipmentDetail from '@/views/inventoryManagement/InventoryQuery/equipmentDetail'
import EquipmentKitDetail from '@/views/inventoryManagement/InventoryQuery/EquipmentKitDetail'
import EquipmentPartDetail from '@/views/inventoryManagement/InventoryQuery/EquipmentPartDetail'
import partDetail from '@/views/inventoryManagement/InventoryQuery/partDetail'
import PartKitDetail from '@/views/inventoryManagement/InventoryQuery/PartKitDetail'

export default {
  name: "StockOutRecord",
  components: { kitDetail, equipmentDetail, EquipmentKitDetail, EquipmentPartDetail, partDetail, PartKitDetail },
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
      title: "",
      // 是否显示弹出层
      open: false,
      //所有仓库
      warehouses: [],
      //类型
      types: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        type: undefined,
        model: undefined,
        belongWarehouse: undefined
      },
      //详情打开
      openDetail:false,
      selectType: '',
      selectId: '',
    };
  },
  created() {
    this.getList();
    //获取所有的仓库
    getAllWarehouse().then(response => {
      this.warehouses = response.rows
    })
    //获取类型
    this.getDicts('type').then(response => {
      this.types = response.data
    })
  },
  methods: {
    /** 查询仓库维护列表 */
    getList() {
      this.loading = true;
      listOutStockRecord(this.queryParams).then(response => {
        this.DataList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delOutStockRecord(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.selectType = row.type
      this.selectId = row.typeId
      if (this.selectId == null) {
        return this.$message('暂无更详细的内容')
      } else {
        this.openDetail = true
        this.title = '详情'
      }
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('inventory/StockOutRecord/export', {
        ...this.queryParams
      }, `recod_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
