<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-form-item label="来源设备编号">
          <el-input v-model="dataForm.machineId" placeholder="来源设备编号" clearable></el-input>
        </el-form-item>
        <el-form-item label="来源设备名称">
          <el-input v-model="dataForm.machineName" placeholder="来源设备名称" clearable></el-input>
        </el-form-item>
        <el-form-item label="设备通道">
          <el-select v-model="dataForm.channel" placeholder="请选择设备通道" clearable>
            <el-option
              v-for="item in channelList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备类型">
          <el-select v-model="dataForm.machineType" disabled clearable>
            <el-option
              v-for="item in machineTypeList"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="接收时间">
          <el-col :span="8">
            <el-date-picker type="date" placeholder="开始时间" v-model="dataForm.startTime"
                            style="width: 100%;"></el-date-picker>
          </el-col>
          <el-col :span="8" style="padding-left: 10px">
            <el-date-picker type="date" placeholder="结束时间" v-model="dataForm.endTime"
                            style="width: 100%;"></el-date-picker>
          </el-col>
        </el-form-item>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="machineId"
        header-align="center"
        align="center"
        label="来源设备编号">
      </el-table-column>
      <el-table-column
        prop="machineName"
        header-align="center"
        align="center"
        label="来源设备名称">
      </el-table-column>
      <el-table-column
        prop="conditionNumber"
        header-align="center"
        align="center"
        label="土壤温度">
      </el-table-column>
      <el-table-column
        prop="unit"
        header-align="center"
        align="center"
        label="单位">
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        label="记录时间">
      </el-table-column>
      <el-table-column
        prop="channel"
        header-align="center"
        align="center"
        label="来源通道">
      </el-table-column>
      <el-table-column
        prop="remark"
        header-align="center"
        align="center"
        label="备注信息">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './windspeedsensor-add-or-update'

  export default {
    data() {
      return {
        dataForm: {
          machineType: 'd'
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false,
        channelList: [{
          value: 'a',
          label: 'a'
        }, {
          value: 'b',
          label: 'b'
        }, {
          value: 'c',
          label: 'c'
        }, {
          value: 'd',
          label: 'd'
        }],
        machineTypeList: [
          {
            value: 'a',
            label: '土壤温度传感器'
          }, {
            value: 'b',
            label: '土壤湿度传感器'
          }, {
            value: 'c',
            label: '空气湿度传感器'
          }, {
            value: 'd',
            label: '风速传感器'
          }, {
            value: 'e',
            label: '光照传感器'
          }, {
            value: 'f',
            label: 'CO2浓度传感器'
          }
        ],
      }
    },
    components: {
      AddOrUpdate
    },
    activated() {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList() {
        this.dataListLoading = true;
        this.$http({
          url: this.$http.adornUrl('/manage/machinesensor/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'machineType': this.dataForm.machineType,
            'machineId': this.dataForm.machineId,
            'machineName': this.dataForm.machineName,
            'channel': this.dataForm.channel,
            'startTime': this.dataForm.startTime,
            'endTime': this.dataForm.endTime
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list;
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = [];
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle(val) {
        this.pageSize = val;
        this.pageIndex = 1;
        this.getDataList()
      },
      // 当前页
      currentChangeHandle(val) {
        this.pageIndex = val;
        this.getDataList()
      },
      // 多选
      selectionChangeHandle(val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle(id) {
        this.addOrUpdateVisible = true;
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle(id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        });
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/manage/machinesensor/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>
