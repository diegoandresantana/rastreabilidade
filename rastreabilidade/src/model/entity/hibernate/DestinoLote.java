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

import annotations.ZKEntity;
import annotations.ZKField;
import annotations.ZKFieldFind;
import annotations.ZKId;

/**
 *
 * @author diego
 */
@ZKEntity(label="Destino do Lote",tipoPaginacao=1)
@Entity
@Table(name = "destinolote", catalog = "rastreabilidade", schema = "public")
public class DestinoLote implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @ZKId
    @ZKField(label="Código",readonly=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddestinolote", nullable = false)
    private Long iddestinolote;
    
    @ZKField(label="Data Destino",nullable=false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    
    @ZKFieldFind(label="Cliente",propDisplayFieldFind="nomecliente",nullable=false)
    @JoinColumn(name = "cliente", referencedColumnName = "idcliente", nullable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;
    
    @ZKFieldFind(label="Lote de Vegetal",nullable=false,propDisplayFieldFind="idlotevegetal")
    @JoinColumn(name = "lotevegetal", referencedColumnName = "idlotevegetal", nullable = false)
    @ManyToOne(optional = false)
    private LoteVegetal lotevegetal;

    public DestinoLote() {
    }

    public DestinoLote(Long iddestinolote) {
        this.iddestinolote = iddestinolote;
    }

    public Long getIddestinolote() {
        return iddestinolote;
    }

    public void setIddestinolote(Long iddestinolote) {
        this.iddestinolote = iddestinolote;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LoteVegetal getLotevegetal() {
        return lotevegetal;
    }

    public void setLotevegetal(LoteVegetal lotevegetal) {
        this.lotevegetal = lotevegetal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddestinolote != null ? iddestinolote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DestinoLote)) {
            return false;
        }
        DestinoLote other = (DestinoLote) object;
        if ((this.iddestinolote == null && other.iddestinolote != null) || (this.iddestinolote != null && !this.iddestinolote.equals(other.iddestinolote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.DestinoLote[iddestinolote=" + iddestinolote + "]";
    }

}
