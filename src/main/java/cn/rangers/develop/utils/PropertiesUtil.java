package cn.rangers.develop.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import cn.rangers.develop.common.SystemException;

/**
 * 配置文件读取的工具类
 * 
 * @version 1.3
 * @since JDK1.7
 * @author fuhw
 * @company 上海浦信科技
 * @copyright (c) 2016 Puxin technology. All rights reserved.
 * @date 2016年7月29日 上午10:38:16
 */
public class PropertiesUtil {
    
    private static PropertiesUtil instatnce;
    
    private Properties property = new Properties();
    
    private PropertiesUtil(){
        loadFile();
    }
    
    private void loadFile(){
        try {
            InputStream in = PropertiesUtil.class.getResourceAsStream("/project.properties");
            property.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 根据key获取配置文件中对应的值 
     *
     * @param key
     * @return
     * 
     * @author fuhw
     * @date 2016年7月29日 上午10:43:45
     */
    public String getValue(String key){
        return property.getProperty(key);
    }


    /**
     * 单例返回一个配置文件实例 
     *
     * @return
     * 
     * @author fuhw
     * @date 2016年7月29日 上午10:45:05
     */
    public static PropertiesUtil getInstatnce() {
        if(instatnce == null){
            instatnce = new PropertiesUtil();
        }
        return instatnce;
    }
   
    /**
     * 判断当前的开发模式是否为开发模式
     * 
     * @return
     * 
     * @author fuhw
     * @date 2016年7月29日 上午10:48:04
     */
    public static boolean isDevelopModel(){
        String model = PropertiesUtil.getInstatnce().getValue("project.model");
        
        if(StringUtils.isBlank(model)){
        	new SystemException("********project.properties配置出错：没有找到project.model").printStackTrace();
        	
        	System.exit(1);
        }
        
        return !model.toLowerCase().startsWith("product");
    }
    
    /**
     * 获取日志配置文件的路径
     * 
     * @return
     * 
     * @author fuhw
     * @date 2016年7月29日 上午10:52:43
     */
    public static String getLogCfgPath(){
        return PropertiesUtil.getInstatnce().getValue(isDevelopModel() ? "log4j.path.dev" : "log4j.path.pro");
    }
    
    /**
     * 获取DB连接配置信息
     * （根据开发模式切换不同 的配置文件）
     * @return
     * 
     * @author fuhw
     * @date 2016年7月29日 上午10:53:08
     */
    public static String getDBConfig(){
    	boolean isDev = isDevelopModel();
    	String prefix = "mysql";
    	
    	/**生产模式*/
    	if(!isDev){
    		String versionStr = PropertiesUtil.getInstatnce().getValue("project.model");
    		
    		/**根据生产模式，获取相应的db配置文件*/
    		String[] version = versionStr.split("_");
    		
    		if(version.length > 1){
    			prefix += "."+version[1];
    		}else{
    			prefix += "."+ "prod";
    		}
    	}
    	/** 
    	 * db.mysql.pc=电脑端
		   db.mysql.ph=手机端
		   db.mysql.wx=微信端
    	 * 等类似的
    	 */
    	String cfg = PropertiesUtil.getInstatnce().getValue("db."+prefix);
    	
    	if(StringUtils.isNotBlank(cfg)){
    		return cfg;
    	}else{
    		return "dbconfig/"+prefix+".properties";
    	}
    }
    
//    public static void main(String[] args) {
//        String model = PropertiesUtil.getInstatnce().getValue("project.model");
//        System.out.println(model);
//        String dbConfig = PropertiesUtil.getDBConfig();
//        System.out.println(dbConfig);
//        
//    }

}
