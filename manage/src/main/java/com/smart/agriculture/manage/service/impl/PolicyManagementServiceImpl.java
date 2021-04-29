package com.smart.agriculture.manage.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.Query;

import com.smart.agriculture.manage.dao.PolicyManagementDao;
import com.smart.agriculture.manage.entity.PolicyManagementEntity;
import com.smart.agriculture.manage.service.PolicyManagementService;


@Service("policyManagementService")
public class PolicyManagementServiceImpl extends ServiceImpl<PolicyManagementDao, PolicyManagementEntity> implements PolicyManagementService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PolicyManagementEntity> page = this.page(
                new Query<PolicyManagementEntity>().getPage(params),
                new QueryWrapper<PolicyManagementEntity>()
        );

        return new PageUtils(page);
    }

}