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
      <el-form-item label="项目日期">
        <el-date-picker
          v-model="queryParams.projectDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择项目日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="发起日期">
        <el-date-picker
          v-model="queryParams.initDate"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="date"
          placeholder="选择发起日期"
        ></el-date-picker>
      </el-form-item>
      <!--      <el-form-item>-->
      <!--        <template>-->
      <!--          <el-radio v-model="queryParams.state" label="1">已审核</el-radio>-->
      <!--          <el-radio v-model="queryParams.state" label="0">待审核</el-radio>-->
      <!--        </template>-->
      <!--      </el-form-item>-->

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
          v-hasPermi="['asset:manage:equipmentrequire:add']"
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
          v-hasPermi="['asset:manage:equipmentrequire:remove']"
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
          v-hasPermi="['asset:manage:equipmentrequire:import']"
        >导入</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['asset:manage:equipmentrequire:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--显示表格-->
    <el-table v-loading="loading" :data="postList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" :selectable='selectInit' width="55" align="center"/>
      <el-table-column
        type="index"
        width="50"
      >
      </el-table-column>

      <el-table-column label="设备名称" align="center" prop="equipmentName"/>
      <el-table-column label="设备编号" align="center" prop="equipmentNumber"/>
      <el-table-column label="生产厂家" align="center" prop="vender"/>
      <el-table-column label="规格型号" align="center" prop="standardModel"/>
      <el-table-column label="设备类型" align="center" prop="equipmentType"/>
      <el-table-column label="设备数量" align="center" prop="equipmentCount"/>
      <el-table-column label="产权单位" align="center" prop="rightsUnit"/>
      <el-table-column label="归属仓库" align="center" prop="belongWarehouse"/>
      <el-table-column label="项目地址" align="center" prop="projectAddress"/>
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
                     v-hasPermi="['asset:manage:equipmentrequire:edit']"
          >修改
          </el-button>
          <el-button v-if="scope.row.state==0"
                     size="mini"
                     type="text"
                     icon="el-icon-delete"
                     @click="handleDelete(scope.row)"
                     v-hasPermi="['asset:manage:equipmentrequire:remove']"
          >删除
          </el-button>
          <el-button
            v-if="scope.row.state==0"
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleAudit(scope.row)"
            v-hasPermi="['asset:manage:equipmentrequire:audit']"
          >审核
          </el-button>

          <el-button
            size="mini"
            type="text"
            icon="el-icon-more"
            @click="handleDetail(scope.row)"
          >设备详情
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
    <!-- 添加 -->
    <el-dialog :title="title" :visible.sync="open" width="1200px"  :close-on-click-modal = "false" v-dialog-drag class="spec-dialog" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
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
                  <el-input v-model="form.equipmentNumber" placeholder="请输入设备编号"/>
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
                <el-form-item label="设备型号" prop="standardModel">
                  <el-input v-model="form.standardModel" placeholder="请输入设备型号"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="设备类型" prop="equipmentType">
                  <el-select v-model="form.equipmentType" placeholder="请选择设备类型">
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
                <el-form-item label="设备数量" prop="equipmentCount">
                  <el-input v-model.number="form.equipmentCount" placeholder="请输入设备数量"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="产权单位" prop="rightsUnit">
                  <el-input v-model="form.rightsUnit" placeholder="请输入产权单位"/>
                </el-form-item>
              </el-col>
              <!--              <el-col :span="8">-->
              <!--                <el-form-item label="配件属性" prop="kitProperties">-->
              <!--                  <el-input v-model="form.kitProperties" placeholder="请输入配件属性"/>-->
              <!--                </el-form-item>-->
              <!--              </el-col>-->
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
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="项目地址" prop="projectAddress">
                  <el-input v-model="form.projectAddress" placeholder="请输入项目地址"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="项目日期">
                  <el-date-picker
                    v-model="form.projectDate"
                    size="small"
                    style="width: 240px"
                    value-format="yyyy-MM-dd"
                    type="date"
                    placeholder="选择项目日期"
                  ></el-date-picker>
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
          <el-collapse-item title="零件清单" name="4">
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
    <!--修改对话框 -->
    <!--    详情弹窗-->
    <el-drawer
      title="设备详情"
      size="60%"
      :visible.sync="drawer"
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
              <el-col :span="4" class="col_title">设备数量：</el-col>
              <el-col :span="4">{{ Detail.equipmentCount != null ? Detail.equipmentCount : '-' }}</el-col>
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
              <el-col :span="4" class="col_title">项目日期：</el-col>
              <el-col :span="4">{{ Detail.projectDate != null ? parseTime2(Detail.projectDate) : '-' }}</el-col>
              <el-col :span="4" class="col_title">发起日期：</el-col>
              <el-col :span="4">{{ Detail.initDate != null ? parseTime2(Detail.initDate) : '-' }}</el-col>
              <el-col :span="4" class="col_title">需求发起人：</el-col>
              <el-col :span="4">{{ Detail.demandSponsors != null ? Detail.demandSponsors : '-' }}</el-col>
            </el-row>
            <el-row>
              <el-col :span="4" class="col_title">采购原因：</el-col>
              <el-col :span="4">{{ Detail.procurementCauses != null ? Detail.procurementCauses : '-' }}</el-col>
              <el-col :span="4" class="col_title">审核状态：</el-col>
              <el-col :span="4">
                <span v-if="Detail.state==0">待审核</span>
                <span v-else-if="Detail.state==1">审核通过</span>
                <span v-else>审核不通过</span>
              </el-col>
              <el-col :span="4" class="col_title"> 审核意见:</el-col>
              <el-col :span="4">
                  <span v-if="Detail.auditAdvice==null">
                待审核状态中
              </span>
                <span v-else> {{ Detail.auditAdvice }}</span>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="4" class="col_title">发起日期：</el-col>
              <el-col :span="4">{{ Detail.initDate != null ? parseTime2(Detail.initDate) : '-' }}</el-col>
              <el-col :span="4" class="col_title">录入人：</el-col>
              <el-col :span="4">
                {{ Detail.insertPerson != null ? Detail.insertPerson : '-' }}
              </el-col>
              <el-col :span="4" class="col_title"> 录入时间:</el-col>
              <el-col :span="4">
                {{ Detail.insertDate != null ? parseTime(Detail.insertDate) : '-' }}
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
              :data="DetailParts"
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
          <el-collapse-item title="零件清单" name="4">
            <el-table
              :data="DetailKits"
              style="width: 100%"
            >
              <el-table-column
                prop="kitName"
                label="零件名称"
                width="180"
              >
              </el-table-column>
              <el-table-column label="零件型号" align="center" prop="kitModel">
              </el-table-column>
              <el-table-column label="零件代码" align="center" prop="kitCode">
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
    <!--    审核弹窗-->
    <el-dialog :title="title" :visible.sync="openAudit" width="25%" @close="getList" class="spec-dialog" append-to-body>
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

.status-icon {
  width: 8px;
  height: 8px;
  border-radius: 1px;
  margin-right: 5px;
  position: relative;
  left: 32px;
  bottom: -15px;
}

.icon1 {
  background-color: #19be6b;
}

.icon0 {
  background-color: #eb194d;
}
</style>
<script>
import {
  addEquipmentRequire,
  delEquipmentRequire,
  listEquipmentRequire,
  updateEquipmentRequire,
  getAllUseWH,
  getEquipmentParam,
  getEquipmentKitAndPart,
  getKitAndPartBySelectiv
} from '@/api/towerparam/equipmentrequire'
import { treeselect } from '@/api/system/dept'
import { getToken } from '@/utils/auth'
import { checkRole } from '@/utils/permission'

export default {
  name: 'equipmentTyperequire',
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
        equipmentName: undefined,
        vender: undefined,
        equipmentType: undefined,
        rightsUnit: undefined,
        belongWarehouse: undefined,
        projectAddress: undefined,
        projectDate: undefined,
        initDate: undefined,
        state: undefined
      },
      openDetail: false,
      openAudit: false,
      Detail: {},
      //添加时选择的设备类别的设备参数
      EquipmentDetail: { id: '' },
      form: {
        //配件
        amEquipmentRequireKits: [],
        //部件
        amEquipmentRequireParts: []
      },
      //可用仓库
      warehouses: [],
      //设备类别
      applicableDeviceTypeS: [],
      // 所属部门ID字典
      depart_idOptions: [],
      activeNames: ['1'],
      //部件清单
      EquipmentPartsList: [],
      //配件清单
      EquipmentKitsList: [],
      equipmentId: -1,
      // table绑定key，更新key可以重新渲染table
      mainTableKey: 1,
      mainTableKey2: 1,
      //部件是否可编辑
      isEdit: false,
      //配件是否可编辑
      isEditKit: false,
      //计量单位
      measurement_unitS: [],
      //适用部件类型
      applicableKitTypeS: [],
      //详情部件
      DetailParts: [],
      //详情配件
      DetailKits: [],
      //详情页抽屉展示
      drawer: false,
      openUpdate: false,

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
        url: process.env.VUE_APP_BASE_API + "/asset/equipmentrequire/importData"
      },
      // 表单校验
      rules: {
        equipmentNumber: [
          { required: true, message: '设备编号不能为空', trigger: 'blur' }
        ],
        equipmentCount: [{
          type: 'number', message: '设备数量必须为数字值', trigger: 'blur'
        }]
      }
    }
  },
  created() {
    this.getList()
    this.getDicts('measurement_unit').then(response => {
      this.measurement_unitS = response.data
    })
    this.getDicts('parts_type').then(response => {
      this.applicableKitTypeS = response.data
    })
    this.getDicts('applicableDeviceType').then(response => {
      this.applicableDeviceTypeS = response.data
    })
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true
      listEquipmentRequire(this.queryParams).then(response => {
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail = false
      this.openAudit = false
      this.EquipmentKitsList = []
      this.EquipmentPartsList = [],
        this.reset()
    },
    //获取可用的仓库
    getUsedWH() {
      getAllUseWH().then(response => {
        this.warehouses = response.data
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
    // 表单重置
    reset() {
      this.form = {
        vender: undefined,
        equipmentNumber: undefined,
        equipmentName: undefined,
        standardModel: undefined,
        equipmentType: undefined,
        equipmentCount: undefined,
        kitProperties: undefined,
        rightsUnit: undefined,
        belongWarehouse: undefined,
        projectAddress: undefined,
        projectDate: undefined,
        initDate: undefined,
        demandSponsors: undefined,
        procurementCauses: undefined,
        state: undefined,
        auditAdvice: undefined
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
      this.queryParams.initDate = ''
      this.queryParams.projectDate = ''
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
      getEquipmentParam(row.standardModel, row.equipmentType).then(response => {
        this.EquipmentDetail = response.data
      })
      getEquipmentKitAndPart(row.id).then((res) => {
        this.EquipmentKitsList = res.data.amEquipmentRequireKits
        this.EquipmentPartsList = res.data.amEquipmentRequireParts
      })
      this.reset()
      this.form = JSON.parse(JSON.stringify(row))
      this.open = true
      this.title = '修改'
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs['form'].validate(valid => {
        this.form.amEquipmentRequireKits = this.EquipmentKitsList
        this.form.amEquipmentRequireParts = this.EquipmentPartsList
        if (valid) {
          if (this.form.id != undefined) {
            updateEquipmentRequire(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addEquipmentRequire(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
          this.EquipmentPartsList = []
          this.EquipmentKitsList = []
        }
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const postIds = row.id || this.ids
      this.$modal.confirm('是否确认删除？').then(function() {
        return delEquipmentRequire(postIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    //设备需求详情
    handleDetail(row) {
      this.getTreeselect()
      this.Detail = row
      getEquipmentKitAndPart(row.id).then((res) => {
        this.DetailKits = res.data.amEquipmentRequireKits
        this.DetailParts = res.data.amEquipmentRequireParts
      })
      if(row.standardModel==undefined&&row.standardModel==null&&row.equipmentType!=undefined&&row.equipmentType!=null) {
        getEquipmentParam(row.standardModel, row.equipmentType).then(response => {
          this.EquipmentDetail = response.data
        })
      }else
        this.EquipmentDetail=undefined
      this.drawer = true
      this.title = '详情'
    },
    handleAudit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.openAudit = true
      this.title = '审核'
    },
    submitAudit() {
      updateEquipmentRequire(this.form).then(response => {
        this.$modal.msgSuccess('修改成功')
        this.openAudit = false
        this.getList()
      })
    },
    filterState(value, row) {
      return row.state === value
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
        if (item.id !== row.id || item.pid != row.pid) {
          list.push(item)
        }
      })
      this.EquipmentPartsList = list
    },
    //删除配件
    delKitRow(row) {
      const list = []
      this.EquipmentKitsList.forEach(function(item) {
        if (item.id !== row.id || item.pid != row.pid) {
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
    // 新增零件
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
    //判断那些列可选
    selectInit(row, index) {
      var roles=["admin"]
      if(checkRole(roles))
        return true
      if (row.state!=0) {    //判断条件
        return false  //不可勾选
      } else {
        return true  //可勾选
      }
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/asset/equipmentrequire/export', {
        ...this.queryParams
      }, `equipmentrequire_${new Date().getTime()}.xlsx`)
    },

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "设备需求导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('/asset/equipmentrequire/importTemplate', {
      }, `equipmentrequire_template_${new Date().getTime()}.xlsx`)
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
    'form.equipmentType': {
      handler(newVal, oldVal) {
        if (newVal != null && newVal != oldVal && this.form.standardModel != undefined) {
          var type = this.form.equipmentType
          var model = this.form.standardModel
          getEquipmentParam(model, type).then(response => {
            this.EquipmentDetail = response.data
            getKitAndPartBySelectiv(this.EquipmentDetail.id).then(response => {
              this.EquipmentKitsList = response.data.amEquipmentRequireKits
              this.EquipmentPartsList = response.data.amEquipmentRequireParts
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
            getKitAndPartBySelectiv(this.EquipmentDetail.id).then(response => {
              this.EquipmentKitsList = response.data.amEquipmentRequireKits
              this.EquipmentPartsList = response.data.amEquipmentRequireParts
            })
          })
        }
      },
      immediate: true
    }
  }
}
</script>
