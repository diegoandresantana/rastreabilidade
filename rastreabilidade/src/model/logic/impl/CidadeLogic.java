package model.logic.impl;

import model.entity.hibernate.Cidade;
import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;

public class CidadeLogic extends GenericLogic {
	public Cidade insertReg(Cidade entity) throws LogicException {
		try {
			return daoFactory.getCidadeDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível inserir registro.", e);
		}
	}

	public Cidade updateReg(Cidade entity) throws LogicException {
		try {
			return daoFactory.getCidadeDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível atualizar o registro.",
					e);
		}
	}

	public void deleteReg(Cidade entity) throws LogicException {
		try {
			daoFactory.getCidadeDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar o registro.", e);
		}
	}

	public List<Cidade> findAll() {
		return daoFactory.getCidadeDAO().findAll();
	}

	public List<Cidade> getRegByExample(Cidade example) {
		return daoFactory.getCidadeDAO().getRegByExample(example);
	}

	public void deleteAll(Cidade entity) throws LogicException {
		try {
			daoFactory.getCidadeDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException(
					"Não foi possível deletar todos os registros.", e);
		}
	}

	public void delByCodigos(String codigos) throws LogicException {
		try {
			daoFactory.getCidadeDAO().delByCodigos(codigos);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar os registros.",
					e);
		}
	}

	public Cidade findById(Integer id) throws LogicException {
		try {
			return daoFactory.getCidadeDAO().findById(id);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível buscar por ID.", e);
		}
	}
	
}