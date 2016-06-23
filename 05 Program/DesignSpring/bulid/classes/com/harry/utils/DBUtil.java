package com.harry.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.harry.exception.DBException;

public class DBUtil {
    private static final String DBDRIVER = PropertyUtil.getProperties("DBDRIVER");
    private static final String DBURL = PropertyUtil.getProperties("DBURL");
    private static final String DBUSER = PropertyUtil.getProperties("DBUSER");
    private static final String DBPASSWORD = PropertyUtil.getProperties("DBPASSWORD");

    public static Connection getConnection() throws DBException {
        Connection conn = null;
        try {
            Class.forName(DBDRIVER);
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
        } catch (Exception e) {
            throw new DBException("Internal error", e);
        }
        return conn;
    }

    public static void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
