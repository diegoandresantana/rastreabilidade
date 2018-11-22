package framework.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Window;

public class WinUtils {

	public final static String dirImg = "/WebContent/images";
	/**
	 * Abre uma janela de Busca (lis) e chama a funcao de callback, importante
	 * que a funcao de callback possua um parametro, pois ele sera retornado
	 * pelo lis com o dado selecionado
	 * 
	 * @param url
	 *            - Endereco da pagina de busca (.zul)
	 * @param param
	 *            - Paramametros que podem ser enviados a tela de busca
	 * @param win
	 *            - Janela que sera retornado os dados (classe ou window que
	 *            possui a funcao de callback)
	 * @param callback
	 *            - Nome da funcao que sera chamada quando houver a selecao na
	 *            tela busca
	 */
	public void abrirLis(String url, Map<String, String> param,
			final Object win, final String callback) {
		final Window cmp = (Window) Executions.createComponents(url, null,
				param);
		cmp.doHighlighted();
		cmp.addEventListener("onClose", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				Method m = win.getClass().getDeclaredMethod(callback,
						Object.class);
				try {
					if (event.getData() != null)
						m.invoke(win, event.getData());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Abre uma janela de Relatorio
	 * 
	 * @param relat
	 *            - caminho e nome do relatorio no birtviewer
	 * @param param
	 *            - Paramametros que podem ser enviados a tela de busca
	 */
	public void abrirBirt(String relat, Map<String, String> param) {
		Map<String, String> url = new HashMap<String, String>();
		String s = "/../birt2.3/frameset?__report=" + relat + "&__format=pdf";
		String par = "";
		for (Object key : param.keySet()) {
			par += "&" + (String) key + "=";
			par += (String) param.get(key);
		}
		url.put("url", s + par);
		final Window cmp = (Window) Executions.createComponents(
				"/forms/birt.zul", null, url);
		cmp.setMaximized(true);
		cmp.setSizable(true);
		cmp.setMaximizable(true);
		cmp.setClosable(true);
		cmp.doOverlapped();
	}
	/**
	 * Abre uma janela de Relatorio
	 * 
	 * @param relat
	 *            - caminho e nome do relatorio no ireport
	 * @param param
	 *            - Paramametros que podem ser enviados a tela de busca
	 */
	public void abrirIreport(String relat, Map<String, String> param) {

		Map<String, String> url = new HashMap<String, String>();
		String s = "/relatorio.do?report="+relat;
		String par = "";
		for (Object key : param.keySet()) {
			par += "&" +(String) key + "=";
			par += (String) param.get(key);
		}
		
		url.put("url", s + par);
		final Window cmp = (Window) Executions.createComponents("/forms/ireport.zul", null, url);
		cmp.setMaximized(true);
		cmp.setSizable(true);
		cmp.setMaximizable(true);
		cmp.setClosable(true);
		cmp.doOverlapped();
	}


	/**
	 * Retorna uma imagem dentro do pacote do framework
	 * @param name - nome do arquivo da imagem
	 * @return
	 */
	public static Image getImageIcons(String name) {
		String img[] = new String[] { "iconsForm/frmEdit.png",
				"iconsForm/frmImp.png", "iconsForm/frmConsulta.png",
				"toolbar/btnIncluir.png", "toolbar/btnSalvar.png",
				"toolbar/btnApagar.png", "toolbar/btnLimpar.png",
				"toolbar/btnPesquisar.png", "toolbar/btnImprimir.png",
				"toolbar/btnSair.png" };

		try {
			for (int i = 0; i < img.length; i++){
				if (img[i].toLowerCase().indexOf(name.toLowerCase()) > -1){
					System.out.println(dirImg + "/" + img[i]);
					return new AImage(" ", new WinUtils().getClass().getResourceAsStream(dirImg + "/" + img[i]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
