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
@ZKEntity(label="Gerenciamento de Produtos",tipoPaginacao=1)
@Entity
@Table(name = "gerenciarproduto", catalog = "rastreabilidade", schema = "public")
public class GerenciarProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @ZKId
    @ZKField(label="Código",readonly=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idgerenciarproduto", nullable = false)
    private Long idgerenciarproduto;
    
    @ZKField(label="Descrição",nullable=false,maxsize=300)
    @Column(name = "descricao", length = 300)
    private String descricao;
    
    @ZKField(label="Data da Utilização",nullable=false)
    @Basic(optional = false)
    @Column(name = "datautilizacao", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date datautilizacao;
    
    @ZKField(label="Quantidade Utilizada",nullable=false)
    @Basic(optional = false)
    @Column(name = "quantidadeutilizada", nullable = false)
    private Double quantidadeutilizada;
    
    @ZKFieldFind(label="Lote de Vegetal",nullable=false,propDisplayFieldFind="idlotevegetal")
    @JoinColumn(name = "lotevegetal", referencedColumnName = "idlotevegetal", nullable = false)
    @ManyToOne(optional = false)
    private LoteVegetal lotevegetal;
    
    @ZKFieldFind(label="Itens da Nota",nullable=false,propDisplayFieldFind="idnotaitem")
    @JoinColumn(name = "notaitem", referencedColumnName = "idnotaitem", nullable = false)
    @ManyToOne(optional = false)
    private NotaItem notaitem;

    public GerenciarProduto() {
    }

    public GerenciarProduto(Long idgerenciarproduto) {
        this.idgerenciarproduto = idgerenciarproduto;
    }

    public GerenciarProduto(Long idgerenciarproduto, Date datautilizacao, Double quantidadeutilizada) {
        this.idgerenciarproduto = idgerenciarproduto;
        this.datautilizacao = datautilizacao;
        this.quantidadeutilizada = quantidadeutilizada;
    }

    public Long getIdgerenciarproduto() {
        return idgerenciarproduto;
    }

    public void setIdgerenciarproduto(Long idgerenciarproduto) {
        this.idgerenciarproduto = idgerenciarproduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDatautilizacao() {
        return datautilizacao;
    }

    public void setDatautilizacao(Date datautilizacao) {
        this.datautilizacao = datautilizacao;
    }

    public Double getQuantidadeutilizada() {
        return quantidadeutilizada;
    }

    public void setQuantidadeutilizada(Double quantidadeutilizada) {
        this.quantidadeutilizada = quantidadeutilizada;
    }

    public LoteVegetal getLotevegetal() {
        return lotevegetal;
    }

    public void setLotevegetal(LoteVegetal lotevegetal) {
        this.lotevegetal = lotevegetal;
    }

    public NotaItem getNotaitem() {
        return notaitem;
    }

    public void setNotaitem(NotaItem notaitem) {
        this.notaitem = notaitem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgerenciarproduto != null ? idgerenciarproduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GerenciarProduto)) {
            return false;
        }
        GerenciarProduto other = (GerenciarProduto) object;
        if ((this.idgerenciarproduto == null && other.idgerenciarproduto != null) || (this.idgerenciarproduto != null && !this.idgerenciarproduto.equals(other.idgerenciarproduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.GerenciarProduto[idgerenciarproduto=" + idgerenciarproduto + "]";
    }

}
