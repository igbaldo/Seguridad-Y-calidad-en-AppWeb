package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Funcionalidad;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.inject.Inject;

@Repository("funcionalildadDao")
public class FuncionalidadDaoImpl implements FuncionalidadDao {

	@Inject
    private SessionFactory sessionFactory;

	@Override
	public Funcionalidad getFuncionalidadByDesc(String descripcion) {

		final Session session = sessionFactory.getCurrentSession();
		
		Funcionalidad func = new Funcionalidad();
		func.setDescripcion(descripcion);
		
		return (Funcionalidad) session.createCriteria(Funcionalidad.class)
				.add(Restrictions.eq("descripcion", func.getDescripcion() ))
				.uniqueResult();
		
	}

}
