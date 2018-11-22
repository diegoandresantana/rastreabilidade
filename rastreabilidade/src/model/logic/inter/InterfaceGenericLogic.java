package model.logic.inter;

import java.util.List;

import model.exceptions.LogicException;

public interface InterfaceGenericLogic<T, ID> {
	public T salvar(T obj) throws LogicException;

	public T insertReg(T entity) throws LogicException;

	public T updateReg(T entity) throws LogicException;

	public void deleteReg(T entity) throws LogicException;

	public List<T> findAll();

	public List<T> getRegByExample(T example);

	public void deleteAll(T entity) throws LogicException;

	public T findById(ID id) throws LogicException;

	public List<T> findAllLimit(Integer count, Integer posStart);

	public List<T> findByMask(String mask, Class<?> classe, String campo);

}
