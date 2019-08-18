package ar.edu.unlam.tallerweb1.modelo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Log {
	
	@Id 
	@GeneratedValue
	private Long id;
	
	private Long idUsuario;
	
	private String funcionalidad;
	
	private String descripcion;
	
	private Date fechaModificacion;
	
	public Log(){}

	public Log(Long usuario, String funcionalidad, String descripcion, Date fechaModificacionDeNota) {
		this.idUsuario = usuario;
		this.funcionalidad = funcionalidad;
		this.descripcion = descripcion;
		this.fechaModificacion = fechaModificacionDeNota;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsuario() {
		return idUsuario;
	}

	public void setUsuario(Long usuario) {
		this.idUsuario = usuario;
	}

	public String getFuncionalidad() {
		return funcionalidad;
	}

	public void setFuncionalidad(String funcionalidad) {
		this.funcionalidad = funcionalidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacionDeNota(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
	
}
