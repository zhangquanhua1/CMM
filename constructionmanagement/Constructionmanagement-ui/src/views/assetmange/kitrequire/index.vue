<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="配件名称" prop="kitName">
        <el-input
          v-model="queryParams.kitName"
          placeholder="请输入配件名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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

      <el-form-item label="所属设备" prop="equipment">
        <el-input
          v-model="queryParams.equipment"
          placeholder="请输入所属设备"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产权单位" prop="rightsUnit">
        <el-input
          v-model="queryParams.rightsUnit"
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
          v-hasPermi="['asset:manage:kitrequire:add']"
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
          v-hasPermi="['asset:manage:kitrequire:remove']"
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
          v-hasPermi="['asset:manage:kitrequire:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['asset:manage:kitrequire:export']"
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
      <el-table-column label="配件名称" align="center" prop="kitName"/>
      <el-table-column label="产品编号" align="center" prop="productNum"/>
      <el-table-column label="生产厂家" align="center" prop="vender"/>
      <el-table-column label="归属仓库" align="center" prop="belongWarehouse"/>
      <el-table-column label="适用部件类别" align="center" prop="equipment"/>
      <el-table-column label="资产总计" align="center" prop="totalAssets"/>
      <el-table-column label="产权单位" align="center" prop="rightsUnit"/>
      <el-table-column label="数量" align="center" prop="amount"/>
      <el-table-column label="单价" align="center" prop="singlePrice"/>
      <el-table-column label="需求发起人" align="center" prop="demandSponsors"/>
      <el-table-column label="发起日期" align="center" prop="initDate">
        <template slot-scope="scope">
          <span>{{ parseTime2(scope.row.initDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="state" :filters="[{ text: '待审核', value: 0 },
      { text: '通过', value: 1 },{ text: '拒绝', value: 2 }]"
                       :filter-method="filterState"
      >
        <template slot-scope="scope">
          <el-tag :type="'primary'" v-if="scope.row.state==0">待审核</el-tag>
          <el-tag :type="'success'" v-else-if="scope.row.state==1">通过</el-tag>
          <el-tag :type="'danger'" v-else>拒绝</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="审核意见" align="center" prop="auditAdvice">
        <template slot-scope="scope">
          <span v-if="scope.row.state==0">待审核中</span>
          <span v-else>{{ scope.row.auditAdvice }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button v-if="scope.row.state==0"
                     size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleUpdate(scope.row)"
                     v-hasPermi="['asset:manage:kitrequire:edit']"
          >修改
          </el-button>
          <el-button v-if="scope.row.state==0"
                     size="mini"
                     type="text"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.row)"
                     v-hasPermi="['asset:manage:kitrequire:remove']"
          >删除
          </el-button>
          <el-button
            v-if="scope.row.state==0"
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleAudit(scope.row)"
            v-hasPermi="['asset:manage:kitrequire:audit']"
          >审核
          </el-button>

          <el-button
            size="mini"
            type="text"
            icon="el-icon-more"
            @click="handleDetail(scope.row)"
          >需求详情
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
    <el-dialog :title="title" :visible.sync="open" width="1200px" v-dialog-drag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="配件名称" prop="kitName">
              <el-input v-model.number="form.kitName" placeholder="请输入配件名称"/>
            </el-form-item>
          </el-col>
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
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="归属仓库" prop="belongWarehouse">
              <el-select v-model="form.belongWarehouse" placeholder="请选择归属仓库">
                <el-option
                  v-for="dict in warehouses"
                  :key="dict.id"
                  :label="dict.name"
                  :value="dict.name"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="适用部件类别" prop="equipment">
              <el-select v-model="form.equipment" placeholder="请选择适用部件类别">
                <el-option
                  v-for="dict in applicableKitTypeS"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="资产总计" prop="totalAssets">
              <el-input readonly="true" v-model.number="form.totalAssets" placeholder="资产总计"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="产权单位" prop="rightsUnit">
              <el-input v-model="form.rightsUnit" placeholder="请输入产权单位"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="配件属性" prop="kitProperties">
              <el-input v-model="form.kitProperties" placeholder="请输入配件属性"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="配件规格" prop="kitStandard">
              <el-input v-model="form.kitStandard" placeholder="请输入配件规格"/>
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
            <el-form-item label="配件型号" prop="kitModel">
              <el-input v-model="form.kitModel" placeholder="请输入配件型号"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="配件代码" prop="kitCode">
              <el-input v-model="form.kitCode" placeholder="请输入配件代码"/>
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
          <el-col :span="8">
            <el-form-item label="发起日期">
              <el-date-picker
                v-model="form.initDate"
                size="small"
                style="width: 240px"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="选择发起日期"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--    详情弹窗-->
    <el-drawer
      :title="title"
      size="40%"
      :visible.sync="openDetail"
      :with-header="true"
    >
      <div style="margin-left: 10px">
          <el-row>
            <el-col :span="4" class="col_title">配件名称：</el-col>
            <el-col :span="4">{{ Detail.kitName != null ? Detail.kitName : '-' }}</el-col>
            <el-col :span="4" class="col_title">产品编号：</el-col>
            <el-col :span="4">{{ Detail.productNum != null ? Detail.productNum : '-' }}</el-col>
            <el-col :span="4" class="col_title">生产厂家：</el-col>
            <el-col :span="4">{{ Detail.vender != null ? Detail.vender : '-' }}</el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="col_title">适用部件类别：</el-col>
            <el-col :span="4">{{ Detail.kitType != null ? Detail.kitType : '-' }}</el-col>
            <el-col :span="4" class="col_title">所属设备：</el-col>
            <el-col :span="4">{{ Detail.equipment != null ? Detail.equipment : '-' }}</el-col>
            <el-col :span="4" class="col_title">资产总计：</el-col>
            <el-col :span="4">{{ Detail.totalAssets != null ? Detail.totalAssets : '-' }}</el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="col_title">产权单位：</el-col>
            <el-col :span="4">{{ Detail.rightsUnit != null ? Detail.rightsUnit : '-' }}</el-col>
            <el-col :span="4" class="col_title">配件属性：</el-col>
            <el-col :span="4">{{ Detail.kitProperties != null ? Detail.kitProperties : '-' }}</el-col>
            <el-col :span="4" class="col_title">配件规格：</el-col>
            <el-col :span="4">{{ Detail.kitStandard != null ? Detail.kitStandard : '-' }}</el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="col_title">臂长：</el-col>
            <el-col :span="4">{{ Detail.brachium != null ? Detail.brachium : '-' }} 米</el-col>

            <el-col :span="4" class="col_title">设备型号：</el-col>
            <el-col :span="4">{{ Detail.unitType != null ? Detail.unitType : '-' }}</el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="col_title">计量单位：</el-col>
            <el-col :span="4">{{ Detail.measurementUnit != null ? Detail.measurementUnit : '-' }}</el-col>
            <el-col :span="4" class="col_title">标准节高度：</el-col>
            <el-col :span="4">{{ Detail.standardSectionHeight != null ? Detail.standardSectionHeight : '-' }}米
            </el-col>
            <el-col :span="4" class="col_title">配件型号：</el-col>
            <el-col :span="4">{{ Detail.kitModel != null ? Detail.kitModel : '-' }}</el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="col_title">配件代码：</el-col>
            <el-col :span="4">{{ Detail.kitCode != null ? Detail.kitCode : '-' }}</el-col>
            <el-col :span="4" class="col_title">数量：</el-col>
            <el-col :span="4">{{ Detail.amount != null ? Detail.amount : '-' }}</el-col>
            <el-col :span="4" class="col_title">单价：</el-col>
            <el-col :span="4">{{ Detail.singlePrice != null ? Detail.singlePrice : '-' }}</el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="col_title">归属仓库：</el-col>
            <el-col :span="4">{{ Detail.belongWarehouse != null ? Detail.belongWarehouse : '-' }}</el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="col_title">是否整机配件：</el-col>
            <el-col :span="4"
            ><span v-if="Detail.wholeMachineAccessories>0">是</span>
              <span v-else>否</span></el-col>
            <el-col :span="4" class="col_title">发起日期：</el-col>
            <el-col :span="4">{{ Detail.initDate != null ? parseTime2(Detail.initDate) : '-' }}</el-col>
            <el-col :span="4" class="col_title">审核状态：</el-col>
            <el-col :span="4"><span v-if="Detail.state>0">是</span>
              <span v-else>否</span></el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="col_title">录入人：</el-col>
            <el-col :span="4">{{ Detail.insertPerson != null ? Detail.insertPerson : '-' }}</el-col>
            <el-col :span="4" class="col_title">录入时间：</el-col>
            <el-col :span="4">{{ Detail.insertDate != null ? parseTime2(Detail.insertDate) : '-' }}</el-col>
            <el-col :span="4" class="col_title">录入人部门：</el-col>
            <el-col :span="4">
              {{ Detail.insertPersonDepartId != null ? getDeptName(Detail.insertPersonDepartId) : '-' }}
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="4" class="col_title">更新人部门：</el-col>
            <el-col :span="4">
              {{ Detail.updatePersonDepartId != null ? getDeptName(Detail.updatePersonDepartId) : '-' }}
            </el-col>
            <el-col :span="4" class="col_title">更新人：</el-col>
            <el-col :span="4">{{ Detail.updatePerson != null ? Detail.updatePerson : '-' }}</el-col>
            <el-col :span="4" class="col_title">更新时间：</el-col>
            <el-col :span="4">{{ Detail.updateDate != null ? parseTime2(Detail.updateDate) : '-' }}</el-col>
          </el-row>
      </div>
    </el-drawer>
<!--    审核对话框-->
    <el-dialog :title="title" :visible.sync="openAudit" @close="getList" width="25%" class="spec-dialog" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="是否通过" prop="auditAdvice">
          <el-radio-group v-model="form.state">
            <el-radio :label="2">否</el-radio>
            <el-radio :label="1">是</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核意见" prop="auditAdvice">
          <el-input type="textarea" autosize v-model="form.auditAdvice" placeholder="请输入审核意见"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAudit">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

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
import { listKitRequire, addKitRequire, delKitRequire, updateKitRequire } from '@/api/towerparam/kitrequire'
import { treeselect } from '@/api/system/dept'
import { getAllUseWH } from '@/api/towerparam/equipmentrequire'
import { getToken } from '@/utils/auth'

export default {
  name: 'kitrequire',
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
      openAudit: false,
      //适用部件类型
      applicableKitTypeS: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        kitName: undefined,
        productNum: undefined,
        vender: undefined,
        equipment: undefined,
        rightsUnit: undefined
      },
      openDetail: false,
      Detail: {},
      //计量单位
      measurement_unitS: [],
      form: {},
      // 所属部门ID字典
      depart_idOptions: [],
      //可用仓库
      warehouses: [],
      //折叠面板
      activeNames: ['1'],
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
        url: process.env.VUE_APP_BASE_API + "/asset/kitrequire/importData"
      },
      // 表单校验
      rules: {
        productNum: [
          { required: true, message: '产品编号不能为空', trigger: 'blur' }
        ],
        brachium: [{
          type: 'number', message: '臂长必须为数字值', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '臂长范围在0-1000 米',
          trigger: 'blur'
        }],
        standardSectionHeight: [{
          type: 'number', message: '标准节高度必须为数字值', trigger: 'blur'
        }, {
          pattern: /^(0|[1-9]\d?|1000)$/,
          message: '标准节高度范围在0-1000米',
          trigger: 'blur'
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
    this.getDicts('parts_type').then(response => {
      this.applicableKitTypeS = response.data
    })
    this.getList()
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      listKitRequire(this.queryParams).then(response => {
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
        kitType: undefined,
        equipment: undefined,
        totalAssets: undefined,
        rightsUnit: undefined,
        kitProperties: undefined,
        kitName: undefined,
        kitStandard: undefined,
        brachium: undefined,
        unitType: undefined,
        standardSectionHeight: undefined,
        kitModel: undefined,
        measurementUnit: undefined,
        kitCode: undefined,
        amount: undefined,
        singlePrice: undefined,
        wholeMachineAccessories: undefined,
        demandSponsors: undefined,
        initDate: undefined,
        procurementCauses: undefined,
        belongWarehouse: undefined
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
    /** 新增按钮操作 */
    handleAdd() {
      this.getUsedWH()
      this.reset()
      this.open = true
      this.title = '添加'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getUsedWH()
      this.reset()
      this.form = row
      this.open = true
      this.title = '修改'
    },
    //获取可用的仓库
    getUsedWH() {
      getAllUseWH().then(response => {
        this.warehouses = response.data
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateKitRequire(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addKitRequire(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
        this.paramsKit = []
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const postIds = row.id || this.ids
      this.$modal.confirm('是否确认删除？').then(function() {
        return delKitRequire(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    handleDetail(row) {
      this.getTreeselect()
      this.Detail = row
      this.openDetail = true
      this.title = '详情'
    },
    handleAudit(row) {
      this.form = row
      this.openAudit = true
      this.title = '审核'
    },
    submitAudit() {
      updateKitRequire(this.form).then(response => {
        this.$modal.msgSuccess('修改成功')
        this.openAudit = false
        this.getList()
      })
    },
    filterState(value, row) {
      return row.state === value
    },
    //判断那些列可选
    selectInit(row, index) {
      if (row.state != 0) {    //判断条件
        return false  //不可勾选
      } else {
        return true  //可勾选
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/asset/kitrequire/export', {
        ...this.queryParams
      }, `kitRequire_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "配件需求导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('/asset/kitrequire/importTemplate', {
      }, `kitrequire_template_${new Date().getTime()}.xlsx`)
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


  },
  watch: {
    'form.amount': {
      handler(newVal, oldVal) {
        if (newVal != oldVal && newVal != null && this.form.singlePrice != null) {
          this.form.totalAssets = this.form.singlePrice * this.form.amount
        }
      }
    },
    'form.singlePrice': {
      handler(newVal, oldVal) {
        if (newVal != oldVal && newVal != null && this.form.amount != null) {
          this.form.totalAssets = this.form.singlePrice * this.form.amount
        }
      }
    }
  }
}
</script>
