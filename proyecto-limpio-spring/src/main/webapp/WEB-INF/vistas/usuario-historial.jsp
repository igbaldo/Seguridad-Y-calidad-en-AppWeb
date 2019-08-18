<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="es">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Usuario - Historial</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<link rel="stylesheet" href="css/admin.css" >
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
					<!-- DataTables Example -->
					<div class="card">
					  <div class="card-header">
						Historial de mi cuenta
					  </div>
					  <div class="card-body pt-0">
						<div class="table-responsive">
						  <table class="table text-center" id="dataTable" width="100%" cellspacing="0">
							<thead>
							  <tr>
							  	<th class="align-middle">Funcionalidad</th>
								<th class="align-middle">Fecha y Hora</th>
								<th class="align-middle">Descripción</th><!-- creo que la descripcion cumple el mismo rol que la funcionalidad -->
							  </tr>
							</thead>
							<tbody>
								<c:forEach items = "${logsUsuario}" var="log">
								  <tr>
									<td class="align-middle">${log.getFuncionalidad()}</td>
									<td class="align-middle">${log.getFechaModificacion()}</td>
									<th class="align-middle">${log.getDescripcion()}</th> 
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
	        <form:form action="cambiar-contrasenia" class="text-center px-5" method="POST" modelAttribute="usuario">
	        	<form:input path="id" value="${id}" class="form-control mb-4" type="hidden" /> <!-- se puede poner el id o email del usuario para usarlo como indice en el cambio de contraseña -->
				<form:input path="password" class="form-control mb-4" id="password" type="password" placeholder="Contraseña Nueva"/>
				<form:input path="password2" class="form-control mb-4" id="password2" type="password" placeholder="Repetir nueva Contraseña"/>
				<input class="btn btn-lg btn-primary btn-block" type="submit" value="Cambiar Contraseña">
			</form:form>
	      </div>
	    </div>
	  </div>
	</div>
	<!-- Fin Modal Cambiar Contraseña-->   		 
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>