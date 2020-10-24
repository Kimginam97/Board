package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import user.UserDAO;

public class BbsDAO {

	private static UserDAO instance = new UserDAO();
    //.jsp페이지에서 DB연동빈인 UserDAO클래스의 메소드에 접근시 필요
    public static UserDAO getInstance() {
        return instance;
    }
	
    //커넥션풀로부터 Connection객체를 얻어냄
    private Connection getConnection() throws Exception {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/orcl");
        return ds.getConnection();
    }
    
    
    //현재의 시간을 가져오는 기능
	public String getDate()throws Exception {
		Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="SELECT to_char(sysdate,'yyyy-mm-dd') from bbs";
		try {
			conn=getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return null; 
	}

	//마지막에 쓴 글의 bbsID값을 가져온다
	public int getNext()throws Exception {
		Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		try {
			conn=getConnection();
			sql="SELECT bbsID FROM BBS ORDER BY bbsID DESC";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) + 1; //나온결과에 +1을하여 다음글에 넣어준다
			}
			return 1; //첫번째 게시물인 경우 0
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1; 
	}

	//글쓰기 기능
	public int write(String bbsTitle, String userID, String bbsContent)throws Exception {
		Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		try {
			conn=getConnection();
			sql="INSERT INTO BBS VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, bbsTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, bbsContent);
			pstmt.setInt(6, 1);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1; 
	}
	
	
	//삭제가 되지않은  글들을 위에서 10까지만 가져온다
	public ArrayList<Bbs> getList(int pageNumber)throws Exception {
		Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			conn=getConnection();
			sql="SELECT * FROM"
					+ "(select * from BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC)"
					+ " where ROWNUM <= 10";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext() - (pageNumber - 1) * 10);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				list.add(bbs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return list;
	}
	
	//10개이후 다음페이지로 넘어간다
	public boolean nextPage(int pageNumber)throws Exception {
		Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		try {
			conn=getConnection();
			sql="SELECT * FROM BBS WHERE bbsID <= ? AND bbsAvailable = 1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext() - (pageNumber) * 10);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return false;
	}
	
	
	//글 리스트기능
	public Bbs getBbs(int bbsID)throws Exception {
		Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		try {
			conn=getConnection();
			sql="SELECT * FROM BBS WHERE bbsID = ? AND bbsAvailable = 1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBbsID(rs.getInt(1));
				bbs.setBbsTitle(rs.getString(2));
				bbs.setUserID(rs.getString(3));
				bbs.setBbsDate(rs.getString(4));
				bbs.setBbsContent(rs.getString(5));
				bbs.setBbsAvailable(rs.getInt(6));
				return bbs;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return null; 
	}
	
	
	//글 수정기능
	public int update(int bbsID, String bbsTitle, String bbsContent)throws Exception {
		Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		try {
			conn=getConnection();
			sql="UPDATE BBS SET bbsTitle = ?, bbsContent = ? WHERE bbsID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bbsTitle);
			pstmt.setString(2, bbsContent);
			pstmt.setInt(3, bbsID);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1; 
	}
	
	//글삭제 기능
	public int delete(int bbsID)throws Exception {
		Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		try {
			conn=getConnection();
			sql="UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bbsID);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1;
	}

}
