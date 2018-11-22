package model.logic;

import java.util.List;

import model.entity.hibernate.Usuario;
import model.exceptions.DaoException;
import model.exceptions.LogicException;

public class UsuarioLogic extends GenericLogic<Usuario, Integer> {
	public Usuario insertReg(Usuario entity) throws LogicException {
		try {
			return daoFactory.getUsuarioDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel inserir registro.", e);
		}
	}

	public Usuario updateReg(Usuario entity) throws LogicException {
		try {
			return daoFactory.getUsuarioDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel atualizar o registro.",
					e);
		}
	}

	public void deleteReg(Usuario entity) throws LogicException {
		try {
			daoFactory.getUsuarioDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel deletar o registro.", e);
		}
	}

	public List<Usuario> findAll() {
		return daoFactory.getUsuarioDAO().findAll();
	}

	public List<Usuario> getRegByExample(Usuario example) {
		return daoFactory.getUsuarioDAO().getRegByExample(example);
	}

	public void deleteAll(Usuario entity) throws LogicException {
		try {
			daoFactory.getUsuarioDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException(
					"N�o foi poss�vel deletar todos os registros.", e);
		}
	}

	public void delByCodigos(String codigos) throws LogicException {
		try {
			daoFactory.getUsuarioDAO().delByCodigos(codigos);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel deletar os registros.",
					e);
		}
	}

	public Usuario findById(Integer id) throws LogicException {
		try {
			return daoFactory.getUsuarioDAO().findById(id);
		} catch (DaoException e) {
			throw new LogicException("N�o foi poss�vel buscar por ID.", e);
		}
	}

	public Usuario getUsuarioByLogin(String login) throws LogicException {
		try {
			return daoFactory.getUsuarioDAO().getUsuarioByLogin(login);
		} catch (DaoException e) {
			throw new LogicException("N�o existe o Login.", e);
		}
	}
	@Override
	public Integer countAllLimit(Usuario example) {		
		return daoFactory.getUsuarioDAO().countAllLimit(example);
	}

	@Override
	public List<Usuario> getRegByExampleLimit(Usuario example,Integer posStart, Integer maximo) {
		return daoFactory.getUsuarioDAO().getRegByExampleLimit(example, posStart, maximo);		
	}
	
}