<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<body>
	<jsp:include page="head.jsp" flush="false" />

	<div class="container">
		<div class="jumbotron" style="margin: 36px;">
			<form>
			<h3 style="text-align: center;">로그인</h3>
				<div class="form-group">
					<label for="exampleInputEmail1">아이디</label> <input type="email"
						class="form-control" id="userId" aria-describedby="emailHelp">
				</div>
				<div class="form-group">
					<label for="exampleInputPassword1">비밀번호</label> <input
						type="password" class="form-control" id="userPassword">
				</div>

				<button type="submit" class="btn btn-primary">로그인</button>
			</form>
		</div>
	</div>

	<jsp:include page="footer.jsp" flush="false" />
</body>
</html>