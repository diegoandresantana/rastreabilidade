package model.logic;

import model.entity.hibernate.Historico;
import model.entity.hibernate.UnidadeMedida;

import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;

public class HistoricoLogic extends GenericLogic<Historico,Long> {
	public Historico insertReg(Historico entity) throws LogicException {
		try {
			return daoFactory.getHistoricoDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel inserir registro.", e);
		}
	}

	public Historico updateReg(Historico entity) throws LogicException {
		try {
			return daoFactory.getHistoricoDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel atualizar o registro.",
					e);
		}
	}

	public void deleteReg(Historico entity) throws LogicException {
		try {
			daoFactory.getHistoricoDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel deletar o registro.", e);
		}
	}

	public List<Historico> findAll() {
		return daoFactory.getHistoricoDAO().findAll();
	}

	public List<Historico> getRegByExample(Historico example) {
		return daoFactory.getHistoricoDAO().getRegByExample(example);
	}

	public void deleteAll(Historico entity) throws LogicException {
		try {
			daoFactory.getHistoricoDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException(
					"N�o foi poss�vel deletar todos os registros.", e);
		}
	}

	public void delByCodigos(String codigos) throws LogicException {
		try {
			daoFactory.getHistoricoDAO().delByCodigos(codigos);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel deletar os registros.",
					e);
		}
	}

	public Historico findById(Long id) throws LogicException {
		try {
			return daoFactory.getHistoricoDAO().findById(id);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel buscar por ID.", e);
		}
	}

	@Override
	public Integer countAllLimit(Historico example) {		
		return daoFactory.getHistoricoDAO().countAllLimit(example);
	}

	@Override
	public List<Historico> getRegByExampleLimit(Historico example,Integer posStart, Integer maximo) {
		return daoFactory.getHistoricoDAO().getRegByExampleLimit(example, posStart, maximo);		
	}
	
}