package com.examen.jahzeel.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.examen.jahzeel.modelos.Serie;
import com.examen.jahzeel.modelos.Usuario;
import com.examen.jahzeel.servicios.ServicioSeries;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ControladorDashboard {

	
	//Servicios
	@Autowired
	private ServicioSeries servicioSeries;
	
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session,
							Model model) {
		
		if(session.getAttribute("usuarioEnSesion") == null) {
			return "redirect:/";
		}
		
		//Generar la lista con las pelis
		List<Serie> series = servicioSeries.todasLasSerie();
		
		//Enviar esa lista a dashboard
		model.addAttribute("series", series);
		
		return "dashboard.jsp";
	}
	
	
	@GetMapping("/nuevo")
	public String nuevo(@ModelAttribute ("nuevaSerie") Serie nuevaSerie,/*Genero objeto vacio de peli para llenar con form:form*/
						HttpSession session /*Permite acceder a la sesion*/) { 
		
		/*==========Revisar que el usuario haya iniciado sesion=========*/
		if(session.getAttribute("usuarioEnSesion") == null) {
			return "redirect:/";
		
		}
		
		return "nuevo.jsp";
	}
	
	
	@PostMapping("/crear")
	public String crear(@Valid @ModelAttribute ("nuevaSerie") Serie nuevaSerie,
						BindingResult result) {
		
		if(result.hasErrors()) {
			return "nuevo.jsp";
		} else {
			servicioSeries.guardarSerie(nuevaSerie);
			return "redirect:/dashboard";
			
		}
		
		
		
	}
	
	
	
	
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") long id,
						@ModelAttribute("serie") Serie serie,
						Model model,
						HttpSession session) {
		
		
		/*==========Revisar que el usuario haya iniciado sesion=========*/
		if(session.getAttribute("usuarioEnSesion") == null) {
			return "redirect:/";
		
		}
		
		
		Serie serieAEditar = servicioSeries.buscarSerie(id);
		
		//Double Check: Usuario En Sesion es el creador
		Usuario usuarioEnSesion = (Usuario)session.getAttribute("usuarioEnSesion"); //Obteniendo de la sesion el objeto usuario
		if(usuarioEnSesion.getId() != serieAEditar.getCreador().getId()) {
			return "redirect:/dashboard";
		}
		
		
		model.addAttribute("serie", serieAEditar);
		
		return "editar.jsp";
		
	}
	
	
	
	
	
	
	@PutMapping("/actualizar/{id}")
	public String actualizar(@Valid @ModelAttribute("serie") Serie serie,
							BindingResult result /*Mostrar e identificar los errores*/ ) {
		
		if(result.hasErrors()) {
			return "editar.jsp";
		} else {
			servicioSeries.guardarSerie(serie);
			return "redirect:/dashboard";
		}
		
		
	}
	
	
	
	
	
	@DeleteMapping("/borrar/{id}")
	public String borrar(@PathVariable("id") Long id) {
		
		servicioSeries.borrarSerie(id);
		return "redirect:/dashboard";
	}
	
	
	
	@GetMapping("/mostrar/{id}")
	public String mostrar(@PathVariable("id") Long id,
							Model model,
							HttpSession session) {
		
		/*========Revisar que el usuario haya iniciado sesion =========*/
		if(session.getAttribute("usuarioEnSesion") == null) {
			return "redirect:/";
		
		}
		
		Serie serie = servicioSeries.buscarSerie(id); //Busco la serie
		List<Usuario> compradores = servicioSeries.obtenerComprasSerie(id);
		
	

		model.addAttribute("compradores", compradores);
		model.addAttribute("serie", serie);
		
		//Enviar el usuario en sesion actualizado
		Usuario usuarioEnSesion = (Usuario)session.getAttribute("usuarioEnSesion"); //Obteniendo de la sesion el objeto usuario
		Usuario usuario = servicioSeries.buscarUsuario(usuarioEnSesion.getId());
		model.addAttribute("usuario", usuario);
		
		
		
		return "mostrar.jsp";
		
	}
	
	
	
	@GetMapping("/agregarFavoritos/{usuarioId}/{serieId}")
	public String agregarFavoritos(@PathVariable("usuarioId") Long usuarioId,
									@PathVariable("serieId") Long serieId,
									HttpSession session) {
		
		/*========Revisar que el usuario haya iniciado sesion =========*/
		if(session.getAttribute("usuarioEnSesion") == null) {
			return "redirect:/";
		
		}
		
		servicioSeries.guardarSerieFavorita(usuarioId, serieId);
		
		return "redirect:/mostrar/"+serieId;
	}
	
	
	
	
	
	
	@GetMapping("/quitarFavoritos/{usuarioId}/{serieId}")
	public String quitarFavoritos(@PathVariable("usuarioId") Long usuarioId,
									@PathVariable("serieId") Long serieId,
									HttpSession session) {
		
		/*========Revisar que el usuario haya iniciado sesion =========*/
		if(session.getAttribute("usuarioEnSesion") == null) {
			return "redirect:/";
		
		}
		
		servicioSeries.quitarSerieFavorita(usuarioId, serieId);
		
		return "redirect:/mostrar/"+serieId;
	}
	
	
	
	
	
	@GetMapping("/favoritos/{id}")
	public String favoritos(@PathVariable("id") Long id,
							HttpSession session,
							Model model) {
		
		
		/*========Revisar que el usuario haya iniciado sesion =========*/
		if(session.getAttribute("usuarioEnSesion") == null) {
			return "redirect:/";
		
		}
		
		List<Serie> seriesFav = servicioSeries.obtenerSeriesFav(id);
		
		model.addAttribute("seriesFav", seriesFav);
		
		
		return "favoritos.jsp";
	}
	
	
	
	
	
	
	@GetMapping("/compras/{usuarioId}")
	public String compras(@PathVariable("usuarioId") Long usuarioId,
							HttpSession session,
							Model model) {
		
		
		/*========Revisar que el usuario haya iniciado sesion =========*/
		if(session.getAttribute("usuarioEnSesion") == null) {
			return "redirect:/";
		
		}
		
		
		List<Serie> seriesCompradas = servicioSeries.obtenerCompras(usuarioId);
		servicioSeries.mostrarOrdenadoPorAnioDesc(usuarioId);
		
		model.addAttribute("seriesCompradas", seriesCompradas);
		
		return "compras.jsp";
		
	}
	
	
	
	@PostMapping("/comprar/{usuarioId}/{serieId}")
	public String comprar(@PathVariable("usuarioId") Long usuarioId,
							@PathVariable("serieId") Long serieId,
							HttpSession session,
							Model model) {
		
		
		/*========Revisar que el usuario haya iniciado sesion =========*/
		if(session.getAttribute("usuarioEnSesion") == null) {
			return "redirect:/";
		
		}
		
		
		servicioSeries.comprarSerie(serieId);
		servicioSeries.guardarCompra(usuarioId, serieId);
		
		servicioSeries.guardarCompradorEnSerie(usuarioId, serieId);
		
		
		return "redirect:/compras/"+usuarioId;
	}
	
	
	
	
	
	
	

}
