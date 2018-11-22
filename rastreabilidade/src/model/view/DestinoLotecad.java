package model.view;

import java.util.HashMap;
import java.util.Map;

import model.dao.impl.hibernate.DestinoLoteDAO;
import model.entity.hibernate.Cliente;
import model.entity.hibernate.DestinoLote;
import model.entity.hibernate.LoteVegetal;
import model.exceptions.DaoException;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class DestinoLotecad extends WindowCrud {

	private static final long serialVersionUID = -8225009276237737749L;
	public DestinoLote destinoLote = new DestinoLote();
	private Cliente cliente = new Cliente();
	private LoteVegetal lotevegetal = new LoteVegetal();

	@SuppressWarnings("unchecked")
	public DestinoLotecad() {
		super("destinolotecad.zul");
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

				DestinoLoteDAO destinoloteDAO = new DestinoLoteDAO();
				destinoLote = destinoloteDAO.insertReg(this.destinoLote);
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro incluído com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.destinoLote);
				}
				this.getCrdBar().getBotao(0).setDisabled(true);
				this.getCrdBar().getBotao(1).setDisabled(false);
				this.vincular();
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
					&& this.destinoLote.getIddestinolote() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {

				DestinoLoteDAO destinoloteDAO = new DestinoLoteDAO();
				destinoloteDAO.updateReg(this.destinoLote);
				Messagebox.show("Registro Salvo com sucesso!");
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro Salvo com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.destinoLote);
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
					new DestinoLoteDAO().deleteReg(destinoLote);
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

		this.destinoLote = new DestinoLote();
		this.cliente = new Cliente();
		this.lotevegetal = new LoteVegetal();

		this.getCrdBar().getBotao(0).setDisabled(false);
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.getCrdBar().getBotao(2).setDisabled(true);
		this.vincular();
	}

	@Override
	public void pesquisar() {

		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/destinolotelis.zul", param, this,
				"retDestinoLote");
	}

	public void retDestinoLote(Object ret) {
		if (ret != null) {
			this.destinoLote = (DestinoLote) ret;

			this.destinoLote = (DestinoLote) ret;
			this.cliente = this.destinoLote.getCliente();
			this.lotevegetal = this.destinoLote.getLotevegetal();
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
		if (this.destinoLote != null) {
			// this.destinoLote.setUsualt(this.getLogin());
			// this.destinoLote.setDatalt(WinUtils.getDataBanco());

			if (this.destinoLote.getCliente() == null) {
				try {
					Messagebox.show("Selecione o Cliente.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return false;
			}
			if (this.destinoLote.getLotevegetal() == null) {
				try {
					Messagebox.show("Selecione o Lote de Vegetal.");
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
			Events.postEvent("onClose", this, this.destinoLote);
		}
	}

	public Cliente getCliente() {
		return cliente;
	}

	public LoteVegetal getLotevegetal() {
		return lotevegetal;
	}

	public void setCliente(Cliente cliente) {
		if (cliente != null) {
			this.destinoLote.setCliente(cliente);
		}
		this.cliente = cliente;
	}

	public void setLotevegetal(LoteVegetal lotevegetal) {
		if (lotevegetal != null) {
			this.destinoLote.setLotevegetal(lotevegetal);
		}
		this.lotevegetal = lotevegetal;
	}

	public void pesquisarCliente() {
		new WinUtils().abrirLis("forms/clientelis.zul", null, this,
				"recebeCliente");
	}

	public void recebeCliente(Object obj) {
		if (obj != null) {
			this.cliente = (Cliente) obj;
			this.destinoLote.setCliente(cliente);
		}
		this.vincular();
	}

	public void pesquisarLoteVegetal() {
		new WinUtils().abrirLis("forms/lotevegetallis.zul", null, this,
				"recebeLoteVegetal");
	}

	public void recebeLoteVegetal(Object obj) {
		if (obj != null) {
			this.lotevegetal = (LoteVegetal) obj;
			this.destinoLote.setLotevegetal(lotevegetal);
		}
		this.vincular();
	}

	public void addCliente() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "destinolotecad.zul");
		String url = "forms/clientecad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeCliente");
	}

	public void addLoteVegetal() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "destinolotecad.zul");
		String url = "forms/lotevegetalcad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeLoteVegetal");
	}

}
