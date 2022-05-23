<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
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
    <div v-if="isNotStockChart">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="add"
            v-hasPermi="['inventory:manage:query:add']"
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
            @click="del"
            v-hasPermi="['inventory:manage:query:remove']"
          >删除
          </el-button>
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-arrow-down"
              size="mini"
              :disabled="multiple"
              @click="outStock"
              v-hasPermi="['inventory:manage:query:out']"
            >出库
            </el-button>
          </el-col>
        </el-col>
        <el-col :span="2.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['inventory:manage:query:export']"
          >导出
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="addCheck"
            v-hasPermi="['inventory:manage:query:add']"
          >新增盘点任务
          </el-button>
        </el-col>
        <el-col :span="2.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-s-grid"
            size="mini"
            @click="tranferChart"
          >图形化显示
          </el-button>
        </el-col>
        <el-col :span="2.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-s-data"
            size="mini"
            @click="tranferBybelongWarehouse"
          >仓库分类显示
          </el-button>
        </el-col>
        <!--      <el-col :span="1.5">-->
        <!--        <el-tag>设备总数 :{{ equipmentTotal }}</el-tag>-->
        <!--      </el-col>-->
        <!--      <el-col :span="1.5">-->
        <!--        <el-tag>部件总数 :{{ partTotal }}</el-tag>-->
        <!--      </el-col>-->
        <!--      <el-col :span="1.5">-->
        <!--        <el-tag>零件总数 :{{ kitTotal }}</el-tag>-->
        <!--      </el-col>-->
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
        <el-table-column label="名称" align="center" prop="name"/>
        <el-table-column label="类别" align="center" prop="type">
          <template slot-scope="scope">
            <el-tag :type="'danger'" v-if="scope.row.type==1">设备</el-tag>
            <el-tag :type="'primary'" v-else-if="scope.row.type==2">零件</el-tag>
            <el-tag :type="'primary'" v-else-if="scope.row.type==4">零件</el-tag>
            <el-tag :type="'primary'" v-else-if="scope.row.type==6">零件</el-tag>
            <el-tag :type="'success'" v-else-if="scope.row.type==3">部件</el-tag>
            <el-tag :type="'success'" v-else-if="scope.row.type==5">部件</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="型号" align="center" prop="model"/>
        <el-table-column label="数量" align="center" prop="amount"/>
        <el-table-column label="所属仓库" align="center" prop="belongWarehouse"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-arrow-down"
              @click="outStock(scope.row)"
              v-hasPermi="['inventory:manage:query:out']"
            >出库
            </el-button>

            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['inventory:manage:query:edit']"
            >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="del(scope.row)"
              v-hasPermi="['inventory:manage:query:remove']"
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
      <el-dialog :title="title" :visible.sync="open" :close-on-click-modal="false" width="50%" class="spec-dialog"
                 v-dialog-drag append-to-body
      >
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <el-row>
            <el-col :span="8">
              <el-form-item label="名称" prop="name">
                <el-input v-model="form.name" placeholder="请输入名称"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="类别" prop="type">
                <el-select clearable v-model="form.type" placeholder="请选择类别">
                  <el-option
                    v-for="dict in dict.type.type"
                    :key="dict.value"
                    :label="dict.label"
                    :value="parseInt(dict.value)"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="型号" prop="model">
                <el-input v-model="form.model" placeholder="请输入型号"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="数量" prop="amount">
                <el-input v-model.number="form.amount" placeholder="请输入数量"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="所属仓库" prop="belongWarehouse">
                <el-select v-model="form.belongWarehouse" placeholder="请选择所属仓库">
                  <el-option
                    v-for="dict in useWarehouse"
                    :key="dict.id"
                    :label="dict.name"
                    :value="dict.name"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>


        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

      <!--    //详情抽屉-->
      <el-drawer
        :title="title"
        size="40%"
        :visible.sync="openDetail"
        :with-header="true"
      >
        <kitDetail :id="this.selectId" v-if="this.selectType==2" v-on:success="getMesFromDetail"/>
        <equipmentDetail :id="this.selectId" v-else-if="this.selectType==1"/>
        <EquipmentKitDetail :id="this.selectId" v-else-if="this.selectType==4"/>
        <EquipmentPartDetail :id="this.selectId" v-else-if="this.selectType==5"/>
        <partDetail :id="this.selectId" v-else-if="this.selectType==3"></partDetail>
        <PartKitDetail :id="this.selectId" v-else-if="this.selectType==6"></PartKitDetail>
        <div align="center" v-else>暂无更详细的信息</div>
      </el-drawer>
      <!--       出库-->
      <el-dialog :title="title" :visible.sync="openOutStock" :close-on-click-modal="false" width="50%"
                 class="spec-dialog"
                 v-dialog-drag append-to-body
      >
        <el-table
          :data="Items2"
          :key="mainTableKey2"
          style="width: 100%"
        >
          <el-table-column prop="name" label="名称">
            <template slot-scope="scope">
              <span>{{ scope.row.name }}</span>
            </template>
          </el-table-column>
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
          <el-table-column label="型号" align="center" prop="model">
            <template slot-scope="scope">
              <span>{{ scope.row.model }}</span>
            </template>
          </el-table-column>
          <el-table-column label="所属仓库" align="center" prop="belongWarehouse">
            <template slot-scope="scope">
              <span>{{ scope.row.belongWarehouse }}</span>
            </template>
          </el-table-column>
          <el-table-column label="出库数量" align="center" prop="" width="200">
            <template slot-scope="scope">
              <el-input-number :min="0" :max="scope.row.Max" v-model="scope.row.amount" label="数量" width="100px"
              ></el-input-number>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="100">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                @click="delOutStock(scope.row)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitOutStock">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

      <!-- 添加或修改盘点对话框 -->
      <el-dialog :title="title" :visible.sync="openCheck"  width="70%" append-to-body>
        <CheckAdd v-on:cancel="cancel"/>
      </el-dialog>
    </div>
    <stock-chart v-else-if="!isNotStockChart&&isStockTypeChart" :equipmentTotal="equipmentTotal" :kitTotal="kitTotal" :part-total="partTotal"
                 :epipment-list="equipmentList" :part-list="partList" :kit-list="kitList" v-on:success="updateStock()" :title="title"
    />
    <stock-type-chart v-else :epipment-list="equipmentList" :part-list="partList" :kit-list="kitList" v-on:success="updateStock()"
            :title="title"/>
  </div>

</template>
<style lang="scss">
</style>
<script>
import {
  getAllWarehouse,
  list,
  add,
  del,
  update, outStockBySelected
} from '@/api/inventory/InventoryQuery'
import { treeselect } from '@/api/system/dept'
import kitDetail from './kitDetail'
import equipmentDetail from './equipmentDetail'
import EquipmentKitDetail from './EquipmentKitDetail'
import EquipmentPartDetail from './EquipmentPartDetail'
import partDetail from './partDetail'
import PartKitDetail from './PartKitDetail'
import { getAllUseWH, getEquipmentParam, getKitAndPartBySelectiv } from '@/api/towerparam/equipmentrequire'
import { addList, addOutStockRecord } from '@/api/inventory/OutStockRecord'
import stockChart from './stockChart'
import stockTypeChart from './stockTypeChart'
import CheckAdd from './CheckAdd'
export default {
  name: 'InventoryQuery',
  components: {
    kitDetail,
    equipmentDetail,
    EquipmentKitDetail,
    EquipmentPartDetail,
    partDetail,
    PartKitDetail,
    stockChart,
    stockTypeChart,
    CheckAdd
  },
  dicts: ['type'],
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
      // 表格数据
      postList: [],
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
        model: undefined,
        belongWarehouse: undefined
      },
      //折叠面板
      activeNames: ['1'],
      openDetail: false,
      Detail: {},
      protectors: [],
      form: {},
      rules: {
        name: [{
          required: true, message: '请输入名称', trigger: 'blur'
        }],
        type: [
          { required: true, message: '类别不能为空', trigger: 'blur' }],
        belongWarehouse: [{
          required: true, message: '请选择归属仓库', trigger: 'blur'
        }],
        amount: [{
          required: true, message: '请输入数量', trigger: 'blur'
        }, {
          type: 'number', message: '数量必须为数字', trigger: 'blur'
        }]
      },
      //所有仓库
      warehouses: [],
      // 所属部门ID字典
      depart_idOptions: [],
      //类型
      types: [],
      //所点击的详情类型
      selectType: '',
      selectId: '',
      //可用的仓库
      useWarehouse: [],
      //设备总数
      equipmentTotal: undefined,
      //部件总数
      partTotal: undefined,
      //零件总数
      kitTotal: undefined,
      //出库数据
      Items: [],
      Items2: [],
      //出库弹窗
      openOutStock: false,
      //刷新标志
      mainTableKey2: 1,
      //显示图表标志
      isNotStockChart: true,
      //零件列表
      kitList: [],
      //部件列表
      partList: [],
      //设备列表
      equipmentList: [],
      isStockTypeChart:true,
      openCheck:false,
      itemKey: 1,
    }
  },

  created() {
    this.getDicts('type').then(response => {
      this.types = response.data
    })
    //获取所有的仓库
    getAllWarehouse().then(response => {
      this.warehouses = response.rows
    })
    this.getList()
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      list(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    onblus(id, amount) {
      var max = ''
      this.Items.forEach(item => {
        if (item.id = id) {
          max = item.amount
        }
      })
      if (amount > max) {
        this.$modal.msgError('出库数量大于已有的数量')
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail = false
      this.openOutStock = false
      this.openCheck=false
      this.Items = []
      this.Items2 = []
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
    // 表单重置
    reset() {
      this.form = {
        name: undefined,
        type: undefined,
        model: undefined,
        amount: undefined,
        belongWarehouse: undefined
      },
        this.resetForm('form')
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.Items = selection
      this.single = selection.length != 1
      this.multiple = !selection.length
    },

    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.depart_idOptions = response.data
      })
    },
    //根据部门id 获取部门名
    getDeptName(id) {
      return this.getDeptNameByID(this.depart_idOptions, id)
    },
    //获取字典数据
    getDics() {
      //获取可用的仓库
      getAllUseWH().then(response => {
        this.useWarehouse = response.data
      })

    },
    add() {
      this.getDics()
      this.reset()
      this.open = true
      this.title = '增加'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getDics()
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
            update(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            add(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },

    /** 删除按钮操作 */
    del(row) {
      const postIds = row.id || this.ids
      this.$modal.confirm('是否确认删除？').then(function() {
        return del(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {

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
    filterState(value, row) {
      return row.status === value
    },

    /** 出库按钮操作 */
    outStock(row) {
      if (row.id != undefined) {
        this.Items.push(JSON.parse(JSON.stringify(row)))
      }
      this.Items.forEach(e => {
        e.Max = e.amount
        this.Items2.push(JSON.parse(JSON.stringify(e)))
      })
      this.title = '出库清单'
      this.openOutStock = true
    },
    //删除要出库的数据
    delOutStock(row) {
      var index = this.Items.indexOf(row)
      if (index !== -1) {
        this.Items.splice(index, 1)
      }
    },
    //提交出库数据
    submitOutStock() {
      var items = this.Items2
      outStockBySelected(items).then(response => {
        console.log('response.msg=' + response.msg)
        if (response.msg == null || response.msg == '' || response.msg == undefined) {
          this.$modal.msgSuccess('出库成功')
        } else {
          this.$alert(response.msg, '出库结果', { dangerouslyUseHTMLString: true })
        }
        this.openOutStock = false
        this.Items2 = []
        this.Items = []
        this.getList()
      })

    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/inventory/query/export', {
        ...this.queryParams
      }, `_${new Date().getTime()}.xlsx`)
    },
    //图形化显示
    tranferChart() {
      this.isNotStockChart = false
      this.title="总库存设备、部件、零件数量"
      //this.$refs.stockChart1.draw()
    },
    //表格显示
    updateStock(res) {
      console.log('显示表格吗')
      this.isNotStockChart = true
      this.isStockTypeChart=true
    },
    //仓库分类显示
    tranferBybelongWarehouse(){
      this.isNotStockChart = false
      this.isStockTypeChart=false
      this.title="仓库分类条形图"
    },
    //详情组件传递的消息
    getMesFromDetail(res){
      this.openDetail = false
    },
    //新增盘点任务
    addCheck(){
      this.openCheck=true
    },

  },
  watch: {
    'postList': {
      handler(newVal, oldVal) {
        if (newVal != null && newVal != oldVal) {
          this.equipmentTotal = 0
          this.partTotal = 0
          this.kitTotal = 0
          this.equipmentList=[]
          this.partList=[]
          this.kitList=[]
          this.postList.forEach(item => {
            if (item.type == 1) {
              this.equipmentList.push(item)
              this.equipmentTotal += item.amount
            } else if (item.type == 3 || item.type == 5) {
              this.partList.push(item)
              this.partTotal += item.amount
            } else if (item.type == 2 || item.type == 4 || item.type == 6) {
              this.kitList.push(item)
              this.kitTotal += item.amount
            }
          })

        }
      },
      immediate: true
    }

  }
}
</script>
