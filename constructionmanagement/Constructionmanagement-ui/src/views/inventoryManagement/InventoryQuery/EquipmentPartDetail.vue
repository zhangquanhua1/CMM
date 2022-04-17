<template>
  <div style="margin-left: 10px">
    <el-collapse v-model="activeNames">
      <el-collapse-item v-if="this.Detail" title="部件基本信息" name="1">
        <el-row>
          <el-col :span="4" class="col_title">部件名称：</el-col>
          <el-col :span="4">{{ Detail.partName != null ? Detail.partName : '-' }}</el-col>
          <el-col :span="4" class="col_title">部件类型：</el-col>
          <el-col :span="4">{{ Detail.partType != null ? Detail.partType : '-' }}</el-col>
          <el-col :span="4" class="col_title">部件代码：</el-col>
          <el-col :span="4">{{ Detail.partCode != null ? Detail.partCode : '-' }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">部件型号：</el-col>
          <el-col :span="4">{{ Detail.partModel != null ? Detail.partModel : '-' }}</el-col>
          <el-col :span="4" class="col_title">适用设备类型：</el-col>
          <el-col :span="4">{{ Detail.applicableDeviceType != null ? Detail.applicableDeviceType : '-' }}</el-col>
          <el-col :span="4" class="col_title">计量单位：</el-col>
          <el-col :span="4">{{ Detail.measurementUnit != null ? Detail.measurementUnit : '-' }}</el-col>
        </el-row>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import { SelectPartById } from '@/api/inventory/EquipmentEntry'

export default {
  name: 'EquipmentPartDetail',
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
    this.getPartInfo(this.id)
  },
  methods: {
    getPartInfo(id) {
      SelectPartById(id).then(response => {
        this.Detail = response.data
      })
    }
  }
}
</script>

<style scoped>

</style>
