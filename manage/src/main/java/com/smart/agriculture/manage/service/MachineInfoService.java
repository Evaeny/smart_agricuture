package com.smart.agriculture.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.manage.entity.MachineInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Evan
 * @email
 * @date 2021-04-29 14:22:58
 */
public interface MachineInfoService extends IService<MachineInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);


    List<MachineInfoEntity> queryAll(Map<String, Object> params);
}

