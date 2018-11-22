
package model.logic.impl;
 import model.entity.hibernate.TipoProduto;
import java.util.List;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
		public class TipoProdutoLogic extends GenericLogic {
	public TipoProduto insertReg(TipoProduto entity) throws LogicException {
		try {
			return daoFactory.getTipoProdutoDAO().insertReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível inserir registro.", e);
		}
	}
	public TipoProduto updateReg(TipoProduto entity) throws LogicException {
		try {
			return daoFactory.getTipoProdutoDAO().updateReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível atualizar o registro.",	e);
		}
	}
	public void deleteReg(TipoProduto entity) throws LogicException {
		try {
			daoFactory.getTipoProdutoDAO().deleteReg(entity);
		} catch (DaoException e) {
			throw new LogicException("Não foi possível deletar o registro.", e);
		}
	}
	public List<TipoProduto> findAll() {
		return daoFactory.getTipoProdutoDAO().findAll();
	}
	public List<TipoProduto> getRegByExample(TipoProduto example) {
		return daoFactory.getTipoProdutoDAO().getRegByExample(example);
	}
	public void deleteAll(TipoProduto entity) throws LogicException {
		try {
		daoFactory.getTipoProdutoDAO().insertReg(entity);
		} catch (DaoException e) {
		throw new LogicException(
				"Não foi possível deletar todos os registros.", e);
		}
	}
public void delByCodigos(String codigos) throws LogicException {
	try {
		daoFactory.getTipoProdutoDAO().delByCodigos(codigos);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível deletar os registros.", e);
	}
}
	public TipoProduto findById(Integer id) throws LogicException {
		try {
	return daoFactory.getTipoProdutoDAO().findById(id);
	} catch (DaoException e) {
		throw new LogicException("Não foi possível buscar por ID.", e);
	}
	}
		}