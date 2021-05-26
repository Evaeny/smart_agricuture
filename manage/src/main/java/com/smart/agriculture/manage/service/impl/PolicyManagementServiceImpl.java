package com.smart.agriculture.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.common.utils.Query;
import com.smart.agriculture.manage.dao.PolicyManagementDao;
import com.smart.agriculture.manage.entity.PolicyManagementEntity;
import com.smart.agriculture.manage.service.PolicyManagementService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("policyManagementService")
public class PolicyManagementServiceImpl extends ServiceImpl<PolicyManagementDao, PolicyManagementEntity> implements PolicyManagementService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PolicyManagementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineType")), "machine_type", params.get("machineType"));
        queryWrapper.eq(!StringUtils.isEmpty(params.get("channel")), "channel", params.get("channel"));
        queryWrapper.eq(!StringUtils.isEmpty(params.get("machineId")), "machine_id", params.get("machineId"));
        queryWrapper.like(!StringUtils.isEmpty(params.get("machineName")), "machine_name", params.get("machineName"));
        IPage<PolicyManagementEntity> page = this.page(
                new Query<PolicyManagementEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

}
