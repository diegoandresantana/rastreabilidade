package model.view;

import java.util.HashMap;
import java.util.Map;

import model.dao.TipoProdutoDAO;
import model.entity.hibernate.TipoProduto;
import model.exceptions.DaoException;
import model.exceptions.LogicException;
import model.logic.LogicFactory;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class TipoProdutocad extends WindowCrud {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3072502400866467429L;
	public TipoProduto tipoProduto = new TipoProduto();

	

	@SuppressWarnings("unchecked")
	public TipoProdutocad() {
		super("tipoprodutocad.zul");
		Map<String, String> map = Executions.getCurrent().getArg();
		if (map.get("pageName") != null) {
			abertoPeloMenu = Boolean.FALSE;
		}
	}

	public void initComponentes() {
         controleBotoesDisable(false, true,true);
		
		this.vincular();
	}

	@Override
	public void incluir() {
		try {
			if (this.validarForm() && this.trataVO()
					&& !this.getCrdBar().getBotao(0).isDisabled()) {

				LogicFactory.getTipoProdutoLogic().insertReg(this.tipoProduto);
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro incluído com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.tipoProduto);
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
					&& this.tipoProduto.getIdtipoproduto() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {

				
				LogicFactory.getTipoProdutoLogic().updateReg(this.tipoProduto);
				
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro Salvo com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.tipoProduto);
				}
				limpar();
			}
		}catch (LogicException e) {
			try {
				Messagebox.show(e.getMessage());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch(WrongValueException e){			
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
					LogicFactory.getTipoProdutoLogic().deleteReg(tipoProduto);
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

		this.tipoProduto = new TipoProduto();
         controleBotoesDisable(false,true,true);
		
		this.vincular();
	}

	@Override
	public void pesquisar() {

		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/tipoprodutolis.zul", param, this,
				"retTipoProduto");
	}

	public void retTipoProduto(Object ret) {
		if (ret != null) {
			this.tipoProduto = (TipoProduto) ret;

			controleBotoesDisable(true,false,false);
		
			this.vincular();
		}
	}

	@Override
	public void imprimir() {
	}

	public boolean trataVO() {
		if (this.tipoProduto != null) {
			// this.tipoProduto.setUsualt(this.getLogin());
			// this.tipoProduto.setDatalt(WinUtils.getDataBanco());

			return true;
		}
		return false;
	}

	@Override
	public void sair() {
		if (this.abertoPeloMenu) {
			this.detach();
		} else {
			Events.postEvent("onClose", this, this.tipoProduto);
		}
	}

}
