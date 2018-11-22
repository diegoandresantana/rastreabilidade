package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.dao.impl.hibernate.DestinoLoteDAO;
import model.entity.hibernate.Cliente;
import model.entity.hibernate.DestinoLote;
import model.entity.hibernate.LoteVegetal;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import framework.util.WinUtils;
import framework.util.WindowList;

@SuppressWarnings("serial")
public class DestinoLotelis extends WindowList<DestinoLote> {

	private Cliente cliente = new Cliente();
	private LoteVegetal lotevegetal = new LoteVegetal();
	public DestinoLote destinoLote = new DestinoLote();
	private List<DestinoLote> hmSis;

	public DestinoLote obj;

	public DestinoLotelis() {
		super();
	}

	public void initComponentes() {

		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}

	public void pesquisar() {
	
		Paging pag = (Paging) this.getComponente("pagDestinoLote");
		
		obj = new DestinoLote();
		try {
			obj = (DestinoLote) BeanUtils.cloneBean(destinoLote);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		pag.setTotalSize(new DestinoLoteDAO().countAllLimit(obj).intValue());
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

	private void redraw(DestinoLote obj, Integer inicial,
			Integer maximoPermitido) {
		this.hmSis = new DestinoLoteDAO().getRegByExampleLimit(obj, inicial,
				maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {

		this.destinoLote = new DestinoLote();
		this.cliente = new Cliente();
		this.lotevegetal = new LoteVegetal();

	}

	public Cliente getCliente() {
		return cliente;
	}

	public LoteVegetal getLotevegetal() {
		return lotevegetal;
	}

	public void setCliente(Cliente cliente) {
		if (cliente != null) {
			this.destinoLote.setCliente(cliente);
		}
		this.cliente = cliente;
	}

	public void setLotevegetal(LoteVegetal lotevegetal) {
		if (lotevegetal != null) {
			this.destinoLote.setLotevegetal(lotevegetal);
		}
		this.lotevegetal = lotevegetal;
	}

	public void pesquisarCliente() {
		new WinUtils().abrirLis("forms/clientelis.zul", null, this,
				"recebeCliente");
	}

	public void recebeCliente(Object obj) {
		if (cliente != null) {
			this.cliente = (Cliente) obj;
			this.destinoLote.setCliente(cliente);
		}
		this.vincular();
	}

	public void pesquisarLoteVegetal() {
		new WinUtils().abrirLis("forms/lotevegetallis.zul", null, this,
				"recebeLoteVegetal");
	}

	public void recebeLoteVegetal(Object obj) {
		if (lotevegetal != null) {
			this.lotevegetal = (LoteVegetal) obj;
			this.destinoLote.setLotevegetal(lotevegetal);
		}
		this.vincular();
	}

}
