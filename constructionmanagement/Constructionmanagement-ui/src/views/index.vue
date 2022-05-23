<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="公告标题" prop="noticeTitle">
        <el-input
          v-model="queryParams.noticeTitle"
          placeholder="请输入公告标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布人员" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入发布人员"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="noticeType">
        <el-select v-model="queryParams.noticeType" placeholder="公告类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_notice_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="noticeList">
      <el-table-column
        label="公告标题"
        align="center"
        prop="noticeTitle"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="公告类型" align="center" prop="noticeType" width="130">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_notice_type" :value="scope.row.noticeType"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="130">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-more"
            @click="detail(scope.row)"
          >详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--    <div v-for="(item, index) in noticeList" >-->
    <!--      <div class="card">-->
    <!--        <el-row style="margin:15px;padding:5px">-->
    <!--          <el-col :span="22" style="text-align:center;font-family:'Microsoft YaHei';font-size:large">{{item.noticeTitle}}</el-col>-->
    <!--          <el-col :span="2" style="text-align:right;">-->
    <!--            <template >-->
    <!--              <dict-tag :options="dict.type.sys_notice_type" :value="item.noticeType"/>-->
    <!--            </template>-->
    <!--          </el-col>-->
    <!--        </el-row>-->
    <!--        <el-row style="margin:15px;padding:5px">-->
    <!--          <el-col :span="8" style="text-align:left;padding-left: 50px">{{ parseTime(item.createTime, '{y}-{m}-{d}') }}</el-col>-->
    <!--          <el-col :span="8" style="text-align: center"><el-button type="primary">详情</el-button></el-col>-->
    <!--          <el-col :span="8" style="text-align:right;padding-right: 50px">{{item.createBy}}</el-col>-->
    <!--        </el-row>-->
    <!--      </div>-->
    <!--    </div>-->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog title="详情" :visible.sync="openDetail" width="70%" append-to-body>
      <div class="gray-bg">
        <div class="wrapper wrapper-content  animated fadeInRight article">
          <div class="row">
            <div class="col-lg-10 col-lg-offset-1">
              <div class="ibox">
                <div class="ibox-content">
                  <div class="text-center article-title">
                    <h1>
                      {{this.DetailItem.noticeTitle}}
                    </h1>
                  </div>
                  <div v-html="this.DetailItem.noticeContent">
                    {{this.DetailItem.noticeContent}}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listNotice } from '@/api/system/notice'

export default {
  name: 'Index',
  dicts: ['sys_notice_status', 'sys_notice_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 公告表格数据
      noticeList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 4,
        noticeTitle: undefined,
        createBy: undefined,
        status: undefined
      },
      openDetail:false,
      DetailItem: {},

    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询公告列表 */
    getList() {
      this.loading = true
      listNotice(this.queryParams).then(response => {
        this.noticeList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    detail(item) {
     this.DetailItem = item;
     this.openDetail=true;
    },

    cancel(){
      this.openDetail=false;
    }
  }
}
</script>

<style scoped lang="scss">
.card {
  box-shadow: 0 2px 4px rgba(0, 0, 0, .2);
  padding: 2px;
  margin: 35px;
  margin-left: 200px;
  width: 1200px;
  height: 115px;

}

</style>

