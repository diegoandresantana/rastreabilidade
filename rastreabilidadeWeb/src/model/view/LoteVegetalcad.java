package model.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.entity.hibernate.Historico;
import model.entity.hibernate.LoteVegetal;
import model.entity.hibernate.Produto;
import model.entity.hibernate.SetorPlantio;
import model.entity.hibernate.TipoHistorico;
import model.exceptions.LogicException;
import model.logic.LogicFactory;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

@SuppressWarnings("serial")
public class LoteVegetalcad extends WindowCrud {

	public LoteVegetal loteVegetal = new LoteVegetal();
	
	public ListModelList lmTipoHistorico;
	public ListModelList lmProduto;

	public List<Historico> listHistorico = new ArrayList<Historico>();
	public String itensRemovidos = "";
	public Historico historico =new Historico();
	private Produto produto=new Produto();
	
	private SetorPlantio setorplantio ;
	private TipoHistorico tipohistorico ;
	@SuppressWarnings("unchecked")
	public LoteVegetalcad() {
		super("lotevegetalcad.zul");
		Map<String, String> map = Executions.getCurrent().getArg();
		if (map.get("pageName") != null) {
			abertoPeloMenu = Boolean.FALSE;
		}
	}

	public void initComponentes() {

		lmTipoHistorico = new ListModelList(LogicFactory.getTipoHistoricoLogic().getRegByExample(null));
		lmProduto= new ListModelList(LogicFactory.getProdutoLogic().getRegByExample(null));
		controleBotoesDisable(false, true, true);
		
		this.vincular();
	}

	@Override
	public void incluir() {
		try {
			if (this.validarForm() && this.trataVO()
					&& !this.getCrdBar().getBotao(0).isDisabled()) {				
				this.loteVegetal.setHistoricoList(listHistorico);
				loteVegetal = LogicFactory.getLoteVegetalLogic().insertReg(this.loteVegetal);
				if (this.loteVegetal.getIdlotevegetal() > 0) {
					if (this.abertoPeloMenu) {
						Messagebox.show("Registro incluído com sucesso!");
					} else {
						Events.postEvent("onClose", this, this.loteVegetal);
					}
					limpar();				
			}
		 }
		} catch (LogicException e) {
			try {
				Messagebox.show(e.getMessage());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}catch(WrongValueException e){			
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
		
					if(!itensRemovidos.equals("")){
					  LogicFactory.getHistoricoLogic().delByCodigos(itensRemovidos);
					}
					this.loteVegetal.setHistoricoList(listHistorico);
					this.loteVegetal=LogicFactory.getLoteVegetalLogic().updateReg(this.loteVegetal);
			
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro Salvo com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.loteVegetal);
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
			
		} catch(WrongValueException e){			
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	public void limparHistorico() {
		this.historico = new Historico();
		listHistorico = new ArrayList<Historico>();
		
		this.tipohistorico =null;
		this.produto=null;
		
		this.vincular();
	}

	@Override
	public void apagar() {

		try {
			if (Messagebox.show("Deseja realmente excluir ?", "Aviso!!!",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {

				try {
					LogicFactory.getLoteVegetalLogic().deleteReg(loteVegetal);
					Messagebox.show("Registro excluído com sucesso!");
					this.limpar();
				}  catch (LogicException e) {
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

		this.loteVegetal = new LoteVegetal();
		this.setorplantio = null;
		this.produto=null;
		this.tipohistorico=null;
		this.historico=new Historico();
		itensRemovidos="";
		listHistorico = new ArrayList<Historico>();
		controleBotoesDisable(false, true, true);
		
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
			this.setorplantio = this.loteVegetal.getSetorplantio();
			Historico h=new Historico();
			h.setLotevegetal(loteVegetal);
			listHistorico=LogicFactory.getHistoricoLogic().getRegByExample(h);
			controleBotoesDisable(true,false,false);
			
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
				 throw new WrongValueException(getComponente("idsetorplantio"),"Selecione o Setor de Plantio!");			
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
		}else{
			this.loteVegetal.setSetorplantio(null);
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
	public void addItem() {
		try {
			if (trataHistorico()) {				
					if (this.historico.getDescricao() == null) {
						Messagebox.show("Entre com a Descrição!");
					} else if (this.historico.getData() == null) {
						Messagebox.show("Entre com a Data!");
					} else if (this.historico.getTipohistorico() == null) {
						Messagebox.show("Selecione o Tipo de Histórico!");
					}else {
						this.historico.setLotevegetal(loteVegetal);
						listHistorico.add(historico);
						historico = new Historico();
						tipohistorico=null;
						produto=null;
					}
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.vincular();
	}

	private boolean trataHistorico() {		
		if (this.historico.getDescricao() == null) {
			throw new WrongValueException(getComponente("dadonumerico"),"Entre com a Descrição!");			
		} 
		if (this.historico.getData() == null) {
			throw new WrongValueException(getComponente("data"),"Entre com a Data!");
		}		
		if (this.historico.getTipohistorico() == null) {
			throw new WrongValueException(getComponente("tipohistorico"),"Selecione o Tipo de Histórico!");			
		}
		return true;
	}

	public void removerItem(Historico item) {
		try {
			if (Messagebox.show("Deseja realmente excluir a mensagem?",
					"Aviso!!!", Messagebox.YES | Messagebox.NO,
					Messagebox.QUESTION) == Messagebox.YES) {
				if (item != null) {
					if (item.getIdhistorico() != null) {
						if (!itensRemovidos.equals("")) {
							itensRemovidos += "," + item.getIdhistorico();
						} else {
							itensRemovidos += "" + item.getIdhistorico();
						}
					}
				}
				listHistorico.remove(item);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.vincular();
	}
	public TipoHistorico getTipohistorico() {
		return tipohistorico;
	}
	public void setTipohistorico(TipoHistorico tipohistorico) {		
		
		this.tipohistorico = tipohistorico;
		this.historico.setTipohistorico(tipohistorico);
	}
	public void addTipoHistorico() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "historicocad.zul");
		String url = "forms/tipohistoricocad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeTipoHistorico");
	}

	public void recebeTipoHistorico(Object obj) {
		lmTipoHistorico = new ListModelList(LogicFactory.getTipoHistoricoLogic()
				.getRegByExample(null));
		if (obj != null) {
			this.tipohistorico = (TipoHistorico) obj;
			this.historico.setTipohistorico(tipohistorico);
		}
		this.vincular();
	}

	public void setProduto(Produto produto) {
		this.produto = produto;		
		historico.setProduto(produto);
		
	}

	public Produto getProduto() {
		return produto;
	}
	public void addProduto() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "lotevegetalcad.zul");
		String url = "forms/produtocad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeProduto");
	}
	public void recebeProduto(Object obj) {
		if (obj != null) {
			this.produto = (Produto) obj;
			this.historico.setProduto(produto);
		}
		this.vincular();
	}
	public void limparProduto(){
		produto=null;
		historico.setProduto(null);
		this.vincular();
		
	}
	
}
