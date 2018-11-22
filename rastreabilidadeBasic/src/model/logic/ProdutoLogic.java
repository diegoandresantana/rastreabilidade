package model.logic;

import model.entity.hibernate.Produto;
import model.entity.hibernate.UnidadeMedida;

import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;

public class ProdutoLogic extends GenericLogic<Produto, Integer> {
	public Produto insertReg(Produto entity) throws LogicException {
		try {
			return daoFactory.getProdutoDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível inserir registro.", e);
		}
	}

	public Produto updateReg(Produto entity) throws LogicException {
		try {
			return daoFactory.getProdutoDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível atualizar o registro.",
					e);
		}
	}

	public void deleteReg(Produto entity) throws LogicException {
		try {
			daoFactory.getProdutoDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar o registro.", e);
		}
	}

	public List<Produto> findAll() {
		return daoFactory.getProdutoDAO().findAll();
	}

	public List<Produto> getRegByExample(Produto example) {
		return daoFactory.getProdutoDAO().getRegByExample(example);
	}

	public void deleteAll(Produto entity) throws LogicException {
		try {
			daoFactory.getProdutoDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException(
					"Não foi possível deletar todos os registros.", e);
		}
	}

	public void delByCodigos(String codigos) throws LogicException {
		try {
			daoFactory.getProdutoDAO().delByCodigos(codigos);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar os registros.",
					e);
		}
	}

	public Produto findById(Integer id) throws LogicException {
		try {
			return daoFactory.getProdutoDAO().findById(id);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível buscar por ID.", e);
		}
	}

	@Override
	public Integer countAllLimit(Produto example) {		
		return daoFactory.getProdutoDAO().countAllLimit(example);
	}

	@Override
	public List<Produto> getRegByExampleLimit(Produto example,Integer posStart, Integer maximo) {
		return daoFactory.getProdutoDAO().getRegByExampleLimit(example, posStart, maximo);		
	}
}