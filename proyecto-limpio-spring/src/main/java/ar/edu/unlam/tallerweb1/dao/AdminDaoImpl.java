package ar.edu.unlam.tallerweb1.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

import java.util.List;

import javax.inject.Inject;

@Repository("adminDao")
public class AdminDaoImpl implements AdminDao {

	@Inject
    private SessionFactory sessionFactory;

	@Override
	public void cambiarEstado(Long idUsuario, Boolean estado) {
		
		final Session session = sessionFactory.getCurrentSession();		
//		Transaction tx = null;
		// con la transaccion me tiraba un error
//	      try{
	        // tx = session.beginTransaction();
	         Usuario user = (Usuario)session.get(Usuario.class, idUsuario); 
	         user.setHabilitado(estado);
	         session.update(user); 
	        // tx.commit();
//	      }catch (HibernateException e) {
//	         if (tx!=null) tx.rollback();
//	         e.printStackTrace(); 
//	      }finally {
//	         session.close(); 
//	      }
	}
	
}
