package ar.edu.unlam.tallerweb1.servicios;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.dao.LogDao;
import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Log;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

// Implelemtacion del Servicio de usuarios, la anotacion @Service indica a Spring que esta clase es un componente que debe
// ser manejado por el framework, debe indicarse en applicationContext que busque en el paquete ar.edu.unlam.tallerweb1.servicios
// para encontrar esta clase.
// La anotacion @Transactional indica que se debe iniciar una transaccion de base de datos ante la invocacion de cada metodo del servicio,
// dicha transaccion esta asociada al transaction manager definido en el archivo spring-servlet.xml y el mismo asociado al session factory definido
// en hibernateCOntext.xml. De esta manera todos los metodos de cualquier dao invocados dentro de un servicio se ejecutan en la misma transaccion
@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

	@Inject
	private UsuarioDao servicioUsuarioDao;
	@Inject
	private ServicioLog servicioLog;

	@Override
	public Usuario consultarUsuario (Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return servicioUsuarioDao.consultarUsuario(usuario);
	}

	@Override
	public void cargarDatos() throws NoSuchAlgorithmException, InvalidKeySpecException {
		servicioUsuarioDao.cargarDatos();
	}

	@Override
	public void registrarUsuario(Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		servicioUsuarioDao.registrarUsuario(usuario);
		
		String mensajeLog;
		mensajeLog = String.format("\" El usuario %s con id = %s se registro en la aplicacion. ", usuario.getNombre(),Long.toString(usuario.getId()));
		
		servicioLog.guardarLog(usuario.getId(), "Regristro de usuario", mensajeLog );
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		return servicioUsuarioDao.obtenerUsuarios();
	}

	@Override
	public void saveLogIngreso(Long idUsuario) {
		
		String mensajeLog;
		mensajeLog = String.format("El usuario con id:%s se logeo en la aplicacion. ", Long.toString(idUsuario));
		
		servicioLog.guardarLog(idUsuario, "Login", mensajeLog );	
	}
	
	@Override
	public void saveLogIntentoIngreso(String mensaje) {
		
		Usuario admin = servicioUsuarioDao.getUsuarioByEmail("admin@admin.com");
		String mensajeLog;
		mensajeLog = String.format(mensaje, Long.toString(admin.getId()));
		
		servicioLog.guardarLog(admin.getId(), "Login", mensajeLog );	
	}

	@Override
	public void cerrarLogSession(Long idUsuario) {
		String mensajeLog;
		mensajeLog = String.format("El usuario con id %s cerro sessión. ", Long.toString(idUsuario));
		
		servicioLog.guardarLog(idUsuario, "Logout", mensajeLog );
	}
	
	@Override
	public void recuperarContraseniaLog(Long idUsuario)  {
		String mensajeLog;
		mensajeLog = String.format("El usuario con id %s recupero su contraseña. ", String.valueOf(idUsuario));
		
		servicioLog.guardarLog(idUsuario, "Recuperar Password", mensajeLog );
	}

}
