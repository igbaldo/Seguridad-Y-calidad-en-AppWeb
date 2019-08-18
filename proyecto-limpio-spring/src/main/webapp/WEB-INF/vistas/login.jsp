<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
   <link href="css/bootstrap.min.css" rel="stylesheet" >
   <link href="css/bootstrap-theme.min.css" rel="stylesheet">
   <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
   <link href="css/estilos.css" rel="stylesheet">
   <script src='https://www.google.com/recaptcha/api.js'></script>
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
				<a class="nav-link text-white" href="login">Login</a>
			</li>
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
			<div class="container  bg-light ">
				<div class="row d-flex align-items-center justify-content-center rounded-sm">
					<div class="col-xs-12 col-md-5">
						<h3 class="text-center pb-2">Iniciar Sesión</h3>
						<form:form action="validar-login" class="text-center" method="POST" modelAttribute="usuario" autocomplete="off" >
							<form:input path="email" class="form-control mb-2" id="email" type="email" name="email" placeholder="Usuario o E-mail" autocomplete="off"/>
<%-- 							<form:input path="password"  class="form-control mb-2" type="password" id="password" placeholder="Password"/>     		   --%>
							  <div class="input-group flex-nowrap mb-4">
								    <form:input path="password" class="form-control" id="password" type="password" name="password" placeholder="Password" onkeyup="validatePassword(this.value);" autocomplete="off"/><span id="password"></span>
									<div class="input-group-prepend">
									  <span class="input-group-text" id="eyelogin"><i class="fas fa-eye"></i>
									  </span>
									</div>
								</div>
							<c:if test="${mostrarCaptcha == true }">
								<div class="g-recaptcha mb-2" data-sitekey="6LeUwKUUAAAAAOHov99X7G3QkdEPw7Pfuvn5vBKl"></div>
							</c:if>
							<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Login</button>
						</form:form>
						<p class="text-center mt-3 mb-0">¿No tenés una cuenta?<a href="registrarUsuarioView"> Registrarse</a></p>
						<p class="text-center"><a href="#" data-toggle="modal" data-target="#modalRecuperarContrasenia">¿Olvidaste tu contraseña?</a></p>
						<c:if test="${not empty error}">
					        <div class="alert alert-dismissible alert-danger text-center py-2">
							  ${error}!
							</div>					  
				        </c:if>	
				        <c:if test="${(not empty msjregistro) && (errorRegistro==1)}">
					        <div class="alert alert-dismissible alert-danger text-center py-2">
							  ${msjregistro}!
							</div>
				        </c:if>
				         <c:if test="${(not empty msjregistro) && (errorRegistro==0)}">
					        <div class="alert alert-dismissible alert-success text-center py-2">
							  ${msjregistro}!
							</div>
				        </c:if>
						<!-- Default form login -->
					</div>
					<div class="col-xs-12 col-md-7 p-0 m-0 overflow-hidden" style="background-color:#10537f">
						<img src="img/login.jpg"/>
					</div>
				</div>
			</div>
		</section>
		
	<!-- Modal Recuperar Contraseña-->
	<div class="modal fade" id="modalRecuperarContrasenia" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content pb-3">
	      <div class="modal-header">
	        <h6 class="modal-title" id="exampleModalLabel">Escriba su dirección de email para recuperar su contraseña</h6>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body pb-3">
	        <form action="recuperarContrasenia" class="text-center px-5" method="GET" autocomplete="off">
	        	<input value="" class="form-control mb-4" type="hidden" /> <!-- se puede poner el id o email del usuario para usarlo como indice en el cambio de contraseña -->
				<input class="form-control mb-4" name="email" id="email" type="email" placeholder="Email" autocomplete="off"/>
				<input class="btn btn-lg btn-warning btn-block text-white" Type="Submit" value="Recuperar Contraseña"/>
			</form>
	      </div>
	    </div>
	  </div>
	</div>
	<!-- Fin Modal Recuperar Contraseña--> 
		
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
		<script src="js/miscript.js" type="text/javascript"></script>
	</body>
</html>
