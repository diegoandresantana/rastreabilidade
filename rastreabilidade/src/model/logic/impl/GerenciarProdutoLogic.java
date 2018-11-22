
package model.logic.impl;
 import model.entity.hibernate.GerenciarProduto;
import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
		public class GerenciarProdutoLogic extends GenericLogic {
	public GerenciarProduto insertReg(GerenciarProduto entity) throws LogicException {
		try {
			return daoFactory.getGerenciarProdutoDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível inserir registro.", e);
		}
	}
	public GerenciarProduto updateReg(GerenciarProduto entity) throws LogicException {
		try {
			return daoFactory.getGerenciarProdutoDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível atualizar o registro.",	e);
		}
	}
	public void deleteReg(GerenciarProduto entity) throws LogicException {
		try {
			daoFactory.getGerenciarProdutoDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar o registro.", e);
		}
	}
	public List<GerenciarProduto> findAll() {
		return daoFactory.getGerenciarProdutoDAO().findAll();
	}
	public List<GerenciarProduto> getRegByExample(GerenciarProduto example) {
		return daoFactory.getGerenciarProdutoDAO().getRegByExample(example);
	}
	public void deleteAll(GerenciarProduto entity) throws LogicException {
		try {
		daoFactory.getGerenciarProdutoDAO().insertReg(entity);
		} catch (DaoException e) {
		throw new LogicException(
				"Não foi possível deletar todos os registros.", e);
		}
	}
public void delByCodigos(String codigos) throws LogicException {
	try {
		daoFactory.getGerenciarProdutoDAO().delByCodigos(codigos);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível deletar os registros.", e);
	}
}
	public GerenciarProduto findById(Long id) throws LogicException {
		try {
	return daoFactory.getGerenciarProdutoDAO().findById(id);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível buscar por ID.", e);
	}
	}
		}