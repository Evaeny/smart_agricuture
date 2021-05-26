<template>
  <el-dialog
    title='查询结果'
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="200px">
      <el-form-item label="传感器编号" prop="machineId">
        <span>{{dataForm.machineId}}</span>
      </el-form-item>
      <el-form-item label="传感器名称" prop="machineName">
        <span>{{dataForm.machineName}}</span>
      </el-form-item>
      <el-form-item label="传感器类型" prop="machineType">
        <span>{{dataForm.machineType}}</span>
      </el-form-item>
      <el-form-item label="数值" prop="conditionNumber">
        <span>{{dataForm.conditionNumber}}</span>
      </el-form-item>
      <el-form-item label="单位" prop="unit">
        <span>{{dataForm.unit}}</span>
      </el-form-item>
      <el-form-item label="设备通道" prop="channel">
        <span>{{dataForm.channel}}</span>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        visible: false,
        dataForm: {
          id: 0,
          machineName: '',
          presetNumber: '',
          unit: '',
          channel: '',
          presetStatus: '',
          machineType: '',
          machineId: ''
        },
        machineTypeList: [
          {
            value: 'a',
            label: '土壤温度控制器'
          }, {
            value: 'b',
            label: '土壤加湿器'
          }, {
            value: 'c',
            label: '空气加湿器'
          }, {
            value: 'd',
            label: '挡风板'
          }, {
            value: 'e',
            label: '遮光板-补光灯'
          }, {
            value: 'f',
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
      }
    },
    methods: {

      init(machineId) {
        this.dataForm.machineId = machineId || 0;
        this.visible = true;
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields();
          if (this.dataForm.machineId) {
            this.$http({
              url: this.$http.adornUrl(`/manage/machinesensor/query/${this.dataForm.machineId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.machineName = data.machineSensor.machineName;
                this.dataForm.conditionNumber = data.machineSensor.conditionNumber;
                this.dataForm.unit = data.machineSensor.unit;
                this.dataForm.channel = data.machineSensor.channel;
                this.dataForm.machineType = data.machineSensor.machineType;
                this.dataForm.machineId = data.machineSensor.machineId;
                this.dataForm.machineType = this.transMachineType(data.machineSensor.machineType);
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/manage/machinesensor/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'machineName': this.dataForm.machineName,
                'conditionNumber': this.dataForm.conditionNumber,
                'unit': this.dataForm.unit,
                'channel': this.dataForm.channel,
                'enableStatus': this.dataForm.enableStatus,
                'machineType': this.dataForm.machineType,
                'machineId': this.dataForm.machineId
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
      transMachineType(data) {
        if (data == 'a') {
          return '土壤温度传感器'
        } else if (data == 'b') {
          return '土壤湿度传感器';
        }
        // switch (data) {
        //   case 'a':
        //     return '土壤温度传感器';
        //   case 'b':
        //     return '土壤湿度传感器';
        //   case 'c':
        //     return '空气湿度传感器';
        //   case 'd':
        //     return '风速传感器';
        //   case 'e':
        //     return '光照传感器';
        //   case 'f':
        //     return 'CO2浓度传感器';
        //   default :
        //     return '';
        // }
      },
    }
  }
</script>
