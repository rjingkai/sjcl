package org.dcfj.sjcl.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.alibaba.druid.util.StringUtils;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.Resource;


@EnableConfigurationProperties(DruidDbProperties.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DruidMonitConfig {
	@Resource
	private DruidDbProperties druidDbProperties;

	@Bean
	public ServletRegistrationBean<StatViewServlet> druidServlet() {
		ServletRegistrationBean<StatViewServlet> reg = new ServletRegistrationBean<StatViewServlet>();
		reg.setServlet(new StatViewServlet());
		reg.addUrlMappings("/druid/*");

		if (!StringUtils.isEmpty(druidDbProperties.getAllow())) {
			reg.addInitParameter("allow", druidDbProperties.getAllow()); // 白名单
		}
		if (!StringUtils.isEmpty(druidDbProperties.getDeny())) {
			reg.addInitParameter("deny", druidDbProperties.getDeny()); // 黑名单
		}
		reg.addInitParameter("loginUsername", druidDbProperties.getUsername());
		reg.addInitParameter("loginPassword", druidDbProperties.getPassword());
		return reg;
	}

	@Bean
	public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
		FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>();
		filterRegistrationBean.setFilter(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}

	/**
	 * 监听Spring 1.定义拦截器 2.定义切入点 3.定义通知类
	 *
	 * @return
	 */
	@Bean
	public DruidStatInterceptor druidStatInterceptor() {
		return new DruidStatInterceptor();
	}

	@Bean
	public JdkRegexpMethodPointcut druidStatPointcut() {
		JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
		String patterns = "org.dcfj.sjcl.*";
		druidStatPointcut.setPatterns(patterns);
		return druidStatPointcut;
	}

	@Bean
	public Advisor druidStatAdvisor() {
		return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
	}
}
