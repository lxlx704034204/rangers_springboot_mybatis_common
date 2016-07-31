
package cn.rangers.develop.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class DataController<T extends BaseEntity , S extends BaseService<T>> extends BaseController<T>{

	@Autowired
	public S service;
	
	public DataController() {
		super();
	}

	/**
	 * 初始化日志
	 * @param clazz
	 * @param moduleName
	 */
	public DataController(Class<?> clazz, String moduleName) {
		super(clazz, moduleName);
	}

	/**
	 * 根据id查询单个对象
	 * 
	 * @param id
	 * @return
	 * 
	 * @author fuhw
	 * @date 2016年7月22日 下午1:49:01
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseResult get(@PathVariable Integer id) {
//		this.outLog("查询", id);
		ResponseResult result = ResponseResult.getInstance();
		try {
			T data = service.selectByPrimaryKey(id);
			if(data == null) {
				result.setSuccess(false);
				result.setMsg("没有查询到相关数据！");
			}else {
				result.setData(data);
				result.setSuccess(true);
			}
			
		} catch (Exception e) {
			result.convertException(e);
		}
		return result;
	}
	/**
	 * 分页列表查询 
	 *
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * 
	 * @author fuhw
	 * @date 2016年7月22日 下午2:23:06
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPage", method = RequestMethod.GET)
	public ResponseResult queryPage(@RequestParam(value="pageNum", required=false, defaultValue="1")int pageNum,  
            @RequestParam(value="pageSize", required=false, defaultValue="10")int pageSize  ) {
		this.outLog("分页查询", pageNum,pageSize);
		ResponseResult result = ResponseResult.getInstance();
		RowBounds rowBounds = new RowBounds(pageNum, pageSize);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			
			List<T> datas = service.selectByExampleAndRowBounds(map , rowBounds);
			if(datas == null || datas.size() <= 0) {
				result.setSuccess(false);
				result.setMsg("没有查询到相关数据！");
			}else {
				result.setData(datas);
				result.setSuccess(true);
			}
			
		} catch (Exception e) {
			result.convertException(e);
		}
		return result;
	}
	/**
	 * 新增操作
	 * 
	 * @param entity
	 * @param errors
	 * @return
	 * 
	 * @author fuhw
	 * @date 2016年7月22日 下午2:40:32
	 */
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseResult create(@RequestBody T entity,BindingResult errors) {
		this.outLog("新增", entity);
		ResponseResult result = new ResponseResult();
		try {
			if (errors.hasErrors()) {
				result.convertErrors(errors.getFieldErrors());
			} else {
				result.setSuccess(service.insert(entity) == 0 ? false : true);
				result.setData(entity);
			}
		} catch (Exception e) {
			result.convertException(e);
		}
		return result;
	}
	/**
	 * 更新操作 
	 *
	 * @param entity
	 * @param errors
	 * @return
	 * 
	 * @author fuhw
	 * @date 2016年7月22日 下午2:43:27
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseResult update(@RequestBody T entity,BindingResult errors) {
		this.outLog("更新", entity);
		ResponseResult result = new ResponseResult();
		try {
			if (errors.hasErrors()) {
				result.convertErrors(errors.getFieldErrors());
			} else {
				result.setSuccess(service.updateByPrimaryKey(entity) == 0 ? false : true);
				result.setData(entity);
			}
		} catch (Exception e) {
			result.convertException(e);
		}
		return result;
	}
	
	/**
	 * 删除操作
	 *
	 * @param entity
	 * @param errors
	 * @return
	 * 
	 * @author fuhw
	 * @date 2016年7月22日 下午2:43:39
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseResult delete(@RequestBody T entity,BindingResult errors) {
		this.outLog("删除", entity);
		ResponseResult result = new ResponseResult();
		try {
			if (errors.hasErrors()) {
				result.convertErrors(errors.getFieldErrors());
			} else {
				result.setSuccess(service.delete(entity) == 0 ? false : true);
				result.setData(entity);
			}
		} catch (Exception e) {
			result.convertException(e);
		}
		return result;
	}
}
