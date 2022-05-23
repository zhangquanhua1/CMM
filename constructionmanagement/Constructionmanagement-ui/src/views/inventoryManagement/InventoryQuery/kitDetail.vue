<template>
  <div style="margin-left: 10px">
    <div>
      <el-collapse v-if="this.Detail" v-model="activeNames">
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
            <el-col :span="4" class="col_title">适用部件类别：</el-col>
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
            <!--            <el-col :span="4" class="col_title">数量：</el-col>-->
            <!--            <el-col :span="4">{{ Detail.amount != null ? Detail.amount : '-' }}</el-col>-->
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
            <el-col :span="4">{{ Detail.productDate != null ? parseTime2(Detail.productDate) : '-' }}</el-col>
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
    </div>
  </div>
</template>

<script>
import { treeselect } from '@/api/system/dept'
import { listKitById } from '@/api/inventory/KitEntry'

export default {
  name: 'kitDetail',
  props: {
    id: {
      type: Number
    }
  },
  data() {
    return {
      //折叠面板
      activeNames: ['1'],
      Detail: undefined,
      // 所属部门ID字典
      depart_idOptions: []
    }
  },
  created() {
    this.getTreeselect()
    this.getKitInfo(this.id)
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
    getKitInfo(id) {
      if(id==null||id==undefined||id=='')
        return
      listKitById(id).then(response => {
        if (response.data == null) {
          console.log('参数为空')
          // this.$emit('success', false)
          // return this.$message('暂无更详细的内容')
        } else {
          this.Detail = response.data
        }
      })
    }
  }

}
</script>

<style scoped>

</style>
