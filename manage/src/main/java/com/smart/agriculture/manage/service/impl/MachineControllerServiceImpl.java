package com.smart.agriculture.manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.Query;

import com.smart.agriculture.manage.dao.MachineControllerDao;
import com.smart.agriculture.manage.entity.MachineControllerEntity;
import com.smart.agriculture.manage.service.MachineControllerService;


@Service("machineControllerService")
public class MachineControllerServiceImpl extends ServiceImpl<MachineControllerDao, MachineControllerEntity> implements MachineControllerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MachineControllerEntity> page = this.page(
                new Query<MachineControllerEntity>().getPage(params),
                new QueryWrapper<MachineControllerEntity>()
        );

        return new PageUtils(page);
    }

}