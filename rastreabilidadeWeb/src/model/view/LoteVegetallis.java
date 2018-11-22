package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.dao.LoteVegetalDAO;
import model.entity.hibernate.LoteVegetal;
import model.entity.hibernate.SetorPlantio;
import model.logic.LogicFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import framework.util.WinUtils;
import framework.util.WindowList;

@SuppressWarnings("serial")
public class LoteVegetallis extends WindowList<LoteVegetal> {

	private SetorPlantio setorplantio = new SetorPlantio();
	public LoteVegetal loteVegetal = new LoteVegetal();
	private List<LoteVegetal> hmSis;

	public LoteVegetal obj;

	public LoteVegetallis() {
		super();
	}

	public void initComponentes() {

		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}

	public void pesquisar() {
		Paging pag = (Paging) this.getComponente("pagLoteVegetal");
		obj = new LoteVegetal();
		try {
			obj = (LoteVegetal) BeanUtils.cloneBean(loteVegetal);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		pag.setTotalSize(LogicFactory.getLoteVegetalLogic().countAllLimit(obj).intValue());
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
	}

	private void redraw(LoteVegetal obj, Integer inicial,
			Integer maximoPermitido) {
		this.hmSis = LogicFactory.getLoteVegetalLogic().getRegByExampleLimit(obj, inicial,
				maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {

		this.loteVegetal = new LoteVegetal();
		this.setorplantio = new SetorPlantio();
		  this.vincular();
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
		if (setorplantio != null) {
			this.setorplantio = (SetorPlantio) obj;
			this.loteVegetal.setSetorplantio(setorplantio);
		}
		this.vincular();
	}

}
