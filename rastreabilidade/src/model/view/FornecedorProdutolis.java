package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.dao.impl.hibernate.FornecedorProdutoDAO;
import model.entity.hibernate.Fornecedor;
import model.entity.hibernate.FornecedorProduto;
import model.entity.hibernate.Produto;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import framework.util.WinUtils;
import framework.util.WindowList;

@SuppressWarnings("serial")
public class FornecedorProdutolis extends WindowList<FornecedorProduto> {

	private Fornecedor fornecedor = new Fornecedor();
	private Produto produto = new Produto();
	public FornecedorProduto fornecedorProduto = new FornecedorProduto();
	private List<FornecedorProduto> hmSis;

	public FornecedorProduto obj;

	public FornecedorProdutolis() {
		super();
	}

	public void initComponentes() {

		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}

	public void pesquisar() {
		Paging pag = (Paging) this.getComponente("pagFornecedorProduto");
		obj = new FornecedorProduto();
		try {
			obj = (FornecedorProduto) BeanUtils.cloneBean(fornecedorProduto);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		pag.setTotalSize(new FornecedorProdutoDAO().countAllLimit(obj)
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

	private void redraw(FornecedorProduto obj, Integer inicial,
			Integer maximoPermitido) {
		this.hmSis = new FornecedorProdutoDAO().getRegByExampleLimit(obj,
				inicial, maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {

		this.fornecedorProduto = new FornecedorProduto();
		this.fornecedor = new Fornecedor();
		this.produto = new Produto();

	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		if (fornecedor != null) {
			this.fornecedorProduto.setFornecedor(fornecedor);
		}
		this.fornecedor = fornecedor;
	}

	public void setProduto(Produto produto) {
		if (produto != null) {
			this.fornecedorProduto.setProduto(produto);
		}
		this.produto = produto;
	}

	public void pesquisarFornecedor() {
		new WinUtils().abrirLis("forms/fornecedorlis.zul", null, this,
				"recebeFornecedor");
	}

	public void recebeFornecedor(Object obj) {
		if (fornecedor != null) {
			this.fornecedor = (Fornecedor) obj;
			this.fornecedorProduto.setFornecedor(fornecedor);
		}
		this.vincular();
	}

	public void pesquisarProduto() {
		new WinUtils().abrirLis("forms/produtolis.zul", null, this,
				"recebeProduto");
	}

	public void recebeProduto(Object obj) {
		if (produto != null) {
			this.produto = (Produto) obj;
			this.fornecedorProduto.setProduto(produto);
		}
		this.vincular();
	}

}
