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
	  
	 
			<section id="section-cambiandopass" class="mt-5">
				<div class="container   ">
					<div class="row d-flex align-items-center justify-content-center rounded-sm">
						<div class="col-xs-12 col-md-5 bg-light py-5">
						  
							 <form:form action="actualizarPass" class="text-center px-5" method="POST" modelAttribute="usuario" autocomplete="off">
					        	<form:input path="idE" value="${id}" class="form-control mb-4" type="hidden" /> 
					        	<div class="form-group mb-4">
									<form:input path="password" class="form-control" id="password" type="password" placeholder="Contraseña Nueva" onkeyup="validatePassword(this.value);" autocomplete="off"/>
									<small id ="calidadPassword" class="form-text text-left"></small>
								</div>
								<form:input path="password2" class="form-control mb-4" id="password2" type="password" placeholder="Repetir nueva Contraseña" autocomplete="off"/>
<!-- 								<span id="calidadPassword"></span> -->
								<input class="btn btn-lg btn-primary btn-block" type="submit" value="Cambiar Contraseña">
						
							 <c:if test="${(not empty msjcambio) && (errorCambio==3)}">
						        <div class="alert alert-dismissible alert-success text-center py-2 mt-4">
								  ${msjcambio}
								</div>
					        </c:if>
					        <c:if test="${(not empty msjcambio) && (errorCambio==1)}">
						        <div class="alert alert-dismissible alert-danger text-center py-2 mt-4">
								  ${msjcambio}
								</div>
					        </c:if>
					         <c:if test="${(not empty msjcambio) && (errorCambio==0)}">
						        <div class="alert alert-dismissible alert-success text-center py-2 mt-4">
								  ${msjcambio}!
								</div>
					        </c:if>
							<!-- Default form login -->
							</form:form>
						
						</div>
					</div>
				</div>
			</section>
		 
		 
		  
	
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	</body>
	<script>
            function validatePassword(password) {
                
                // Do not show anything when the length of password is zero.
                if (password.length === 0) {
                    document.getElementById("calidadPassword").innerHTML = "";
                    return;
                }
                // Create an array and push all possible values that you want in password
                var matchedCase = new Array();
                matchedCase.push("[$@$!%*#?&_]"); // Special Charector
                matchedCase.push("[A-Z]");      // Uppercase Alpabates
                matchedCase.push("[0-9]");      // Numbers
                matchedCase.push("[a-z]");     // Lowercase Alphabates

                // Check the conditions
                var ctr = 0;
                for (var i = 0; i < matchedCase.length; i++) {
                    if (new RegExp(matchedCase[i]).test(password)) {
                        ctr++;
                    }
                }
                // Display it
                var color = "";
                var strength = "";
                switch (ctr) {
                    case 0:
                    case 1:
                    case 2:
                        strength = "Seguridad de contraseña: Débil";
                        color = "red";
                        break;
                    case 3:
                        strength = "Seguridad de contraseña: Media";
                        color = "orange";
                        break;
                    case 4:
                        strength = "Seguridad de contraseña: Fuerte";
                        color = "green";
                        break;
                }
                document.getElementById("calidadPassword").innerHTML = strength;
                document.getElementById("calidadPassword").style.color = color;
            }
        </script>
</html>