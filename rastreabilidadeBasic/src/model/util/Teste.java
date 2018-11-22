package model.util;

import java.util.List;

import model.dao.DAOFactory;
import model.entity.hibernate.Cidade;
import model.entity.hibernate.Usuario;
import model.exceptions.DaoException;

public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DAOFactory daoFactory = DAOFactory.instance();
		List<Cidade> listusu = null;
		Cidade cid = new Cidade();
		cid.setNomecidade("CAMP");
		listusu = daoFactory.getCidadeDAO().getRegByExample(cid);
		System.out.println(daoFactory.getCidadeDAO().countAllLimit(cid));

		for (Cidade u : listusu) {
			System.out.println(u.getNomecidade());
		}
	}

}
