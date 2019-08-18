package ar.edu.unlam.tallerweb1.controladores;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Log;
import ar.edu.unlam.tallerweb1.modelo.Nota;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioCaptcha;
import ar.edu.unlam.tallerweb1.servicios.ServicioLog;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.servicios.ServicioNota;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;  

@Controller
public class ControladorUsuario {

	
	@Inject
	private ServicioLogin servicioLogin;
	
	@Inject
	private ServicioUsuario servicioUsuario;
	
	@Inject
	private ServicioNota servicioNota;
	
	@Inject
	private ServicioLog servicioLog;
	
	@RequestMapping(path="/usuario")
	public ModelAndView irAusuario(HttpServletRequest request){
		HttpSession misession= (HttpSession) request.getSession();
		if(misession.getAttribute("sessionId") != null) {
			if(servicioUsuario.getHabilitado((long) misession.getAttribute("sessionId"))){
				ModelMap modelo = new ModelMap();
				
				Usuario usuarioBuscado = servicioUsuario.GetUsuarioById((long) misession.getAttribute("sessionId"));
				
//				if(usuarioBuscado.getInicioSessionPorPrimeraVez() == false){
//					modelo.put("usuario", new Usuario());
//					return new ModelAndView("cambiarPass1VezView",modelo);
//				}
				
				Nota nota = new Nota();
				modelo.put("nota", nota);
				List<Nota> notasUsuario = servicioNota.getByUsuario((long) misession.getAttribute("sessionId"));
				modelo.put("notas", notasUsuario);
				modelo.put("nombre",misession.getAttribute("sessionNombre"));
				modelo.put("id", (long) misession.getAttribute("sessionId"));
				modelo.put("usuario", new Usuario());
				return new ModelAndView("usuario", modelo);
			}else{
				return new ModelAndView("redirect:cerrarSession");
			}	
		}else{
			return new ModelAndView("redirect:login");
		}
	}
	
	@RequestMapping(path="/usuario-historial")
	public ModelAndView irAusuarioHistorial(HttpServletRequest request){
		HttpSession misession= (HttpSession) request.getSession();
		ModelMap modelo = new ModelMap();
		
		Usuario usuarioBuscado = servicioUsuario.GetUsuarioById((long) misession.getAttribute("sessionId"));
		
//		if(usuarioBuscado.getInicioSessionPorPrimeraVez() == false){
//			modelo.put("usuario", new Usuario());
//			return new ModelAndView("cambiarPass1VezView",modelo);
//		}
		
		if(misession.getAttribute("sessionId") != null) {
			if(servicioUsuario.getHabilitado((long) misession.getAttribute("sessionId"))){
				
				List<Log> logUsuario = servicioLog.getLogByUsuario((long) misession.getAttribute("sessionId"));
				modelo.put("logsUsuario", logUsuario);
				modelo.put("id", (long) misession.getAttribute("sessionId"));
				modelo.put("usuario", new Usuario());
				modelo.put("nombre",misession.getAttribute("sessionNombre"));
				return new ModelAndView("usuario-historial",modelo);
			}else{
				return new ModelAndView("redirect:cerrarSession");
			}
		}else{
			return new ModelAndView("redirect:login");
		}
	}

	@RequestMapping(path="/registrarUsuarioView")
	public ModelAndView irAregistrarUsuarioView(HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException{
		ModelMap modelo = new ModelMap();
		HttpSession misession= (HttpSession) request.getSession();
		if(misession.getAttribute("sessionId") == null)misession.invalidate();
		else modelo.put("rol", misession.getAttribute("ROL"));
		servicioLogin.cargarDatos();	
		modelo.put("usuario", new Usuario());
		return new ModelAndView("registrarUsuarioView",modelo);
	}

	@RequestMapping(path="/registrar-usuario", method = RequestMethod.POST)
	public ModelAndView registrarUsuario(@ModelAttribute("usuario") Usuario usuarioNuevo,HttpServletRequest request){
		
	    ModelMap modelo = new ModelMap();
	    
	    Integer validacionUsuario = servicioUsuario.validacionDeUsuario(usuarioNuevo);
	    Integer validacionPassword = servicioUsuario.validarPasswordUsuarioAlRegistrar(usuarioNuevo);
	
	    String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
	    boolean verificaCaptcha = ServicioCaptcha.verify("6LeUwKUUAAAAANonvOMzybP_z_rJukkVktfN6wE8", gRecaptchaResponse);
	    
	    Usuario usuarioBuscado = null;
	    usuarioBuscado = servicioUsuario.getUsuarioByEmail(usuarioNuevo.getEmail());
	    Boolean usuarioConCuentaEliminada = false;
	    if(usuarioBuscado != null ){
	    	if(usuarioBuscado.getDeleted()) usuarioConCuentaEliminada = true;
	    }
	    
	    
		try{	
			
			if(validacionUsuario == 0 && validacionPassword == 0 && verificaCaptcha && !usuarioConCuentaEliminada) {
				servicioLogin.registrarUsuario(usuarioNuevo);
				modelo.put("errorRegistro", 0);
				modelo.put("msjregistro", "Se registro exitosamente, <a href='login'>inicie sesión</a>");
			}else if(validacionUsuario == 5 && validacionPassword == 0 && verificaCaptcha && usuarioConCuentaEliminada) {
				modelo.put("errorRegistro", 1);
				modelo.put("msjregistro", "Ya existia una cuenta con el mismo email, comuniquese con el administrador");
			}else if(validacionUsuario == 1 || validacionPassword == 1){
				modelo.put("errorRegistro", 1);
				modelo.put("msjregistro", "Complete todos los campos");
			}else if(validacionUsuario == 5){
				modelo.put("errorRegistro", 1);
				modelo.put("msjregistro", "El usuario ingresado no se encuentra disponible");
			}else if(validacionPassword == 2){
				modelo.put("errorRegistro", 1);
				modelo.put("msjregistro", "Las contraseñas son distintas");
			}else if(validacionPassword == 3){
				modelo.put("errorRegistro", 1);
				modelo.put("msjregistro", "La contraseña tiene menos de 12 caracteres");
			}else if(validacionPassword == 4){
				modelo.put("errorRegistro", 1);
				modelo.put("msjregistro", "La contraseña ingresada contiene caracteres inválidos");
			}else if(!verificaCaptcha){
				modelo.put("errorRegistro", 1);
				modelo.put("msjregistro", "No paso la verificacion del captcha");
			}
			
		}catch(Exception e){
			modelo.put("errorRegistro", 1);
			modelo.put("msjregistro", "Hubo problemas para dar de alta su usuario");
			System.out.println(e.getMessage());
		}

        
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		return new ModelAndView("registrarUsuarioView",modelo);
	}

	@RequestMapping(path="/registrar-nota", method = RequestMethod.POST)
	public ModelAndView registrarNota(@ModelAttribute("nota") Nota nuevaNota,HttpServletRequest request){
		HttpSession misession= (HttpSession) request.getSession();
		if(misession.getAttribute("sessionId") != null && servicioUsuario.getHabilitado((long) misession.getAttribute("sessionId"))) {
			ModelMap modelo = new ModelMap();
			try{
				if(!nuevaNota.getDescripcion().isEmpty()){
					servicioNota.nuevaNota((long) misession.getAttribute("sessionId"), nuevaNota.getDescripcion());
					modelo.put("errorRegistro", 0);
					modelo.put("msjregistro", "se registro exitosamente");
				}else{
					modelo.put("errorRegistro", 1);
					modelo.put("msjregistro", "complete todos los campos");
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
				modelo.put("errorRegistro", 1);
				modelo.put("msjregistro", "Hubo problemas para dar de alta su nota");
			}
			
			Nota nota = new Nota();
			modelo.put("nota", nota);
	
			List<Nota> notasUsuario = servicioNota.getByUsuario((long) misession.getAttribute("sessionId"));
			modelo.put("notas", notasUsuario);
			modelo.put("id", (long) misession.getAttribute("sessionId"));
			modelo.put("usuario", new Usuario());
			modelo.put("nombre",misession.getAttribute("sessionNombre"));
			return new ModelAndView("usuario", modelo);
		}else{
			return new ModelAndView("redirect:login");
		}
	}
	

	@RequestMapping(path="/cambiar-contrasenia", method = RequestMethod.POST)
	public ModelAndView cambiarContraseña(@ModelAttribute("usuario") Usuario usuarioLogeado ,HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException{

		HttpSession misession= request.getSession();

		if(misession.getAttribute("sessionId") != null && servicioUsuario.getHabilitado((long) misession.getAttribute("sessionId"))) {

			long idUsuarioLogueado = (long)misession.getAttribute("sessionId");
			ModelMap modelo = new ModelMap();
			usuarioLogeado.setId(idUsuarioLogueado);
			Integer validacionContraseña = servicioUsuario.validarPasswordUsuario(usuarioLogeado);

			if(validacionContraseña == 0) {
				try{
					usuarioLogeado.setPassword(usuarioLogeado.getPassword2());
					servicioUsuario.cambiarContrasenia(idUsuarioLogueado, usuarioLogeado.getPassword());
					modelo.put("errorCambio", 0);
					modelo.put("msjcambio", "Se actualizo su contraseña exitosamente");
				}catch(Exception e){
					modelo.put("errorCambio", 1);
					modelo.put("msjcambio", "Hubo problemas para actualizar la contraseña");
				}
			}else if(validacionContraseña == 1){
				modelo.put("errorCambio", 1);
				modelo.put("msjcambio", "Complete todos los campos");
			}else if(validacionContraseña == 2){
				modelo.put("errorCambio", 1);
				modelo.put("msjcambio", "contraseña actual incorrecta");
			}else if(validacionContraseña == 3){
				modelo.put("errorCambio", 1);
				modelo.put("msjcambio", "La contraseña tiene menos de 12 caracteres");
			}else if(validacionContraseña == 4){
				modelo.put("errorCambio", 1);
				modelo.put("msjcambio", "La contraseña ingresada contiene caracteres inválidos");
			}else if(validacionContraseña == 5){
				modelo.put("errorCambio", 1);
				modelo.put("msjcambio", "La contraseña ingresada ya fue utilizada anteriormente.");
			}
			
			List<Nota> notasUsuario = servicioNota.getByUsuario((long) misession.getAttribute("sessionId"));
			modelo.put("notas", notasUsuario);
			modelo.put("nombre",misession.getAttribute("sessionNombre"));
			modelo.put("nota", new Nota());

			return new ModelAndView("usuario",modelo);
		}else{
			return new ModelAndView("redirect:login");
		}	
	}

	@RequestMapping(path="/ingresar-texto", method = RequestMethod.POST)
	public ModelAndView ingresarTexto(Long idUsuario, String texto,HttpServletRequest request){
		HttpSession misession= (HttpSession) request.getSession();
		if(misession.getAttribute("sessionId") != null && servicioUsuario.getHabilitado((long) misession.getAttribute("sessionId"))) {
			ModelMap modelo = new ModelMap();
			
			if(!texto.isEmpty()) {
				
				servicioNota.nuevaNota(idUsuario, texto);
				
			}else {
				modelo.put("errorIngreso", 1);
				modelo.put("msjIngreso", "Debe Ingresar un texto.");
			}
	
			return new ModelAndView("usuario",modelo);
		}else{
			return new ModelAndView("redirect:login");
		}
	}
   
	@RequestMapping(path="/recuperarContrasenia", method= RequestMethod.GET)
	public ModelAndView recuperarContrasenia(@RequestParam(value="email") String email){
		
		Usuario usuarioBuscado = null;
		usuarioBuscado = servicioUsuario.getUsuarioByEmail(email);
		ModelMap modelo = new ModelMap();
		
		if(usuarioBuscado != null){
			String idEncriptado = Md5Crypt.md5Crypt(usuarioBuscado.getId().toString().getBytes());
			Date fechaSolicitud = new Date();
			String keyLog = Md5Crypt.md5Crypt((usuarioBuscado.getId().toString()+ fechaSolicitud.toString()).getBytes());
			String link = "http://localhost:8080/proyecto-limpio-spring/obteniendoPass?id="+idEncriptado+"&keylog="+keyLog;
			//String msj = usuarioBuscado.getNombre() + ", este es un mensaje para recuperar tu contraseña por favor entre al siguiente enlace: <a href='christianperalta.com'>enlace</a>" ;
			String msj = "<h3>"+ usuarioBuscado.getNombre() +", si ha solicitado recuperar su contraseña haga click en el siguiente <a href='"+link+"'>Link</a> </h3>";

			servicioUsuario.persistirSolicitudCambioDeContrasenia(usuarioBuscado.getId(),fechaSolicitud,keyLog);
			
			servicioUsuario.enviarEmail(usuarioBuscado.getEmail(),msj);
			modelo.put("errorRegistro", 0);
			modelo.put("msjregistro", "Se envió un email a su cuenta de correo");
		}else{
			modelo.put("errorRegistro", 1);
			modelo.put("error", "El usuario con el email ingresado no existe");
		}
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		return new ModelAndView("login",modelo);
	}
	
	@RequestMapping(path="/obteniendoPass", method= RequestMethod.GET)
	public ModelAndView obteniendoPass(@RequestParam(value="id") String id, @RequestParam(value="keylog") String keylog){
		ModelMap modelo = new ModelMap();
		modelo.put("usuario", new Usuario());
		// busco si el usuario indicado esta cambiando de contraseña, si es asi entro a la
		// vista de cambiarcontraseña si no es asi voy a otra vista
		Integer respuesta = servicioUsuario.usuarioCambiandoPass(id,keylog);
		
		if(respuesta == 0) {
			modelo.put("errorRegistro", 1);
			modelo.put("msjregistro", "error al querer recuperar la contraseña");
			return new ModelAndView("redirect: login");
		}else if(respuesta == 1){
			modelo.put("usuario", new Usuario());
			modelo.put("id", id);
			return new ModelAndView("cambiarContrasenia",modelo);
		}else{
			modelo.put("errorRegistro", 1);
			modelo.put("msjregistro", "error al querer recuperar la contraseña");
			return new ModelAndView("redirect: login");
		}

	}
	
	
	@RequestMapping(path="/actualizarPass", method = RequestMethod.POST)
	public ModelAndView actualizarPass(@ModelAttribute("usuario") Usuario usuario,HttpServletRequest request){
		Long idUsuario = this.servicioUsuario.getId(usuario.getIdE()) ;
		if(idUsuario != 0 ) {
			ModelMap modelo = new ModelMap();
			String contraseniaAnt = usuario.getPassword();
			String contraseniaNueva = usuario.getPassword2();
			try{
				if(!contraseniaAnt.isEmpty() && !contraseniaNueva.isEmpty()){
					if(contraseniaAnt.equals(contraseniaNueva)){
							if(contraseniaAnt.length() >= 12 && contraseniaNueva.length() >= 12){
								servicioUsuario.cambiarContrasenia(idUsuario, contraseniaNueva);
								//System.out.println("cambiando la pass");
								modelo.put("errorCambio", 3);
								modelo.put("msjcambio", "Contraseña actualizada ya puede <a href='login'>iniciar sesión</a>");
								//enviar email al usuario con la contraseña en texto
								/*Usuario user = servicioUsuario.GetUsuarioById(idUsuario);
								String msj = "<h3>"+ user.getNombre() +", su nueva contraseña es <span style='color:green'>"+contraseniaNueva+"</span></h3>";

								servicioUsuario.enviarEmail(user.getEmail(), msj);*/
							}else{
								modelo.put("errorCambio", 1);
								modelo.put("msjcambio", "las contraseñas tienen menos de 12 caracteres");
							}
						
						}
						else{
							modelo.put("errorCambio", 1);
							modelo.put("msjcambio", "las contraseñas son distintas");
						}
				}else{
					modelo.put("errorCambio", 1);
					modelo.put("msjcambio", "complete todos los campos");
				}
			}catch(Exception e){
				//System.out.println(e.getMessage());
				modelo.put("errorCambio", 1);
				modelo.put("msjcambio", "Hubo problemas para actualizar la contraseña");
			}
			modelo.put("id", usuario.getIdE());
			modelo.put("usuario", new Usuario());
			return new ModelAndView("cambiarContrasenia",modelo);
		}else{
			return new ModelAndView("redirect:login");
		}
	}
	
	@RequestMapping(path="/cambiarPass1VezView", method= RequestMethod.GET)
	public ModelAndView cambiarPass1VezView(){
		ModelMap modelo = new ModelMap();
		modelo.put("usuario", new Usuario());
	    return new ModelAndView("cambiarPass1VezView",modelo);
	}
	
	@RequestMapping(path="/actualizarPassPorPrimeraVez", method = RequestMethod.POST)
	public ModelAndView actualizarPassPorPrimeraVez(@ModelAttribute("usuario") Usuario usuario,HttpServletRequest request){
		HttpSession misession= (HttpSession) request.getSession();
		Long idUsuario = (Long) misession.getAttribute("sessionId");// this.servicioUsuario.getId(usuario.getIdE()) ;
		if(idUsuario != 0 ) {
			ModelMap modelo = new ModelMap();
			String contraseniaAnt = usuario.getPassword();
			String contraseniaNueva = usuario.getPassword2();
			try{
				if(!contraseniaAnt.isEmpty() && !contraseniaNueva.isEmpty()){
					if(contraseniaAnt.equals(contraseniaNueva)){
							if(contraseniaAnt.length() >= 12 && contraseniaNueva.length() >= 12){
								servicioUsuario.cambiarContrasenia(idUsuario, contraseniaNueva);
								modelo.put("errorCambio", 3);
								modelo.put("msjcambio", "Muchas gracias, ya puede ir a su <a href='usuario'>panel de usuario</a>");
							}else{
								modelo.put("errorCambio", 1);
								modelo.put("msjcambio", "las contraseñas tienen menos de 12 caracteres");
							}
						}
						else{
							modelo.put("errorCambio", 1);
							modelo.put("msjcambio", "las contraseñas son distintas");
						}
				}else{
					modelo.put("errorCambio", 1);
					modelo.put("msjcambio", "complete todos los campos");
				}
			}catch(Exception e){
				modelo.put("errorCambio", 1);
				modelo.put("msjcambio", "Hubo problemas para actualizar la contraseña");
			}
			modelo.put("id", usuario.getIdE());
			modelo.put("usuario", new Usuario());
			return new ModelAndView("cambiarPass1VezView",modelo);
		}else{
			return new ModelAndView("redirect:login");
		}
	}
	
}
