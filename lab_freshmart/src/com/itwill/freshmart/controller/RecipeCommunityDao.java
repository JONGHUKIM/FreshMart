package com.itwill.freshmart.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.itwill.freshmart.model.RecipeCommunity;
import static com.itwill.jdbcOracle.OracleJdbc.PASSWORD;
import static com.itwill.jdbcOracle.OracleJdbc.URL;
import static com.itwill.jdbcOracle.OracleJdbc.USER;
import static com.itwill.freshmart.model.RecipeCommunity.Entity.*;

import oracle.jdbc.OracleDriver;

public enum RecipeCommunityDao {

	INSTANCE;

	RecipeCommunityDao() {

		try {
			DriverManager.registerDriver(new OracleDriver());
		} catch (SQLException e) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void closeResources(Connection conn, Statement stmt) {
		closeResources(conn, stmt, null);
	}

	private RecipeCommunity getRecipeCommunityFromResultSet(ResultSet rs) throws SQLException {
		String liked = rs.getString(COL_LIKED);
		if (liked == null)
			liked = "";

		int id = rs.getInt(COL_ID);
		String title = rs.getString(COL_TITLE);
		String content = rs.getString(COL_CONTENT);
		String author = rs.getString(COL_AUTHOR);

		Timestamp createdTime = rs.getTimestamp(COL_CREATED_TIME);
		if (createdTime == null)
			createdTime = new Timestamp(System.currentTimeMillis());

		Timestamp modifiedTime = rs.getTimestamp(COL_MODIFIED_TIME);
		if (modifiedTime == null)
			modifiedTime = new Timestamp(System.currentTimeMillis());

		return RecipeCommunity.builder().liked(liked).id(id).title(title).content(content).author(author)
				.createdTime(createdTime).modifiedTime(modifiedTime).build();
	}

	private static final String SQL_SELECT_ALL = String.format("select * from %s order by %s desc", TBL_RECIPECOMMUNITY,
			COL_ID);

	public List<RecipeCommunity> read() {
		List<RecipeCommunity> RecipeCommunitys = new ArrayList<RecipeCommunity>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			stmt = conn.prepareStatement(SQL_SELECT_ALL);
			rs = stmt.executeQuery();

			while (rs.next()) {
				RecipeCommunity recipeCommunity = getRecipeCommunityFromResultSet(rs);
				RecipeCommunitys.add(recipeCommunity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}

		return RecipeCommunitys;
	}

	private static final String SQL_INSERT = String.format(
			"insert into %s (%s, %s, %s, %s, %s) values (?, ?, ?, systimestamp, systimestamp)", TBL_RECIPECOMMUNITY,
			COL_TITLE, COL_CONTENT, COL_AUTHOR, COL_CREATED_TIME, COL_MODIFIED_TIME);

	public int create(RecipeCommunity recipeCommunity) {
		int result = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			stmt = conn.prepareStatement(SQL_INSERT);

			stmt.setString(1, recipeCommunity.getTitle());
			stmt.setString(2, recipeCommunity.getContent());
			stmt.setString(3, recipeCommunity.getAuthor());

			result = stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt);
		}

		return result;
	}

	private static final String SQL_DELETE_BY_ID = String.format("delete from %s where %s = ?", TBL_RECIPECOMMUNITY,
			COL_ID);

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

	private static final String SQL_SELECT_BY_ID = String.format("select * from %s where %s = ?", TBL_RECIPECOMMUNITY,
			COL_ID);

	public RecipeCommunity read(Integer id) {
		RecipeCommunity recipeCommunity = null;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
			stmt.setInt(1, id);

			rs = stmt.executeQuery();
			if (rs.next()) {
				recipeCommunity = getRecipeCommunityFromResultSet(rs);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}

		return recipeCommunity;
	}

	private static final String SQL_UPDATE_BY_ID = String.format(
			"update %s set %s = ?, %s = ?, %s = systimestamp where %s = ?", TBL_RECIPECOMMUNITY, COL_TITLE, COL_CONTENT,
			COL_MODIFIED_TIME, COL_ID);

	public int update(RecipeCommunity recipeCommunity) {
		int result = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			stmt = conn.prepareStatement(SQL_UPDATE_BY_ID);
			stmt.setString(1, recipeCommunity.getTitle());
			stmt.setString(2, recipeCommunity.getContent());
			stmt.setInt(3, recipeCommunity.getId());

			result = stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt);
		}

		return result;
	}

	// readByLiked 메서드 추가 (liked 값으로 조회)
	private static final String SQL_SELECT_BY_LIKED = String.format("select * from %s where %s = ? order by %s desc",
			TBL_RECIPECOMMUNITY, COL_LIKED, COL_ID);

	public List<RecipeCommunity> readByLiked(String liked) {
		List<RecipeCommunity> result = new ArrayList<RecipeCommunity>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			stmt = conn.prepareStatement(SQL_SELECT_BY_LIKED);
			stmt.setString(1, liked); // liked 값 (예: 'Y' 또는 'N')

			rs = stmt.executeQuery();
			while (rs.next()) {
				RecipeCommunity recipeCommunity = getRecipeCommunityFromResultSet(rs);
				result.add(recipeCommunity);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}

		return result;
	}
	
	public RecipeCommunity findById(Integer id) {
	    return read(id); // 기존 read 메서드 재활용
	}
	
	private static final String SQL_SELECT_LIKED_BY_ID = String.format(
		    "SELECT %s FROM %s WHERE %s = ?", COL_LIKED, TBL_RECIPECOMMUNITY, COL_ID);

		private static final String SQL_UPDATE_LIKED_BY_ID = String.format(
		    "UPDATE %s SET %s = ?, %s = systimestamp WHERE %s = ?", 
		    TBL_RECIPECOMMUNITY, COL_LIKED, COL_MODIFIED_TIME, COL_ID);

		public boolean toggleLiked(Integer id) {
		    boolean isLiked = false;  // 기본값
		    Connection conn = null;
		    PreparedStatement selectStmt = null;
		    PreparedStatement updateStmt = null;
		    ResultSet rs = null;

		    try {
		        conn = DriverManager.getConnection(URL, USER, PASSWORD);

		        // 현재 liked 값 가져오기
		        selectStmt = conn.prepareStatement(SQL_SELECT_LIKED_BY_ID);
		        selectStmt.setInt(1, id);
		        rs = selectStmt.executeQuery();

		        if (rs.next()) {
		            String currentLiked = rs.getString(COL_LIKED);
		            String newLiked = "N";  // 기본값 "N"

		            if ("N".equalsIgnoreCase(currentLiked)) {
		                newLiked = "Y";  // N -> Y
		                isLiked = true;
		            }

		            // liked 값 업데이트
		            updateStmt = conn.prepareStatement(SQL_UPDATE_LIKED_BY_ID);
		            updateStmt.setString(1, newLiked);
		            updateStmt.setInt(2, id);
		            updateStmt.executeUpdate();
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        closeResources(conn, selectStmt, rs);
		        closeResources(conn, updateStmt);
		    }

		    return isLiked;  // 최종 liked 상태 반환
		}

	// 기존의 검색 메서드들
	private static final String SQL_SELECT_BY_TITLE = String.format(
			"select * from %s where upper(%s) like upper(?) order by %s desc", TBL_RECIPECOMMUNITY, COL_TITLE, COL_ID);

	private static final String SQL_SELECT_BY_CONTENT = String.format(
			"select * from %s where upper(%s) like upper(?) order by %s desc", TBL_RECIPECOMMUNITY, COL_CONTENT,
			COL_ID);

	private static final String SQL_SELECT_BY_AUTHOR = String.format(
			"select * from %s where upper(%s) like upper(?) order by %s desc", TBL_RECIPECOMMUNITY, COL_AUTHOR, COL_ID);

	private static final String SQL_SELECT_BY_TITLE_OR_CONTENT = String.format(
			"select * from %s where upper(%s) like upper(?) or upper(%s) like upper(?) order by %s desc",
			TBL_RECIPECOMMUNITY, COL_TITLE, COL_CONTENT, COL_ID);

	public List<RecipeCommunity> read(int type, String keyword) {
		List<RecipeCommunity> result = new ArrayList<RecipeCommunity>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);

			String searchKeyword = "%" + keyword + "%";
			switch (type) {
			case 0:
				stmt = conn.prepareStatement(SQL_SELECT_BY_TITLE);
				stmt.setString(1, searchKeyword);
				break;
			case 1:
				stmt = conn.prepareStatement(SQL_SELECT_BY_CONTENT);
				stmt.setString(1, searchKeyword);
				break;
			case 2:
				stmt = conn.prepareStatement(SQL_SELECT_BY_TITLE_OR_CONTENT);
				stmt.setString(1, searchKeyword);
				stmt.setString(2, searchKeyword);
				break;
			case 3:
				stmt = conn.prepareStatement(SQL_SELECT_BY_AUTHOR);
				stmt.setString(1, searchKeyword);
				break;
			}

			rs = stmt.executeQuery();
			while (rs.next()) {
				RecipeCommunity recipeCommunity = getRecipeCommunityFromResultSet(rs);
				result.add(recipeCommunity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources(conn, stmt, rs);
		}

		return result;
	}

}