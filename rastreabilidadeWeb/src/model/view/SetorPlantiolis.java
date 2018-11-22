package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.dao.SetorPlantioDAO;
import model.entity.hibernate.LoteTerra;
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
public class SetorPlantiolis extends WindowList<SetorPlantio> {

	private LoteTerra loteterra = new LoteTerra();
	public SetorPlantio setorPlantio = new SetorPlantio();
	private List<SetorPlantio> hmSis;

	public SetorPlantio obj;

	public SetorPlantiolis() {
		super();
	}

	public void initComponentes() {

		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}

	public void pesquisar() {
		Paging pag = (Paging) this.getComponente("pagSetorPlantio");
		obj = new SetorPlantio();
		try {
			obj = (SetorPlantio) BeanUtils.cloneBean(setorPlantio);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		pag.setTotalSize(LogicFactory.getSetorPlantioLogic().countAllLimit(obj).intValue());
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

	private void redraw(SetorPlantio obj, Integer inicial,
			Integer maximoPermitido) {
		this.hmSis = LogicFactory.getSetorPlantioLogic().getRegByExampleLimit(obj, inicial,
				maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {

		this.setorPlantio = new SetorPlantio();
		this.loteterra = new LoteTerra();
		  this.vincular();
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
		new WinUtils().abrirLis("forms/loteterralis.zul", null, this,
				"recebeLoteTerra");
	}

	public void recebeLoteTerra(Object obj) {
		if (loteterra != null) {
			this.loteterra = (LoteTerra) obj;
			this.setorPlantio.setLoteterra(loteterra);
		}
		this.vincular();
	}

}
