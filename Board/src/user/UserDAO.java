package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
	
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
	
	
	//회원가입
	public int join(User user)throws Exception {
		Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		try {
			conn=getConnection();
			sql="INSERT INTO USERS VALUES (?, ?, ?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			
			//executeUpdate -int 타입의값을 반환하고 SELECT 구문을 제외한 다른 구문을 수행할 때 사용되는 함수 
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1;  //회원가입문제
	}
	
	//로그인
	public int login(String userID, String userPassword) throws Exception  {
		Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		try {
			conn=getConnection();
			sql="SELECT userPassword FROM USERS WHERE userID = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			
			//executeQuery - ResultSet 객체에 결과값을 담을 수 있습니다
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword))
					return 1; //로그인성공
				else
					return 0; //비밀번호 실패
			}
			return -1; //존재하지않는 아이디
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -2; //데이터베이스 오류
	}
	
}
 