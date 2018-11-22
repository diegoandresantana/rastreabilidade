package model.dao.inter;

import java.io.Serializable;
import java.util.List;

import model.exceptions.DaoException;

public interface InterfaceGenericDAO<T, ID extends Serializable> {

	public T insertReg(T entity) throws DaoException;

	public T updateReg(T entity) throws DaoException;

	public void deleteReg(T entity) throws DaoException;	

	public List<T> getRegByExample(T example);

	public void deleteAll(T entity) throws DaoException;

	public T findById(ID id) throws DaoException;


	public void setPersistentClass(Class<T> persistentClass);

	public void delByCodigos(String codigos) throws DaoException;
	public List<T> getRegByExampleLimit( T example,Integer posStart,Integer maximo);
	public Integer countAllLimit(T example);
}