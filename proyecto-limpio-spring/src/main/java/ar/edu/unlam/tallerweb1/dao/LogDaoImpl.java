package ar.edu.unlam.tallerweb1.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Log;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Repository("logDao")
public class LogDaoImpl implements LogDao {
	
	@Inject
    private SessionFactory sessionFactory;

	@Override
	public void guardarLog(Log nuevoLog) {
		
		final Session session = sessionFactory.getCurrentSession();
		
		session.save(nuevoLog);
	}
	
	@Override
	public List<Log> getLogByUsuario(Long idUsuario) {
		final Session session = sessionFactory.getCurrentSession();
		return  session.createCriteria(Log.class)
				.add(Restrictions.eq("idUsuario",idUsuario))
				.addOrder(Order.desc("fechaModificacion"))
				.list();
	}

	@Override
	public List<Log> getAllLogs() {
		final Session session = sessionFactory.getCurrentSession();
		
		 List<Usuario> usuariosNoEliminados = session.createCriteria(Usuario.class)
				.add(Restrictions.eq("deleted", false))
				.list();
		
		 List<Long> listaIds = new ArrayList<Long>();
			for(Usuario usuario : usuariosNoEliminados){
				listaIds.add(usuario.getId());
			}
	
		return  session.createCriteria(Log.class)
		.add(Restrictions.in("idUsuario", listaIds))
		.addOrder(Order.desc("fechaModificacion"))
		.list();

	}
}
