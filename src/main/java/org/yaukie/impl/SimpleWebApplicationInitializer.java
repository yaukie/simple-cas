package org.yaukie.impl;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.yaukie.helper.ConfigHelper;
import org.yaukie.inter.WebApplicationInitializer;

public class SimpleWebApplicationInitializer implements WebApplicationInitializer {

	public void init(Logger logger ,ServletContext servletContext) {
			if(logger.isDebugEnabled())
			{
				logger.debug("SimpleWebApplicationInitializer init starts ....");
			}
		   if (ConfigHelper.isSso()) {
	            String casServerUrlPrefix = ConfigHelper.getCasServerUrlPrefix();
	            String casServerLoginUrl = ConfigHelper.getCasServerLoginUrl();
	            String serverName = ConfigHelper.getServerName();
	            String filterMapping = ConfigHelper.getFilterMapping();

	        	if(logger.isDebugEnabled())
				{
					logger.debug("SimpleWebApplicationInitializer gets casServerUrlPrefix ["+casServerUrlPrefix+"],casServerLoginUrl ["
							+casServerLoginUrl+"],serverName ["+serverName+"],filterMapping ["+filterMapping+"]");
				}
	        	/** 用于实现单点登出功能  可选 **/
	            servletContext.addListener(SingleSignOutHttpSessionListener.class);

	            /**该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前 可选*/
	            FilterRegistration.Dynamic singleSignOutFilter = servletContext.addFilter("SingleSignOutFilter", SingleSignOutFilter.class);
	            singleSignOutFilter.setInitParameter("casServerUrlPrefix", casServerUrlPrefix);
	            singleSignOutFilter.addMappingForUrlPatterns(null, false, filterMapping);

	            /**该过滤器负责用户的认证工作，必须 */
	            FilterRegistration.Dynamic authenticationFilter = servletContext.addFilter("AuthenticationFilter", AuthenticationFilter.class);
	            authenticationFilter.setInitParameter("casServerLoginUrl", casServerLoginUrl);
	            authenticationFilter.setInitParameter("serverName", serverName);
	            authenticationFilter.addMappingForUrlPatterns(null, false, filterMapping);

	            /**该过滤器负责对Ticket的校验工作，必须**/
	            FilterRegistration.Dynamic ticketValidationFilter = servletContext.addFilter("TicketValidationFilter", Cas20ProxyReceivingTicketValidationFilter.class);
	            ticketValidationFilter.setInitParameter("casServerUrlPrefix", casServerUrlPrefix);
	            ticketValidationFilter.setInitParameter("serverName", ConfigHelper.getServerName());
	            ticketValidationFilter.addMappingForUrlPatterns(null, false, filterMapping);

	            /**该过滤器对HttpServletRequest请求包装， 可通过HttpServletRequest的getRemoteUser()方法获得登录用户的登录名，可选*/
	            FilterRegistration.Dynamic requestWrapperFilter = servletContext.addFilter("RequestWrapperFilter", HttpServletRequestWrapperFilter.class);
	            requestWrapperFilter.addMappingForUrlPatterns(null, false, filterMapping);
	            
	            /***该过滤器使得可以通过org.jasig.cas.client.util.AssertionHolder来获取用户的登录名。
         			比如AssertionHolder.getAssertion().getPrincipal().getName()。 
        			 这个类把Assertion信息放在ThreadLocal变量中，这样应用程序不在web层也能够获取到当前登录信息 */
	            FilterRegistration.Dynamic assertionThreadLocalFilter = servletContext.addFilter("AssertionThreadLocalFilter", AssertionThreadLocalFilter.class);
	            assertionThreadLocalFilter.addMappingForUrlPatterns(null, false, filterMapping);
	        }
		   
			if(logger.isDebugEnabled())
			{
				logger.debug("SimpleWebApplicationInitializer init ends  ....");
			}

	}

}
