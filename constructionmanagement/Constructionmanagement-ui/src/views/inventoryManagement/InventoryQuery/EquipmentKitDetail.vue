<template>
  <div style="margin-left: 10px">
    <el-collapse v-model="activeNames">
      <el-collapse-item v-if="this.Detail" title="零件基本信息" name="1">
      <el-row>
        <el-col :span="4" class="col_title">零件名称：</el-col>
        <el-col :span="4">{{ Detail.kitName != null ? Detail.kitName : '-' }}</el-col>
        <el-col :span="4" class="col_title">零件代码：</el-col>
        <el-col :span="4">{{ Detail.kitCode != null ? Detail.kitCode : '-' }}</el-col>
        <el-col :span="4" class="col_title">零件型号：</el-col>
        <el-col :span="4">{{ Detail.kitModel != null ? Detail.kitModel : '-' }}</el-col>
      </el-row>
      <el-row>
        <el-col :span="4" class="col_title">技术参数：</el-col>
        <el-col :span="4">{{ Detail.technicalParam != null ? Detail.technicalParam : '-' }}</el-col>
        <el-col :span="4" class="col_title">适用部件类别：</el-col>
        <el-col :span="4">{{ Detail.applicableKitType != null ? Detail.applicableKitType : '-' }}</el-col>
        <el-col :span="4" class="col_title">计量单位：</el-col>
        <el-col :span="4">{{ Detail.measurementUnit != null ? Detail.measurementUnit : '-' }}</el-col>
      </el-row>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import { SelectKitById } from '@/api/inventory/EquipmentEntry'

export default {
  name: 'EquipmentKitDetail',
  props: {
    id: {
      type: Number
    }
  },
  data() {
    return {
      Detail: undefined,
      activeNames: ['1'],
    }
  },
  created() {
    this.getKitInfo(this.id)
  },
  methods: {
    getKitInfo(id) {
      SelectKitById(id).then(response => {
        this.Detail = response.data
      })
    }
  }
}
</script>

<style scoped>

</style>
