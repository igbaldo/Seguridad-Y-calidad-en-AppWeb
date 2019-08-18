package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.dao.AdminDao;
import ar.edu.unlam.tallerweb1.dao.LogDao;
import ar.edu.unlam.tallerweb1.dao.NotaDao;
import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Funcionalidad;
import ar.edu.unlam.tallerweb1.modelo.Log;
import ar.edu.unlam.tallerweb1.modelo.Nota;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Service("servicioNota")
@Transactional
public class ServicioNotaImpl implements ServicioNota{

	@Inject
	private UsuarioDao usuarioDao;
	
	@Inject
	private NotaDao notaDao;
	
	@Inject
	private ServicioLog servicioLog;

	
	@Override
	public void nuevaNota(Long idUsuario, String texto) {
		
		Usuario usuario = usuarioDao.GetUsuarioById(idUsuario);
		java.util.Date fechaActual = new java.util.Date();
		java.util.Date fechaModificacion = new java.util.Date();
		Nota nota = new Nota(usuario,fechaActual , fechaModificacion,texto);
		
		notaDao.guardarNota(nota);
		
		String funcionalidad = "Ingreso texto nuevo";
		
		String mensajeLog;
		mensajeLog = String.format("\"El usuario %s ingreso una nueva nota. ", Long.toString(idUsuario));

		servicioLog.guardarLog(idUsuario, funcionalidad, mensajeLog);		
	}
	
	@Override
	public List<Nota> getByUsuario(Long idUsuario) {
		return notaDao.getByUsuario(idUsuario);
	}
}
