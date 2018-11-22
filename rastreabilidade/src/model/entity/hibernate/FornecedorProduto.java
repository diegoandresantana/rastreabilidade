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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import annotations.ZKEntity;
import annotations.ZKField;
import annotations.ZKFieldFind;
import annotations.ZKId;

/**
 *
 * @author diego
 */
@ZKEntity(label="Produtos do Fornecedor",tipoPaginacao=1)
@Entity
@Table(name = "fornecedorproduto", catalog = "rastreabilidade", schema = "public")
public class FornecedorProduto implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @Id
    @ZKId
    @ZKField(label="Código",readonly=true)
     @SequenceGenerator(name = "idfornecedorproduto", sequenceName = "fornecedorproduto_idfornecedorproduto_seq")
	@GeneratedValue(generator = "idfornecedorproduto", strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idfornecedorproduto", nullable = false)
    private Integer idfornecedorproduto;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fornecedorproduto")
    private List<NotaItem> notaItemList;
    
    @ZKFieldFind(label="Fornecedor",nullable=false,propDisplayFieldFind="nomefornecedor")
    @JoinColumn(name = "fornecedor", referencedColumnName = "idfornecedor", nullable = false)
    @ManyToOne(optional = false)
    private Fornecedor fornecedor;
    
    @ZKFieldFind(label="Produto",nullable=false,propDisplayFieldFind="nomeproduto")
    @JoinColumn(name = "produto", referencedColumnName = "idproduto", nullable = false)
    @ManyToOne(optional = false)
    private Produto produto;
    
    @ZKField(label="Avaliação do Produto",nullable=false,maxsize=250)
    @Basic(optional = false)
    @Column(name = "avaliacaoproduto", nullable = false, length = 250)
    private String avaliacaoproduto;
    public FornecedorProduto() {
    }

    public FornecedorProduto(Integer idfornecedorproduto) {
        this.idfornecedorproduto = idfornecedorproduto;
    }

    public FornecedorProduto(Integer idfornecedorproduto, String avaliacaoproduto) {
        this.idfornecedorproduto = idfornecedorproduto;
        this.avaliacaoproduto = avaliacaoproduto;
    }

    public String getAvaliacaoproduto() {
        return avaliacaoproduto;
    }

    public void setAvaliacaoproduto(String avaliacaoproduto) {
        this.avaliacaoproduto = avaliacaoproduto;
    }

    public Integer getIdfornecedorproduto() {
        return idfornecedorproduto;
    }

    public void setIdfornecedorproduto(Integer idfornecedorproduto) {
        this.idfornecedorproduto = idfornecedorproduto;
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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedorproduto != null ? idfornecedorproduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FornecedorProduto)) {
            return false;
        }
        FornecedorProduto other = (FornecedorProduto) object;
        if ((this.idfornecedorproduto == null && other.idfornecedorproduto != null) || (this.idfornecedorproduto != null && !this.idfornecedorproduto.equals(other.idfornecedorproduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.FornecedorProduto[idfornecedorproduto=" + idfornecedorproduto + "]";
    }
	public String getProdutoEFornecedor(){
		if(fornecedor==null && produto==null)return null;
		
		return fornecedor.getNomefornecedor() +" - "+produto.getNomeproduto();
		
	}
}
