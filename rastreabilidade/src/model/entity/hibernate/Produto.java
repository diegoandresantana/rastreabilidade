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
import javax.persistence.UniqueConstraint;

import annotations.ZKCombo;
import annotations.ZKEntity;
import annotations.ZKField;
import annotations.ZKId;

/**
 *
 * @author diego
 */
@ZKEntity(label="Produto",tipoPaginacao=1)
@Entity
@Table(name = "produto", catalog = "rastreabilidade", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nomeproduto"})})
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @ZKId
    @ZKField(label="Código",readonly=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproduto", nullable = false)
    private Integer idproduto;
    
    @ZKField(label="Nome do Produto",maxsize=100,nullable=false)
    @Basic(optional = false)
    @Column(name = "nomeproduto", nullable = false, length = 100)
    private String nomeproduto;
    
    @ZKField(label="Valor",nullable=false)
    @Basic(optional = false)
    @Column(name = "valor", nullable = false)
    private Double valor;
    
    @ZKCombo(label="Tipo do Produto",nullable=false,propDisplayCombo="descricao")
    @JoinColumn(name = "tipoproduto", referencedColumnName = "idtipoproduto", nullable = false)
    @ManyToOne(optional = false)
    private TipoProduto tipoproduto;
    
    @ZKCombo(label="Unidade de Medida",nullable=false, propDisplayCombo="descricao")
    @JoinColumn(name = "unidademedida", referencedColumnName = "idunidademedida", nullable = false)
    @ManyToOne(optional = false)
    private UnidadeMedida unidademedida;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produto")
    private List<FornecedorProduto> fornecedorProdutoList;

    public Produto() {
    }

    public Produto(Integer idproduto) {
        this.idproduto = idproduto;
    }

    public Produto(Integer idproduto, String nomeproduto, Double valor) {
        this.idproduto = idproduto;
        this.nomeproduto = nomeproduto;
        this.valor = valor;
    }

    public Integer getIdproduto() {
        return idproduto;
    }

    public void setIdproduto(Integer idproduto) {
        this.idproduto = idproduto;
    }

    public String getNomeproduto() {
        return nomeproduto;
    }

    public void setNomeproduto(String nomeproduto) {
        this.nomeproduto = nomeproduto;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public TipoProduto getTipoproduto() {
        return tipoproduto;
    }

    public void setTipoproduto(TipoProduto tipoproduto) {
        this.tipoproduto = tipoproduto;
    }

    public UnidadeMedida getUnidademedida() {
        return unidademedida;
    }

    public void setUnidademedida(UnidadeMedida unidademedida) {
        this.unidademedida = unidademedida;
    }

    public List<FornecedorProduto> getFornecedorProdutoList() {
        return fornecedorProdutoList;
    }

    public void setFornecedorProdutoList(List<FornecedorProduto> fornecedorProdutoList) {
        this.fornecedorProdutoList = fornecedorProdutoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproduto != null ? idproduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.idproduto == null && other.idproduto != null) || (this.idproduto != null && !this.idproduto.equals(other.idproduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.Produto[idproduto=" + idproduto + "]";
    }

}
