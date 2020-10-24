<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<jsp:include page="head.jsp" flush="false" />
	<%
		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		if(userID == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 하세요.');");
			script.println("location.href='login.jsp'");
			script.println("</script>");
			script.close();
		}
	%>
	<div class="container">
			<form method="post" action="writeAction.jsp">
				<table class="table table-bordered" style="text-align: center;">
					<thead>
						<tr>
							<th colspan="2" style="background-color: #eeeeee; text-align: center;">게시판 글쓰기 양식</th>
						</tr>
					</thead>
					<tbody>
							<tr>
								<td colspan="2"><input type="text" class="form-control" placeholder="글 제목" name="bbsTitle" maxlength="50"></td>
							</tr>
							<tr>
								<td colspan="2"><textarea class="form-control" name="bbsContent" placeholder="글 내용" maxlength="2048" style="height: 350px;"></textarea></td>
							</tr>
					</tbody>
				</table>
				<input type="submit" class="btn btn-primary pull-right" value="글쓰기">
			</form>
	</div>
	<jsp:include page="footer.jsp" flush="false" />
</body>
</html>