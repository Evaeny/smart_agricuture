<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="光照强度" prop="lightintensity">
      <el-input v-model="dataForm.lightintensity" placeholder="光照强度"></el-input>
    </el-form-item>
    <el-form-item label="记录时间" prop="createtime">
      <el-input v-model="dataForm.createtime" placeholder="记录时间"></el-input>
    </el-form-item>
    <el-form-item label="来源设备编号" prop="machineid">
      <el-input v-model="dataForm.machineid" placeholder="来源设备编号"></el-input>
    </el-form-item>
    <el-form-item label="单位" prop="unit">
      <el-input v-model="dataForm.unit" placeholder="单位"></el-input>
    </el-form-item>
    <el-form-item label="来源通道" prop="channel">
      <el-input v-model="dataForm.channel" placeholder="来源通道"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
    </el-form-item>
    <el-form-item label="来源设备名称" prop="machinename">
      <el-input v-model="dataForm.machinename" placeholder="来源设备名称"></el-input>
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
          lightintensity: '',
          createtime: '',
          machineid: '',
          id: 0,
          unit: '',
          channel: '',
          remark: '',
          machinename: ''
        },
        dataRule: {
          lightintensity: [
            { required: true, message: '光照强度不能为空', trigger: 'blur' }
          ],
          createtime: [
            { required: true, message: '记录时间不能为空', trigger: 'blur' }
          ],
          machineid: [
            { required: true, message: '来源设备编号不能为空', trigger: 'blur' }
          ],
          unit: [
            { required: true, message: '单位不能为空', trigger: 'blur' }
          ],
          channel: [
            { required: true, message: '来源通道不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          machinename: [
            { required: true, message: '来源设备名称不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/manage/lightintensitysensor/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.lightintensity = data.lightIntensitySensor.lightintensity
                this.dataForm.createtime = data.lightIntensitySensor.createtime
                this.dataForm.machineid = data.lightIntensitySensor.machineid
                this.dataForm.unit = data.lightIntensitySensor.unit
                this.dataForm.channel = data.lightIntensitySensor.channel
                this.dataForm.remark = data.lightIntensitySensor.remark
                this.dataForm.machinename = data.lightIntensitySensor.machinename
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
              url: this.$http.adornUrl(`/manage/lightintensitysensor/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'lightintensity': this.dataForm.lightintensity,
                'createtime': this.dataForm.createtime,
                'machineid': this.dataForm.machineid,
                'id': this.dataForm.id || undefined,
                'unit': this.dataForm.unit,
                'channel': this.dataForm.channel,
                'remark': this.dataForm.remark,
                'machinename': this.dataForm.machinename
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
