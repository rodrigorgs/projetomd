/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.bean;

import java.io.Serializable;
import javax.persistence.Column;
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
@Table(name = "turma")
@NamedQueries({@NamedQuery(name = "Turma.findByIdDisciplina", query = "SELECT t FROM Turma t WHERE t.turmaPK.idDisciplina = :idDisciplina"), 
@NamedQuery(name = "Turma.findByIdTurma", query = "SELECT t FROM Turma t WHERE t.turmaPK.idTurma = :idTurma"), 
@NamedQuery(name = "Turma.findByDiaSemana", query = "SELECT t FROM Turma t WHERE t.diaSemana = :diaSemana"), 
@NamedQuery(name = "Turma.findByHoraInicio", query = "SELECT t FROM Turma t WHERE t.horaInicio = :horaInicio"), 
@NamedQuery(name = "Turma.findByHoraFim", query = "SELECT t FROM Turma t WHERE t.horaFim = :horaFim")})

public class Turma implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TurmaPK turmaPK;
    @Column(name = "diaSemana", nullable = false)
    private String diaSemana;
    @Column(name = "horaInicio", nullable = false)
    private int horaInicio;
    @Column(name = "horaFim", nullable = false)
    private int horaFim;
    @JoinColumn(name = "idDisciplina", referencedColumnName = "idDisciplina", insertable = false, updatable = false)
    @ManyToOne
    private Disciplina disciplina;

    public Turma() {
    }

    public Turma(TurmaPK turmaPK) {
        this.turmaPK = turmaPK;
    }

    public Turma(TurmaPK turmaPK, String diaSemana, int horaInicio, int horaFim) {
        this.turmaPK = turmaPK;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public Turma(String idDisciplina, String idTurma) {
        this.turmaPK = new TurmaPK(idDisciplina, idTurma);
    }

    public TurmaPK getTurmaPK() {
        return turmaPK;
    }

    public void setTurmaPK(TurmaPK turmaPK) {
        this.turmaPK = turmaPK;
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

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (turmaPK != null ? turmaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turma)) {
            return false;
        }
        Turma other = (Turma) object;
        if ((this.turmaPK == null && other.turmaPK != null) || (this.turmaPK != null && !this.turmaPK.equals(other.turmaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spmp.bean.Turma[turmaPK=" + turmaPK + "]";
    }

}
