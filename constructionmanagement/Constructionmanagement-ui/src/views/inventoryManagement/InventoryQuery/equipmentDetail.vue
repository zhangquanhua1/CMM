<template>
  <div style="margin-left: 10px">
    <el-collapse v-model="activeNames" accordion>
      <el-collapse-item v-if="this.Detail" title="设备信息" name="1">
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
          <!--          <el-col :span="4" class="col_title">数量：</el-col>-->
          <!--          <el-col :span="4">{{ Detail.amount != null ? (Detail.amount) : '-' }}</el-col>-->
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
      <el-collapse-item v-if="this.EquipmentDetail" title="设备参数" name="2">
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
    </el-collapse>
  </div>
</template>

<script>
import { getEquipmentParam } from '@/api/towerparam/equipmentrequire'
import {SelectById} from '@/api/inventory/EquipmentEntry'
import { treeselect } from '@/api/system/dept'
export default {
  name: 'equipmentDetail',
  props: {
    id: {
      type: Number
    }
  },
  created() {
    this.getTreeselect()
    this.getEquipmentInfo(this.id)

  },
  data() {
    return {
      //折叠面板
      activeNames: ['1'],
      Detail: undefined,
      // 所属部门ID字典
      depart_idOptions: [],
      //设备参数
      EquipmentDetail: undefined
    }
  },
  methods: {
    getEquipmentInfo(id) {
       SelectById(id).then(response => {
         this.Detail = response.data
         this.getEquipmentDetail()
       })
    },
    getEquipmentDetail() {
      getEquipmentParam(this.Detail.standardModel, this.Detail.equipmentType).then(response => {
        this.EquipmentDetail = response.data
      })
    },
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
