# 7. logoutAction.jsp

## logoutAction

### logoutAction.jsp
```
<body>
	//invalidate 는 session값을 소멸해준다
	<%
		session.invalidate();
	%>
	<script>
		location.href = 'main.jsp';
	</script>
</body>
```

### head의 jsp 구성
```

//userID값을 null초기화 값이 있을경우 userID의 session값 userID값을 객체타입으로 가져온다

//getAttributegetAttribute(String name) 
// java.lang.Object
 //세션 속성명이 name인 속성의 값을 Object 타입으로 리턴한다. 해당 되는 속성명이 없을 경우에는 null 값을 리턴한다.
<%
		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
	%>

	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<a class="navbar-brand" href="main.jsp">JSP 게시판 DB 프로젝트 </a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link" href="main.jsp">메인</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="#">게시판</a></li>
			</ul>

      //로그인이 안됬을 경우
			<%
				if (userID == null) {
			%>
			<ul class="navbar-nav navbar-right">
				<li class="nav-item dropdown float-right"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> 접속하기 </a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="login.jsp">로그인</a> <a
							class="dropdown-item" href="join.jsp">회원가입</a>
					</div></li>
			</ul>
      
      //로그인이 되있는경우
			<%
				}
				else {
			%>
			<ul class="navbar-nav navbar-right">
				<li class="nav-item dropdown float-right"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> 회원관리 </a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="logoutAction.jsp">로그아웃</a> 
					</div>
				</li>
			</ul>
			<%
				}
			%>
      </div>
	</nav>
```


