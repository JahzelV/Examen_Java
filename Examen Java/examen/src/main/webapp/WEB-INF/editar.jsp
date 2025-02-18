<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Editar Serie</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
	<div class="container">
		<header class="d-flex justify-content-between align-items-center">
			<h1>Bienvenido ${usuarioEnSesion.nombre}</h1>
			
			<a href="/compras/${usuarioEnSesion.id}" >Mis Compras</a>
			
			<a href="/favoritos/${usuarioEnSesion.id}" >Favoritos</a>
			
			<a href="/dashboard" >Series</a>
			
			<a href="/nuevo" >Agregar</a>
			
			<a class="btn btn-danger" href="/logout">Cerrar Sesion</a>
		</header>
		<div class="row">
			<h2>Editar Serie</h2>
			<form:form action="/actualizar/${serie.id}" method="POST" modelAttribute="serie">
				<input type="hidden" name="_method" value="PUT" />
				<div>
					<form:label path="titulo" >Titulo:</form:label>
					<form:input path="titulo" class="form-control" />
					<form:errors path="titulo" class="text-danger" />
				</div>
				<div>
					<form:label path="anio" >Anio:</form:label>
					<form:input path="anio" class="form-control" />
					<form:errors path="anio" class="text-danger" />
				</div>
				<div>
					<form:label path="descripcion" >Descripcion:</form:label>
					<form:textarea path="descripcion" class="form-control" />
					<form:errors path="descripcion" class="text-danger" />
				</div>
				<div>
					<form:label path="urlImagen" >URL imagen:</form:label>
					<form:input path="urlImagen" class="form-control" />
					<form:errors path="urlImagen" class="text-danger" />
				</div>
				<div>
					<form:label path="cantidad" >Cantidad:</form:label>
					<form:input path="cantidad" class="form-control" />
					<form:errors path="cantidad" class="text-danger" />
				</div>
				<div>
					<form:label path="precio" >Precio:</form:label>
					<form:input path="precio" class="form-control" />
					<form:errors path="precio" class="text-danger" />
				</div>
				
				<form:hidden path="creador" value="${usuarioEnSesion.id}" />
				<input type="submit" class="btn btn-success mt-3" value="Guardar" >
			</form:form>
			<form action="/borrar/${serie.id}" method="POST" >
				<input type="hidden" name="_method" value="DELETE" />
				<input type="submit" value="Borrar" class="btn btn-danger" />
			</form>
		</div>
	</div>
</body>
</html>