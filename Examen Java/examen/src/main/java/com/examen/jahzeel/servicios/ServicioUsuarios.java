package com.examen.jahzeel.servicios;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.examen.jahzeel.modelos.LoginUsuario;
import com.examen.jahzeel.modelos.Usuario;
import com.examen.jahzeel.repositorios.RepositorioUsuarios;

@Service
public class ServicioUsuarios {

	@Autowired
	private RepositorioUsuarios repoUsuarios;
	
	
	public Usuario registrar(Usuario nuevoUsuario, BindingResult result) {
		
		//Comparar las contrasenas
		String password = nuevoUsuario.getPassword();
		String confirmacion = nuevoUsuario.getConfirmacion();
		if(!password.equals(confirmacion)) {
			//Si NO SON IGUALES
			result.rejectValue("confirmacion", "Matches", "Los passwords no coinciden"); //path(atributo), clave, mensaje
		}
		
		//Revisar que el email no este registrado
		String email = nuevoUsuario.getEmail();
		Usuario existeUsuario = repoUsuarios.findByEmail(email); //Objeto usuario o null
		if(existeUsuario != null) {
			//El correo ya existe en mi BD
			result.rejectValue("email", "Unique", "El correo ya esta registrado");
		}
		
		if(result.hasErrors()) {
			return null;
		} else {
			
			//No hay ERRORES
			//HASHEANDO CONTRASENA
			String passwordHasheado = BCrypt.hashpw(password, BCrypt.gensalt());
			nuevoUsuario.setPassword(passwordHasheado); //Establezco el password hasheado
			
			//Guardando el usuario repoUsuarios.save(nuevoUsuario);
				return repoUsuarios.save(nuevoUsuario);
			
		}
		
		
	}
	
	
	
	
	//Metodo que me haga las validaciones del inicio de sesion
	public Usuario login(LoginUsuario loginUsuario, BindingResult result) {
		
		//Revisar que el email exista en mi BD
		String email = loginUsuario.getEmailLogin();
		Usuario existeUsuario = repoUsuarios.findByEmail(email);
		if(existeUsuario == null) {
			//NO EXISTE ESE USUARIO EN LA TABLA
			result.rejectValue("emailLogin", "Unique", "Email no registrado");
			
		} else {
			
			//Si si existe este email
			//REviso las contrasenas empaten
			if(! BCrypt.checkpw(loginUsuario.getPasswordLogin(), existeUsuario.getPassword())) {
				
				//Si NO EMPATAN, creamos el error de validacion
				result.rejectValue("passwordLogin", "Matches", "Password incorrecto");
				
			}
			
		}
		
		if(result.hasErrors()) {
			return null;
		} else {
			return existeUsuario;
		}
		
	}
	
	
	
	
	
	
	
	
}
