package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.dao.TipoProdutoDAO;
import model.entity.hibernate.TipoProduto;
import model.logic.LogicFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import framework.util.WindowList;

@SuppressWarnings("serial")
public class TipoProdutolis extends WindowList<TipoProduto> {

	public TipoProduto tipoProduto = new TipoProduto();
	private List<TipoProduto> hmSis;
	public TipoProduto obj;

	public TipoProdutolis() {
		super();
	}

	public void initComponentes() {

		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}

	public void pesquisar() {
		Paging pag = (Paging) this.getComponente("pagTipoProduto");
		obj = new TipoProduto();
		try {
			obj = (TipoProduto) BeanUtils.cloneBean(tipoProduto);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		pag.setTotalSize(LogicFactory.getTipoProdutoLogic().countAllLimit(obj).intValue());
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

	private void redraw(TipoProduto obj, Integer inicial,
			Integer maximoPermitido) {
		this.hmSis = LogicFactory.getTipoProdutoLogic().getRegByExampleLimit(obj, inicial,
				maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {

		this.tipoProduto = new TipoProduto();
		  this.vincular();
	}

}
