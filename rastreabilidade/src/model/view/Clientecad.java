package model.view;

import java.util.HashMap;
import java.util.Map;

import model.dao.impl.hibernate.ClienteDAO;
import model.entity.hibernate.Cidade;
import model.entity.hibernate.Cliente;
import model.exceptions.DaoException;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class Clientecad extends WindowCrud {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8110440006456179537L;
	public Cliente cliente = new Cliente();
	private Cidade cidade = new Cidade();

	@SuppressWarnings("unchecked")
	public Clientecad() {
		super("clientecad.zul");
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

				ClienteDAO clienteDAO = new ClienteDAO();
				cliente = clienteDAO.insertReg(this.cliente);
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro incluído com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.cliente);
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
					&& this.cliente.getIdcliente() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {

				ClienteDAO clienteDAO = new ClienteDAO();
				clienteDAO.updateReg(this.cliente);
				Messagebox.show("Registro Salvo com sucesso!");
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro Salvo com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.cliente);
				}
				this.vincular();
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
					new ClienteDAO().deleteReg(cliente);
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

		this.cliente = new Cliente();
		this.cidade = new Cidade();

		this.getCrdBar().getBotao(0).setDisabled(false);
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.getCrdBar().getBotao(2).setDisabled(true);
		this.vincular();
	}

	@Override
	public void pesquisar() {

		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/clientelis.zul", param, this,
				"retCliente");
	}

	public void retCliente(Object ret) {
		if (ret != null) {
			this.cliente = (Cliente) ret;

			this.cliente = (Cliente) ret;
			this.cidade = this.cliente.getCidade();
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
		if (this.cliente != null) {
			// this.cliente.setUsualt(this.getLogin());
			// this.cliente.setDatalt(WinUtils.getDataBanco());

			if (this.cliente.getCidade() == null) {
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
			Events.postEvent("onClose", this, this.cliente);
		}
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		if (cidade != null) {
			this.cliente.setCidade(cidade);
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
			this.cliente.setCidade(cidade);
		}
		this.vincular();
	}

	public void addCidade() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "clientecad.zul");
		String url = "forms/cidadecad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeCidade");
	}

}
