package com.smart.agriculture.manage.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.Query;
import com.smart.agriculture.manage.converter.MachineConverter;
import com.smart.agriculture.manage.dao.MachineSensorDao;
import com.smart.agriculture.manage.entity.MachineSensorEntity;
import com.smart.agriculture.manage.entity.PolicyManagementEntity;
import com.smart.agriculture.manage.service.MachineSensorService;
import com.smart.agriculture.manage.service.PolicyManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;


@Service("machineSensorService")
public class MachineSensorServiceImpl extends ServiceImpl<MachineSensorDao, MachineSensorEntity> implements MachineSensorService {

    @Autowired
    private PolicyManagementService policyManagementService;

    @Autowired
    private MachineConverter machineSensorConverter;

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

        PolicyManagementEntity policyManagementEntity = machineSensorConverter.  sensorToPolicy(machineSensor);
        policyManagementService.save(policyManagementEntity);

        return null;
    }

}