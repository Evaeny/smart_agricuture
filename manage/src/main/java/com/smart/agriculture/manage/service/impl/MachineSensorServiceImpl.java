package com.smart.agriculture.manage.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.Query;
import com.smart.agriculture.manage.converter.MachineConverter;
import com.smart.agriculture.manage.dao.MachineSensorDao;
import com.smart.agriculture.manage.entity.MachineInfoEntity;
import com.smart.agriculture.manage.entity.MachineSensorEntity;
import com.smart.agriculture.manage.entity.MessageInfoEntity;
import com.smart.agriculture.manage.entity.PolicyManagementEntity;
import com.smart.agriculture.manage.service.MachineInfoService;
import com.smart.agriculture.manage.service.MachineSensorService;
import com.smart.agriculture.manage.service.MessageInfoService;
import com.smart.agriculture.manage.service.PolicyManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("machineSensorService")
public class MachineSensorServiceImpl extends ServiceImpl<MachineSensorDao, MachineSensorEntity> implements MachineSensorService {

    @Autowired
    private PolicyManagementService policyManagementService;

    @Autowired
    private MachineConverter machineSensorConverter;

    @Autowired
    private MachineInfoService machineInfoService;

    @Autowired
    private MessageInfoService messageInfoService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<MachineSensorEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineType")), "machine_type", params.get("machineType"));
        queryWrapper.eq(!StringUtils.isEmpty(params.get("channel")), "channel", params.get("channel"));
        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineId")), "machineId", params.get("machineId"));
        queryWrapper.like(!StringUtils.isEmpty(params.get("machineName")), "machineName", params.get("machineName"));
        IPage<MachineSensorEntity> page = this.page(
                new Query<MachineSensorEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public Integer saveMachineSensor(MachineSensorEntity machineSensor) {
        machineSensor.setCreateTime(new Date());
        this.save(machineSensor);

        PolicyManagementEntity policyManagementEntity = machineSensorConverter.sensorToPolicy(machineSensor);
        policyManagementService.save(policyManagementEntity);

        return null;
    }

    @Override
    public MachineSensorEntity saveMachineSensor(String id) throws Exception {
        LambdaQueryWrapper<MachineInfoEntity> infoQueryWrapper = new LambdaQueryWrapper<>();
        infoQueryWrapper.eq(MachineInfoEntity::getId, id);
        infoQueryWrapper.eq(MachineInfoEntity::getMachineStatus, true);
        infoQueryWrapper.last("limit 1");
        MachineInfoEntity machineInfoEntity = machineInfoService.getBaseMapper().selectOne(infoQueryWrapper);
        if (machineInfoEntity == null) {
            throw new Exception("设备编码不存在或者设备未启用,请确认");
        }

        MachineSensorEntity sensorEntity = machineSensorConverter.infoToSensor(machineInfoEntity);
        double randomDouble = RandomUtil.randomDouble(10, 40, 2, RoundingMode.HALF_UP);
        sensorEntity.setConditionNumber(randomDouble);
        sensorEntity.setCreateTime(new Date());
        this.save(sensorEntity);

        LambdaQueryWrapper<PolicyManagementEntity> policyQueryWrapper = new LambdaQueryWrapper<>();
        policyQueryWrapper.eq(PolicyManagementEntity::getBetweenSensorId, machineInfoEntity.getMachineId()).eq(PolicyManagementEntity::getEnableStatus, true);
        List<PolicyManagementEntity> policyList = policyManagementService.list(policyQueryWrapper);
        if (!CollectionUtils.isEmpty(policyList)) {
            for (PolicyManagementEntity policyItem : policyList) {
                Double numberMin = policyItem.getNumberMin();
                Double numberMax = policyItem.getNumberMax();
                if (randomDouble > numberMax || randomDouble < numberMin) {
                    MessageInfoEntity messageInfoEntity = new MessageInfoEntity();
                    messageInfoEntity.setMachineId(machineInfoEntity.getMachineId());
                    messageInfoEntity.setMachineName(machineInfoEntity.getMachineName());
                    messageInfoEntity.setMessageType("数据异常");
                    messageInfoEntity.setCreatTime(new Date());
                    messageInfoEntity.setDeletYn("1");
                    messageInfoService.save(messageInfoEntity);

                    MessageInfoEntity messageInfoEntity2 = new MessageInfoEntity();
                    messageInfoEntity2.setMachineId(policyItem.getMachineId());
                    messageInfoEntity2.setMachineName(policyItem.getMachineName());
                    messageInfoEntity2.setMessageType("控制器修复");
                    messageInfoEntity2.setCreatTime(new Date());
                    messageInfoEntity2.setDeletYn("1");
                    messageInfoService.save(messageInfoEntity2);
                }
            }
        }
        return sensorEntity;
    }

    @Override
    public List<MachineSensorEntity> queryAll(Map<String, Object> params) {
        return null;
    }

}