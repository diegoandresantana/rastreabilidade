
package model.logic.impl;
 import model.entity.hibernate.NotaFiscal;
import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
		public class NotaFiscalLogic extends GenericLogic {
	public NotaFiscal insertReg(NotaFiscal entity) throws LogicException {
		try {
			return daoFactory.getNotaFiscalDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel inserir registro.", e);
		}
	}
	public NotaFiscal updateReg(NotaFiscal entity) throws LogicException {
		try {
			return daoFactory.getNotaFiscalDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel atualizar o registro.",	e);
		}
	}
	public void deleteReg(NotaFiscal entity) throws LogicException {
		try {
			daoFactory.getNotaFiscalDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel deletar o registro.", e);
		}
	}
	public List<NotaFiscal> findAll() {
		return daoFactory.getNotaFiscalDAO().findAll();
	}
	public List<NotaFiscal> getRegByExample(NotaFiscal example) {
		return daoFactory.getNotaFiscalDAO().getRegByExample(example);
	}
	public void deleteAll(NotaFiscal entity) throws LogicException {
		try {
		daoFactory.getNotaFiscalDAO().insertReg(entity);
		} catch (DaoException e) {
		throw new LogicException(
				"N�o foi poss�vel deletar todos os registros.", e);
		}
	}
public void delByCodigos(String codigos) throws LogicException {
	try {
		daoFactory.getNotaFiscalDAO().delByCodigos(codigos);
	} catch (DaoException e) {
		throw new LogicException("N�o foi poss�vel deletar os registros.", e);
	}
}
	public NotaFiscal findById(Long id) throws LogicException {
		try {
	return daoFactory.getNotaFiscalDAO().findById(id);
	} catch (DaoException e) {
		throw new LogicException("N�o foi poss�vel buscar por ID.", e);
	}
	}
		}