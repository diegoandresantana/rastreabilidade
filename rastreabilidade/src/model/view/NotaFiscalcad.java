package model.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.entity.hibernate.Fornecedor;
import model.entity.hibernate.FornecedorProduto;
import model.entity.hibernate.NotaFiscal;
import model.entity.hibernate.NotaItem;
import model.exceptions.LogicException;
import model.logic.impl.FornecedorLogic;
import model.logic.impl.NotaFiscalLogic;
import model.logic.impl.NotaItemLogic;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class NotaFiscalcad extends WindowCrud {
	
	private static final long serialVersionUID = -6257589126768491806L;
	public NotaItem notaItem;
	public NotaFiscal notaFiscal;
	private FornecedorProduto fornecedorproduto;
	private Fornecedor fornecedor;
	public List<NotaItem> listNotaItem;
	public String itensRemovidos = "";

	@SuppressWarnings("unchecked")
	public NotaFiscalcad() {
		super("notafiscalcad.zul");
		notaItem = new NotaItem();
		notaFiscal = new NotaFiscal();
		fornecedorproduto = new FornecedorProduto();
		fornecedor = new Fornecedor();
		listNotaItem = new ArrayList<NotaItem>();
		Map<String, String> map = Executions.getCurrent().getArg();
		if (map.get("pageName") != null) {
			abertoPeloMenu = Boolean.FALSE;
		}
	}

	public void initComponentes() {
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.vincular();
	}

	public void addItem() {
		if (trataNotaItem()) {
			if (listNotaItem.contains(notaItem)) {
				try {
					Messagebox.show("Ja exite Itens da Nota na Lista!");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				this.notaItem.setNotafiscal(this.notaFiscal);

				listNotaItem.add(notaItem);
				notaItem = new NotaItem();
				this.fornecedorproduto = new FornecedorProduto();
			}
		}
		this.vincular();
	}

	public void removerItem(NotaItem item) {

		try {
			if (Messagebox.show("Deseja realmente excluir a mensagem?",
					"Aviso!!!", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION) == Messagebox.YES) {
				if (item != null) {
					if (item.getIdnotaitem() != null) {
						if (!itensRemovidos.equals("")) {
							itensRemovidos += "," + item.getIdnotaitem();
						} else {
							itensRemovidos += "" + item.getIdnotaitem();
						}
					}
				}

				listNotaItem.remove(item);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.vincular();
	}

	@Override
	public void incluir() {
		try {
			if (this.validarForm() && this.trataVO()
					&& !this.getCrdBar().getBotao(0).isDisabled()) {
				this.notaFiscal.setNotaItemList(listNotaItem);
				this.fornecedor = new FornecedorLogic()
						.insertReg(this.fornecedor);
				if (this.notaFiscal.getIdnotafiscal() > 0) {
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro incluído com sucesso!");
					} else {
						Events.postEvent("onClose", this, this.notaFiscal);
					}
					this.getCrdBar().getBotao(0).setDisabled(true);
					this.getCrdBar().getBotao(1).setDisabled(false);
					this.vincular();
				} else {
					Messagebox.show("O registro não pode ser incluído!",
							"Erro:", Messagebox.OK, Messagebox.ERROR);
				}
			}
		} catch(WrongValueException e){			
			throw e;
		}catch (Exception e) {
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
					&& this.notaFiscal.getIdnotafiscal() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {
				try {
					new NotaItemLogic().delByCodigos(itensRemovidos);
					this.notaFiscal.setNotaItemList(listNotaItem);
					this.notaFiscal = new NotaFiscalLogic()
							.updateReg(this.notaFiscal);
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro Salvo com sucesso!");
					} else {
						Events.postEvent("onClose", this, this.notaFiscal);
					}
				} catch (LogicException e) {
					Messagebox.show(e.getMessage(), "Erro:", Messagebox.OK,
							Messagebox.ERROR);
					e.printStackTrace();
				}
				this.vincular();
			} else {
				Messagebox.show("O registro não pode ser salvo!", "Erro:",
						Messagebox.OK, Messagebox.ERROR);
			}
		} catch(WrongValueException e){			
			throw e;
		}catch (Exception e) {
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
					new NotaItemLogic().deleteReg(this.notaItem);
				} catch (LogicException e) {
					this.vincular();
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
		this.notaFiscal = new NotaFiscal();
		this.fornecedor = new Fornecedor();
		this.getCrdBar().getBotao(0).setDisabled(false);
		this.getCrdBar().getBotao(1).setDisabled(true);
		limparNotaItem();
	}

	public void limparNotaItem() {
		this.notaItem = new NotaItem();
		this.fornecedorproduto = new FornecedorProduto();
		this.vincular();
	}

	@Override
	public void pesquisar() {
		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/notafiscallis.zul", param, this,
				"retNotaFiscal");
	}

	public void retNotaFiscal(Object ret) {
		if (ret != null) {
			this.notaFiscal = (NotaFiscal) ret;
			this.fornecedor = this.notaFiscal.getFornecedor();
			this.listNotaItem = this.notaFiscal.getNotaItemList();
			this.getCrdBar().getBotao(0).setDisabled(true);
			this.getCrdBar().getBotao(1).setDisabled(false);
			this.vincular();
		}
	}

	@Override
	public void imprimir() {
	}

	public boolean trataVO() {
		if (this.notaFiscal != null) {
			if (this.notaFiscal.getFornecedor() == null) {
				try {
					Messagebox.show("Selecione o Fornecedor.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return false;
			}
			return true;
		}
		return false;
	}

	public boolean trataNotaItem() {
		if (this.notaItem.getFornecedorproduto() == null) {
			try {
				Messagebox.show("Selecione o Produtos do Fornecedor.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}

	@Override
	public void sair() {
		if (this.abertoPeloMenu) {
			this.detach();
		} else {
			Events.postEvent("onClose", this, this.notaItem);
		}
	}

	public FornecedorProduto getFornecedorproduto() {
		return fornecedorproduto;
	}

	public void setFornecedorproduto(FornecedorProduto fornecedorproduto) {
		if (fornecedorproduto != null) {
			this.notaItem.setFornecedorproduto(fornecedorproduto);
		}
		this.fornecedorproduto = fornecedorproduto;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		if (fornecedor != null) {
			this.notaFiscal.setFornecedor(fornecedor);
		}
		this.fornecedor = fornecedor;
	}

	public void pesquisarFornecedorProduto() {
		new WinUtils().abrirLis("forms/fornecedorprodutolis.zul", null, this,
				"recebeFornecedorProduto");
	}

	public void recebeFornecedorProduto(Object obj) {
		if (obj != null) {
			this.fornecedorproduto = (FornecedorProduto) obj;
			this.notaItem.setFornecedorproduto(fornecedorproduto);
		}
		this.vincular();
	}

	public void pesquisarFornecedor() {
		new WinUtils().abrirLis("forms/fornecedorlis.zul", null, this,
				"recebeFornecedor");
	}

	public void recebeFornecedor(Object obj) {
		if (obj != null) {
			this.fornecedor = (Fornecedor) obj;
			this.notaFiscal.setFornecedor(fornecedor);
		}
		this.vincular();
	}

	public void addFornecedorProduto() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "notaitemcad.zul");
		String url = "forms/fornecedorprodutocad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeFornecedorProduto");
	}

	public void addNotaFiscal() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "notaitemcad.zul");
		String url = "forms/notafiscalcad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeNotaFiscal");
	}

	public void addFornecedor() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "notafiscalcad.zul");
		String url = "forms/fornecedorcad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeFornecedor");
	}

	public void buscaPorCodFornecedorProduto() {
		if (this.notaItem.getFornecedorproduto() != null) {
			// recebeFornecedorProduto(new FornecedorProdutoLogic()
			// .getRegByCod(this.notaItem.getIdfornecedorproduto()));
		}
	}

	public void buscaPorCodNotaFiscal() {
		if (this.notaItem.getNotafiscal() != null) {
			// recebeNotaFiscal(new
			// NotaFiscalLogic().getRegByCod(this.notaItem.getIdnotafiscal()));
		}
	}

	public void buscaPorCodFornecedor() {
		if (this.notaFiscal.getFornecedor() != null) {
			// recebeFornecedor(new
			// FornecedorLogic().getRegByCod(this.notaFiscal.getIdfornecedor()));
		}
	}
}