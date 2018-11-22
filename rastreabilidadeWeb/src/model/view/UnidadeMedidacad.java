package model.view;

import java.util.HashMap;
import java.util.Map;

import model.dao.UnidadeMedidaDAO;
import model.entity.hibernate.UnidadeMedida;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
import model.logic.LogicFactory;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class UnidadeMedidacad extends WindowCrud {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2837857668060308087L;
	public UnidadeMedida unidadeMedida = new UnidadeMedida();


	@SuppressWarnings("unchecked")
	public UnidadeMedidacad() {
		super("unidademedidacad.zul");
		Map<String, String> map = Executions.getCurrent().getArg();
		if (map.get("pageName") != null) {
			abertoPeloMenu = Boolean.FALSE;
		}
	}

	public void initComponentes() {
         controleBotoesDisable(false, true, true);
		this.vincular();
	}

	@Override
	public void incluir() {
		try {
			if (this.validarForm() && this.trataVO()
					&& !this.getCrdBar().getBotao(0).isDisabled()) {

				
				LogicFactory.getUnidadeMedidaLogic().insertReg(this.unidadeMedida);
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro incluído com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.unidadeMedida);
				}
				limpar();
			}
		} catch (LogicException e) {
			try {
				Messagebox.show(e.getMessage());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}catch(WrongValueException e){			
			throw e;
		}		
		catch (Exception e) {
			try {
				Messagebox.show("O registro não pode ser incluído: "
						+ e.getMessage(), "Erro:", Messagebox.OK,
						Messagebox.ERROR);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void salvar() {
		try {
			if (this.validarForm() && this.trataVO()
					&& this.unidadeMedida.getIdunidademedida() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {

				
				LogicFactory.getUnidadeMedidaLogic().updateReg(this.unidadeMedida);
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro Salvo com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.unidadeMedida);
				}
				limpar();
			}
		} catch (LogicException e) {
			try {
				Messagebox.show(e.getMessage());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}catch(WrongValueException e){			
			throw e;
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void apagar() {

		try {
			if (Messagebox.show("Deseja realmente excluir ?", "Aviso!!!",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {

				try {
					LogicFactory.getUnidadeMedidaLogic().deleteReg(unidadeMedida);
					Messagebox.show("Registro excluído com sucesso!");
					this.limpar();
				} catch (LogicException e) {
					Messagebox.show(e.getMessage());
				}
			}

		} catch (InterruptedException e) {
			try {
				Messagebox.show("Registro não pode ser excluído!");
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@Override
	public void limpar() {

		this.unidadeMedida = new UnidadeMedida();
         controleBotoesDisable(false, true, true);
		
		this.vincular();
	}

	@Override
	public void pesquisar() {

		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/unidademedidalis.zul", param, this,
				"retUnidadeMedida");
	}

	public void retUnidadeMedida(Object ret) {
		if (ret != null) {
			this.unidadeMedida = (UnidadeMedida) ret;
              controleBotoesDisable(true, false, false);
			
			this.vincular();
		}
	}

	@Override
	public void imprimir() {
	}

	public boolean trataVO() {
		if (this.unidadeMedida != null) {
			// this.unidadeMedida.setUsualt(this.getLogin());
			// this.unidadeMedida.setDatalt(WinUtils.getDataBanco());

			return true;
		}
		return false;
	}

	@Override
	public void sair() {
		if (this.abertoPeloMenu) {
			this.detach();
		} else {
			Events.postEvent("onClose", this, this.unidadeMedida);
		}
	}

}
