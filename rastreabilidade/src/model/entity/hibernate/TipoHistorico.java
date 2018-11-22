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
@ZKEntity(label="Tipo de Histórico",tipoPaginacao=1)
@Entity
@Table(name = "tipohistorico", catalog = "rastreabilidade", schema = "public")
public class TipoHistorico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @ZKId
    @ZKField(label="Código",readonly=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtipohistorico", nullable = false)
    private Integer idtipohistorico;
    
    @ZKField(label="Descrição",maxsize=300,nullable=false)
    @Basic(optional = false)
    @Column(name = "descricao", nullable = false, length = 300)
    private String descricao;
    
    @ZKCombo(label="Unidade de Medida",nullable=false,propDisplayCombo="descricao")
    @JoinColumn(name = "unidademedida", referencedColumnName = "idunidademedida", nullable = false)
    @ManyToOne(optional = false)
    private UnidadeMedida unidademedida;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipohistorico")
    private List<Historico> historicoList;

    public TipoHistorico() {
    }

    public TipoHistorico(Integer idtipohistorico) {
        this.idtipohistorico = idtipohistorico;
    }

    public TipoHistorico(Integer idtipohistorico, String descricao) {
        this.idtipohistorico = idtipohistorico;
        this.descricao = descricao;
    }

    public Integer getIdtipohistorico() {
        return idtipohistorico;
    }

    public void setIdtipohistorico(Integer idtipohistorico) {
        this.idtipohistorico = idtipohistorico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public UnidadeMedida getUnidademedida() {
        return unidademedida;
    }

    public void setUnidademedida(UnidadeMedida unidademedida) {
        this.unidademedida = unidademedida;
    }

    public List<Historico> getHistoricoList() {
        return historicoList;
    }

    public void setHistoricoList(List<Historico> historicoList) {
        this.historicoList = historicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipohistorico != null ? idtipohistorico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoHistorico)) {
            return false;
        }
        TipoHistorico other = (TipoHistorico) object;
        if ((this.idtipohistorico == null && other.idtipohistorico != null) || (this.idtipohistorico != null && !this.idtipohistorico.equals(other.idtipohistorico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.TipoHistorico[idtipohistorico=" + idtipohistorico + "]";
    }

}
