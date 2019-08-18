package ar.edu.unlam.tallerweb1.servicios;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import ar.edu.unlam.tallerweb1.dao.HistorialPasswordDao;
import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.HistorialPassword;
import ar.edu.unlam.tallerweb1.modelo.PBKDF2;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Service("servicioUsuario")
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario{

	@Inject
	private UsuarioDao usuarioDao;
	@Inject
	private HistorialPasswordDao historialPasswordDao;
	@Inject
	private ServicioLog servicioLog;

	private static final List<String> contraseniasNoPermitidas = Arrays.asList("123456", "abc123","usuario123456","admin","root","admin123456");
	private static final List<String> emailsNoPermitidos = Arrays.asList("admin", "admin@admin.com", "christian_estel87@hotmail.com","usuario2@usuario2.com","usuario3@usuario3.com");
	
	@Override
	public void cambiarContrasenia(Long idUsuario, String contrasenia) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		try {
			
			String contraseniaEncrypt = PBKDF2.generateStorngPasswordHash(contrasenia);
			
			usuarioDao.cambiarContrasenia(idUsuario, contraseniaEncrypt);	
			historialPasswordDao.SaveHistorialPassword(idUsuario, contraseniaEncrypt);
			
			String funcionalidad = "Cambio de contraseña";
			String mensajeLog;
			mensajeLog = String.format("\"El usuario %s cambio su contrasenia. ", Long.toString(idUsuario));

			servicioLog.guardarLog(idUsuario, funcionalidad, mensajeLog);
			
		} catch (Exception e) {
			
			String funcionalidad = "Cambio de contraseña";
			String mensajeLog;
			mensajeLog = String.format("\"Se ha producido un errr en el cambio de contraseña. ", Long.toString(idUsuario));

			servicioLog.guardarLog(idUsuario, funcionalidad, mensajeLog);
		}
		
	}

	@Override
	public Boolean recuperarContrasenia(Long idUsuario) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		Usuario usuario = usuarioDao.GetUsuarioById(idUsuario);
		
		if(usuario != null) {
			
			String nuevaContrasenia = PBKDF2.generateStorngPasswordHash("123456");
			
			usuarioDao.cambiarContrasenia(idUsuario, nuevaContrasenia);
			
			String funcionalidad = "Recupero de contraseña";
			
			String mensajeLog;
			mensajeLog = String.format("\"El usuario %s recuperó su contrasenia. ", Long.toString(idUsuario));

			servicioLog.guardarLog(idUsuario, funcionalidad, mensajeLog);
			
			return true;
		}else {
			
			return false;
		}	
	}

	@Override
	public Boolean getHabilitado(Long id) {
		return usuarioDao.getHabilitado(id);
	}

	@Override
	public Usuario getUsuarioByEmail(String email) {
		return usuarioDao.getUsuarioByEmail(email);
	}

	@Override
	public String getPassById(Long id) {
		return usuarioDao.getPassById(id);
	}

	@Override
	public void persistirSolicitudCambioDeContrasenia(Long id, Date fechaSolicitud, String keyLog) {
		usuarioDao.persistirSolicitudCambioDeContrasenia(id, fechaSolicitud, keyLog);
	}

	@Override
	public Integer usuarioCambiandoPass(String id, String keylog) {
		return usuarioDao.usuarioCambiandoPass(id, keylog);
	}

	@Override
	public Long getId(String idEncript) {
		return usuarioDao.getId(idEncript);
	}

	@Override
	public Integer validacionDeUsuario(Usuario usuarioNuevo) {
		
		if(usuarioNuevo.getEmail().isEmpty() || usuarioNuevo.getNombre().isEmpty()){
			return 1 ; // los campos estan vacios
		}else if(emailsNoPermitidos.contains(usuarioNuevo.getEmail())){
			return 5 ;// usuario dentro de la lista de no permitidos
		}
		
		return 0;	
	}
	
	public Integer validarPasswordUsuarioAlRegistrar(Usuario usuarioNuevo) {
		
		if(usuarioNuevo.getPassword().isEmpty() || usuarioNuevo.getPassword2().isEmpty() ){
			return 1 ; // campos icompletos
		}else if(!usuarioNuevo.getPassword().isEmpty() && !usuarioNuevo.getPassword2().isEmpty() && 
				!usuarioNuevo.getPassword().equals(usuarioNuevo.getPassword2())){
			return 2 ; // las contraseñas son distintas
		}else if(contraseniasNoPermitidas.contains(usuarioNuevo.getPassword())
				&& contraseniasNoPermitidas.contains(usuarioNuevo.getPassword2())){
			return 4 ;// contraseña dentro de la lista de no permitidas
		}else if(usuarioNuevo.getPassword().length() < 12 || usuarioNuevo.getPassword2().length() < 12 ){
			return 4 ;// contraseña dentro de la lista de no permitidas
		}else if(usuarioNuevo.getPassword().length() < 12 || usuarioNuevo.getPassword2().length() < 12 ){
			return 3 ;// la contraseña ingresada tiene menos de 12 caracteres
		}else if(StringUtils.containsWhitespace(usuarioNuevo.getPassword()) || StringUtils.containsWhitespace(usuarioNuevo.getPassword2()) ){
			return 4 ;// la contraseña contiene espacios en blanco
		}else if(!this.ValidarCaracteres(usuarioNuevo.getPassword()) || !this.ValidarCaracteres(usuarioNuevo.getPassword2()) ) 			
			return 4 ;// la contraseña contiene emogis

		return 0;	
		
	}
	
	@Override
	public Integer validarPasswordUsuario(Usuario usuarioNuevo) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		String contraseniaActualAlmacenada = usuarioDao.getPassById(usuarioNuevo.getId()); 		

		if(!this.ValidarContraseniasAnteriores(usuarioNuevo)) 			
			return 5 ;
		
		if(usuarioNuevo.getPassword().isEmpty() || usuarioNuevo.getPassword2().isEmpty() ){
			return 1 ; // campos icompletos
		}else if(!PBKDF2.validatePassword(usuarioNuevo.getPassword(), contraseniaActualAlmacenada)){
			return 2 ; // las contraseña actual en el usuario no coindide con 
							   // la contraseña actual ingresada en el formulario
		}else if(contraseniasNoPermitidas.contains(usuarioNuevo.getPassword2()) ){
			return 4 ;// contraseña dentro de la lista de no permitidas
		}else if(usuarioNuevo.getPassword2().length() < 12 ){
			return 3 ;// la contraseña ingresada tiene menos de 12 caracteres
		}else if(StringUtils.containsWhitespace(usuarioNuevo.getPassword2()) ){
			return 4 ;// la contraseña contiene espacios en blanco
		}else if(!this.ValidarCaracteres(usuarioNuevo.getPassword2()) ) 			
			return 4 ;// la contraseña contiene emogis
		
		return 0;	
	}
	
	private boolean ValidarContraseniasAnteriores(Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		List<HistorialPassword> contraseniasAnteriores = historialPasswordDao.getHistorialByIdUsuario(usuario.getId());

		for (HistorialPassword historialPassword : contraseniasAnteriores) {
			
			if(PBKDF2.validatePassword(usuario.getPassword2(), historialPassword.getPassword())
					&& historialPassword.getActiva() == true)
				return false;	
		}
		
		return true;
	}

	private boolean ValidarCaracteres(String password) {
		
		Pattern letter = Pattern.compile("[a-zA-z]");  
		Pattern digit = Pattern.compile("[0-9]");
		Pattern caracteresPermitidos = Pattern.compile("[$@$!%*#?&_]");
		
		Matcher hasLetter = letter.matcher(password);  
	    Matcher hasDigit = digit.matcher(password); 
	    Matcher hasCaracteresPermitidos = caracteresPermitidos.matcher(password); 
	    		
		boolean result = hasLetter.find() && hasDigit.find() && hasCaracteresPermitidos.find() && !EmojiUtils.containsEmoji(password);
			
		return result;
	};
	
	@Override
	public void enviarEmail(String email,String msj){
		  String host="c1360878.ferozo.com";  
		  final String user="unlam@christianperalta.com";//change accordingly  
		  final String password="M5a9h@26yF";//change accordingly  
		    
		  String to= email;//change accordingly  
		  
		   //Get the session object  
		   Properties props = new Properties();  
		   props.put("mail.smtp.host",host);  
		   props.put("mail.smtp.auth", "true");  
		     
		    Session session = Session.getDefaultInstance(props,  
		    new javax.mail.Authenticator() {  
		      protected PasswordAuthentication getPasswordAuthentication() {  
		    return new PasswordAuthentication(user,password);  
		      }  
		    });  
		  
		   //Compose the message  
		    try {  
		     MimeMessage message = new MimeMessage(session); 
		     message.addHeader("Content-type", "text/html; charset=UTF-8");
		     message.setFrom(new InternetAddress(user));  
		     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		     message.setSubject("javatpoint"); 
		    
		     message.setText(msj, "UTF-8", "html");

		     Transport.send(message);  
		
		   
		     } catch (MessagingException e) {e.printStackTrace();} 
	}
	
	@Override
	public Usuario GetUsuarioById(Long idUsuario) {
		return usuarioDao.GetUsuarioById(idUsuario);
	}
	
	@Override
	public void verificarCuentasInactivas(){
		usuarioDao.verificarCuentasInactivas();
	}

}


