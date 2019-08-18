<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Administrador</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
	<link rel="stylesheet" href="css/admin.css" >
</head>
<body>
	
		<nav class="navbar navbar-expand navbar-dark static-top d-flex justify-content-between" style="background-color:#1C2331">
			<a class="navbar-brand mr-1" href="admin">Administrador</a>  
			 <ul class="navbar-nav ml-auto ml-md-0">
				<li class="nav-item dropdown no-arrow">
					<a class="nav-link dropdown-toggle text-white" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						<i class="fas fa-user-circle fa-fw"></i>
					</a>
					<div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
						<a class="dropdown-item" href="login">Inicio</a>
						<a class="dropdown-item" href="cerrarSession">Cerrar Sesión</a>
					</div>
				</li>
			</ul>
		</nav>
		  
			  <div id="wrapper">
				<!-- Sidebar -->
				<ul class="sidebar navbar-nav " style="background-color:#23bfcc">
				  <li class="nav-item">
					<a class="nav-link text-white" href="admin">
					  <i class="fas fa-fw fa-table"></i>
					  <span>Usuarios</span></a>
				  </li>
				  <li class="nav-item">
					<a class="nav-link text-white" href="historial-logs">
					  <i class="fas fa-fw fa-table"></i>
					  <span>Logs</span></a>
				  </li>
				</ul>
		  
				<div id="content-wrapper" style="background-color:#e6e6e6">
				  <div class="container-fluid white">
					<!-- DataTables Example -->
					<div class="card">
					  	<div class="card">
					  <div class="card-header">
						Historial de actividad
					  </div>
					  <div class="card-body pt-0">
						<div class="table-responsive">
						  <table class="table text-center" id="dataTable" width="100%" cellspacing="0">
							<thead>
							  <tr>
							    <th class="align-middle">Id del Usuario</th>
							  	<th class="align-middle">Funcionalidad</th>
								<th class="align-middle">Fecha y Hora</th>
								<th class="align-middle">Descripcion</th>
							  </tr>
							</thead>
							<tbody>
								<c:forEach items = "${historial}"  var="historial">
								  <tr>
								    <td class="align-middle">${historial.getUsuario()}</td>
								  	<td class="align-middle">${historial.getFuncionalidad()}</td>
									<td class="align-middle">${historial.getFechaModificacion()}</td>
									<td class="align-middle">${historial.getDescripcion()}</td>
								  </tr> 
								</c:forEach>
							</tbody>
						  </table>
						</div>
					  </div>
					</div>
					</div>
				  </div>
				  <!-- /.container-fluid --> 
				</div>
				<!-- /.content-wrapper -->  
			  </div>
			  <!-- /#wrapper -->
		  
	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>