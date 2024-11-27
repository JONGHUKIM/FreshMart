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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.itwill.freshmart.model.Freshmart;

import oracle.jdbc.OracleDriver;

public enum FreshmartDao {
	INSTANCE;
	
	private byte[] decodeImageFromBase64(String base64ImageData) {
	    return Base64.getDecoder().decode(base64ImageData);
	}
	
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
	        if ("T".equals(storage)) {
	            stmt.setString(4, "냉장실");
	        } else if ("F".equals(storage)) {
	            stmt.setString(4, "냉동실");
	        } else {
	            stmt.setString(4, storage);
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

	            if (freshmart.getIMG() != null) {
	                // `getIMG` 값이 실제 이미지 데이터인 경우, Base64로 디코딩하여 저장
	                byte[] imageData = decodeImageFromBase64(freshmart.getIMG());
	                image = ImageIO.read(new ByteArrayInputStream(imageData));
	            } else {
	                // 기본 이미지를 생성
	                image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
	                Graphics2D g2d = image.createGraphics();
	                g2d.setColor(Color.WHITE);
	                g2d.fillRect(0, 0, image.getWidth(), image.getHeight()); // 흰색 배경
	                g2d.setColor(Color.BLACK);
	                g2d.drawString("기본 이미지", 10, 50); // 텍스트 추가
	                g2d.dispose();
	            }

	            // 이미지 파일 저장
	            ImageIO.write(image, "jpg", imageFile);
	        }

	        conn.commit(); // 트랜잭션 커밋
	        result = 1; // 성공 시 1 반환
	    } catch (SQLException e) {
	        try {
	            if (conn != null) conn.rollback(); // 실패 시 롤백
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        e.printStackTrace();
	    } catch (IOException e) {
	        System.err.println("이미지 파일 저장 실패: " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        closeResources(conn, stmt); // 리소스 정리
	    }

	    return result;
	}

}
