package model.logic;

import java.io.Serializable;
import java.util.List;

import model.exceptions.DaoException;
import model.exceptions.LogicException;

public interface InterfaceGenericLogic<T, ID extends Serializable> {


	public T insertReg(T entity) throws LogicException;

	public T updateReg(T entity) throws LogicException;

	public void deleteReg(T entity) throws LogicException;

	public List<T> findAll();

	public List<T> getRegByExample(T example);

	public void deleteAll(T entity) throws LogicException;

	public T findById(ID id) throws LogicException;


	public void delByCodigos(String codigos) throws LogicException;
	public List<T> getRegByExampleLimit( T example,Integer posStart,Integer maximo);
	public Integer countAllLimit(T example);
}
