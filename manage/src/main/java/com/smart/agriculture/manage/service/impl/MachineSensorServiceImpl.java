package com.smart.agriculture.manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.Query;

import com.smart.agriculture.manage.dao.MachineSensorDao;
import com.smart.agriculture.manage.entity.MachineSensorEntity;
import com.smart.agriculture.manage.service.MachineSensorService;


@Service("machineSensorService")
public class MachineSensorServiceImpl extends ServiceImpl<MachineSensorDao, MachineSensorEntity> implements MachineSensorService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MachineSensorEntity> page = this.page(
                new Query<MachineSensorEntity>().getPage(params),
                new QueryWrapper<MachineSensorEntity>()
        );

        return new PageUtils(page);
    }

}