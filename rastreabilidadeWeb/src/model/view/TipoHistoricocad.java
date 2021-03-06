package model.view;

import java.util.HashMap;
import java.util.Map;

import model.entity.hibernate.TipoHistorico;
import model.entity.hibernate.UnidadeMedida;
import model.exceptions.LogicException;
import model.logic.LogicFactory;

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

				LogicFactory.getTipoHistoricoLogic().insertReg(this.tipoHistorico);
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro inclu�do com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.tipoHistorico);
				}
				limpar();
			}
		} catch (LogicException e) {
			try {
				Messagebox.show(e.getMessage(), "Erro:", Messagebox.OK,
						Messagebox.ERROR);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();

		} catch (WrongValueException e) {
			throw e;
		} catch (Exception e) {
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

				LogicFactory.getTipoHistoricoLogic().updateReg(
						this.tipoHistorico);
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro Salvo com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.tipoHistorico);
				}
				limpar();
			}
		} catch (LogicException e) {
			try {
				Messagebox.show(e.getMessage(), "Erro:", Messagebox.OK,
						Messagebox.ERROR);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();

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
					LogicFactory.getTipoHistoricoLogic().deleteReg(	tipoHistorico);
					Messagebox.show("Registro exclu�do com sucesso!");
					this.limpar();
				} catch (LogicException e) {
					Messagebox.show(e.getMessage());
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
		controleBotoesDisable(false,true, true);
		
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
			
			controleBotoesDisable(true,false, false);
			
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
		lmUnidadeMedida = new ListModelList(LogicFactory
				.getUnidadeMedidaLogic().getRegByExample(null));
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
