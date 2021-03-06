package com.smart.agriculture.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.manage.entity.MachineSensorEntity;

import java.util.List;
import java.util.Map;

/**
 * @author Evan
 * @email
 * @date 2021-04-29 14:22:58
 */
public interface MachineSensorService extends IService<MachineSensorEntity> {

    PageUtils queryPage(Map<String, Object> params);


    Integer saveMachineSensor(MachineSensorEntity machineSensorEntity);

    MachineSensorEntity saveMachineSensor(String machineId) throws Exception;


    List<MachineSensorEntity> queryAll(Map<String, Object> params);
}

