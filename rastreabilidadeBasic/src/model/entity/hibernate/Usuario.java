/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model.entity.hibernate;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "usuario", catalog = "rastreabilidade", schema = "public", uniqueConstraints = {
@UniqueConstraint(columnNames = {"loginusu"})})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "idusuario", sequenceName = "usuario_idusuario_seq")
     @GeneratedValue(generator = "idusuario", strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idusuario", nullable = false)
    private Integer idusuario;
    
    
    @Basic(optional = false)
    @Column(name = "nome", nullable = false, length = 200)
    private String nome;
    

    @Basic(optional = false)
    @Column(name = "loginusu", nullable = false, length = 30)
    private String loginusu;
    
    @Basic(optional = false)
    @Column(name = "senha", nullable = false, length = 30)
    private String senha;
    
	public Usuario() {
    }

    public Usuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Usuario(Integer idusuario, String nome) {
        this.idusuario = idusuario;
        this.nome = nome;

    }

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

    
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLoginusu() {
		return loginusu;
	}

	public void setLoginusu(String loginusu) {
		this.loginusu = loginusu;
		if(loginusu!=null){
			this.loginusu=this.loginusu.toUpperCase();
			
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idusuario == null) ? 0 : idusuario.hashCode());
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
		Usuario other = (Usuario) obj;
		if (idusuario == null) {
			if (other.idusuario != null)
				return false;
		} else if (!idusuario.equals(other.idusuario))
			return false;
		return true;
	}
   

}
