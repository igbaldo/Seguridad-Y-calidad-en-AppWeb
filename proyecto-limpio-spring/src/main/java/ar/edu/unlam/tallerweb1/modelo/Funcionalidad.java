package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Funcionalidad {
	@Id
	@GeneratedValue
	private Long id;
	
	private String descripcion;

	public Funcionalidad(){}
	
	public Funcionalidad(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
