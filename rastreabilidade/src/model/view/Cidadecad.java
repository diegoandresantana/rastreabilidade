package model.view;

import java.util.HashMap;
import java.util.Map;

import model.dao.impl.hibernate.CidadeDAO;
import model.dao.impl.hibernate.UfDAO;
import model.entity.hibernate.Cidade;
import model.entity.hibernate.Uf;
import model.exceptions.DaoException;

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

		lmUf = new ListModelList(new UfDAO().getRegByExample(null));
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.getCrdBar().getBotao(2).setDisabled(true);
		this.vincular();
	}

	@Override
	public void incluir() {
		try {
			if (this.validarForm() && this.trataVO()
					&& !this.getCrdBar().getBotao(0).isDisabled()) {

				CidadeDAO cidadeDAO = new CidadeDAO();
				cidade = cidadeDAO.insertReg(this.cidade);
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro incluído com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.cidade);
				}
				this.getCrdBar().getBotao(0).setDisabled(true);
				this.getCrdBar().getBotao(1).setDisabled(false);
				this.vincular();
			}
		} catch(WrongValueException e){			
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
					&& this.cidade.getIdcidade() != null
					&& !this.getCrdBar().getBotao(1).isDisabled()) {

				CidadeDAO cidadeDAO = new CidadeDAO();
				cidadeDAO.updateReg(this.cidade);
				Messagebox.show("Registro Salvo com sucesso!");
				if (this.abertoPeloMenu) {
					Messagebox.show("Registro Salvo com sucesso!");
				} else {
					Events.postEvent("onClose", this, this.cidade);
				}
				this.vincular();
			}
		}catch(WrongValueException e){			
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.limpar();
	}

	@Override
	public void apagar() {

		try {
			if (Messagebox.show("Deseja realmente excluir ?", "Aviso!!!",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {

				try {
					new CidadeDAO().deleteReg(cidade);
					Messagebox.show("Registro excluído com sucesso!");
					this.limpar();
				} catch (DaoException e) {
					Messagebox.show("Registro excluído com sucesso!");
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

		this.getCrdBar().getBotao(0).setDisabled(false);
		this.getCrdBar().getBotao(1).setDisabled(true);
		this.getCrdBar().getBotao(2).setDisabled(true);
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

			this.cidade = (Cidade) ret;
			this.uf = this.cidade.getUf();
			this.getCrdBar().getBotao(0).setDisabled(true);
			this.getCrdBar().getBotao(1).setDisabled(false);
			this.getCrdBar().getBotao(2).setDisabled(false);
			this.vincular();
		}
	}

	@Override
	public void imprimir() {
	}

	public boolean trataVO() {
		if (this.cidade != null) {
			// this.cidade.setUsualt(this.getLogin());
			// this.cidade.setDatalt(WinUtils.getDataBanco());

			if (this.cidade.getUf() == null) {
				try {
					Messagebox.show("Selecione o UF.");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return false;
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
		lmUf = new ListModelList(new UfDAO().getRegByExample(null));
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
