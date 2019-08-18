package ar.edu.unlam.tallerweb1.modelo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class HistorialPassword {
	@Id
	@GeneratedValue
	private Long id;
	
	private Date fechaUltimaModificacion;
	
	@ManyToOne(cascade = {CascadeType.ALL})
	private Usuario usuario;
	
	private String password;
	private Boolean activa; // true si la pass esta activa - false si no esta activa
	
	public HistorialPassword(){}

	public HistorialPassword(Date fechaUltimaModificacion, Usuario usuario, String password, Boolean activa) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
		this.usuario = usuario;
		this.password = password;
		this.activa = activa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActiva() {
		return activa;
	}

	public void setActiva(Boolean activa) {
		this.activa = activa;
	}
}
