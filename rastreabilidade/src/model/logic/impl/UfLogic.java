
package model.logic.impl;
 import model.entity.hibernate.Uf;
import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
		public class UfLogic extends GenericLogic {
	public Uf insertReg(Uf entity) throws LogicException {
		try {
			return daoFactory.getUfDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel inserir registro.", e);
		}
	}
	public Uf updateReg(Uf entity) throws LogicException {
		try {
			return daoFactory.getUfDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel atualizar o registro.",	e);
		}
	}
	public void deleteReg(Uf entity) throws LogicException {
		try {
			daoFactory.getUfDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel deletar o registro.", e);
		}
	}
	public List<Uf> findAll() {
		return daoFactory.getUfDAO().findAll();
	}
	public List<Uf> getRegByExample(Uf example) {
		return daoFactory.getUfDAO().getRegByExample(example);
	}
	public void deleteAll(Uf entity) throws LogicException {
		try {
		daoFactory.getUfDAO().insertReg(entity);
		} catch (DaoException e) {
		throw new LogicException(
				"N�o foi poss�vel deletar todos os registros.", e);
		}
	}
public void delByCodigos(String codigos) throws LogicException {
	try {
		daoFactory.getUfDAO().delByCodigos(codigos);
	} catch (DaoException e) {
		throw new LogicException("N�o foi poss�vel deletar os registros.", e);
	}
}
	public Uf findById(Integer id) throws LogicException {
		try {
	return daoFactory.getUfDAO().findById(id);
	} catch (DaoException e) {
		throw new LogicException("N�o foi poss�vel buscar por ID.", e);
	}
	}
		}