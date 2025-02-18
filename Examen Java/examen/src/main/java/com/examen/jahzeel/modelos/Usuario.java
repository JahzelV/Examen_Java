package com.examen.jahzeel.modelos;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="usuarios")
public class Usuario {

	

	
	@Id //PK
	@GeneratedValue(strategy=GenerationType.IDENTITY) //AI
	private Long id;
	
	
	
	@NotBlank(message="El campo de nombre es obligatorio")
	@Size(min=3, message="El nombre debe tener al menos 3 caracteres")
	private String nombre;
	
	
	
	@NotBlank(message="El campo de apellido es obligatorio")
	@Size(min=3, message="El apellido debe tener al menos 3 caracteres")
	private String apellido;
	
	
	
	@NotBlank(message="El campo de email es obligatorio")
	private String email;
	
	@NotBlank(message="El campo de password es obligatorio")
	@Size(min=8, message="El password debe tener al menos ocho caracteres")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "El password necesita incluir al menos una letra mayúscula, una letra minúscula y un número")
	private String password;
	
	@Transient //No se guarda en BD
	private String confirmacion;
	
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;
	
	
	
	@OneToMany(mappedBy="creador", fetch=FetchType.LAZY) //Empata con atributo
	private List<Serie> seriesCreadas;
	
	
	
	@ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
				name="series_favoritas",
				joinColumns = @JoinColumn(name="usuario_id"),
				inverseJoinColumns = @JoinColumn(name="serie_id")
				)
	private List<Serie> seriesFavoritas;



	
	@ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
				name="series_compradas",
				joinColumns = @JoinColumn(name="usuario_id"),
				inverseJoinColumns = @JoinColumn(name="serie_id")
				)
	private List<Serie> seriesCompradas;
	
	
	
	
	public Usuario() {}





	
	
	
	
	
	
	
	
	
	
	

	public List<Serie> getSeriesCompradas() {
		return seriesCompradas;
	}

















	public void setSeriesCompradas(List<Serie> seriesCompradas) {
		this.seriesCompradas = seriesCompradas;
	}

















	public List<Serie> getSeriesCreadas() {
		return seriesCreadas;
	}

















	public void setSeriesCreadas(List<Serie> seriesCreadas) {
		this.seriesCreadas = seriesCreadas;
	}

















	public List<Serie> getSeriesFavoritas() {
		return seriesFavoritas;
	}

















	public void setSeriesFavoritas(List<Serie> seriesFavoritas) {
		this.seriesFavoritas = seriesFavoritas;
	}

















	public Long getId() {
		return id;
	}






	public void setId(Long id) {
		this.id = id;
	}






	public String getEmail() {
		return email;
	}






	public void setEmail(String email) {
		this.email = email;
	}






	public String getPassword() {
		return password;
	}






	public void setPassword(String password) {
		this.password = password;
	}






	public String getConfirmacion() {
		return confirmacion;
	}






	public void setConfirmacion(String confirmacion) {
		this.confirmacion = confirmacion;
	}






	public Date getCreatedAt() {
		return createdAt;
	}






	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}






	public Date getUpdatedAt() {
		return updatedAt;
	}






	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}






	
	
	
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}






	@PrePersist //ANTES de crear al usuario 
	protected void onCreate() {
		this.createdAt = new Date(); //DEFAULT CURRENT_TIMESTAMP
	}
	
	@PreUpdate //ANTES de actualizar
	protected void onUpdate() {
		this.updatedAt = new Date(); //DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT TIME_STAMP
	}
	
	
	
	
	
	
	
	
	
	
}
