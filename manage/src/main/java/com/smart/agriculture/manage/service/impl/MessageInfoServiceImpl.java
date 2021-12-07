package com.smart.agriculture.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.Query;
import com.smart.agriculture.manage.dao.MessageInfoDao;
import com.smart.agriculture.manage.entity.MachineInfoEntity;
import com.smart.agriculture.manage.entity.MessageInfoEntity;
import com.smart.agriculture.manage.service.MessageInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("messageInfoService")
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoDao, MessageInfoEntity> implements MessageInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<MessageInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineType")), "machine_type", params.get("machineType"));
        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineId")), "machine_id", params.get("machineId"));
        queryWrapper.like(!StringUtils.isEmpty(params.get("machineName")), "machine_name", params.get("machineName"));
        queryWrapper.eq("delete_yn",true);
        queryWrapper.orderByDesc("creat_time");
        IPage<MessageInfoEntity> page = this.page(
                new Query<MessageInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils selectListInfo(Map<String, Object> params) {
        QueryWrapper<MessageInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineType")), "machine_type", params.get("machineType"));
        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineId")), "machine_id", params.get("machineId"));
        queryWrapper.like(!StringUtils.isEmpty(params.get("machineName")), "machine_name", params.get("machineName"));
        queryWrapper.eq("delete_yn",true);
        queryWrapper.ge(!StringUtils.isEmpty(params.get("startTime")), "creat_time", params.get("startTime"));
        queryWrapper.le(!StringUtils.isEmpty(params.get("endTime")), "creat_time", params.get("endTime"));
        queryWrapper.orderByDesc("creat_time");
        IPage<MessageInfoEntity> page = this.page(
                new Query<MessageInfoEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public Boolean deleteMessageInfo(Long[] ids) {
        LambdaQueryWrapper<MessageInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MessageInfoEntity::getId, ids);
        List<MessageInfoEntity> entityList = this.baseMapper.selectList(queryWrapper);
        for (MessageInfoEntity messageInfoEntity : entityList) {
                messageInfoEntity.setDeleteYn("0");
                  this.updateById(messageInfoEntity);
        }
        return true;
    }

}
