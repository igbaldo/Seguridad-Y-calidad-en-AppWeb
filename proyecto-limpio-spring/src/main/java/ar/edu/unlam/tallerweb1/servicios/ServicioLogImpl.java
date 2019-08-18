package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.dao.FuncionalidadDao;
import ar.edu.unlam.tallerweb1.dao.LogDao;
import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Funcionalidad;
import ar.edu.unlam.tallerweb1.modelo.Log;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Service("servicioLog")
@Transactional
public class ServicioLogImpl implements ServicioLog{

	@Inject
	private LogDao logDao;
	@Inject
	private FuncionalidadDao funcionalidadDao;
	@Inject
	private UsuarioDao usuarioDao;

	@Override
	public void guardarLog(Long idUsuario, String funcionalildad, String mensaje) {
		
		java.util.Date fechaActual = new java.util.Date();
		
		Log log = new Log(idUsuario, funcionalildad, mensaje, fechaActual );	
		
		logDao.guardarLog(log);
	}

	@Override
	public List<Log> getLogByUsuario(Long idUsuario) {
		return logDao.getLogByUsuario(idUsuario);
	}

	@Override
	public List<Log> GetAll() {
		return logDao.getAllLogs();
	}
}
