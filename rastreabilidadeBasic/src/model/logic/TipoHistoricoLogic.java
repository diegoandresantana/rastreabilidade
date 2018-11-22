package model.logic;

import model.entity.hibernate.TipoHistorico;
import model.entity.hibernate.UnidadeMedida;

import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;

public class TipoHistoricoLogic extends GenericLogic<TipoHistorico, Integer> {
	public TipoHistorico insertReg(TipoHistorico entity) throws LogicException {
		try {
			return daoFactory.getTipoHistoricoDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível inserir registro.", e);
		}
	}

	public TipoHistorico updateReg(TipoHistorico entity) throws LogicException {
		try {
			return daoFactory.getTipoHistoricoDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível atualizar o registro.",
					e);
		}
	}

	public void deleteReg(TipoHistorico entity) throws LogicException {
		try {
			daoFactory.getTipoHistoricoDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar o registro.", e);
		}
	}

	public List<TipoHistorico> findAll() {
		return daoFactory.getTipoHistoricoDAO().findAll();
	}

	public List<TipoHistorico> getRegByExample(TipoHistorico example) {
		return daoFactory.getTipoHistoricoDAO().getRegByExample(example);
	}

	public void deleteAll(TipoHistorico entity) throws LogicException {
		try {
			daoFactory.getTipoHistoricoDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException(
					"Não foi possível deletar todos os registros.", e);
		}
	}

	public void delByCodigos(String codigos) throws LogicException {
		try {
			daoFactory.getTipoHistoricoDAO().delByCodigos(codigos);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar os registros.",
					e);
		}
	}

	public TipoHistorico findById(Integer id) throws LogicException {
		try {
			return daoFactory.getTipoHistoricoDAO().findById(id);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível buscar por ID.", e);
		}
	}

	@Override
	public Integer countAllLimit(TipoHistorico example) {		
		return daoFactory.getTipoHistoricoDAO().countAllLimit(example);
	}

	@Override
	public List<TipoHistorico> getRegByExampleLimit(TipoHistorico example,Integer posStart, Integer maximo) {
		return daoFactory.getTipoHistoricoDAO().getRegByExampleLimit(example, posStart, maximo);		
	}
}