package ar.edu.unlam.tallerweb1.dao;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.HistorialPassword;

public interface HistorialPasswordDao {
	
	HistorialPassword getPasswordByDesc(String string);
	
	void SaveHistorialPassword(Long idUsuario, String contrasenia);

	List<HistorialPassword> getHistorialByIdUsuario(Long id);
}
