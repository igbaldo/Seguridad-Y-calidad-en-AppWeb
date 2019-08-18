package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.HistorialPassword;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.inject.Inject;

@Repository("historialPasswordDao")
public class HistorialPasswordDaoImpl implements HistorialPasswordDao {

	@Inject
    private SessionFactory sessionFactory;

	@Override
	public HistorialPassword getPasswordByDesc(String descripcion) {
		  
		final Session session = sessionFactory.getCurrentSession();
		  
		HistorialPassword func = new HistorialPassword(); 
		func.setPassword(descripcion);
		  
		  return (HistorialPassword) session.createCriteria(HistorialPassword.class)
				  .add(Restrictions.eq("password", func.getPassword()))
				  .uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void SaveHistorialPassword(Long idUsuario, String contrasenia) {
		
		final Session session = sessionFactory.getCurrentSession();
		
		HistorialPassword historial = new HistorialPassword(); 
		
		Usuario usuario = (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("id", idUsuario))
				.uniqueResult();
		
		List<HistorialPassword> contraseniasAnt = session.createCriteria(HistorialPassword.class)
				.createAlias("usuario", "usuarioBuscado")
				.add(Restrictions.eq("usuarioBuscado.id",idUsuario))
				.addOrder(Order.desc("fechaUltimaModificacion"))
				.list();
		
		if(contraseniasAnt != null) {
			for (HistorialPassword ant : contraseniasAnt) {
				ant.setActiva(false);
				
				session.update(ant);
			}
		}
		
		historial.setUsuario(usuario);
		historial.setPassword(contrasenia);
		historial.setFechaUltimaModificacion(new java.util.Date());
		historial.setActiva(true);
		
		session.save(historial);
		
	}

	@Override
	public List<HistorialPassword> getHistorialByIdUsuario(Long id) {
		
		final Session session = sessionFactory.getCurrentSession();
		
		List<HistorialPassword> contraseniasAnt = session.createCriteria(HistorialPassword.class)
				.setMaxResults(12)
				.createAlias("usuario", "usuarioBuscado")
				.add(Restrictions.eq("usuarioBuscado.id", id))
				.addOrder(Order.desc("fechaUltimaModificacion"))
				.list();
		
		return contraseniasAnt;
	}

}
