package com.itwill.freshmart.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import oracle.jdbc.OracleDriver;

public enum FreshmartDao {
	INSTANCE;
	
	FreshmartDao() {
        try {
            
        	DriverManager.registerDriver(new OracleDriver());
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
    private void closeResources(Connection conn, Statement stmt) {
        closeResources(conn, stmt, null);
    }
    
    

}
