<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
   <link href="css/bootstrap.min.css" rel="stylesheet" >
   <link href="css/bootstrap-theme.min.css" rel="stylesheet">
   <link href="css/estilos.css" rel="stylesheet">
</head>
<body>
		<section id="section-login" class="mt-5">
			<div class="container  bg-light ">
				<div class="row d-flex align-items-center justify-content-center rounded-sm">
					<div class="col-xs-12 col-md-5">
						<h3 class="text-center pb-3">Ingrese un texto</h3>
						<form:form action="/registrar-nuevoTexto" class="text-center" method="POST" modelAttribute="usuario">   
							<input type = "text" name = "text" id="text" height = "200px">
							<textarea id="address" name="address" rows="5" cols="30"></textarea>
							<button class="btn btn-lg btn-primary btn-block" Type="Submit">Guardar</button>
						</form:form>
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
					</div>
					<div class="col-xs-12 col-md-7 p-0 m-0 overflow-hidden" style="background-color:#10537f">
						<img src="img/login.jpg"/>
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
