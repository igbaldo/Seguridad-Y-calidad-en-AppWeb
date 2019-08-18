package ar.edu.unlam.tallerweb1.servicios;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioUsuario {
	
	void cambiarContrasenia(Long idUsuario, String contrasenia) throws NoSuchAlgorithmException, InvalidKeySpecException;

	Boolean recuperarContrasenia(Long idUsuario) throws NoSuchAlgorithmException, InvalidKeySpecException;
	Boolean getHabilitado(Long id);
	Usuario getUsuarioByEmail(String email);
	String getPassById(Long id);
	void persistirSolicitudCambioDeContrasenia(Long id, Date fechaSolicitud, String keyLog);
	Integer usuarioCambiandoPass(String id, String keylog);
	public Long getId(String idEncript);
	public Integer validacionDeUsuario(Usuario usuarioNuevo);
	public void enviarEmail(String email,String msj);
	public Integer validarPasswordUsuario(Usuario usuarioNuevo) throws NoSuchAlgorithmException, InvalidKeySpecException;
	Integer validarPasswordUsuarioAlRegistrar(Usuario usuarioNuevo);
	Usuario GetUsuarioById(Long idUsuario);
	void verificarCuentasInactivas();
}
