package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Log;

public interface ServicioLog {

	void guardarLog(Long idUsuario, String funcionalidad, String descripcion);
	
	List<Log> getLogByUsuario(Long idUsuario);

	List<Log> GetAll();
}
