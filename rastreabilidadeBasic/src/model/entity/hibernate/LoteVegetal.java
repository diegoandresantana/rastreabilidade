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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author diego
 */

@Entity
@Table(name = "lotevegetal", catalog = "rastreabilidade", schema = "public")
public class LoteVegetal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id   
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "idlotevegetal", sequenceName = "lotevegetal_idlotevegetal_seq")
    @GeneratedValue(generator = "idlotevegetal", strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "idlotevegetal", nullable = false)
    private Long idlotevegetal;
    
   
    @Basic(optional = false)
    @Column(name = "tipovegetal", nullable = false, length = 60)
    private String tipovegetal;
    
    
    @Column(name = "inicioplantio")
    @Temporal(TemporalType.DATE)
    private Date inicioplantio;
    
    
    @Column(name = "finalcolheita")
    @Temporal(TemporalType.DATE)
    private Date finalcolheita;
    
      
    @JoinColumn(name = "setorplantio", referencedColumnName = "idsetorplantio", nullable = false)
    @ManyToOne(optional = false)
    private SetorPlantio setorplantio;
    
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lotevegetal")
    private List<Historico> historicoList;

    public LoteVegetal() {
    }

    public LoteVegetal(Long idlotevegetal) {
        this.idlotevegetal = idlotevegetal;
    }

    public LoteVegetal(Long idlotevegetal, String tipovegetal) {
        this.idlotevegetal = idlotevegetal;
        this.tipovegetal = tipovegetal;
       
    }

    public Long getIdlotevegetal() {
        return idlotevegetal;
    }

    public void setIdlotevegetal(Long idlotevegetal) {
        this.idlotevegetal = idlotevegetal;
    }

    public String getTipovegetal() {
        return tipovegetal;
    }

    public void setTipovegetal(String tipovegetal) {
        this.tipovegetal = tipovegetal;
    }

    public Date getInicioplantio() {
        return inicioplantio;
    }

    public void setInicioplantio(Date inicioplantio) {
        this.inicioplantio = inicioplantio;
    }

    public Date getFinalcolheita() {
        return finalcolheita;
    }

    public void setFinalcolheita(Date finalcolheita) {
        this.finalcolheita = finalcolheita;
    }

 

  

    public SetorPlantio getSetorplantio() {
        return setorplantio;
    }

    public void setSetorplantio(SetorPlantio setorplantio) {
        this.setorplantio = setorplantio;
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
        hash += (idlotevegetal != null ? idlotevegetal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoteVegetal)) {
            return false;
        }
        LoteVegetal other = (LoteVegetal) object;
        if ((this.idlotevegetal == null && other.idlotevegetal != null) || (this.idlotevegetal != null && !this.idlotevegetal.equals(other.idlotevegetal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "teste.LoteVegetal[idlotevegetal=" + idlotevegetal + "]";
    }

}
