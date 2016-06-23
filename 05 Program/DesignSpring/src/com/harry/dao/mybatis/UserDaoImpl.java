package com.harry.dao.mybatis;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.harry.dao.UserDao;
import com.harry.model.User;

public  class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {

	public final String CLASS_NAME = User.class.getName();
	public final String SQL_ID_CREATE = ".create";
	public final String SQL_ID_DELETE = ".delete";
	public final String SQL_ID_UPDATE = ".update";
	public final String SQL_ID_READ = ".read";
	public final String SQL_ID_READ_BY_USER_NAME = ".readByUserName";
	public final String SQL_ID_READ_BY_USER_ID = ".readByUserId";

	@Override
	public int create(User user) {
		return getSqlSession().insert(CLASS_NAME + SQL_ID_CREATE, user);
	}

	@Override
	public int delete(int id) {
		return getSqlSession().update(CLASS_NAME + SQL_ID_DELETE, id);
	}

	@Override
	public int update(User user) {
		return getSqlSession().update(CLASS_NAME + SQL_ID_UPDATE, user);
	}

	@Override
	public List<User> read() {
		return getSqlSession().selectList(CLASS_NAME + SQL_ID_READ);
	}

	@Override
	public User readByUserName(String userName) {
		return getSqlSession().selectOne(CLASS_NAME + SQL_ID_READ_BY_USER_NAME, userName);
	}

	@Override
	public User readByUserId(int id) {
		return getSqlSession().selectOne(CLASS_NAME + SQL_ID_READ_BY_USER_ID, id);
	}

	@Override
	public List<User> query(String sql) {
		return null;
	}

}
