package com.harry.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.harry.dao.UserDao;
import com.harry.exception.DBException;
import com.harry.model.User;

public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(final User user) {
        KeyHolder keyHoder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql = "insert into data.person(user_name, user_pwd) values(?, ?);";
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getUserPassword());
                return ps;
            }
        }, keyHoder);
        return keyHoder.getKey().intValue();
    }

    @Override
    public int delete(final int id) {
        String sql = "UPDATE data.person SET user_isdelete = 1 WHERE user_id = " + id;
        return jdbcTemplate.update(sql);
    }

    @Override
    public int update(final User user) {
        return jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                String sql = "UPDATE data.person SET user_name = ?, user_pwd = ? WHERE user_id = ? AND user_isdelete = 0";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getUserPassword());
                ps.setInt(3, user.getUserId());
                return ps;
            }
        });
    }

    @Override
    public List<User> read() {
        String sql = "SELECT * FROM data.person WHERE user_isdelete = 0";
        return query(sql);
    }

    @Override
    public User readByUserName(final String userName) {
        String sql = "SELECT * FROM data.person WHERE user_name = '" + userName + "' AND user_isdelete = 0";
        List<User> users = query(sql);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public User readByUserId(final int id) {
        String sql = "SELECT * FROM data.person WHERE user_id = " + id + " AND user_isdelete = 0";
        List<User> users = query(sql);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public List<User> query(String sql) {
        List<User> userList = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rsToUser(rs);
            }
        });
        if (userList == null) {
            userList = new ArrayList<User>();
        }
        return userList;
    }

    private User rsToUser(ResultSet rs) {
        User user = new User();
        try {
            user.setUserId(rs.getInt("user_id"));
            user.setUserName(rs.getString("user_name"));
            user.setUserPassword(rs.getString("user_pwd"));
        } catch (SQLException e) {
            throw new DBException("Internal error", e);
        }
        return user;
    }
}