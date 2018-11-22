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

/**
 *
 * @author diego
 */
@ZKEntity(label="Lote de Terra",tipoTela=0,tipoPaginacao=1)
@Entity
@Table(name = "loteterra", catalog = "rastreabilidade", schema = "public")
public class LoteTerra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @ZKId
    @ZKField(label="Código",readonly=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idloteterra", nullable = false)
    private Integer idloteterra;
    
    @ZKField(label="Endereço",maxsize=300,nullable=false)
    @Basic(optional = false)
    @Column(name = "endereco", nullable = false, length = 300)
    private String endereco;
    
    @ZKField(label="Area Total",maxsize=20,nullable=false)
    @Basic(optional = false)
    @Column(name = "areatotal", nullable = false, length = 20)
    private String areatotal;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loteterra")
    private List<SetorPlantio> setorPlantioList;
    
    @ZKFieldFind(label="Cidade",nullable=false,propDisplayFieldFind="nomecidade")
    @JoinColumn(name = "cidade", referencedColumnName = "idcidade", nullable = false)
    @ManyToOne(optional = false)
    private Cidade cidade;


	public LoteTerra() {
    }

    public LoteTerra(Integer idloteterra) {
        this.idloteterra = idloteterra;
    }

    public LoteTerra(Integer idloteterra, String endereco, String areatotal) {
        this.idloteterra = idloteterra;
        this.endereco = endereco;
        this.areatotal = areatotal;
    }

    public Integer getIdloteterra() {
        return idloteterra;
    }

    public void setIdloteterra(Integer idloteterra) {
        this.idloteterra = idloteterra;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getAreatotal() {
        return areatotal;
    }

    public void setAreatotal(String areatotal) {
        this.areatotal = areatotal;
    }

    public List<SetorPlantio> getSetorPlantioList() {
        return setorPlantioList;
    }

    public void setSetorPlantioList(List<SetorPlantio> setorPlantioList) {
        this.setorPlantioList = setorPlantioList;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idloteterra != null ? idloteterra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoteTerra)) {
            return false;
        }
        LoteTerra other = (LoteTerra) object;
        if ((this.idloteterra == null && other.idloteterra != null) || (this.idloteterra != null && !this.idloteterra.equals(other.idloteterra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.LoteTerra[idloteterra=" + idloteterra + "]";
    }

}
