<template>
  <div id="button" style="width:50%;margin-left: 20%;">
    <div>
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
    </div>
    <div>
      <div style="margin-left: 30%"> <h2>{{title}}</h2><br/></div>
      <div id="container"/>
      <br/> <br/> <br/>
      <div id="container2"/>
    </div>
  </div>

</template>
<script>
import { Column, G2, Pie, measureTextWidth, Bar } from '@antv/g2plot'

export default {
  name: 'stockChart',
  props: {
    equipmentTotal: '',
    partTotal: '',
    kitTotal: '',
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
    this.draw()
  },
  data() {
    return {
      data: [
        {
          type: '设备库存总数量',
          sales: this.equipmentTotal
        },
        {
          type: '部件库存总数量',
          sales: this.partTotal
        },
        {
          type: '零件库存总数量',
          sales: this.kitTotal
        }
      ],
      columnPlot: '',
      //饼形图数据
      partList2: this.partList,
      epipmentList2: this.epipmentList,
      kitList2: this.kitList,
      pieChart: null,
      name: '',
      statistic: {
        title: {
          offsetY: -4,
          customHtml: (container, view, datum) => {
            const { width, height } = container.getBoundingClientRect()
            const d = Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2))
            const text = datum ? datum.model : this.name
            return this.renderStatistic(d, text, { fontSize: 28 })
          }
        },
        content: {
          offsetY: 4,
          style: {
            fontSize: '32px'
          },
          customHtml: (container, view, datum, data) => {
            const { width } = container.getBoundingClientRect()
            const text = datum ? `${datum.amount}` : `${data.reduce((r, d) => r + d.amount, 0)}`
            return this.renderStatistic(width, text, { fontSize: 32 })
          }
        }
      },

      isNotType: true
    }
  },
  methods: {
    draw() {
      let data = this.data
      const { registerTheme } = G2
      const columnPlot = new Column(document.getElementById('container'), {
        title: {
          visible: true,
          text: '图表标题及描述'

        },
        padding: 'auto',
        data,
        xField: 'type',
        yField: 'sales',
        label: {
          // 可手动配置 label 数据标签位置
          position: 'middle', // 'top', 'bottom', 'middle',
          // 配置样式
          style: {
            fill: '#FFFFFF',
            opacity: 1,
            fontSize: 15,
            fontFamily: 'Arial'
          }
        },
        xAxis: {
          label: {
            autoHide: true,
            autoRotate: false,
            style: {
              fontSize: 17,
              fill: 'rgba(0, 0, 0, 1)'
            }
          }
        },
        meta: {
          type: {
            alias: '类别'
          },
          sales: {
            alias: '数量'
          }
        },
        minColumnWidth: 50,
        maxColumnWidth: 50
      })
      columnPlot.on('element:click', (ev) => {
        const { type, view, gEvent, data, x, y, clientX, clientY } = ev
        if (this.pieChart != null) {
          if (data.data.type == '部件库存总数量') {
            this.name = '部件总数量'
            this.partList2 = this.arrayMerge(this.partList2)
            this.pieChart.update({
              statistic: this.statistic
            })
            this.pieChart.changeData(this.partList2)
          } else if (data.data.type == '零件库存总数量') {
            this.name = '零件总数量'
            this.kitList2 = this.arrayMerge(this.kitList2)
            this.pieChart.update({
              statistic: this.statistic
            })
            this.pieChart.changeData(this.kitList2)
          } else {
            this.name = '设备总数量'
            this.epipmentList2 = this.arrayMerge(this.epipmentList2)
            this.pieChart.update({
              statistic: this.statistic
            })
            this.pieChart.changeData(this.epipmentList2)
          }
        } else {
          if (data.data.type == '部件库存总数量') {
            this.name = '部件总数量'
            this.partList2 = this.arrayMerge(this.partList2)
            this.showPie('部件总数量', this.partList2)
          } else if (data.data.type == '零件库存总数量') {
            this.name = '零件总数量'
            this.kitList2 = this.arrayMerge(this.kitList2)
            this.showPie('零件总数量', this.kitList2)
          } else {
            this.name = '设备总数量'
            this.epipmentList2 = this.arrayMerge(this.epipmentList2)
            this.showPie('设备总数量', this.epipmentList2)
          }
        }
      })
      this.columnPlot = columnPlot
      columnPlot.render()
    },

    showPie(name, list) {
      var data = list
      const piePlot = new Pie('container2', {
        appendPadding: 20,
        data,
        angleField: 'amount',
        colorField: 'model',
        radius: 1,
        innerRadius: 0.6,
        label: {
          type: 'inner',
          offset: '-50%',
          content: '{name}',
          style: {
            textAlign: 'center',
            fontSize: 14
          }
        },
        // title: {
        //   visible: true,
        //   text: '图表标题及描述',
        // },
        meta: {
          name: {
            alias: '名称'
          },
          model: {
            alias: '型号'
          },
          amount: {
            alias: '数量'
          },
          belongWarehouse: {
            alias: '所属仓库'
          }
        },
        statistic: this.statistic,
        // 添加 中心统计文本 交互
        interactions: [{ type: 'element-selected' }, { type: 'element-active' }, { type: 'pie-statistic-active' }, {
          type: 'tooltip',
          cfg: { start: [{ trigger: 'element:click', action: 'tooltip:show' }] }
        }],
        tooltip: {
          fields: ['name', 'model', 'amount', 'belongWarehouse']
        }

      })
      this.pieChart = piePlot
      piePlot.render()
    },
    //图形转表格
    tranferTable() {
      this.$emit('success', true)
    },
    //合并相同名称、型号、仓库的数据
    arrayMerge(n) {
      var newArr = []
      n = JSON.parse(JSON.stringify(n))
      n.forEach((e) => {
        var index = newArr.findIndex(function(item) {
          return item.name === e.name && item.belongWarehouse === e.belongWarehouse && item.model === e.model
        })
        if (index !== -1) {
          newArr[index].amount += e.amount
        } else {
          newArr.push(e)
        }
      })
      return newArr
    },
    renderStatistic(containerWidth, text, style) {
      const { width: textWidth, height: textHeight } = measureTextWidth(text, style)
      const R = containerWidth / 2
      // r^2 = (w / 2)^2 + (h - offsetY)^2
      let scale = 1
      if (containerWidth < textWidth) {
        scale = Math.min(Math.sqrt(Math.abs(Math.pow(R, 2) / (Math.pow(textWidth / 2, 2) + Math.pow(textHeight, 2)))), 1)
      }
      const textStyleStr = `width:${containerWidth}px;`
      return `<div style="${textStyleStr};font-size:${scale}em;line-height:${scale < 1 ? 1 : 'inherit'};">${text}</div>`
    },


  },
  watch: {
    equipmentTotal(n, o) {
      if (n != o) {
        this.data[0].sales = n
        this.columnPlot.changeData(this.data)
      }
    },
    partTotal(n, o) {
      if (n != o) {
        this.data[1].sales = n
        this.columnPlot.changeData(this.data)
      }
    },
    kitTotal(n, o) {
      if (n != o) {
        this.data[2].sales = n
        this.columnPlot.changeData(this.data)
      }
    },
    partList: {
      handler(n, o) {
        this.partList2 = n
      }
    },
    epipmentList: {
      handler(n, o) {
        this.epipmentList2 = n
      }
    },
    kitList: {
      handler(n, o) {
        this.kitList2 = n
      }
    }
  }
}
</script>

<style scoped>

</style>
