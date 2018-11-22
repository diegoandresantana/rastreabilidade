
package model.dao.impl.hibernate;
import java.util.ArrayList;
import java.util.List;

import model.entity.hibernate.Fornecedor;
import model.entity.hibernate.FornecedorProduto;

import org.hibernate.Query;
public class FornecedorDAO extends GenericHibernateDAO<Fornecedor, Integer> {

	public void deleteFornecedorProdutoPorFornecedor(
			List<FornecedorProduto> selecionados,Integer idfornecedor) {
		List<Integer> parameter=new ArrayList<Integer>();
		for(FornecedorProduto fornPro:selecionados){
			if(fornPro.getIdfornecedorproduto()!=null){
				parameter.add(fornPro.getIdfornecedorproduto());
				
			}
		}
		String criterio="";
		if(parameter.size()>0){
			System.out.println(parameter.toString());
			criterio="and fp.idfornecedorproduto not in("+(parameter.toString()).replace("[", "").replace("]","")+")";
		}
		 
		Query q=getSession().createQuery("delete from FornecedorProduto  fp where fp.fornecedor.idfornecedor=? "+criterio);
		q.setInteger(0, idfornecedor);
		q.executeUpdate();
	
		// TODO Auto-generated method stub
		
	}
}