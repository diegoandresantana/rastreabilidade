
package model.dao;
import java.util.List;

import org.hibernate.Query;

import model.entity.hibernate.Usuario;
import model.exceptions.DaoException;

public class UsuarioDAO extends GenericHibernateDAO<Usuario, Integer> {

	@SuppressWarnings("finally")
	public Usuario getUsuarioByLogin(String login) throws DaoException {
		Query query =null;
		Usuario usuario = null;
		try {
			query =getSession().createQuery(" from " + getPersistentClass().getSimpleName()+" where loginusu like '"+login+"'");

			usuario = (Usuario) query.uniqueResult();
		} catch (Exception e) {
			throw new DaoException("Não existe o login.", e);
		} finally{
			 
			getSession().close();
			return usuario;
		}
	}
	
	
}