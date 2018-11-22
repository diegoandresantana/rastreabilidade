package model.view;

import java.util.HashMap;
import java.util.Map;

import model.dao.ProdutoDAO;
import model.dao.TipoProdutoDAO;
import model.dao.UnidadeMedidaDAO;
import model.entity.hibernate.Produto;
import model.entity.hibernate.TipoProduto;
import model.entity.hibernate.UnidadeMedida;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
import model.logic.LogicFactory;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class Produtocad extends WindowCrud {

	private static final long serialVersionUID = 6593308991222203163L;
	public Produto produto = new Produto();
	public ListModelList lmTipoProduto;
	public ListModelList lmUnidadeMedida;
	private TipoProduto tipoproduto = new TipoProduto();
	private UnidadeMedida unidademedida = new UnidadeMedida();

	@SuppressWarnings("unchecked")
	public Produtocad() {
		super("produtocad.zul");
		Map<String, String> map = Executions.getCurrent().getArg();
		if (map.get("pageName") != null) {
			abertoPeloMenu = Boolean.FALSE;
		}
	}

	public void initComponentes() {

		lmTipoProduto = new ListModelList(LogicFactory.getTipoProdutoLogic()
				.getRegByExample(null));
		lmUnidadeMedida = new ListModelList(LogicFactory
				.getUnidadeMedidaLogic().getRegByExample(null));
		controleBotoesDisable(false,true, true);
		this.vincular();
	}

	@Override
	public void incluir() {
		try {
			if (this.validarForm() && this.trataVO()
					&& !this.getCrdBar().getBotao(0).isDisabled()) {

				produto = LogicFactory.getProdutoLogic()
						.insertReg(this.produto);
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro incluído com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.produto);
				}
				limpar();
			}
		} catch (WrongValueException e) {
			throw e;
		} catch (Exception e) {
			try {
				Messagebox.show("O registro não pode ser incluído: "
						+ e.getMessage(), "Erro:", Messagebox.OK,
						Messagebox.ERROR);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void salvar() {
		try {
			if (this.validarForm() && this.trataVO()
					&& this.produto.getIdproduto() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {

				LogicFactory.getProdutoLogic().updateReg(this.produto);

				if (this.abertoPeloMenu) {
					Messagebox.show("Registro Salvo com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.produto);
				}
				limpar();
			}
		} catch (WrongValueException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void apagar() {

		try {
			if (Messagebox.show("Deseja realmente excluir ?", "Aviso!!!",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {

				try {
					LogicFactory.getProdutoLogic().deleteReg(produto);
					Messagebox.show("Registro excluído com sucesso!");
					this.limpar();
				} catch (LogicException e) {
					Messagebox.show(e.getMessage());
				}
			}

		} catch (InterruptedException e) {
			try {
				Messagebox.show("Registro não pode ser excluído!");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void limpar() {

		this.produto = new Produto();
		this.tipoproduto = new TipoProduto();
		this.unidademedida = new UnidadeMedida();
		controleBotoesDisable(false,true, true);
		
		this.vincular();
	}

	@Override
	public void pesquisar() {

		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/produtolis.zul", param, this,
				"retProduto");
	}

	public void retProduto(Object ret) {
		if (ret != null) {
			this.produto = (Produto) ret;

			this.produto = (Produto) ret;
			this.tipoproduto = this.produto.getTipoproduto();
			this.unidademedida = this.produto.getUnidademedida();
			controleBotoesDisable(true,false, false);
			
			this.vincular();
		}
	}

	@Override
	public void imprimir() {
	}

	public boolean trataVO() {
		if (this.produto != null) {
			// this.produto.setUsualt(this.getLogin());
			// this.produto.setDatalt(WinUtils.getDataBanco());

			if (this.produto.getTipoproduto() == null) {
				  throw new WrongValueException(getComponente("tipoproduto"),"Selecione o Tipo de Produto!");	
			
			}
			if (this.produto.getUnidademedida() == null) {
				  throw new WrongValueException(getComponente("unidademedida"),"Selecione o Unidade de Medida!");	
				
			}
			return true;
		}
		return false;
	}

	@Override
	public void sair() {
		if (this.abertoPeloMenu) {
			this.detach();
		} else {
			Events.postEvent("onClose", this, this.produto);
		}
	}

	public TipoProduto getTipoproduto() {
		return tipoproduto;
	}

	public UnidadeMedida getUnidademedida() {
		return unidademedida;
	}

	public void setTipoproduto(TipoProduto tipoproduto) {
		if (tipoproduto != null) {
			this.produto.setTipoproduto(tipoproduto);
		}
		this.tipoproduto = tipoproduto;
	}

	public void setUnidademedida(UnidadeMedida unidademedida) {
		if (unidademedida != null) {
			this.produto.setUnidademedida(unidademedida);
		}
		this.unidademedida = unidademedida;
	}

	public void recebeTipoProduto(Object obj) {
		lmTipoProduto = new ListModelList(LogicFactory.getTipoProdutoLogic()
				.getRegByExample(null));
		lmTipoProduto = new ListModelList(LogicFactory.getTipoProdutoLogic()
				.getRegByExample(null));
		if (obj != null) {
			this.tipoproduto = (TipoProduto) obj;
			this.produto.setTipoproduto(tipoproduto);
		}
		this.vincular();
	}

	public void recebeUnidadeMedida(Object obj) {
		lmUnidadeMedida = new ListModelList(LogicFactory
				.getUnidadeMedidaLogic().getRegByExample(null));
		lmUnidadeMedida = new ListModelList(LogicFactory
				.getUnidadeMedidaLogic().getRegByExample(null));
		if (obj != null) {
			this.unidademedida = (UnidadeMedida) obj;
			this.produto.setUnidademedida(unidademedida);
		}
		this.vincular();
	}

	public void addTipoProduto() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "produtocad.zul");
		String url = "forms/tipoprodutocad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeTipoProduto");
	}

	public void addUnidadeMedida() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "produtocad.zul");
		String url = "forms/unidademedidacad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeUnidadeMedida");
	}

}
