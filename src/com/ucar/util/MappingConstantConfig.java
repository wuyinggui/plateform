package com.ucar.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;






import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
/**
 * @Package com.sinosoft.common.constant
 * @ClassName: MappingConstantConfig
 * @Description: 将系统常量对应配置读入，并转换为Map类型对象，放到内存中
 * @Version V1.0
 */
public class MappingConstantConfig {
	
	private static final Logger LOGGER = LogManager.getLogger(MappingConstantConfig.class);

	private static Map<String, String> map = new HashMap<String, String>();
	
	private static final String propertiesName = "config.properties";
	
	static{
		LOGGER.debug("读取系统配置名称的文件：{}", propertiesName);
		try {
			Map<String, String> rstMap = PropertiesUtils.prop2Map(propertiesName);
			Set<String> set = rstMap.keySet();
			Iterator<String> it = set.iterator();
			while(it.hasNext()){
				String key = it.next();
				map.put(key, rstMap.get(key));
			}
		} catch(Exception e) {
			LOGGER.error("读取系统常量对应配置文件：{}，出现异常: {}", propertiesName, e);
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private MappingConstantConfig(){}
	
	public static String getValue(String key){
		return map.get(key);
	}
	public static int getIntValue(String key){
		return Integer.parseInt(map.get(key));
	}
}
