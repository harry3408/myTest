package com.harry.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface JDBCCallback<T> {

    public T rsToObject(ResultSet rs) throws SQLException;

    public void setParameters(PreparedStatement ps) throws SQLException;
}
