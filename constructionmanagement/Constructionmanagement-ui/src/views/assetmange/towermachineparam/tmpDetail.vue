<template>
  <div style="margin-left: 10px">
    <el-collapse v-model="activeNames" >
      <el-collapse-item v-show="this.Detail" title="设备参数信息" name="1">
        <el-row>
          <el-col :span="4" class="col_title">设备名称：</el-col>
          <el-col :span="4">{{ Detail.towerMachineName != null ? Detail.towerMachineName : '-' }}</el-col>
          <el-col :span="4" class="col_title">设备型号：</el-col>
          <el-col :span="4">{{ Detail.towerMachineModel != null ? Detail.towerMachineModel : '-' }}</el-col>
          <el-col :span="4" class="col_title">生产厂家：</el-col>
          <el-col :span="4">{{ Detail.vender != null ? Detail.vender : '-' }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">设备类别：</el-col>
          <el-col :span="4">{{ Detail.towerMachineType != null ? Detail.towerMachineType : '-' }}</el-col>
          <el-col :span="4" class="col_title">额定起重力距：</el-col>
          <el-col :span="4">{{ Detail.ratedLiftingDistance != null ? Detail.ratedLiftingDistance : '-' }}</el-col>
          <el-col :span="4" class="col_title">最大起重量：</el-col>
          <el-col :span="4">{{ Detail.maximumLiftingWeight != null ? Detail.maximumLiftingWeight : '-' }} T</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">最大幅度额定起重量：</el-col>
          <el-col :span="4">{{ Detail.maximumRatedWeight != null ? Detail.maximumRatedWeight : '-' }}T</el-col>
          <el-col :span="4" class="col_title">最大起重量工作幅度：</el-col>
          <el-col :span="4">{{ Detail.maximumStartingWeightWorkingRange != null ? Detail.maximumStartingWeightWorkingRange : '-' }}</el-col>
          <el-col :span="4" class="col_title">最大工作幅度：</el-col>
          <el-col :span="4">{{ Detail.maximumRangeWork != null ? Detail.maximumRangeWork : '-' }}</el-col>
        </el-row>

        <el-row>
          <el-col :span="4" class="col_title">最大起升高度：</el-col>
          <el-col :span="4">{{ Detail.maximumHeightLift != null ? Detail.maximumHeightLift : '-' }}m</el-col>
          <el-col :span="4" class="col_title">最大幅度额定起重量：</el-col>
          <el-col :span="4">{{ Detail.maximumRatedWeight != null ? Detail.maximumRatedWeight : '-' }}T</el-col>
          <el-col :span="4" class="col_title">起升速度：</el-col>
          <el-col :span="4">{{ Detail.upDownSpeed != null ? Detail.upDownSpeed : '-' }}m/min</el-col>
        </el-row>

        <el-row>
          <el-col :span="4" class="col_title">变幅速度：</el-col>
          <el-col :span="4">{{ Detail.variableAmplitudeSpeed != null ? Detail.variableAmplitudeSpeed : '-' }}</el-col>
          <el-col :span="4" class="col_title">回转速度：</el-col>
          <el-col :span="4">{{ Detail.swivellingSpeed != null ? Detail.swivellingSpeed : '-' }}</el-col>
          <el-col :span="4" class="col_title">顶升速度：</el-col>
          <el-col :span="4">{{ Detail.upSpeed != null ? Detail.upSpeed : '-' }}</el-col>
        </el-row>

        <el-row>
          <el-col :span="4" class="col_title">塔机自重：</el-col>
          <el-col :span="4">{{ Detail.towerMachineWeight != null ? Detail.towerMachineWeight : '-' }}T</el-col>
          <el-col :span="4" class="col_title">平衡重量：</el-col>
          <el-col :span="4">{{ Detail.balanceWeight != null ? Detail.balanceWeight : '-' }}T</el-col>
          <el-col :span="4" class="col_title">工作电压：</el-col>
          <el-col :span="4">{{ Detail.workingVoltage != null ? Detail.workingVoltage : '-' }}V</el-col>
        </el-row>

        <el-row>
          <el-col :span="4" class="col_title">装机总容量：</el-col>
          <el-col :span="4">{{ Detail.totalInstalledCapacity != null ? Detail.totalInstalledCapacity : '-' }}</el-col>
          <el-col :span="4" class="col_title">电机功率：</el-col>
          <el-col :span="4">{{ Detail.motorPower != null ? Detail.motorPower : '-' }}KW</el-col>
          <el-col :span="4" class="col_title">变频器功率：</el-col>
          <el-col :span="4">{{ Detail.frequencyConverterPower != null ? Detail.frequencyConverterPower : '-' }}KW</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">标准节规格：</el-col>
          <el-col :span="4">{{ Detail.standardSectionSpecifications != null ? Detail.standardSectionSpecifications : '-' }}</el-col>
          <el-col :span="4" class="col_title">附墙框规格：</el-col>
          <el-col :span="4">{{ Detail.attachedWallFrameSpecifications != null ? Detail.attachedWallFrameSpecifications : '-' }}</el-col>
          <el-col :span="4" class="col_title">规格归类：</el-col>
          <el-col :span="4">{{ Detail.specificationClassification != null ? Detail.specificationClassification : '-' }}</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">备注：</el-col>
          <el-col :span="4">{{ Detail.remark != null ? Detail.remark : '-' }}</el-col>
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
      <el-collapse-item v-show="this.kits" title="零件信息" name="2">
        <el-table
          :data="kits"
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
      <el-collapse-item v-show="this.parts" title="部件信息" name="3">
        <el-table
          :data="parts"
          style="width: 100%"
        >
          <el-table-column
            prop="amPartParam.partName"
            label="部件名称"
            width="180"
          >
          </el-table-column>
          <el-table-column label="部件代码" align="center" prop="amPartParam.partCode">
          </el-table-column>
          <el-table-column label="部件型号" align="center" prop="amPartParam.partModel">
          </el-table-column>
          <el-table-column label="适用设备类型" align="center" prop="amPartParam.applicableDeviceType">
          </el-table-column>
          <el-table-column label="数量" align="center" prop="partCount">
          </el-table-column>
          <el-table-column label="计量单位" align="center" prop="amPartParam.measurementUnit">
          </el-table-column>
        </el-table>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import { getEquipmentParam } from '@/api/towerparam/equipmentrequire'
import {SelectById} from '@/api/inventory/EquipmentEntry'
import { treeselect } from '@/api/system/dept'
export default {
  name: 'tmpDetail',
  props: {
    Detail: {},
    kits: {
      type :Array,
      default:() =>[]
    },
    parts:{
      type :Array,
      default:() =>[]
    },
  },
  created() {
    this.getTreeselect()

  },
  data() {
    return {
      //折叠面板
      activeNames: ['1'],
      // 所属部门ID字典
      depart_idOptions: [],

    }
  },
  methods: {
    getTreeselect() {
      treeselect().then(response => {
        this.depart_idOptions = response.data
      })
    },
    //根据部门id 获取部门名
    getDeptName(id) {
      return this.getDeptNameByID(this.depart_idOptions, id)
    },


  }
}
</script>

<style scoped>

</style>
