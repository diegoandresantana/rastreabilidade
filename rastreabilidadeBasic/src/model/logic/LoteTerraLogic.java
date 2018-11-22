package model.logic;

import model.entity.hibernate.LoteTerra;
import model.entity.hibernate.UnidadeMedida;

import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;

public class LoteTerraLogic extends GenericLogic<LoteTerra, Integer> {
	public LoteTerra insertReg(LoteTerra entity) throws LogicException {
		try {
			return daoFactory.getLoteTerraDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel inserir registro.", e);
		}
	}

	public LoteTerra updateReg(LoteTerra entity) throws LogicException {
		try {
			return daoFactory.getLoteTerraDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel atualizar o registro.",
					e);
		}
	}

	public void deleteReg(LoteTerra entity) throws LogicException {
		try {
			daoFactory.getLoteTerraDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel deletar o registro.", e);
		}
	}

	public List<LoteTerra> findAll() {
		return daoFactory.getLoteTerraDAO().findAll();
	}

	public List<LoteTerra> getRegByExample(LoteTerra example) {
		return daoFactory.getLoteTerraDAO().getRegByExample(example);
	}

	public void deleteAll(LoteTerra entity) throws LogicException {
		try {
			daoFactory.getLoteTerraDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException(
					"N�o foi poss�vel deletar todos os registros.", e);
		}
	}

	public void delByCodigos(String codigos) throws LogicException {
		try {
			daoFactory.getLoteTerraDAO().delByCodigos(codigos);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel deletar os registros.",
					e);
		}
	}

	public LoteTerra findById(Integer id) throws LogicException {
		try {
			return daoFactory.getLoteTerraDAO().findById(id);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel buscar por ID.", e);
		}
	}

	@Override
	public Integer countAllLimit(LoteTerra example) {
		return daoFactory.getLoteTerraDAO().countAllLimit(example);
	}

	@Override
	public List<LoteTerra> getRegByExampleLimit(LoteTerra example,
			Integer posStart, Integer maximo) {
		return daoFactory.getLoteTerraDAO().getRegByExampleLimit(example,
				posStart, maximo);
	}
}