<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="设备通道" prop="channel">
      <el-input v-model="dataForm.channel" placeholder="设备通道"></el-input>
    </el-form-item>
    <el-form-item label="设备名称" prop="machinename">
      <el-input v-model="dataForm.machinename" placeholder="设备名称"></el-input>
    </el-form-item>
    <el-form-item label="设备类型" prop="machinetype">
      <el-input v-model="dataForm.machinetype" placeholder="设备类型"></el-input>
    </el-form-item>
    <el-form-item label="设备启用状态（默认为不启用）" prop="machinestatus">
      <el-input v-model="dataForm.machinestatus" placeholder="设备启用状态（默认为不启用）"></el-input>
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
          channel: '',
          id: 0,
          machinename: '',
          machinetype: '',
          machinestatus: '',
          machineid: ''
        },
        dataRule: {
          channel: [
            { required: true, message: '设备通道不能为空', trigger: 'blur' }
          ],
          machinename: [
            { required: true, message: '设备名称不能为空', trigger: 'blur' }
          ],
          machinetype: [
            { required: true, message: '设备类型不能为空', trigger: 'blur' }
          ],
          machinestatus: [
            { required: true, message: '设备启用状态（默认为不启用）不能为空', trigger: 'blur' }
          ],
          machineid: [
            { required: true, message: '设备编号不能为空', trigger: 'blur' }
          ]
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
              url: this.$http.adornUrl(`/manage/machineinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.channel = data.machineInfo.channel
                this.dataForm.machinename = data.machineInfo.machinename
                this.dataForm.machinetype = data.machineInfo.machinetype
                this.dataForm.machinestatus = data.machineInfo.machinestatus
                this.dataForm.machineid = data.machineInfo.machineid
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
              url: this.$http.adornUrl(`/manage/machineinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'channel': this.dataForm.channel,
                'id': this.dataForm.id || undefined,
                'machinename': this.dataForm.machinename,
                'machinetype': this.dataForm.machinetype,
                'machinestatus': this.dataForm.machinestatus,
                'machineid': this.dataForm.machineid
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
