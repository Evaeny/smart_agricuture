package com.smart.agriculture.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.Query;
import com.smart.agriculture.manage.dao.MessageInfoDao;
import com.smart.agriculture.manage.entity.MessageInfoEntity;
import com.smart.agriculture.manage.service.MessageInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("messageInfoService")
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoDao, MessageInfoEntity> implements MessageInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<MessageInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineType")), "machine_type", params.get("machineType"));
        queryWrapper.eq(!StringUtils.isEmpty(params.get("channel")), "channel", params.get("channel"));
        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineId")), "machine_id", params.get("machineId"));
        queryWrapper.like(!StringUtils.isEmpty(params.get("machineName")), "machine_name", params.get("machineName"));
        queryWrapper.orderByDesc("creatTime");
        IPage<MessageInfoEntity> page = this.page(
                new Query<MessageInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}