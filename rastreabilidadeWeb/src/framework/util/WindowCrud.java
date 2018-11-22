package framework.util;

import java.util.HashMap;

import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;


@SuppressWarnings("unchecked")
public abstract class WindowCrud extends Window implements EventListener {

	private static final long serialVersionUID = -2726993296221714093L;

	/**
	 * {@link CrudBar#btnIncluir()}
	 */
	public abstract void incluir();
	/**
	 * {@link CrudBar#btnSalvar()}
	 */
	public abstract void salvar();
	/**
	 * {@link CrudBar#btnApagar()}
	 */
	public abstract void apagar();
	/**
	 * {@link CrudBar#btnPesquisar()}
	 */
	public abstract void pesquisar();
	/**
	 * {@link CrudBar#btnLimpar()}
	 */
	public abstract void limpar();
	/**
	 * {@link CrudBar#btnImprimir()}
	 */
	public abstract void imprimir();
	/**
	 * {@link CrudBar#btnSair()}
	 */
	public abstract void sair();
	
	private CrudBar crdBar = null;

	public CrudBar getCrdBar() {
		return crdBar;
	}
	
	public void setCrdBar(CrudBar crdBar) {
		this.crdBar = crdBar;
	}
	
	public WindowCrud() {
		this.addCrudBar();
		
	}
     public void controleBotoesDisable(boolean inserir,boolean salvar,boolean apagar){
    	 getCrdBar().getBotao(0).setDisabled(inserir);
    	 getCrdBar().getBotao(1).setDisabled(salvar);
    	 getCrdBar().getBotao(2).setDisabled(apagar);
    	
     }
	/**
	 * Instancia o objeto fazendo a checagem de permissoes
	 * @param zulPage - Nome da pagina que deve ser verificada
	 */
	public WindowCrud(String zulPage) {
		this.addCrudBar();
		this.chkButoes(zulPage);
		getCrdBar().getBotao(5).setVisible(false);
	}

	private void addCrudBar() {
		/**
		 * Listener para os eventos de cliques dos botoes da Toolbar
		 */
		this.setCtrlKeys("^i^d^s^l^r^p^q");
		this.addEventListener(Events.ON_CTRL_KEY, this);

		this.appendChild(this.crdBar = new CrudBar() {
			@Override
			public void btnApagar() {
				apagar();
			}

			@Override
			public void btnIncluir() {
				incluir();
			}

			@Override
			public void btnPesquisar() {
				pesquisar();
			}

			@Override
			public void btnSalvar() {
				salvar();
			}

			@Override
			public void btnLimpar() {
				limpar();
			}

			@Override
			public void btnImprimir() {
				imprimir();
			}

			@Override
			public void btnSair() {
				sair();
			}
		});
	}

	private boolean chkButtonAvailable(int i){
		if (this.getCrdBar().getBotao(i).isVisible() &&
				!this.getCrdBar().getBotao(i).isDisabled())
			return true;
		else return false;
	}
	
	public void onEvent(Event event) throws UiException {
		if (event.getName().equals(Events.ON_CTRL_KEY)) {
			KeyEvent key = (KeyEvent) event;
			switch (key.getKeyCode()) {
			// CTRL+I = INCLUIR
			case 73:
				if (this.chkButtonAvailable(0))			
					this.incluir();
				break;
			// CTRL+S = SALVAR
			case 83:
				if (this.chkButtonAvailable(1))
					this.salvar();
				break;
			// CTRL+D = APAGAR
			case 68:
				if (this.chkButtonAvailable(2))
					this.apagar();
				break;
			// CTRL+R = LIMPAR
			case 82:
				if (this.chkButtonAvailable(3))
					this.limpar();
				break;
			// CTRL+L = PESQUISAR
			case 76:
				if (this.chkButtonAvailable(4))
					this.pesquisar();
				break;
			// CTRL+P = IMPRIMIR
			case 80:
				if (this.chkButtonAvailable(5))
					this.imprimir();
				break;
			// CTRL+Q = SAIR
			case 81:
				if (this.chkButtonAvailable(6))
					this.sair();
				break;
			}
		}
	}

	private void chkButoes(String key) {
		HashMap<String, HashMap> per = null;
		HashMap frm = null;
		String f[] = { "cadfrm", "altfrm", "excfrm", "", "busfrm", "impfrm" };
		String p[] = { "cadace", "altace", "excace", "", "busace", "impace" };

		try {
			per = (HashMap) getSession().getAttribute("permissoes");
			frm = per.get(key);
			if ("1".equals(String.valueOf(((HashMap) getSession().getAttribute(
					"usumnu")).get("codprf"))))
				return;

			for (int i = 0; i < p.length; i++) {
				if ("N".equals((String) frm.get(f[i])))
					this.crdBar.getBotao(i).setVisible(false);
				else if ("N".equals((String) frm.get(p[i])))
					this.crdBar.getBotao(i).setDisabled(true);
			}
		} catch (Exception e) {		}
	}
}