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
public class Nota {
	@Id 
	@GeneratedValue
	private Long id;
	
	@ManyToOne (cascade = {CascadeType.ALL})
	private Usuario usuario;
	
	private Date fechaAlta;
	private Date fechaModificacion;
	private String Descripcion;
	
	public Nota(){}

	public Nota(Usuario usuario, Date fechaAlta, Date fechaModificacion, String nota) {
		this.usuario = usuario;
		this.fechaAlta = fechaAlta;
		this.fechaModificacion = fechaModificacion;
		this.Descripcion = nota;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}	
}
