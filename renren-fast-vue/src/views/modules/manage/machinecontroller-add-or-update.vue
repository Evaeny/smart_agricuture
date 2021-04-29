<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="设备名称" prop="machinename">
      <el-input v-model="dataForm.machinename" placeholder="设备名称"></el-input>
    </el-form-item>
    <el-form-item label="设定数值" prop="presetnumber">
      <el-input v-model="dataForm.presetnumber" placeholder="设定数值"></el-input>
    </el-form-item>
    <el-form-item label="单位" prop="unit">
      <el-input v-model="dataForm.unit" placeholder="单位"></el-input>
    </el-form-item>
    <el-form-item label="设备通道（a、b、c......)" prop="channel">
      <el-input v-model="dataForm.channel" placeholder="设备通道（a、b、c......)"></el-input>
    </el-form-item>
    <el-form-item label="设定启用状态（默认为不启用）" prop="presetstatus">
      <el-input v-model="dataForm.presetstatus" placeholder="设定启用状态（默认为不启用）"></el-input>
    </el-form-item>
    <el-form-item label="设备类型" prop="machinetype">
      <el-input v-model="dataForm.machinetype" placeholder="设备类型"></el-input>
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
          id: 0,
          machinename: '',
          presetnumber: '',
          unit: '',
          channel: '',
          presetstatus: '',
          machinetype: '',
          machineid: ''
        },
        dataRule: {
          machinename: [
            { required: true, message: '设备名称不能为空', trigger: 'blur' }
          ],
          presetnumber: [
            { required: true, message: '设定数值不能为空', trigger: 'blur' }
          ],
          unit: [
            { required: true, message: '单位不能为空', trigger: 'blur' }
          ],
          channel: [
            { required: true, message: '设备通道（a、b、c......)不能为空', trigger: 'blur' }
          ],
          presetstatus: [
            { required: true, message: '设定启用状态（默认为不启用）不能为空', trigger: 'blur' }
          ],
          machinetype: [
            { required: true, message: '设备类型不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/manage/machinecontroller/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.machinename = data.machineController.machinename
                this.dataForm.presetnumber = data.machineController.presetnumber
                this.dataForm.unit = data.machineController.unit
                this.dataForm.channel = data.machineController.channel
                this.dataForm.presetstatus = data.machineController.presetstatus
                this.dataForm.machinetype = data.machineController.machinetype
                this.dataForm.machineid = data.machineController.machineid
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
              url: this.$http.adornUrl(`/manage/machinecontroller/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'machinename': this.dataForm.machinename,
                'presetnumber': this.dataForm.presetnumber,
                'unit': this.dataForm.unit,
                'channel': this.dataForm.channel,
                'presetstatus': this.dataForm.presetstatus,
                'machinetype': this.dataForm.machinetype,
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
