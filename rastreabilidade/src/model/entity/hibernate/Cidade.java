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

import annotations.ZKCombo;
import annotations.ZKEntity;
import annotations.ZKField;
import annotations.ZKId;

/**
 *
 * @author diego
 */
@ZKEntity(label="Cidade",tipoPaginacao=1)
@Entity
@Table(name = "cidade", catalog = "rastreabilidade", schema = "public")
public class Cidade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @ZKId
    @ZKField(label="Código",readonly=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcidade", nullable = false)
    private Integer idcidade;
    
    @ZKField(label="Nome da Cidade",maxsize=150,nullable=false)
    @Basic(optional = false)
    @Column(name = "nomecidade", nullable = false, length = 150)
    private String nomecidade;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cidade")
    private List<Fornecedor> fornecedorList;
    
    @ZKCombo(label="UF",nullable=false,propDisplayCombo="sigla")
    @JoinColumn(name = "uf", referencedColumnName = "iduf", nullable = false)
    @ManyToOne(optional = false)
    private Uf uf;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cidade")
    private List<Cliente> clienteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cidade")
    private List<LoteTerra> loteTerraList;

    public Cidade() {
    }

    public Cidade(Integer idcidade) {
        this.idcidade = idcidade;
    }

    public Cidade(Integer idcidade, String nomecidade) {
        this.idcidade = idcidade;
        this.nomecidade = nomecidade;
    }

    public Integer getIdcidade() {
        return idcidade;
    }

    public void setIdcidade(Integer idcidade) {
        this.idcidade = idcidade;
    }

    public String getNomecidade() {
        return nomecidade;
    }

    public void setNomecidade(String nomecidade) {
        this.nomecidade = nomecidade;
    }

    public List<Fornecedor> getFornecedorList() {
        return fornecedorList;
    }

    public void setFornecedorList(List<Fornecedor> fornecedorList) {
        this.fornecedorList = fornecedorList;
    }

    public Uf getUf() {
        return uf;
    }

    public void setUf(Uf uf) {
        this.uf = uf;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public List<LoteTerra> getLoteTerraList() {
        return loteTerraList;
    }

    public void setLoteTerraList(List<LoteTerra> loteTerraList) {
        this.loteTerraList = loteTerraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcidade != null ? idcidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cidade)) {
            return false;
        }
        Cidade other = (Cidade) object;
        if ((this.idcidade == null && other.idcidade != null) || (this.idcidade != null && !this.idcidade.equals(other.idcidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.Cidade[idcidade=" + idcidade + "]";
    }

}
