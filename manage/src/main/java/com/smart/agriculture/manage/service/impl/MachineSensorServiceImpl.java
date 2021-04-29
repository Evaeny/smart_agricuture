package com.smart.agriculture.manage.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.Query;
import com.smart.agriculture.manage.dao.MachineSensorDao;
import com.smart.agriculture.manage.entity.MachineSensorEntity;
import com.smart.agriculture.manage.service.MachineSensorService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("machineSensorService")
public class MachineSensorServiceImpl extends ServiceImpl<MachineSensorDao, MachineSensorEntity> implements MachineSensorService {

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

}