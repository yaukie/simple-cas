package org.yaukie.inter;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;


/**
 * 创建容器启动接口
 * @author yaukie
 */
public interface WebApplicationInitializer {

	void init(Logger logger  ,ServletContext servletContext);
	
}
