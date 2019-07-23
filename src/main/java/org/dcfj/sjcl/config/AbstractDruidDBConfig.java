package org.dcfj.sjcl.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.sql.SQLException;


@Configuration
@EnableConfigurationProperties(DruidDbProperties.class)
@Import({ DruidMonitConfig.class })
public abstract class AbstractDruidDBConfig {
	private Logger logger = LoggerFactory.getLogger(AbstractDruidDBConfig.class);

	@Resource
	private DruidDbProperties druidDbProperties;

	public DruidDataSource createDataSource(String driverClassName, String url, String username, String password) {
		if (StringUtils.isEmpty(url)) {
			throw new ApplicationContextException("Database connection pool is not configured correctly");
		}
		DruidDataSource datasource = new DruidDataSource();
		datasource.setDriverClassName(driverClassName);
		datasource.setUrl(url);
		datasource.setUsername(username);
		datasource.setPassword(password);
		datasource.setInitialSize(druidDbProperties.getInitialSize());
		datasource.setMinIdle(druidDbProperties.getMinIdle());
		datasource.setMaxActive(druidDbProperties.getMaxActive());
		datasource.setMaxWait(druidDbProperties.getMaxWait());
		datasource.setTimeBetweenEvictionRunsMillis(druidDbProperties.getTimeBetweenEvictionRunsMillis());
		datasource.setMinEvictableIdleTimeMillis(druidDbProperties.getMinEvictableIdleTimeMillis());
		datasource.setValidationQuery(druidDbProperties.getValidationQuery());
		datasource.setTestWhileIdle(druidDbProperties.isTestWhileIdle());
		datasource.setTestOnBorrow(druidDbProperties.isTestOnBorrow());
		datasource.setTestOnReturn(druidDbProperties.isTestOnReturn());
		try {
			datasource.setFilters(druidDbProperties.getFilters());
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}
		datasource.setConnectionProperties(druidDbProperties.getConnectionProperties());
		return datasource;
	}

}
