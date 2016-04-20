package com.harry.dao.impl;

import java.util.List;

import com.harry.common.JDBCCallback;
import com.harry.common.JDBCTemplet;
import com.harry.dao.UserDao;
import com.harry.model.User;

<<<<<<< HEAD:05 Program/Design/src/com/harry/dao/UserDao.java
public interface UserDao {

    public int create(User user);
=======
public class UserDaoImpl implements UserDao {

    private JDBCTemplet<User> jdbcTemplet;

    public void setJdbcTemplet(JDBCTemplet<User> jdbcTemplet) {
        this.jdbcTemplet = jdbcTemplet;
    }

    @Override
    public int create(final User user) {
        String sql = "insert into data.person(user_name, user_pwd) values(?, ?);";
        return jdbcTemplet.create(sql, new JDBCCallback<User>() {
>>>>>>> 493aacda90640c7493e10edeb90a524c29c67dc5:05 Program/Design/src/com/harry/dao/impl/UserDaoImpl.java

    public int delete(int id);

    public int update(User user);

<<<<<<< HEAD:05 Program/Design/src/com/harry/dao/UserDao.java
    public List<User> read();

    public User readByUserName(String userName);

    public User readByUserId(int id);
=======
    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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
>>>>>>> 493aacda90640c7493e10edeb90a524c29c67dc5:05 Program/Design/src/com/harry/dao/impl/UserDaoImpl.java
}
