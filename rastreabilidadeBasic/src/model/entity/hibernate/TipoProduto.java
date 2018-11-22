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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "tipoproduto", catalog = "rastreabilidade", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"descricao"})})
public class TipoProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipoproduto", nullable = false)
    private Integer idtipoproduto;
    
    @Basic(optional = false)
    @Column(name = "descricao", nullable = false, length = 300)
    private String descricao;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoproduto")
    private List<Produto> produtoList;

    public TipoProduto() {
    }

    public TipoProduto(Integer idtipoproduto) {
        this.idtipoproduto = idtipoproduto;
    }

    public TipoProduto(Integer idtipoproduto, String descricao) {
        this.idtipoproduto = idtipoproduto;
        this.descricao = descricao;
    }

    public Integer getIdtipoproduto() {
        return idtipoproduto;
    }

    public void setIdtipoproduto(Integer idtipoproduto) {
        this.idtipoproduto = idtipoproduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Produto> getProdutoList() {
        return produtoList;
    }

    public void setProdutoList(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoproduto != null ? idtipoproduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoProduto)) {
            return false;
        }
        TipoProduto other = (TipoProduto) object;
        if ((this.idtipoproduto == null && other.idtipoproduto != null) || (this.idtipoproduto != null && !this.idtipoproduto.equals(other.idtipoproduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.TipoProduto[idtipoproduto=" + idtipoproduto + "]";
    }

}
