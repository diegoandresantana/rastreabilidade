/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model.entity.hibernate;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author diego
 */

@Entity

@Table(name = "unidademedida", catalog = "rastreabilidade", schema = "public")
public class UnidadeMedida implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
   
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idunidademedida", nullable = false)
    private Integer idunidademedida;
    
   
    @Column(name = "descricao", length = 300)
    private String descricao;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidademedida")
    private List<Produto> produtoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unidademedida")
    private List<TipoHistorico> tipoHistoricoList;

    public UnidadeMedida() {
    }

    public UnidadeMedida(Integer idunidademedida) {
        this.idunidademedida = idunidademedida;
    }

    public Integer getIdunidademedida() {
        return idunidademedida;
    }

    public void setIdunidademedida(Integer idunidademedida) {
        this.idunidademedida = idunidademedida;
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

    public List<TipoHistorico> getTipoHistoricoList() {
        return tipoHistoricoList;
    }

    public void setTipoHistoricoList(List<TipoHistorico> tipoHistoricoList) {
        this.tipoHistoricoList = tipoHistoricoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idunidademedida != null ? idunidademedida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadeMedida)) {
            return false;
        }
        UnidadeMedida other = (UnidadeMedida) object;
        if ((this.idunidademedida == null && other.idunidademedida != null) || (this.idunidademedida != null && !this.idunidademedida.equals(other.idunidademedida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.UnidadeMedida[idunidademedida=" + idunidademedida + "]";
    }

}
