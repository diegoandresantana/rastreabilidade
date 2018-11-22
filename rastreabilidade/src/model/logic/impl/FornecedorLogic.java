
package model.logic.impl;
 import java.util.List;

import model.entity.hibernate.Fornecedor;
import model.entity.hibernate.FornecedorProduto;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
		public class FornecedorLogic extends GenericLogic {
	public Fornecedor insertReg(Fornecedor entity) throws LogicException {
		try {
			return daoFactory.getFornecedorDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível inserir registro.", e);
		}
	}
	public Fornecedor updateReg(Fornecedor entity) throws LogicException {
		try {
			return daoFactory.getFornecedorDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível atualizar o registro.",	e);
		}
	}
	public void deleteReg(Fornecedor entity) throws LogicException {
		try {
			daoFactory.getFornecedorDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar o registro.", e);
		}
	}
	public List<Fornecedor> findAll() {
		return daoFactory.getFornecedorDAO().findAll();
	}
	public List<Fornecedor> getRegByExample(Fornecedor example) {
		return daoFactory.getFornecedorDAO().getRegByExample(example);
	}
	public void deleteAll(Fornecedor entity) throws LogicException {
		try {
		daoFactory.getFornecedorDAO().insertReg(entity);
		} catch (DaoException e) {
		throw new LogicException(
				"Não foi possível deletar todos os registros.", e);
		}
	}
	public Fornecedor findById(Integer id) throws LogicException {
		try {
	return daoFactory.getFornecedorDAO().findById(id);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível buscar por ID.", e);
	}
	}
	public void deleteFornecedorProdutoPorFornecedor(
			List<FornecedorProduto> selecionados,Integer idfornecedor) {
	
			daoFactory.getFornecedorDAO().deleteFornecedorProdutoPorFornecedor(selecionados, idfornecedor);
			
		
	}
		}