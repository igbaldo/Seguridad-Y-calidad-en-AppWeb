package ar.edu.unlam.tallerweb1.dao;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Log;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface LogDao {
	
	void guardarLog(Log log);
	List<Log> getLogByUsuario(Long idUsuario);
	List<Log> getAllLogs();
}
