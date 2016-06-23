package com.harry.service;

import java.util.List;

import com.harry.exception.ParameterException;
import com.harry.model.User;

public interface UserService {
    public User login(String userName, String password) throws ParameterException;

    public List<User> getAllUsers();

    public User getUserById(int id);

    public int update(User user);

    public int create(User user);

    public int delete(int id);
}
