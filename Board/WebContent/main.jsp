<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<body>
	<jsp:include page="head.jsp" flush="false" />
	<div class="container">
		<div class="jumbotron" style="margin: 36px">
			<h1 class="display-4">�Խ��� ������Ʈ!!</h1>
			<p class="lead">
				�� �� ����Ʈ�� ��Ʈ��Ʈ������ ���� JSP �Խ��� ����Ʈ�Դϴ�.</br> DB ������Ʈ �غ��ϸ鼭 ���ο� ���� ����� �Դϴ�!
				������ ���ø����δ� ��Ʈ��Ʈ���� �̿��߽��ϴ�.
			</p>
			<hr class="my-4">
			<p>�α��� ȸ������ �Խ��� ����� �־�ý��ϴ�! �ʱ�� git hub�� �߽��ϴ�!</p>
			<a class="btn btn-dark btn-lg"
				href="https://github.com/Kimginam97/Board" role="button">Github</a>
		</div>
	</div>

	<div class="container">
		<div id="carouselExampleIndicators" class="carousel slide"
			data-ride="carousel" style="margin: 36px">
			<ol class="carousel-indicators">
				<li data-target="#carouselExampleIndicators" data-slide-to="0"
					class="active"></li>
				<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
				<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
			</ol>
			<div class="carousel-inner">
				<div class="carousel-item active">
					<img src="images/1.jpg" class="d-block w-100" alt="..." >
				</div>
				<div class="carousel-item">
					<img src="images/2.jpg" class="d-block w-100" alt="...">
				</div>
				<div class="carousel-item">
					<img src="images/3.jpg" class="d-block w-100" alt="...">
				</div>
			</div>
			<a class="carousel-control-prev" href="#carouselExampleIndicators"
				role="button" data-slide="prev"> <span
				class="carousel-control-prev-icon" aria-hidden="true"></span> <span
				class="sr-only">Previous</span>
			</a> <a class="carousel-control-next" href="#carouselExampleIndicators"
				role="button" data-slide="next"> <span
				class="carousel-control-next-icon" aria-hidden="true"></span> <span
				class="sr-only">Next</span>
			</a>
		</div>
	</div>

	<jsp:include page="footer.jsp" flush="false" />
</body>
</html>