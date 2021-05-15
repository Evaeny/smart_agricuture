<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="140px">
      <el-form-item label="启用状态" prop="enablestatus">
        <el-input v-model="dataForm.enablestatus" placeholder="启用状态"></el-input>
      </el-form-item>
      <el-form-item label="单位" prop="unit">
        <el-input v-model="dataForm.unit" placeholder="单位"></el-input>
      </el-form-item>
      <el-form-item label="策略设定最小值" prop="numberMin">
        <el-input v-model="dataForm.numberMin" placeholder="策略设定最小值"></el-input>
      </el-form-item>
      <el-form-item label="策略设定最大值" prop="numberMax">
        <el-input v-model="dataForm.numberMax" placeholder="策略设定最大值"></el-input>
      </el-form-item>
      <el-form-item label="设备通道" prop="channel">
        <el-input v-model="dataForm.channel" placeholder="设备通道"></el-input>
      </el-form-item>
      <el-form-item label="设备类型" prop="machineType">
        <el-input v-model="dataForm.machineType" placeholder="设备类型"></el-input>
      </el-form-item>
      <el-form-item label="设备名称" prop="machineName">
        <el-input v-model="dataForm.machineName" placeholder="设备名称"></el-input>
      </el-form-item>
      <el-form-item label="设备编号" prop="machineId">
        <el-input v-model="dataForm.machineId" placeholder="设备编号"></el-input>
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
          enablestatus: '',
          unit: '',
          numberMin: '',
          numberMax: '',
          channel: '',
          machineType: '',
          machineName: '',
          machineId: '',
          id: 0,
        },
        dataRule: {
          enablestatus: [
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
        }
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
              url: this.$http.adornUrl(`/manage/policymanagement/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.enablestatus = data.policyManagement.enablestatus
                this.dataForm.unit = data.policyManagement.unit
                this.dataForm.numberMin = data.policyManagement.numberMin
                this.dataForm.numberMax = data.policyManagement.numberMax
                this.dataForm.channel = data.policyManagement.channel
                this.dataForm.machineType = data.policyManagement.machineType
                this.dataForm.machineName = data.policyManagement.machineName
                this.dataForm.machineId = data.policyManagement.machineId
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
              url: this.$http.adornUrl(`/manage/policymanagement/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'enablestatus': this.dataForm.enablestatus,
                'unit': this.dataForm.unit,
                'numberMin': this.dataForm.numberMin,
                'numberMax': this.dataForm.numberMax,
                'channel': this.dataForm.channel,
                'machineType': this.dataForm.machineType,
                'machineName': this.dataForm.machineName,
                'machineId': this.dataForm.machineId,
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
      }
    }
  }
</script>
