package cn.rangers.develop.common;

import java.util.List;

import org.apache.ibatis.session.RowBounds;


public interface BaseService <T extends BaseEntity>{

	/**
	 * 根据主键查询
	 *
	 * @param key
	 * @return
	 * 
	 * @author fuhw
	 * @date 2016年7月22日 下午1:51:19
	 */
	 public T selectByPrimaryKey(Object key);
	
	 /**
	  * 
	  * 查询单个
	  *
	  * @param t
	  * @return
	  * 
	  * @author fuhw
	  * @date 2016年7月22日 下午1:51:36
	  */
	 public T queryOne (T t);
	 
	 /**
	  * 
	  * 查询所有
	  *
	  * @return
	  * 
	  * @author fuhw
	  * @date 2016年7月22日 下午1:51:49
	  */
	 public List<T> queryAll();
	 
	 /**
	  * 
	  * 按条件查询
	  *
	  * @param example
	  * @return
	  * 
	  * @author fuhw
	  * @date 2016年7月22日 下午1:52:03
	  */
	 public List<T> queryListByWhere(Object example);
	 
	 /**
	  * 
	  * 根据example条件和RowBounds进行分页查询
	  *
	  * @param example
	  * @param rowBounds
	  * @return
	  * 
	  * @author fuhw
	  * @date 2016年7月22日 下午1:52:15
	  */
	 public List<T> selectByExampleAndRowBounds(Object example,RowBounds rowBounds);
	 
	 /**
	  * 根据实体属性和RowBounds进行分页查询 
	  *
	  * @param t
	  * @param rowBounds
	  * @return
	  * 
	  * @author fuhw
	  * @date 2016年7月22日 下午1:52:51
	  */
	 public List<T> selectByRowBounds(T t,RowBounds rowBounds);
	 
	 /**
	  * 保存一个实体，null依旧保存 
	  *
	  * @param t
	  * @return
	  * 
	  * @author fuhw
	  * @date 2016年7月22日 下午1:53:11
	  */
	 public int insert(T t);
	 
	 /**
	  * 保存一个实体,null不保存 
	  *
	  * @param t
	  * @return
	  * 
	  * @author fuhw
	  * @date 2016年7月22日 下午1:53:23
	  */
	 public int insertSelective(T t);
	 
	 
	 /**
	  * 批量插入
	  *
	  * @param list
	  * @return
	  * 
	  * @author fuhw
	  * @date 2016年7月22日 下午1:53:35
	  */
	 public int insertList(List<T> list);
	 
	 /**
	  * 
	  * 根据Example条件更新实体包含的全部属性，null值会被更新
	  *
	  * @param t
	  * @param example
	  * @return
	  * 
	  * @author fuhw
	  * @date 2016年7月22日 下午1:53:50
	  */
	 public int updateByExample(T t,Object example);
	 
	 /**
	  * 根据主键更新实体全部字段，null值会被更新 
	  *
	  * @param t
	  * @return
	  * 
	  * @author fuhw
	  * @date 2016年7月22日 下午1:54:17
	  */
	 public int updateByPrimaryKey(T t);
	 
	 /**
	  * 删除
	  * 
	  * @param t
	  * @return
	  * 
	  * @author fuhw
	  * @date 2016年7月22日 下午1:54:28
	  */
	 public int delete(T t);
	 
	 /**
	  * 根据条件删除 
	  *
	  * @param example
	  * @return
	  * 
	  * @author fuhw
	  * @date 2016年7月22日 下午1:54:49
	  */
	 public int deleteByExample(Object example);
	 
 }
