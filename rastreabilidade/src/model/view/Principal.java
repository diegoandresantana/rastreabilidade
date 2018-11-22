package model.view;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;

import framework.util.WinUtils;
import framework.util.Window;

public class Principal extends Window {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public void initComponentes() {
		if (session.getAttribute("login") == null) {
			final Window win = (Window) Executions.createComponents(
					"/login.zul", null, null);
			win.setMaximizable(true);
			try {
				win.doModal();
			} catch (SuspendNotAllowedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void abrirTela(String url) {
		new WinUtils().abrirLis("forms/" + url, null, this, null);
	}

	public void desconectar() {
		session.removeAttribute("login");
		initComponentes();

	}
}
