/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model.entity.hibernate;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import annotations.ZKCombo;
import annotations.ZKEntity;
import annotations.ZKField;
import annotations.ZKFieldFind;
import annotations.ZKId;

/**
 *
 * @author diego
 */
@ZKEntity(label="Histórico",tipoPaginacao=1)
@Entity
@Table(name = "historico", catalog = "rastreabilidade", schema = "public")
public class Historico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @ZKId
    @ZKField(label="Código",readonly=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhistorico", nullable = false)
    private Long idhistorico;
    
    @ZKField(label="Descrição",nullable=false,maxsize=500)
    @Basic(optional = false)
    @Column(name = "descricao", nullable = false, length = 500)
    private String descricao;
    
    @ZKField(label="Descrição",nullable=false)
    @Basic(optional = false)
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;
    
    @ZKField(label="Dado Númerico",nullable=false)
    @Basic(optional = false)
    @Column(name = "dadonumerico", nullable = false)
    private Double dadonumerico;
    
    @Basic(optional = false)
    @Column(name = "irrigate", nullable = false)
    private Integer irrigate;
    
    @Basic(optional = false)
    @Column(name = "state", nullable = false)
    private Integer state;
    
    @ZKFieldFind(label="Lote de Vegetal",nullable=false,propDisplayFieldFind="idlotevegetal")
    @JoinColumn(name = "lotevegetal", referencedColumnName = "idlotevegetal", nullable = false)
    @ManyToOne(optional = false)
    private LoteVegetal lotevegetal;
    
    @ZKCombo(label="Tipo de Histórico",nullable=false,propDisplayCombo="descricao")
    @JoinColumn(name = "tipohistorico", referencedColumnName = "idtipohistorico", nullable = false)
    @ManyToOne(optional = false)
    private TipoHistorico tipohistorico;

    public Historico() {
    }

    public Historico(Long idhistorico) {
        this.idhistorico = idhistorico;
    }

    public Historico(Long idhistorico, String descricao, Date data, Double dadonumerico, Integer irrigate, Integer state) {
        this.idhistorico = idhistorico;
        this.descricao = descricao;
        this.data = data;
        this.dadonumerico = dadonumerico;
        this.irrigate = irrigate;
        this.state = state;
    }

    public Long getIdhistorico() {
        return idhistorico;
    }

    public void setIdhistorico(Long idhistorico) {
        this.idhistorico = idhistorico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getDadonumerico() {
        return dadonumerico;
    }

    public void setDadonumerico(Double dadonumerico) {
        this.dadonumerico = dadonumerico;
    }

    public Integer getIrrigate() {
        return irrigate;
    }

    public void setIrrigate(Integer irrigate) {
        this.irrigate = irrigate;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public LoteVegetal getLotevegetal() {
        return lotevegetal;
    }

    public void setLotevegetal(LoteVegetal lotevegetal) {
        this.lotevegetal = lotevegetal;
    }

    public TipoHistorico getTipohistorico() {
        return tipohistorico;
    }

    public void setTipohistorico(TipoHistorico tipohistorico) {
        this.tipohistorico = tipohistorico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhistorico != null ? idhistorico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historico)) {
            return false;
        }
        Historico other = (Historico) object;
        if ((this.idhistorico == null && other.idhistorico != null) || (this.idhistorico != null && !this.idhistorico.equals(other.idhistorico))) {
            return false;
        }
        return true;
    }

}
