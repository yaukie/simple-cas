package org.yaukie;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

import org.apache.log4j.Logger;
import org.yaukie.inter.WebApplicationInitializer;

/**
 * 实现web接口启动类
 * tomcat 容器启动入口
 * @author yaukie
 */
@HandlesTypes(WebApplicationInitializer.class)
public class SimpleServletContainerInitializer implements ServletContainerInitializer{

	private static Logger logger = Logger.getLogger(SimpleServletContainerInitializer.class);

	
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		if(logger.isDebugEnabled())
		{
			logger.debug("SimpleServletContainerInitializer init...");
		}
		for(Class<?> webApplicationInitializer : c ){
			try {
				WebApplicationInitializer tmp  = (WebApplicationInitializer) webApplicationInitializer.newInstance();
				tmp.init(logger,ctx);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if(logger.isDebugEnabled())
		{
			logger.debug("SimpleServletContainerInitializer init end ...");
		}
	}

}
