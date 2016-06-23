package com.harry.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.harry.exception.DBException;
import com.harry.utils.DBUtil;
//import com.mysql.jdbc.Statement;

public class JDBCTemplet<T> {
	public List<T> read(String sql, JDBCCallback<T> jdbcCallback) {
		List<T> results = new ArrayList<T>();

		boolean needClose = false;
		Connection conn = null;
		ConnectionHoder hoder = (ConnectionHoder) AppContext.getAppContext().getObject(Const.APP_CONTEXT_CONNECTHODER);
		if (hoder != null) {
			conn = hoder.getConn();
		}
		if (conn == null) {
			conn = DBUtil.getConnection();
			needClose = true;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				T object = jdbcCallback.rsToObject(rs);
				results.add(object);
			}
		} catch (Exception e) {
			throw new DBException("Internal error", e);
		} finally {
			DBUtil.close(rs, ps, null);
			if (needClose) {
				DBUtil.close(null, null, conn);
			}
		}
		return results;
	}

	public T readOne(String sql, JDBCCallback<T> jdbcCallback) {
		boolean needClose = false;
		Connection conn = null;
		ConnectionHoder hoder = (ConnectionHoder) AppContext.getAppContext().getObject(Const.APP_CONTEXT_CONNECTHODER);
		if (hoder != null) {
			conn = hoder.getConn();
		}
		if (conn == null) {
			conn = DBUtil.getConnection();
			needClose = true;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		T object = null;
		try {
			ps = conn.prepareStatement(sql);
			jdbcCallback.setParameters(ps);
			rs = ps.executeQuery();
			if (rs.next()) {
				object = jdbcCallback.rsToObject(rs);
			}
		} catch (Exception e) {
			throw new DBException("Internal error", e);
		} finally {
			DBUtil.close(rs, ps, null);
			if (needClose) {
				DBUtil.close(null, null, conn);
			}
		}
		return object;
	}

	public int update(String sql, JDBCCallback<T> jdbcCallback) {
		boolean needClose = false;
		Connection conn = null;
		ConnectionHoder hoder = (ConnectionHoder) AppContext.getAppContext().getObject(Const.APP_CONTEXT_CONNECTHODER);
		if (hoder != null) {
			conn = hoder.getConn();
		}
		if (conn == null) {
			conn = DBUtil.getConnection();
			needClose = true;
		}
		System.out.println("update Conenction = " + conn);
		PreparedStatement ps = null;
		int count = -1;
		try {
			ps = conn.prepareStatement(sql);
			jdbcCallback.setParameters(ps);
			count = ps.executeUpdate();
			return count;
		} catch (Exception e) {
			throw new DBException("Internal error", e);
		} finally {
			DBUtil.close(null, ps, null);
			if (needClose) {
				DBUtil.close(null, null, conn);
			}
		}
	}

	public int create(String sql, JDBCCallback<T> jdbcCallback) {
		boolean needClose = false;
		Connection conn = null;
		ConnectionHoder hoder = (ConnectionHoder) AppContext.getAppContext().getObject(Const.APP_CONTEXT_CONNECTHODER);
		if (hoder != null) {
			conn = hoder.getConn();
		}
		if (conn == null) {
			conn = DBUtil.getConnection();
			needClose = true;
		}
		System.out.println("create Conenction = " + conn);
		PreparedStatement ps = null;
		ResultSet rs = null;
		int generateKey = -1;
		try {
			// TODO
			// ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			jdbcCallback.setParameters(ps);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				generateKey = rs.getInt(1);
			}
		} catch (Exception e) {
			throw new DBException("Internal error", e);
		} finally {
			DBUtil.close(rs, ps, null);
			if (needClose) {
				DBUtil.close(null, null, conn);
			}
		}
		return generateKey;
	}
}
