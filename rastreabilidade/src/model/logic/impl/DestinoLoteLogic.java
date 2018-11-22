
package model.logic.impl;
 import model.entity.hibernate.DestinoLote;
import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
		public class DestinoLoteLogic extends GenericLogic {
	public DestinoLote insertReg(DestinoLote entity) throws LogicException {
		try {
			return daoFactory.getDestinoLoteDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível inserir registro.", e);
		}
	}
	public DestinoLote updateReg(DestinoLote entity) throws LogicException {
		try {
			return daoFactory.getDestinoLoteDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível atualizar o registro.",	e);
		}
	}
	public void deleteReg(DestinoLote entity) throws LogicException {
		try {
			daoFactory.getDestinoLoteDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar o registro.", e);
		}
	}
	public List<DestinoLote> findAll() {
		return daoFactory.getDestinoLoteDAO().findAll();
	}
	public List<DestinoLote> getRegByExample(DestinoLote example) {
		return daoFactory.getDestinoLoteDAO().getRegByExample(example);
	}
	public void deleteAll(DestinoLote entity) throws LogicException {
		try {
		daoFactory.getDestinoLoteDAO().insertReg(entity);
		} catch (DaoException e) {
		throw new LogicException(
				"Não foi possível deletar todos os registros.", e);
		}
	}
public void delByCodigos(String codigos) throws LogicException {
	try {
		daoFactory.getDestinoLoteDAO().delByCodigos(codigos);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível deletar os registros.", e);
	}
}
	public DestinoLote findById(Long id) throws LogicException {
		try {
	return daoFactory.getDestinoLoteDAO().findById(id);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível buscar por ID.", e);
	}
	}
		}