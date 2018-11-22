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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author diego
 */

@Entity
@Table(name = "uf", catalog = "rastreabilidade", schema = "public")
public class Uf implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
   
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iduf", nullable = false)
    private Integer iduf;
    
    
    @Basic(optional = false)
    @Column(name = "sigla", nullable = false, length = 2)
    private String sigla;
        
    @Column(name = "nome", length = 100)
    private String nome;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uf")
    private List<Cidade> cidadeList;

    public Uf() {
    }

    public Uf(Integer iduf) {
        this.iduf = iduf;
    }

    public Uf(Integer iduf, String sigla) {
        this.iduf = iduf;
        this.sigla = sigla;
    }

    public Integer getIduf() {
        return iduf;
    }

    public void setIduf(Integer iduf) {
        this.iduf = iduf;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Cidade> getCidadeList() {
        return cidadeList;
    }

    public void setCidadeList(List<Cidade> cidadeList) {
        this.cidadeList = cidadeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iduf != null ? iduf.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uf)) {
            return false;
        }
        Uf other = (Uf) object;
        if ((this.iduf == null && other.iduf != null) || (this.iduf != null && !this.iduf.equals(other.iduf))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.Uf[iduf=" + iduf + "]";
    }

}
