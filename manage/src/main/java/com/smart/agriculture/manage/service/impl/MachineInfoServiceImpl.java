package com.smart.agriculture.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.Query;
import com.smart.agriculture.manage.dao.MachineInfoDao;
import com.smart.agriculture.manage.entity.MachineInfoEntity;
import com.smart.agriculture.manage.service.MachineInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("machineInfoService")
public class MachineInfoServiceImpl extends ServiceImpl<MachineInfoDao, MachineInfoEntity> implements MachineInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<MachineInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineType")), "machine_type", params.get("machineType"));
        queryWrapper.eq(!StringUtils.isEmpty(params.get("channel")), "channel", params.get("channel"));

        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineId")), "machine_id", params.get("machineId"));


        queryWrapper.like(!StringUtils.isEmpty(params.get("machineName")), "machine_name", params.get("machineName"));
        IPage<MachineInfoEntity> page = this.page(
                new Query<MachineInfoEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

}