package model.view;

import java.util.HashMap;
import java.util.Map;

import model.entity.hibernate.Cidade;
import model.entity.hibernate.Fornecedor;
import model.exceptions.LogicException;
import model.logic.impl.FornecedorLogic;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class Fornecedorcad extends WindowCrud {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7391558983659264739L;

	public Fornecedor fornecedor = new Fornecedor();
	
	private Cidade cidade = new Cidade();

	@SuppressWarnings("unchecked")
	public Fornecedorcad() {
		super("fornecedorcad.zul");
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
				this.fornecedor = new FornecedorLogic()
						.insertReg(this.fornecedor);
				if (this.fornecedor.getIdfornecedor() > 0) {
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro incluído com sucesso!");
					} else {
						Events.postEvent("onClose", this, this.fornecedor);
					}
					this.getCrdBar().getBotao(0).setDisabled(true);
					this.getCrdBar().getBotao(1).setDisabled(false);
					this.vincular();
				} else {
					Messagebox.show("O registro não pode ser incluído!",
							"Erro:", Messagebox.OK, Messagebox.ERROR);
				}
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
					&& this.fornecedor.getIdfornecedor() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {
				try {
					this.fornecedor = new FornecedorLogic()
							.updateReg(this.fornecedor);
					Messagebox.show("Registro Salvo com sucesso!");
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro Salvo com sucesso!");
					} else {
						Events.postEvent("onClose", this, this.fornecedor);
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
					new FornecedorLogic().deleteReg(this.fornecedor);
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
		this.fornecedor = new Fornecedor();
		this.cidade = new Cidade();
		this.getCrdBar().getBotao(0).setDisabled(false);
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.vincular();
	}

	@Override
	public void pesquisar() {
		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/fornecedorlis.zul", param, this,
				"retFornecedor");
	}

	public void retFornecedor(Object ret) {
		if (ret != null) {
			this.fornecedor = (Fornecedor) ret;
			this.cidade = this.fornecedor.getCidade();
			this.getCrdBar().getBotao(0).setDisabled(true);
			this.getCrdBar().getBotao(1).setDisabled(false);
			this.vincular();
		}
	}

	@Override
	public void imprimir() {
	}

	public boolean trataVO() {
		if (this.fornecedor != null) {
			if (this.fornecedor.getCidade() == null) {
				try {
					Messagebox.show("Selecione o Cidade.");
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
			Events.postEvent("onClose", this, this.fornecedor);
		}
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		if (cidade != null) {
			this.fornecedor.setCidade(cidade);
		}
		this.cidade = cidade;
	}

	public void pesquisarCidade() {
		new WinUtils().abrirLis("forms/cidadelis.zul", null, this,
				"recebeCidade");
	}

	public void recebeCidade(Object obj) {
		if (obj != null) {
			this.cidade = (Cidade) obj;
			this.fornecedor.setCidade(cidade);
		}
		this.vincular();
	}

	public void addCidade() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "fornecedorcad.zul");
		String url = "forms/cidadecad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeCidade");
	}
}