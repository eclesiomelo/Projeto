/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.modal;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eclesio
 */
@Entity
@Table(name = "sequence", catalog = "info", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sequence_1.findAll", query = "SELECT s FROM Sequence_1 s"),
    @NamedQuery(name = "Sequence_1.findBySeqName", query = "SELECT s FROM Sequence_1 s WHERE s.seqName = :seqName"),
    @NamedQuery(name = "Sequence_1.findBySeqCount", query = "SELECT s FROM Sequence_1 s WHERE s.seqCount = :seqCount")})
public class Sequence_1 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SEQ_NAME", nullable = false, length = 50)
    private String seqName;
    @Column(name = "SEQ_COUNT")
    private BigInteger seqCount;

    public Sequence_1() {
    }

    public Sequence_1(String seqName) {
        this.seqName = seqName;
    }

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    public BigInteger getSeqCount() {
        return seqCount;
    }

    public void setSeqCount(BigInteger seqCount) {
        this.seqCount = seqCount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (seqName != null ? seqName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sequence_1)) {
            return false;
        }
        Sequence_1 other = (Sequence_1) object;
        if ((this.seqName == null && other.seqName != null) || (this.seqName != null && !this.seqName.equals(other.seqName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "info.modal.Sequence_1[ seqName=" + seqName + " ]";
    }
    
}
