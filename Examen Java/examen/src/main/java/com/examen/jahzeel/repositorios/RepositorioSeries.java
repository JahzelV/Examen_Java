package com.examen.jahzeel.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.examen.jahzeel.modelos.Serie;
import com.examen.jahzeel.modelos.Usuario;

@Repository
public interface RepositorioSeries extends CrudRepository<Serie, Long> {

	
	List<Serie> findAll();
	

	List<Serie> findAllByOrderByAnioDesc();
	
	
	List<Serie> findByTituloContaining(String palabra);
	
	
}
