package framework.util;

import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Toolbar;

/**
 * Toolbar padrao para ser utilizadas com as telas CRUD do modelo
 * 
 
 * 
 */
public abstract class CrudBar extends Toolbar implements EventListener {
	private static final long serialVersionUID = -8793631862368577896L;

	/**
	 * Metodo chamado para inclusao de registro
	 */
	public abstract void btnIncluir();

	/**
	 * Metodo chamado para salvar/alterar um registro
	 */
	public abstract void btnSalvar();

	/**
	 * Metodo chamada na exclusao de um registro
	 */
	public abstract void btnApagar();

	/**
	 * Metodo chamado para pesquisa de registro
	 */
	public abstract void btnPesquisar();

	/**
	 * Metodo chamado para limpar a tela
	 */
	public abstract void btnLimpar();

	/**
	 * Metodo chamado para imprimir relatorio
	 */
	public abstract void btnImprimir();

	/**
	 * Metodo chamado para fechar a janela
	 */
	public abstract void btnSair();

	private Button[] btn = new Button[10];
	private String dirImg = "/images/toolbar/";
	private String[] tip = { "Incluir (CTRL+I)", "Salvar (CTRL+S)",
			"Apagar (CTRL+D)", "Limpar (CTRL+R)", "Pesquisar (CTRL+L)",
			"Imprimir (CTRL+P)", "Sair (CTLR+Q)" };
	private String[] img = { "btnIncluir.png", "btnSalvar.png",
			"btnApagar.png", "btnLimpar.png", "btnPesquisar.png",
			"btnImprimir.png", "btnSair.png" };
	private int nBtn = 0;

	/**
	 * Retorna o diretorio em que as imagens padroes estao armazenas no pacote
	 * @return String
	 */
	public String getDirImg() {
		return dirImg;
	}

	/**
	 * Configura o diretorio de imagens
	 * @param dirImg
	 */
	public void setDirImg(String dirImg) {
		this.dirImg = dirImg;
	}

	/**
	 * Retorna as dicas configuradas para os botoes
	 * @return String[]
	 */
	public String[] getTip() {
		return tip;
	}

	/**
	 * Configura as dicas dos botoes
	 * @param tip
	 */
	public void setTip(String[] tip) {
		this.tip = tip;
	}

	/**
	 * Retorna os nomes dos arquivos das imagens dos botoes
	 * @return String[]
	 */
	public String[] getImg() {
		return img;
	}

	/**
	 * Configura os nomes dos arquivos das imagens dos botoes
	 * @param img
	 */
	public void setImg(String[] img) {
		this.img = img;
	}

	public CrudBar() {
		super();
		initButtons();
		this.setHeight("40px");
		
	}

	/**
	 * Seta as configuracoes dos botoes, preparando-os para exibixao
	 */
	protected void initButtons() {
		Hbox hbox = new Hbox();
		try {
			for (int i = 0; i < img.length; i++) {
				btn[i] = new Button();
				// btn[i].setImage(dirImg + img[i]);
				//java.io.InputStream is = getClass().getResourceAsStream("/WebContent" + dirImg + img[i]);
				//Image ig = new AImage(img[i], is);
				//btn[i].setImageContent(ig);
				btn[i].setImage( dirImg + img[i]);
				//btn[i].setImageContent(WinUtils.getImageIcons(img[i]));
				btn[i].setId(img[i].split("\\.")[0].replaceAll("btn", ""));
				btn[i].setTooltiptext(tip[i]);
				btn[i].setTooltip(tip[i]);
				btn[i].setWidth("60px");
				btn[i].addEventListener(Events.ON_CLICK, this);
				btn[i].setHeight("32px");
				btn[i].setOrient("vertical");
				hbox.appendChild(btn[i]);
				this.nBtn++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.appendChild(hbox);
	}
	
	public void onEvent(Event event) throws UiException {
		if (Events.ON_CLICK.equals(event.getName())
				|| Events.ON_CTRL_KEY.equals(event.getName())) {
			String id = event.getTarget().getId().toLowerCase();
			if ("incluir".equals(id))
				this.btnIncluir();
			else if ("salvar".equals(id))
				this.btnSalvar();
			else if ("apagar".equals(id))
				this.btnApagar();
			else if ("pesquisar".equals(id))
				this.btnPesquisar();
			else if ("limpar".equals(id))
				this.btnLimpar();
			else if ("imprimir".equals(id))
				this.btnImprimir();
			else if ("sair".equals(id))
				this.btnSair();
		}
	}

	/**
	 * Adiciona um botao a barra
	 * @param b
	 * @return boolean
	 */
	public boolean adicionarBotao(Button b) {
		try {
			btn[this.nBtn++] = b;
		} catch (Exception ex) {
			return false;
		}
		return true;

	}

	/**
	 * Retorna um botao da barra
	 * 0 = Incluir,   1 = Salvar,   2 = Apagar, 3 = Limpar, 
	 * 4 = Pesquisar, 5 = Imprimir, 6 = Sair
	 * 
	 * @param i indice do botao a ser retornado
	 * @return Button
	 */
	public Button getBotao(int i) {
		return btn[i];
	}

	/**
	 * Modifica os parametros do botao
	 * 
	 * @param btn
	 *            int - Referencia do botao: 
	 *               0 = Incluir,   1 = Salvar,   2 = Apagar, 3 = Limpar, 
	 *               4 = Pesquisar, 5 = Imprimir, 6 = Sair
	 * @param imagem
	 *            String - Caminho da imagem do botao
	 * @param tip
	 *            String - Dica do botao
	 */
	public void setBotao(int btn, String imagem, String tip) {
		this.getBotao(btn).setImage(imagem);
		this.getBotao(btn).setTooltiptext(tip);
		this.getBotao(btn).setTooltip(tip);
	}

}