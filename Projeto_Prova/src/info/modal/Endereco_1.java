/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.modal;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Eclesio
 */
@Entity
@Table(name = "endereco", catalog = "info", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Endereco_1.findAll", query = "SELECT e FROM Endereco_1 e"),
    @NamedQuery(name = "Endereco_1.findById", query = "SELECT e FROM Endereco_1 e WHERE e.id = :id"),
    @NamedQuery(name = "Endereco_1.findByBairro", query = "SELECT e FROM Endereco_1 e WHERE e.bairro = :bairro"),
    @NamedQuery(name = "Endereco_1.findByLogradouro", query = "SELECT e FROM Endereco_1 e WHERE e.logradouro = :logradouro"),
    @NamedQuery(name = "Endereco_1.findByNumerocasa", query = "SELECT e FROM Endereco_1 e WHERE e.numerocasa = :numerocasa"),
    @NamedQuery(name = "Endereco_1.findByRua", query = "SELECT e FROM Endereco_1 e WHERE e.rua = :rua")})
public class Endereco_1 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Column(length = 255)
    private String bairro;
    @Column(length = 255)
    private String logradouro;
    @Column(length = 255)
    private String numerocasa;
    @Column(length = 255)
    private String rua;
    @OneToMany(mappedBy = "endId")
    private Collection<Pessoa_1> pessoaCollection;

    public Endereco_1() {
    }

    public Endereco_1(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumerocasa() {
        return numerocasa;
    }

    public void setNumerocasa(String numerocasa) {
        this.numerocasa = numerocasa;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    @XmlTransient
    public Collection<Pessoa_1> getPessoaCollection() {
        return pessoaCollection;
    }

    public void setPessoaCollection(Collection<Pessoa_1> pessoaCollection) {
        this.pessoaCollection = pessoaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Endereco_1)) {
            return false;
        }
        Endereco_1 other = (Endereco_1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.modal.Endereco_1[ id=" + id + " ]";
    }
    
}
