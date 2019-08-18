package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Nota;

public interface ServicioNota {
	
	void nuevaNota(Long idUsuario, String nota);

	List<Nota> getByUsuario(Long idUsuario);
}
