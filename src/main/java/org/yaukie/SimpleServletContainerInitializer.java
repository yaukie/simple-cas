package org.yaukie;

import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaukie.inter.WebApplicationInitializer;

@HandlesTypes(WebApplicationInitializer.class)
public class SimpleServletContainerInitializer implements ServletContainerInitializer{

    private static final Logger logger = LoggerFactory.getLogger(SimpleServletContainerInitializer.class);

	
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		
		for(Class<?> webApplicationInitializer : c ){
			try {
				WebApplicationInitializer tmp  = (WebApplicationInitializer) webApplicationInitializer.newInstance();
				tmp.init(ctx);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
	}

}
