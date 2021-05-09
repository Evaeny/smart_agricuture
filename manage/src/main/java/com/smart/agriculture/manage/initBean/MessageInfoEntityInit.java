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
        messageInfoEntity.setMachinename("自贡汽车总站");
        messageInfoEntity.setMessagetype("接口连接");
        messageInfoEntity.setMachineid("Asc02396");

        MessageInfoEntity messageInfoEntity2 = new MessageInfoEntity();
        messageInfoEntity2.setMachinename("自贡汽车总站");
        messageInfoEntity2.setMessagetype("接口断开");
        messageInfoEntity2.setMachineid("Asc02396");

        MessageInfoEntity messageInfoEntity3 = new MessageInfoEntity();
        messageInfoEntity3.setMachinename("武汉汽车总站");
        messageInfoEntity3.setMessagetype("接口连接");
        messageInfoEntity3.setMachineid("Asc13588");

        MessageInfoEntity messageInfoEntity4 = new MessageInfoEntity();
        messageInfoEntity4.setMachinename("武汉汽车总站");
        messageInfoEntity4.setMessagetype("接口连接");
        messageInfoEntity4.setMachineid("Asc13588");

        MessageInfoEntity messageInfoEntity5 = new MessageInfoEntity();
        messageInfoEntity5.setMachinename("南京汽车总站");
        messageInfoEntity5.setMessagetype("接口连接");
        messageInfoEntity5.setMachineid("Asc60234");

        MessageInfoEntity messageInfoEntity6 = new MessageInfoEntity();
        messageInfoEntity6.setMachinename("南京汽车总站");
        messageInfoEntity6.setMessagetype("接口断开");
        messageInfoEntity6.setMachineid("Asc60234");

        map.put("1", messageInfoEntity);
        map.put("2", messageInfoEntity2);
        map.put("3", messageInfoEntity3);
        map.put("4", messageInfoEntity4);
        map.put("5", messageInfoEntity5);
        map.put("6", messageInfoEntity6);
        logger.info("----初始化系统参数结束--------success-------end" + map.toString());
    }
}
