package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.dao.impl.hibernate.NotaFiscalDAO;
import model.entity.hibernate.Fornecedor;
import model.entity.hibernate.NotaFiscal;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import framework.util.WinUtils;
import framework.util.WindowList;

@SuppressWarnings("serial")
public class NotaFiscallis extends WindowList<NotaFiscal> {

	private Fornecedor fornecedor = new Fornecedor();
	public NotaFiscal notaFiscal = new NotaFiscal();
	private List<NotaFiscal> hmSis;

	public NotaFiscal obj;

	public NotaFiscallis() {
		super();
	}

	public void initComponentes() {

		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}

	public void pesquisar() {
		Paging pag = (Paging) this.getComponente("pagNotaFiscal");
		obj = new NotaFiscal();
		try {
			obj = (NotaFiscal) BeanUtils.cloneBean(notaFiscal);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		pag.setTotalSize(new NotaFiscalDAO().countAllLimit(obj).intValue());
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

	private void redraw(NotaFiscal obj, Integer inicial, Integer maximoPermitido) {
		this.hmSis = new NotaFiscalDAO().getRegByExampleLimit(obj, inicial,
				maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {

		this.notaFiscal = new NotaFiscal();
		this.fornecedor = new Fornecedor();

	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		if (fornecedor != null) {
			this.notaFiscal.setFornecedor(fornecedor);
		}
		this.fornecedor = fornecedor;
	}

	public void pesquisarFornecedor() {
		new WinUtils().abrirLis("forms/fornecedorlis.zul", null, this,
				"recebeFornecedor");
	}

	public void recebeFornecedor(Object obj) {
		if (fornecedor != null) {
			this.fornecedor = (Fornecedor) obj;
			this.notaFiscal.setFornecedor(fornecedor);
		}
		this.vincular();
	}

}
