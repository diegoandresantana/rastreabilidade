package model.dao;

import model.util.HibernateUtil;

import org.hibernate.Session;

public class DAOFactory {
	private static DAOFactory daoFactory;
	private static CidadeDAO cidadeDAO;
	
	private static HistoricoDAO historicoDAO;
	private static LoteTerraDAO loteTerraDAO;
	private static LoteVegetalDAO loteVegetalDAO;
	
	private static ProdutoDAO produtoDAO;
	private static SetorPlantioDAO setorPlantioDAO;
	private static TipoHistoricoDAO tipoHistoricoDAO;
	private static TipoProdutoDAO tipoProdutoDAO;
	private static UfDAO ufDAO;
	private static UsuarioDAO usuarioDAO;
	private static UnidadeMedidaDAO unidadeMedidaDAO;

	public DAOFactory() {
	}

	public static DAOFactory instance() {
		try {
			if (daoFactory == null) {
				daoFactory = (DAOFactory) (DAOFactory.class).newInstance();
			}
			return daoFactory;
		} catch (Exception ex) {
			throw new RuntimeException("Não pode criar o  DAOFactory");
		}
	}

	protected Session getCurrentSession() {
		return HibernateUtil.getCurrentSession();
	}

	public Object classInstance(Class<?> clazz) {
		try {
			return Class.forName(clazz.getName()).newInstance();
		} catch (IllegalAccessException ex) {
		} catch (InstantiationException ex) {
		} catch (Exception e) {
		}
		return null;
	}

	public CidadeDAO getCidadeDAO() {
		if (cidadeDAO == null)
			cidadeDAO = (CidadeDAO) classInstance(CidadeDAO.class);
		return cidadeDAO;
	}


	public HistoricoDAO getHistoricoDAO() {
		if (historicoDAO == null)
			historicoDAO = (HistoricoDAO) classInstance(HistoricoDAO.class);
		return historicoDAO;
	}

	public LoteTerraDAO getLoteTerraDAO() {
		if (loteTerraDAO == null)
			loteTerraDAO = (LoteTerraDAO) classInstance(LoteTerraDAO.class);
		return loteTerraDAO;
	}

	public LoteVegetalDAO getLoteVegetalDAO() {
		if (loteVegetalDAO == null)
			loteVegetalDAO = (LoteVegetalDAO) classInstance(LoteVegetalDAO.class);
		return loteVegetalDAO;
	}

	

	public ProdutoDAO getProdutoDAO() {
		if (produtoDAO == null)
			produtoDAO = (ProdutoDAO) classInstance(ProdutoDAO.class);
		return produtoDAO;
	}

	public SetorPlantioDAO getSetorPlantioDAO() {
		if (setorPlantioDAO == null)
			setorPlantioDAO = (SetorPlantioDAO) classInstance(SetorPlantioDAO.class);
		return setorPlantioDAO;
	}

	public TipoHistoricoDAO getTipoHistoricoDAO() {
		if (tipoHistoricoDAO == null)
			tipoHistoricoDAO = (TipoHistoricoDAO) classInstance(TipoHistoricoDAO.class);
		return tipoHistoricoDAO;
	}

	public TipoProdutoDAO getTipoProdutoDAO() {
		if (tipoProdutoDAO == null)
			tipoProdutoDAO = (TipoProdutoDAO) classInstance(TipoProdutoDAO.class);
		return tipoProdutoDAO;
	}

	public UfDAO getUfDAO() {
		if (ufDAO == null)
			ufDAO = (UfDAO) classInstance(UfDAO.class);
		return ufDAO;
	}
	public UsuarioDAO getUsuarioDAO() {
		if (usuarioDAO == null)
			usuarioDAO = (UsuarioDAO) classInstance(UsuarioDAO.class);
		return usuarioDAO;
	}
	public UnidadeMedidaDAO getUnidadeMedidaDAO() {
		if (unidadeMedidaDAO == null)
			unidadeMedidaDAO = (UnidadeMedidaDAO) classInstance(UnidadeMedidaDAO.class);
		return unidadeMedidaDAO;
	}
}