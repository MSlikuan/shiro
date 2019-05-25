package com.woniu.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
	// 加载领域对象
	@Bean
	public UserRealm initRealm() {
		UserRealm realm = new UserRealm();
		return realm;
	}

	// 加载安全管理器
	@Bean
	public SecurityManager securityManager() {
		// 创建SecurityManager
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 将领域对象保存到SecurityManager对象中
		securityManager.setRealm(initRealm());
		return securityManager;
	}

	@Bean  
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		// 创建ShiroFilterFactory对象
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 注册securityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 配置不同的请求地址与处理它的过滤器
		// LinkHashMap是有序的，shiro会根据添加的顺序进行拦截,匹配到过滤器后就执行该过滤器不会在继续向下查找过滤器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会被拦截地址规则
		// anon:所有的url都可以不登陆的情况下访问
		// authc：所有url都必须认证通过才可以访问
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/images/**", "anon");
		filterChainDefinitionMap.put("/page/login.html", "anon");
		filterChainDefinitionMap.put("/login", "anon");
		// 注销过滤器 清空主体 跳转到登陆
		filterChainDefinitionMap.put("/logout", "logout");
		// 如果不满足上方所有的规则 则需要进行登录验证
		filterChainDefinitionMap.put("/**", "authc");
		// 未登录时重定向的网页地址
		shiroFilterFactoryBean.setLoginUrl("/page/login.html");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		// 返回
		return shiroFilterFactoryBean;
	}

	// Shiro扫描注解
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager());
		return advisor;
	}

	// 配置Aop
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator app = new DefaultAdvisorAutoProxyCreator();
		app.setProxyTargetClass(true);
		return app;
	}
}
