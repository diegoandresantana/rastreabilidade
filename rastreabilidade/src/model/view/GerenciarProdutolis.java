package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.dao.impl.hibernate.GerenciarProdutoDAO;
import model.entity.hibernate.GerenciarProduto;
import model.entity.hibernate.LoteVegetal;
import model.entity.hibernate.NotaItem;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import framework.util.WinUtils;
import framework.util.WindowList;

@SuppressWarnings("serial")
public class GerenciarProdutolis extends WindowList<GerenciarProduto> {

	private LoteVegetal lotevegetal = new LoteVegetal();
	private NotaItem notaitem = new NotaItem();
	public GerenciarProduto gerenciarProduto = new GerenciarProduto();
	private List<GerenciarProduto> hmSis;

	public GerenciarProduto obj;

	public GerenciarProdutolis() {
		super();
	}

	public void initComponentes() {

		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}

	public void pesquisar() {
		Paging pag = (Paging) this.getComponente("pagGerenciarProduto");
		obj = new GerenciarProduto();
		try {
			obj = (GerenciarProduto) BeanUtils.cloneBean(gerenciarProduto);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		pag.setTotalSize(new GerenciarProdutoDAO().countAllLimit(obj)
				.intValue());
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

	private void redraw(GerenciarProduto obj, Integer inicial,
			Integer maximoPermitido) {
		this.hmSis = new GerenciarProdutoDAO().getRegByExampleLimit(obj,
				inicial, maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {

		this.gerenciarProduto = new GerenciarProduto();
		this.lotevegetal = new LoteVegetal();
		this.notaitem = new NotaItem();

	}

	public LoteVegetal getLotevegetal() {
		return lotevegetal;
	}

	public NotaItem getNotaitem() {
		return notaitem;
	}

	public void setLotevegetal(LoteVegetal lotevegetal) {
		if (lotevegetal != null) {
			this.gerenciarProduto.setLotevegetal(lotevegetal);
		}
		this.lotevegetal = lotevegetal;
	}

	public void setNotaitem(NotaItem notaitem) {
		if (notaitem != null) {
			this.gerenciarProduto.setNotaitem(notaitem);
		}
		this.notaitem = notaitem;
	}

	public void pesquisarLoteVegetal() {
		new WinUtils().abrirLis("forms/lotevegetallis.zul", null, this,
				"recebeLoteVegetal");
	}

	public void recebeLoteVegetal(Object obj) {
		if (lotevegetal != null) {
			this.lotevegetal = (LoteVegetal) obj;
			this.gerenciarProduto.setLotevegetal(lotevegetal);
		}
		this.vincular();
	}

	public void pesquisarNotaItem() {
		new WinUtils().abrirLis("forms/notaitemlis.zul", null, this,
				"recebeNotaItem");
	}

	public void recebeNotaItem(Object obj) {
		if (notaitem != null) {
			this.notaitem = (NotaItem) obj;
			this.gerenciarProduto.setNotaitem(notaitem);
		}
		this.vincular();
	}

}
