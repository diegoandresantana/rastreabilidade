package model.view;

import java.util.List;

import model.entity.hibernate.Usuario;
import model.exceptions.LogicException;
import model.logic.LogicFactory;

import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zul.Messagebox;

import framework.util.Window;

public class Login extends Window {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public Usuario usuario=new Usuario();
	public void initComponentes() throws SuspendNotAllowedException,
			InterruptedException {
       this.vincular();
	}

	public void verificaLogin() {
		Usuario usu;
		try {
			
			usu = LogicFactory.getUsuarioLogic().getUsuarioByLogin(usuario.getLoginusu());
			if(usu!=null){
				if(usuario.getSenha().toUpperCase().equals(usu.getSenha().toUpperCase())){
					session.setAttribute("login", usu.getLoginusu());
					this.detach();
				}else{
					try {
						Messagebox.show("Login ou Senha esta incorreto");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				try {
					Messagebox.show("Login ou Senha esta incorreto");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (LogicException e) {
			
			e.printStackTrace();
			try {
				Messagebox.show(e.getMessage());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}

}
