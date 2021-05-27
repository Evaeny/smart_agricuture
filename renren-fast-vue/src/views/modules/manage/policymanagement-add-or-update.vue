<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="140px">
      <el-form-item label="控制器设备编号" prop="machineId">
        <el-select v-model="dataForm.machineId" @change="changeMachineId(dataForm.machineId)" :disabled="dataForm.id"
                   placeholder="请选择控制器设备编号" clearable>
          <el-option
            v-for="item in controllerList"
            :key="item.machineId"
            :label="item.machineId"
            :value="item.machineId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="控制器设备名称" prop="machineName">
        <el-input v-model="dataForm.machineName" disabled="true" placeholder="设备名称"></el-input>
      </el-form-item>
      <el-form-item label="策略设定最小值" prop="numberMin">
        <el-input v-model="dataForm.numberMin" placeholder="策略设定最小值"></el-input>
      </el-form-item>
      <el-form-item label="策略设定最大值" prop="numberMax">
        <el-input v-model="dataForm.numberMax" placeholder="策略设定最大值"></el-input>
      </el-form-item>
      <el-form-item label="设备通道" prop="channel">
        <el-select v-model="dataForm.channel" disabled="true" placeholder="请选择设备通道" clearable>
          <el-option
            v-for="item in channelList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="设备类型" prop="machineType">
        <el-select v-model="dataForm.machineType" disabled="true" placeholder="请选择设备通道" clearable>
          <el-option
            v-for="item in machineTypeList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="关联传感器编号" prop="betweenSensorId">
        <el-select v-model="dataForm.betweenSensorId" :disabled="dataForm.id" placeholder="请选择设备通道" clearable>
          <el-option
            v-for="item in dataList"
            :key="item.machineId"
            :label="item.machineId"
            :value="item.machineId">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="设定启用状态" prop="enableStatus">
        <el-switch
          v-model="dataForm.enableStatus"
          active-color="#13ce66"
          inactive-color="#ff4949"
          active-value="1"
          inactive-value="0"
        ></el-switch>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        visible: false,
        dataForm: {
          enableStatus: '',
          unit: '',
          numberMin: '',
          numberMax: '',
          channel: '',
          machineType: '',
          machineName: '',
          machineId: '',
          betweenSensorId: '',
          id: 0,
        },
        dataRule: {
          enableStatus: [
            {required: true, message: '启用状态（默认为不启用）不能为空', trigger: 'blur'}
          ],
          unit: [
            {required: true, message: '单位不能为空', trigger: 'blur'}
          ],
          numberMin: [
            {required: true, message: '策略设定最小值不能为空', trigger: 'blur'}
          ],
          numberMax: [
            {required: true, message: '策略设定最大值不能为空', trigger: 'blur'}
          ],
          channel: [
            {required: true, message: '设备通道不能为空', trigger: 'blur'}
          ],
          machineType: [
            {required: true, message: '设备类型不能为空', trigger: 'blur'}
          ],
          machineName: [
            {required: true, message: '设备名称不能为空', trigger: 'blur'}
          ],
          machineId: [
            {required: true, message: '设备编号不能为空', trigger: 'blur'}
          ],
          betweenSensorId: [
            {required: true, message: '设备编号不能为空', trigger: 'blur'}
          ],
        },
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
          },
          {
            value: 'g',
            label: '土壤温度控制器'
          }, {
            value: 'h',
            label: '土壤加湿器'
          }, {
            value: 'i',
            label: '空气加湿器'
          }, {
            value: 'j',
            label: '挡风板'
          }, {
            value: 'k',
            label: '遮光板-补光灯'
          }, {
            value: 'l',
            label: 'CO2浓度控制器'
          }
        ],
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
        dataList: [],
        controllerList: []
      }
    },
    methods: {
      init(id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields();
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/manage/policymanagement/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.enableStatus = data.policyManagement.enableStatus
                this.dataForm.unit = data.policyManagement.unit
                this.dataForm.numberMin = data.policyManagement.numberMin
                this.dataForm.numberMax = data.policyManagement.numberMax
                this.dataForm.channel = data.policyManagement.channel
                this.dataForm.machineType = data.policyManagement.machineType
                this.dataForm.machineName = data.policyManagement.machineName
                this.dataForm.machineId = data.policyManagement.machineId
                this.dataForm.betweenSensorId = data.policyManagement.betweenSensorId
              }
            })
          }
        })

        this.$http({
          url: this.$http.adornUrl('/manage/machineinfo/queryAll'),
          method: 'get',
          params: this.$http.adornParams({
            machineSensor: 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page
          } else {
            this.dataList = []
          }
        });

        this.$http({
          url: this.$http.adornUrl('/manage/machinecontroller/queryAll'),
          method: 'get',
          params: this.$http.adornParams({
            machineSensor: 0
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.controllerList = data.page
          } else {
            this.controllerList = []
          }
        })
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/manage/policymanagement/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'enableStatus': this.dataForm.enableStatus,
                'unit': this.dataForm.unit,
                'numberMin': this.dataForm.numberMin,
                'numberMax': this.dataForm.numberMax,
                'channel': this.dataForm.channel,
                'machineType': this.dataForm.machineType,
                'machineName': this.dataForm.machineName,
                'machineId': this.dataForm.machineId,
                'betweenSensorId': this.dataForm.betweenSensorId,
                'id': this.dataForm.id || undefined,
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      },
      changeMachineId(machineId) {
        this.controllerList.forEach((item) => {
          if (item.machineId === machineId) {
            this.dataForm.machineName = item.machineName;
            this.dataForm.enableStatus = "1";
            this.dataForm.channel = item.channel;
            this.dataForm.machineType = item.machineType;
          }
        })
      }
    }
  }
</script>
