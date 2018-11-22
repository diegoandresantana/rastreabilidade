/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model.entity.hibernate;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import annotations.ZKEntity;
import annotations.ZKField;
import annotations.ZKFieldFind;
import annotations.ZKId;
import annotations.ZKTelaForte;

/**
 *
 * @author diego
 */
@ZKEntity(label="Setor de Plantio",tipoTela=2,tipoPaginacao=1)
@Entity
@Table(name = "setorplantio", catalog = "rastreabilidade", schema = "public")
public class SetorPlantio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @ZKId
    @ZKField(label="Código",readonly=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsetorplantio", nullable = false)
    private Integer idsetorplantio;
    
    @ZKField(label="Codigo do Setor",maxsize=20,nullable=false)
    @Basic(optional = false)
    @Column(name = "codigosetor", nullable = false, length = 20)
    private String codigosetor;
    

	@ZKField(label="Tamanho da Area",maxsize=20,nullable=false)
    @Basic(optional = false)
    @Column(name = "tamanhoarea", nullable = false, length = 20)
    private String tamanhoarea;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "setorplantio")
    private List<LoteVegetal> loteVegetalList;
    
    @ZKTelaForte
    @ZKFieldFind(label="Lote de Terra",nullable=false,propDisplayFieldFind="idloteterra")
    @JoinColumn(name = "loteterra", referencedColumnName = "idloteterra", nullable = false)
    @ManyToOne(optional = false)
    private LoteTerra loteterra;

    public SetorPlantio() {
    }

    public SetorPlantio(Integer idsetorplantio) {
        this.idsetorplantio = idsetorplantio;
    }

    public SetorPlantio(Integer idsetorplantio, String tamanhoarea) {
        this.idsetorplantio = idsetorplantio;
        this.tamanhoarea = tamanhoarea;
    }

    public Integer getIdsetorplantio() {
        return idsetorplantio;
    }

    public void setIdsetorplantio(Integer idsetorplantio) {
        this.idsetorplantio = idsetorplantio;
    }

    public String getTamanhoarea() {
        return tamanhoarea;
    }

    public void setTamanhoarea(String tamanhoarea) {
        this.tamanhoarea = tamanhoarea;
    }

    public List<LoteVegetal> getLoteVegetalList() {
        return loteVegetalList;
    }

    public void setLoteVegetalList(List<LoteVegetal> loteVegetalList) {
        this.loteVegetalList = loteVegetalList;
    }

    public LoteTerra getLoteterra() {
        return loteterra;
    }

    public void setLoteterra(LoteTerra loteterra) {
        this.loteterra = loteterra;
    }

    public String getCodigosetor() {
		return codigosetor;
	}

	public void setCodigosetor(String codigosetor) {
		this.codigosetor = codigosetor;
	}
   

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codigosetor == null) ? 0 : codigosetor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetorPlantio other = (SetorPlantio) obj;
		if (codigosetor == null) {
			if (other.codigosetor != null)
				return false;
		} else if (!codigosetor.equals(other.codigosetor))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "teste.SetorPlantio[idsetorplantio=" + idsetorplantio + "]";
    }

}
