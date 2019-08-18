<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
   <link href="css/bootstrap.min.css" rel="stylesheet" >
   <link href="css/bootstrap-theme.min.css" rel="stylesheet">
   <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
   <link href="css/estilos.css" rel="stylesheet">
</head>
<body>
	   <c:if test="${not empty rol && rol == 'admin'}">
		  <c:set var="link"  value = "admin"/>
       </c:if>
       <c:if test="${not empty rol && rol == 'user'}">
		  <c:set var="link"  value = "usuario"/>
       </c:if>		
	  <nav class="navbar navbar-expand navbar-dark static-top d-flex justify-content-between" style="background-color:#1C2331">
		<a class="navbar-brand mr-1" href="admin">S&C de app web</a>  
		 <ul class="navbar-nav ml-auto ml-md-0">
			<li class="nav-item">
			  <c:if test="${not empty link}">
				<a class="nav-link text-white" href="${link}">
					<i class="fas fa-user-circle fa-fw"></i>Entrar
				</a>
			  </c:if>		
			</li>
		</ul>
	  </nav>

		<section id="section-login" class="mt-5">
			<div class="container">
				<div class="row d-flex align-items-center justify-content-center rounded-sm">
					<div class="alert alert-dismissible alert-info">
					  Su contraseña es:  
					  <c:if test="${not empty pass}">
						  <strong>${pass}</strong> <br>
				     </c:if>
				     <a href="login" class="text-center d-block">iniciar Sessión</a>
					</div>
				</div>
			</div>
		</section>

		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	</body>
</html>