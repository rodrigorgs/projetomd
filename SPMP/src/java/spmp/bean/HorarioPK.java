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
public class HorarioPK implements Serializable {
    @Column(name = "idTurma", nullable = false)
    private String idTurma;
    @Column(name = "diaSemana", nullable = false)
    private String diaSemana;
    @Column(name = "horaInicio", nullable = false)
    private int horaInicio;
    @Column(name = "horaFim", nullable = false)
    private int horaFim;

    public HorarioPK() {
    }

    public HorarioPK(String idTurma, String diaSemana, int horaInicio, int horaFim) {
        this.idTurma = idTurma;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public String getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(String idTurma) {
        this.idTurma = idTurma;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(int horaFim) {
        this.horaFim = horaFim;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTurma != null ? idTurma.hashCode() : 0);
        hash += (diaSemana != null ? diaSemana.hashCode() : 0);
        hash += (int) horaInicio;
        hash += (int) horaFim;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HorarioPK)) {
            return false;
        }
        HorarioPK other = (HorarioPK) object;
        if ((this.idTurma == null && other.idTurma != null) || (this.idTurma != null && !this.idTurma.equals(other.idTurma))) {
            return false;
        }
        if ((this.diaSemana == null && other.diaSemana != null) || (this.diaSemana != null && !this.diaSemana.equals(other.diaSemana))) {
            return false;
        }
        if (this.horaInicio != other.horaInicio) {
            return false;
        }
        if (this.horaFim != other.horaFim) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spmp.bean.HorarioPK[idTurma=" + idTurma + ", diaSemana=" + diaSemana + ", horaInicio=" + horaInicio + ", horaFim=" + horaFim + "]";
    }

}
