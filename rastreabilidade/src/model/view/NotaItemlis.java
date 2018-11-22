package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.dao.impl.hibernate.NotaItemDAO;
import model.entity.hibernate.FornecedorProduto;
import model.entity.hibernate.NotaFiscal;
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
public class NotaItemlis extends WindowList<NotaItem> {

	private FornecedorProduto fornecedorproduto = new FornecedorProduto();
	private NotaFiscal notafiscal = new NotaFiscal();
	public NotaItem notaItem = new NotaItem();
	private List<NotaItem> hmSis;
	
	public NotaItem obj;

	public NotaItemlis() {
		super();
	}

	public void initComponentes() {

		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}

	public void pesquisar() {
		Paging pag = (Paging) this.getComponente("pagNotaItem");
		obj = new NotaItem();
		try {
			obj = (NotaItem) BeanUtils.cloneBean(notaItem);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		pag.setTotalSize(new NotaItemDAO().countAllLimit(obj).intValue());
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

	private void redraw(NotaItem obj, Integer inicial, Integer maximoPermitido) {
		this.hmSis = new NotaItemDAO().getRegByExampleLimit(obj, inicial,
				maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {

		this.notaItem = new NotaItem();
		this.fornecedorproduto = new FornecedorProduto();
		this.notafiscal = new NotaFiscal();

	}

	public FornecedorProduto getFornecedorproduto() {
		return fornecedorproduto;
	}

	public NotaFiscal getNotafiscal() {
		return notafiscal;
	}

	public void setFornecedorproduto(FornecedorProduto fornecedorproduto) {
		if (fornecedorproduto != null) {
			this.notaItem.setFornecedorproduto(fornecedorproduto);
		}
		this.fornecedorproduto = fornecedorproduto;
	}

	public void setNotafiscal(NotaFiscal notafiscal) {
		if (notafiscal != null) {
			this.notaItem.setNotafiscal(notafiscal);
		}
		this.notafiscal = notafiscal;
	}

	public void pesquisarFornecedorProduto() {
		new WinUtils().abrirLis("forms/fornecedorprodutolis.zul", null, this,
				"recebeFornecedorProduto");
	}

	public void recebeFornecedorProduto(Object obj) {
		if (fornecedorproduto != null) {
			this.fornecedorproduto = (FornecedorProduto) obj;
			this.notaItem.setFornecedorproduto(fornecedorproduto);
		}
		this.vincular();
	}

	public void pesquisarNotaFiscal() {
		new WinUtils().abrirLis("forms/notafiscallis.zul", null, this,
				"recebeNotaFiscal");
	}

	public void recebeNotaFiscal(Object obj) {
		if (notafiscal != null) {
			this.notafiscal = (NotaFiscal) obj;
			this.notaItem.setNotafiscal(notafiscal);
		}
		this.vincular();
	}

}
