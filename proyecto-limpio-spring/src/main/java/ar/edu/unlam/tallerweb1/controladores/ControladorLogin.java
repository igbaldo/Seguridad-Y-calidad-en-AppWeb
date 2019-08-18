package ar.edu.unlam.tallerweb1.controladores;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioAdmin;
import ar.edu.unlam.tallerweb1.servicios.ServicioCaptcha;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;

@Controller
public class ControladorLogin {

	private int cantIntentoIngreso = 0;
	private int contadorDeIngresosConCaptcha = 0;
	private Boolean captchaActivadoLogin = false;
	
	// La anotacion @Inject indica a Spring que en este atributo se debe setear (inyeccion de dependencias)
	// un objeto de una clase que implemente la interface ServicioLogin, dicha clase debe estar anotada como
	// @Service o @Repository y debe estar en un paquete de los indicados en applicationContext.xml
	@Inject
	private ServicioLogin servicioLogin;
	@Inject
	private ServicioUsuario servicioUsuario;
	@Inject
	private ServicioAdmin servicioAdmin;
	/*
	@Inject
	private ServicioCache cacheManager;
	*/
	// Este metodo escucha la URL localhost:8080/NOMBRE_APP/login si la misma es invocada por metodo http GET
	@RequestMapping("/login")
	public ModelAndView irALogin(HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
		ModelMap modelo = new ModelMap();
		HttpSession misession= (HttpSession) request.getSession();
		
		if(misession.getAttribute("sessionId") == null)
			misession.invalidate();
		else modelo.put("rol", misession.getAttribute("ROL"));
		
		servicioLogin.cargarDatos();
		
		Usuario usuario = new Usuario();
		modelo.put("usuario", usuario);
		
		servicioUsuario.verificarCuentasInactivas();
		
		return new ModelAndView("login", modelo);
	}

	// Este metodo escucha la URL validar-login siempre y cuando se invoque con metodo http POST
	// El m√©todo recibe un objeto Usuario el que tiene los datos ingresados en el form correspondiente y se corresponde con el modelAttribute definido en el
	// tag form:form
	@RequestMapping(path = "/validar-login", method = RequestMethod.POST)
	public ModelAndView validarLogin(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
		servicioUsuario.verificarCuentasInactivas();
		String urlBaseDelSitio = "http://localhost:8080/proyecto-limpio-spring/";
		String urlDeOrigen = request.getRequestURL().toString();
		boolean contieneUrl = urlDeOrigen.contains(urlBaseDelSitio);

		if (contieneUrl) {
			ModelMap model = new ModelMap();
			Usuario usuarioBuscado = servicioLogin.consultarUsuario(usuario);

			HttpSession session = request.getSession();
			
			if(session.getAttribute("sessionId") != null && usuarioBuscado != null && session.getAttribute("sessionId") == usuarioBuscado.getId()) {
				model.put("error","Ya se encuentra una sesion Activa. Cierrela y vuelva a ingreasar.");
				return new ModelAndView("login", model);
			}
				
			Boolean mostrarCaptcha = false;
			if (usuarioBuscado != null) {
				if (!captchaActivadoLogin) {
					this.cantIntentoIngreso = 0;

					if (usuarioBuscado.getDeleted() != true){
						session.setAttribute("ROL", usuarioBuscado.getRol());
						session.setAttribute("sessionId", usuarioBuscado.getId());
						session.setAttribute("sessionNombre", usuarioBuscado.getNombre());
						session.setMaxInactiveInterval(15*60);
	
						Long id = usuarioBuscado.getId();
						servicioLogin.saveLogIngreso(id);
	
							if (usuarioBuscado.getRol() == "admin") {
								return new ModelAndView("redirect:/admin");
							} else {
								if (usuarioBuscado.getHabilitado() == true) {
									/*if(usuarioBuscado.getInicioSessionPorPrimeraVez() == false){
									model.put("usuario", new Usuario());
									return new ModelAndView("cambiarPass1VezView",model);
								}else*/ return new ModelAndView("redirect:/usuario");
								} else {
									model.put("error",
											"Su usuario se encuentra deshabilitado. Comuniquese con el Administrador");
									servicioLogin.saveLogIntentoIngreso("Intento Ingreso: Usuario Deshabilitado.");
									return new ModelAndView("login", model);
								}
							}
					}else{
						model.put("error","Su usuario fue eliminado por inactividad. Comuniquese con el Administrador");
						return new ModelAndView("login", model);
					}	
					
				} else {
					String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
					boolean verificaCaptcha = ServicioCaptcha.verify("6LeUwKUUAAAAANonvOMzybP_z_rJukkVktfN6wE8",
							gRecaptchaResponse);
					if (verificaCaptcha) {
						this.cantIntentoIngreso = 0;
						this.contadorDeIngresosConCaptcha = 0;
						this.captchaActivadoLogin = false;

						session.setAttribute("ROL", usuarioBuscado.getRol());
						session.setAttribute("sessionId", usuarioBuscado.getId());
						session.setAttribute("sessionNombre", usuarioBuscado.getNombre());
						session.setMaxInactiveInterval(15*60);

						Long id = usuarioBuscado.getId();
						servicioLogin.saveLogIngreso(id);

						if (usuarioBuscado.getRol() == "admin") {
							return new ModelAndView("redirect:/admin");
						} else {
							if (usuarioBuscado.getHabilitado() == true) {
								return new ModelAndView("redirect:/usuario");
							} else {
								model.put("error",
										"Su usuario se encuentra deshabilitado. Comuniquese con el Administrador");
								servicioLogin.saveLogIntentoIngreso("Intento Ingreso: Usuario Deshabilitado.");
								return new ModelAndView("login", model);
							}
						}
					} else {
						model.put("error", "No verifico la prueba del captcha");
						servicioLogin.saveLogIntentoIngreso("Intento Ingreso: No verifico el captcha.");
						mostrarCaptcha = true;
						model.put("mostrarCaptcha", mostrarCaptcha);
						this.contadorDeIngresosConCaptcha++;
						if (this.contadorDeIngresosConCaptcha < 2)
							return new ModelAndView("login", model);
						else {
							servicioAdmin.cambiarEstadoUsuario(usuarioBuscado.getId(), false);
							this.cantIntentoIngreso = 0;
							this.contadorDeIngresosConCaptcha = 0;
							model.put("error", "Su usuario ha sido deshabilitado. Comuniquese con el Administrador");
							servicioLogin.saveLogIntentoIngreso("Intento Ingreso: Maximo de intentos superado.");//
							// maximo de intentos de ingreso superado
							return new ModelAndView("login", model);
						}
					}
				}
			} else {
				Usuario usuarioIntento = servicioUsuario.getUsuarioByEmail(usuario.getEmail());

				if (usuarioIntento != null) {
					if (cantIntentoIngreso < 3) {
						Usuario user = servicioLogin.consultarUsuario(usuario);
						if (user == null) {
							model.put("error", "Usuario o clave incorrecta");
							servicioLogin.saveLogIntentoIngreso("Intento de Ingreso: usuario o contraseÒa incorrecta.");
							this.cantIntentoIngreso++;
							if (cantIntentoIngreso == 3) {
								mostrarCaptcha = true;
								captchaActivadoLogin = true;
							}
						}
					} else if (cantIntentoIngreso >= 5 && contadorDeIngresosConCaptcha < 2) {
						mostrarCaptcha = true;
						String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
						boolean verificaCaptcha = ServicioCaptcha.verify("6LeUwKUUAAAAANonvOMzybP_z_rJukkVktfN6wE8",
								gRecaptchaResponse);
						Usuario user = servicioLogin.consultarUsuario(usuario);
						if (user == null || !verificaCaptcha) {
							model.put("error", "Error al querer ingresar");
							servicioLogin.saveLogIntentoIngreso(
									"Intento de Ingreso: usuario, contraseÒa incorrecta o error en verificacion del captcha.");
							this.contadorDeIngresosConCaptcha++;
						}
					} else {
						servicioAdmin.cambiarEstadoUsuario(usuarioIntento.getId(), false);
						this.cantIntentoIngreso = 0;
						this.contadorDeIngresosConCaptcha = 0;
						model.put("error", "Su usuario ha sido deshabilitado. Comuniquese con el Administrador");
						servicioLogin.saveLogIntentoIngreso("Intento Ingreso: Maximo de intentos superado.");//
						// maximo de intentos de ingreso superado
						return new ModelAndView("login", model);
					}

				}
			}

			model.put("mostrarCaptcha", mostrarCaptcha);
			model.put("error", "Usuario o clave incorrecta");
			servicioLogin.saveLogIntentoIngreso("Intento de Ingreso: usuario o contraseÒa incorrecta.");
			return new ModelAndView("login", model);
		} else {
			return new ModelAndView("redirect: login");
		}

	}

	// Escucha la URL /home por GET, y redirige a una vista.
	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public ModelAndView irAHome() {
		return new ModelAndView("home");
	}

	// Escucha la url /, y redirige a la URL /login, es lo mismo que si se invoca la url /login directamente.
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView inicio() {
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping(path = "/cerrarSession", method = RequestMethod.GET)
	public ModelAndView irACerrarSesision(HttpServletRequest request) {
		HttpSession misession= (HttpSession) request.getSession();
		
		servicioLogin.cerrarLogSession((Long)misession.getAttribute("sessionId"));
		
		misession.removeAttribute("sessionId");
		misession.removeAttribute("sessionNombre");
		misession.removeAttribute("ROL");
		return new ModelAndView("redirect:login");
	}
	
}
