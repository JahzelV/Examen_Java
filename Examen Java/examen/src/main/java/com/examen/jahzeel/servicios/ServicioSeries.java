package com.examen.jahzeel.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examen.jahzeel.modelos.Serie;
import com.examen.jahzeel.modelos.Usuario;
import com.examen.jahzeel.repositorios.RepositorioSeries;
import com.examen.jahzeel.repositorios.RepositorioUsuarios;

@Service
public class ServicioSeries {

	@Autowired
	private RepositorioSeries repoSeries;
	
	@Autowired
	private RepositorioUsuarios repoUsuarios;
	
	
	
	//Metodo que me regrese todas las pelis
	
	public List<Serie> todasLasSerie() {
		return repoSeries.findAll();
		
	}
	
	
	//Metodo que me guarde: crea un registro si no tiene id y actualiza si lo tiene
	public Serie guardarSerie(Serie serie) {
		return repoSeries.save(serie);
	}
	
	
	
	//Metodo que busca una peli
	public Serie buscarSerie(Long id) {
		return repoSeries.findById(id).orElse(null);
		
	}
	
	
	
	//Metodo que borre una peli
	public void borrarSerie(Long id) {
		repoSeries.deleteById(id);
	}
	
	
	
	public Usuario buscarUsuario(Long id) {
		return repoUsuarios.findById(id).orElse(null);
		
	}
	
	
	
	public void guardarSerieFavorita(Long usuarioId, Long serieId) {
		
		//Obtener al usuario que quiere agregar a fav
		Usuario miUsuario = buscarUsuario(usuarioId);
		
		//Obtener la peli que quiero agregar
		Serie miSerie = buscarSerie(serieId);
		
		miUsuario.getSeriesFavoritas().add(miSerie);
		repoUsuarios.save(miUsuario);
		
	}
	
	
	
	public void quitarSerieFavorita(Long usuarioId, Long serieId) {
		
		//Obtener al usuario que quiere agregar a fav
		Usuario miUsuario = buscarUsuario(usuarioId);
		
		//Obtener la peli que quiero agregar
		Serie miSerie = buscarSerie(serieId);
		
		miUsuario.getSeriesFavoritas().remove(miSerie);
		repoUsuarios.save(miUsuario);
	
	}
	
	
	
	public List<Serie> obtenerSeriesFav(Long usuarioId) {
		
		Usuario miUsuario = buscarUsuario(usuarioId);
		
		return miUsuario.getSeriesFavoritas();
		
		
	}
	
	
	public Serie comprarSerie(Long id) {
		
		Serie estaSerie = buscarSerie(id);
		
		
		if(estaSerie != null) {
			estaSerie.setCantidad(estaSerie.getCantidad() - 1);
			return repoSeries.save(estaSerie);
		}
		
		return null;
	}
	
	
	
	
	public void guardarCompra(Long usuarioId, Long serieId) {
		
		Usuario miUsuario = buscarUsuario(usuarioId);
		
		Serie miSerie = buscarSerie(serieId);
		
		miUsuario.getSeriesCompradas().add(miSerie);
		
		repoUsuarios.save(miUsuario);
	}
	
	
	
	
	public List<Serie> obtenerCompras(Long usuarioId) {
		
		Usuario miUsuario = buscarUsuario(usuarioId);
		
		return miUsuario.getSeriesCompradas();
	}
	
	
	
	public List<Usuario> obtenerComprasSerie(Long serieId) {
		
		Serie estaSerie = buscarSerie(serieId);
		
		return estaSerie.getCompradores();
		
	}
	
	
	
	public void guardarCompradorEnSerie(Long usuarioId, Long serieId) {
		
		Usuario esteUsuario = buscarUsuario(usuarioId);
		
		Serie estaSerie = buscarSerie(serieId);
		
		if(estaSerie.getCompradores() == null) {
			estaSerie.setCompradores(new ArrayList<>());
			
		}
		
		estaSerie.getCompradores().add(esteUsuario);
		
		guardarSerie(estaSerie);
		
		
		
		
	}
	
	
	
	public List<Serie> mostrarOrdenadoPorAnioDesc(Long usuarioId) {
		
		return repoSeries.findAllByOrderByAnioDesc();
		
		
	}
}
