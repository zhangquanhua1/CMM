<template>
  <div style="margin-left: 10px">
    <el-collapse v-model="activeNames" accordion>
      <el-collapse-item v-if="this.Detail" title="电梯参数信息" name="1">
        <el-row>
          <el-col :span="4" class="col_title">生产厂家：</el-col>
          <el-col :span="4">{{ Detail.vender != null ? Detail.vender : '-' }}</el-col>
          <el-col :span="4" class="col_title">底笼尺寸：</el-col>
          <el-col :span="4">{{ Detail.bottomCageSize != null ? Detail.bottomCageSize : '-' }}m</el-col>
          <el-col :span="4" class="col_title">吊笼内空尺寸：</el-col>
          <el-col :span="4">{{ Detail.cageSize != null ? Detail.cageSize : '-' }}m</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">吊笼重量：</el-col>
          <el-col :span="4">{{ Detail.cageWeight != null ? Detail.cageWeight : '-' }} kg</el-col>
          <el-col :span="4" class="col_title">标准节：</el-col>
          <el-col :span="4">{{ Detail.standardKnot != null ? Detail.standardKnot : '-' }} m</el-col>
          <el-col :span="4" class="col_title">标准节重量：</el-col>
          <el-col :span="4">{{ Detail.standardSectionWeight != null ? Detail.standardSectionWeight : '-' }} kg</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">最大高度：</el-col>
          <el-col :span="4">{{ Detail.maxHeight != null ? Detail.maxHeight : '-' }}米</el-col>
          <el-col :span="4" class="col_title">额定载荷：</el-col>
          <el-col :span="4">{{ Detail.specifiedLoad != null ? Detail.specifiedLoad : '-' }}kg</el-col>
          <el-col :span="4" class="col_title">额定乘员：</el-col>
          <el-col :span="4">{{ Detail.ratedOccupant != null ? Detail.ratedOccupant : '-' }} 人</el-col>
        </el-row>

        <el-row>
          <el-col :span="4" class="col_title">起升速度：</el-col>
          <el-col :span="4">{{ Detail.upDownSpeed != null ? Detail.upDownSpeed : '-' }}m/min</el-col>
          <el-col :span="4" class="col_title">总功率：</el-col>
          <el-col :span="4">{{ Detail.totalPower != null ? Detail.totalPower : '-' }} KW</el-col>
          <el-col :span="4" class="col_title">额定电压：</el-col>
          <el-col :span="4">{{ Detail.ratedVoltage != null ? Detail.ratedVoltage : '-' }}v</el-col>
        </el-row>

        <el-row>
          <el-col :span="4" class="col_title">额定电流：</el-col>
          <el-col :span="4">{{ Detail.ratedCurrent != null ? Detail.ratedCurrent : '-' }} A</el-col>
          <el-col :span="4" class="col_title">减速机速比：</el-col>
          <el-col :span="4">{{ Detail.reduceSpeedRatio != null ? Detail.reduceSpeedRatio : '-' }}</el-col>
          <el-col :span="4" class="col_title">限速器制动力矩：</el-col>
          <el-col :span="4">{{ Detail.speedLimiterBrakeTorque != null ? Detail.speedLimiterBrakeTorque : '-' }}KN</el-col>
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
          <el-col :span="4" class="col_title">制动速度：</el-col>
          <el-col :span="4">{{ Detail.retroSpeed != null ? Detail.retroSpeed : '-' }}m/min</el-col>
          <el-col :span="4" class="col_title">附着要求：</el-col>
          <el-col :span="4">{{ Detail.attachmentRequirements != null ? Detail.attachmentRequirements : '-' }}</el-col>
          <el-col :span="4" class="col_title">基础要求：</el-col>
          <el-col :span="4">{{ Detail.basicRequirements != null ? Detail.basicRequirements : '-' }}KW</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="col_title">供电要求：</el-col>
          <el-col :span="4">{{ Detail.supplyPowerRequirements != null ? Detail.supplyPowerRequirements : '-' }}</el-col>
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
      <el-collapse-item v-if="this.kits" title="配件信息" name="2">
        <el-table
          :data="kits"
          style="width: 100%"
        >
          <el-table-column
            prop="amKitParam.kitName"
            label="零配件名称"
            width="180"
          >
          </el-table-column>
          <el-table-column label="配件型号" align="center" prop="amKitParam.kitModel">
          </el-table-column>
          <el-table-column label="配件代码" align="center" prop="amKitParam.kitCode">
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
      <el-collapse-item v-if="this.parts" title="部件信息" name="3">
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
          <el-table-column label="数量" align="center" prop="kitCount">
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
  name: 'elepDetail',
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
