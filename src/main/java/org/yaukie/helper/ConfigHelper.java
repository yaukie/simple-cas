package org.yaukie.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigHelper {

	private static final Logger log = 	LoggerFactory.getLogger(ConfigHelper.class);
	
	private static final Properties properties = new Properties();
	
	static {
		InputStream is = null; 
		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream("sso.properties");
			if(is ==null)
			{
				throw new FileNotFoundException("sso.properties"+" file is not found");
			}
			properties.load(is);
		} catch (IOException e) {
			log.error("加载资源出错!");
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				log.error("释放资源出错!");
			}
		}
	}
	
	
	public static boolean isSso(){
		return Boolean.parseBoolean(properties.getProperty("sso"));
	}
	
	public static String getCasServerUrlPrefix(){
		return properties.getProperty("sso.cas_url");
	}
	
	public static String  getCasServerLoginUrl(){
		return properties.getProperty("sso.cas_url") +"/login";
	}
	
	public static String getServerName(){
		return properties.getProperty("sso.app_url");
	}
	
	public static String getFilterMapping(){
		return properties.getProperty("sso.filter_mapping");
	}
	
}
