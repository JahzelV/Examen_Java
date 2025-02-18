<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mostrar Serie - ${serie.titulo}</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
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
			   <h2>${serie.titulo}</h2>
			   <div class="card mb-3">
			     <div class="row g-0">
			       <div class="col-md-4">
			         <img src="${serie.urlImagen}" class="img-fluid rounded-start" alt="serie">
			       </div>
			       <div class="col-md-8">
			         <div class="card-body">
			           <p class="card-text">Descripcion: ${serie.descripcion}</p>
			           <p class="card-text"><small class="text-body-secondary">Año: ${serie.anio}</small></p>
			           <p class="card-text">Usuario creador: ${serie.creador.nombre}</p>
			           <p class="card-text">Precio: $ ${serie.precio}</p>
			           <p class="card-text"><small class="text-body-secondary">Cantidad: ${serie.cantidad}</small></p>
			           <div class="card-footer text-end">

			       
			       
			          <form action="/comprar/${usuario.id}/${serie.id}" method="POST">
			         	<input type="submit" value="Comprar" />
			         </form>
			       
			       
			       
			     </div>
			     <div>
			     	<h5>Compradores:</h5>
			     	<c:if test="${not empty compradores}">
			     		<c:set var="nombresDuplicados" value="" />
			     		<c:forEach var="comprador" items="${compradores}">
			     			<c:if test="${not nombresDuplicados.contains(comprador.nombre)}">
			     			<li> ${comprador.nombre}</li>
			     			<c:set var="nombresDuplicados" value="${nombresDuplicados}, ${comprador.nombre}" />
			     			</c:if>
			     		</c:forEach>
			     	</c:if>
			     	<c:if test="${empty compradores}">
			     	<p>No hay compradores</p>
			     	</c:if>
			     </div>
			         </div>
			       </div>
			     <div class="card-footer text-end">
			      <!-- Agregar a Fav -->
			       <c:if test="${not usuario.seriesFavoritas.contains(serie)}" >
			       	<a href="/agregarFavoritos/${usuario.id}/${serie.id}" ><i class="fa-regular fa-heart"></i></a>
			       </c:if>
			       
			        <c:if test="${usuario.seriesFavoritas.contains(serie)}" >
			       	<a href="/quitarFavoritos/${usuario.id}/${serie.id}" ><i class="fa-solid fa-heart"></i></a>
			       </c:if>
			       
			     </div>
			   </div>
			  </div>
	</div>
</body>
</html>