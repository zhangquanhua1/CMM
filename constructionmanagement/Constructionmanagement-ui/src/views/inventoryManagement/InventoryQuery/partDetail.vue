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
      <div v-else style="margin-left: 50%">
        <el-row>
          <el-col :sm="12" :lg="6">
            <el-result icon="info" title="信息提示" >
              <template slot="extra">
                <h2 >暂无更多详细信息</h2>
              </template>
            </el-result>
          </el-col>
        </el-row>
      </div>
    </el-collapse>
  </div>
</template>

<script>

import { listPartById } from '@/api/inventory/PartEntry'

export default {
  name: 'partDetail',
  props: {
    id: {
      type: Number
    },
  },
  data() {
    return {
      Detail: undefined,
      activeNames: ['1'],
    }
  },
  created() {
    this.getPartInfo(this.id)
  },
  methods: {
    getPartInfo(id) {
      if(id==null||id==undefined||id=='')
        return
      listPartById(id).then(response => {
        this.Detail = response.data
      })
    }
  }
}
</script>

<style scoped>

</style>
