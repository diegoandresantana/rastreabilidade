package model.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import model.exceptions.DaoException;
import model.util.HibernateUtil;
import model.util.ReflectionUtil;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.proxy.HibernateProxy;



/**
 * Generic implementation of the DAO services
 * 
 * @author
 * 
 * @param <T>
 * @param <ID>
 */
public class GenericHibernateDAO<T, ID extends Serializable> implements
		InterfaceGenericDAO<T, ID> {

	private Class<T> persistentClass;

	private Session session;

	//
	/**
	 * Default constructor
	 * 
	 */
	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		// Obtendo sessao da HibernateUtil
		this.session = (Session) HibernateUtil.getCurrentSession();
		this.session.setCacheMode(CacheMode.IGNORE);
	}

	/**
	 * Injector method
	 * 
	 * @param s
	 */
	public void setSession(Session s) {
		this.session = s;
	}

	public Session getSession() {
		try {
			if (session == null || !session.isOpen()){
				session = HibernateUtil.getCurrentSession();
			}
			
		} catch (Exception e) {
			throw new IllegalStateException(
			"Session has not been set on DAO before usage");
		} finally{
			return session;
		}
	
	}

	protected Class<T> getPersistentClass() {
		return persistentClass;
	}




	@Override
	public T findById(ID id) throws DaoException {
		return findById(id, false);
	}

	@SuppressWarnings("unchecked")
	public T findById(ID id, boolean lock) throws DaoException {

		T entity;
		try {

			if (lock)
				entity = (T) getSession().load(getPersistentClass(), id,
						LockMode.UPGRADE);
			else
				entity = (T) getSession().load(getPersistentClass(), id);

			if (entity instanceof HibernateProxy)
				return (T) ((HibernateProxy) entity)
						.getHibernateLazyInitializer().getImplementation();
			else
				return entity;

		} catch (Exception e) {
			throw new DaoException("Nao foi possivel buscar por id.", e);
		} finally{
			getSession().close();
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Query query =null;
		List<T> list=null;
		try {
			query =getSession().createQuery("from " + getPersistentClass());
			list = query.list();
		} catch (Exception e) {
			// TODO: handle exception
		} finally{			 
			getSession().close();
			return list;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public T insertReg(T entity) throws DaoException {
		Transaction tx = getSession().beginTransaction();
		try {

			entity = (T) getSession().merge(entity);			
			tx.commit();
			
			return entity;
		} catch (Exception e) {
			tx.rollback();
			getSession().flush();
			throw new DaoException("Nao foi possivel salvar o registro.", e);

		} finally{
			getSession().close();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public T updateReg(T entity) throws DaoException {
		Transaction tx = getSession().beginTransaction();
		try {

			entity = (T) getSession().merge(entity);

			tx.commit();
			return entity;
		} catch (Exception e) {
			tx.rollback();
			getSession().flush();
			throw new DaoException("Nao foi possivel salvar o registro.", e);

		} finally{
			getSession().close();
		}
	}

	@Override
	public void deleteReg(T entity) throws DaoException {

		    Transaction tx = getSession().beginTransaction();
		try {
			getSession().delete(entity);
			tx.commit();
		} catch (ConstraintViolationException e) {
			tx.rollback();
			getSession().flush();
			throw new DaoException("Nao foi possÌvel excluir o registro.", e);
		} finally{
			getSession().close();
		}
		
	}
	
	@Override
	public void delByCodigos(String codigos) throws DaoException {

		Transaction tx = getSession().beginTransaction();
		try {
			List<Field> fields=ReflectionUtil.getAllFields(persistentClass,null);
			String id = "";
			for(Field f:fields){
				if(f.isAnnotationPresent(Id.class)){
					id=f.getName();
					break;
				}
			}
			Query q=getSession().createQuery("delete from "+persistentClass.getSimpleName()+"   where "+id+"  in ("+codigos+")");
		
			q.executeUpdate();
			tx.commit();
		} catch (ConstraintViolationException e) {
			tx.rollback();
			getSession().flush();
			throw new DaoException("Nao foi possÌvel excluir o registro.", e);
		} finally{
			getSession().close();
		}
	}
	

	@SuppressWarnings("unchecked")
	public List<T> getRegByExample(T example) {

		
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		    
           if (example != null) {
                   criteria.add(createExample(example));
           }else{
        	  
			try {
				example = getPersistentClass().newInstance();
				criteria.add(createExample(example));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				
			}
        	   
           }
           
   	//	criteria.add(Example.create(example).enableLike(MatchMode.START).ignoreCase());
   		//criteria.setMaxResults(200);
   		
   		final List<Criterion> criterios = buildCriterionForRelationships(example,null);
		for ( Criterion criterion : criterios )
		{
			criteria.add( criterion );
		}

   	    buildCriteria(criteria,example);
		List<T> lista=criteria.list();
		
		getSession().close();
		
		return lista;
	}
    public Example createExample(T entity) {
        
        Example example = Example.create(entity);
        example.enableLike(MatchMode.START);
        example.ignoreCase();   
        
        return example;

   }

	@Override
	public void deleteAll(T entity) throws DaoException {

		Transaction tx = getSession().beginTransaction();
		try {

			String sqldel = "DELETE FROM " + entity.getClass().getSimpleName();
			Query query = session.createQuery(sqldel);
			query.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			throw new DaoException("Nao foi possÌvel excluir os registros", e);

		} finally{
			getSession().close();
		}
	}
	
	public void buildCriteria(Criteria criteria,T example){
		List<Field> fields=ReflectionUtil.getAllFields(example.getClass(), null);
		int i=0;
		for(Field f:fields){
			if(f.isAnnotationPresent(OrderBy.class)){
				criteria.addOrder(Order.asc(f.getName()));
				i=1;				
			}			
		}		
		if(i==0){
			for(Field f:fields){
				if(f.isAnnotationPresent(Id.class)){
					criteria.addOrder(Order.asc(f.getName()));
					break;
				}
			}
		}		
	}
	/**
	 * Constr√≥i crit√©rios do tipo {@link Restrictions#eq(String, Object)} para todos os
	 * relacionamentos do objeto informado que n√£o forem nulos.
	 * 
	 * @param entity Entidade
	 * @param excludeProperty Propriedades que seram deconsideradas
	 * @return Crit√©rios
	 */
	@SuppressWarnings("unchecked")
	public static List<Criterion> buildCriterionForRelationships( Object entity, String... excludeProperty )
	{
		final List<Criterion> criterions = new ArrayList<Criterion>();
		final List<Field> fields = ReflectionUtil.getAllFields( entity.getClass(),null );
		for ( Field field : fields )
		{
			if (field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToMany.class))
			{
				if ( excludeProperty == null)
				{
					try
					{
						Object obj = null;
						try
						{							
							Method method = entity.getClass().getMethod("get"+ReflectionUtil.capitalizeFirst(field.getName()),null);
							if (method != null) {
								obj = method.invoke(entity);
							} 						
						}
						catch ( Exception e )
						{
						}
						if ( obj != null )
						{
							boolean ok = true;
							if ( obj instanceof List )
							{
								final List c = ( List ) obj;
								ok = !c.isEmpty();
							}
							if ( ok )
							{
								criterions.add( Restrictions.eq( field.getName(), obj ) );
							}
						}
					}
					catch ( Exception e )
					{
						final RuntimeException ex = new RuntimeException( "Erro ao gerar os criterios para os relacionamentos", e );
						throw ex;
					}
				}
			}
			else {
				Object obj = null;
				try
				{					
					Method method = entity.getClass().getMethod("get"+ReflectionUtil.capitalizeFirst(field.getName()),null);
					if (method != null) {
						obj = method.invoke(entity);
					} 				
				}catch(Exception e){
					
				}
				if ( obj != null )
				{
					if(!field.getType().getName().equals("java.lang.String")){
						criterions.add( Restrictions.eq( field.getName(), obj ) );
					}
				}
			}
		}
		return criterions;
	}

	public Integer countAllLimit(T example) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());

		if (example != null) {
			criteria.add(createExample(example));
		}else{
        	  
			try {
				example = getPersistentClass().newInstance();
				criteria.add(createExample(example));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				
			}
        	   
           }
		// criteria.add(Example.create(example).enableLike(MatchMode.START).
		// ignoreCase());

		final List<Criterion> criterios = buildCriterionForRelationships(
				example, null);
		for (Criterion criterion : criterios) {
			criteria.add(criterion);
		}
		// buildCriteria(criteria, example);
		criteria.setProjection(Projections.rowCount());
		Integer count=new Integer((criteria.list().get(0)).toString());
		getSession().close();
		return count;
		/**
		 * Query query = session.createQuery("select count(*) as num from " +
		 * persistentClass.getSimpleName()); Long lon; lon = (Long)
		 * query.uniqueResult(); Integer numberRows =
		 * Integer.valueOf(lon.toString()); return numberRows;
		 **/
	}
	@SuppressWarnings("unchecked")
	public List<T> getRegByExampleLimit(T example,Integer posStart,Integer maximo) {
		
		Criteria criteria = getSession().createCriteria(getPersistentClass());

		if (example != null) {
			criteria.add(createExample(example));
		}else{
        	  
			try {
				example = getPersistentClass().newInstance();
				criteria.add(createExample(example));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				
			}
        	   
           }
		// criteria.add(Example.create(example).enableLike(MatchMode.START).
		// ignoreCase());
		criteria.setMaxResults(maximo).setFirstResult(posStart);
		final List<Criterion> criterios = buildCriterionForRelationships(
				example, null);
		for (Criterion criterion : criterios) {
			criteria.add(criterion);
		}
		buildCriteria(criteria, example);
		
		List<T> lista= criteria.list();
		getSession().close();
		return lista;

	}
	@Override
	public void setPersistentClass(Class<T> persistentClass) {
		// TODO Auto-generated method stub

	}
	
}
