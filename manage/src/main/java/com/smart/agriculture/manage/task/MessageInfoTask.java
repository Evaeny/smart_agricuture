package com.smart.agriculture.manage.task;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.smart.agriculture.manage.converter.MachineConverter;
import com.smart.agriculture.manage.entity.MachineControllerEntity;
import com.smart.agriculture.manage.entity.MachineSensorEntity;
import com.smart.agriculture.manage.entity.MessageInfoEntity;
import com.smart.agriculture.manage.entity.PolicyManagementEntity;
import com.smart.agriculture.manage.initBean.MessageInfoEntityInit;
import com.smart.agriculture.manage.service.MachineControllerService;
import com.smart.agriculture.manage.service.MachineSensorService;
import com.smart.agriculture.manage.service.MessageInfoService;
import com.smart.agriculture.manage.service.PolicyManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

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
public class MessageInfoTask {

    @Autowired
    private MessageInfoService messageInfoService;

    @Autowired
    private PolicyManagementService policyManagementService;

    @Autowired
    private MachineSensorService machineSensorService;

    @Autowired
    private MachineControllerService machineControllerService;

    @Autowired
    private MachineConverter machineConverter;

    private static String[] machineNameList = new String[]{"1", "2", "3", "4", "5", "6"};

    //3.添加定时任务
    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    public void configureTasks() {
        System.out.println("执行消息中心定时任务时间: " + LocalDateTime.now());
        String randomEle = RandomUtil.randomEle(machineNameList);
        LambdaQueryWrapper<MachineControllerEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MachineControllerEntity::getPresetStatus, true);
        // 1.查询所有启用状态的设备
        List<MachineControllerEntity> machineList = machineControllerService.list(queryWrapper);

        for (MachineControllerEntity item : machineList) {
            // 2.生成随机数
            Integer randomInt = RandomUtil.randomInt(0, 100);
            MachineSensorEntity sensorEntity = machineConverter.controllerToSensor(item);
            sensorEntity.setConditionNumber(randomInt);


            String controllerId = item.getMachineId();
            LambdaQueryWrapper<PolicyManagementEntity> policyQueryWrapper = new LambdaQueryWrapper<>();
            policyQueryWrapper.eq(PolicyManagementEntity::getBetweenSensorId, controllerId).eq(PolicyManagementEntity::getEnableStatus, true);
            List<PolicyManagementEntity> policyList = policyManagementService.list(policyQueryWrapper);
            if (!CollectionUtils.isEmpty(policyList)) {
                for (PolicyManagementEntity policyItem : policyList) {
                    Integer numberMin = policyItem.getNumberMin();
                    Integer numberMax = policyItem.getNumberMax();
                }
            }

        }


        MessageInfoEntity messageInfoEntity = MessageInfoEntityInit.map.get(randomEle);
        messageInfoEntity.setDeletYn("1");
        messageInfoEntity.setCreatTime(new Date());
        messageInfoService.save(messageInfoEntity);
    }
}
