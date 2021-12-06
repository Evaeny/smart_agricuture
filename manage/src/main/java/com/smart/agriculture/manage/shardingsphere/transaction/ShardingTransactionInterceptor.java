package com.smart.agriculture.manage.shardingsphere.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * Sharding 事务管理器
 */
@Component
public class ShardingTransactionInterceptor extends TransactionInterceptor {

    public ShardingTransactionInterceptor() {
        Properties properties = new Properties();
        properties.put("start*", "PROPAGATION_REQUIRED,-Exception");
        properties.put("insert*", "PROPAGATION_REQUIRED,-Exception");
        properties.put("add*", "PROPAGATION_REQUIRED,-Exception");
        properties.put("save*", "PROPAGATION_REQUIRED,-Exception");
        properties.put("modify*", "PROPAGATION_REQUIRED,-Exception");
        properties.put("update*", "PROPAGATION_REQUIRED,-Exception");
        properties.put("delete*", "PROPAGATION_REQUIRED,-Exception");
        properties.put("del*", "PROPAGATION_REQUIRED,-Exception");
        properties.put("get*", "PROPAGATION_SUPPORTS,readOnly,-Exception");
        properties.put("count*", "PROPAGATION_SUPPORTS,readOnly,-Exception");
        properties.put("find*", "PROPAGATION_SUPPORTS,readOnly,-Exception");
        properties.put("list*", "PROPAGATION_SUPPORTS,readOnly,-Exception");
        properties.put("select*", "PROPAGATION_SUPPORTS,readOnly,-Exception");
        this.setTransactionAttributes(properties);
    }

}
