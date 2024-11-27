package com.itwill.freshmart.controller;

import static com.itwill.freshmart.model.Freshmart.Entity.COL_EXPIRATION_DATE;
import static com.itwill.freshmart.model.Freshmart.Entity.COL_FOOD_NAME;
import static com.itwill.freshmart.model.Freshmart.Entity.COL_FOOD_QUANTITY;
import static com.itwill.freshmart.model.Freshmart.Entity.COL_ID;
import static com.itwill.freshmart.model.Freshmart.Entity.COL_IMG;
import static com.itwill.freshmart.model.Freshmart.Entity.COL_STORAGE;
import static com.itwill.freshmart.model.Freshmart.Entity.COL_TYPE_ID;
import static com.itwill.freshmart.model.Freshmart.Entity.TBL_FRESHMART;
import static com.itwill.jdbcOracle.OracleJdbc.PASSWORD;
import static com.itwill.jdbcOracle.OracleJdbc.URL;
import static com.itwill.jdbcOracle.OracleJdbc.USER;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.itwill.freshmart.model.Freshmart;

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
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
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

		return Freshmart.builder().id(id).typeid(typeid).foodname(foodname).expirationdate(expirationdate)
				.storage(storage).foodquantity(foodquantity).img(img).build();

	}
	
	public List<String> getFoodCategoryList() {
	    List<String> foodCategories = new ArrayList<>();
	    Connection conn = null;
	    Statement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DriverManager.getConnection(URL, USER, PASSWORD);
	        stmt = conn.createStatement();
	        String sql = "SELECT CATEGORY FROM FOOD_CATEGORY";
	        rs = stmt.executeQuery(sql);

	        while (rs.next()) {
	            foodCategories.add(rs.getString("CATEGORY"));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeResources(conn, stmt, rs);
	    }

	    return foodCategories;
	}
	
	public int getFoodCategoryIdByName(String categoryName) {
	    int typeid = -1;
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DriverManager.getConnection(URL, USER, PASSWORD);
	        String sql = "SELECT ID FROM FOOD_CATEGORY WHERE CATEGORY = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, categoryName);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            typeid = rs.getInt("ID");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        closeResources(conn, stmt, rs);
	    }

	    return typeid;
	}

	private static final String SQL_INSERT = String.format(
			"insert into %s (%s, %s, %s, %s, %s, %s) values (?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?)", TBL_FRESHMART,
			COL_TYPE_ID, COL_FOOD_NAME, COL_EXPIRATION_DATE, COL_STORAGE, COL_FOOD_QUANTITY, COL_IMG);

	public int create(Freshmart freshmart) {
		int result = 0;

		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.prepareStatement(SQL_INSERT);

			stmt.setInt(1, freshmart.getTypeid());
			stmt.setString(2, freshmart.getFoodname());
			stmt.setString(3, freshmart.getExpirationdate().toString());

			String storage = freshmart.getStorage();
			if ("냉장실".equals(storage)) {
				stmt.setString(4, "냉장실");
			} else {
				stmt.setString(4, "냉동실");
			}

			stmt.setInt(5, freshmart.getFoodquantity());

			String imagePath = null;

			if (freshmart.getIMG() != null && !freshmart.getIMG().isEmpty()) {
				String uploadPath = "C:\\Users\\MYCOM\\Desktop\\Images\\";
				File uploadDir = new File(uploadPath);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}

				String fileName = freshmart.getId() + "_" + System.currentTimeMillis() + ".jpg";
				imagePath = uploadPath + fileName;
				stmt.setString(6, imagePath);
			} else {
				stmt.setString(6, null);
			}

			conn.setAutoCommit(false);
			result = stmt.executeUpdate();

			if (imagePath != null) {
				File imageFile = new File(imagePath);
				BufferedImage image;

				if (freshmart.getIMG() != null && !freshmart.getIMG().isEmpty()) {
				    File imageFile1 = new File(freshmart.getIMG());
				    if (imageFile1.exists()) {
				        image = ImageIO.read(imageFile1);
				    } else {
				        image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
				        Graphics2D g2d = image.createGraphics();
				        g2d.setColor(Color.WHITE);
				        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
				        g2d.setColor(Color.BLACK);
				        g2d.drawString("기본 이미지", 10, 50);
				        g2d.dispose();
				    }
				} else {
				    image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
				    Graphics2D g2d = image.createGraphics();
				    g2d.setColor(Color.WHITE);
				    g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
				    g2d.setColor(Color.BLACK);
				    g2d.drawString("기본 이미지", 10, 50);
				    g2d.dispose();
				}

				ImageIO.write(image, "jpg", imageFile);
			}

			conn.commit();
			result = 1;

		} catch (SQLException e) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException rollbackEx) {
				rollbackEx.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("이미지 파일 저장 실패: " + e.getMessage());
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt);
		}

		return result;
	}

}
