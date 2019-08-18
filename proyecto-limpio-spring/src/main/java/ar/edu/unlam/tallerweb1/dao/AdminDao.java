package ar.edu.unlam.tallerweb1.dao;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface AdminDao {
	
	void cambiarEstado(Long idUsuario, Boolean estado);

}
