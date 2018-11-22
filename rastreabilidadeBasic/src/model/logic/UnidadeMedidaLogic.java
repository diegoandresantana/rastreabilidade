package model.logic;

import model.entity.hibernate.UnidadeMedida;
import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;

public class UnidadeMedidaLogic extends GenericLogic<UnidadeMedida,Integer> {
	public UnidadeMedida insertReg(UnidadeMedida entity) throws LogicException {
		try {
			return daoFactory.getUnidadeMedidaDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível inserir registro.", e);
		}
	}

	public UnidadeMedida updateReg(UnidadeMedida entity) throws LogicException {
		try {
			return daoFactory.getUnidadeMedidaDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível atualizar o registro.",
					e);
		}
	}

	public void deleteReg(UnidadeMedida entity) throws LogicException {
		try {
			daoFactory.getUnidadeMedidaDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar o registro.", e);
		}
	}

	public List<UnidadeMedida> findAll() {
		return daoFactory.getUnidadeMedidaDAO().findAll();
	}

	public List<UnidadeMedida> getRegByExample(UnidadeMedida example) {
		return daoFactory.getUnidadeMedidaDAO().getRegByExample(example);
	}

	public void deleteAll(UnidadeMedida entity) throws LogicException {
		try {
			daoFactory.getUnidadeMedidaDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException(
					"Não foi possível deletar todos os registros.", e);
		}
	}

	public void delByCodigos(String codigos) throws LogicException {
		try {
			daoFactory.getUnidadeMedidaDAO().delByCodigos(codigos);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar os registros.",
					e);
		}
	}

	public UnidadeMedida findById(Integer id) throws LogicException {
		try {
			return daoFactory.getUnidadeMedidaDAO().findById(id);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível buscar por ID.", e);
		}
	}

	@Override
	public Integer countAllLimit(UnidadeMedida example) {		
		return daoFactory.getUnidadeMedidaDAO().countAllLimit(example);
	}

	@Override
	public List<UnidadeMedida> getRegByExampleLimit(UnidadeMedida example,Integer posStart, Integer maximo) {
		return daoFactory.getUnidadeMedidaDAO().getRegByExampleLimit(example, posStart, maximo);		
	}


}