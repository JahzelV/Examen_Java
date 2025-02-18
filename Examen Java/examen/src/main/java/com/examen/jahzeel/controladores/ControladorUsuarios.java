package com.examen.jahzeel.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.examen.jahzeel.modelos.LoginUsuario;
import com.examen.jahzeel.modelos.Usuario;
import com.examen.jahzeel.servicios.ServicioUsuarios;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ControladorUsuarios {

	

	
	@Autowired
	private ServicioUsuarios servUsuario;
	
	
	
	
	@GetMapping("/")
	public String index(@ModelAttribute("nuevoUsuario") Usuario nuevoUsuario,
						@ModelAttribute("loginUsuario") LoginUsuario loginUsuario) {
		
		return "index.jsp";
	}
	
	
	
	@PostMapping("/registro")
	public String registro(@Valid @ModelAttribute("nuevoUsuario") Usuario nuevoUsuario,
							BindingResult result,
							Model model,
							HttpSession session) {
		
		servUsuario.registrar(nuevoUsuario, result);
		
		if(result.hasErrors()) {
		
			model.addAttribute("loginUsuario", new LoginUsuario());
			
			return "index.jsp";
		} else {
			
			//Guardo al nuevo usuario en sesion
			session.setAttribute("usuarioEnSesion", nuevoUsuario); //Guardo al objeto usuario
			
			return "redirect:/dashboard";
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/login")
	public String login(@ModelAttribute("loginUsuario") LoginUsuario loginUsuario) {
		return "login.jsp";
	}
	
	
	
	
	
	
	@PostMapping("/iniciarSesion")
	public String iniciarSesion(@Valid @ModelAttribute("loginUsuario") LoginUsuario loginUsuario,
						BindingResult result,
						Model model,
						HttpSession session) {
		
		Usuario usuarioIntentandoLogin = servUsuario.login(loginUsuario, result);
		
		if(result.hasErrors()) {
			
			model.addAttribute("nuevoUsuario", new Usuario());
			
			return "login.jsp";
		} else {
			
			session.setAttribute("usuarioEnSesion", usuarioIntentandoLogin); // Guardo el objeto Usuario
			
			return "redirect:/dashboard";
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.removeAttribute("usuarioEnSesion");
		return "redirect:/";
		
	}
	
	
	
}
