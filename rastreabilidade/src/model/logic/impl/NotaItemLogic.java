
package model.logic.impl;
 import model.entity.hibernate.NotaItem;
import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
		public class NotaItemLogic extends GenericLogic {
	public NotaItem insertReg(NotaItem entity) throws LogicException {
		try {
			return daoFactory.getNotaItemDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel inserir registro.", e);
		}
	}
	public NotaItem updateReg(NotaItem entity) throws LogicException {
		try {
			return daoFactory.getNotaItemDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel atualizar o registro.",	e);
		}
	}
	public void deleteReg(NotaItem entity) throws LogicException {
		try {
			daoFactory.getNotaItemDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel deletar o registro.", e);
		}
	}
	public List<NotaItem> findAll() {
		return daoFactory.getNotaItemDAO().findAll();
	}
	public List<NotaItem> getRegByExample(NotaItem example) {
		return daoFactory.getNotaItemDAO().getRegByExample(example);
	}
	public void deleteAll(NotaItem entity) throws LogicException {
		try {
		daoFactory.getNotaItemDAO().insertReg(entity);
		} catch (DaoException e) {
		throw new LogicException(
				"N�o foi poss�vel deletar todos os registros.", e);
		}
	}
public void delByCodigos(String codigos) throws LogicException {
	try {
		daoFactory.getNotaItemDAO().delByCodigos(codigos);
	} catch (DaoException e) {
		throw new LogicException("N�o foi poss�vel deletar os registros.", e);
	}
}
	public NotaItem findById(Long id) throws LogicException {
		try {
	return daoFactory.getNotaItemDAO().findById(id);
	} catch (DaoException e) {
		throw new LogicException("N�o foi poss�vel buscar por ID.", e);
	}
	}
		}