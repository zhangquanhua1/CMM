<template>
  <div style="margin-left: 10px">
    <el-collapse v-model="activeNames">
      <el-collapse-item v-if="this.Detail" title="部件基本信息" name="1">
        <el-row>
          <el-col :span="4" class="col_title">部件名称：</el-col>
          <el-col :span="4">{{ Detail.partName != null ? Detail.partName : '-' }}</el-col>
          <el-col :span="4" class="col_title">部件代码：</el-col>
          <el-col :span="4">{{ Detail.partCode != null ? Detail.partCode : '-' }}</el-col>
          <el-col :span="4" class="col_title">部件型号：</el-col>
          <el-col :span="4">{{ Detail.partModel != null ? Detail.partModel : '-' }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">计量单位：</el-col>
          <el-col :span="4">{{ Detail.measurementUnit != null ? Detail.measurementUnit : '-' }}</el-col>
          <el-col :span="4" class="col_title">适用设备类型：</el-col>
          <el-col :span="4">{{ Detail.applicableDeviceType != null ? Detail.applicableDeviceType : '-' }}</el-col>
          <el-col :span="4" class="col_title">备注：</el-col>
          <el-col :span="4">{{ Detail.remark != null ? Detail.remark : '-' }}</el-col>
        </el-row>
      </el-collapse-item>
      <el-collapse-item v-if="this.KitsList" title="零件信息" name="2">
        <el-table
          :data="KitsList"
          style="width: 100%"
        >
          <el-table-column
            prop="amKitParam.kitName"
            label="零件名称"
            width="180"
          >
          </el-table-column>
          <el-table-column label="零件型号" align="center" prop="amKitParam.kitModel">
          </el-table-column>
          <el-table-column label="零件代码" align="center" prop="amKitParam.kitCode">
          </el-table-column>
          <el-table-column label="技术参数" align="center" prop="amKitParam.technicalParam">
          </el-table-column>
          <el-table-column label="适用部件类型" align="center" prop="amKitParam.applicableKitType">
          </el-table-column>
          <el-table-column label="数量" align="center" prop="kitCount">
          </el-table-column>
          <el-table-column label="计量单位" align="center" prop="amKitParam.measurementUnit">
          </el-table-column>
        </el-table>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>


export default {
  name: 'partParamDetail',
  props: {
    Detail: {},
    KitsList:{
      type :Array,
      default:() =>[]
    },
  },
  data() {
    return {
      activeNames: ['1'],
    }
  },
  created() {
  },
  methods: {

  }
}
</script>

<style scoped>

</style>
