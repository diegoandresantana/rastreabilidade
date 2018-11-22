package model.view;

import java.util.HashMap;
import java.util.Map;

import model.dao.impl.hibernate.LoteVegetalDAO;
import model.entity.hibernate.LoteVegetal;
import model.entity.hibernate.SetorPlantio;
import model.exceptions.DaoException;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

@SuppressWarnings("serial")
public class LoteVegetalcad extends WindowCrud {

	public LoteVegetal loteVegetal = new LoteVegetal();
	private SetorPlantio setorplantio = new SetorPlantio();


	@SuppressWarnings("unchecked")
	public LoteVegetalcad() {
		super("lotevegetalcad.zul");
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

				LoteVegetalDAO lotevegetalDAO = new LoteVegetalDAO();
				loteVegetal = lotevegetalDAO.insertReg(this.loteVegetal);
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro incluído com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.loteVegetal);
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
					&& this.loteVegetal.getIdlotevegetal() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {

				LoteVegetalDAO lotevegetalDAO = new LoteVegetalDAO();
				lotevegetalDAO.updateReg(this.loteVegetal);
				Messagebox.show("Registro Salvo com sucesso!");
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro Salvo com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.loteVegetal);
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
					new LoteVegetalDAO().deleteReg(loteVegetal);
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

		this.loteVegetal = new LoteVegetal();
		this.setorplantio = new SetorPlantio();

		this.getCrdBar().getBotao(0).setDisabled(false);
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.getCrdBar().getBotao(2).setDisabled(true);
		this.vincular();
	}

	@Override
	public void pesquisar() {

		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/lotevegetallis.zul", param, this,
				"retLoteVegetal");
	}

	public void retLoteVegetal(Object ret) {
		if (ret != null) {
			this.loteVegetal = (LoteVegetal) ret;

			this.loteVegetal = (LoteVegetal) ret;
			this.setorplantio = this.loteVegetal.getSetorplantio();
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
		if (this.loteVegetal != null) {
			// this.loteVegetal.setUsualt(this.getLogin());
			// this.loteVegetal.setDatalt(WinUtils.getDataBanco());

			if (this.loteVegetal.getSetorplantio() == null) {
				try {
					Messagebox.show("Selecione o Setor de Plantio.");
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
			Events.postEvent("onClose", this, this.loteVegetal);
		}
	}

	public SetorPlantio getSetorplantio() {
		return setorplantio;
	}

	public void setSetorplantio(SetorPlantio setorplantio) {
		if (setorplantio != null) {
			this.loteVegetal.setSetorplantio(setorplantio);
		}
		this.setorplantio = setorplantio;
	}

	public void pesquisarSetorPlantio() {
		new WinUtils().abrirLis("forms/setorplantiolis.zul", null, this,
				"recebeSetorPlantio");
	}

	public void recebeSetorPlantio(Object obj) {
		if (obj != null) {
			this.setorplantio = (SetorPlantio) obj;
			this.loteVegetal.setSetorplantio(setorplantio);
		}
		this.vincular();
	}

	public void addSetorPlantio() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "lotevegetalcad.zul");
		String url = "forms/setorplantiocad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeSetorPlantio");
	}

}
