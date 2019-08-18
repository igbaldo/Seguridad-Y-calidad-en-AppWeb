package ar.edu.unlam.tallerweb1.dao;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Nota;

public interface NotaDao {

	void guardarNota(Nota nota);
	
	List<Nota> getByUsuario(Long idUsuario);
}
