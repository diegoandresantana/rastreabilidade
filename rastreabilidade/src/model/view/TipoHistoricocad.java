package model.view;

import java.util.HashMap;
import java.util.Map;

import model.dao.impl.hibernate.TipoHistoricoDAO;
import model.dao.impl.hibernate.UnidadeMedidaDAO;
import model.entity.hibernate.TipoHistorico;
import model.entity.hibernate.UnidadeMedida;
import model.exceptions.DaoException;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class TipoHistoricocad extends WindowCrud {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1988558598397420496L;
	public TipoHistorico tipoHistorico = new TipoHistorico();
	public ListModelList lmUnidadeMedida;
	private UnidadeMedida unidademedida = new UnidadeMedida();
	
	@SuppressWarnings("unchecked")
	public TipoHistoricocad() {
		super("tipohistoricocad.zul");
		Map<String, String> map = Executions.getCurrent().getArg();
		if (map.get("pageName") != null) {
			abertoPeloMenu = Boolean.FALSE;
		}
	}

	public void initComponentes() {

		lmUnidadeMedida = new ListModelList(new UnidadeMedidaDAO()
				.getRegByExample(null));
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.getCrdBar().getBotao(2).setDisabled(true);
		this.vincular();
	}

	@Override
	public void incluir() {
		try {
			if (this.validarForm() && this.trataVO()
					&& !this.getCrdBar().getBotao(0).isDisabled()) {

				TipoHistoricoDAO tipohistoricoDAO = new TipoHistoricoDAO();
				tipoHistorico = tipohistoricoDAO.insertReg(this.tipoHistorico);
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro inclu�do com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.tipoHistorico);
				}
				this.getCrdBar().getBotao(0).setDisabled(true);
				this.getCrdBar().getBotao(1).setDisabled(false);
				this.vincular();
			}
		} catch(WrongValueException e){			
			throw e;
		}catch (Exception e) {
			try {
				Messagebox.show("O registro n�o pode ser inclu�do: "
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
					&& this.tipoHistorico.getIdtipohistorico() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {

				TipoHistoricoDAO tipohistoricoDAO = new TipoHistoricoDAO();
				tipohistoricoDAO.updateReg(this.tipoHistorico);
				Messagebox.show("Registro Salvo com sucesso!");
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro Salvo com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.tipoHistorico);
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
					new TipoHistoricoDAO().deleteReg(tipoHistorico);
					Messagebox.show("Registro exclu�do com sucesso!");
					this.limpar();
				} catch (DaoException e) {
					Messagebox.show("Registro exclu�do com sucesso!");
				}
			}

		} catch (InterruptedException e) {
			try {
				Messagebox.show("Registro n�o pode ser exclu�do!");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void limpar() {

		this.tipoHistorico = new TipoHistorico();
		this.unidademedida = new UnidadeMedida();

		this.getCrdBar().getBotao(0).setDisabled(false);
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.getCrdBar().getBotao(2).setDisabled(true);
		this.vincular();
	}

	@Override
	public void pesquisar() {

		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/tipohistoricolis.zul", param, this,
				"retTipoHistorico");
	}

	public void retTipoHistorico(Object ret) {
		if (ret != null) {
			this.tipoHistorico = (TipoHistorico) ret;

			this.tipoHistorico = (TipoHistorico) ret;
			this.unidademedida = this.tipoHistorico.getUnidademedida();
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
		if (this.tipoHistorico != null) {
			// this.tipoHistorico.setUsualt(this.getLogin());
			// this.tipoHistorico.setDatalt(WinUtils.getDataBanco());

			if (this.tipoHistorico.getUnidademedida() == null) {
				try {
					Messagebox.show("Selecione o Unidade de Medida.");
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
			Events.postEvent("onClose", this, this.tipoHistorico);
		}
	}

	public UnidadeMedida getUnidademedida() {
		return unidademedida;
	}

	public void setUnidademedida(UnidadeMedida unidademedida) {
		if (unidademedida != null) {
			this.tipoHistorico.setUnidademedida(unidademedida);
		}
		this.unidademedida = unidademedida;
	}

	public void recebeUnidadeMedida(Object obj) {
		lmUnidadeMedida = new ListModelList(new UnidadeMedidaDAO()
				.getRegByExample(null));
		if (obj != null) {
			this.unidademedida = (UnidadeMedida) obj;
			this.tipoHistorico.setUnidademedida(unidademedida);
		}
		this.vincular();
	}

	public void addUnidadeMedida() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "tipohistoricocad.zul");
		String url = "forms/unidademedidacad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeUnidadeMedida");
	}

}
