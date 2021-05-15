package com.smart.agriculture.manage.initBean;

import com.smart.agriculture.manage.entity.MessageInfoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: MachineEntityInit
 * Description:
 * date: 2021/5/9 22:19
 *
 * @author 蒋一帆
 */
@Component
public class MessageInfoEntityInit implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(MessageInfoEntityInit.class);

    //项目启动加载数据存入map中
    public static Map<String, MessageInfoEntity> map = new HashMap<String, MessageInfoEntity>();

    public static Map<String, MessageInfoEntity> getMap() {
        return map;
    }


    public static void setMap(Map<String, MessageInfoEntity> map) {
        MessageInfoEntityInit.map = map;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("----初始化系统参数开始---------------start");
        MessageInfoEntity messageInfoEntity = new MessageInfoEntity();
        messageInfoEntity.setMachineName("自贡汽车总站");
        messageInfoEntity.setMessageType("接口连接");
        messageInfoEntity.setMachineId("Asc02396");

        MessageInfoEntity messageInfoEntity2 = new MessageInfoEntity();
        messageInfoEntity2.setMachineName("自贡汽车总站");
        messageInfoEntity2.setMessageType("接口断开");
        messageInfoEntity2.setMachineId("Asc02396");

        MessageInfoEntity messageInfoEntity3 = new MessageInfoEntity();
        messageInfoEntity3.setMachineName("武汉汽车总站");
        messageInfoEntity3.setMessageType("接口连接");
        messageInfoEntity3.setMachineId("Asc13588");

        MessageInfoEntity messageInfoEntity4 = new MessageInfoEntity();
        messageInfoEntity4.setMachineName("武汉汽车总站");
        messageInfoEntity4.setMessageType("接口连接");
        messageInfoEntity4.setMachineId("Asc13588");

        MessageInfoEntity messageInfoEntity5 = new MessageInfoEntity();
        messageInfoEntity5.setMachineName("南京汽车总站");
        messageInfoEntity5.setMessageType("接口连接");
        messageInfoEntity5.setMachineId("Asc60234");

        MessageInfoEntity messageInfoEntity6 = new MessageInfoEntity();
        messageInfoEntity6.setMachineName("南京汽车总站");
        messageInfoEntity6.setMessageType("接口断开");
        messageInfoEntity6.setMachineId("Asc60234");

        map.put("1", messageInfoEntity);
        map.put("2", messageInfoEntity2);
        map.put("3", messageInfoEntity3);
        map.put("4", messageInfoEntity4);
        map.put("5", messageInfoEntity5);
        map.put("6", messageInfoEntity6);
        logger.info("----初始化系统参数结束--------success-------end" + map.toString());
    }
}
