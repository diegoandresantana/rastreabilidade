package model.view;

import java.util.HashMap;
import java.util.Map;

import model.dao.impl.hibernate.HistoricoDAO;
import model.dao.impl.hibernate.TipoHistoricoDAO;
import model.entity.hibernate.Historico;
import model.entity.hibernate.LoteVegetal;
import model.entity.hibernate.TipoHistorico;
import model.exceptions.DaoException;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class Historicocad extends WindowCrud {

	private static final long serialVersionUID = -7645289319811043245L;
	public Historico historico = new Historico();
	public ListModelList lmTipoHistorico;
	private LoteVegetal lotevegetal = new LoteVegetal();
	private TipoHistorico tipohistorico = new TipoHistorico();

	@SuppressWarnings("unchecked")
	public Historicocad() {
		super("historicocad.zul");
		Map<String, String> map = Executions.getCurrent().getArg();
		if (map.get("pageName") != null) {
			abertoPeloMenu = Boolean.FALSE;
		}
	}

	public void initComponentes() {

		lmTipoHistorico = new ListModelList(new TipoHistoricoDAO()
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

				HistoricoDAO historicoDAO = new HistoricoDAO();
				historico = historicoDAO.insertReg(this.historico);
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro incluído com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.historico);
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
					&& this.historico.getIdhistorico() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {

				HistoricoDAO historicoDAO = new HistoricoDAO();
				historicoDAO.updateReg(this.historico);
				Messagebox.show("Registro Salvo com sucesso!");
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro Salvo com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.historico);
				}
				this.vincular();
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
					new HistoricoDAO().deleteReg(historico);
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

		this.historico = new Historico();
		this.lotevegetal = new LoteVegetal();
		this.tipohistorico = new TipoHistorico();

		this.getCrdBar().getBotao(0).setDisabled(false);
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.getCrdBar().getBotao(2).setDisabled(true);
		this.vincular();
	}

	@Override
	public void pesquisar() {

		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/historicolis.zul", param, this,
				"retHistorico");
	}

	public void retHistorico(Object ret) {
		if (ret != null) {
			this.historico = (Historico) ret;

			this.historico = (Historico) ret;
			this.lotevegetal = this.historico.getLotevegetal();
			this.tipohistorico = this.historico.getTipohistorico();
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
		if (this.historico != null) {
			// this.historico.setUsualt(this.getLogin());
			// this.historico.setDatalt(WinUtils.getDataBanco());

			if (this.historico.getLotevegetal() == null) {
				try {
					Messagebox.show("Selecione o Lote de Vegetal.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return false;
			}
			if (this.historico.getTipohistorico() == null) {
				try {
					Messagebox.show("Selecione o Tipo de Histórico.");
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
			Events.postEvent("onClose", this, this.historico);
		}
	}

	public LoteVegetal getLotevegetal() {
		return lotevegetal;
	}

	public TipoHistorico getTipohistorico() {
		return tipohistorico;
	}

	public void setLotevegetal(LoteVegetal lotevegetal) {
		if (lotevegetal != null) {
			this.historico.setLotevegetal(lotevegetal);
		}
		this.lotevegetal = lotevegetal;
	}

	public void setTipohistorico(TipoHistorico tipohistorico) {
		if (tipohistorico != null) {
			this.historico.setTipohistorico(tipohistorico);
		}
		this.tipohistorico = tipohistorico;
	}

	public void pesquisarLoteVegetal() {
		new WinUtils().abrirLis("forms/lotevegetallis.zul", null, this,
				"recebeLoteVegetal");
	}

	public void recebeLoteVegetal(Object obj) {
		if (obj != null) {
			this.lotevegetal = (LoteVegetal) obj;
			this.historico.setLotevegetal(lotevegetal);
		}
		this.vincular();
	}

	public void recebeTipoHistorico(Object obj) {
		lmTipoHistorico = new ListModelList(new TipoHistoricoDAO()
				.getRegByExample(null));
		if (obj != null) {
			this.tipohistorico = (TipoHistorico) obj;
			this.historico.setTipohistorico(tipohistorico);
		}
		this.vincular();
	}

	public void addLoteVegetal() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "historicocad.zul");
		String url = "forms/lotevegetalcad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeLoteVegetal");
	}

	public void addTipoHistorico() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "historicocad.zul");
		String url = "forms/tipohistoricocad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeTipoHistorico");
	}

}
