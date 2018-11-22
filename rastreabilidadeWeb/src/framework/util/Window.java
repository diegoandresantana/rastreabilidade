package framework.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.http.ExecutionImpl;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.InputElement;

@SuppressWarnings( { "serial", "unchecked" })
public class Window extends org.zkoss.zul.Window {

	/**
	 * Requisicoes enviados para o Formulario
	 */
	protected Map request;

	/**
	 * Sessao
	 */
	protected HttpSession session;

	private AnnotateDataBinder binder = new AnnotateDataBinder(this);

	/**
	 * Componentes do Formulario
	 */
	private Map<String, HtmlBasedComponent> componentes = new HashMap<String, HtmlBasedComponent>();
	/**
	 * Variavel booleana para verificar se foi aberto pelo menu ou nao;
	 */
	protected Boolean abertoPeloMenu=Boolean.TRUE;


	public Window() {
		super();
		initComponentes();
	}

	/**
	 * Use o metodo {@link #setLogado}.
	 * 
	 * @param valida
	 */
	@Deprecated
	public void setValidaUser(boolean valida) {
		if (valida) {
			if (session.getAttribute("usuario") == null)
				Executions.sendRedirect("/principal.zul");
		}
	}

	/**
	 * Faz com que a pagina valide se o usuario esta logado, ou seja se sua
	 * session foi registrada, caso nao tenha sido registrada a sessao a pagina
	 * e redirecionada para <b>index</b>
	 * 
	 * @param b
	 */
	public void setLogado(Boolean b) {
		if (b && getSession().getAttribute("login") == null)
			Executions.sendRedirect("/principal.zul");
	}

	/**
	 * Retorna um objeto da tela, e' necessario que o elemento tenha o seu ID
	 * setado
	 * 
	 * @param id
	 *            - ID do elemento
	 * @return HtmlBasedComponent - pode ser convertido para qualquer tipo de
	 *         objeto da tela
	 */
	protected HtmlBasedComponent getComponente(String id) {
		if (componentes.size() == 0) {
			for (Object c : getSpaceOwner().getFellows()) {
				HtmlBasedComponent e = (HtmlBasedComponent) c;
				componentes.put(e.getId(), e);
			}
		}
		return componentes.get(id);
	}

	/**
	 * Retorna uma variavel configurada no arquivo ZUL pela TAG: <variable>
	 * 
	 * @param name
	 *            - String - nome da variavel
	 * @return Object
	 */
	public Object getVariavel(String name) {
		return getVariable(name, true);
	}

	/**
	 * Seta um valor para uma variavel configurada no arquivo ZUL pela TAG:
	 * <variable>
	 * 
	 * @param name
	 *            - nome da variavel
	 * @param val
	 *            - valor da variavel
	 */
	public void setVariavel(String name, Object val) {
		this.setVariable(name, val, true);
	}

	/**
	 * Inicializa os componentes da classe: request e session
	 */
	private void initComponentes() {
		request = ((ExecutionImpl) Executions.getCurrent()).getArg();
		// session = Sessions.getCurrent();
		session = (HttpSession) Executions.getCurrent().getDesktop()
				.getSession().getNativeSession();
	}

	/**
	 * Retorna verdadeiro quando todos os elementos da tela que possuem o
	 * atributo <b>constraint</b> forem satisfeitos, para a validacao destes
	 * elementos o ID do elemente tambem tem que estar setado
	 * 
	 * @return Boolean
	 */
	protected Boolean validarForm() {
		for (Object c : getSpaceOwner().getFellows()) {
			if (c instanceof Textbox || c instanceof Combobox
					|| c instanceof Datebox || c instanceof Intbox
					|| c instanceof Decimalbox) {
				InputElement e = (InputElement) c;
				Object pai = e.getParent();
				if (!e.isValid()) {
					for (;;) {
						if (pai instanceof Tabpanel) {
							((Tabpanel) pai).getLinkedTab().setSelected(true);
							break;
						} else if (pai instanceof org.zkoss.zul.Window) {
							((org.zkoss.zul.Window) pai).focus();
							break;
						}
						pai = ((HtmlBasedComponent) pai).getParent();
					}
					e.focus();
					e.getConstraint().validate(e, e.getText());
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Retorna um Map com os valores passados na requisicao
	 * 
	 * @return Map
	 */
	public Map getRequest() {
		initComponentes();
		return request;
	}

	/**
	 * Retorna a Session
	 * 
	 * @return Session
	 */
	public HttpSession getSession() {
		initComponentes();
		return session;
	}


	/**
	 * Retorna o siguni da unidade selecionada
	 * 
	 * @return String
	 */
	public String getSiguni() {

		HashMap m = (HashMap) session.getAttribute("usumnu");

		if (m.get("siguni") != null)
			return (String) m.get("siguni");

		return "-";
	}

	


	

	/**
	 * Método para Restaurar o vinculo do Bean com os campos da tela
	 */
	public void vincular() {
		// this.binder = new AnnotateDataBinder(this);
		binder.init(this, true);
		binder.loadAll();
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}
	public Boolean getAbertoPeloMenu() {
		return abertoPeloMenu;
	}

	public void setAbertoPeloMenu(Boolean abertoPeloMenu) {
		this.abertoPeloMenu = abertoPeloMenu;
	}
}