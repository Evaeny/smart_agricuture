<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="140px">
      <el-form-item label="土壤湿度" prop="conditionNumber">
        <el-input v-model="dataForm.conditionNumber" placeholder="土壤湿度"></el-input>
      </el-form-item>
      <el-form-item label="单位" prop="unit">
        <el-input v-model="dataForm.unit" placeholder="单位"></el-input>
      </el-form-item>
      <el-form-item label="来源设备编号" prop="machineid">
        <el-input v-model="dataForm.machineId" placeholder="来源设备编号"></el-input>
      </el-form-item>
      <el-form-item label="来源设备名称" prop="machinename">
        <el-input v-model="dataForm.machineName" placeholder="来源设备名称"></el-input>
      </el-form-item>
      <el-form-item label="来源通道" prop="channel">
        <el-select v-model="dataForm.channel" placeholder="请选择设备通道" clearable>
          <el-option
            v-for="item in channelList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
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
          conditionNumber: '',
          createTime: '',
          machineId: '',
          id: 0,
          unit: '',
          channel: '',
          remark: '',
          machineName: '',
          machineType: 'b'
        },
        dataRule: {
          conditionNumber: [
            {required: true, message: '土壤湿度不能为空', trigger: 'blur'}
          ],
          machineId: [
            {required: true, message: '来源设备编号不能为空', trigger: 'blur'}
          ],
          unit: [
            {required: true, message: '单位不能为空', trigger: 'blur'}
          ],
          channel: [
            {required: true, message: '来源通道不能为空', trigger: 'blur'}
          ],
          remark: [
            {required: true, message: '备注不能为空', trigger: 'blur'}
          ],
          machineName: [
            {required: true, message: '来源设备名称不能为空', trigger: 'blur'}
          ]
        },
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
      init(id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/manage/machinesensor/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.conditionNumber = data.machineSensor.conditionNumber
                this.dataForm.createTime = data.machineSensor.createTime
                this.dataForm.machineId = data.machineSensor.machineId
                this.dataForm.unit = data.machineSensor.unit
                this.dataForm.channel = data.machineSensor.channel
                this.dataForm.remark = data.machineSensor.remark
                this.dataForm.machineName = data.machineSensor.machineName
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
                'conditionNumber': this.dataForm.conditionNumber,
                'createTime': this.dataForm.createTime,
                'machineId': this.dataForm.machineId,
                'id': this.dataForm.id || undefined,
                'unit': this.dataForm.unit,
                'channel': this.dataForm.channel,
                'remark': this.dataForm.remark,
                'machineName': this.dataForm.machineName,
                'machineType': this.dataForm.machineType,
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
      }
    }
  }
</script>
