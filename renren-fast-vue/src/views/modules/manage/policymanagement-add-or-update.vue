<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="启用状态（默认为不启用）" prop="enablestatus">
      <el-input v-model="dataForm.enablestatus" placeholder="启用状态（默认为不启用）"></el-input>
    </el-form-item>
    <el-form-item label="单位" prop="unit">
      <el-input v-model="dataForm.unit" placeholder="单位"></el-input>
    </el-form-item>
    <el-form-item label="策略设定最小值" prop="numbermin">
      <el-input v-model="dataForm.numbermin" placeholder="策略设定最小值"></el-input>
    </el-form-item>
    <el-form-item label="策略设定最大值" prop="numbermax">
      <el-input v-model="dataForm.numbermax" placeholder="策略设定最大值"></el-input>
    </el-form-item>
    <el-form-item label="设备通道" prop="channel">
      <el-input v-model="dataForm.channel" placeholder="设备通道"></el-input>
    </el-form-item>
    <el-form-item label="设备类型" prop="machinetype">
      <el-input v-model="dataForm.machinetype" placeholder="设备类型"></el-input>
    </el-form-item>
    <el-form-item label="设备名称" prop="machinename">
      <el-input v-model="dataForm.machinename" placeholder="设备名称"></el-input>
    </el-form-item>
    <el-form-item label="设备编号" prop="machineid">
      <el-input v-model="dataForm.machineid" placeholder="设备编号"></el-input>
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
    data () {
      return {
        visible: false,
        dataForm: {
          enablestatus: '',
          unit: '',
          numbermin: '',
          numbermax: '',
          channel: '',
          machinetype: '',
          machinename: '',
          machineid: '',
          id: 0,
        },
        dataRule: {
          enablestatus: [
            { required: true, message: '启用状态（默认为不启用）不能为空', trigger: 'blur' }
          ],
          unit: [
            { required: true, message: '单位不能为空', trigger: 'blur' }
          ],
          numbermin: [
            { required: true, message: '策略设定最小值不能为空', trigger: 'blur' }
          ],
          numbermax: [
            { required: true, message: '策略设定最大值不能为空', trigger: 'blur' }
          ],
          channel: [
            { required: true, message: '设备通道不能为空', trigger: 'blur' }
          ],
          machinetype: [
            { required: true, message: '设备类型不能为空', trigger: 'blur' }
          ],
          machinename: [
            { required: true, message: '设备名称不能为空', trigger: 'blur' }
          ],
          machineid: [
            { required: true, message: '设备编号不能为空', trigger: 'blur' }
          ],
        }
      }
    },
    methods: {
      init (id) {
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
                this.dataForm.numbermin = data.policyManagement.numbermin
                this.dataForm.numbermax = data.policyManagement.numbermax
                this.dataForm.channel = data.policyManagement.channel
                this.dataForm.machinetype = data.policyManagement.machinetype
                this.dataForm.machinename = data.policyManagement.machinename
                this.dataForm.machineid = data.policyManagement.machineid
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/manage/policymanagement/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'enablestatus': this.dataForm.enablestatus,
                'unit': this.dataForm.unit,
                'numbermin': this.dataForm.numbermin,
                'numbermax': this.dataForm.numbermax,
                'channel': this.dataForm.channel,
                'machinetype': this.dataForm.machinetype,
                'machinename': this.dataForm.machinename,
                'machineid': this.dataForm.machineid,
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
