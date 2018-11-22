/**
 * 
 */
package framework.util;

import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.ListModel;

/**
 * Esta classe implementa uma Window com uma Toolbar e tabela, devem ser
 * implementados os métodos de chamada de cada botão. <br>
 * - Incluir() <br>
 * - Salvar() <br>
 * - Apagar() <br>
 * - Pesquisar() <br>
 * - Limpar() <br>
 * - Imprimir() <br>
 * - Sair() <br>
 * <b>A clase java deve estender esta classe.</b>
 * 

 * 
 */
public abstract class WindowCrudList<IT, DT> extends Window implements
		EventListener {

	/**
	 * Metodo Incluir para ser implementado para a Toolbar do CRUD
	 */
	public abstract void incluir();

	/**
	 * Metodo Salvar para ser implementado para a Toolbar do CRUD
	 */
	public abstract void salvar();

	/**
	 * Metodo Apagar para ser implementado para a Toolbar do CRUD
	 */
	public abstract void apagar();

	/**
	 * Metodo Pesquisar para ser implementado para a Toolbar do CRUD
	 */
	public abstract void pesquisar();

	/**
	 * Metodo Limpar para ser implementado para a Toolbar do CRUD
	 */
	public abstract void limpar();

	/**
	 * Metodo Imprimir para ser implementado para a Toolbar do CRUD
	 */
	public abstract void imprimir();

	/**
	 * Metodo Sair para ser implementado para a Toolbar do CRUD
	 */
	public abstract void sair();

	/**
	 * Vinculado ao Item selecionado
	 */
	protected IT item;

	/**
	 * ListModel Recebe os dados apartir de List fornecido por um DAO
	 */
	protected ListModel listmodel;

	/**
	 * HashMap Objeto VO vinculado ao Forumlario Detalhe
	 */
	protected DT detalheVO;

	private CrudBar crdBar = null;

	public CrudBar getCrdBar() {
		return crdBar;
	}

	public void setCrdBar(CrudBar crdBar) {
		this.crdBar = crdBar;
	}

	public WindowCrudList() {
		super();

		this.setCtrlKeys("^i^d^s^l^r^p^q");
		this.addEventListener(Events.ON_CTRL_KEY, this);
		this.crdBar = new CrudBar() {
			@Override
			public void btnApagar() {
				apagar();
			}

			@Override
			public void btnIncluir() {
				incluir();
			}

			@Override
			public void btnLimpar() {
				limpar();
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
			public void btnImprimir() {
				imprimir();
			}

			@Override
			public void btnSair() {
				sair();
			}
		};
		this.appendChild(this.crdBar);
	}

	/**
	 * Atualiza o vinculo dos atributos da classe com o formulario
	 */
	public void Vincular() {
		new AnnotateDataBinder(this).loadAll();
	}

	/**
	 * Método utilizado para retornar o Objeto vincluado a Linha
	 */
	public abstract void Selecionar();

	/**
	 * Objeto da Linha do ListBox selecionada
	 * 
	 * @return IT
	 */
	public IT getItem() {
		return item;
	}

	/**
	 * Objeto da Linha do ListBox selecionada
	 * 
	 * @param item
	 */
	public void setItem(IT item) {
		this.item = item;
	}

	/**
	 * ListModel do Listbox com a lista de dados pesquisados
	 * 
	 * @return ListModel
	 */
	public ListModel getListmodel() {
		return listmodel;
	}

	/**
	 * Configura o ListModel do Listbox com a lista de dados pesquisados
	 * 
	 * @param listmodel
	 */
	public void setListmodel(ListModel listmodel) {
		this.listmodel = listmodel;
	}

	public DT getDetalheVO() {
		return detalheVO;
	}

	public void setDetalheVO(DT detalheVO) {
		this.detalheVO = detalheVO;
	}

	private boolean chkButtonAvailable(int i) {
		if (this.getCrdBar().getBotao(0).isVisible()
				&& !this.getCrdBar().getBotao(0).isDisabled())
			return true;
		else
			return false;
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

}
