package com.harry.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.harry.model.User;

public class UserDao {

    private JDBCTemplet<User> jdbcTemplet;
    
    public void setJdbcTemplet(JDBCTemplet<User> jdbcTemplet) {
		this.jdbcTemplet = jdbcTemplet;
	}

	public int create(final User user) {
        String sql = "insert into data.person(user_name, user_pwd) values(?, ?);";
        return jdbcTemplet.create(sql, new JDBCCallback<User>() {

            @Override
            public void setParameters(PreparedStatement ps) throws SQLException {
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getUserPassword());
            }

            @Override
            public User rsToObject(ResultSet rs) throws SQLException {
                return null;
            }
        });
    }

    public int delete(final int id) {

        String sql = "UPDATE data.person SET user_isdelete = 1 WHERE user_id = ?";
        return jdbcTemplet.update(sql, new JDBCCallback<User>() {
            @Override
            public void setParameters(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
            }

            @Override
            public User rsToObject(ResultSet rs) throws SQLException {
                return null;
            }
        });
    }

    public int update(final User user) {
        String sql = "UPDATE data.person SET user_name = ?, user_pwd = ? WHERE user_id = ? AND user_isdelete = 0";
        return jdbcTemplet.update(sql, new JDBCCallback<User>() {

            @Override
            public void setParameters(PreparedStatement ps) throws SQLException {
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getUserPassword());
                ps.setInt(3, user.getUserId());
            }

            @Override
            public User rsToObject(ResultSet rs) throws SQLException {
                return null;
            }
        });
    }

    public List<User> read() {
        String sql = "SELECT * FROM data.person WHERE user_isdelete = 0";
        return jdbcTemplet.read(sql, new JDBCCallback<User>() {
            @Override
            public User rsToObject(ResultSet rs) throws SQLException {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                return user;
            }

            @Override
            public void setParameters(PreparedStatement ps) throws SQLException {
            }
        });
    }

    public User readByUserName(final String userName) {
        String sql = "SELECT * FROM data.person WHERE user_name = ? AND user_isdelete = 0";
        return jdbcTemplet.readOne(sql, new JDBCCallback<User>() {

            @Override
            public void setParameters(PreparedStatement ps) throws SQLException {
                ps.setString(1, userName);
            }

            @Override
            public User rsToObject(ResultSet rs) throws SQLException {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(userName);
                user.setUserPassword(rs.getString("user_pwd"));
                return user;
            }
        });
    }

    public User readByUserId(final int id) {
        String sql = "SELECT * FROM data.person WHERE user_id = ? AND user_isdelete = 0";
        return jdbcTemplet.readOne(sql, new JDBCCallback<User>() {

            @Override
            public void setParameters(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
            }

            @Override
            public User rsToObject(ResultSet rs) throws SQLException {
                User user = new User();
                user.setUserId(id);
                user.setUserName(rs.getString("user_name"));
                user.setUserPassword(rs.getString("user_pwd"));
                return user;
            }
        });
    }
}
