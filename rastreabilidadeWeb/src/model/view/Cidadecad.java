package model.view;

import java.util.HashMap;
import java.util.Map;

import model.entity.hibernate.Cidade;
import model.entity.hibernate.Uf;
import model.exceptions.LogicException;
import model.logic.CidadeLogic;
import model.logic.LogicFactory;
import model.logic.UfLogic;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import framework.util.WinUtils;
import framework.util.WindowCrud;

public class Cidadecad extends WindowCrud {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7920990520552523952L;
	public Cidade cidade = new Cidade();
	public ListModelList lmUf;
	private Uf uf = new Uf();

	@SuppressWarnings("unchecked")
	public Cidadecad() {
		super("cidadecad.zul");
		Map<String, String> map = Executions.getCurrent().getArg();
		if (map.get("pageName") != null) {
			abertoPeloMenu = Boolean.FALSE;
		}
	}

	public void initComponentes() {

		lmUf = new ListModelList(LogicFactory.getUfLogic()
				.getRegByExample(null));
		controleBotoesDisable(false, true, true);
		this.vincular();
	}

	@Override
	public void incluir() {
		try {
			if (this.validarForm() && this.trataVO()
					&& !this.getCrdBar().getBotao(0).isDisabled()) {

				cidade = LogicFactory.getCidadeLogic().insertReg(this.cidade);
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro incluído com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.cidade);
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
		} catch (WrongValueException e) {
			throw e;
		} catch (Exception e) {
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
					&& this.cidade.getIdcidade() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {

				LogicFactory.getCidadeLogic().updateReg(this.cidade);

				if (this.abertoPeloMenu) {
					Messagebox.show("Registro Salvo com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.cidade);
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
		} catch (WrongValueException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void apagar() {

		try {
			if (Messagebox.show("Deseja realmente excluir ?", "Aviso!!!",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {

				try {
					LogicFactory.getCidadeLogic().deleteReg(cidade);
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

		this.cidade = new Cidade();
		this.uf = new Uf();
		controleBotoesDisable(false, true, true);

		this.vincular();
	}

	@Override
	public void pesquisar() {

		Map<String, String> param = new HashMap<String, String>();
		new WinUtils().abrirLis("/forms/cidadelis.zul", param, this,
				"retCidade");
	}

	public void retCidade(Object ret) {
		if (ret != null) {
			this.cidade = (Cidade) ret;

			this.uf = this.cidade.getUf();
			controleBotoesDisable(true, false, false);

			this.vincular();
		}
	}

	@Override
	public void imprimir() {
	}

	public boolean trataVO() {
		if (this.cidade != null) {			

			if (this.cidade.getUf() == null) {
				 throw  new WrongValueException(getComponente("uf"),"Selecione o UF!");
			
			}
			return true;
		}
		return false;
	}

	@Override
	public void sair() {
		if (this.abertoPeloMenu) {
			this.detach();
		} else {
			Events.postEvent("onClose", this, this.cidade);
		}
	}

	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		if (uf != null) {
			this.cidade.setUf(uf);
		}
		this.uf = uf;
	}

	public void recebeUf(Object obj) {
		lmUf = new ListModelList(LogicFactory.getUfLogic()
				.getRegByExample(null));
		if (obj != null) {
			this.uf = (Uf) obj;
			this.cidade.setUf(uf);
		}
		this.vincular();
	}

	public void addUf() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageName", "cidadecad.zul");
		String url = "forms/ufcad.zul";
		new WinUtils().abrirLis(url, map, this, "recebeUf");
	}

}
