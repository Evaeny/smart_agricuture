package com.smart.agriculture.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.manage.entity.PolicyManagementEntity;

import java.util.Map;

/**
 * 
 *
 * @author Evan
 * @email 
 * @date 2021-04-29 14:22:58
 */
public interface PolicyManagementService extends IService<PolicyManagementEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

