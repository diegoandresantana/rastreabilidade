
package model.logic.impl;
 import model.entity.hibernate.Cliente;
import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
		public class ClienteLogic extends GenericLogic {
	public Cliente insertReg(Cliente entity) throws LogicException {
		try {
			return daoFactory.getClienteDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível inserir registro.", e);
		}
	}
	public Cliente updateReg(Cliente entity) throws LogicException {
		try {
			return daoFactory.getClienteDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível atualizar o registro.",	e);
		}
	}
	public void deleteReg(Cliente entity) throws LogicException {
		try {
			daoFactory.getClienteDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar o registro.", e);
		}
	}
	public List<Cliente> findAll() {
		return daoFactory.getClienteDAO().findAll();
	}
	public List<Cliente> getRegByExample(Cliente example) {
		return daoFactory.getClienteDAO().getRegByExample(example);
	}
	public void deleteAll(Cliente entity) throws LogicException {
		try {
		daoFactory.getClienteDAO().insertReg(entity);
		} catch (DaoException e) {
		throw new LogicException(
				"Não foi possível deletar todos os registros.", e);
		}
	}
public void delByCodigos(String codigos) throws LogicException {
	try {
		daoFactory.getClienteDAO().delByCodigos(codigos);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível deletar os registros.", e);
	}
}
	public Cliente findById(Integer id) throws LogicException {
		try {
	return daoFactory.getClienteDAO().findById(id);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível buscar por ID.", e);
	}
	}
		}