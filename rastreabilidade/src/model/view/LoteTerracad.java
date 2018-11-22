package model.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.entity.hibernate.Cidade;
import model.entity.hibernate.LoteTerra;
import model.entity.hibernate.SetorPlantio;
import model.exceptions.LogicException;
import model.logic.impl.LoteTerraLogic;
import model.logic.impl.SetorPlantioLogic;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class LoteTerracad extends WindowCrud {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SetorPlantio setorPlantio = new SetorPlantio();
	public LoteTerra loteTerra = new LoteTerra();
	private Cidade cidade = new Cidade();
	public List<SetorPlantio> listSetorPlantio = new ArrayList<SetorPlantio>();
	public String itensRemovidos = "";


	@SuppressWarnings("unchecked")
	public LoteTerracad() {
		super("loteterracad.zul");
		Map<String, String> map = Executions.getCurrent().getArg();
		if (map.get("pageName") != null) {
			abertoPeloMenu = Boolean.FALSE;
		}
	}

	public void initComponentes() {
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.vincular();
	}

	public void addItem() {
		try {
			if (trataSetorPlantio()) {
				if (listSetorPlantio.contains(setorPlantio)) {

					Messagebox.show("Ja exite Setor de Plantio na Lista!");

				} else {
					if (this.setorPlantio.getCodigosetor() != null) {
						Messagebox
								.show("Entre com o Codigo do Setor de Plantio!");
					} else if (this.setorPlantio.getTamanhoarea() != null) {

						Messagebox
								.show("Entre com o Tamanho da Area do Setor de Plantio!");

					} else {

						this.setorPlantio.setLoteterra(this.loteTerra);
						listSetorPlantio.add(setorPlantio);
						setorPlantio = new SetorPlantio();
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.vincular();
	}

	public void removerItem(SetorPlantio item) {
		try {
			if (Messagebox.show("Deseja realmente excluir a mensagem?",
					"Aviso!!!", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION) == Messagebox.YES) {
				if (item != null) {
					if (item.getIdsetorplantio() != null) {
						if (!itensRemovidos.equals("")) {
							itensRemovidos += "," + item.getIdsetorplantio();
						} else {
							itensRemovidos += "" + item.getIdsetorplantio();
						}
					}
				}
				listSetorPlantio.remove(item);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.vincular();
	}

	@Override
	public void incluir() {
		try {
			if (this.validarForm() && this.trataVO()
					&& !this.getCrdBar().getBotao(0).isDisabled()) {
				this.loteTerra.setSetorPlantioList(listSetorPlantio);
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
					&& this.loteTerra.getIdloteterra() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {
				try {
					new SetorPlantioLogic().delByCodigos(itensRemovidos);
					this.loteTerra.setSetorPlantioList(listSetorPlantio);
					this.loteTerra = new LoteTerraLogic()
							.updateReg(this.loteTerra);
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
		this.loteTerra = new LoteTerra();
		this.cidade = new Cidade();
		this.getCrdBar().getBotao(0).setDisabled(false);
		this.getCrdBar().getBotao(1).setDisabled(true);
		limparSetorPlantio();
	}

	public void limparSetorPlantio() {
		this.setorPlantio = new SetorPlantio();
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
			this.listSetorPlantio = this.loteTerra.getSetorPlantioList();
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

	public boolean trataSetorPlantio() {
		return true;
	}

	@Override
	public void sair() {
		if (this.abertoPeloMenu) {
			this.detach();
		} else {
			Events.postEvent("onClose", this, this.setorPlantio);
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

	public void addLoteTerra() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "setorplantiocad.zul");
		String url = "forms/loteterracad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeLoteTerra");
	}

	public void addCidade() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "loteterracad.zul");
		String url = "forms/cidadecad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeCidade");
	}

	public void buscaPorCodLoteTerra() {
		if (this.setorPlantio.getLoteterra() != null) {
			// recebeLoteTerra(new
			// LoteTerraLogic().getRegByCod(this.setorPlantio.getIdloteterra()));
		}
	}

	public void buscaPorCodCidade() {
		if (this.loteTerra.getCidade() != null) {
			// recebeCidade(new
			// CidadeLogic().getRegByCod(this.loteTerra.getIdcidade()));
		}
	}
}