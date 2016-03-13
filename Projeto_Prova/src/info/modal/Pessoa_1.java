/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.modal;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eclesio
 */
@Entity
@Table(name = "pessoa", catalog = "info", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pessoa_1.findAll", query = "SELECT p FROM Pessoa_1 p"),
    @NamedQuery(name = "Pessoa_1.findById", query = "SELECT p FROM Pessoa_1 p WHERE p.id = :id"),
    @NamedQuery(name = "Pessoa_1.findByIdade", query = "SELECT p FROM Pessoa_1 p WHERE p.idade = :idade"),
    @NamedQuery(name = "Pessoa_1.findByNome", query = "SELECT p FROM Pessoa_1 p WHERE p.nome = :nome")})
public class Pessoa_1 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    private Integer idade;
    @Column(length = 255)
    private String nome;
    @JoinColumn(name = "END_ID", referencedColumnName = "ID")
    @ManyToOne
    private Endereco_1 endId;

    public Pessoa_1() {
    }

    public Pessoa_1(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco_1 getEndId() {
        return endId;
    }

    public void setEndId(Endereco_1 endId) {
        this.endId = endId;
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
        if (!(object instanceof Pessoa_1)) {
            return false;
        }
        Pessoa_1 other = (Pessoa_1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.modal.Pessoa_1[ id=" + id + " ]";
    }
    
}
