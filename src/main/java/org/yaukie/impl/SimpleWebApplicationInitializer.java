package org.yaukie.impl;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.yaukie.helper.ConfigHelper;
import org.yaukie.inter.WebApplicationInitializer;

public class SimpleWebApplicationInitializer implements WebApplicationInitializer {

	public void init(ServletContext servletContext) {
		   if (ConfigHelper.isSso()) {
	            String casServerUrlPrefix = ConfigHelper.getCasServerUrlPrefix();
	            String casServerLoginUrl = ConfigHelper.getCasServerLoginUrl();
	            String serverName = ConfigHelper.getServerName();
	            String filterMapping = ConfigHelper.getFilterMapping();

	            servletContext.addListener(SingleSignOutHttpSessionListener.class);

	            
	            FilterRegistration.Dynamic singleSignOutFilter = servletContext.addFilter("SingleSignOutFilter", SingleSignOutFilter.class);
	            singleSignOutFilter.addMappingForUrlPatterns(null, false, filterMapping);

	            FilterRegistration.Dynamic authenticationFilter = servletContext.addFilter("AuthenticationFilter", AuthenticationFilter.class);
	            authenticationFilter.setInitParameter("casServerLoginUrl", casServerLoginUrl);
	            authenticationFilter.setInitParameter("serverName", serverName);
	            authenticationFilter.addMappingForUrlPatterns(null, false, filterMapping);

	            FilterRegistration.Dynamic ticketValidationFilter = servletContext.addFilter("TicketValidationFilter", Cas20ProxyReceivingTicketValidationFilter.class);
	            ticketValidationFilter.setInitParameter("casServerUrlPrefix", casServerUrlPrefix);
	            ticketValidationFilter.setInitParameter("serverName", ConfigHelper.getServerName());
	            ticketValidationFilter.addMappingForUrlPatterns(null, false, filterMapping);

	            FilterRegistration.Dynamic requestWrapperFilter = servletContext.addFilter("RequestWrapperFilter", HttpServletRequestWrapperFilter.class);
	            requestWrapperFilter.addMappingForUrlPatterns(null, false, filterMapping);

	            FilterRegistration.Dynamic assertionThreadLocalFilter = servletContext.addFilter("AssertionThreadLocalFilter", AssertionThreadLocalFilter.class);
	            assertionThreadLocalFilter.addMappingForUrlPatterns(null, false, filterMapping);
	        }

	}

}
