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
      <el-form-item label="产权单位" prop="rightsUnit">
        <el-input
          v-model="queryParams.rightsUnit"
          placeholder="请输入产权单位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备型号" prop="unitType">
        <el-input
          v-model="queryParams.unitType"
          placeholder="请输入设备型号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发起日期">
        <el-date-picker
          v-model="queryParams.insertDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择发起日期"
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
          v-hasPermi="['asset:manage:partrequire:add']"
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
          v-hasPermi="['asset:manage:partrequire:remove']"
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
          v-hasPermi="['asset:manage:partrequire:import']"
        >导入</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['asset:manage:partrequire:export']"
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
                     v-hasPermi="['asset:manage:partrequire:edit']"
          >修改
          </el-button>
          <el-button v-if="scope.row.state==0"
                     size="mini"
                     type="text"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.row)"
                     v-hasPermi="['asset:manage:partrequire:remove']"
          >删除
          </el-button>
          <el-button
            v-if="scope.row.state==0"
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleAudit(scope.row)"
            v-hasPermi="['asset:manage:partrequire:audit']"
          >审核
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-more"
            @click="handleDetail(scope.row)"
          >需求详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-more"
            @click="checkProgress(scope.row)"
          >查看进度
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
    <el-dialog :title="title" :visible.sync="open" width="1200px" class="spec-dialog" v-dialog-drag v-dialog-drag-width
               v-dialog-drag-height append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-collapse v-model="activeNames" >
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
                  <el-select v-model="form.unitType"
                             placeholder="请选择设备型号"
                  >
                    <el-option
                      v-for="dict in this.equipmentModelS"
                      :key="dict.id"
                      :label="dict.towerMachineModel"
                      :value="dict.towerMachineModel"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <input type="hidden" v-model="equipmentModel"/>
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
                  v-hasPermi="['asset:manage:partrequire:add']"
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
        </el-collapse>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button type="primary" @click="submitForm">确 定</el-button>
    <el-button @click="cancel">取 消</el-button>
  </div>
  </el-dialog>

  <!--    详情弹窗-->
    <el-drawer
      title="设备详情"
      size="60%"
      :visible.sync="openDetail"
      :with-header="true"
    >
      <div style="margin-left: 10px">
        <PartRequireDetail :detail="this.Detail">/</PartRequireDetail>
      </div>
    </el-drawer>
  <el-dialog :title="title" :visible.sync="openAudit" @close="getList" width="25%" v-dialog-drag class="spec-dialog"
             append-to-body
  >
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
    <el-dialog :title="title" :visible.sync="openProgress" @close="cancel" width="20%" height="200px" class="spec-dialog"
               append-to-body
    >
      <div class="block">
        <el-timeline>
          <el-timeline-item
            v-for="(activity, index) in activities"
            :key="index"
            :icon="activity.icon"
            :type="activity.type"
            :color="activity.color"
            :size="activity.size"
            :timestamp="activity.timestamp"
          >
            {{ activity.content }}
          </el-timeline-item>
        </el-timeline>
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
import {
  listPartRequire,
  getPartRequireKit,
  addPartRequire,
  delPartRequire,
  updatePartRequire,
  getKitS,
  getEquipmentModel
} from '@/api/towerparam/partrequire'
import { treeselect } from '@/api/system/dept'
import { getEquipmentParam, getKitAndPartBySelectiv } from '@/api/towerparam/equipmentrequire'
import PartRequireDetail from './PartRequireDetail'
import { getToken } from '@/utils/auth'
import { checkRole } from '@/utils/permission'
import { parseTime } from '@/utils/ruoyi'
export default {
  name: 'partrequire',
  // dicts: ['sys_normal_disable'],
  components: {PartRequireDetail},
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
        productNum: undefined,
        vender: undefined,
        partType: undefined,
        unitType: undefined,
        insertDate: undefined,
      },
      openDetail: false,
      Detail: {},
      openAudit: false,
      kits: [],
      form: {
        amPartRequireKits: []
      },
      //获取所选所属设备下的设备型号
      getform: {
        towerMachineType: undefined
      },
      // 设备型号集合
      equipmentModelS: [],
      //计量单位
      measurement_unitS: [],
      //所属设备
      applicableDeviceTypeS: [],
      //部件类别
      applicableKitTypeS: [],
      //记录已选择的部件名
      last_partName: '',
      // 所属部门ID字典
      depart_idOptions: [],
      activeNames: ['1'],
      //配件是否可编辑
      isEditKit: false,
      mainTableKey2: 1,
      //配件清单
      KitsList: [],
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
        url: process.env.VUE_APP_BASE_API + "/asset/partrequire/importData"
      },
      // 表单校验
      rules: {
        productNum: [
          { required: true, message: '产品编号不能为空', trigger: 'blur' }
        ],
        brachium: [{
          type: 'number', message: '臂长必须为数字值', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '臂长范围在0-999 米',
          trigger: 'blur'
        }],
        standardSectionHeight: [{
          type: 'number', message: '标准节高度', trigger: 'blur'
        }, {
          pattern: /^([0-9]|[1-9]\d|1\d\d|2[0-4]\d|99[0-9])$/,
          message: '标准节高度范围在0-999米',
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
        }],

      },
      //查看进度
      openProgress: false,
      activities: []
    }
  },
  computed: {
    equipmentModel: function() {
      var result = []
      this.getform.towerMachineType = this.form.equipment
      getEquipmentModel(this.getform).then(response => {
        this.equipmentModelS = response.rows
      })
      return result
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
      listPartRequire(this.queryParams).then(response => {
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
      this.openProgress=false
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
        procurementCauses: undefined
      },
        this.paramsKit = []
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
      this.reset()
      this.open = true
      this.title = '添加'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.form = JSON.parse(JSON.stringify(row))
      getPartRequireKit(row.id).then((res) => {
        this.KitsList = res.data
      })
      this.open = true
      this.title = '修改'
    },
    /** 提交按钮 */
    submitForm: function() {
      this.form.amPartRequireKits = this.KitsList
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updatePartRequire(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addPartRequire(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
          this.KitsList=[]
        }
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const postIds = row.id || this.ids
      this.$modal.confirm('是否确认删除？').then(function() {
        return delPartRequire(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    handleDetail(row) {
      this.Detail = row
      this.openDetail = true
      this.title = '详情'
    },
    handleAudit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.openAudit = true
      this.title = '审核'
    },
    submitAudit() {
      this.form.amPartRequireKits = []
      this.form.auditDate=new Date();
      updatePartRequire(this.form).then(response => {
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
      var roles=["admin"]
      if(checkRole(roles))
        return true
      if (row.state != 0) {    //判断条件
        return false  //不可勾选
      } else {
        return true  //可勾选
      }
    },
    //删除配件
    delKitRow(row) {
      const list = []
      this.KitsList.forEach(function(item) {
        if (item.id !== row.id || item.pid != row.pid) {
          list.push(item)
        }
      })
      this.KitsList = list
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

    /** 导出按钮操作 */
    handleExport() {
      this.download('/asset/partrequire/export', {
        ...this.queryParams
      }, `partrequire_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "部件需求导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('/asset/partrequire/importTemplate', {
      }, `partrequire_template_${new Date().getTime()}.xlsx`)
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
    },

    checkProgress(row) {
      this.title = '查看部件需求申请进度'
      this.openProgress = true
      this.activities=[{
        content: '待审核',
        timestamp: '',
        size: 'large',
        type: 'success'
      },
        {
          content: '审核结果',
          timestamp: '',
          color: '',
          type: ''
        },
        {
          content: '待采购',
          timestamp: '',
          color: '',
          type: ''
        }, {
          content: '已采购',
          timestamp: '',
          size: 'large',
          type: ''
        }]
      if(row.state==1&&row.isBuy==1){
        this.activities[1].content="审核通过";
        this.activities[1].type="success";
        this.activities[1].timestamp=parseTime(row.auditDate);
        this.activities[2].type="success";
        this.activities[3].type="success";
        this.activities[3].timestamp=parseTime(row.buyDate);
      }else if(row.state==1){
        this.activities[1].content="审核通过";
        this.activities[1].type="success";
        this.activities[1].timestamp=parseTime(row.auditDate);
        this.activities[2].type="success";
      }else if(row.state==2){
        this.activities[1].content="审核不通过";
        this.activities[1].type="danger";
        this.activities[1].timestamp=parseTime(row.auditDate);
      }

    }



  },
  watch: {
    'form.partType': {
      handler(newVal, oldVal) {
        if (newVal != null && newVal != oldVal && this.form.partType != undefined) {
          getKitS(this.form).then(response => {
            this.KitsList=response.data
          })
        }
      },
      immediate: true
    },
  }
}
</script>
