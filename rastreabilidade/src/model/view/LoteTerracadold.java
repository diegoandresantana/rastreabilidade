package model.view;

import java.util.HashMap;
import java.util.Map;

import model.entity.hibernate.Cidade;
import model.entity.hibernate.LoteTerra;
import model.exceptions.LogicException;
import model.logic.impl.LoteTerraLogic;

import org.zkoss.gmaps.Ginfo;
import org.zkoss.gmaps.Gmaps;
import org.zkoss.gmaps.Gmarker;
import org.zkoss.gmaps.Gpolyline;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;
/***
 
public class LoteTerracadold extends WindowCrud {
	public LoteTerra loteTerra = new LoteTerra();
	Boolean abertoPeloMenu = Boolean.TRUE;
	private Cidade cidade = new Cidade();
	public Gmarker gmarker;
	public Gmaps gmap;
	public Ginfo ginfo;
	public Gpolyline gpolyline;

	public LoteTerracadold() {
		super("loteterracad.zul");
		Map<String, String> map = Executions.getCurrent().getArg();
		if (map.get("pageName") != null) {
			abertoPeloMenu = Boolean.FALSE;
		}
	}

	public void initComponentes() {
		gmap = (Gmaps) this.getComponente("map");
		gmarker = new Gmarker();
		ginfo = new Ginfo();
		gpolyline = new Gpolyline();
		ginfo.setParent(gmap);
		gmarker.setParent(gmap);
		gpolyline.setParent(gmap);

		this.getCrdBar().getBotao(1).setDisabled(true);
		this.vincular();
	}

	@Override
	public void incluir() {
		try {
			if (this.validarForm() && this.trataVO()
					&& !this.getCrdBar().getBotao(0).isDisabled()) {
				this.loteTerra = new LoteTerraLogic().insertReg(this.loteTerra);
				if (this.loteTerra.getIdloteterra() > 0) {
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro incluído com sucesso!");
					} else {
						Events.postEvent("onClose", this, this.loteTerra);
					}
					this.getCrdBar().getBotao(0).setDisabled(true);
					this.getCrdBar().getBotao(1).setDisabled(false);
					this.vincular();
				} else {
					Messagebox.show("O registro não pode ser incluído!",
							"Erro:", Messagebox.OK, Messagebox.ERROR);
				}
			}
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
					&& this.loteTerra.getIdloteterra() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {
				try {
					this.loteTerra = new LoteTerraLogic()
							.updateReg(this.loteTerra);
					Messagebox.show("Registro Salvo com sucesso!");
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro Salvo com sucesso!");
					} else {
						Events.postEvent("onClose", this, this.loteTerra);
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
					new LoteTerraLogic().deleteReg(this.loteTerra);
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
		this.loteTerra = new LoteTerra();
		this.cidade = new Cidade();
		this.getCrdBar().getBotao(0).setDisabled(false);
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.vincular();
	}

	@Override
	public void pesquisar() {
		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/loteterralis.zul", param, this,
				"retLoteTerra");
	}

	public void retLoteTerra(Object ret) {
		if (ret != null) {
			this.loteTerra = (LoteTerra) ret;
			this.cidade = this.loteTerra.getCidade();
			this.getCrdBar().getBotao(0).setDisabled(true);
			this.getCrdBar().getBotao(1).setDisabled(false);
			this.vincular();
		}
	}

	@Override
	public void imprimir() {
	}

	public boolean trataVO() {
		if (this.loteTerra != null) {
			if (this.loteTerra.getCidade() == null) {
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
			Events.postEvent("onClose", this, this.loteTerra);
		}
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		if (cidade != null) {
			this.loteTerra.setCidade(cidade);
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
			this.loteTerra.setCidade(cidade);
		}
		this.vincular();
	}

	public void addCidade() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "loteterracad.zul");
		String url = "forms/cidadecad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeCidade");
	}

	public void posicionarMapa() {
		if (loteTerra.getLatitude() == null || loteTerra.getLongitude() == null)
			try {
				Messagebox.show("Coloque a Latitude e a Longitude.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		else {
			gmap.setCenter(loteTerra.getLatitude(), loteTerra.getLongitude());

			gmarker.setContent(loteTerra.getIdloteterra() + " - Endereço:"
					+ loteTerra.getEndereco() + "\nArea do Lote:"
					+ loteTerra.getAreatotal());
			gmarker.setDraggingEnabled(false);

			gmarker.setOpen(true);
			gmarker.setTooltip(loteTerra.getIdloteterra() + " - Endereço:"
					+ loteTerra.getEndereco() + "\nArea do Lote:"
					+ loteTerra.getAreatotal());
			gmarker
					.setAnchor(loteTerra.getLatitude(), loteTerra
							.getLongitude());
			gmarker.setId("map" + loteTerra.getIdloteterra());
			gmap.setZoom(16);
			ginfo.setAnchor(loteTerra.getLatitude(), loteTerra.getLongitude());
			ginfo.setContent(loteTerra.getIdloteterra() + " - Endereço:"
					+ loteTerra.getEndereco() + "\nArea do Lote:"
					+ loteTerra.getAreatotal());
			gmap.openInfo(ginfo);
			gmap.setEnableGoogleBar(true);
			gmarker.setMinzoom(0);
			gmarker.setMaxzoom(19);
			gpolyline.setPoints(loteTerra.getLatitude() + ","
					+ loteTerra.getLongitude() + ",3," + (-20.43632270367392)
					+ "," + (-54.5806360244751) + ",3," + (-20.44032397324736)
					+ "," + (-54.582180976867676) + ",3,"
					+ (loteTerra.getLatitude()) + ","
					+ (loteTerra.getLongitude()) + ",3");
			// gmap.setLat(loteTerra.getLatitude());
			// gmap.setLng(loteTerra.getLongitude());
			// gmap.panTo(loteTerra.getLatitude(), loteTerra.getLongitude());

			this.vincular();
		}
	}
}

 */