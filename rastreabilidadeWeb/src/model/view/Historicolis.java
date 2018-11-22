package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.dao.HistoricoDAO;
import model.dao.TipoHistoricoDAO;
import model.entity.hibernate.Historico;
import model.entity.hibernate.LoteVegetal;
import model.entity.hibernate.TipoHistorico;
import model.exceptions.LogicException;
import model.logic.LogicFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import framework.util.WinUtils;
import framework.util.WindowList;

@SuppressWarnings("serial")
public class Historicolis extends WindowList<Historico> {

	private LoteVegetal lotevegetal = new LoteVegetal();
	public Historico historico = new Historico();
	private List<Historico> hmSis;
	public Long idlotevegetal;
	public Historico obj;
	public Historicolis() {
		super();
	}
	public void initComponentes() {		
		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		this.getCrdBar().getBotao(5).setVisible(true);
		this.getCrdBar().getBotao(6).setVisible(false);
		pesquisar();

	}

	public void pesquisar() {
		if(pesquisarLoteVegetal()){
			Paging pag = (Paging) this.getComponente("pagHistorico");
			obj = new Historico();
			try {
				obj = (Historico) BeanUtils.cloneBean(historico);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			pag.setTotalSize(LogicFactory.getHistoricoLogic().countAllLimit(obj).intValue());
			final int PAGE_SIZE = pag.getPageSize();
			redraw(obj, 0, PAGE_SIZE);
			pag.setActivePage(0);
			pag.setDetailed(true);
			pag.addEventListener("onPaging", new EventListener() {
				public void onEvent(Event event) {
					PagingEvent pe = (PagingEvent) event;
					int pgno = pe.getActivePage();
					int ofs = pgno * PAGE_SIZE;
					redraw(obj, ofs, PAGE_SIZE);
					vincular();
				}
			});
			vincular();
		}else{
			limpar();
		}
		
	}

	private void redraw(Historico obj, Integer inicial, Integer maximoPermitido) {
		this.hmSis = LogicFactory.getHistoricoLogic().getRegByExampleLimit(obj, inicial,
				maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {
		Paging pag = (Paging) this.getComponente("pagHistorico");
		pag.setActivePage(0);
		pag.setTotalSize(0);
		setListmodel(new ListModelList());
		this.historico = new Historico();
		this.lotevegetal = new LoteVegetal();
		
		this.vincular();
	}

	public LoteVegetal getLotevegetal() {
		return lotevegetal;
	}

	
	public void setLotevegetal(LoteVegetal lotevegetal) {
		if (lotevegetal != null) {
			this.historico.setLotevegetal(lotevegetal);
		}
		this.lotevegetal = lotevegetal;
	}

	

	public boolean pesquisarLoteVegetal() {
		 if(idlotevegetal!=null){
			 try {
				lotevegetal=LogicFactory.getLoteVegetalLogic().findById(idlotevegetal);
				this.historico.setLotevegetal(lotevegetal);
				return true;
			} catch (LogicException e) {				
				try {
					Messagebox.show(e.getMessage());
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					this.historico.setLotevegetal(null);
					limpar();
				}
				return false;
			}
		 }
		return false;
	}
   public void imprimir(){
	   if(lotevegetal.getIdlotevegetal()!=null){
	   Map<String, String> url = new HashMap<String, String>();
		String s = "/../birt/frameset?__report=historico.rptdesign&__format=pdf&idlotevegetal="+lotevegetal.getIdlotevegetal();
	
	
		url.put("url", s );
		final Window cmp = (Window) Executions.createComponents(
				"/forms/birt.zul", null, url);
		cmp.setMaximized(true);
		cmp.setSizable(true);
		cmp.setMaximizable(true);
		cmp.setClosable(true);
		cmp.doOverlapped();
	   }else{
		   try {
			Messagebox.show("Não é possivel abrir o relatorio, é necessario ter buscado um Lote de Vegetal para que possa abrir o relatório!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
   }

}
