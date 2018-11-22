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

import org.hibernate.annotations.Cascade;
import annotations.ZKEntity;
import annotations.ZKField;
import annotations.ZKFieldFind;
import annotations.ZKId;

/**
 *
 * @author diego
 */
@ZKEntity(label="Fornecedor",tipoPaginacao=1)
@Entity
@Table(name = "fornecedor", catalog = "rastreabilidade", schema = "public")
public class Fornecedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @ZKId
    @ZKField(label="Código",readonly=true)
    @SequenceGenerator(name = "idfornecedor", sequenceName = "fornecedor_idfornecedor_seq")
	@GeneratedValue(generator = "idfornecedor", strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idfornecedor", nullable = false)
    private Integer idfornecedor;
    
    @ZKField(label="Nome do Fornecedor",maxsize=100,nullable=false)
    @Basic(optional = false)
    @Column(name = "nomefornecedor", nullable = false, length = 100)
    private String nomefornecedor;
    
    @ZKField(label="Descrição",maxsize=300,nullable=false)
    @Basic(optional = false)
    @Column(name = "descricao", nullable = false, length = 300)
    private String descricao;
    
    @ZKField(label="Descrição",maxsize=150)
    @Column(name = "endereco", length = 150)
    private String endereco;
    
    @ZKField(label="Cpf/Cnpj",maxsize=20,nullable=false)
    @Column(name = "cnpj", length = 20)
    private String cnpj;
    
    @ZKFieldFind(label="Cidade",nullable=false,propDisplayFieldFind="nomecidade")
    @JoinColumn(name = "cidade", referencedColumnName = "idcidade", nullable = false)
    @ManyToOne(optional = false)
    private Cidade cidade;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fornecedor")
    @Cascade(value=org.hibernate.annotations.CascadeType.ALL)
    private List<FornecedorProduto> fornecedorProdutoList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fornecedor")
    private List<NotaFiscal> notaFiscalList;

    public Fornecedor() {
    }

    public Fornecedor(Integer idfornecedor) {
        this.idfornecedor = idfornecedor;
    }

    public Fornecedor(Integer idfornecedor, String nomefornecedor, String descricao) {
        this.idfornecedor = idfornecedor;
        this.nomefornecedor = nomefornecedor;
        this.descricao = descricao;
    }

    public Integer getIdfornecedor() {
        return idfornecedor;
    }

    public void setIdfornecedor(Integer idfornecedor) {
        this.idfornecedor = idfornecedor;
    }

    public String getNomefornecedor() {
        return nomefornecedor;
    }

    public void setNomefornecedor(String nomefornecedor) {
        this.nomefornecedor = nomefornecedor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<FornecedorProduto> getFornecedorProdutoList() {
        return fornecedorProdutoList;
    }

    public void setFornecedorProdutoList(List<FornecedorProduto> fornecedorProdutoList) {
        this.fornecedorProdutoList = fornecedorProdutoList;
    }

    public List<NotaFiscal> getNotaFiscalList() {
        return notaFiscalList;
    }

    public void setNotaFiscalList(List<NotaFiscal> notaFiscalList) {
        this.notaFiscalList = notaFiscalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfornecedor != null ? idfornecedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fornecedor)) {
            return false;
        }
        Fornecedor other = (Fornecedor) object;
        if ((this.idfornecedor == null && other.idfornecedor != null) || (this.idfornecedor != null && !this.idfornecedor.equals(other.idfornecedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.Fornecedor[idfornecedor=" + idfornecedor + "]";
    }

}
