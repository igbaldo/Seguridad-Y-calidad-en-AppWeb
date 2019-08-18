package ar.edu.unlam.tallerweb1.servicios;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

// Interface que define los metodos del Servicio de Usuarios.
public interface ServicioLogin {

	Usuario consultarUsuario(Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException;
	void cargarDatos() throws NoSuchAlgorithmException, InvalidKeySpecException;
	void registrarUsuario(Usuario usuario) throws NoSuchAlgorithmException, InvalidKeySpecException;
	List<Usuario> obtenerUsuarios();
	void saveLogIngreso(Long idUsuario);
	void cerrarLogSession(Long idUsuario);
	void recuperarContraseniaLog(Long idUsuario) ;
	void saveLogIntentoIngreso(String string);
}
