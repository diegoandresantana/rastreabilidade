package model.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import model.dao.impl.hibernate.HistoricoDAO;
import model.dao.impl.hibernate.TipoHistoricoDAO;
import model.entity.hibernate.Historico;
import model.entity.hibernate.LoteVegetal;
import model.entity.hibernate.TipoHistorico;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Paging;
import org.zkoss.zul.event.PagingEvent;

import framework.util.WinUtils;
import framework.util.WindowList;

@SuppressWarnings("serial")
public class Historicolis extends WindowList<Historico> {

	public ListModelList lmTipoHistorico;
	private LoteVegetal lotevegetal = new LoteVegetal();
	private TipoHistorico tipohistorico = new TipoHistorico();
	public Historico historico = new Historico();
	private List<Historico> hmSis;

	public Historico obj;

	public Historicolis() {
		super();
	}

	public void initComponentes() {

		lmTipoHistorico = new ListModelList(new TipoHistoricoDAO()
				.getRegByExample(null));
		this.getCrdBar().getBotao(3).setVisible(true);
		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}

	public void pesquisar() {
		Paging pag = (Paging) this.getComponente("pagHistorico");
		obj = new Historico();
		try {
			obj = (Historico) BeanUtils.cloneBean(historico);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		pag.setTotalSize(new HistoricoDAO().countAllLimit(obj).intValue());
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

	private void redraw(Historico obj, Integer inicial, Integer maximoPermitido) {
		this.hmSis = new HistoricoDAO().getRegByExampleLimit(obj, inicial,
				maximoPermitido);
		setListmodel(new ListModelList(hmSis));
	}

	public void limpar() {

		this.historico = new Historico();
		this.lotevegetal = new LoteVegetal();
		this.tipohistorico = new TipoHistorico();

	}

	public LoteVegetal getLotevegetal() {
		return lotevegetal;
	}

	public TipoHistorico getTipohistorico() {
		return tipohistorico;
	}

	public void setLotevegetal(LoteVegetal lotevegetal) {
		if (lotevegetal != null) {
			this.historico.setLotevegetal(lotevegetal);
		}
		this.lotevegetal = lotevegetal;
	}

	public void setTipohistorico(TipoHistorico tipohistorico) {
		if (tipohistorico != null) {
			this.historico.setTipohistorico(tipohistorico);
		}
		this.tipohistorico = tipohistorico;
	}

	public void pesquisarLoteVegetal() {
		new WinUtils().abrirLis("forms/lotevegetallis.zul", null, this,
				"recebeLoteVegetal");
	}

	public void recebeLoteVegetal(Object obj) {
		if (lotevegetal != null) {
			this.lotevegetal = (LoteVegetal) obj;
			this.historico.setLotevegetal(lotevegetal);
		}
		this.vincular();
	}

}
