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
import annotations.ZKTelaForte;

/**
 *
 * @author diego
 */
@ZKEntity(label="Itens da Nota",tipoTela=2,tipoPaginacao=1)
@Entity
@Table(name = "notaitem", catalog = "rastreabilidade", schema = "public")
public class NotaItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @ZKId
    @ZKField(label="Código",readonly=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnotaitem", nullable = false)
    private Long idnotaitem;
    
    @ZKField(label="Quantidade",nullable=false)
    @Basic(optional = false)
    @Column(name = "quantidade", nullable = false)
    private Double quantidade;
    
    @ZKField(label="Validade",nullable=false)
    @Column(name = "validade")
    @Temporal(TemporalType.DATE)
    private Date validade;
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notaitem")
    private List<GerenciarProduto> gerenciarProdutoList;
    
    @ZKFieldFind(label="Produtos do Fornecedor",nullable=false,propDisplayFieldFind="idfornecedorproduto")
    @JoinColumn(name = "fornecedorproduto", referencedColumnName = "idfornecedorproduto", nullable = false)
    @ManyToOne(optional = false)
    private FornecedorProduto fornecedorproduto;
    
    @ZKTelaForte
    @ZKFieldFind(label="Nota Fiscal",nullable=false,propDisplayFieldFind="idnotafiscal")
    @JoinColumn(name = "notafiscal", referencedColumnName = "idnotafiscal", nullable = false)
    @ManyToOne(optional = false)
    private NotaFiscal notafiscal;

    public NotaItem() {
    }

    public NotaItem(Long idnotaitem) {
        this.idnotaitem = idnotaitem;
    }

    public NotaItem(Long idnotaitem, Double quantidade) {
        this.idnotaitem = idnotaitem;
        this.quantidade = quantidade;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public Long getIdnotaitem() {
        return idnotaitem;
    }

    public void setIdnotaitem(Long idnotaitem) {
        this.idnotaitem = idnotaitem;
    }

    public List<GerenciarProduto> getGerenciarProdutoList() {
        return gerenciarProdutoList;
    }

    public void setGerenciarProdutoList(List<GerenciarProduto> gerenciarProdutoList) {
        this.gerenciarProdutoList = gerenciarProdutoList;
    }

    public FornecedorProduto getFornecedorproduto() {
        return fornecedorproduto;
    }

    public void setFornecedorproduto(FornecedorProduto fornecedorproduto) {
        this.fornecedorproduto = fornecedorproduto;
    }

    public NotaFiscal getNotafiscal() {
        return notafiscal;
    }

    public void setNotafiscal(NotaFiscal notafiscal) {
        this.notafiscal = notafiscal;
    }

  
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((fornecedorproduto == null) ? 0 : fornecedorproduto
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotaItem other = (NotaItem) obj;
		if (fornecedorproduto == null) {
			if (other.fornecedorproduto != null)
				return false;
		} else if (!(fornecedorproduto.getIdfornecedorproduto().intValue()==other.fornecedorproduto.getIdfornecedorproduto().intValue()))
			return false;
		return true;
	}

	@Override
    public String toString() {
        return "teste.NotaItem[idnotaitem=" + idnotaitem + "]";
    }

}
