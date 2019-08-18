package ar.edu.unlam.tallerweb1.servicios;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.dao.FuncionalidadDao;
import ar.edu.unlam.tallerweb1.modelo.Funcionalidad;

@Service("servicioFuncionalidad")
@Transactional
public class ServicioFuncionalidadImpl implements ServicioFuncionalidad{

	@Inject
	private FuncionalidadDao funcionalidadDao;

	@Override
	public Funcionalidad getFuncionalidadByDesc(String funcionalidad) {
		
		return funcionalidadDao.getFuncionalidadByDesc(funcionalidad);
	}
}
