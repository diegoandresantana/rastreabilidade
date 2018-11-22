package model.logic;


public class LogicFactory {

	public LogicFactory() {
	}

	private  static Object classInstance(Class<?> clazz) {
		try {
			return Class.forName(clazz.getName()).newInstance();
		} catch (IllegalAccessException ex) {
		} catch (InstantiationException ex) {
		} catch (Exception e) {
		}
		return null;
	}

	public static CidadeLogic getCidadeLogic() {
		return (CidadeLogic) classInstance(CidadeLogic.class);
	}

	public  static  HistoricoLogic getHistoricoLogic() {
		return (HistoricoLogic) classInstance(HistoricoLogic.class);
	}

	public  static  LoteTerraLogic getLoteTerraLogic() {
		return (LoteTerraLogic) classInstance(LoteTerraLogic.class);
	}

	public  static  LoteVegetalLogic getLoteVegetalLogic() {
		return (LoteVegetalLogic) classInstance(LoteVegetalLogic.class);
	}

	public  static  ProdutoLogic getProdutoLogic() {
		return (ProdutoLogic) classInstance(ProdutoLogic.class);
	}

	public  static  SetorPlantioLogic getSetorPlantioLogic() {
		return (SetorPlantioLogic) classInstance(SetorPlantioLogic.class);
	}

	public  static  TipoHistoricoLogic getTipoHistoricoLogic() {
		return (TipoHistoricoLogic) classInstance(TipoHistoricoLogic.class);
	}

	public  static  TipoProdutoLogic getTipoProdutoLogic() {
		return (TipoProdutoLogic) classInstance(TipoProdutoLogic.class);
	}

	public  static  UfLogic getUfLogic() {
		return (UfLogic) classInstance(UfLogic.class);
	}
	public  static  UsuarioLogic getUsuarioLogic() {
		return (UsuarioLogic) classInstance(UsuarioLogic.class);
	}
	public  static  UnidadeMedidaLogic getUnidadeMedidaLogic() {
		return (UnidadeMedidaLogic) classInstance(UnidadeMedidaLogic.class);
	}
}