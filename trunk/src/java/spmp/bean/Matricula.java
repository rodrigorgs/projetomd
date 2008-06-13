/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.bean;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Giuseppe
 */
@Entity
@Table(name = "matricula")
@NamedQueries({@NamedQuery(name = "Matricula.findByIdAluno", query = "SELECT m FROM Matricula m WHERE m.matriculaPK.idAluno = :idAluno"), @NamedQuery(name = "Matricula.findByIdTurma", query = "SELECT m FROM Matricula m WHERE m.matriculaPK.idTurma = :idTurma")})
public class Matricula implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MatriculaPK matriculaPK;

    public Matricula() {
    }

    public Matricula(MatriculaPK matriculaPK) {
        this.matriculaPK = matriculaPK;
    }

    public Matricula(String idAluno, String idTurma) {
        this.matriculaPK = new MatriculaPK(idAluno, idTurma);
    }

    public MatriculaPK getMatriculaPK() {
        return matriculaPK;
    }

    public void setMatriculaPK(MatriculaPK matriculaPK) {
        this.matriculaPK = matriculaPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matriculaPK != null ? matriculaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Matricula)) {
            return false;
        }
        Matricula other = (Matricula) object;
        if ((this.matriculaPK == null && other.matriculaPK != null) || (this.matriculaPK != null && !this.matriculaPK.equals(other.matriculaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spmp.bean.Matricula[matriculaPK=" + matriculaPK + "]";
    }

}
