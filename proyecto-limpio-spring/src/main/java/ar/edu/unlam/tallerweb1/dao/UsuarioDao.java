package ar.edu.unlam.tallerweb1.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

// Interface que define los metodos del DAO de Usuarios.
public interface UsuarioDao {
	
	Usuario consultarUsuario (Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException;
	void cargarDatos() throws NoSuchAlgorithmException, InvalidKeySpecException;
	void registrarUsuario(Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException;
	List<Usuario> obtenerUsuarios();
	Usuario GetUsuarioById(Long idUsuario);
	void cambiarContrasenia(Long idUsuario, String contrasenia);
	Boolean getHabilitado(Long id);
	Usuario getUsuarioByEmail(String email);
	String getPassById(Long id);
	void persistirSolicitudCambioDeContrasenia(Long id, Date fechaSolicitud,String keyLog);
	Integer usuarioCambiandoPass(String id, String keylog);
	Long getId(String idEncript);
	void verificarCuentasInactivas();
}
