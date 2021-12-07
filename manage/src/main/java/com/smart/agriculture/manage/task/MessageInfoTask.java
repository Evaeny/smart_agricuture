package com.smart.agriculture.manage.task;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smart.agriculture.manage.converter.MachineConverter;
import com.smart.agriculture.manage.entity.*;
import com.smart.agriculture.manage.service.*;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * ClassName: MessageInfoTask
 * Description:
 * date: 2021/5/9 21:37
 *
 * @author 蒋一帆
 */
//@Component
@Log4j2
public class MessageInfoTask {

    @Autowired
    private MessageInfoService messageInfoService;

    @Autowired
    private PolicyManagementService policyManagementService;

    @Autowired
    private MachineSensorService machineSensorService;

    @Autowired
    private MachineInfoService machineInfoService;

    @Autowired
    private MachineConverter machineConverter;

    private static String[] typeList = new String[]{"a", "b", "c", "d", "e", "f"};

    //3.添加定时任务
    @Scheduled(cron = "0/20 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    public void configureTasks() {
        log.info("执行消息中心定时任务时间: " + LocalDateTime.now());
        LambdaQueryWrapper<MachineInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MachineInfoEntity::getMachineType, typeList);
        queryWrapper.eq(MachineInfoEntity::getMachineStatus, true);
        // 1.查询所有启用状态的设备
        List<MachineInfoEntity> machineList = machineInfoService.list(queryWrapper);
        MachineSensorEntity machineSensorEntity = new MachineSensorEntity();
        for (MachineInfoEntity item : machineList) {
            // 2.生成随机数
            Double randomDouble = RandomUtil.randomDouble(0, 100, 2, RoundingMode.HALF_UP);
            log.info("生成随机数: " + randomDouble);
            item.setId(null);
            machineSensorEntity.setConditionNumber(randomDouble);
            machineSensorEntity.setMachineId(item.getMachineId());
            machineSensorEntity.setChannel(item.getChannel());
            machineSensorEntity.setMachineName(item.getMachineName());
            machineSensorEntity.setUnit(item.getUnit());
            machineSensorEntity.setMachineType(item.getMachineType());
            machineSensorEntity.setCreateTime(new Date());
            machineSensorService.saveMachineSensor(machineSensorEntity);

            String sensorId = item.getMachineId();
            LambdaQueryWrapper<PolicyManagementEntity> policyQueryWrapper = new LambdaQueryWrapper<>();
            policyQueryWrapper.eq(PolicyManagementEntity::getBetweenSensorId, sensorId).eq(PolicyManagementEntity::getEnableStatus, true);
            List<PolicyManagementEntity> policyList = policyManagementService.list(policyQueryWrapper);
            if (!CollectionUtils.isEmpty(policyList)) {
                for (PolicyManagementEntity policyItem : policyList) {
                    Double numberMin = policyItem.getNumberMin();
                    Double numberMax = policyItem.getNumberMax();
                    if (randomDouble > numberMax || randomDouble < numberMin) {
                        log.info("插入消息中心表:數據異常 設備id" + item.getMachineId());
                        MessageInfoEntity messageInfoEntity = new MessageInfoEntity();
                        messageInfoEntity.setMachineId(item.getMachineId());
                        messageInfoEntity.setMachineName(item.getMachineName());
                        messageInfoEntity.setMessageType("数据异常");
                        messageInfoEntity.setCreatTime(new Date());
                        messageInfoEntity.setDeleteYn("1");
                        messageInfoService.save(messageInfoEntity);

                      log.info("插入消息中心表:控制器修复 設備id" + item.getMachineId());
                        MessageInfoEntity messageInfoEntity2 = new MessageInfoEntity();
                        messageInfoEntity2.setMachineId(policyItem.getMachineId());
                        messageInfoEntity2.setMachineName(policyItem.getMachineName());
                        messageInfoEntity2.setMessageType("控制器修复");
                        messageInfoEntity2.setCreatTime(new Date());
                        messageInfoEntity2.setDeleteYn("1");
                        messageInfoService.save(messageInfoEntity2);
                    }
                }
            }

        }
    }
}
