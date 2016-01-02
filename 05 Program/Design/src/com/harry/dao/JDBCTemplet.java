package com.harry.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.harry.exception.DBException;
import com.harry.utils.DBUtil;
import com.mysql.jdbc.Statement;

public class JDBCTemplet<T> {
    public List<T> read(String sql, JDBCCallback<T> jdbcCallback) {
        List<T> results = new ArrayList<T>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                T object = jdbcCallback.rsToObject(rs);
                results.add(object);
            }
        } catch (Exception e) {
            throw new DBException("Internal error", e);
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return results;
    }

    public T readOne(String sql, JDBCCallback<T> jdbcCallback) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        T object = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            jdbcCallback.setParameters(ps);
            rs = ps.executeQuery();
            if (rs.next()) {
                object = jdbcCallback.rsToObject(rs);
            }
        } catch (Exception e) {
            throw new DBException("Internal error", e);
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return object;
    }

    public int update(String sql, JDBCCallback<T> jdbcCallback) {
        Connection conn = null;
        PreparedStatement ps = null;
        int count = -1;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            jdbcCallback.setParameters(ps);
            count = ps.executeUpdate();
            return count;
        } catch (Exception e) {
            throw new DBException("Internal error", e);
        } finally {
            DBUtil.close(null, ps, conn);
        }
    }

    public int create(String sql, JDBCCallback<T> jdbcCallback) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int generateKey = -1;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            jdbcCallback.setParameters(ps);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generateKey = rs.getInt(1);
            }
        } catch (Exception e) {
            throw new DBException("Internal error", e);
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return generateKey;
    }
}
