<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品编号" prop="productNum">
        <el-input
          v-model="queryParams.productNum"
          placeholder="请输入产品编号"
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
      <el-form-item label="零配件类别" label-width="auto" prop="kitType">
        <el-input
          v-model="queryParams.kitType"
          placeholder="请输入零配件类别"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="配件型号" prop="kitModel">
        <el-input
          v-model="queryParams.kitModel"
          placeholder="请输入配件型号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="配件代码" prop="kitCode">
        <el-input
          v-model="queryParams.kitCode"
          placeholder="请输入配件代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属设备" prop="equipment">
        <el-input
          v-model="queryParams.equipment"
          placeholder="请输入所属设备"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购日期">
        <el-date-picker
          v-model="queryParams.purchaseDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择采购日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="报废日期">
        <el-date-picker
          v-model="queryParams.retireDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择报废日期"
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
          icon="el-icon-check"
          size="mini"
          :disabled="multiple"
          @click="handleConfirmS"
          v-hasPermi="['inventory:manage:kitentry:confirm']"
        >确认
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-close"
          size="mini"
          :disabled="multiple"
          @click="handleAntiConfirmS"
          v-hasPermi="['inventory:manage:kitentry:confirm']"
        >反确认
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
      <el-table-column label="产品编号" align="center" prop="productNum"/>
      <el-table-column label="生产厂家" align="center" prop="vender"/>
      <el-table-column label="零配件类别" align="center" prop="kitType"/>
      <el-table-column label="配件型号" align="center" prop="kitModel"/>
      <el-table-column label="配件代码" align="center" prop="kitCode"/>
      <el-table-column label="采购日期" align="center" prop="purchaseDate">
        <template slot-scope="scope">
          <span>{{ parseTime2(scope.row.purchaseDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报废日期" align="center" prop="retireDate">
        <template slot-scope="scope">
          <span>{{ parseTime2(scope.row.retireDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单价" align="center" prop="singlePrice"/>
      <el-table-column label="数量" align="center" prop="amount"/>
      <el-table-column label="状态" align="center" prop="status" :filters="[{ text: '未确认', value: 0 },
      { text: '确认', value: 1 },{ text: '反确认', value: 2 }]"
                       :filter-method="filterState">
      <template slot-scope="scope">
        <el-tag :type="'primary'" v-if="scope.row.status==0" >未确认</el-tag>
        <el-tag :type="'success'" v-else-if="scope.row.status==1">确认</el-tag>
        <el-tag :type="'danger'"v-else>反确认</el-tag>
      </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleConfirm(scope.row)"
            v-hasPermi="['inventory:manage:kitentry:confirm']"
          >确认
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleAntiConfirm(scope.row)"
            v-hasPermi="['inventory:manage:kitentry:confirm']"
          >反确认
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
    <!--    详情弹窗-->
    <el-dialog :title="title" :visible.sync="openDetail" width="40%"  class="spec-dialog" append-to-body>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              零配件名称: {{ Detail.kitName }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              产品编号: {{ Detail.productNum }}
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
              零配件类别: {{ Detail.kitType }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              所属设备: {{ Detail.equipment }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              资产总计: {{ Detail.totalAssets }}
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
              配件属性: {{ Detail.kitProperties }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              配件规格: {{ Detail.kitStandard }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              臂长: {{ Detail.brachium}} 米
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              折旧率: {{ Detail.depreciationRate}}%
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              设备型号: {{ Detail.unitType }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              计量单位: {{ Detail.measurementUnit }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              标准节高度: {{ Detail.standardSectionHeight }}米
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              配件型号: {{ Detail.kitModel }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              配件代码: {{ Detail.kitCode }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              数量: {{ Detail.amount }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              单价: {{ Detail.singlePrice }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              防坠器: {{ Detail.protectorId }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              是否整机配件:
              <span v-if="Detail.wholeMachineAccessories>0">是</span>
              <span v-else>否</span>
            </li>
          </ul>
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              报废日期: {{ parseTime2(Detail.retireDate) }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              采购日期: {{ parseTime2(Detail.purchaseDate) }}
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
              更新时间: {{ parseTime(Detail.updateDate) }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="submit" v-if="this.title!='详情'">{{title}}</el-button>
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
import {
  kitInforConfirm,
  listKitEntry
  ,kitAntiConfirm
} from '@/api/inventory/KitEntry'
export default {
  name: 'kitInforConfirm',
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
        productNum: undefined,
        vender:undefined,
        kitType: undefined,
        purchaseDate: undefined,
        retireDate: undefined,
        equipment: undefined,
        kitModel: undefined,
        kitCode: undefined,
        objectValueAllEmpty:false,
      },
      openDetail: false,
      Detail:{},
      protectors:[],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询列表 */
    getList() {
      if(this.objectValueAllEmpty1(this.queryParams))
        this.queryParams.objectValueAllEmpty=true;
        this.loading = true
      listKitEntry(this.queryParams).then(response => {
        this.queryParams.objectValueAllEmpty=false;
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail= false
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.queryParams.retireDate='',
        this.queryParams.purchaseDate='',
        this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 多选确认按钮操作 */
    handleConfirmS() {
      if(this.Detail.id!=null) this.ids.push(this.Detail.id)
      const postIds=this.ids
      this.$modal.confirm('是否 确认').then(function() {
        return kitInforConfirm(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('确认 成功')
      }).catch(() => {
        this.$modal.msgError('确认 失败')
      })
      this.ids=[]
    },
    /** 提交按钮 */
    submit: function() {
      if(this.title=='信息确认'){
        this.handleConfirmS(this.Detail.id);
        this.openDetail  = false

      }else if(this.title=='信息反确认'){
        this.handleAntiConfirmS(this.Detail.id)
        this.openDetail  = false
      }
    },

    /** 多选反确认按钮操作 */
    handleAntiConfirmS() {
      if(this.Detail.id!=null) this.ids.push(this.Detail.id)
      const Ids =this.ids
      this.$modal.confirm('是否 反确认').then(function() {
        return kitAntiConfirm(Ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('反确认成功')
      }).catch(() => {
        this.$modal.msgError('反确认 失败')
      })
      this.ids=[]
    },
    handleDetail(row){
      this.Detail = row
      this.openDetail = true
      this.title = '详情'
    },
    /**行确认操作*/
    handleConfirm(row){
      this.Detail = row
      this.openDetail = true
      this.title = '信息确认'
    },
    /**行确认操作*/
    handleAntiConfirm(row){
      this.Detail = row
      this.openDetail = true
      this.title = '信息反确认'
    },
    filterState(value,row){
      return row.status === value;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download()
    }
  }
}
</script>
