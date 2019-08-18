package ar.edu.unlam.tallerweb1.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.codec.digest.Md5Crypt;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Funcionalidad;
import ar.edu.unlam.tallerweb1.modelo.HistorialPassword;
import ar.edu.unlam.tallerweb1.modelo.Nota;
import ar.edu.unlam.tallerweb1.modelo.PBKDF2;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Repository("usuarioDao")
public class UsuarioDaoImpl implements UsuarioDao {
	
	static Boolean datosCargados = false; // flag para cargar los datos una sola vez
	
	// Como todo dao maneja acciones de persistencia, normalmente estarÃ¡ inyectado el session factory de hibernate
	// el mismo estÃ¡ difinido en el archivo hibernateContext.xml
	@Inject
    private SessionFactory sessionFactory;

	@Override
	public Usuario consultarUsuario(Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException {
		final Session session = sessionFactory.getCurrentSession();
		Usuario resultado = null;
		Usuario usuarioExistente = null;
		usuarioExistente = (Usuario) session.createCriteria(Usuario.class)
		.add(Restrictions.eq("email", usuario.getEmail()))
		.uniqueResult();
		
		if(usuarioExistente != null){
			if(PBKDF2.validatePassword(usuario.getPassword(), usuarioExistente.getPassword())) 
				resultado = usuarioExistente;
		}

		return resultado;
	}

	@Override
	public void cargarDatos() throws NoSuchAlgorithmException, InvalidKeySpecException {
		if(!this.datosCargados){
			final Session session = sessionFactory.getCurrentSession();
			this.datosCargados = true;
			Funcionalidad login = new Funcionalidad("Login");
			Funcionalidad logout = new Funcionalidad("Logout");
			Funcionalidad registro = new Funcionalidad("Regristro de usuario");
			Funcionalidad resetPassword = new Funcionalidad("Cambio de contraseña");
			Funcionalidad recuperarContraseña = new Funcionalidad("Recupero de contraseña");
			Funcionalidad ingresartexto = new Funcionalidad("Ingreso texto nuevo");
			Funcionalidad modificacionTexto = new Funcionalidad("Modificacion de texto");
			Funcionalidad habilitarUsuario = new Funcionalidad("Usuario Habilitado");
			Funcionalidad rechazarUsuario = new Funcionalidad("Usuario Deshablitado");
			Funcionalidad verActividadPersonal = new Funcionalidad("Ver Historial de actividad");
			Funcionalidad verActividadUsuarios = new Funcionalidad("Ver Historial de usuarios");
			
			Usuario usuario1 = new Usuario("admin@admin.com",PBKDF2.generateStorngPasswordHash("admin123456") ,"admin");
			Usuario usuario2 = new Usuario("christian_estel87@hotmail.com", PBKDF2.generateStorngPasswordHash("123456"),"user");
			Usuario usuario3 = new Usuario("usuario2@usuario2.com", PBKDF2.generateStorngPasswordHash("usuario123456"), "user");
			Usuario usuario4 = new Usuario("usuario3@usuario3.com", PBKDF2.generateStorngPasswordHash("usuario123456"), "user");
			usuario1.setNombre("admin");
			usuario2.setNombre("Homero");
			usuario3.setNombre("usuario2");
			usuario4.setNombre("usuario3");
			usuario3.setHabilitado(false);
			usuario4.setHabilitado(true);
			Date fechaActual = new Date();
			usuario1.setFechaAltaDeUsuario(fechaActual);
			usuario2.setFechaAltaDeUsuario(fechaActual);
			usuario3.setFechaAltaDeUsuario(fechaActual);
			usuario4.setFechaAltaDeUsuario(fechaActual);
			usuario1.setFechaUltimaModificacion(fechaActual);
			usuario2.setFechaUltimaModificacion(fechaActual);
			usuario3.setFechaUltimaModificacion(fechaActual);
			usuario4.setFechaUltimaModificacion(fechaActual);
			
			session.save(usuario1);
			session.save(usuario2);
			session.save(usuario3);
			session.save(usuario4);
			session.save(login);
			session.save(logout);		
			session.save(registro);
			session.save(resetPassword);
			session.save(recuperarContraseña);
			session.save(ingresartexto);
			session.save(modificacionTexto);
			session.save(habilitarUsuario);
			session.save(rechazarUsuario);
			session.save(verActividadPersonal);
			session.save(verActividadUsuarios);
			
			HistorialPassword hist = new HistorialPassword(new java.util.Date() ,usuario4, usuario4.getPassword(), true);
			session.save(hist);
		}
	}

	@Override
	public void registrarUsuario(Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException {
		final Session session = sessionFactory.getCurrentSession();
		Date fechaActual = new Date();
		usuario.setFechaAltaDeUsuario(fechaActual);
		usuario.setFechaUltimaModificacion(fechaActual);

		usuario.setPassword(PBKDF2.generateStorngPasswordHash(usuario.getPassword()));
		
		session.save(usuario);
	}

	@Override
	public List<Usuario> obtenerUsuarios() {
		final Session session = sessionFactory.getCurrentSession();
		List<Usuario> usuarios = null;
		usuarios = session.createCriteria(Usuario.class)
				.add(Restrictions.eq("rol","user"))
				.list();
		return usuarios;
	}

	@Override
	public Usuario GetUsuarioById(Long idUsuario) {
		
		final Session session = sessionFactory.getCurrentSession();
		
		return (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", idUsuario))
				.uniqueResult();
	}

	@Override
	public void cambiarContrasenia(Long idUsuario, String contrasenia) {
		
		final Session session = sessionFactory.getCurrentSession();

	     Usuario user = (Usuario)session.get(Usuario.class, idUsuario); 
	     user.setPassword(contrasenia);
	     if(user.getInicioSessionPorPrimeraVez() == false)
	    	 user.setInicioSessionPorPrimeraVez(true);
	     
	     session.update(user); 
	}

	@Override
	public Boolean getHabilitado(Long id) {
		final Session session = sessionFactory.getCurrentSession();
		Usuario usuario = null;
		usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
		Boolean resultado = false;
		resultado = usuario.getHabilitado();
		return resultado;
	}

	@Override
	public Usuario getUsuarioByEmail(String email) {
		final Session session = sessionFactory.getCurrentSession();
		Usuario usuario = null;
		usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("email",email))
				.uniqueResult();
		return usuario;
	}

	@Override
	public String getPassById(Long id) {
		final Session session = sessionFactory.getCurrentSession();
		Usuario usuario = null;
		usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id",id))
				.uniqueResult();
		String pass = "";
		pass = usuario.getPassword();
		return pass;
	}

	@Override
	public void persistirSolicitudCambioDeContrasenia(Long id, Date fechaSolicitud, String keyLog) {
		final Session session = sessionFactory.getCurrentSession();
		Usuario usuario = null;
		usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id))
				.uniqueResult();
		
		usuario.setFechaDeRecuperacionDePass(fechaSolicitud);
		usuario.setKeyLog(keyLog);
		usuario.setRecuperandoPass(true);
		session.update(usuario);
	}

	@Override
	public Integer usuarioCambiandoPass(String id, String keylog) {
		Integer respuesta = 0;
		final Session session = sessionFactory.getCurrentSession();
		Usuario usuario = null;
		
		//obtener lista de ids y comparar con el parametro id
		List<Usuario> listaUsuarios = session.createCriteria(Usuario.class)
						.list();
		Long idBuscado = 0L;
		for(Usuario u : listaUsuarios){
			String encrypted2 = Md5Crypt.md5Crypt(u.getId().toString().getBytes(), id);
			if(id.equals(encrypted2)){
				idBuscado = u.getId();
				break;
			}
		}
	
		usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", idBuscado))
				.uniqueResult();

		if(usuario != null){
			// preguntar si el valor de recuperandoPass esta en true
			if(usuario.getRecuperandoPass()){
				// preguntar si el tiempo esta dentro de lo establecido
				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				Date fecha = new Date();
				Long diferenciaDeTiempo = fecha.getTime() - usuario.getFechaDeRecuperacionDePass().getTime();
				//long diffMinutes = diferenciaDeTiempo / (60 * 1000) % 60;
				long diffSeconds = diferenciaDeTiempo / 1000;// % 60;
				//System.out.println(diffSeconds + " segundos");
				if(diffSeconds > 0 && diffSeconds < 120){

					if(keylog.equals(usuario.getKeyLog())){
						usuario.setKeyLog("");
						usuario.setRecuperandoPass(false);
						usuario.setFechaDeRecuperacionDePass(null);
						respuesta = 1; // todo ok para realizar el cammbio de pass
					}else{
						usuario.setKeyLog("");
						usuario.setRecuperandoPass(false);
						usuario.setFechaDeRecuperacionDePass(null);
						respuesta = 4; // no coincide los keylog
					}
				}else{
					respuesta = 3;//error de diferencia de tiempo
					usuario.setKeyLog("");
					usuario.setRecuperandoPass(false);
					usuario.setFechaDeRecuperacionDePass(null);
					//reseteo el keylog, fecha y estado
				}
			}else respuesta = 2; // no esta habilitado el flag recuperandoPass
		}
		System.out.println("respuesta:" + respuesta);
		return respuesta;
	}

	@Override
	public Long getId(String idEncript) {
		final Session session = sessionFactory.getCurrentSession();
		Usuario usuario = null;

		//obtener lista de ids y comparar con el parametro id
		List<Usuario> listaUsuarios = session.createCriteria(Usuario.class)
						.list();
		Long idBuscado = 0L;
		for(Usuario u : listaUsuarios){
			String encrypted2 = Md5Crypt.md5Crypt(u.getId().toString().getBytes(), idEncript);
			if(idEncript.equals(encrypted2)){
				idBuscado = u.getId();
				break;
			}
		}
		return idBuscado;
	}

	@Override
	public void verificarCuentasInactivas() {
		final Session session = sessionFactory.getCurrentSession();
		List<Usuario> listaUsuarios = null;
		
		listaUsuarios = session.createCriteria(Usuario.class)
						.add(Restrictions.and(Restrictions.eq("rol", "user"),Restrictions.eq("deleted", false)))
						.list();
		Date fecha = new Date();
		
		if(listaUsuarios != null ){
			//recorro la lista y aquellos usuarios que tengan mas de 90 dias sin iniciar session los elimino
			for(Usuario user : listaUsuarios){
				Long diferenciaDeTiempo = fecha.getTime() - user.getFechaUltimaModificacion().getTime();
				long diffMinutes = diferenciaDeTiempo / ( 24 * 60 * 60 * 1000) % 60; 
				if(diffMinutes >= 90 ) {
					user.setDeleted(true);
					session.update(user);
				}
			}
		}
	}
}

