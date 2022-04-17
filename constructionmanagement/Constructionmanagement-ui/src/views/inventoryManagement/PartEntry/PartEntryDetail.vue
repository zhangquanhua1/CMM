<template>
  <div style="margin-left: 10px">
    <el-collapse v-model="activeNames">
      <el-collapse-item v-if="this.Detail" title="部件基本信息" name="1">
        <el-row>
          <el-col :span="4" class="col_title">部件名称：</el-col>
          <el-col :span="4">{{ Detail.partName != null ? Detail.partName : '-' }}</el-col>
          <el-col :span="4" class="col_title">产品编号：</el-col>
          <el-col :span="4">{{ Detail.productNum != null ? Detail.productNum : '-' }}</el-col>
          <el-col :span="4" class="col_title">生产厂家：</el-col>
          <el-col :span="4">{{ Detail.vender != null ? Detail.vender : '-' }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">部件类别：</el-col>
          <el-col :span="4">{{ Detail.partType != null ? Detail.partType : '-' }}</el-col>
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
          <el-col :span="4">{{ Detail.brachium != null ? Detail.brachium : '-' }}米</el-col>
          <el-col :span="4" class="col_title">设备型号：</el-col>
          <el-col :span="4">{{ Detail.unitType != null ? Detail.unitType : '-' }}</el-col>
          <el-col :span="4" class="col_title">计量单位：</el-col>
          <el-col :span="4">{{ Detail.measurementUnit != null ? Detail.measurementUnit : '-' }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">标准节高度：</el-col>
          <el-col :span="4">{{ Detail.standardSectionHeight != null ? Detail.standardSectionHeight : '-' }}米</el-col>
          <el-col :span="4" class="col_title">部件型号：</el-col>
          <el-col :span="4">{{ Detail.partModel != null ? Detail.partModel : '-' }}</el-col>
          <el-col :span="4" class="col_title">部件代码：</el-col>
          <el-col :span="4">{{ Detail.partCode != null ? Detail.partCode : '-' }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">数量：</el-col>
          <el-col :span="4">{{ Detail.amount != null ? Detail.amount : '-' }}</el-col>
          <el-col :span="4" class="col_title">单价：</el-col>
          <el-col :span="4">{{ Detail.singlePrice != null ? Detail.singlePrice : '-' }}</el-col>
          <el-col :span="4" class="col_title">是否整机配件：</el-col>
          <el-col :span="4" v-if="Detail.wholeMachineAccessories>0">是</el-col>
          <el-col :span="4" v-else>否</el-col>
        </el-row>

        <el-row>
          <el-col :span="4" class="col_title">需求发起人：</el-col>
          <el-col :span="4">{{ Detail.demandSponsors != null ? Detail.demandSponsors : '-' }}</el-col>
          <el-col :span="4" class="col_title">采购原因：</el-col>
          <el-col :span="4">{{ Detail.procurementCauses != null ? Detail.procurementCauses : '-' }}</el-col>
        </el-row>


      </el-collapse-item>
      <el-collapse-item v-if="this.KitsList" title="零件清单" name="2">
        <el-table
          :data="KitsList"
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
</template>

<script>
import { listKitByPartId } from '@/api/inventory/PartEntry'

export default {
  name: 'PartEntryDetail',
  props: {
    Detail: {
      type: Object
    },
  },
  data() {
    return {
      //折叠面板
      activeNames: ['1'],
      KitsList:[],
    }
  },
  created() {
    this.getKitInfo()
  },
  methods: {
    getKitInfo(){
      listKitByPartId(this.Detail.id).then(response => {
        this.KitsList =response.data
      })
    }
  }
}
</script>

<style scoped>

</style>
