<template>
  <el-dialog
    title='查询结果'
    :close-on-click-modal="false"
    :visible.sync="visible"
    :destroy-on-close=true
  >
    <el-form :model="dataForm" ref="dataForm" label-width="200px">
      <el-form-item label="传感器编号" prop="machineId">
        <span v-model="dataForm.machineId">{{dataForm.machineId}}</span>
      </el-form-item>
      <el-form-item label="传感器名称" prop="machineName">
        <span v-model="dataForm.machineName">{{dataForm.machineName}}</span>
      </el-form-item>
      <el-form-item label="传感器类型" prop="machineType">
        <span v-model="dataForm.machineType">{{dataForm.machineType}}</span>
      </el-form-item>
      <el-form-item label="数值" prop="conditionNumber">
        <span v-model="dataForm.conditionNumber">{{dataForm.conditionNumber}}</span>
      </el-form-item>
      <el-form-item label="单位" prop="unit">
        <span v-model="dataForm.unit">{{dataForm.unit}}</span>
      </el-form-item>
      <el-form-item label="设备通道" prop="channel">
        <span v-model="dataForm.channel">{{dataForm.channel}}</span>
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
        },{
          value: 'e',
          label: 'e'
        },{
          value: 'f',
          label: 'f'
        },{
          value: 'g',
          label: 'g'
        },{
          value: 'h',
          label: 'h'
        },{
          value: 'i',
          label: 'i'
        },{
          value: 'j',
          label: 'j'
        }],
      }
    },
    methods: {
      init(id) {
        this.dataForm.id = id;
        this.visible = true;
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields();
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/manage/machinesensor/query/${this.dataForm.id}`),
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
      transMachineType(data) {
        if (data == 'a') {
          return '土壤温度传感器'
        } else if (data == 'b') {
          return '土壤湿度传感器';
        } else if (data == 'c') {
          return '';
        }
      },
    }
  }
</script>
