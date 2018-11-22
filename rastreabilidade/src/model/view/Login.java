package model.view;

import org.zkoss.zk.ui.SuspendNotAllowedException;

import framework.util.Window;

public class Login extends Window {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public void initComponentes() throws SuspendNotAllowedException,
			InterruptedException {

	}

	public void verificaLogin() {
		session.setAttribute("login", "Diego");
		this.detach();
	}

}
