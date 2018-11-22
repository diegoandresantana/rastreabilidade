package model.view;

import java.util.HashMap;
import java.util.Map;

import model.entity.hibernate.FornecedorProduto;
import model.entity.hibernate.NotaFiscal;
import model.entity.hibernate.NotaItem;
import model.exceptions.LogicException;
import model.logic.impl.NotaItemLogic;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class NotaItemcad extends WindowCrud {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5406290543406004706L;

	public NotaItem notaItem = new NotaItem();
	
	private FornecedorProduto fornecedorproduto = new FornecedorProduto();
	private NotaFiscal notafiscal = new NotaFiscal();

	@SuppressWarnings("unchecked")
	public NotaItemcad() {
		super("notaitemcad.zul");
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
				this.notaItem = new NotaItemLogic().insertReg(this.notaItem);
				if (this.notaItem.getIdnotaitem() > 0) {
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro incluído com sucesso!");
					} else {
						Events.postEvent("onClose", this, this.notaItem);
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
					&& this.notaItem.getIdnotaitem() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {
				try {
					this.notaItem = new NotaItemLogic()
							.updateReg(this.notaItem);
					Messagebox.show("Registro Salvo com sucesso!");
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro Salvo com sucesso!");
					} else {
						Events.postEvent("onClose", this, this.notaItem);
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
		this.notaItem = new NotaItem();
		this.fornecedorproduto = new FornecedorProduto();
		this.notafiscal = new NotaFiscal();
		this.getCrdBar().getBotao(0).setDisabled(false);
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.vincular();
	}

	@Override
	public void pesquisar() {
		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/notaitemlis.zul", param, this,
				"retNotaItem");
	}

	public void retNotaItem(Object ret) {
		if (ret != null) {
			this.notaItem = (NotaItem) ret;
			this.fornecedorproduto = this.notaItem.getFornecedorproduto();
			this.notafiscal = this.notaItem.getNotafiscal();
			this.getCrdBar().getBotao(0).setDisabled(true);
			this.getCrdBar().getBotao(1).setDisabled(false);
			this.vincular();
		}
	}

	@Override
	public void imprimir() {
	}

	public boolean trataVO() {
		if (this.notaItem != null) {
			if (this.notaItem.getFornecedorproduto() == null) {
				try {
					Messagebox.show("Selecione o Produtos do Fornecedor.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return false;
			}
			if (this.notaItem.getNotafiscal() == null) {
				try {
					Messagebox.show("Selecione o Nota Fiscal.");
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
			Events.postEvent("onClose", this, this.notaItem);
		}
	}

	public FornecedorProduto getFornecedorproduto() {
		return fornecedorproduto;
	}

	public NotaFiscal getNotafiscal() {
		return notafiscal;
	}

	public void setFornecedorproduto(FornecedorProduto fornecedorproduto) {
		if (fornecedorproduto != null) {
			this.notaItem.setFornecedorproduto(fornecedorproduto);
		}
		this.fornecedorproduto = fornecedorproduto;
	}

	public void setNotafiscal(NotaFiscal notafiscal) {
		if (notafiscal != null) {
			this.notaItem.setNotafiscal(notafiscal);
		}
		this.notafiscal = notafiscal;
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

	public void pesquisarNotaFiscal() {
		new WinUtils().abrirLis("forms/notafiscallis.zul", null, this,
				"recebeNotaFiscal");
	}

	public void recebeNotaFiscal(Object obj) {
		if (obj != null) {
			this.notafiscal = (NotaFiscal) obj;
			this.notaItem.setNotafiscal(notafiscal);
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
}