package com.harry.service;

import java.util.List;

import com.harry.common.Const;
import com.harry.dao.UserDao;
import com.harry.exception.ParameterException;
import com.harry.model.User;
import com.harry.utils.StringUtil;

public class UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public User login(String userName, String password) throws ParameterException {
		ParameterException parameterException = new ParameterException();
		if (StringUtil.isEmpty(userName)) {
			parameterException.putErrorMsg(Const.PARAM_USER_NAME, "User name is requied.");
		}
		if (StringUtil.isEmpty(password)) {
			parameterException.putErrorMsg(Const.PARAM_PWD, "Password is requied.");
		}
		if (parameterException.hasErrorMsg()) {
			throw parameterException;
		}

		User user = userDao.readByUserName(userName);
		if (user != null && password.equals(user.getUserPassword())) {
			user.setUserPassword(null);
			return user;
		}
		return null;
	}

	public List<User> getAllUsers() {
		return userDao.read();
	}

	public User getUserById(int id) {
		return userDao.readByUserId(id);
	}

	public int update(User user) {
		return userDao.update(user);
	}

	public int create(User user) {
		return userDao.create(user);
	}

	public int delete(int id) {
		return userDao.delete(id);
	}
}
