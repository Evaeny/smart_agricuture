package com.smart.agriculture.manage.shardingsphere.config;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.shardingjdbc.spring.boot.common.SpringBootPropertiesConfigurationProperties;
import org.apache.shardingsphere.shardingjdbc.spring.boot.encrypt.SpringBootEncryptRuleConfigurationProperties;
import org.apache.shardingsphere.shardingjdbc.spring.boot.masterslave.SpringBootMasterSlaveRuleConfigurationProperties;
import org.apache.shardingsphere.shardingjdbc.spring.boot.shadow.SpringBootShadowRuleConfigurationProperties;
import org.apache.shardingsphere.shardingjdbc.spring.boot.sharding.SpringBootShardingRuleConfigurationProperties;
import org.apache.shardingsphere.spring.boot.datasource.DataSourcePropertiesSetterHolder;
import org.apache.shardingsphere.spring.boot.util.DataSourceUtil;
import org.apache.shardingsphere.spring.boot.util.PropertyUtil;
import org.apache.shardingsphere.underlying.common.config.inline.InlineExpressionParser;
import org.apache.shardingsphere.underlying.common.exception.ShardingSphereException;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.*;

@Configuration
@ComponentScan("org.apache.shardingsphere.spring.boot.converter")
@EnableConfigurationProperties({SpringBootShardingRuleConfigurationProperties.class,
        SpringBootMasterSlaveRuleConfigurationProperties.class,
        SpringBootEncryptRuleConfigurationProperties.class,
        SpringBootPropertiesConfigurationProperties.class,
        SpringBootShadowRuleConfigurationProperties.class})
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "manage.shardingsphere", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ManageShardingjdbcConfiguration implements EnvironmentAware {


    private final Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();

    private final String jndiName = "jndi-Name";

    @Override
    public void setEnvironment(Environment environment) {
        String prefix = "spring.shardingsphere.datasource.";
        List<String> dataSourceNames = getDataSourceNames(environment, prefix);
        for (String dataSourceName : dataSourceNames) {
            try {
                dataSourceMap.put(dataSourceName, getDataSource(environment, prefix, dataSourceName));
            } catch (final ReflectiveOperationException e) {
                throw new ShardingSphereException("Can't find datasource type !", e);
            } catch (final NamingException e) {
                throw new ShardingSphereException("Can't find JNDI datasource !", e);
            }
        }

    }


    private List<String> getDataSourceNames(final Environment environment, final String prefix) {
        StandardEnvironment standardEnvironment = (StandardEnvironment) environment;
        standardEnvironment.setIgnoreUnresolvableNestedPlaceholders(true);
        return null == standardEnvironment.getProperty(prefix + "name") ?
                new InlineExpressionParser(standardEnvironment.getProperty(prefix + "names")).splitAndEvaluate()
                : Collections.singletonList(standardEnvironment.getProperty(prefix + "name"));
    }


    private DataSource getDataSource(final Environment environment, final String prefix, final String dataSourceName) throws ReflectiveOperationException, NamingException {
        Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, prefix + dataSourceName.trim(), Map.class);
        Preconditions.checkState(!dataSourceProps.isEmpty(), "wrong datasource properties");
        if (dataSourceProps.containsKey(jndiName)) {
            return getJndiDataSource(dataSourceProps.get(jndiName).toString());
        }

        Map<String, Object> finalDataSourceMap = new HashMap<>(dataSourceProps);
        // 解密
        finalDataSourceMap.put("password", dataSourceProps.getOrDefault("password", ""));

        DataSource result = DataSourceUtil.getDataSource(dataSourceProps.get("type").toString(), finalDataSourceMap);
        DataSourcePropertiesSetterHolder.getDataSourcePropertiesSetterByType(dataSourceProps.get("type").toString()).ifPresent(dataSourcePropertiesSetter -> dataSourcePropertiesSetter.propertiesSet(environment, prefix, dataSourceName, result));
        return result;
    }

    private DataSource getJndiDataSource(final String jndiName) throws NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setResourceRef(true);
        bean.setJndiName(jndiName);
        bean.setProxyInterface(DataSource.class);
        bean.afterPropertiesSet();
        return (DataSource) bean.getObject();
    }
}
