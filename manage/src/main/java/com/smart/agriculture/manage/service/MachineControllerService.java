package com.smart.agriculture.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.manage.entity.MachineControllerEntity;
import com.smart.agriculture.manage.entity.MachineInfoEntity;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author Evan
 * @email
 * @date 2021-04-29 14:22:58
 */
public interface MachineControllerService extends IService<MachineControllerEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 修改状态
     */
    Boolean updateStatus (MachineControllerEntity machineControllerEntity);

    /**
     * 设备删除
     * @return
     */
    void deleteMachineController (Long[] ids);

    List<MachineInfoEntity> queryAll(Map<String, Object> params);
}

