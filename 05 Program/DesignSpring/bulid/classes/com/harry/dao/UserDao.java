package com.harry.dao;

import java.util.List;

import com.harry.model.User;

public interface UserDao {

    public int create(User user);

    public int delete(int id);

    public int update(User user);

    public List<User> read();

    public User readByUserName(String userName);

    public User readByUserId(int id);

    public List<User> query(String sql);
}
