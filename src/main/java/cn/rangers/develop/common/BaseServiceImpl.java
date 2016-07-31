package cn.rangers.develop.common;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceImpl<T extends BaseEntity , DAO extends BaseMapper<T>> implements BaseService<T>{

	@Autowired
	protected DAO dao;

	@Override
	public T selectByPrimaryKey(Object key) {
		return dao.selectByPrimaryKey(key);
		
	}

	@Override
	public T queryOne(T t) {
		return dao.selectOne(t);
		
	}

	@Override
	public List<T> queryAll() {
		return dao.selectAll();
		
	}

	@Override
	public List<T> queryListByWhere(Object example) {
		return dao.selectByExample(example);
		
	}

	@Override
	public List<T> selectByExampleAndRowBounds(Object example, RowBounds rowBounds) {
		return dao.selectByExampleAndRowBounds(example, rowBounds);
		
	}

	@Override
	public List<T> selectByRowBounds(T t, RowBounds rowBounds) {
		return dao.selectByRowBounds(t, rowBounds);
		
	}

	@Override
	public int insertSelective(T t) {
		return dao.insertSelective(t);
		
	}

	@Override
	public int insert(T t) {
		return dao.insert(t);
		
	}

	@Override
	public int insertList(List<T> list) {
		return dao.insertList(list);
		
	}

	@Override
	public int updateByExample(T t, Object example) {
		return dao.updateByExample(t, example);
		
	}

	@Override
	public int updateByPrimaryKey(T t) {
		return dao.updateByPrimaryKey(t);
		
	}

	@Override
	public int delete(T t) {
		return dao.delete(t);
		
	}

	@Override
	public int deleteByExample(Object example) {
		return dao.deleteByExample(example);
	}

	
	
}
