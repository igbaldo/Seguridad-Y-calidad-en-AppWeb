package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Log;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioAdmin;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;
@Controller
public class ContoladorAdmin {
	
	@Inject
	private ServicioAdmin servicioAdmin;
	
	@Inject
	private ServicioUsuario servicioUsuario;
	
	@RequestMapping(path="/admin")
	public ModelAndView admin(HttpServletRequest request){
		HttpSession misession= (HttpSession) request.getSession();
		if(misession.getAttribute("sessionId") != null) {
			ModelMap modelo = new ModelMap();
			List<Usuario> usuarios = servicioAdmin.listarUsuarios();
			modelo.put("usuarios", usuarios);
			return new ModelAndView("admin", modelo);
		}else{
			return new ModelAndView("redirect:login");
		}
	}
	
	@RequestMapping(path="/admin-historial", method= RequestMethod.GET)
	public ModelAndView adminHistorial(@RequestParam(value="idUsuario") Long id,HttpServletRequest request){
		HttpSession misession= (HttpSession) request.getSession();
		servicioUsuario.verificarCuentasInactivas();
		if(misession.getAttribute("sessionId") != null) {
			ModelMap modelo = new ModelMap();
			List<Log> historial = servicioAdmin.getLogsByIdUsuario(id);
			modelo.put("historial", historial);
			return new ModelAndView("admin-historial-usuario",modelo);
		}else{
			return new ModelAndView("redirect:login");
		}
	}
	
	@RequestMapping(path="/historial-logs", method= RequestMethod.GET)
	public ModelAndView historialLogs(HttpServletRequest request){
		HttpSession misession= (HttpSession) request.getSession();
		servicioUsuario.verificarCuentasInactivas();
		if(misession.getAttribute("sessionId") != null) {
			ModelMap modelo = new ModelMap();
			List<Log> historial = servicioAdmin.getAllLogs();
			modelo.put("historial", historial);
			return new ModelAndView("admin-historial",modelo);
		}else{
			return new ModelAndView("redirect:login");
		}
	}
	
	@RequestMapping(path = "/cambiarEstadoUsuario", method = RequestMethod.GET)
	public ModelAndView cambiarEstadoUsuario(@RequestParam(value="idUsuario") Long idUsuario,@RequestParam(value="estado") Boolean estado, HttpServletRequest request) {
		HttpSession misession= (HttpSession) request.getSession();
		if(misession.getAttribute("sessionId") != null) {
			ModelMap model = new ModelMap();
			servicioAdmin.cambiarEstadoUsuario(idUsuario, estado);
			List<Usuario> usuarios = servicioAdmin.listarUsuarios();
			model.put("usuarios", usuarios);	
			return new ModelAndView("admin", model);
		}else{
			return new ModelAndView("redirect:login");
		}
	}
}
