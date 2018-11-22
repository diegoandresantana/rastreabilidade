
package model.logic.impl;
 import model.entity.hibernate.LoteVegetal;
import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
		public class LoteVegetalLogic extends GenericLogic {
	public LoteVegetal insertReg(LoteVegetal entity) throws LogicException {
		try {
			return daoFactory.getLoteVegetalDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível inserir registro.", e);
		}
	}
	public LoteVegetal updateReg(LoteVegetal entity) throws LogicException {
		try {
			return daoFactory.getLoteVegetalDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível atualizar o registro.",	e);
		}
	}
	public void deleteReg(LoteVegetal entity) throws LogicException {
		try {
			daoFactory.getLoteVegetalDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar o registro.", e);
		}
	}
	public List<LoteVegetal> findAll() {
		return daoFactory.getLoteVegetalDAO().findAll();
	}
	public List<LoteVegetal> getRegByExample(LoteVegetal example) {
		return daoFactory.getLoteVegetalDAO().getRegByExample(example);
	}
	public void deleteAll(LoteVegetal entity) throws LogicException {
		try {
		daoFactory.getLoteVegetalDAO().insertReg(entity);
		} catch (DaoException e) {
		throw new LogicException(
				"Não foi possível deletar todos os registros.", e);
		}
	}
public void delByCodigos(String codigos) throws LogicException {
	try {
		daoFactory.getLoteVegetalDAO().delByCodigos(codigos);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível deletar os registros.", e);
	}
}
	public LoteVegetal findById(Long id) throws LogicException {
		try {
	return daoFactory.getLoteVegetalDAO().findById(id);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível buscar por ID.", e);
	}
	}
		}