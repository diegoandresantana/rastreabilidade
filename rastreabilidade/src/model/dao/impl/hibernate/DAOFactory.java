package model.dao.impl.hibernate;

import model.dao.impl.hibernate.CidadeDAO;
import model.dao.impl.hibernate.ClienteDAO;
import model.dao.impl.hibernate.DestinoLoteDAO;
import model.dao.impl.hibernate.FornecedorDAO;
import model.dao.impl.hibernate.FornecedorProdutoDAO;
import model.dao.impl.hibernate.GerenciarProdutoDAO;
import model.dao.impl.hibernate.HistoricoDAO;
import model.dao.impl.hibernate.LoteTerraDAO;
import model.dao.impl.hibernate.LoteVegetalDAO;
import model.dao.impl.hibernate.NotaFiscalDAO;
import model.dao.impl.hibernate.NotaItemDAO;
import model.dao.impl.hibernate.ProdutoDAO;
import model.dao.impl.hibernate.SetorPlantioDAO;
import model.dao.impl.hibernate.TipoHistoricoDAO;
import model.dao.impl.hibernate.TipoProdutoDAO;
import model.dao.impl.hibernate.UfDAO;
import model.dao.impl.hibernate.UnidadeMedidaDAO;
import model.dao.impl.hibernate.DAOFactory;
import org.hibernate.Session;
import model.util.HibernateUtil;

public class DAOFactory {
	private static DAOFactory daoFactory;
	private static CidadeDAO cidadeDAO;
	private static ClienteDAO clienteDAO;
	private static DestinoLoteDAO destinoLoteDAO;
	private static FornecedorDAO fornecedorDAO;
	private static FornecedorProdutoDAO fornecedorProdutoDAO;
	private static GerenciarProdutoDAO gerenciarProdutoDAO;
	private static HistoricoDAO historicoDAO;
	private static LoteTerraDAO loteTerraDAO;
	private static LoteVegetalDAO loteVegetalDAO;
	private static NotaFiscalDAO notaFiscalDAO;
	private static NotaItemDAO notaItemDAO;
	private static ProdutoDAO produtoDAO;
	private static SetorPlantioDAO setorPlantioDAO;
	private static TipoHistoricoDAO tipoHistoricoDAO;
	private static TipoProdutoDAO tipoProdutoDAO;
	private static UfDAO ufDAO;
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

	public ClienteDAO getClienteDAO() {
		if (clienteDAO == null)
			clienteDAO = (ClienteDAO) classInstance(ClienteDAO.class);
		return clienteDAO;
	}

	public DestinoLoteDAO getDestinoLoteDAO() {
		if (destinoLoteDAO == null)
			destinoLoteDAO = (DestinoLoteDAO) classInstance(DestinoLoteDAO.class);
		return destinoLoteDAO;
	}

	public FornecedorDAO getFornecedorDAO() {
		if (fornecedorDAO == null)
			fornecedorDAO = (FornecedorDAO) classInstance(FornecedorDAO.class);
		return fornecedorDAO;
	}

	public FornecedorProdutoDAO getFornecedorProdutoDAO() {
		if (fornecedorProdutoDAO == null)
			fornecedorProdutoDAO = (FornecedorProdutoDAO) classInstance(FornecedorProdutoDAO.class);
		return fornecedorProdutoDAO;
	}

	public GerenciarProdutoDAO getGerenciarProdutoDAO() {
		if (gerenciarProdutoDAO == null)
			gerenciarProdutoDAO = (GerenciarProdutoDAO) classInstance(GerenciarProdutoDAO.class);
		return gerenciarProdutoDAO;
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

	public NotaFiscalDAO getNotaFiscalDAO() {
		if (notaFiscalDAO == null)
			notaFiscalDAO = (NotaFiscalDAO) classInstance(NotaFiscalDAO.class);
		return notaFiscalDAO;
	}

	public NotaItemDAO getNotaItemDAO() {
		if (notaItemDAO == null)
			notaItemDAO = (NotaItemDAO) classInstance(NotaItemDAO.class);
		return notaItemDAO;
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

	public UnidadeMedidaDAO getUnidadeMedidaDAO() {
		if (unidadeMedidaDAO == null)
			unidadeMedidaDAO = (UnidadeMedidaDAO) classInstance(UnidadeMedidaDAO.class);
		return unidadeMedidaDAO;
	}
}