package com.smart.agriculture.manage.task;

import cn.hutool.core.util.RandomUtil;
import com.smart.agriculture.manage.entity.MessageInfoEntity;
import com.smart.agriculture.manage.initBean.MessageInfoEntityInit;
import com.smart.agriculture.manage.service.MessageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

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

    private static String[] machineNameList = new String[]{"1", "2", "3", "4", "5", "6"};

    //3.添加定时任务
    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    public void configureTasks() {
        System.out.println("执行消息中心定时任务时间: " + LocalDateTime.now());
        String randomEle = RandomUtil.randomEle(machineNameList);
        MessageInfoEntity messageInfoEntity = MessageInfoEntityInit.map.get(randomEle);
        messageInfoEntity.setDeletYn("1");
        messageInfoEntity.setCreatTime(new Date());
        messageInfoService.save(messageInfoEntity);
    }
}
