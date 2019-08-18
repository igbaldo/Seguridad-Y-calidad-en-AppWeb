<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Usuario</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="https://www.google.com/recaptcha/api.js" async defer></script>
	<link rel="stylesheet" href="css/bootstrap.min.css">
<!-- 	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous"> -->
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
	<link rel="stylesheet" href="css/admin.css" >
	<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
	
		<nav class="navbar navbar-expand navbar-dark static-top d-flex justify-content-between" style="background-color:#3c8dbc">
			<a class="navbar-brand mr-1" href="usuario">Usuario</a>  
		   <ul class="navbar-nav ml-auto ml-md-0">
				<li class="nav-item dropdown no-arrow">
					<a class="nav-link dropdown-toggle text-white" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<i class="fas fa-user-circle fa-fw"></i>
					</a>
					<div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
						<a class="dropdown-item" href="#">${nombre}</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="#" data-toggle="modal" data-target="#modalCambiarContrasenia">Cambiar contraseña</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="login">Inicio</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="cerrarSession">Cerrar Sesión</a>
					</div>
				</li>
			</ul>
		</nav>
		  
			  <div id="wrapper">
				<!-- Sidebar -->
				<ul class="sidebar navbar-nav" style="background-color:#1C2331">
				  <li class="nav-item">
					<a class="nav-link text-white" href="usuario">
					  <i class="fas fa-fw fa-table"></i>
					  <span>Notas</span></a>
				  </li>
				  <li class="nav-item">
					<a class="nav-link text-white" href="usuario-historial">
					  <i class="fas fa-fw fa-table"></i>
					  <span>Historial</span></a>
				  </li>
				</ul>
		  
				<div id="content-wrapper" style="background-color:#e6e6e6">
				  <div class="container-fluid">
				  
				  <c:if test="${not empty errorCambio && errorCambio == 0}">
				  	<div class="alert alert-dismissible alert-success">
					  <button type="button" class="close" data-dismiss="alert">&times;</button>
					  <strong>Felicitaciones!</strong> ${msjcambio}
				    </div>
				  </c:if>
				  <c:if test="${not empty errorCambio && errorCambio == 1}">
				  	<div class="alert alert-dismissible alert-danger">
					  <button type="button" class="close" data-dismiss="alert">&times;</button>
					  <strong>Error!</strong> ${msjcambio}
				    </div>
				  </c:if>

					<!-- DataTables Example -->
					<div class="card">
					  <div class="card-header">
						Mis Notas <a href="#" class="btn btn-success float-right" data-toggle="modal" data-target="#modalAgregarNota">Agregar Nota</a>
					  </div>
					  <div class="card-body pt-0">
						<div class="table-responsive">
						  <table class="table text-center" id="dataTable" width="100%" cellspacing="0">
							<thead>
							  <tr>
							  	<th class="align-middle">Fecha</th>
								<th class="align-middle">Nota</th>
							  </tr>
							</thead>
							<tbody>
								<c:forEach items = "${notas}"  var="nota">
								  <tr>
									<td class="align-middle">${nota.getFechaModificacion()}</td>
									<td class="align-middle">${nota.getDescripcion()}</td>
								  </tr> 
								</c:forEach>
							</tbody>
						  </table>
						</div>
					  </div>
					</div>
				  </div>
				  <!-- /.container-fluid --> 
				</div>
				<!-- /.content-wrapper -->  
			  </div>
			  <!-- /#wrapper -->
			  
	<!-- Modal -->
		<div class="modal fade" id="modalAgregarNota" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content pb-3">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">Escriba su nota</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body pb-3">
		        <form:form action="registrar-nota" class="text-center px-5" method="POST" modelAttribute="nota" autocomplete="off">
					<div class="form-group">
					    <form:textarea path="Descripcion" class="form-control" id="Descripcion" rows="3" autocomplete="off"></form:textarea>
					</div>
					<button class="btn btn-lg btn-primary btn-block" Type="Submit"/>Crear Nota</button>
				</form:form>
		      </div>
		    </div>
		  </div>
		</div>
	<!-- Fin Modal --> 
	
	
	<!-- Modal Cambiar Contraseña-->
	<div class="modal fade" id="modalCambiarContrasenia" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content pb-3">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Cambiar Contraseña</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body pb-3">
	        <form:form action="cambiar-contrasenia" id='demo-form' class="text-center px-5" method="POST" modelAttribute="usuario" autocomplete="off">
				   <form:input path="password" class="form-control mb-4" id="password" type="password" name="password" placeholder="Contraseña Actual" autocomplete="off"/> 
				   
				   <small id ="calidadPassword" class="form-text text-left"></small>
				   <div class="input-group flex-nowrap mb-4">
				    <form:input path="password2" class="form-control" id="password2" type="password" placeholder="Contraseña Nueva" onkeyup="validatePassword(this.value);" autocomplete="off"/>
					<div class="input-group-prepend">
					  <span class="input-group-text" id="eye">
					  	<i class="fas fa-eye"></i>
					  </span>
					</div>
				   </div>
				<div class="g-recaptcha" data-sitekey="6LeUwKUUAAAAAOHov99X7G3QkdEPw7Pfuvn5vBKl"></div>
      			<br/>
				<input class="btn btn-lg btn-primary btn-block" type="submit" value="Cambiar Contraseña"> 
	
			</form:form>
	      </div>
	    </div>
	  </div>
	</div>
	<!-- Fin Modal Cambiar Contraseña-->  
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/miscript.js" type="text/javascript"></script>
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