package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.entity.hibernate.TipoHistorico;
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
public class TipoHistoricolis extends WindowList<TipoHistorico> {

	public ListModelList lmUnidadeMedida;
	private UnidadeMedida unidademedida = new UnidadeMedida();
	public TipoHistorico tipoHistorico = new TipoHistorico();
	private List<TipoHistorico> hmSis;

	public TipoHistorico obj;

	public TipoHistoricolis() {
		super();
	}

	public void initComponentes() {

		lmUnidadeMedida = new ListModelList(LogicFactory.getUnidadeMedidaLogic()
				.getRegByExample(null));
		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}

	public void pesquisar() {
		Paging pag = (Paging) this.getComponente("pagTipoHistorico");
		obj = new TipoHistorico();
		try {
			obj = (TipoHistorico) BeanUtils.cloneBean(tipoHistorico);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		pag.setTotalSize(LogicFactory.getTipoHistoricoLogic().countAllLimit(obj).intValue());
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

	private void redraw(TipoHistorico obj, Integer inicial,
			Integer maximoPermitido) {
		this.hmSis = LogicFactory.getTipoHistoricoLogic().getRegByExampleLimit(obj, inicial,
				maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {

		this.tipoHistorico = new TipoHistorico();
		this.unidademedida = new UnidadeMedida();
		  this.vincular();
	}

	public UnidadeMedida getUnidademedida() {
		return unidademedida;
	}

	public void setUnidademedida(UnidadeMedida unidademedida) {
		if (unidademedida != null) {
			this.tipoHistorico.setUnidademedida(unidademedida);
		}
		this.unidademedida = unidademedida;
	}

}
