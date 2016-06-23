package com.harry.jdbctemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionDefinition;

import com.harry.service.UserService;

public class JDBCT {
    private JdbcTemplate jdbcTemplate;
    String sqlURL = "jdbc:mysql://localhost:3306/data?useUnicode=true&characterEncoding=utf8";
    String sqlUsername = "root";
    String sqlPassword = "";
    String sqlDriver = "com.mysql.jdbc.Driver";

    @Before
    public void setup() {

        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(sqlURL, sqlUsername, sqlPassword);
        driverManagerDataSource.setDriverClassName(sqlDriver);

        jdbcTemplate = new JdbcTemplate(driverManagerDataSource);
        System.out.println("Executed setup function...");
    }

    @Test
    public void testPpreparedStatement1() {
        int count = jdbcTemplate.execute(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("select count(*) from person");
            }
        }, new PreparedStatementCallback<Integer>() {
            @Override
            public Integer doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
                pstmt.execute();
                ResultSet rs = pstmt.getResultSet();
                rs.next();
                return rs.getInt(1);
            }
        });
        System.out.println("Executed testPpreparedStatement1 function...");
        Assert.assertEquals(42, count);
    }

    @Test
    public void testPreparedStatement2() {
        String insertSql = "insert into person(user_name, user_pwd) values (?, ?)";
        int count = jdbcTemplate.update(insertSql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setObject(1, "name4");
                pstmt.setObject(2, "name4_PWD");
            }
        });
        Assert.assertEquals(1, count);
        String deleteSql = "delete from person where user_name=?";
        count = jdbcTemplate.update(deleteSql, new Object[] { "name4" });
        System.out.println("Executed testPreparedStatement2 function...");
        Assert.assertEquals(1, count);
    }

    @Test
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void testResultSet1() {
        jdbcTemplate.batchUpdate("", new BatchPreparedStatementSetter() {
            
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, "");
                ps.setBoolean(2, Boolean.TRUE); 
            }
            
            @Override
            public int getBatchSize() {
                
                return 0;
            }
        });
        
        
        
        jdbcTemplate.execute(new PreparedStatementCreator() {
            
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                // TODO Auto-generated method stub
                return con.prepareStatement("");
            }
        }, new PreparedStatementCallback<UserService>() {

            @Override
            public UserService doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ResultSet rs = ps.getResultSet();
                return null;
            }
            
        });
        
        
        
        
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(sqlURL, sqlUsername, sqlPassword);
        driverManagerDataSource.setDriverClassName(sqlDriver);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(driverManagerDataSource);
        Object[] os = new Object[]{};
        SqlParameterSource[] sqlParameterSource = new SqlParameterSourceUtils().createBatch(os);
        namedParameterJdbcTemplate.batchUpdate("", sqlParameterSource);
        
        String listSql = "select * from person";
        List result = jdbcTemplate.query(listSql, new RowMapper<Map>() {
            @Override
            public Map mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map row = new HashMap();
                row.put(rs.getInt("user_id"), rs.getString("user_name"));
                return row;
            }
        });
        
        Assert.assertEquals(42, result.size());
    }

    public static void main(String[] args) {
        TransactionDefinition s;
    }
}
