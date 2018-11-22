package model.view;

import java.util.HashMap;
import java.util.Map;

import model.entity.hibernate.LoteTerra;
import model.entity.hibernate.SetorPlantio;
import model.exceptions.LogicException;
import model.logic.impl.SetorPlantioLogic;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class SetorPlantiocad extends WindowCrud {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SetorPlantio setorPlantio = new SetorPlantio();
	
	private LoteTerra loteterra = new LoteTerra();

	@SuppressWarnings("unchecked")
	public SetorPlantiocad() {
		super("setorplantiocad.zul");
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
				this.setorPlantio = new SetorPlantioLogic()
						.insertReg(this.setorPlantio);
				if (this.setorPlantio.getIdsetorplantio() > 0) {
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro incluído com sucesso!");
					} else {
						Events.postEvent("onClose", this, this.setorPlantio);
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
					&& this.setorPlantio.getIdsetorplantio() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {
				try {
					this.setorPlantio = new SetorPlantioLogic()
							.updateReg(this.setorPlantio);
					Messagebox.show("Registro Salvo com sucesso!");
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro Salvo com sucesso!");
					} else {
						Events.postEvent("onClose", this, this.setorPlantio);
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
					new SetorPlantioLogic().deleteReg(this.setorPlantio);
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
		this.setorPlantio = new SetorPlantio();
		this.loteterra = new LoteTerra();
		this.getCrdBar().getBotao(0).setDisabled(false);
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.vincular();
	}

	@Override
	public void pesquisar() {
		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/setorplantiolis.zul", param, this,
				"retSetorPlantio");
	}

	public void retSetorPlantio(Object ret) {
		if (ret != null) {
			this.setorPlantio = (SetorPlantio) ret;
			this.loteterra = this.setorPlantio.getLoteterra();
			this.getCrdBar().getBotao(0).setDisabled(true);
			this.getCrdBar().getBotao(1).setDisabled(false);
			this.vincular();
		}
	}

	@Override
	public void imprimir() {
	}

	public boolean trataVO() {
		if (this.setorPlantio != null) {
			if (this.setorPlantio.getLoteterra() == null) {
				try {
					Messagebox.show("Selecione o Lote de Terra.");
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
			Events.postEvent("onClose", this, this.setorPlantio);
		}
	}

	public LoteTerra getLoteterra() {
		return loteterra;
	}

	public void setLoteterra(LoteTerra loteterra) {
		if (loteterra != null) {
			this.setorPlantio.setLoteterra(loteterra);
		}
		this.loteterra = loteterra;
	}

	public void pesquisarLoteTerra() {
		new WinUtils().abrirLis("/forms/loteterralis.zul", null, this,
				"recebeLoteTerra");
	}

	public void recebeLoteTerra(Object obj) {
		if (obj != null) {
			this.loteterra = (LoteTerra) obj;
			this.setorPlantio.setLoteterra(loteterra);
		}
		this.vincular();
	}

	public void addLoteTerra() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "setorplantiocad.zul");
		String url = "/forms/loteterracad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeLoteTerra");
	}
}