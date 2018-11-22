package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.dao.UnidadeMedidaDAO;
import model.entity.hibernate.UnidadeMedida;
import model.logic.LogicFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;

import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import framework.util.WindowList;

@SuppressWarnings("serial")
public class UnidadeMedidalis extends WindowList<UnidadeMedida> {

	public UnidadeMedida unidadeMedida = new UnidadeMedida();
	private List<UnidadeMedida> hmSis;
	
	public UnidadeMedida obj;

	public UnidadeMedidalis() {
		super();
	}

	public void initComponentes() {

		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}

	public void pesquisar() {
	
		Paging pag = (Paging) this.getComponente("pagUnidadeMedida");

		obj = new UnidadeMedida();
		try {
			obj = (UnidadeMedida) BeanUtils.cloneBean(unidadeMedida);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		pag.setTotalSize(LogicFactory.getUnidadeMedidaLogic().countAllLimit(obj).intValue());
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

	private void redraw(UnidadeMedida obj, Integer inicial,
			Integer maximoPermitido) {
		this.hmSis = LogicFactory.getUnidadeMedidaLogic().getRegByExampleLimit(obj, inicial,
				maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {

		this.unidadeMedida = new UnidadeMedida();
		  this.vincular();
	}

}
