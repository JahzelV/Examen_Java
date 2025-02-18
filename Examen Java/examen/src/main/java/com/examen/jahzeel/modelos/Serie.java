package com.examen.jahzeel.modelos;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="series")
public class Serie {

	
	
	
	@Id //PK
	@GeneratedValue(strategy=GenerationType.IDENTITY) //AI
	private Long id;
	
	
	@NotBlank(message="El campo de titulo es obligatorio")
	@Size(min=2, message="el titulo debe tener al menos 2 caracteres")
	private String titulo;
	
	
	
	@NotNull(message="El campo de anio es obligatorio") //Cuando es diferente a STRING solo puede ser NOT NULL
	@Min(1900)
	@Max(2026)
	private Integer anio;
	
	
	@NotBlank(message="Descripcion obligatoria")
	@Column(columnDefinition="TEXT")
	private String descripcion;
	
	
	@NotBlank(message="URL obligatorio")
	private String urlImagen;
	
	
	@NotNull(message="La cantidad es obligatoria")
	@Min(1)
	private Integer cantidad;
	
	
	@NotNull(message="Precio obligatorio")
	@Min(1)
	private Double precio;
	
	
	
	
	
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;

	
	
	
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="usuario_id") //LLAVE FORANEA
	private Usuario creador;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	           name = "usuario_serie_favorita",
	           joinColumns = @JoinColumn(name = "serie_id"),
	           inverseJoinColumns = @JoinColumn(name = "usuario_id")
	    )
	    private List<Usuario> favoritos;

	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	           name = "usuario_serie_comprada",
	           joinColumns = @JoinColumn(name = "serie_id"),
	           inverseJoinColumns = @JoinColumn(name = "usuario_id")
	    )
	    private List<Usuario> compradores;
	
	
	
	
	
	
	
	
	
	public Serie() {}






	
	
	
	
	
	
	

















	public List<Usuario> getFavoritos() {
		return favoritos;
	}






























	public void setFavoritos(List<Usuario> favoritos) {
		this.favoritos = favoritos;
	}






























	public List<Usuario> getCompradores() {
		return compradores;
	}






























	public void setCompradores(List<Usuario> compradores) {
		this.compradores = compradores;
	}






























	public Long getId() {
		return id;
	}








	public void setId(Long id) {
		this.id = id;
	}








	public String getTitulo() {
		return titulo;
	}








	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}








	public Integer getAnio() {
		return anio;
	}








	public void setAnio(Integer anio) {
		this.anio = anio;
	}








	public Usuario getCreador() {
		return creador;
	}








	public void setCreador(Usuario creador) {
		this.creador = creador;
	}








	

	
	public String getDescripcion() {
		return descripcion;
	}








	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}








	public String getUrlImagen() {
		return urlImagen;
	}








	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}








	public Integer getCantidad() {
		return cantidad;
	}








	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}








	public Double getPrecio() {
		return precio;
	}








	public void setPrecio(Double precio) {
		this.precio = precio;
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


	
	
	
	@PrePersist //ANTES de crear al usuario 
	protected void onCreate() {
		this.createdAt = new Date(); //DEFAULT CURRENT_TIMESTAMP
	}
	
	@PreUpdate //ANTES de actualizar
	protected void onUpdate() {
		this.updatedAt = new Date(); //DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT TIME_STAMP
	}
	
	
	
	
	
}
