# 7. login.jsp

## login

### userDAO 추가
```
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
```

### login의 jsp 구성
```
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
	<jsp:include page="head.jsp" flush="false" />

	<div class="container">
		<div class="jumbotron" style="margin: 36px;">
			<form method="post" action="loginPro.jsp">
			<h3 style="text-align: center;">로그인</h3>
				<div class="form-group">
						<input type="text" class="form-control" placeholder="아이디" name="userID" maxlength="20">
					</div>
					<div class="form-group">
						<input type="password" class="form-control" placeholder="비밀번호" name="userPassword" maxlength="20">
					</div>
					<button type="submit" class="btn btn-primary form-control">로그인</button>
			</form>
		</div>
	</div>

	<jsp:include page="footer.jsp" flush="false" />
</body>
</html>
```

### loginPro의 jsp 구성
```
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "user.UserDAO" %>
<%@ page import = "java.sql.Timestamp" %>
<%@ page import="java.io.PrintWriter"%>
<% request.setCharacterEncoding("utf-8");%>

<jsp:useBean id="user" scope="page" class="user.User">
   <jsp:setProperty name="user" property="userID"/>
   <jsp:setProperty name="user" property="userPassword"/>
</jsp:useBean>
 <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP 게시판 웹 사이트</title>
</head>
<body>
<%
		UserDAO userDAO = new UserDAO();
		int result = userDAO.login(user.getUserID(), user.getUserPassword());
		
		//로그인성공
		if (result == 1) {
			session.setAttribute("userID", user.getUserID());
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href='main.jsp'");
			script.println("</script>");
			script.close();
		}else if (result == 0) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('비밀번호가 틀립니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
		} else if (result == -1) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('존재하지 않는 아이디입니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
		} else if (result == -2) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('데이터베이스 오류가 발생했습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
		}
%>
</body>
</html>
```


