package ar.edu.unlam.tallerweb1.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Usuario {

	@Id
	@GeneratedValue
	private Long id;

	@Transient
	private String idE;

	@Column(unique = true)
	private String email;
	private String nombre;

	private String password;
	@Transient
	private String password2;
	private String rol = "user"; // user - admin
	private Boolean habilitado = true; // true: habilitado, false: deshabilitado
	private Date fechaAltaDeUsuario;
	private Date fechaUltimaModificacion;

	private String keyLog; // condigo de hash el cual se guarda al enviar el email para recuperar
							// contraseña
	private Date fechaDeRecuperacionDePass; // se pone la fecha y hora actual al momento de realizar la peticion de
											// recuperar contraseña
	private Boolean recuperandoPass = false; // flag para garantizar que solo se realiza una vez el cambio de contraseña
												// con el link enviado

	private Boolean inicioSessionPorPrimeraVez = false;
	private Boolean deleted = false; // indica si el usuario fue eliminado por falta de actividad (90 dias)
	
	
	public Usuario() {
	}

	public Usuario(String email, String password, String rol) {
		this.email = email;
		this.password = password;
		this.rol = rol;
	}

	public Usuario(String email, String nombre, String password,String rol, Date fechaAltaDeUsuario) {
		this.email = email;
		this.nombre = nombre;
		this.password = password;
		this.rol = rol;
		this.fechaAltaDeUsuario = fechaAltaDeUsuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdE() {
		return idE;
	}

	public void setIdE(String idE) {
		this.idE = idE;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	public Date getFechaAltaDeUsuario() {
		return fechaAltaDeUsuario;
	}

	public void setFechaAltaDeUsuario(Date fechaAltaDeUsuario) {
		this.fechaAltaDeUsuario = fechaAltaDeUsuario;
	}

	public Date getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}

	public void setFechaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public String getKeyLog() {
		return keyLog;
	}

	public void setKeyLog(String keyLog) {
		this.keyLog = keyLog;
	}

	public Date getFechaDeRecuperacionDePass() {
		return fechaDeRecuperacionDePass;
	}

	public void setFechaDeRecuperacionDePass(Date fechaDeRecuperacionDePass) {
		this.fechaDeRecuperacionDePass = fechaDeRecuperacionDePass;
	}

	public Boolean getRecuperandoPass() {
		return recuperandoPass;
	}

	public void setRecuperandoPass(Boolean recuperandoPass) {
		this.recuperandoPass = recuperandoPass;
	}

	public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public Boolean getInicioSessionPorPrimeraVez() {
		return inicioSessionPorPrimeraVez;
	}

	public void setInicioSessionPorPrimeraVez(Boolean inicioSessionPorPrimeraVez) {
		this.inicioSessionPorPrimeraVez = inicioSessionPorPrimeraVez;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
}
