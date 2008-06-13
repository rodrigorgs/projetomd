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
 * @author Giuseppe
 */
@Embeddable
public class PreMatriculaPK implements Serializable {
    @Column(name = "idAluno", nullable = false)
    private String idAluno;
    @Column(name = "idDisciplina", nullable = false)
    private String idDisciplina;

    public PreMatriculaPK() {
    }

    public PreMatriculaPK(String idAluno, String idDisciplina) {
        this.idAluno = idAluno;
        this.idDisciplina = idDisciplina;
    }

    public String getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }

    public String getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(String idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAluno != null ? idAluno.hashCode() : 0);
        hash += (idDisciplina != null ? idDisciplina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreMatriculaPK)) {
            return false;
        }
        PreMatriculaPK other = (PreMatriculaPK) object;
        if ((this.idAluno == null && other.idAluno != null) || (this.idAluno != null && !this.idAluno.equals(other.idAluno))) {
            return false;
        }
        if ((this.idDisciplina == null && other.idDisciplina != null) || (this.idDisciplina != null && !this.idDisciplina.equals(other.idDisciplina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spmp.bean.PreMatriculaPK[idAluno=" + idAluno + ", idDisciplina=" + idDisciplina + "]";
    }

}
