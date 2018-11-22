/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model.entity.hibernate;

import java.io.Serializable;
import java.util.Date;
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
@ZKEntity(label="Nota Fiscal",tipoTela=0,tipoPaginacao=1)
@Entity
@Table(name = "notafiscal", catalog = "rastreabilidade", schema = "public")
public class NotaFiscal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @ZKId
    @ZKField(label="Código",readonly=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnotafiscal", nullable = false)
    private Long idnotafiscal;
    
    @ZKField(label="Data de Emissão",nullable=false)
    @Basic(optional = false)
    @Column(name = "dataemissao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataemissao;
    
    @ZKField(label="Data de Entrada",nullable=false)
    @Basic(optional = false)
    @Column(name = "dataentrada", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataentrada;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notafiscal")
    private List<NotaItem> notaItemList;
    
    @ZKFieldFind(label="Fornecedor",nullable=false,propDisplayFieldFind="nomefornecedor")
    @JoinColumn(name = "fornecedor", referencedColumnName = "idfornecedor", nullable = false)
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;

    public NotaFiscal() {
    }

    public NotaFiscal(Long idnotafiscal) {
        this.idnotafiscal = idnotafiscal;
    }

    public NotaFiscal(Long idnotafiscal, Date dataemissao, Date dataentrada) {
        this.idnotafiscal = idnotafiscal;
        this.dataemissao = dataemissao;
        this.dataentrada = dataentrada;
    }

    public Long getIdnotafiscal() {
        return idnotafiscal;
    }

    public void setIdnotafiscal(Long idnotafiscal) {
        this.idnotafiscal = idnotafiscal;
    }

    public Date getDataemissao() {
        return dataemissao;
    }

    public void setDataemissao(Date dataemissao) {
        this.dataemissao = dataemissao;
    }

    public Date getDataentrada() {
        return dataentrada;
    }

    public void setDataentrada(Date dataentrada) {
        this.dataentrada = dataentrada;
    }

    public List<NotaItem> getNotaItemList() {
        return notaItemList;
    }

    public void setNotaItemList(List<NotaItem> notaItemList) {
        this.notaItemList = notaItemList;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnotafiscal != null ? idnotafiscal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotaFiscal)) {
            return false;
        }
        NotaFiscal other = (NotaFiscal) object;
        if ((this.idnotafiscal == null && other.idnotafiscal != null) || (this.idnotafiscal != null && !this.idnotafiscal.equals(other.idnotafiscal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.NotaFiscal[idnotafiscal=" + idnotafiscal + "]";
    }

}
