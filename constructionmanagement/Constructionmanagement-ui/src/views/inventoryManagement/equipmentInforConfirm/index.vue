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
      <el-form-item label="融资到期日期" >
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
          icon="el-icon-check"
          size="mini"
          @click="handleConfirmS"
          v-hasPermi="['inventory:manage:equipmententry:confirm']"
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
          v-hasPermi="['inventory:manage:equipmententry:confirm']"
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
            v-hasPermi="['inventory:manage:equipmententry:confirm']"
          >确认
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="handleAntiConfirm(scope.row)"
            v-hasPermi="['inventory:manage:equipmententry:confirm']"
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
        <br/> <br/> <br/>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submit" v-if="this.title=='信息确认'">{{title}}</el-button>
          <el-button type="danger" @click="submit" v-if="this.title=='信息反确认'">{{title}}</el-button>
          <el-button @click="cancel" v-if="this.title!='详情'">取 消</el-button>
        </div>
      </div>

    </el-drawer>
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
  equipmentAntiConfirm, equipmentInforConfirm, getKitAndPartByEquipmentEntry2,
  listEquipmentEntry,
} from '@/api/inventory/EquipmentEntry'
import { getEquipmentParam } from '@/api/towerparam/equipmentrequire'
import { treeselect } from '@/api/system/dept'
export default {
  name: 'equipmentEntryInforConfirm',
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
      //部件清单
      EquipmentPartsList: [],
      //配件清单
      EquipmentKitsList: [],
      activeNames: ['1'],
      //添加时选择的设备类别的设备参数
      EquipmentDetail: { id: undefined },
      // 所属部门ID字典
      depart_idOptions: [],
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
    /** 多选确认按钮操作 */
    handleConfirmS() {
      if(this.Detail.id!=null) this.ids.push(this.Detail.id)
      const postIds=this.ids
      this.$modal.confirm('是否 确认').then(function() {
        return equipmentInforConfirm(postIds)
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
        return equipmentAntiConfirm(Ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('反确认成功')
      }).catch(() => {
        this.$modal.msgError('反确认 失败')
      })
      this.ids=[]
    },
    handleDetail(row){
      getEquipmentParam(row.standardModel, row.equipmentType).then(response => {
        this.EquipmentDetail = response.data
      })
      getKitAndPartByEquipmentEntry2(row.id).then((response) => {
        this.EquipmentKitsList = response.data.wmEquipmentEntryKits
        this.EquipmentPartsList = response.data.wmEquipmentEntryParts
      })
      this.Detail=row
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
