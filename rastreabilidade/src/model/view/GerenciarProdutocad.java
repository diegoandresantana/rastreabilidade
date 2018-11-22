package model.view;

import java.util.HashMap;
import java.util.Map;

import model.dao.impl.hibernate.GerenciarProdutoDAO;
import model.entity.hibernate.GerenciarProduto;
import model.entity.hibernate.LoteVegetal;
import model.entity.hibernate.NotaItem;
import model.exceptions.DaoException;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class GerenciarProdutocad extends WindowCrud {

	private static final long serialVersionUID = 6700024616378847705L;
	public GerenciarProduto gerenciarProduto = new GerenciarProduto();
	private LoteVegetal lotevegetal = new LoteVegetal();
	private NotaItem notaitem = new NotaItem();

	@SuppressWarnings("unchecked")
	public GerenciarProdutocad() {
		super("gerenciarprodutocad.zul");
		Map<String, String> map = Executions.getCurrent().getArg();
		if (map.get("pageName") != null) {
			abertoPeloMenu = Boolean.FALSE;
		}
	}

	public void initComponentes() {

		this.getCrdBar().getBotao(1).setDisabled(true);
		this.getCrdBar().getBotao(2).setDisabled(true);
		this.vincular();
	}

	@Override
	public void incluir() {
		try {
			if (this.validarForm() && this.trataVO()
					&& !this.getCrdBar().getBotao(0).isDisabled()) {

				GerenciarProdutoDAO gerenciarprodutoDAO = new GerenciarProdutoDAO();
				gerenciarProduto = gerenciarprodutoDAO
						.insertReg(this.gerenciarProduto);
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro incluído com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.gerenciarProduto);
				}
				this.getCrdBar().getBotao(0).setDisabled(true);
				this.getCrdBar().getBotao(1).setDisabled(false);
				this.vincular();
			}
		}catch(WrongValueException e){			
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
					&& this.gerenciarProduto.getIdgerenciarproduto() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {

				GerenciarProdutoDAO gerenciarprodutoDAO = new GerenciarProdutoDAO();
				gerenciarprodutoDAO.updateReg(this.gerenciarProduto);
				Messagebox.show("Registro Salvo com sucesso!");
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro Salvo com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.gerenciarProduto);
				}
				this.vincular();
			}
		}catch(WrongValueException e){			
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.limpar();
	}

	@Override
	public void apagar() {

		try {
			if (Messagebox.show("Deseja realmente excluir ?", "Aviso!!!",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {

				try {
					new GerenciarProdutoDAO().deleteReg(gerenciarProduto);
					Messagebox.show("Registro excluído com sucesso!");
					this.limpar();
				} catch (DaoException e) {
					Messagebox.show("Registro excluído com sucesso!");
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

		this.gerenciarProduto = new GerenciarProduto();
		this.lotevegetal = new LoteVegetal();
		this.notaitem = new NotaItem();

		this.getCrdBar().getBotao(0).setDisabled(false);
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.getCrdBar().getBotao(2).setDisabled(true);
		this.vincular();
	}

	@Override
	public void pesquisar() {

		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/gerenciarprodutolis.zul", param, this,
				"retGerenciarProduto");
	}

	public void retGerenciarProduto(Object ret) {
		if (ret != null) {
			this.gerenciarProduto = (GerenciarProduto) ret;

			this.gerenciarProduto = (GerenciarProduto) ret;
			this.lotevegetal = this.gerenciarProduto.getLotevegetal();
			this.notaitem = this.gerenciarProduto.getNotaitem();
			this.getCrdBar().getBotao(0).setDisabled(true);
			this.getCrdBar().getBotao(1).setDisabled(false);
			this.getCrdBar().getBotao(2).setDisabled(false);
			this.vincular();
		}
	}

	@Override
	public void imprimir() {
	}

	public boolean trataVO() {
		if (this.gerenciarProduto != null) {
			// this.gerenciarProduto.setUsualt(this.getLogin());
			// this.gerenciarProduto.setDatalt(WinUtils.getDataBanco());

			if (this.gerenciarProduto.getLotevegetal() == null) {
				try {
					Messagebox.show("Selecione o Lote de Vegetal.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return false;
			}
			if (this.gerenciarProduto.getNotaitem() == null) {
				try {
					Messagebox.show("Selecione o Itens da Nota.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return false;
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
			Events.postEvent("onClose", this, this.gerenciarProduto);
		}
	}

	public LoteVegetal getLotevegetal() {
		return lotevegetal;
	}

	public NotaItem getNotaitem() {
		return notaitem;
	}

	public void setLotevegetal(LoteVegetal lotevegetal) {
		if (lotevegetal != null) {
			this.gerenciarProduto.setLotevegetal(lotevegetal);
		}
		this.lotevegetal = lotevegetal;
	}

	public void setNotaitem(NotaItem notaitem) {
		if (notaitem != null) {
			this.gerenciarProduto.setNotaitem(notaitem);
		}
		this.notaitem = notaitem;
	}

	public void pesquisarLoteVegetal() {
		new WinUtils().abrirLis("forms/lotevegetallis.zul", null, this,
				"recebeLoteVegetal");
	}

	public void recebeLoteVegetal(Object obj) {
		if (obj != null) {
			this.lotevegetal = (LoteVegetal) obj;
			this.gerenciarProduto.setLotevegetal(lotevegetal);
		}
		this.vincular();
	}

	public void pesquisarNotaItem() {
		new WinUtils().abrirLis("forms/notaitemlis.zul", null, this,
				"recebeNotaItem");
	}

	public void recebeNotaItem(Object obj) {
		if (obj != null) {
			this.notaitem = (NotaItem) obj;
			this.gerenciarProduto.setNotaitem(notaitem);
		}
		this.vincular();
	}

	public void addLoteVegetal() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "gerenciarprodutocad.zul");
		String url = "forms/lotevegetalcad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeLoteVegetal");
	}

	public void addNotaItem() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "gerenciarprodutocad.zul");
		String url = "forms/notaitemcad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeNotaItem");
	}

}
