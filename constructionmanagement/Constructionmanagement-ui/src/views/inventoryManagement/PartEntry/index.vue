<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="部件名称" prop="partName">
        <el-input
          v-model="queryParams.partName"
          placeholder="请输入部件名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部件类别" prop="partType">
        <el-select v-model="queryParams.partType" placeholder="请选择部件类别">
          <el-option
            v-for="dict in applicableKitTypeS"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="部件型号" prop="partModel">
        <el-input
          v-model="queryParams.partModel"
          placeholder="请输入产权单位"
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
          v-hasPermi="['inventory:manage:partEntry:add']"
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
          v-hasPermi="['inventory:manage:partEntry:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['inventory:manage:partEntry:import']"
        >导入</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['inventory:manage:partEntry:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--显示表格-->
    <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" :selectable="selectInit" width="55" align="center"/>
      <el-table-column
        type="index"
        width="50"
      >
      </el-table-column>
      <el-table-column label="部件名称" align="center" prop="partName"/>
      <el-table-column label="产品编号" align="center" prop="productNum"/>
      <el-table-column label="生产厂家" align="center" prop="vender"/>
      <el-table-column label="部件类别" align="center" prop="partType"/>
      <el-table-column label="所属设备" align="center" prop="equipment"/>
      <el-table-column label="资产总计" align="center" prop="totalAssets"/>
      <el-table-column label="产权单位" align="center" prop="rightsUnit"/>
      <el-table-column label="设备型号" align="center" prop="unitType"/>
      <el-table-column label="计量单位" align="center" prop="measurementUnit"/>
      <el-table-column label="部件型号" align="center" prop="partModel"/>
      <el-table-column label="部件代码" align="center" prop="partCode"/>
      <el-table-column label="数量" align="center" prop="amount"/>
      <el-table-column label="单价" align="center" prop="singlePrice"/>
      <el-table-column label="需求发起人" align="center" prop="demandSponsors"/>
      <el-table-column label="采购原因" align="center" prop="procurementCauses"/>
      <el-table-column label="状态" align="center" prop="status" :filters="[{ text: '未确认', value: 0 },
      { text: '确认', value: 1 },{ text: '反确认', value: 2 }]"
                       :filter-method="filterState"
      >
        <template slot-scope="scope">
          <el-tag :type="'primary'" v-if="scope.row.status==0">未确认</el-tag>
          <el-tag :type="'success'" v-else-if="scope.row.status==1">确认</el-tag>
          <el-tag :type="'danger'" v-else>反确认</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.status==0"
                     size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleUpdate(scope.row)"
                     v-hasPermi="['asset:manage:partentry:edit']"
          >修改
          </el-button>
          <el-button v-if="scope.row.status==0"
                     size="mini"
                     type="text"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.row)"
                     v-hasPermi="['asset:manage:partentry:remove']"
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
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px" class="spec-dialog"  :close-on-click-modal = "false" v-dialog-drag v-dialog-drag-width
               v-dialog-drag-height append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-collapse v-model="activeNames" accordion>
          <el-collapse-item title="部件信息" name="1">
            <el-row>
              <el-col :span="8">
                <el-form-item label="产品编号" prop="productNum">
                  <el-input v-model.number="form.productNum" placeholder="请输入产品编号"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="生产厂家" prop="vender">
                  <el-input v-model="form.vender" placeholder="请输入生产厂家"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="部件类别" prop="partType">
                  <el-select v-model="form.partType" placeholder="请选择部件类别">
                    <el-option
                      v-for="dict in applicableKitTypeS"
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
                <el-form-item label="所属设备" prop="equipment">
                  <el-select v-model="form.equipment" placeholder="请选择所属设备">
                    <el-option
                      v-for="dict in applicableDeviceTypeS"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="资产总计" prop="totalAssets">
                  <el-input v-model.number="form.totalAssets" placeholder="请输入资产总计"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="产权单位" prop="rightsUnit">
                  <el-input v-model="form.rightsUnit" placeholder="请输入产权单位"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="零件属性" prop="kitProperties">
                  <el-input v-model="form.kitProperties" placeholder="请输入零件属性"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="部件名称" prop="partName">
                  <el-input v-model="form.partName" placeholder="请输入部件名称"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="零件规格" prop="kitStandard">
                  <el-input v-model="form.kitStandard" placeholder="请输入零件规格"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="臂长" prop="brachium">
                  <el-input v-model.number="form.brachium" placeholder="请输入臂长(米)"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="设备型号" prop="unitType">
                  <el-input v-model="form.unitType" placeholder="请输入设备型号"/>
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
                <el-form-item label="标准节高度" prop="standardSectionHeight">
                  <el-input v-model.number="form.standardSectionHeight" placeholder="请输入标准节高度(米)"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="部件型号" prop="partModel">
                  <el-input v-model="form.partModel" placeholder="请输入部件型号"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="部件代码" prop="partCode">
                  <el-input v-model="form.partCode" placeholder="请输入部件代码"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="数量" prop="amount">
                  <el-input v-model.number="form.amount" placeholder="请输入数量"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="单价" prop="singlePrice">
                  <el-input v-model.number="form.singlePrice" placeholder="请输入单价"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否整机配件" prop="wholeMachineAccessories">
                  <template>
                    <el-radio-group v-model="form.wholeMachineAccessories">
                      <el-radio :label="0">否</el-radio>
                      <el-radio :label="1">是</el-radio>
                    </el-radio-group>
                  </template>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="所属仓库" prop="belongWarehouse">
                  <el-select v-model="form.belongWarehouse" placeholder="请选择所属仓库">
                    <el-option
                      v-for="dict in warehouses"
                      :key="dict.id"
                      :label="dict.name"
                      :value="dict.name"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="需求发起人" prop="demandSponsors">
                  <el-input v-model="form.demandSponsors" placeholder="请输入需求发起人"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="采购原因" prop="procurementCauses">
                  <el-input v-model="form.procurementCauses" placeholder="请输入采购原因"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-collapse-item>
          <el-collapse-item title="零件清单" name="2">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  size="mini"
                  @click="addKitRow"
                  v-hasPermi="['asset:manage:partentry:add']"
                >新增
                </el-button>
                <el-button type="primary" size="mini" v-if="!isEditKit" @click="editKit()">编辑</el-button>
                <el-button type="primary" size="mini" v-if="isEditKit" @click="saveKitRow()">保存</el-button>
              </el-col>
            </el-row>
            <el-table
              :data="KitsList"
              :key="mainTableKey2"
              style="width: 100%"
            >
              <el-table-column
                prop=""
                label="零件名称"
                width="180"
              >
                <template slot-scope="scope">
                  <el-input v-if="isEditKit" v-model="scope.row.kitName" placeholder="请输入零件名称"></el-input>
                  <span v-else>{{ scope.row.kitName }}</span>
                </template>
              </el-table-column>
              <el-table-column label="零件型号" align="center" prop="">
                <template slot-scope="scope">
                  <el-input v-if="isEditKit" v-model="scope.row.kitModel" placeholder="请输入零件型号"></el-input>
                  <span v-else>{{ scope.row.kitModel }}</span>
                </template>
              </el-table-column>
              <el-table-column label="零件代码" align="center" prop="">
                <template slot-scope="scope">
                  <el-input v-if="isEditKit" v-model="scope.row.kitCode" placeholder="请输入零件代码"></el-input>
                  <span v-else>{{ scope.row.kitCode }}</span>
                </template>
              </el-table-column>
              <el-table-column label="技术参数" align="center" prop="">
                <template slot-scope="scope">
                  <el-input v-if="isEditKit" v-model="scope.row.technicalParam"
                            placeholder="请输入技术参数"
                  ></el-input>
                  <span v-else>{{ scope.row.technicalParam }}</span>
                </template>
              </el-table-column>
              <el-table-column label="适用部件类型" align="center" prop="">
                <template slot-scope="scope">
                  <el-select v-if="isEditKit" v-model="scope.row.applicableKitType" placeholder="请选择适用部件类型">
                    <el-option
                      v-for="dict in applicableKitTypeS"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                  <span v-else>{{ scope.row.applicableKitType }}</span>
                </template>
              </el-table-column>
              <el-table-column label="数量" align="center" prop="">
                <template slot-scope="scope">
                  <el-input v-if="isEditKit" v-model="scope.row.kitCount" placeholder="请输入数量"></el-input>
                  <span v-else>{{ scope.row.kitCount }}</span>
                </template>
              </el-table-column>
              <el-table-column label="计量单位" align="center" prop="">
                <template slot-scope="scope">
                  <el-select v-if="isEditKit" v-model="scope.row.measurementUnit" placeholder="请选择计量单位">
                    <el-option
                      v-for="dict in measurement_unitS"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                  <span v-else>{{ scope.row.measurementUnit }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="100">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    @click="delKitRow(scope.row)"
                  >删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-collapse-item>
          <el-collapse-item title="其他信息" name="3">
            <el-row>
              <el-col :span="8">
                <el-form-item label="供应商" prop="supplierId">
                  <el-input v-model="form.supplierId" placeholder="请输入供应商"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系地址" prop="supplierAdd">
                  <el-input v-model="form.supplierAdd" placeholder="请输入供应商联系地址"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系电话" prop="supplierTel">
                  <el-input v-model="form.supplierTel" placeholder="请输入供应商联系电话"/>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="8">
                <el-form-item label="经办人" prop="practiName">
                  <el-input v-model="form.practiName" placeholder="请输入经办人"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="经办人电话" prop="mobile">
                  <el-input v-model="form.mobile" placeholder="请输入经办人电话"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="录入批次号" prop="batchNumber">
                  <el-input v-model="form.batchNumber" placeholder="请输入录入批次号"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="附件" prop="enclosure">
                  <el-button type="cyan" icon="el-icon-upload" size="mini" @click="handleImport">点击上传</el-button>
                  <el-button v-if="form.enclosure!=null" type="cyan" icon="el-icon-download" size="mini"
                             @click="handleDownload(form.enclosure)"
                  >点击下载
                  </el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-collapse-item>
        </el-collapse>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-drawer
      :title="title"
      size="40%"
      :visible.sync="openDetail"
      :with-header="true"
    >
      <PartEntryDetail :Detail="this.Detail"/>
    </el-drawer>

    <!--    导入对话框-->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
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
  listPartEntry,
  addPartEntry,
  updatePartEntry,
  delPartEntry,
  listKitByPartId, exportPartEntry
} from '@/api/inventory/PartEntry'
import { treeselect } from '@/api/system/dept'
import { getAllUseWH } from '@/api/towerparam/equipmentrequire'
import PartEntryDetail from'./PartEntryDetail'
import { getToken } from '@/utils/auth'
import { checkRole } from '@/utils/permission'
export default {
  name: 'PartEntry',
  components: { PartEntryDetail },
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
        partType: undefined,
        partModel: undefined,
      },
      openDetail: false,
      Detail: {},
      form: {
        kits: []
      },
      //计量单位
      measurement_unitS: [],
      //所属设备
      applicableDeviceTypeS: [],
      //部件类别
      applicableKitTypeS: [],
      // 所属部门ID字典
      depart_idOptions: [],
      //配件清单
      KitsList: [],
      mainTableKey2: 1,
      //配件是否可编辑
      isEditKit: false,
      activeNames: ['1'],
      //可用仓库
      warehouses: [],
      // 导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/inventory/partEntry/importData"
      },
      // 表单校验
      rules: {
        productNum: [
          { required: true, message: '产品编号不能为空', trigger: 'blur' }
        ],
        brachium: [{
          type: 'number', message: '臂长必须为数字值', trigger: 'blur'
        }],
        standardSectionHeight: [{
          type: 'number', message: '标准节高度必须为数字值', trigger: 'blur'
        }],
        amount: [{
          type: 'number', message: '数量必须为数字值', trigger: 'blur'
        }],
        singlePrice: [{
          type: 'number', message: '单价必须为数字值', trigger: 'blur'
        }],
        totalAssets: [{
          type: 'number', message: '资产总计必须为数字值', trigger: 'blur'
        }]
      }
    }
  },
  created() {
    this.getDicts('measurement_unit').then(response => {
      this.measurement_unitS = response.data
    })
    this.getDicts('applicableDeviceType').then(response => {
      this.applicableDeviceTypeS = response.data
    })
    this.getDicts('parts_type').then(response => {
      this.applicableKitTypeS = response.data
    })
    this.getList()
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      listPartEntry(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
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
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail = false
      this.openAudit = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        productNum: undefined,
        vender: undefined,
        partType: undefined,
        equipment: undefined,
        totalAssets: undefined,
        rightsUnit: undefined,
        kitProperties: undefined,
        partName: undefined,
        kitStandard: undefined,
        brachium: undefined,
        unitType: undefined,
        standardSectionHeight: undefined,
        partModel: undefined,
        measurementUnit: undefined,
        partCode: undefined,
        amount: undefined,
        singlePrice: undefined,
        wholeMachineAccessories: undefined,
        demandSponsors: undefined,
        procurementCauses: undefined,
        batchNumber: undefined,
        supplierId: undefined,
        practiName: undefined,
        supplierAdd: undefined,
        supplierTel: undefined,
        mobile: undefined,
        enclosure: undefined,
        belongWarehouse:undefined
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
      this.queryParams.insertDate = ''
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    //获取字典数据
    getDics() {
      //获取可用的仓库
      getAllUseWH().then(response => {
        this.warehouses = response.data
      })
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getDics();
      this.reset()
      this.open = true
      this.title = '添加'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getDics();
      this.reset()
      this.form = JSON.parse(JSON.stringify(row))
     listKitByPartId(row.id).then(response => {
        this.KitsList =response.data
      })
      this.open = true
      this.title = '修改'
    },
    /** 提交按钮 */
    submitForm: function() {
      this.form.kits = this.KitsList
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updatePartEntry(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addPartEntry(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除？').then(function() {
        return delPartEntry(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    //删除配件
    delKitRow(row) {
     var index=this.KitsList.indexOf(row)
      if (index !== -1) {
        this.KitsList.splice(index, 1)
      }
    },// 编辑配件
    editKit(row) {
      this.isEditKit = true
      this.mainTableKey2 = Math.random()
    },
    // 保存配件
    saveKitRow() {
      this.isEditKit = false
      this.mainTableKey2 = Math.random()
    },
    // 新增配件
    addKitRow() {
      this.KitsList.push({
          pid: new Date().getTime(),
          kitName: '',
          kitModel: '',
          kitCode: '',
          technicalParam: '',
          applicableKitType: '',
          measurementUnit: '',
          kitCount: ''
        }
      )
      this.isEditKit = true
    },
    handleDetail(row) {
      this.getTreeselect()
      this.Detail = row
      this.openDetail = true
      this.title = '详情'
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
    /** 导出按钮操作 */
    handleExport() {
      this.download('/inventory/partEntry/export', {
        ...this.queryParams
      }, `partEntry_${new Date().getTime()}.xlsx`)
    },

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "部件录入导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('/inventory/partEntry/importTemplate', {
      }, `partEntry_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }

  }
}
</script>
