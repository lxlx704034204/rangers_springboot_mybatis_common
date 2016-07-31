package cn.rangers.develop.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 基类controller接口
 * 
 * @param <T>
 * 
 * @version 1.0
 * @since JDK1.7
 * @author Administrator
 * @company 令其小屋
 * @copyright (c) 2016 SunTime Co'Ltd Inc. All rights reserved.
 * @date 2016年7月31日 下午3:44:29
 */
@RestController
public  abstract class BaseController<T extends BaseEntity> {

	protected Logger logger;

	private String moduleName;
	
	public BaseController() {
	}
	
	public BaseController(Class<?> clazz, String moduleName) {
		super();
		this.logger = LoggerFactory.getLogger(clazz);
		this.moduleName = moduleName;
	}
	
	protected void outLog(String methodName, Object... data) {
		logger.debug(moduleName + " >>>> " + methodName + " data ===> " + JsonMapper.getInstance().toJson(data));
//		log.create(new OperationLog(moduleName, methodName, JsonMapper.getInstance().toJson(data)));
	}
}
