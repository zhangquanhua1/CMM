<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
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

      <el-form-item label="设备自编号" prop="equipmentSelfNumber">
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
      <el-form-item label="融资到期日期">
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
        width="50"
      >
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
    <el-dialog :title="title" :visible.sync="open" :close-on-click-modal="false" width="60%"
               append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-collapse v-model="activeNames" accordion>
          <el-collapse-item title="设备信息" name="1">
            <el-row>
              <el-col :span="8">
                <el-form-item label="设备名称" prop="equipmentName">
                  <el-input v-model="form.equipmentName" placeholder="请输入设备名称"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="设备编号" prop="equipmentNumber">
                  <el-input v-model.number="form.equipmentNumber" placeholder="请输入设备编号"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="设备自编号" prop="equipmentSelfNumber">
                  <el-input v-model.number="form.equipmentSelfNumber" placeholder="请输入设备自编号"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="生产厂家" prop="vender">
                  <el-input v-model="form.vender" placeholder="请输入生产厂家"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="规格型号" prop="standardModel">
                  <el-input v-model="form.standardModel" placeholder="请输入规格型号"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="设备类型" prop="equipmentType">
                  <el-select v-model="form.equipmentType" placeholder="适用设备类型">
                    <el-option
                      v-for="dict in applicableDeviceTypeS"
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
                <el-form-item label="产权单位" prop="rightsUnit">
                  <el-input v-model="form.rightsUnit" placeholder="请输入产权单位"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="备案编号" prop="recordNumber">
                  <el-input v-model="form.recordNumber" placeholder="请输入备案编号"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="归属仓库" prop="belongWarehouse">
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
                <el-form-item label="项目地址" prop="projectAddress">
                  <el-input v-model="form.projectAddress" placeholder="请输入项目地址"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="是否融资租赁" prop="financeLease">
                  <template>
                    <el-radio-group v-model="form.financeLease">
                      <el-radio :label="0">否</el-radio>
                      <el-radio :label="1">是</el-radio>
                    </el-radio-group>
                  </template>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="发票号" prop="invoiceNumber">
                  <el-input v-model.number="form.invoiceNumber" placeholder="请输入发票号"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="购买合同" prop="purchaseContract">
                  <el-input v-model="form.purchaseContract" placeholder="请输入购买合同"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="制造许可证号" prop="manufacturingLicenseNo">
                  <el-input v-model="form.manufacturingLicenseNo" placeholder="请输入制造许可证号"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
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
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
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
              </el-col>
              <el-col :span="8">
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
              </el-col>
              <el-col :span="8">
                <el-form-item label="单位责任人" prop="unitResponsiblePerson">
                  <el-input v-model="form.unitResponsiblePerson" placeholder="请输入单位责任人"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="所属部门" prop="department">
                  <treeselect v-model="form.department" :options="depart_idOptions" :show-count="true"
                              placeholder="请选择所属部门" clearable style="width: 185px"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="楼号" prop="buildingNo">
                  <el-input v-model.number="form.buildingNo" placeholder="请输入楼号"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="设备来源" prop="equipmentSource">
                  <el-input v-model="form.equipmentSource" placeholder="请输入设备来源"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="登记人" prop="registrant">
                  <el-input v-model="form.registrant" placeholder="请输入登记人"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="确认人" prop="confirmPerson">
                  <el-input v-model="form.confirmPerson" placeholder="请输入确认人"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
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
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
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
              </el-col>
              <el-col :span="8">
                <el-form-item label="出厂编号" prop="manufacturingNo">
                  <el-input v-model="form.manufacturingNo" placeholder="请输入出厂编号"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="合同单价(元)" prop="contractUnitPrice">
                  <el-input v-model.number="form.contractUnitPrice" placeholder="请输入合同单价(元)"/>
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
                <el-form-item label="税率(单位 %)" prop="rate">
                  <el-input v-model.number="form.rate" placeholder="请输入税率(单位 %)"/>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="不含税价(元)" prop="exclusiveTaxPrice">
                  <el-input v-model.number="form.exclusiveTaxPrice" placeholder="请输入不含税价(元)"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="税金(元)" prop="taxation">
                  <el-input v-model.number="form.taxation" placeholder="请输入税金(元)"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="折旧率(%)" prop="depreciationRate">
                  <el-input v-model.number="form.depreciationRate" placeholder="请输入折旧率(%)"/>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="联系电话" prop="contactNumber">
                  <el-input v-model.number="form.contactNumber" placeholder="请输入联系电话"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="融资批次" prop="financingBatch">
                  <el-input v-model="form.financingBatch" placeholder="请输入融资批次"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="融资金额" prop="financingAmount">
                  <el-input v-model.number="form.financingAmount" placeholder="请输入融资金额"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="登记时间">
                  <el-date-picker
                    v-model="form.registrationTime"
                    size="small"
                    style="width: 240px"
                    value-format="yyyy-MM-dd"
                    type="date"
                    placeholder="选择登记时间"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
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
              </el-col>
            </el-row>
          </el-collapse-item>
          <el-collapse-item title="设备参数" name="2">
            <div v-if="EquipmentDetail!=null&&EquipmentDetail!=undefined">
              <el-row>
                <el-col :span="4" class="col_title">设备名称：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.towerMachineName != null ? EquipmentDetail.towerMachineName : '-' }}
                </el-col>
                <el-col :span="4" class="col_title">生产厂家：</el-col>
                <el-col :span="4">{{ EquipmentDetail.vender != null ? EquipmentDetail.vender : '-' }}</el-col>
                <el-col :span="4" class="col_title">设备型号：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.towerMachineModel != null ? EquipmentDetail.towerMachineModel : '-' }}
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">额定起重力距：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.ratedLiftingDistance != null ? EquipmentDetail.ratedLiftingDistance : '-' }} KN/m
                </el-col>
                <el-col :span="4" class="col_title">最大起重量：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.maximumLiftingWeight != null ? EquipmentDetail.maximumLiftingWeight : '-' }} T
                </el-col>
                <el-col :span="4" class="col_title">最大幅度额定起重量：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.maximumRatedWeight != null ? EquipmentDetail.maximumRatedWeight : '-' }} T
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">最大起重量工作幅度：</el-col>
                <el-col :span="4">
                  {{
                    EquipmentDetail.maximumStartingWeightWorkingRange != null ? EquipmentDetail.maximumStartingWeightWorkingRange : '-'
                  }}
                  M
                </el-col>
                <el-col :span="4" class="col_title">最大工作幅度：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.maximumRangeWork != null ? EquipmentDetail.maximumRangeWork : '-' }} M
                </el-col>
                <el-col :span="4" class="col_title">最大起升高度：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.maximumHeightLift != null ? EquipmentDetail.maximumHeightLift : '-' }} M
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">起升速度：</el-col>
                <el-col :span="4">{{ EquipmentDetail.upDownSpeed != null ? EquipmentDetail.upDownSpeed : '-' }} m/min
                </el-col>
                <el-col :span="4" class="col_title">变幅速度：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.variableAmplitudeSpeed != null ? EquipmentDetail.variableAmplitudeSpeed : '-' }}
                  m/min
                </el-col>
                <el-col :span="4" class="col_title">回转速度：</el-col>
                <el-col :span="4">{{ EquipmentDetail.swivellingSpeed != null ? EquipmentDetail.swivellingSpeed : '-' }}
                  r/min
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">顶升速度：</el-col>
                <el-col :span="4">{{ EquipmentDetail.upSpeed != null ? EquipmentDetail.upSpeed : '-' }} m/min</el-col>
                <el-col :span="4" class="col_title">塔机自重：</el-col>
                <el-col :span="4">{{ EquipmentDetail.balanceWeight != null ? EquipmentDetail.balanceWeight : '-' }} t
                </el-col>
                <el-col :span="4" class="col_title">平衡重量：</el-col>
                <el-col :span="4">{{ EquipmentDetail.balanceWeight != null ? EquipmentDetail.balanceWeight : '-' }} t
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">工作电压：</el-col>
                <el-col :span="4">{{ EquipmentDetail.workingVoltage != null ? EquipmentDetail.workingVoltage : '-' }}
                  v
                </el-col>
                <el-col :span="4" class="col_title">装机总容量：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.totalInstalledCapacity != null ? EquipmentDetail.totalInstalledCapacity : '-' }} KW
                </el-col>
                <el-col :span="4" class="col_title">电机功率：</el-col>
                <el-col :span="4">{{ EquipmentDetail.motorPower != null ? EquipmentDetail.motorPower : '-' }} KW
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">变频器功率：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.frequencyConverterPower != null ? EquipmentDetail.frequencyConverterPower : '-' }}
                  KW
                </el-col>
                <el-col :span="4" class="col_title">标准节规格：</el-col>
                <el-col :span="4">
                  {{
                    EquipmentDetail.standardSectionSpecifications != null ? EquipmentDetail.standardSectionSpecifications : '-'
                  }}
                </el-col>
                <el-col :span="4" class="col_title">附墙框规格：</el-col>
                <el-col :span="4">
                  {{
                    EquipmentDetail.attachedWallFrameSpecifications != null ? EquipmentDetail.attachedWallFrameSpecifications : '-'
                  }}
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">备注：</el-col>
                <el-col :span="20">{{ EquipmentDetail.remark != null ? EquipmentDetail.remark : '-' }}</el-col>
              </el-row>
            </div>
            <div v-else>无该设备的参数信息</div>
          </el-collapse-item>
          <el-collapse-item title="部件清单" name="3">
            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  size="mini"
                  @click="addPartRow"
                  v-hasPermi="['asset:manage:partrequire:add']"
                >新增
                </el-button>
                <el-button type="primary" size="mini" v-if="!isEdit" @click="editPart()">编辑</el-button>
                <el-button type="primary" size="mini" v-if="isEdit" @click="savePartRow()">保存</el-button>
              </el-col>
            </el-row>
            <el-table
              :data="EquipmentPartsList"
              :key="mainTableKey"
              style="width: 100%"
            >
              <el-table-column
                prop="partName"
                label="部件名称"
                width="180"
              >
                <template slot-scope="scope">
                  <el-input v-if="isEdit" v-model="scope.row.partName" placeholder="请输入部件名称"></el-input>
                  <span v-else>{{ scope.row.partName }}</span>
                </template>
              </el-table-column>
              <el-table-column label="部件型号" align="center" prop="partModel">
                <template slot-scope="scope">
                  <el-input v-if="isEdit" v-model="scope.row.partModel" placeholder="请输入部件型号"></el-input>
                  <span v-else>{{ scope.row.partModel }}</span>
                </template>
              </el-table-column>
              <el-table-column label="部件代码" align="center" prop="partCode">
                <template slot-scope="scope">
                  <el-input v-if="isEdit" v-model="scope.row.partCode" placeholder="请输入部件代码"></el-input>
                  <span v-else>{{ scope.row.partCode }}</span>
                </template>
              </el-table-column>
              <el-table-column label="部件类型" align="center" prop="partType">
                <template slot-scope="scope">
                  <el-select v-if="isEdit" v-model="scope.row.partType" placeholder="请选择适用部件类型">
                    <el-option
                      v-for="dict in applicableKitTypeS"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                  <span v-else>{{ scope.row.partType }}</span>
                </template>
              </el-table-column>
              <el-table-column label="适用设备类型" align="center" prop="applicableDeviceType">
                <template slot-scope="scope">
                  <el-select v-if="isEdit" v-model="scope.row.applicableDeviceType" placeholder="适用设备类型">
                    <el-option
                      v-for="dict in applicableDeviceTypeS"
                      :key="dict.dictValue"
                      :label="dict.dictLabel"
                      :value="dict.dictValue"
                    ></el-option>
                  </el-select>
                  <span v-else>{{ scope.row.applicableDeviceType }}</span>
                </template>
              </el-table-column>
              <el-table-column label="数量" align="center" prop="partCount">
                <template slot-scope="scope">
                  <el-input v-if="isEdit" v-model="scope.row.partCount" placeholder="请输入数量"></el-input>
                  <span v-else>{{ scope.row.partCount }}</span>
                </template>
              </el-table-column>
              <el-table-column label="计量单位" align="center" prop="measurementUnit">
                <template slot-scope="scope">
                  <el-select v-if="isEdit" v-model="scope.row.measurementUnit" placeholder="请选择计量单位">
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
                    @click="delPartRow(scope.row)"
                  >删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-collapse-item>
          <el-collapse-item title="配件清单" name="4">

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
              :data="EquipmentKitsList"
              :key="mainTableKey2"
              style="width: 100%"
            >
              <el-table-column
                prop=""
                label="配件名称"
                width="180"
              >
                <template slot-scope="scope">
                  <el-input v-if="isEditKit" v-model="scope.row.kitName" placeholder="请输入配件名称"></el-input>
                  <span v-else>{{ scope.row.kitName }}</span>
                </template>
              </el-table-column>
              <el-table-column label="配件型号" align="center" prop="">
                <template slot-scope="scope">
                  <el-input v-if="isEditKit" v-model="scope.row.kitModel" placeholder="请输入配件型号"></el-input>
                  <span v-else>{{ scope.row.kitModel }}</span>
                </template>
              </el-table-column>
              <el-table-column label="配件代码" align="center" prop="">
                <template slot-scope="scope">
                  <el-input v-if="isEditKit" v-model="scope.row.kitCode" placeholder="请输入配件代码"></el-input>
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

    <el-drawer
      title="设备详情"
      size="60%"
      :visible.sync="openDetail"
      :with-header="true"
    >
      <div style="margin-left: 10px">
        <el-collapse v-model="activeNames" accordion>
          <el-collapse-item title="设备信息" name="1">
            <el-row>
              <el-col :span="4" class="col_title">设备名称：</el-col>
              <el-col :span="4">{{ Detail.equipmentName != null ? Detail.equipmentName : '-' }}</el-col>
              <el-col :span="4" class="col_title">设备编号：</el-col>
              <el-col :span="4">{{ Detail.equipmentNumber != null ? Detail.equipmentNumber : '-' }}</el-col>
              <el-col :span="4" class="col_title">生产厂家：</el-col>
              <el-col :span="4">{{ Detail.vender != null ? Detail.vender : '-' }}</el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title">规格型号：</el-col>
              <el-col :span="4">{{ Detail.standardModel != null ? Detail.standardModel : '-' }}</el-col>
              <el-col :span="4" class="col_title">设备类别：</el-col>
              <el-col :span="4">{{ Detail.equipmentType != null ? Detail.equipmentType : '-' }}</el-col>
              <el-col :span="4" class="col_title">设备自编号：</el-col>
              <el-col :span="4">{{ Detail.equipmentSelfNumber != null ? Detail.equipmentSelfNumber : '-' }}</el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title">产权单位：</el-col>
              <el-col :span="4">{{ Detail.rightsUnit != null ? Detail.rightsUnit : '-' }}</el-col>
              <el-col :span="4" class="col_title">归属仓库：</el-col>
              <el-col :span="4">{{ Detail.belongWarehouse != null ? Detail.belongWarehouse : '-' }}</el-col>
              <el-col :span="4" class="col_title">项目地址：</el-col>
              <el-col :span="4">{{ Detail.projectAddress != null ? Detail.projectAddress : '-' }}</el-col>
            </el-row>

            <el-row>
              <el-col :span="4" class="col_title">是否融资：</el-col>
              <el-col :span="4"><span v-if="Detail.financeLease==0">否</span>
                <span v-else>是</span></el-col>
              <el-col :span="4" class="col_title">购买合同：</el-col>
              <el-col :span="4">{{ Detail.purchaseContract != null ? Detail.purchaseContract : '-' }}</el-col>
              <el-col :span="4" class="col_title">制造许可证号：</el-col>
              <el-col :span="4">{{
                  Detail.manufacturingLicenseNo != null ? Detail.manufacturingLicenseNo : '-'
                }}
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title">出厂日期：</el-col>
              <el-col :span="4">{{ Detail.productDate != null ? parseTime2(Detail.productDate) : '-' }}</el-col>
              <el-col :span="4" class="col_title">采购日期：</el-col>
              <el-col :span="4">{{ Detail.purchaseDate != null ? parseTime2(Detail.purchaseDate) : '-' }}</el-col>
              <el-col :span="4" class="col_title">报废日期：</el-col>
              <el-col :span="4">{{ Detail.retireDate != null ? parseTime2(Detail.retireDate) : '-' }}</el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title">单位责任人：</el-col>
              <el-col :span="4">{{ Detail.unitResponsiblePerson != null ? Detail.unitResponsiblePerson : '-' }}</el-col>
              <el-col :span="4" class="col_title">所属部门：</el-col>
              <el-col :span="4">
                <el-col :span="4">{{ Detail.department != null ? Detail.department : '-' }}</el-col>
              </el-col>
              <el-col :span="4" class="col_title"> 楼号:</el-col>
              <el-col :span="4">
                <el-col :span="4">{{ Detail.buildingNo != null ? Detail.buildingNo : '-' }}</el-col>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="4" class="col_title">设备来源：</el-col>
              <el-col :span="4">{{ Detail.equipmentSource != null ? (Detail.equipmentSource) : '-' }}</el-col>
              <el-col :span="4" class="col_title">登记人：</el-col>
              <el-col :span="4">
                {{ Detail.registrant != null ? Detail.registrant : '-' }}
              </el-col>
              <el-col :span="4" class="col_title"> 开票时间:</el-col>
              <el-col :span="4">
                {{ Detail.invoiceTime != null ? parseTime(Detail.invoiceTime) : '-' }}
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="4" class="col_title">出厂编号：</el-col>
              <el-col :span="4">{{ Detail.manufacturingNo != null ? (Detail.manufacturingNo) : '-' }}</el-col>
              <el-col :span="4" class="col_title">合同单价：</el-col>
              <el-col :span="4">
                {{ Detail.contractUnitPrice != null ? Detail.contractUnitPrice : '-' }} 元
              </el-col>
              <el-col :span="4" class="col_title"> 融资到期日期:</el-col>
              <el-col :span="4">
                {{ Detail.financingMaturityDate != null ? parseTime(Detail.financingMaturityDate) : '-' }}
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="4" class="col_title">数量：</el-col>
              <el-col :span="4">{{ Detail.amount != null ? (Detail.amount) : '-' }}</el-col>
              <el-col :span="4" class="col_title">税率：</el-col>
              <el-col :span="4">
                {{ Detail.rate != null ? Detail.rate : '-' }} %
              </el-col>
              <el-col :span="4" class="col_title"> 不含税价:</el-col>
              <el-col :span="4">
                {{ Detail.exclusiveTaxPrice != null ? Detail.exclusiveTaxPrice : '-' }} 元
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title">税金：</el-col>
              <el-col :span="4">{{ Detail.taxation != null ? (Detail.taxation) : '-' }} 元</el-col>
              <el-col :span="4" class="col_title">折旧率：</el-col>
              <el-col :span="4">
                {{ Detail.depreciationRate != null ? Detail.depreciationRate : '-' }} %
              </el-col>
              <el-col :span="4" class="col_title"> 联系电话:</el-col>
              <el-col :span="4">
                {{ Detail.contactNumber != null ? Detail.contactNumber : '-' }} 元
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title"> 登记时间:</el-col>
              <el-col :span="4">
                {{ Detail.registrationTime != null ? parseTime(Detail.registrationTime) : '-' }}
              </el-col>
              <el-col :span="4" class="col_title"> 确认时间:</el-col>
              <el-col :span="4">
                {{ Detail.acknowledgingTime != null ? parseTime(Detail.acknowledgingTime) : '-' }}
              </el-col>
              <el-col :span="4" class="col_title"> 融资批次:</el-col>
              <el-col :span="4">
                {{ Detail.financingBatch != null ? (Detail.financingBatch) : '-' }}
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title"> 融资金额:</el-col>
              <el-col :span="4">
                {{ Detail.financingAmount != null ? (Detail.financingAmount) : '-' }}
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title">录入人部门：</el-col>
              <el-col :span="4">
                {{ Detail.insertPersonDepartId != null ? getDeptName(Detail.insertPersonDepartId) : '-' }}
              </el-col>
              <el-col :span="4" class="col_title">更新人部门：</el-col>
              <el-col :span="4">
                {{ Detail.updatePersonDepartId != null ? getDeptName(Detail.updatePersonDepartId) : '-' }}
              </el-col>
              <el-col :span="4" class="col_title"> 更新人:</el-col>
              <el-col :span="4">
                {{ Detail.updatePerson != null ? Detail.updatePerson : '-' }}
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title">录入时间：</el-col>
              <el-col :span="4">{{ Detail.updateDate != null ? parseTime(Detail.updateDate) : '-' }}</el-col>
            </el-row>
          </el-collapse-item>
          <el-collapse-item title="设备参数" name="2">
            <div v-if="EquipmentDetail!=null&&EquipmentDetail!=undefined">
              <el-row>
                <el-col :span="4" class="col_title">设备名称：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.towerMachineName != null ? EquipmentDetail.towerMachineName : '-' }}
                </el-col>
                <el-col :span="4" class="col_title">生产厂家：</el-col>
                <el-col :span="4">{{ EquipmentDetail.vender != null ? EquipmentDetail.vender : '-' }}</el-col>
                <el-col :span="4" class="col_title">设备型号：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.towerMachineModel != null ? EquipmentDetail.towerMachineModel : '-' }}
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">额定起重力距：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.ratedLiftingDistance != null ? EquipmentDetail.ratedLiftingDistance : '-' }} KN/m
                </el-col>
                <el-col :span="4" class="col_title">最大起重量：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.maximumLiftingWeight != null ? EquipmentDetail.maximumLiftingWeight : '-' }} T
                </el-col>
                <el-col :span="4" class="col_title">最大幅度额定起重量：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.maximumRatedWeight != null ? EquipmentDetail.maximumRatedWeight : '-' }} T
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">最大起重量工作幅度：</el-col>
                <el-col :span="4">
                  {{
                    EquipmentDetail.maximumStartingWeightWorkingRange != null ? EquipmentDetail.maximumStartingWeightWorkingRange : '-'
                  }}
                  M
                </el-col>
                <el-col :span="4" class="col_title">最大工作幅度：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.maximumRangeWork != null ? EquipmentDetail.maximumRangeWork : '-' }} M
                </el-col>
                <el-col :span="4" class="col_title">最大起升高度：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.maximumHeightLift != null ? EquipmentDetail.maximumHeightLift : '-' }} M
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">起升速度：</el-col>
                <el-col :span="4">{{ EquipmentDetail.upDownSpeed != null ? EquipmentDetail.upDownSpeed : '-' }} m/min
                </el-col>
                <el-col :span="4" class="col_title">变幅速度：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.variableAmplitudeSpeed != null ? EquipmentDetail.variableAmplitudeSpeed : '-' }}
                  m/min
                </el-col>
                <el-col :span="4" class="col_title">回转速度：</el-col>
                <el-col :span="4">{{ EquipmentDetail.swivellingSpeed != null ? EquipmentDetail.swivellingSpeed : '-' }}
                  r/min
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">顶升速度：</el-col>
                <el-col :span="4">{{ EquipmentDetail.upSpeed != null ? EquipmentDetail.upSpeed : '-' }} m/min</el-col>
                <el-col :span="4" class="col_title">塔机自重：</el-col>
                <el-col :span="4">{{ EquipmentDetail.balanceWeight != null ? EquipmentDetail.balanceWeight : '-' }} t
                </el-col>
                <el-col :span="4" class="col_title">平衡重量：</el-col>
                <el-col :span="4">{{ EquipmentDetail.balanceWeight != null ? EquipmentDetail.balanceWeight : '-' }} t
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">工作电压：</el-col>
                <el-col :span="4">{{ EquipmentDetail.workingVoltage != null ? EquipmentDetail.workingVoltage : '-' }}
                  v
                </el-col>
                <el-col :span="4" class="col_title">装机总容量：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.totalInstalledCapacity != null ? EquipmentDetail.totalInstalledCapacity : '-' }} KW
                </el-col>
                <el-col :span="4" class="col_title">电机功率：</el-col>
                <el-col :span="4">{{ EquipmentDetail.motorPower != null ? EquipmentDetail.motorPower : '-' }} KW
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">变频器功率：</el-col>
                <el-col :span="4">
                  {{ EquipmentDetail.frequencyConverterPower != null ? EquipmentDetail.frequencyConverterPower : '-' }}
                  KW
                </el-col>
                <el-col :span="4" class="col_title">标准节规格：</el-col>
                <el-col :span="4">
                  {{
                    EquipmentDetail.standardSectionSpecifications != null ? EquipmentDetail.standardSectionSpecifications : '-'
                  }}
                </el-col>
                <el-col :span="4" class="col_title">附墙框规格：</el-col>
                <el-col :span="4">
                  {{
                    EquipmentDetail.attachedWallFrameSpecifications != null ? EquipmentDetail.attachedWallFrameSpecifications : '-'
                  }}
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">备注：</el-col>
                <el-col :span="20">{{ EquipmentDetail.remark != null ? EquipmentDetail.remark : '-' }}</el-col>
              </el-row>
            </div>
            <div v-else>无该设备的参数信息</div>
          </el-collapse-item>
          <el-collapse-item title="部件清单" name="3">
            <el-table
              :data="EquipmentPartsList"
              style="width: 100%"
            >
              <el-table-column
                prop="partName"
                label="部件名称"
                width="180"
              >
              </el-table-column>
              <el-table-column label="部件型号" align="center" prop="partModel">
              </el-table-column>
              <el-table-column label="部件代码" align="center" prop="partCode">
              </el-table-column>
              <el-table-column label="部件类型" align="center" prop="partType">
              </el-table-column>
              <el-table-column label="适用设备类型" align="center" prop="applicableDeviceType">
              </el-table-column>
              <el-table-column label="数量" align="center" prop="partCount">
              </el-table-column>
              <el-table-column label="计量单位" align="center" prop="measurementUnit">
              </el-table-column>
            </el-table>
          </el-collapse-item>
          <el-collapse-item title="配件清单" name="4">
            <el-table
              :data="EquipmentKitsList"
              style="width: 100%"
            >
              <el-table-column
                prop="kitName"
                label="零配件名称"
                width="180"
              >
              </el-table-column>
              <el-table-column label="配件型号" align="center" prop="kitModel">
              </el-table-column>
              <el-table-column label="配件代码" align="center" prop="kitCode">
              </el-table-column>
              <el-table-column label="技术参数" align="center" prop="technicalParam">
              </el-table-column>
              <el-table-column label="适用部件类型" align="center" prop="applicableKitType">
              </el-table-column>
              <el-table-column label="数量" align="center" prop="kitCount">
              </el-table-column>
              <el-table-column label="计量单位" align="center" prop="measurementUnit">
              </el-table-column>
            </el-table>
          </el-collapse-item>
        </el-collapse>
      </div>

    </el-drawer>

  </div>
</template>
<style lang="scss">
.spec-dialog .el-dialog__body {
  padding: 3px 30px;
  height: 500px;
  //overflow: auto;
}
</style>
<script>
import {
  addEquipmentEntry,
  delEquipmentEntry,
  listEquipmentEntry,
  updateEquipmentEntry,
  getAllUseWH,
  getKitAndPartByEquipmentEntry,
  getKitAndPartByEquipmentEntry2
} from '@/api/inventory/EquipmentEntry'
import { treeselect } from '@/api/system/dept'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { getEquipmentParam } from '@/api/towerparam/equipmentrequire'

export default {
  name: 'equipmentEntry',
  // dicts: ['sys_normal_disable'],
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
        equipmentSelfNumber: undefined,
        equipmentName: undefined,
        standardModel: undefined,
        vender: undefined,
        equipmentType: undefined,
        rightsUnit: undefined,
        belongWarehouse: undefined,
        projectAddress: undefined,
        recordNumber: undefined,
        purchaseDate: undefined,
        retireDate: undefined,
        financingMaturityDate: undefined,
        insertDate: undefined,
        objectValueAllEmpty: false
      },
      openDetail: false,
      Detail: {},
      form: {
        wmEquipmentEntryKits: undefined,
        wmEquipmentEntryParts: undefined
      },
      activeNames: ['1'],
      //添加时选择的设备类别的设备参数
      EquipmentDetail: { id: undefined },
      //设备类别
      applicableDeviceTypeS: [],
      //可用仓库
      warehouses: [],
      //计量单位
      measurement_unitS: [],
      // 所属部门ID字典
      depart_idOptions: [],
      mainTableKey: 1,
      mainTableKey2: 1,
      //部件是否可编辑
      isEdit: false,
      //配件是否可编辑
      isEditKit: false,
      last_modle: '',
      //记录上一次的选择的设备类别
      last_type: '',
      //记录上一次选择的设备id
      last_equmentId: '',
      //部件清单
      EquipmentPartsList: [],
      //配件清单
      EquipmentKitsList: [],
      //适用部件类型
      applicableKitTypeS: [],
      // 表单校验
      rules: {
        equipmentNumber: [
          { required: true, message: '设备编号不能为空', trigger: 'blur' }, {
            type: 'number', message: '设备编号必须为数字值', trigger: 'blur'
          }
        ],
        equipmentSelfNumber: [
          { type: 'number', message: '设备自编号必须为数字值', trigger: 'blur' }
        ],
        invoiceNumber: [
          { type: 'number', message: '发票号必须为数字值', trigger: 'blur' }
        ],
        buildingNo: [
          { type: 'number', message: '楼号必须为数字值', trigger: 'blur' }
        ],
        contractUnitPrice: [
          { type: 'number', message: '合同单价必须为数字值', trigger: 'blur' }
        ],
        amount: [
          { type: 'number', message: '数量必须为数字值', trigger: 'blur' }
        ],
        rate: [
          { type: 'number', message: '税率必须为数字值', trigger: 'blur' }
        ],
        exclusiveTaxPrice: [
          { type: 'number', message: '不含税价必须为数字值', trigger: 'blur' }
        ],
        taxation: [
          { type: 'number', message: '税金必须为数字值', trigger: 'blur' }
        ],
        depreciationRate: [
          { type: 'number', message: '折旧率必须为数字值', trigger: 'blur' }
        ],
        // contactNumber: [
        //   { type: 'number', message: '联系电话必须为数字值',trigger: 'blur' }
        // ],
        contactNumber: [
          {
            required: true,
            message: '请输入联系电话',
            trigger: 'blur'
          },
          {
            validator: function(rule, value, callback) {
              if (/^1[34578]\d{9}$/.test(value) == false) {
                callback(new Error('手机号格式错误'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        financingAmount: [
          { type: 'number', message: '融资金额必须为数字值', trigger: 'blur' }
        ]

      }
    }
  },
  created() {
    this.getList()
  },
  methods: {

    /** 查询列表 */
    getList() {
      if (this.objectValueAllEmpty1(this.queryParams)) {
        this.queryParams.objectValueAllEmpty = true
      }
      this.loading = true
      listEquipmentEntry(this.queryParams).then(response => {
        this.queryParams.objectValueAllEmpty = false
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
    getDeptName(row) {
      return this.getDeptNameByID(this.depart_idOptions, row.depart_id)
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail = false
      this.EquipmentKitsList = []
      this.EquipmentPartsList = [],
        this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        vender: undefined,
        equipmentNumber: undefined,
        equipmentSelfNumber: undefined,
        equipmentName: undefined,
        standardModel: undefined,
        equipmentType: undefined,
        recordNumber: undefined,
        rightsUnit: undefined,
        belongWarehouse: undefined,
        projectAddress: undefined,
        financeLease: undefined,
        invoiceNumber: undefined,
        purchaseContract: undefined,
        manufacturingLicenseNo: undefined,
        purchaseDate: undefined,
        retireDate: undefined,
        unitResponsiblePerson: undefined,
        department: undefined,
        buildingNo: undefined,
        equipmentSource: undefined,
        registrant: undefined,
        confirmPerson: undefined,
        invoiceTime: undefined,
        financingMaturityDate: undefined,
        manufacturingNo: undefined,
        contractUnitPrice: undefined,
        amount: undefined,
        rate: undefined,
        exclusiveTaxPrice: undefined,
        taxation: undefined,
        depreciationRate: undefined,
        contactNumber: undefined,
        registrationTime: undefined,
        acknowledgingTime: undefined,
        financingBatch: undefined,
        financingAmount: undefined
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
      this.queryParams.equipmentName = ''
      this.queryParams.retireDate = '',
        this.queryParams.purchaseDate = '',
        this.queryParams.financingMaturityDate = '',
        this.queryParams.insertDate = '',
        this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    //获取字典数据
    getDict() {
      this.getDicts('parts_type').then(response => {
        this.applicableKitTypeS = response.data
      })
      this.getDicts('measurement_unit').then(response => {
        this.measurement_unitS = response.data
      })
      //获取设备类别
      this.getDicts('applicableDeviceType').then(response => {
        this.applicableDeviceTypeS = response.data
      })
      //获取可用的仓库
      getAllUseWH().then(response => {
        this.warehouses = response.data
      })
      this.getTreeselect()
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getDict()
      this.reset()
      this.open = true
      this.title = '添加'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getDict()
      this.reset()
      this.form = row
      this.open = true
      this.title = '修改'
    },

    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        this.form.wmEquipmentEntryKits = this.EquipmentKitsList
        this.form.wmEquipmentEntryParts = this.EquipmentPartsList
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
    handleDetail(row) {
      this.Detail = row
      getEquipmentParam(row.standardModel, row.equipmentType).then(response => {
        this.EquipmentDetail = response.data
      })
      getKitAndPartByEquipmentEntry2(row.id).then((response) => {
        this.EquipmentKitsList = response.data.wmEquipmentEntryKits
        this.EquipmentPartsList = response.data.wmEquipmentEntryParts
      })
      this.openDetail = true
      this.title = '详情'
    },
    // 编辑部件
    editPart(row) {
      this.isEdit = true
      this.mainTableKey = Math.random()
    },
    // 保存部件
    savePartRow() {
      this.isEdit = false
      this.mainTableKey = Math.random()
    },
    // 新增部件
    addPartRow() {
      this.EquipmentPartsList.push({
          pid: new Date().getTime(),
          partName: '',
          partModel: '',
          partCode: '',
          partType: '',
          applicableDeviceType: '',
          measurementUnit: '',
          partCount: ''
        }
      )
      this.isEdit = true
    },
    //删除部件
    delPartRow(row) {
      const list = []
      this.EquipmentPartsList.forEach(function(item) {
        if (item.id !== row.id || item.pid !== row.pid) {
          list.push(item)
        }
      })
      this.EquipmentPartsList = list
    },
    //删除配件
    delKitRow(row) {
      const list = []
      this.EquipmentKitsList.forEach(function(item) {
        if (item.id !== row.id || item.pid !== row.pid) {
          list.push(item)
        }
      })
      this.EquipmentKitsList = list
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
      this.EquipmentKitsList.push({
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
      this.download()
    }
  },
  watch: {
    'form.equipmentType': {
      handler(newVal, oldVal) {
        if (newVal != null && newVal != oldVal && this.form.standardModel != undefined) {
          var type = this.form.equipmentType
          var model = this.form.standardModel
          getEquipmentParam(model, type).then(response => {
            this.EquipmentDetail = response.data
            getKitAndPartByEquipmentEntry(this.EquipmentDetail.id).then(response => {
              this.EquipmentKitsList = response.data.wmEquipmentEntryKits
              this.EquipmentPartsList = response.data.wmEquipmentEntryParts
            })
          })
        }
      },
      immediate: true
    },
    'form.standardModel': {
      handler(newVal, oldVal) {
        if (newVal != null && newVal != oldVal && this.form.equipmentType != undefined) {
          var type = this.form.equipmentType
          var model = this.form.standardModel
          getEquipmentParam(model, type).then(response => {
            this.EquipmentDetail = response.data
            getKitAndPartByEquipmentEntry(this.EquipmentDetail.id).then(response => {
              this.EquipmentKitsList = response.data.wmEquipmentEntryKits
              this.EquipmentPartsList = response.data.wmEquipmentEntryParts
            })
          })
        }
      },
      immediate: true
    }
  }
}
</script>
