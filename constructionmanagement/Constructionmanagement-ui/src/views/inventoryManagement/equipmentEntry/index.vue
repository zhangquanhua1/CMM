<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备名称" prop="equipmentTypeName">
        <el-input
          v-model="queryParams.equipmentName"
          placeholder="请输入设备名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备编号" prop="equipmentNumber">
        <el-input
          v-model="queryParams.equipmentNumber"
          placeholder="请输入设备编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="设备自编号" label-width="auto" prop="equipmentSelfNumber">
        <el-input
          v-model="queryParams.equipmentSelfNumber"
          placeholder="请输入设备自编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="规格型号" prop="standardModel">
        <el-input
          v-model="queryParams.standardModel"
          placeholder="请输入规格型号"
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
      <el-form-item label="设备类型" prop="equipmentType">
        <el-input
          v-model="queryParams.equipmentType"
          placeholder="请输入设备类型"
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
      <el-form-item label="归属仓库" prop="belongWarehouse">
        <el-input
          v-model="queryParams.belongWarehouse"
          placeholder="请输入归属仓库"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目地址" prop="projectAddress">
        <el-input
          v-model="queryParams.projectAddress"
          placeholder="请输入项目地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="备案编号" prop="recordNumber">
        <el-input
          v-model="queryParams.recordNumber"
          placeholder="请输入备案编号"
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
      <el-form-item label="融资到期日期" label-width="auto">
        <el-date-picker
          v-model="queryParams.financingMaturityDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择融资到期日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="录入日期">
        <el-date-picker
          v-model="queryParams.insertDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择录入日期"
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
          v-hasPermi="['inventory:manage:equipmententry:add']"
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
          v-hasPermi="['inventory:manage:equipmententry:remove']"
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
      <el-table-column
        type="index"
        width="50">
      </el-table-column>
      <el-table-column label="设备名称" align="center" prop="equipmentName"/>
      <el-table-column label="设备编号" align="center" prop="equipmentNumber"/>
      <el-table-column label="设备自编号" align="center" prop="equipmentSelfNumber"/>
      <el-table-column label="生产厂家" align="center" prop="vender"/>
      <el-table-column label="规格型号" align="center" prop="standardModel"/>
      <el-table-column label="设备类型" align="center" prop="equipmentType"/>
      <el-table-column label="产权单位" align="center" prop="rightsUnit"/>
      <el-table-column label="归属仓库" align="center" prop="belongWarehouse"/>
      <el-table-column label="项目地址" align="center" prop="projectAddress"/>
      <el-table-column label="录入日期" align="center" prop="insertDate">
        <template slot-scope="scope">
          <span>{{ parseTime2(scope.row.insertDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
                     size="mini"
                     type="text"
                     icon="el-icon-edit"
                     @click="handleUpdate(scope.row)"
                     v-hasPermi="['inventory:manage:equipmententry:edit']"
          >修改
          </el-button>
          <el-button
                     size="mini"
                     type="text"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.row)"
                     v-hasPermi="['inventory:manage:equipmententry:remove']"
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
    <el-dialog :title="title" :visible.sync="open" :close-on-click-modal = "false" width="30%"  class="spec-dialog" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备名称" prop="equipmentName">
          <el-input v-model="form.equipmentName" placeholder="请输入设备名称"/>
        </el-form-item>
        <el-form-item label="设备编号" prop="equipmentNumber">
          <el-input v-model.number="form.equipmentNumber" placeholder="请输入设备编号"/>
        </el-form-item>
        <el-form-item label="设备自编号" prop="equipmentSelfNumber">
          <el-input v-model.number="form.equipmentSelfNumber" placeholder="请输入设备自编号"/>
        </el-form-item>
        <el-form-item label="生产厂家" prop="vender">
          <el-input v-model="form.vender" placeholder="请输入生产厂家"/>
        </el-form-item>
        <el-form-item label="规格型号" prop="standardModel">
          <el-input v-model="form.standardModel" placeholder="请输入规格型号"/>
        </el-form-item>
        <el-form-item label="设备类型" prop="equipmentType">
          <el-input v-model="form.equipmentType" placeholder="请输入设备类型"/>
        </el-form-item>
        <el-form-item label="产权单位" prop="rightsUnit">
          <el-input v-model="form.rightsUnit" placeholder="请输入产权单位"/>
        </el-form-item>
        <el-form-item label="备案编号" prop="recordNumber">
          <el-input v-model="form.recordNumber" placeholder="请输入备案编号"/>
        </el-form-item>
        <el-form-item label="归属仓库" prop="belongWarehouse">
          <el-input v-model="form.belongWarehouse" placeholder="请输入归属仓库"/>
        </el-form-item>
        <el-form-item label="项目地址" prop="projectAddress">
          <el-input v-model="form.projectAddress" placeholder="请输入项目地址"/>
        </el-form-item>
        <el-form-item label="是否融资租赁" prop="financeLease">
          <template>
            <el-radio-group v-model="form.financeLease">
              <el-radio :label="0">否</el-radio>
              <el-radio :label="1">是</el-radio>
            </el-radio-group>
          </template>
        </el-form-item>
        <el-form-item label="发票号" prop="invoiceNumber">
          <el-input v-model.number="form.invoiceNumber" placeholder="请输入发票号"/>
        </el-form-item>
        <el-form-item label="购买合同" prop="purchaseContract">
          <el-input v-model="form.purchaseContract" placeholder="请输入购买合同"/>
        </el-form-item>
        <el-form-item label="制造许可证号" prop="manufacturingLicenseNo">
          <el-input v-model="form.manufacturingLicenseNo" placeholder="请输入制造许可证号"/>
        </el-form-item>
        <el-form-item label="出厂日期">
          <el-date-picker
            v-model="form.productDate"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择出厂日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="采购日期">
          <el-date-picker
            v-model="form.purchaseDate"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择采购日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="报废日期">
          <el-date-picker
            v-model="form.retireDate"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择报废日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="单位责任人" prop="unitResponsiblePerson">
          <el-input v-model="form.unitResponsiblePerson" placeholder="请输入单位责任人"/>
        </el-form-item>
        <el-form-item label="所属部门" prop="department">
          <el-input v-model="form.department" placeholder="请输入所属部门"/>
        </el-form-item>
        <el-form-item label="楼号" prop="buildingNo">
          <el-input v-model.number="form.buildingNo" placeholder="请输入楼号"/>
        </el-form-item>
        <el-form-item label="设备来源" prop="equipmentSource">
          <el-input v-model="form.equipmentSource" placeholder="请输入设备来源"/>
        </el-form-item>
        <el-form-item label="登记人" prop="registrant">
          <el-input v-model="form.registrant" placeholder="请输入登记人"/>
        </el-form-item>
        <el-form-item label="确认人" prop="confirmPerson">
          <el-input v-model="form.confirmPerson" placeholder="请输入确认人"/>
        </el-form-item>
        <el-form-item label="开票时间">
          <el-date-picker
            v-model="form.invoiceTime"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择开票时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="融资到期日期">
          <el-date-picker
            v-model="form.financingMaturityDate"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择融资到期日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="出厂编号" prop="manufacturingNo">
          <el-input v-model="form.manufacturingNo" placeholder="请输入出厂编号"/>
        </el-form-item>
        <el-form-item label="合同单价(元)" prop="contractUnitPrice">
          <el-input v-model.number="form.contractUnitPrice" placeholder="请输入合同单价(元)"/>
        </el-form-item>
        <el-form-item label="数量" prop="amount">
          <el-input v-model.number="form.amount" placeholder="请输入数量"/>
        </el-form-item>
        <el-form-item label="税率(单位 %)" prop="rate">
          <el-input v-model.number="form.rate" placeholder="请输入税率(单位 %)"/>
        </el-form-item>
        <el-form-item label="不含税价(元)" prop="exclusiveTaxPrice">
          <el-input v-model.number="form.exclusiveTaxPrice" placeholder="请输入不含税价(元)"/>
        </el-form-item>
        <el-form-item label="税金(元)" prop="taxation">
          <el-input v-model.number="form.taxation" placeholder="请输入税金(元)"/>
        </el-form-item>
        <el-form-item label="折旧率(%)" prop="depreciationRate">
          <el-input v-model.number="form.depreciationRate" placeholder="请输入折旧率(%)"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactNumber">
          <el-input v-model.number="form.contactNumber" placeholder="请输入联系电话"/>
        </el-form-item>
        <el-form-item label="融资批次" prop="financingBatch">
          <el-input v-model="form.financingBatch" placeholder="请输入融资批次"/>
        </el-form-item>
        <el-form-item label="融资金额" prop="financingAmount">
          <el-input v-model.number="form.financingAmount" placeholder="请输入融资金额"/>
        </el-form-item>
        <el-form-item label="开票时间">
          <el-date-picker
            v-model="form.registrationTime"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="选择登记时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="确认时间">
          <el-date-picker
            v-model="form.acknowledgingTime"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="确认时间"
          ></el-date-picker>
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
              设备名称: {{ Detail.equipmentName }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              设备编号: {{ Detail.equipmentNumber }}
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
              规格型号: {{ Detail.standardModel }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              设备类别: {{ Detail.equipmentType }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              设备自编号: {{ Detail.equipmentSelfNumber }}
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
              归属仓库: {{ Detail.belongWarehouse }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              项目地址: {{ Detail.projectAddress }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">是否融资:
              <span v-if="Detail.financeLease==0">否</span>
              <span v-else>是</span>
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              发票号: {{ Detail.invoiceNumber}}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              购买合同: {{ Detail.purchaseContract }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="24" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              制造许可证号: {{ Detail.manufacturingLicenseNo }}
            </li>
          </ul>
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              出厂日期: {{ parseTime2(Detail.productDate) }}
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
              报废日期: {{ parseTime2(Detail.retireDate) }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              单位责任人: {{ Detail.unitResponsiblePerson }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              所属部门: {{Detail.department}}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              楼号: {{ Detail.buildingNo }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              设备来源: {{ Detail.equipmentSource }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              登记人: {{ (Detail.registrant) }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              开票时间: {{ parseTime2(Detail.invoiceTime) }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              融资到期日期: {{parseTime2(Detail.financingMaturityDate)  }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              出厂编号: {{ Detail.manufacturingNo }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              合同单价: {{ (Detail.contractUnitPrice) }} 元
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
              税率: {{ (Detail.rate) }}%
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              不含税价: {{ Detail.exclusiveTaxPrice }}元
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              税金: {{ (Detail.taxation) }}元
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              折旧率: {{ Detail.depreciationRate }}%
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              联系电话: {{ (Detail.contactNumber) }}
            </li>
          </ul>
        </el-col>
      </el-row>

      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              登记时间: {{ parseTime(Detail.registrationTime) }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              确认时间: {{parseTime(Detail.acknowledgingTime)  }}
            </li>
          </ul>
        </el-col>
      </el-row>
      <el-row >
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              融资批次: {{ Detail.financingBatch }}
            </li>
          </ul>
        </el-col>
        <el-col :span="12" :xs="100">
          <ul class="list-group">
            <li class="list-group-item">
              融资金额: {{ (Detail.financingAmount) }}
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
              录入时间: {{ parseTime(Detail.updateDate) }}
            </li>
          </ul>
        </el-col>
      </el-row>
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
  addEquipmentEntry,
  delEquipmentEntry,
  listEquipmentEntry,
  updateEquipmentEntry
} from '@/api/inventory/EquipmentEntry'
export default {
  name: 'equipmentEntry',
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
        equipmentNumber: undefined,
        equipmentSelfNumber:undefined,
        equipmentName: undefined,
        standardModel: undefined,
        vender: undefined,
        equipmentType: undefined,
        rightsUnit: undefined,
        belongWarehouse: undefined,
        projectAddress: undefined,
        recordNumber:undefined,
        purchaseDate: undefined,
        retireDate: undefined,
        financingMaturityDate:undefined,
        insertDate:undefined,
        objectValueAllEmpty:false,
      },
      openDetail: false,
      Detail:{},
      form: {
      },
      // 表单校验
      rules: {
        equipmentNumber: [
          { required: true, message: '设备编号不能为空', trigger: 'blur' },{
            type: 'number', message: '设备编号必须为数字值',trigger: 'blur' }
        ],
        equipmentSelfNumber: [
          { type: 'number', message: '设备自编号必须为数字值',trigger: 'blur' }
        ],
        invoiceNumber: [
          { type: 'number', message: '发票号必须为数字值',trigger: 'blur' }
        ],
        buildingNo: [
          { type: 'number', message: '楼号必须为数字值',trigger: 'blur' }
        ],
        contractUnitPrice: [
          { type: 'number', message: '合同单价必须为数字值',trigger: 'blur' }
        ],
        amount: [
          { type: 'number', message: '数量必须为数字值',trigger: 'blur' }
        ],
        rate: [
          { type: 'number', message: '税率必须为数字值',trigger: 'blur' }
        ],
        exclusiveTaxPrice: [
          { type: 'number', message: '不含税价必须为数字值',trigger: 'blur' }
        ],
        taxation: [
          { type: 'number', message: '税金必须为数字值',trigger: 'blur' }
        ],
        depreciationRate: [
          { type: 'number', message: '折旧率必须为数字值',trigger: 'blur' }
        ],
        // contactNumber: [
        //   { type: 'number', message: '联系电话必须为数字值',trigger: 'blur' }
        // ],
        contactNumber: [
          {
            required: true,
            message: "请输入联系电话",
            trigger: "blur"
          },
          {
            validator: function(rule, value, callback) {
              if (/^1[34578]\d{9}$/.test(value) == false) {
                callback(new Error("手机号格式错误"));
              } else {
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        financingAmount: [
          { type: 'number', message: '融资金额必须为数字值',trigger: 'blur' }
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
      if(this.objectValueAllEmpty1(this.queryParams))
        this.queryParams.objectValueAllEmpty=true;
      this.loading = true
      listEquipmentEntry(this.queryParams).then(response => {
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
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        vender: undefined,
        equipmentNumber: undefined,
        equipmentSelfNumber:undefined,
        equipmentName: undefined,
        standardModel: undefined,
        equipmentType: undefined,
        recordNumber: undefined,
        rightsUnit:undefined,
        belongWarehouse:undefined ,
        projectAddress:undefined ,
        financeLease:undefined,
        invoiceNumber:undefined,
        purchaseContract:undefined,
        manufacturingLicenseNo:undefined,
        purchaseDate:undefined ,
        retireDate:undefined ,
        unitResponsiblePerson:undefined ,
        department:undefined ,
        buildingNo:undefined ,
        equipmentSource:undefined ,
        registrant:undefined ,
        confirmPerson:undefined ,
        invoiceTime:undefined ,
        financingMaturityDate:undefined ,
        manufacturingNo:undefined ,
        contractUnitPrice:undefined ,
        amount:undefined ,
        rate:undefined ,
        exclusiveTaxPrice:undefined ,
        taxation:undefined ,
        depreciationRate:undefined ,
        contactNumber:undefined ,
        registrationTime:undefined ,
        acknowledgingTime:undefined ,
        financingBatch:undefined ,
        financingAmount:undefined ,
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
      this.queryParams.equipmentName=''
      this.queryParams.retireDate='',
        this.queryParams.purchaseDate='',
        this.queryParams.financingMaturityDate='',
        this.queryParams.insertDate='',
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
            updateEquipmentEntry(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addEquipmentEntry(this.form).then(response => {
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
        return delEquipmentEntry(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    handleDetail(row){
      this.Detail = row
      this.openDetail = true
      this.title = '详情'
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download()
    }
  }
}
</script>
