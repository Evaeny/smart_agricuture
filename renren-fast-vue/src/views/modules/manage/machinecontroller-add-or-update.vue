<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="140px">
      <el-form-item label="设备编号" prop="machineId">
        <el-input v-model="dataForm.machineId" placeholder="设备编号"></el-input>
      </el-form-item>
      <el-form-item label="设备名称" prop="machineName">
        <el-input v-model="dataForm.machineName" placeholder="设备名称"></el-input>
      </el-form-item>
      <el-form-item label="设定数值" prop="presetNumber">
        <el-input v-model="dataForm.presetNumber" placeholder="设定数值"></el-input>
      </el-form-item>
      <el-form-item label="单位" prop="unit">
        <el-input v-model="dataForm.unit" placeholder="单位"></el-input>
      </el-form-item>
      <el-form-item label="设备通道" prop="channel">
        <el-select v-model="dataForm.channel" placeholder="请选择设备通道" clearable>
          <el-option
            v-for="item in channelList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="设定启用状态" prop="presetStatus">
        <el-switch
          v-model="dataForm.presetStatus"
          active-color="#13ce66"
          inactive-color="#ff4949"
          active-value="1"
          inactive-value="0"
        ></el-switch>
      </el-form-item>
      <el-form-item label="设备类型" prop="machineType">
        <el-select v-model="dataForm.machineType" placeholder="请选择设备通道" clearable>
          <el-option
            v-for="item in machineTypeList"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
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
          id: 0,
          machineName: '',
          presetNumber: '',
          unit: '',
          channel: '',
          presetStatus: '',
          machineType: '',
          machineId: ''
        },
        dataRule: {
          machineName: [
            {required: true, message: '设备名称不能为空', trigger: 'blur'}
          ],
          presetNumber: [
            {required: true, message: '设定数值不能为空', trigger: 'blur'}
          ],
          unit: [
            {required: true, message: '单位不能为空', trigger: 'blur'}
          ],
          channel: [
            {required: true, message: '设备通道不能为空', trigger: 'blur'}
          ],
          presetStatus: [
            {required: true, message: '设定启用状态不能为空', trigger: 'blur'}
          ],
          machineType: [
            {required: true, message: '设备类型不能为空', trigger: 'blur'}
          ],
          machineId: [
            {required: true, message: '设备编号不能为空', trigger: 'blur'}
          ]
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
                this.dataForm.machineName = data.machineController.machineName
                this.dataForm.presetNumber = data.machineController.presetNumber
                this.dataForm.unit = data.machineController.unit
                this.dataForm.channel = data.machineController.channel
                this.dataForm.presetStatus = data.machineController.presetStatus
                this.dataForm.machineType = data.machineController.machineType
                this.dataForm.machineId = data.machineController.machineId
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
              url: this.$http.adornUrl(`/manage/machinecontroller/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'machineName': this.dataForm.machineName,
                'presetNumber': this.dataForm.presetNumber,
                'unit': this.dataForm.unit,
                'channel': this.dataForm.channel,
                'presetStatus': this.dataForm.presetStatus,
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
      }
    }
  }
</script>
