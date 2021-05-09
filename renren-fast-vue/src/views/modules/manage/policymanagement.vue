<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-form-item>
          <el-form-item>
            <el-form-item label="设备编号">
              <el-input v-model="dataForm.machineId" placeholder="设备编号" clearable></el-input>
            </el-form-item>
            <el-form-item label="设备名称">
              <el-input v-model="dataForm.machineName" placeholder="设备名称" clearable></el-input>
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
              <el-select v-model="dataForm.machineType" placeholder="请选择设备通道" clearable>
                <el-option
                  v-for="item in machineTypeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-form-item>
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
        prop="machineid"
        header-align="center"
        align="center"
        label="控制器编号">
      </el-table-column>
      <el-table-column
        prop="machinename"
        header-align="center"
        align="center"
        label="控制器名称">
      </el-table-column>
      <el-table-column
        prop="machinetype"
        header-align="center"
        align="center"
        label="控制器类型">
      </el-table-column>
      <el-table-column
        prop="channel"
        header-align="center"
        align="center"
        label="控制器通道">
      </el-table-column>
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="numbermin"
        header-align="center"
        align="center"
        label="策略设定最小值">
      </el-table-column>
      <el-table-column
        prop="numbermax"
        header-align="center"
        align="center"
        label="策略设定最大值">
      </el-table-column>
      <el-table-column
        prop="unit"
        header-align="center"
        align="center"
        label="单位">
      </el-table-column>
      <el-table-column prop="enablestatus" header-align="center" align="center" label="策略启用状态">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.enablestatus"
            active-color="#13ce66"
            inactive-color="#ff4949"
            :active-value="1"
            :inactive-value="0"
            @change="updatepresetStatus(scope.row)"
          ></el-switch>
        </template>
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
  import AddOrUpdate from './policymanagement-add-or-update'

  export default {
    data() {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
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
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/manage/policymanagement/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'machineType': this.dataForm.machineType,
            'machineId': this.dataForm.machineId,
            'machineName': this.dataForm.machineName,
            'channel': this.dataForm.channel,
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle(val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle(val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle(val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle(id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle(id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.id
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/manage/policymanagement/delete'),
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
      },
      // 修改显示状态
      updatepresetStatus(data) {
        // console.log("最新信息", data);
        let {id, presetStatus} = data;
        //发送请求修改状态
        this.$http({
          url: this.$http.adornUrl("/manage/policymanagement/update/status"),
          method: "post",
          data: this.$http.adornData({id, presetStatus}, false)
        }).then(({data}) => {
          this.$message({
            type: "success",
            message: "状态更新成功"
          });
        }).catch(() => {
        });
      },
    }
  }
</script>
