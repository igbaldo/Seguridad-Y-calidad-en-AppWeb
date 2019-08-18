package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Funcionalidad;
import ar.edu.unlam.tallerweb1.modelo.Nota;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.inject.Inject;

@Repository("notaDao")
public class NotaDaoImpl implements NotaDao {

	@Inject
    private SessionFactory sessionFactory;

	@Override
	public void guardarNota(Nota nota) {
		
		final Session session = sessionFactory.getCurrentSession();
		
		session.save(nota);	
	}

	@Override
	public List<Nota> getByUsuario(Long idUsuario) {
		final Session session = sessionFactory.getCurrentSession();
		List<Nota> notas = session.createCriteria(Nota.class)
							.createAlias("usuario", "usuarioBuscado")
							.add(Restrictions.eq("usuarioBuscado.id", idUsuario))
							.list();
		
		return notas;		
	}
}
