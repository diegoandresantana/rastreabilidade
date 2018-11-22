package model.logic;

import java.io.Serializable;

import model.dao.DAOFactory;

public abstract class GenericLogic<T,ID extends Serializable> implements InterfaceGenericLogic<T, ID> {

	protected DAOFactory daoFactory = DAOFactory.instance();
    
}
