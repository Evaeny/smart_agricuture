package com.smart.agriculture.manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.Query;

import com.smart.agriculture.manage.dao.MessageInfoDao;
import com.smart.agriculture.manage.entity.MessageInfoEntity;
import com.smart.agriculture.manage.service.MessageInfoService;


@Service("messageInfoService")
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoDao, MessageInfoEntity> implements MessageInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MessageInfoEntity> page = this.page(
                new Query<MessageInfoEntity>().getPage(params),
                new QueryWrapper<MessageInfoEntity>()
        );

        return new PageUtils(page);
    }

}