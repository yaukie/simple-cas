package org.yaukie.inter;

import javax.servlet.ServletContext;

/**
 * 创建容器启动接口
 * @author yaukie
 *
 */
public interface WebApplicationInitializer {

	void init(ServletContext servletContext);
	
}
