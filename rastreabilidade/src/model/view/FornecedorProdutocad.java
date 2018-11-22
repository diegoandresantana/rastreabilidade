package model.view;

import java.util.HashMap;
import java.util.Map;

import model.entity.hibernate.Fornecedor;
import model.entity.hibernate.FornecedorProduto;
import model.entity.hibernate.Produto;
import model.exceptions.LogicException;
import model.logic.impl.FornecedorProdutoLogic;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class FornecedorProdutocad extends WindowCrud {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public FornecedorProduto fornecedorProduto = new FornecedorProduto();
	
	private Fornecedor fornecedor = new Fornecedor();
	private Produto produto = new Produto();

	@SuppressWarnings("unchecked")
	public FornecedorProdutocad() {
		super("fornecedorprodutocad.zul");
		Map<String, String> map = Executions.getCurrent().getArg();
		if (map.get("pageName") != null) {
			abertoPeloMenu = Boolean.FALSE;
		}
	}

	public void initComponentes() {
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.vincular();
	}

	@Override
	public void incluir() {
		try {
			if (this.validarForm() && this.trataVO()
					&& !this.getCrdBar().getBotao(0).isDisabled()) {
				this.fornecedorProduto = new FornecedorProdutoLogic()
						.insertReg(this.fornecedorProduto);
				if (this.fornecedorProduto.getIdfornecedorproduto() > 0) {
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro incluído com sucesso!");
					} else {
						Events.postEvent("onClose", this,
								this.fornecedorProduto);
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
					&& this.fornecedorProduto.getIdfornecedorproduto() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {
				try {
					this.fornecedorProduto = new FornecedorProdutoLogic()
							.updateReg(this.fornecedorProduto);
					Messagebox.show("Registro Salvo com sucesso!");
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro Salvo com sucesso!");
					} else {
						Events.postEvent("onClose", this,
								this.fornecedorProduto);
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
					new FornecedorProdutoLogic()
							.deleteReg(this.fornecedorProduto);
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
		this.fornecedorProduto = new FornecedorProduto();
		this.fornecedor = new Fornecedor();
		this.produto = new Produto();
		this.getCrdBar().getBotao(0).setDisabled(false);
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.vincular();
	}

	@Override
	public void pesquisar() {
		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/fornecedorprodutolis.zul", param, this,
				"retFornecedorProduto");
	}

	public void retFornecedorProduto(Object ret) {
		if (ret != null) {
			this.fornecedorProduto = (FornecedorProduto) ret;
			this.fornecedor = this.fornecedorProduto.getFornecedor();
			this.produto = this.fornecedorProduto.getProduto();
			this.getCrdBar().getBotao(0).setDisabled(true);
			this.getCrdBar().getBotao(1).setDisabled(false);
			this.vincular();
		}
	}

	@Override
	public void imprimir() {
	}

	public boolean trataVO() {
		if (this.fornecedorProduto != null) {
			if (this.fornecedorProduto.getFornecedor() == null) {
				try {
					Messagebox.show("Selecione o Fornecedor.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return false;
			}
			if (this.fornecedorProduto.getProduto() == null) {
				try {
					Messagebox.show("Selecione o Produto.");
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
			Events.postEvent("onClose", this, this.fornecedorProduto);
		}
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		if (fornecedor != null) {
			this.fornecedorProduto.setFornecedor(fornecedor);
		}
		this.fornecedor = fornecedor;
	}

	public void setProduto(Produto produto) {
		if (produto != null) {
			this.fornecedorProduto.setProduto(produto);
		}
		this.produto = produto;
	}

	public void pesquisarFornecedor() {
		new WinUtils().abrirLis("forms/fornecedorlis.zul", null, this,
				"recebeFornecedor");
	}

	public void recebeFornecedor(Object obj) {
		if (obj != null) {
			this.fornecedor = (Fornecedor) obj;
			this.fornecedorProduto.setFornecedor(fornecedor);
		}
		this.vincular();
	}

	public void pesquisarProduto() {
		new WinUtils().abrirLis("forms/produtolis.zul", null, this,
				"recebeProduto");
	}
	

	public void addFornecedor() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "fornecedorprodutocad.zul");
		String url = "forms/fornecedorcad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeFornecedor");
	}

	
}