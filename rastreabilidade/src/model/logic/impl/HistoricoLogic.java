
package model.logic.impl;
 import model.entity.hibernate.Historico;
import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
		public class HistoricoLogic extends GenericLogic {
	public Historico insertReg(Historico entity) throws LogicException {
		try {
			return daoFactory.getHistoricoDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível inserir registro.", e);
		}
	}
	public Historico updateReg(Historico entity) throws LogicException {
		try {
			return daoFactory.getHistoricoDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível atualizar o registro.",	e);
		}
	}
	public void deleteReg(Historico entity) throws LogicException {
		try {
			daoFactory.getHistoricoDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar o registro.", e);
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
				"Não foi possível deletar todos os registros.", e);
		}
	}
public void delByCodigos(String codigos) throws LogicException {
	try {
		daoFactory.getHistoricoDAO().delByCodigos(codigos);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível deletar os registros.", e);
	}
}
	public Historico findById(Long id) throws LogicException {
		try {
	return daoFactory.getHistoricoDAO().findById(id);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível buscar por ID.", e);
	}
	}
		}