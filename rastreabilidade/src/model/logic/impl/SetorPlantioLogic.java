
package model.logic.impl;
 import model.entity.hibernate.SetorPlantio;
import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
		public class SetorPlantioLogic extends GenericLogic {
	public SetorPlantio insertReg(SetorPlantio entity) throws LogicException {
		try {
			return daoFactory.getSetorPlantioDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível inserir registro.", e);
		}
	}
	public SetorPlantio updateReg(SetorPlantio entity) throws LogicException {
		try {
			return daoFactory.getSetorPlantioDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível atualizar o registro.",	e);
		}
	}
	public void deleteReg(SetorPlantio entity) throws LogicException {
		try {
			daoFactory.getSetorPlantioDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar o registro.", e);
		}
	}
	public List<SetorPlantio> findAll() {
		return daoFactory.getSetorPlantioDAO().findAll();
	}
	public List<SetorPlantio> getRegByExample(SetorPlantio example) {
		return daoFactory.getSetorPlantioDAO().getRegByExample(example);
	}
	public void deleteAll(SetorPlantio entity) throws LogicException {
		try {
		daoFactory.getSetorPlantioDAO().insertReg(entity);
		} catch (DaoException e) {
		throw new LogicException(
				"Não foi possível deletar todos os registros.", e);
		}
	}
public void delByCodigos(String codigos) throws LogicException {
	try {
		daoFactory.getSetorPlantioDAO().delByCodigos(codigos);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível deletar os registros.", e);
	}
}
	public SetorPlantio findById(Integer id) throws LogicException {
		try {
	return daoFactory.getSetorPlantioDAO().findById(id);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível buscar por ID.", e);
	}
	}
		}