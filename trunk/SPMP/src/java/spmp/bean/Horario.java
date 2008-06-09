/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.bean;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Stefani Pires
 */
@Entity
@Table(name = "horario")
@NamedQueries({@NamedQuery(name = "Horario.findByIdTurma", query = "SELECT h FROM Horario h WHERE h.horarioPK.idTurma = :idTurma"), 
@NamedQuery(name = "Horario.findByDiaSemana", query = "SELECT h FROM Horario h WHERE h.horarioPK.diaSemana = :diaSemana"), 
@NamedQuery(name = "Horario.findByHoraInicio", query = "SELECT h FROM Horario h WHERE h.horarioPK.horaInicio = :horaInicio"), 
@NamedQuery(name = "Horario.findByHoraFim", query = "SELECT h FROM Horario h WHERE h.horarioPK.horaFim = :horaFim")})

public class Horario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HorarioPK horarioPK;
    @JoinColumn(name = "idTurma", referencedColumnName = "idTurma", insertable = false, updatable = false)
    @ManyToOne
    private Turma turma;

    public Horario() {
    }

    public Horario(HorarioPK horarioPK) {
        this.horarioPK = horarioPK;
    }

    public Horario(String idTurma, String diaSemana, int horaInicio, int horaFim) {
        this.horarioPK = new HorarioPK(idTurma, diaSemana, horaInicio, horaFim);
    }

    public HorarioPK getHorarioPK() {
        return horarioPK;
    }

    public void setHorarioPK(HorarioPK horarioPK) {
        this.horarioPK = horarioPK;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (horarioPK != null ? horarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horario)) {
            return false;
        }
        Horario other = (Horario) object;
        if ((this.horarioPK == null && other.horarioPK != null) || (this.horarioPK != null && !this.horarioPK.equals(other.horarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spmp.bean.Horario[horarioPK=" + horarioPK + "]";
    }

}
