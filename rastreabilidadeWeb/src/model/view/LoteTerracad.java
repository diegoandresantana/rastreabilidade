package model.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.entity.hibernate.Cidade;
import model.entity.hibernate.LoteTerra;
import model.entity.hibernate.SetorPlantio;
import model.exceptions.LogicException;
import model.logic.LogicFactory;
import model.logic.LoteTerraLogic;
import model.logic.SetorPlantioLogic;

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
		controleBotoesDisable(false,true, true);
	
		this.vincular();
	}
	

	public void addItem() {
		try {
			if (trataSetorPlantio()) {
				if (listSetorPlantio.contains(setorPlantio)) {

					Messagebox.show("Ja exite Setor de Plantio na Lista!");

				} else {
					

						this.setorPlantio.setLoteterra(this.loteTerra);
						listSetorPlantio.add(setorPlantio);
						setorPlantio = new SetorPlantio();
					
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
				this.loteTerra = LogicFactory.getLoteTerraLogic().insertReg(this.loteTerra);
				if (this.loteTerra.getIdloteterra() > 0) {
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro incluído com sucesso!");
					} else {
						Events.postEvent("onClose", this, this.loteTerra);
					}
					limpar();
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
					if(!itensRemovidos.equals("")){
					  LogicFactory.getSetorPlantioLogic().delByCodigos(itensRemovidos);
					}
					this.loteTerra.setSetorPlantioList(listSetorPlantio);
					this.loteTerra =LogicFactory.getLoteTerraLogic().updateReg(this.loteTerra);
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro Salvo com sucesso!");
					} else {
						Events.postEvent("onClose", this, this.loteTerra);
					}
					limpar();
				} catch (LogicException e) {
					Messagebox.show(e.getMessage(), "Erro:", Messagebox.OK,
							Messagebox.ERROR);
					e.printStackTrace();
				}
				
			} else {
				Messagebox.show("O registro não pode ser salvo!", "Erro:",
						Messagebox.OK, Messagebox.ERROR);
			}
		}catch(WrongValueException e){			
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
					LogicFactory.getLoteTerraLogic().deleteReg(this.loteTerra);
					limpar();
				} catch (LogicException e) {
					Messagebox.show(e.getMessage());
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
		this.setorPlantio = new SetorPlantio();
		listSetorPlantio =new ArrayList<SetorPlantio>();
		itensRemovidos="";
		controleBotoesDisable(false,true, true);
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

	@SuppressWarnings("static-access")
	public void retLoteTerra(Object ret) {
		if (ret != null) {
			this.loteTerra = (LoteTerra) ret;
			this.cidade = this.loteTerra.getCidade();
			SetorPlantio sP= new SetorPlantio();
			sP.setLoteterra(loteTerra);
			this.listSetorPlantio = new LogicFactory().getSetorPlantioLogic().getRegByExample(sP);
			controleBotoesDisable(true,false,false);		
			this.vincular();
		}
	}

	@Override
	public void imprimir() {
	}

	public boolean trataVO() {
		if (this.loteTerra != null) {
			if (this.loteTerra.getCidade() == null) {
			     throw new WrongValueException(getComponente("idcidade"),"Selecione o Cidade!");			
			}
			return true;
		}
		return false;
	}

	public boolean trataSetorPlantio() {
		if (this.setorPlantio.getCodigosetor() == null) {
			throw new WrongValueException(getComponente("codigosetor"),"Entre com o Codigo do Setor de Plantio!");
		} 
		if (this.setorPlantio.getTamanhoarea() == null) {
			throw new WrongValueException(getComponente("tamanhoarea"),"Entre com o Tamanho da Area do Setor de Plantio!");
		}
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

}