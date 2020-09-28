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