# join.jsp

## join

### userDAO 추가
```
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

```

### join의 jsp 구성
```
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
	<% request.setCharacterEncoding("utf-8");%>
<!DOCTYPE html>
<html>
<body>
	<jsp:include page="head.jsp" flush="false" />

	<div class="container">
		<div class="jumbotron" style="margin: 36px;">
			<form method="post" action="joinPro.jsp">
				<h3 style="text-align: center;">회원가입</h3>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="아이디"
						name="userID" maxlength="20">
				</div>
				<div class="form-group">
					<input type="password" class="form-control" placeholder="비밀번호"
						name="userPassword" maxlength="20">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="이름"
						name="userName" maxlength="20">
				</div>
				<div class="form-group" style="text-align: center;">
					<div class="btn-group" data-toggle="buttons">
						<label class="btn btn-primary active"> 
							<input type="radio"name="userGender" autocomplete="off" value="male" checked>남자
						</label> 
						<label class="btn btn-primary"> 
							<input type="radio"name="userGender" autocomplete="off" value="female">여자
						</label>
					</div>
				</div>
				<div class="form-group">
					<input type="email" class="form-control" placeholder="이메일"
						name="userEmail" maxlength="50">
				</div>
				<button type="submit" class="btn btn-primary form-control">로그인</button>
			</form>
		</div>
	</div>

	<jsp:include page="footer.jsp" flush="false" />
</body>
</html>
```
### joinpro의 jsp 구성
```
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "user.UserDAO" %>
<%@ page import = "java.sql.Timestamp" %>
<%@ page import="java.io.PrintWriter"%>
<% request.setCharacterEncoding("utf-8");%>

<jsp:useBean id="user" scope="page" class="user.User">
   <jsp:setProperty name="user" property="*"/>
</jsp:useBean>
 
<%
		if (user.getUserID() == null || user.getUserPassword() == null || user.getUserName() == null
				|| user.getUserGender() == null || user.getUserEmail() == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
		} else {
			UserDAO userDAO = new UserDAO();
			int result = userDAO.join(user);
			if (result == -1) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('이미 존재하는 아이디입니다.');");
				script.println("history.back();");
				script.println("</script>");
				script.close();
			} else {
				session.setAttribute("userID", user.getUserID());
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('회원가입에 성공하셨습니다!');");
				script.println("location.href = 'main.jsp';");
				script.println("</script>");
				script.close();
			}
		}
	%>

```

