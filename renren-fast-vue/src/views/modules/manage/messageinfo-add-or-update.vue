<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="消息类型" prop="messagetype">
      <el-input v-model="dataForm.messagetype" placeholder="消息类型"></el-input>
    </el-form-item>
    <el-form-item label="设备id" prop="machineid">
      <el-input v-model="dataForm.machineid" placeholder="设备id"></el-input>
    </el-form-item>
    <el-form-item label="设备名称" prop="machinename">
      <el-input v-model="dataForm.machinename" placeholder="设备名称"></el-input>
    </el-form-item>
    <el-form-item label="接收时间" prop="creattime">
      <el-input v-model="dataForm.creattime" placeholder="接收时间"></el-input>
    </el-form-item>
    <el-form-item label="存在标识符" prop="deletyn">
      <el-input v-model="dataForm.deletyn" placeholder="存在标识符"></el-input>
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
          messagetype: '',
          machineid: '',
          machinename: '',
          creattime: '',
          deletyn: ''
        },
        dataRule: {
          messagetype: [
            { required: true, message: '消息类型不能为空', trigger: 'blur' }
          ],
          machineid: [
            { required: true, message: '设备id不能为空', trigger: 'blur' }
          ],
          machinename: [
            { required: true, message: '设备名称不能为空', trigger: 'blur' }
          ],
          creattime: [
            { required: true, message: '接收时间不能为空', trigger: 'blur' }
          ],
          deletyn: [
            { required: true, message: '存在标识符不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/manage/messageinfo/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.messagetype = data.messageInfo.messagetype
                this.dataForm.machineid = data.messageInfo.machineid
                this.dataForm.machinename = data.messageInfo.machinename
                this.dataForm.creattime = data.messageInfo.creattime
                this.dataForm.deletyn = data.messageInfo.deletyn
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
              url: this.$http.adornUrl(`/manage/messageinfo/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'messagetype': this.dataForm.messagetype,
                'machineid': this.dataForm.machineid,
                'machinename': this.dataForm.machinename,
                'creattime': this.dataForm.creattime,
                'deletyn': this.dataForm.deletyn
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
