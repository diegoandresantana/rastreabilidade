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

import annotations.ZKEntity;
import annotations.ZKField;
import annotations.ZKFieldFind;
import annotations.ZKId;

/**
 *
 * @author diego
 */
@ZKEntity(label="Cliente",tipoPaginacao=1)
@Entity
@Table(name = "cliente", catalog = "rastreabilidade", schema = "public")
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @ZKId
    @ZKField(label="Código",readonly=true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcliente", nullable = false)
    private Integer idcliente;
    
    
    
    @ZKField(label="Nome",nullable=false,maxsize=100)
    @Basic(optional = false)
    @Column(name = "nomecliente", nullable = false, length = 100)
    private String nomecliente;
    
    @ZKField(label="CNPJ",nullable=false,maxsize=20)
    @Basic(optional = false)
    @Column(name = "cnpj", nullable = false, length = 20)
    private String cnpj;
    
    @ZKField(label="Endereço",nullable=false,maxsize=150)
    @Basic(optional = false)
    @Column(name = "endereco", nullable = false, length = 150)
    private String endereco;
    
    @ZKField(label="Empresa",nullable=false,maxsize=100)
    @Basic(optional = false)
    @Column(name = "empresa", nullable = false, length = 100)
    private String empresa;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<DestinoLote> destinoLoteList;
    
    @ZKFieldFind(label="Cidade",nullable=false,propDisplayFieldFind="nomecidade")
    @JoinColumn(name = "cidade", referencedColumnName = "idcidade", nullable = false)
    @ManyToOne(optional = false)
    private Cidade cidade;

    public Cliente() {
    }

    public Cliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public Cliente(Integer idcliente, String cnpj, String nomecliente, String endereco, String empresa) {
        this.idcliente = idcliente;
        this.cnpj =cnpj;
        this.nomecliente = nomecliente;
        this.endereco = endereco;
        this.empresa = empresa;
    }

    public Integer getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomecliente() {
        return nomecliente;
    }

    public void setNomecliente(String nomecliente) {
        this.nomecliente = nomecliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public List<DestinoLote> getDestinoLoteList() {
        return destinoLoteList;
    }

    public void setDestinoLoteList(List<DestinoLote> destinoLoteList) {
        this.destinoLoteList = destinoLoteList;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcliente != null ? idcliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idcliente == null && other.idcliente != null) || (this.idcliente != null && !this.idcliente.equals(other.idcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.Cliente[idcliente=" + idcliente + "]";
    }

}
