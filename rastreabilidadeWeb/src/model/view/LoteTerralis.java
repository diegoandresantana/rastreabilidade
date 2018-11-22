package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.dao.LoteTerraDAO;
import model.entity.hibernate.Cidade;
import model.entity.hibernate.LoteTerra;
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
public class LoteTerralis extends WindowList<LoteTerra> {

	private Cidade cidade = new Cidade();
	public LoteTerra loteTerra = new LoteTerra();
	private List<LoteTerra> hmSis;

	public LoteTerra obj;

	public LoteTerralis() {
		super();
	}

	public void initComponentes() {

		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}

	public void pesquisar() {
		Paging pag = (Paging) this.getComponente("pagLoteTerra");
		obj = new LoteTerra();
		try {
			obj = (LoteTerra) BeanUtils.cloneBean(loteTerra);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		pag.setTotalSize(LogicFactory.getLoteTerraLogic().countAllLimit(obj).intValue());
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

	private void redraw(LoteTerra obj, Integer inicial, Integer maximoPermitido) {
		this.hmSis =LogicFactory.getLoteTerraLogic().getRegByExampleLimit(obj, inicial,
				maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {

		this.loteTerra = new LoteTerra();
		this.cidade = new Cidade();
		  this.vincular();
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
		if (cidade != null) {
			this.cidade = (Cidade) obj;
			this.loteTerra.setCidade(cidade);
		}
		this.vincular();
	}

}
