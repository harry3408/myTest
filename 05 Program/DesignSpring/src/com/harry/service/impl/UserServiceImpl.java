package com.harry.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.harry.common.Const;
import com.harry.dao.UserDao;
import com.harry.exception.ParameterException;
import com.harry.model.User;
import com.harry.service.UserService;
import com.harry.utils.StringUtil;

public class UserServiceImpl implements UserService {

	private Logger logger = Logger.getLogger(UserServiceImpl.class);

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User login(String userName, String password) throws ParameterException {
		logger.info("userName login");
		ParameterException parameterException = new ParameterException();
		if (StringUtil.isEmpty(userName)) {
			parameterException.putErrorMsg(Const.PARAM_USER_NAME, "User name is requied.");
			logger.info("User name is requied.");
		}
		if (StringUtil.isEmpty(password)) {
			parameterException.putErrorMsg(Const.PARAM_PWD, "Password is requied.");
			logger.info("Password is requied.");
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

	@Override
	public List<User> getAllUsers() {
		return userDao.read();
	}

	@Override
	public User getUserById(int id) {
		return userDao.readByUserId(id);
	}

	@Override
	public int update(User user) {
		return userDao.update(user);
	}

	@Override
	public int create(User user) {
		return userDao.create(user);
	}

	@Override
	public int delete(int id) {
		return userDao.delete(id);
	}
}
