package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.dao.impl.hibernate.CidadeDAO;
import model.dao.impl.hibernate.UfDAO;
import model.entity.hibernate.Cidade;
import model.entity.hibernate.Uf;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import framework.util.WindowList;

@SuppressWarnings("serial")
public class Cidadelis extends WindowList<Cidade> {

	public ListModelList lmUf;
	private Uf uf = new Uf();
	public Cidade cidade = new Cidade();
	private List<Cidade> hmSis;
	
	public Cidade obj;

	public Cidadelis() {
		super();
	}

	public void initComponentes() {

		lmUf = new ListModelList(new UfDAO().getRegByExample(null));
		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}

	public void pesquisar() {
		Paging pag = (Paging) this.getComponente("pagCidade");
		obj = new Cidade();
		try {
			obj = (Cidade) BeanUtils.cloneBean(cidade);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		pag.setTotalSize(new CidadeDAO().countAllLimit(obj).intValue());
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

	private void redraw(Cidade obj, Integer inicial, Integer maximoPermitido) {
		this.hmSis = new CidadeDAO().getRegByExampleLimit(obj, inicial,
				maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {

		this.cidade = new Cidade();
		this.uf = new Uf();

	}

	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		if (uf != null) {
			this.cidade.setUf(uf);
		}
		this.uf = uf;
	}

}
