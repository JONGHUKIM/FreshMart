package com.itwill.freshmart.controller;

import static com.itwill.freshmart.model.Freshmart.Entity.TBL_FRESHMART;
import static com.itwill.freshmart.model.Freshmart.Entity.COL_ID;
import static com.itwill.freshmart.model.Freshmart.Entity.COL_TYPE_ID;
import static com.itwill.freshmart.model.Freshmart.Entity.COL_FOOD_NAME;
import static com.itwill.freshmart.model.Freshmart.Entity.COL_EXPIRATION_DATE;
import static com.itwill.freshmart.model.Freshmart.Entity.COL_STORAGE;
import static com.itwill.freshmart.model.Freshmart.Entity.COL_FOOD_QUANTITY;
import static com.itwill.freshmart.model.Freshmart.Entity.COL_IMG;
import static com.itwill.jdbcOracle.OracleJdbc.USER;
import static com.itwill.jdbcOracle.OracleJdbc.URL;
import static com.itwill.jdbcOracle.OracleJdbc.PASSWORD;



import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.itwill.freshmart.model.Freshmart;
import com.itwill.freshmart.model.Freshmart.FreshmartBuilder;

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
    
    private Freshmart getFreshmartFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt(COL_ID);
        int typeid = rs.getInt(COL_TYPE_ID);
        String foodname = rs.getString(COL_FOOD_NAME);
        Date expirationdate = rs.getDate(COL_EXPIRATION_DATE);
        String storage = rs.getString(COL_STORAGE);
        int foodquantity = rs.getInt(COL_FOOD_QUANTITY);
        String img = rs.getString(COL_IMG);
        
        return Freshmart.builder()
        		.id(id)
        		.typeid(typeid)
        		.foodname(foodname)
        		.expirationdate(expirationdate)
        		.storage(storage)
        		.foodquantity(foodquantity)
        		.img(img)
        		.build();
            
    }
    
    

}
