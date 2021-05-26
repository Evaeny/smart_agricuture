package com.smart.agriculture.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.agriculture.common.utils.PageUtils;
import com.smart.agriculture.manage.entity.MachineInfoEntity;

import java.security.GeneralSecurityException;
import java.util.Map;

/**
 *
 *
 * @author Evan
 * @email
 * @date 2021-04-29 14:22:58
 */
public interface MachineInfoService extends IService<MachineInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 设备创建
     * @return Boolean
     */
    Boolean saveMachineInfo (MachineInfoEntity machineInfoEntity) throws Exception;

    /**
     * 设备删除
     * @return
     */
    void deleteMachineInfo (Long[] ids);

    /**
     * 状态更改
     */
    Boolean updateStatus (MachineInfoEntity machineInfoEntity);

    /**
     * 修改名称
     */
    Boolean updateName (MachineInfoEntity machineInfoEntity);
}

