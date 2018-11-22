
package model.logic.impl;
 import java.util.List;

import model.entity.hibernate.FornecedorProduto;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
		public class FornecedorProdutoLogic extends GenericLogic {
	public FornecedorProduto insertReg(FornecedorProduto entity) throws LogicException {
		try {
			return daoFactory.getFornecedorProdutoDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível inserir registro.", e);
		}
	}
	public FornecedorProduto updateReg(FornecedorProduto entity) throws LogicException {
		try {
			return daoFactory.getFornecedorProdutoDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível atualizar o registro.",	e);
		}
	}
	public void deleteReg(FornecedorProduto entity) throws LogicException {
		try {
			daoFactory.getFornecedorProdutoDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar o registro.", e);
		}
	}
	public List<FornecedorProduto> findAll() {
		return daoFactory.getFornecedorProdutoDAO().findAll();
	}
	public List<FornecedorProduto> getRegByExample(FornecedorProduto example) {
		return daoFactory.getFornecedorProdutoDAO().getRegByExample(example);
	}
	public void deleteAll(FornecedorProduto entity) throws LogicException {
		try {
		daoFactory.getFornecedorProdutoDAO().insertReg(entity);
		} catch (DaoException e) {
		throw new LogicException(
				"Não foi possível deletar todos os registros.", e);
		}
	}
	public FornecedorProduto findById(Integer id) throws LogicException {
		try {
	return daoFactory.getFornecedorProdutoDAO().findById(id);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível buscar por ID.", e);
	}
	}
		}