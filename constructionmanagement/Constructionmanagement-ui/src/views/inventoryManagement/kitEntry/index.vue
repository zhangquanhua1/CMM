<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
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
      <el-form-item label="零件名称"  prop="kitName">
        <el-input
          v-model="queryParams.kitName"
          placeholder="请输入零件名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="适用部件类别"  prop="kitType">
        <el-input
          v-model="queryParams.kitType"
          placeholder="请输入适用部件类别"
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
      <el-form-item label="零件型号" prop="kitModel">
        <el-input
          v-model="queryParams.kitModel"
          placeholder="请输入零件型号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="零件代码" prop="kitCode">
        <el-input
          v-model="queryParams.kitCode"
          placeholder="请输入零件代码"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['inventory:manage:kitentry:add']"
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
          v-hasPermi="['inventory:manage:kitentry:remove']"
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
          v-hasPermi="['inventory:manage:kitentry:import']"
        >导入</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['inventory:manage:kitentry:export']"
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
      <el-table-column label="零件名称" align="center" prop="kitName"/>
      <el-table-column label="产品编号" align="center" prop="productNum"/>
      <el-table-column label="生产厂家" align="center" prop="vender"/>
      <el-table-column label="适用部件类别" align="center" prop="kitType"/>
      <el-table-column label="零件型号" align="center" prop="kitModel"/>
      <el-table-column label="零件代码" align="center" prop="kitCode"/>
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['inventory:manage:kitentry:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['inventory:manage:kitentry:remove']"
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
    <el-dialog :title="title" :visible.sync="open" :close-on-click-modal="false" width="80%" class="spec-dialog"
               v-dialog-drag append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-collapse v-model="activeNames">
          <el-collapse-item title="零件基本信息" name="1">
            <el-row>
              <el-col :span="8">
                <el-form-item label="零件名称" prop="kitName">
                  <el-input v-model="form.kitName" placeholder="请输入零件名称"/>
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
                <el-form-item label="适用部件类别" prop="kitType">
                  <el-select v-model="form.kitType" placeholder="请选择部件类别">
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
            </el-row>

            <el-row>
              <el-col :span="8">
                <el-form-item label="产权单位" prop="rightsUnit">
                  <el-input v-model="form.rightsUnit" placeholder="请输入产权单位"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="零件属性" prop="kitProperties">
                  <el-input v-model="form.kitProperties" placeholder="请输入零件属性"/>
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
                  <el-input v-model.number="form.brachium" placeholder="请输入臂长,单位为：米"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="折旧率" prop="depreciationRate">
                  <el-input v-model.number="form.depreciationRate" placeholder="请输入折旧率(%)"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="设备型号" prop="unitType">
                  <!--          <el-input v-model="form.unitType" placeholder="请输入设备型号"/>-->
                  <el-autocomplete
                    class="inline-input"
                    v-model="form.unitType"
                    :fetch-suggestions="querySearchAsync"
                    placeholder="请输入内容"
                  ></el-autocomplete>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
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
              <el-col :span="8">
                <el-form-item label="标准节高度" prop="standardSectionHeight">
                  <el-input v-model.number="form.standardSectionHeight" placeholder="请输入标准节高度（米）"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="零件型号" prop="kitModel">
                  <el-input v-model="form.kitModel" placeholder="请输入零件型号"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="零件代码" prop="kitCode">
                  <el-input v-model="form.kitCode" placeholder="请输入零件代码"/>
                </el-form-item>
              </el-col>
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
            </el-row>
            <el-row>
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
              <!--              <el-col :span="8">-->
              <!--                <el-form-item label="防坠器" prop="protectorId">-->
              <!--                  <template>-->
              <!--                    <el-select v-model="form.protectorId" clearable placeholder="请选择">-->
              <!--                      <el-option-->
              <!--                        v-for="item in this.protectors"-->
              <!--                        :key="item.value"-->
              <!--                        :label="item.standard"-->
              <!--                        :value="item.id"-->
              <!--                      >-->
              <!--                      </el-option>-->
              <!--                    </el-select>-->
              <!--                  </template>-->
              <!--                </el-form-item>-->
              <!--              </el-col>-->
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
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="所属仓库" prop="warehouse">
                  <el-select v-model="form.warehouse" placeholder="请选择所属仓库">
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
            </el-row>
          </el-collapse-item>
          <el-collapse-item title="防坠器信息" name="2">
            <el-row>
              <el-col :span="8">
                <el-form-item label="防坠器编号" prop="protectorNum">
                  <el-input v-model="form.protectorNum" placeholder="请输入防坠器编号"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="防坠器规格" prop="standard">
                  <el-input v-model="form.standard" placeholder="请输入防坠器规格"/>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="防坠器型号" prop="model">
                  <el-input v-model="form.model" placeholder="请输入防坠器型号"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="出厂日期" prop="productDate">
                  <el-date-picker clearable size="small" style="width: 100%"
                                  v-model="form.productDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择防坠器出厂日期"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="检测日期" prop="checkDate">
                  <el-date-picker clearable size="small" style="width: 100%"
                                  v-model="form.checkDate"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择防坠器检测日期"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
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
                  <el-button type="cyan" icon="el-icon-upload" size="mini" @click="handleImport2">点击上传</el-button>
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
    <!--    详情弹窗-->
    <!--    <el-dialog :title="title" :visible.sync="openDetail" width="40%" class="spec-dialog" append-to-body>-->
    <el-drawer
      title="零件详情"
      size="60%"
      :visible.sync="openDetail"
      :with-header="true"
    >
      <div style="margin-left: 10px">
        <div>
          <el-collapse v-model="activeNames">
            <el-collapse-item title="零件基本信息" name="1">
              <el-row>
                <el-col :span="4" class="col_title">零件名称：</el-col>
                <el-col :span="4">{{ Detail.kitName != null ? Detail.kitName : '-' }}</el-col>
                <el-col :span="4" class="col_title">产品编号：</el-col>
                <el-col :span="4">{{ Detail.productNum != null ? Detail.productNum : '-' }}</el-col>
                <el-col :span="4" class="col_title">生产厂家：</el-col>
                <el-col :span="4">{{ Detail.vender != null ? Detail.vender : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">零件类别：</el-col>
                <el-col :span="4">{{ Detail.kitType != null ? Detail.kitType : '-' }}</el-col>
                <el-col :span="4" class="col_title">所属设备：</el-col>
                <el-col :span="4">{{ Detail.equipment != null ? Detail.equipment : '-' }}</el-col>
                <el-col :span="4" class="col_title">资产总计：</el-col>
                <el-col :span="4">{{ Detail.totalAssets != null ? Detail.totalAssets : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">产权单位：</el-col>
                <el-col :span="4">{{ Detail.rightsUnit != null ? Detail.rightsUnit : '-' }}</el-col>
                <el-col :span="4" class="col_title">零件属性：</el-col>
                <el-col :span="4">{{ Detail.kitProperties != null ? Detail.kitProperties : '-' }}</el-col>
                <el-col :span="4" class="col_title">零件规格：</el-col>
                <el-col :span="4">{{ Detail.kitStandard != null ? Detail.kitStandard : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">臂长：</el-col>
                <el-col :span="4">{{ Detail.brachium != null ? Detail.brachium : '-' }} 米</el-col>
                <el-col :span="4" class="col_title">折旧率：</el-col>
                <el-col :span="4">{{ Detail.depreciationRate != null ? Detail.depreciationRate : '-' }}%</el-col>
                <el-col :span="4" class="col_title">设备型号：</el-col>
                <el-col :span="4">{{ Detail.unitType != null ? Detail.unitType : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">计量单位：</el-col>
                <el-col :span="4">{{ Detail.measurementUnit != null ? Detail.measurementUnit : '-' }}</el-col>
                <el-col :span="4" class="col_title">标准节高度：</el-col>
                <el-col :span="4">{{ Detail.standardSectionHeight != null ? Detail.standardSectionHeight : '-' }}米
                </el-col>
                <el-col :span="4" class="col_title">零件型号：</el-col>
                <el-col :span="4">{{ Detail.kitModel != null ? Detail.kitModel : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">零件代码：</el-col>
                <el-col :span="4">{{ Detail.kitCode != null ? Detail.kitCode : '-' }}</el-col>
                <el-col :span="4" class="col_title">数量：</el-col>
                <el-col :span="4">{{ Detail.amount != null ? Detail.amount : '-' }}</el-col>
                <el-col :span="4" class="col_title">单价：</el-col>
                <el-col :span="4">{{ Detail.singlePrice != null ? Detail.singlePrice : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">所属仓库：</el-col>
                <el-col :span="4">{{ Detail.warehouse != null ? Detail.warehouse : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">是否整机配件：</el-col>
                <el-col :span="4"
                ><span v-if="Detail.wholeMachineAccessories>0">是</span>
                  <span v-else>否</span></el-col>
                <el-col :span="4" class="col_title">报废日期：</el-col>
                <el-col :span="4">{{ Detail.retireDate != null ? parseTime2(Detail.retireDate) : '-' }}</el-col>
                <el-col :span="4" class="col_title">采购日期：</el-col>
                <el-col :span="4">{{ Detail.purchaseDate != null ? parseTime2(Detail.purchaseDate) : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">录入人：</el-col>
                <el-col :span="4">{{ Detail.insertPerson != null ? Detail.insertPerson : '-' }}</el-col>
                <el-col :span="4" class="col_title">录入时间：</el-col>
                <el-col :span="4">{{ Detail.insertDate != null ? parseTime(Detail.insertDate) : '-' }}</el-col>
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
                <el-col :span="4">{{ Detail.updateDate != null ? parseTime(Detail.updateDate) : '-' }}</el-col>
              </el-row>
            </el-collapse-item>
            <el-collapse-item title="防坠器信息" name="2">
              <el-row>
                <el-col :span="4" class="col_title">防坠器编号：</el-col>
                <el-col :span="4">{{ Detail.protectorNum != null ? Detail.protectorNum : '-' }}</el-col>
                <el-col :span="4" class="col_title">防坠器规格：</el-col>
                <el-col :span="4">{{ Detail.standard != null ? Detail.standard : '-' }}
                </el-col>
                <el-col :span="4" class="col_title">防坠器型号：</el-col>
                <el-col :span="4">{{ Detail.model != null ? Detail.model : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">出厂日期：</el-col>
                <el-col :span="4">{{ Detail.productDate != null ?parseTime2(Detail.productDate) : '-' }}</el-col>
                <el-col :span="4" class="col_title">检测日期：</el-col>
                <el-col :span="4">{{ Detail.checkDate != null ? parseTime2(Detail.checkDate) : '-' }}</el-col>
              </el-row>
            </el-collapse-item>
            <el-collapse-item title="其他信息" name="3">
              <el-row>
                <el-col :span="4" class="col_title">供应商：</el-col>
                <el-col :span="4">{{ Detail.supplierId != null ? Detail.supplierId : '-' }}</el-col>
                <el-col :span="4" class="col_title">联系地址：</el-col>
                <el-col :span="4">{{ Detail.supplierAdd != null ? Detail.supplierAdd : '-' }}</el-col>
                <el-col :span="4" class="col_title">联系电话：</el-col>
                <el-col :span="4">{{ Detail.supplierTel != null ? Detail.supplierTel : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">经办人：</el-col>
                <el-col :span="4">{{ Detail.practiName != null ? Detail.practiName : '-' }}</el-col>
                <el-col :span="4" class="col_title">经办人电话：</el-col>
                <el-col :span="4">{{ Detail.mobile != null ? Detail.mobile : '-' }}</el-col>
                <el-col :span="4" class="col_title">录入批次号：</el-col>
                <el-col :span="4">{{ Detail.batchNumber != null ? Detail.batchNumber : '-' }}</el-col>
              </el-row>
              <el-row>
                <el-col :span="4" class="col_title">附件：</el-col>
                <el-col :span="4" v-if="Detail.enclosure!=null">
                  <el-button type="cyan" icon="el-icon-download" size="mini" @click="handleDownload(form.enclosure)">
                    点击下载
                  </el-button>
                </el-col>
                <el-col :span="4" v-else>
                    无附件
                </el-col>
              </el-row>
            </el-collapse-item>
          </el-collapse>
        </div>
      </div>
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

    <!--    附件导入对话框-->
    <el-dialog :title="upload2.title" :visible.sync="upload2.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        :headers="upload2.headers"
        :action="upload2.url"
        :disabled="upload2.isUploading"
        :on-progress="handleFileUploadProgress2"
        :on-success="handleFileSuccess2"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm2">确 定</el-button>
        <el-button @click="upload2.open = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<style lang="scss">
</style>
<script>
import {
  addKitEntry,
  delKitEntry,
  listKitEntry,
  updateKitEntry, getProtector
} from '@/api/inventory/KitEntry'
import { getEquipmentModel } from '@/api/towerparam/partrequire'
import { getAllUseWH } from '@/api/towerparam/equipmentrequire'
import { treeselect } from '@/api/system/dept'
import { getToken } from '@/utils/auth'
import { checkRole } from '@/utils/permission'

export default {
  name: 'kitEntry',
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
        vender: undefined,
        kitName: undefined,
        kitType: undefined,
        purchaseDate: undefined,
        retireDate: undefined,
        equipment: undefined,
        kitModel: undefined,
        kitCode: undefined,
        objectValueAllEmpty: false
      },
      //折叠面板
      activeNames: ['1'],
      openDetail: false,
      Detail: {},
      protectors: [],
      form: {},
      //所属设备
      applicableDeviceTypeS: [],
      //部件类别
      applicableKitTypeS: [],
      //计量单位
      measurement_unitS: [],
      //获取所选所属设备下的设备型号
      getform: {
        towerMachineType: undefined
      },
      // 设备型号集合
      equipmentModelS: [],
      //可用仓库
      warehouses: [],
      // 所属部门ID字典
      depart_idOptions: [],

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
        url: process.env.VUE_APP_BASE_API + "/inventory/kitEntry/importData"
      },
      // 附件导入参数
      upload2: {
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
        url: process.env.VUE_APP_BASE_API + "/common/upload"
      },

      // 表单校验
      rules: {
        productNum: [
          { required: true, message: '产品编号不能为空', trigger: 'blur' }, {
            type: 'number', message: '产品编号必须为数字值', trigger: 'blur'
          }
        ],
        totalAssets: [
          { type: 'number', message: '资产总计必须为数字值', trigger: 'blur' }
        ],
        brachium: [
          { type: 'number', message: '臂长必须为数字值', trigger: 'blur' }, {
            pattern: /^(0|[1-9]\d?|10000)$/,
            message: '臂长范围在0-100',
            trigger: 'blur'
          }
        ],
        standardSectionHeight: [
          { type: 'number', message: '标准节高度必须为数字值', trigger: 'blur' }, {
            pattern: /^(0|[1-9]\d?|100)$/,
            message: '标准节高度范围在0-10000',
            trigger: 'blur'
          }
        ],
        depreciationRate: [
          { type: 'number', message: '折旧率必须为数字值', trigger: 'blur' }, {
            pattern: /^(0|[1-9]\d?|100)$/,
            message: '折旧率范围在0-100',
            trigger: 'blur'
          }
        ],
        amount: [
          { type: 'number', message: '数量必须为数字值', trigger: 'blur' }, {
            pattern: /^(0|[1-9]\d?|100)$/,
            message: '数量范围在0-1000000',
            trigger: 'blur'
          }
        ],
        singlePrice: [
          { type: 'number', message: '单价必须为数字值', trigger: 'blur' }, {
            pattern: /^(0|[1-9]\d?|100)$/,
            message: '单价范围在0-1000000',
            trigger: 'blur'
          }
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
      listKitEntry(this.queryParams).then(response => {
        this.queryParams.objectValueAllEmpty = false
        this.postList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.openDetail = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        kitName: undefined,
        productNum: undefined,
        vender: undefined,
        kitType: undefined,
        equipment: undefined,
        totalAssets: undefined,
        rightsUnit: undefined,
        kitProperties: undefined,
        kitStandard: undefined,
        brachium: undefined,
        depreciationRate: undefined,
        unitType: undefined,
        measurementUnit: undefined,
        standardSectionHeight: undefined,
        kitModel: undefined,
        kitCode: undefined,
        amount: undefined,
        singlePrice: undefined,
        wholeMachineAccessories: undefined,
        protectorId: undefined,
        purchaseDate: undefined,
        retireDate: undefined,
        warehouse: undefined,
        whetherStorage: undefined,
        protectorNum: undefined,
        standard: undefined,
        model: undefined,
        productDate: undefined,
        checkDate: undefined,
        batchNumber: undefined,
        supplierId: undefined,
        practiName: undefined,
        supplierAdd: undefined,
        supplierTel: undefined,
        mobile: undefined,
        enclosure: undefined
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
      this.queryParams.retireDate = '',
        this.queryParams.purchaseDate = '',
        this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    //获取所选所属设备的设备型号
    getModels() {
      this.getform.towerMachineType = this.form.equipment
      getEquipmentModel(this.getform).then(response => {
        this.equipmentModelS = response.rows
      })
    },
    //设备型号建议
    querySearchAsync(queryString, cb) {
      this.getModels()
      var oj = { value: '' }
      var results = []
      this.equipmentModelS.forEach(function(item) {
        oj.value = item.towerMachineModel
        results.push(oj)
      })
      cb(results)
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
        this.warehouses = response.data
      })

      this.getDicts('measurement_unit').then(response => {
        this.measurement_unitS = response.data
      })
      this.getDicts('applicableDeviceType').then(response => {
        this.applicableDeviceTypeS = response.data
      })
      this.getDicts('parts_type').then(response => {
        this.applicableKitTypeS = response.data
      })
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getDics()
      getProtector().then((res) => {
        this.protectors = res.data
        console.log('this.protectors' + res.data)
      })
      this.reset()
      this.open = true
      this.title = '添加'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.getDics()
      getProtector().then((res) => {
        this.protectors = res.data
        console.log('this.protectors' + res.data)
      })
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
            updateKitEntry(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addKitEntry(this.form).then(response => {
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
        return delKitEntry(postIds)
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
    filterState(value,row){
      return row.status === value;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/inventory/kitEntry/export', {
        ...this.queryParams
      }, `kitEntry_${new Date().getTime()}.xlsx`)
    },

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "零件录入导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('/inventory/kitEntry/importTemplate', {
      }, `kitEntry_template_${new Date().getTime()}.xlsx`)
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

    //上传附件
    handleImport2() {
      this.upload2.title = '上传附件'
      this.upload2.open = true
    },
    //导出附件
    handleDownload(url) {
      window.open(process.env.VUE_APP_BASE_API+url)
    },

    // 提交上传文件
    submitFileForm2() {
      this.$refs.upload.submit();
    },

    // 文件上传中处理
    handleFileUploadProgress2(event, file, fileList) {
      this.upload2.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess2(response, file, fileList) {
      this.upload2.open = false;
      this.upload2.isUploading = false;
      this.$refs.upload.clearFiles();
      console.log(response)
      if(200===response.code){
        this.form.enclosure=response.fileName
        this.$alert('附件上传成功', "上传结果", {dangerouslyUseHTMLString: true});
      }else {
        this.$alert('附件上传失败', "上传结果", {dangerouslyUseHTMLString: true});
      }
      // this.getList();
    },

  }
}
</script>
