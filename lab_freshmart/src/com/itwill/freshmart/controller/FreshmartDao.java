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
import java.util.Random;

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

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
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

	private static final String SQL_DELETE_BY_ID = String.format("delete from %s where %s = ?", TBL_FRESHMART, COL_ID);

	public int delete(Integer id) {
		int result = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.prepareStatement(SQL_DELETE_BY_ID);
			stmt.setInt(1, id);
			result = stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt);
		}

		return result;
	}

	private static final String SQL_SELECT_ALL = String.format("select * from %s order by %s desc", TBL_FRESHMART,
			COL_ID);

	public List<Freshmart> read() {

		List<Freshmart> freshmarts = new ArrayList<>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			stmt = conn.prepareStatement(SQL_SELECT_ALL);

			rs = stmt.executeQuery();
			while (rs.next()) {
				Freshmart freshmart = getFreshmartFromResultSet(rs);

				String imgPath = getFoodImagePath(freshmart.getFoodname());
				if (imgPath == null) {
					imgPath = "C:\\Users\\MYCOM\\Desktop\\Images";
				}
				freshmart.setIMG(imgPath);

				freshmarts.add(freshmart);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}

		return freshmarts;
	}

	public List<Freshmart> readByStorage(String storageType) {
		List<Freshmart> resultList = new ArrayList<>();
		String query = "SELECT * FROM " + TBL_FRESHMART + " WHERE " + COL_STORAGE + " = ?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setString(1, storageType);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					Freshmart freshmart = getFreshmartFromResultSet(rs);

					String imgPath = getFoodImagePath(freshmart.getFoodname());
					if (imgPath == null) {
						imgPath = "C:\\Users\\MYCOM\\Desktop\\Images";
					}
					freshmart.setIMG(imgPath);

					resultList.add(freshmart);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
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
		String imagePath = null;

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

			if (freshmart.getIMG() != null && !freshmart.getIMG().isEmpty()) {
				String uploadPath = "C:\\Users\\MYCOM\\Desktop\\Images\\"; // 이미지가 저장될 경로
				File uploadDir = new File(uploadPath);

				// 이미지 디렉토리가 없으면 생성
				if (!uploadDir.exists()) {
					uploadDir.mkdirs(); // 디렉토리 생성
				}

				// 고유한 파일 이름 생성
				String fileName = freshmart.getFoodname() + "_" + System.currentTimeMillis() + ".png";
				imagePath = uploadPath + fileName; // 경로에 파일 이름 추가
				stmt.setString(6, imagePath); // DB에 저장될 이미지 경로 설정
			} else {
				stmt.setString(6, null); // 이미지가 없으면 null 저장
			}

			conn.setAutoCommit(false);
			result = stmt.executeUpdate();

			if (imagePath != null) {
				File imageFile = new File(imagePath);
				BufferedImage image;

				// 이미지를 불러오는 로직
				if (freshmart.getIMG() != null && !freshmart.getIMG().isEmpty()) {
					File imageFile1 = new File(freshmart.getIMG()); // 기존 이미지 경로
					if (imageFile1.exists()) {
						// 기존 이미지 파일이 존재하면 읽어서 저장
						image = ImageIO.read(imageFile1);
					} else {
						// 파일이 존재하지 않으면 기본 이미지를 생성
						image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
						Graphics2D g2d = image.createGraphics();
						g2d.setColor(Color.WHITE);
						g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
						g2d.setColor(Color.BLACK);
						g2d.drawString("기본 이미지", 10, 50); // 기본 이미지에 "기본 이미지" 텍스트 표시
						g2d.dispose();
					}
				} else {
					// 사용자가 이미지를 업로드하지 않았을 경우 기본 이미지 생성
					image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
					Graphics2D g2d = image.createGraphics();
					g2d.setColor(Color.WHITE);
					g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
					g2d.setColor(Color.BLACK);
					g2d.drawString("기본 이미지", 10, 50); // 기본 이미지에 "기본 이미지" 텍스트 표시
					g2d.dispose();
				}

				// 생성된 이미지를 파일로 저장
				ImageIO.write(image, "png", imageFile);
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

	public String getFoodCategoryNameById(Integer typeId) {
		String categoryName = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String sql = "SELECT CATEGORY FROM FOOD_CATEGORY WHERE ID = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, typeId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				categoryName = rs.getString("CATEGORY");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}

		return categoryName;
	}

	public String getFoodImagePath(String foodName) {
		String imagePath = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			String query = "SELECT " + COL_IMG + " FROM " + TBL_FRESHMART + " WHERE " + COL_FOOD_NAME + " = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, foodName);
			rs = stmt.executeQuery();

			if (rs.next()) {
				imagePath = rs.getString(COL_IMG);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}

		return imagePath;
	}

	public int update(Freshmart freshmart) {
		int result = 0;
		String sql = "UPDATE " + Freshmart.Entity.TBL_FRESHMART + " SET " + Freshmart.Entity.COL_FOOD_NAME + " = ?, "
				+ Freshmart.Entity.COL_TYPE_ID + " = ?, " + Freshmart.Entity.COL_EXPIRATION_DATE + " = ?, "
				+ Freshmart.Entity.COL_STORAGE + " = ?, " + Freshmart.Entity.COL_FOOD_QUANTITY + " = ?, "
				+ Freshmart.Entity.COL_IMG + " = ? " + "WHERE " + Freshmart.Entity.COL_ID + " = ?";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, freshmart.getFoodname());
			stmt.setInt(2, freshmart.getTypeid());
			stmt.setDate(3, java.sql.Date.valueOf(freshmart.getExpirationdate()));
			stmt.setString(4, freshmart.getStorage());
			stmt.setInt(5, freshmart.getFoodquantity());
			stmt.setString(6, freshmart.getIMG());
			stmt.setInt(7, freshmart.getId());

			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Freshmart getFoodItemById(int foodId) {
		Freshmart foodItem = null;

		String sql = "SELECT * FROM " + Freshmart.Entity.TBL_FRESHMART + " WHERE " + Freshmart.Entity.COL_ID + " = ?";

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, foodId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				foodItem = Freshmart.builder().id(rs.getInt(Freshmart.Entity.COL_ID))
						.typeid(rs.getInt(Freshmart.Entity.COL_TYPE_ID))
						.foodname(rs.getString(Freshmart.Entity.COL_FOOD_NAME))
						.expirationdate(rs.getDate(Freshmart.Entity.COL_EXPIRATION_DATE).toLocalDate())
						.storage(rs.getString(Freshmart.Entity.COL_STORAGE))
						.foodquantity(rs.getInt(Freshmart.Entity.COL_FOOD_QUANTITY))
						.img(rs.getString(Freshmart.Entity.COL_IMG)).build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foodItem;
	}

	public List<Freshmart> readByExpirationDateDesc(String storage) {
		String sql = String.format("SELECT * FROM %s WHERE %s = ? ORDER BY %s DESC", TBL_FRESHMART, COL_STORAGE,
				COL_EXPIRATION_DATE);
		List<Freshmart> resultList = new ArrayList<>();

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, storage);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				resultList.add(getFreshmartFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public List<Freshmart> readByExpirationDateAsc(String storage) {
		String sql = String.format("SELECT * FROM %s WHERE %s = ? ORDER BY %s asc", TBL_FRESHMART, COL_STORAGE,
				COL_EXPIRATION_DATE);
		List<Freshmart> resultList = new ArrayList<>();

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, storage);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				resultList.add(getFreshmartFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public Freshmart recommendFood(String storage) {

		List<Freshmart> foodList = readByExpirationDateAsc(storage);

		if (foodList.isEmpty()) {
			return null;
		}

		Random rand = new Random();
		int randomIndex = rand.nextInt(foodList.size());
		return foodList.get(randomIndex);
	}

}