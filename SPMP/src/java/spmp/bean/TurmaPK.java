/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Stefani Pires
 */
@Embeddable
public class TurmaPK implements Serializable {
    @Column(name = "idDisciplina", nullable = false)
    private String idDisciplina;
    @Column(name = "idTurma", nullable = false)
    private String idTurma;

    public TurmaPK() {
    }

    public TurmaPK(String idDisciplina, String idTurma) {
        this.idDisciplina = idDisciplina;
        this.idTurma = idTurma;
    }

    public String getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(String idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(String idTurma) {
        this.idTurma = idTurma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDisciplina != null ? idDisciplina.hashCode() : 0);
        hash += (idTurma != null ? idTurma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TurmaPK)) {
            return false;
        }
        TurmaPK other = (TurmaPK) object;
        if ((this.idDisciplina == null && other.idDisciplina != null) || (this.idDisciplina != null && !this.idDisciplina.equals(other.idDisciplina))) {
            return false;
        }
        if ((this.idTurma == null && other.idTurma != null) || (this.idTurma != null && !this.idTurma.equals(other.idTurma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spmp.bean.TurmaPK[idDisciplina=" + idDisciplina + ", idTurma=" + idTurma + "]";
    }

}
