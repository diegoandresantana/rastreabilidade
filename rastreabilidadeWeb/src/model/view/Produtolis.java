package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.entity.hibernate.Produto;
import model.entity.hibernate.TipoProduto;
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
public class Produtolis extends WindowList<Produto> {

	public ListModelList lmTipoProduto;
	public ListModelList lmUnidadeMedida;
	private TipoProduto tipoproduto = new TipoProduto();
	private UnidadeMedida unidademedida = new UnidadeMedida();
	public Produto produto = new Produto();
	private List<Produto> hmSis;
	public Produto obj;

	public Produtolis() {
		super();
	}

	public void initComponentes() {

		lmTipoProduto = new ListModelList(LogicFactory.getTipoProdutoLogic()
				.getRegByExample(null));
		lmUnidadeMedida = new ListModelList(LogicFactory.getUnidadeMedidaLogic()
				.getRegByExample(null));
		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}

	public void pesquisar() {
		Paging pag = (Paging) this.getComponente("pagProduto");
		obj = new Produto();
		try {
			obj = (Produto) BeanUtils.cloneBean(produto);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		pag.setTotalSize(LogicFactory.getProdutoLogic().countAllLimit(obj).intValue());
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

	private void redraw(Produto obj, Integer inicial, Integer maximoPermitido) {
		this.hmSis =LogicFactory.getProdutoLogic().getRegByExampleLimit(obj, inicial,
				maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {

		this.produto = new Produto();
		this.tipoproduto = new TipoProduto();
		this.unidademedida = new UnidadeMedida();
		  this.vincular();
	}

	public TipoProduto getTipoproduto() {
		return tipoproduto;
	}

	public UnidadeMedida getUnidademedida() {
		return unidademedida;
	}

	public void setTipoproduto(TipoProduto tipoproduto) {
		if (tipoproduto != null) {
			this.produto.setTipoproduto(tipoproduto);
		}
		this.tipoproduto = tipoproduto;
	}

	public void setUnidademedida(UnidadeMedida unidademedida) {
		if (unidademedida != null) {
			this.produto.setUnidademedida(unidademedida);
		}
		this.unidademedida = unidademedida;
	}

}
