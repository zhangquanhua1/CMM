<template>
  <div>
    <div v-if="!isStockChart">
      <div id="button" style="width:50%;margin-left: 20%;">
        <el-row :gutter="30" class="mb8">
          <el-col :span="2.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-s-data"
              size="mini"
              @click="tranferTable"
            >表格显示
            </el-button>
          </el-col>
        </el-row>
        <br/>
        <div style="margin-left: 30%"> <h2>{{title}}</h2><br/></div>
        <div id="container5"/>
      </div>
    </div>
    <stockChart v-else :kit-list="kitList3" :part-list="partList3" :epipment-list="epipmentList3"
                :equipmentTotal="equipmentTotal"
                :part-total="partTotal" :kit-total="kitTotal" v-on:success="updateStock()" :title="title"
    />
    <!--    <div>{{title}}</div>-->
  </div>
</template>

<script>
import { Bar } from '@antv/g2plot'
import stockChart from './stockChart'

export default {
  name: 'stockTypeChart',
  components: {
    stockChart
  },
  props: {
    epipmentList: {
      type: Array,
      default: () => []
    },
    partList: {
      type: Array,
      default: () => []
    },
    kitList: {
      type: Array,
      default: () => []
    },
    title:''
  },
  mounted() {
    this.tranferType()
  },
  data() {
    return {
      //数据
      partList2: this.partList,
      epipmentList2: this.epipmentList,
      kitList2: this.kitList,
      stackedBarPlot: '',
      //点击某一类的仓库数据
      partList3: [],
      kitList3: [],
      epipmentList3: [],
      //是否显示
      isStockChart: false,
      //数量
      equipmentTotal: '',
      partTotal: '',
      kitTotal: '',

      belongWarehouseName: '',
     // title:''
    }
  },
  methods: {
    //相同仓库的进行合并
    arrayMergeByTye(n) {
      var newArr = []
      n = JSON.parse(JSON.stringify(n))
      n.forEach((e) => {
        var index = newArr.findIndex(function(item) {
          return item.belongWarehouse === e.belongWarehouse
        })
        if (index !== -1) {
          newArr[index].amount += e.amount
        } else {
          newArr.push(e)
        }
      })
      return newArr
    },
    getData() {
      var kits = this.arrayMergeByTye(this.kitList2)
      var parts = this.arrayMergeByTye(this.partList2)
      var equipments = this.arrayMergeByTye(this.epipmentList2)
      var data = []
      for (let i in kits) {
        data.push(kits[i])
      }
      for (let i in parts) {
        data.push(parts[i])
      }
      for (let i in equipments) {
        data.push(equipments[i])
      }
      return data
    },
    //以仓库分类显示
    tranferType() {
      var data = this.getData()
      const stackedBarPlot = new Bar('container5', {
        data,
        isGroup: true,
        xField: 'amount',
        yField: 'belongWarehouse',
        /** 自定义颜色 */
        // color: ['#1383ab', '#c52125'],
        seriesField: 'type',
        marginRatio: 0,
        label: {
          // 可手动配置 label 数据标签位置
          position: 'middle', // 'left', 'middle', 'right'
          // 可配置附加的布局方法
          layout: [
            // 柱形图数据标签位置自动调整
            { type: 'interval-adjust-position' },
            // 数据标签防遮挡
            { type: 'interval-hide-overlap' },
            // // 数据标签文颜色自动调整
            { type: 'adjust-color' }
          ]
        },
        tooltip: {
          fields: ['type', 'amount'],
          formatter: (datum) => {
            if (datum.type == 1) {
              return { name: '设备', value: datum.amount }
            } else if (datum.type == 2) {
              return { name: '零件', value: datum.amount }
            } else {
              return { name: '部件', value: datum.amount }
            }
          }
        },
        legend: {
          layout: 'vertical',
          position: 'right'
        }
      })
      this.stackedBarPlot = stackedBarPlot
      stackedBarPlot.on('element:click', (ev) => {
        const { type, view, gEvent, data, x, y, clientX, clientY } = ev
        //console.log('ev data: ' + data.data.belongWarehouse)
        this.belongWarehouseName = data.data.belongWarehouse
        this.getStockChartData(data.data.belongWarehouse)
        this.title=this.belongWarehouseName+"设备、部件、零件数量"
        this.isStockChart = true
      })
      stackedBarPlot.render()

    },
    tranferTable() {
      this.$emit('success', true)
    },
    //获取stockChart数据
    getStockChartData(name) {
      this.equipmentTotal = 0
      this.partTotal = 0
      this.kitTotal = 0
      this.partList3 = []
      this.epipmentList3 = []
      this.kitList3 = []
      this.partList2.forEach((e) => {
        if (e.belongWarehouse == name) {
          this.partTotal += e.amount
          this.partList3.push(e)
        }
      })
      this.kitList2.forEach((e) => {
        if (e.belongWarehouse == name) {
          this.kitTotal += e.amount
          this.kitList3.push(e)
        }
      })
      this.epipmentList2.forEach((e) => {
        if (e.belongWarehouse == name) {
          this.equipmentTotal += e.amount
          this.epipmentList3.push(e)
        }
      })
    },
    //表格显示
    updateStock(res) {
      this.isStockChart = false
      this.tranferTable()
      var data = this.getData()
      console.log('显示表格吗????' + this.stackedBarPlot)

    }
  },
  watch: {
    partList: {
      handler(n, o) {
        this.partList2 = n
        var data = this.getData()
        this.stackedBarPlot.changeData(data)
        this.getStockChartData(this.belongWarehouseName)
      }
    },
    epipmentList: {
      handler(n, o) {
        this.epipmentList2 = n
        var data = this.getData()
        this.stackedBarPlot.changeData(data)
        this.getStockChartData(this.belongWarehouseName)
      }
    },
    kitList: {
      handler(n, o) {
        this.kitList2 = n
        var data = this.getData()
        this.stackedBarPlot.changeData(data)
        this.getStockChartData(this.belongWarehouseName)
      }
    }
  }
}
</script>

<style scoped>

</style>
