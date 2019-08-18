package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Log;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioAdmin {
	
	void cambiarEstadoUsuario(Long idUsuario, Boolean estado);
	
	List<Usuario> listarUsuarios();

	List<Log> getAllLogs();
	
	List<Log> getLogsByIdUsuario(Long idUsuario);
}
