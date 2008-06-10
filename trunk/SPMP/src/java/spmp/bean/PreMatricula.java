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
@Table(name = "pre_matricula")
@NamedQueries({@NamedQuery(name = "PreMatricula.findByIdAluno", query = "SELECT p FROM PreMatricula p WHERE p.preMatriculaPK.idAluno = :idAluno"), 
@NamedQuery(name = "PreMatricula.findByIdDisciplina", query = "SELECT p FROM PreMatricula p WHERE p.preMatriculaPK.idDisciplina = :idDisciplina"), 
@NamedQuery(name = "PreMatricula.findByTempo", query = "SELECT p FROM PreMatricula p WHERE p.tempo = :tempo")})

public class PreMatricula implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreMatriculaPK preMatriculaPK;
    @Column(name = "tempo", nullable = false)
    private String tempo;
    @JoinColumn(name = "idAluno", referencedColumnName = "idAluno", insertable = false, updatable = false)
    @ManyToOne
    private Aluno aluno;
    @JoinColumn(name = "idDisciplina", referencedColumnName = "idDisciplina", insertable = false, updatable = false)
    @ManyToOne
    private Disciplina disciplina;

    public PreMatricula() {
    }

    public PreMatricula(PreMatriculaPK preMatriculaPK) {
        this.preMatriculaPK = preMatriculaPK;
    }

    public PreMatricula(PreMatriculaPK preMatriculaPK, String tempo) {
        this.preMatriculaPK = preMatriculaPK;
        this.tempo = tempo;
    }

    public PreMatricula(String idAluno, String idDisciplina) {
        this.preMatriculaPK = new PreMatriculaPK(idAluno, idDisciplina);
    }

    public PreMatriculaPK getPreMatriculaPK() {
        return preMatriculaPK;
    }

    public void setPreMatriculaPK(PreMatriculaPK preMatriculaPK) {
        this.preMatriculaPK = preMatriculaPK;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
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
        hash += (preMatriculaPK != null ? preMatriculaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreMatricula)) {
            return false;
        }
        PreMatricula other = (PreMatricula) object;
        if ((this.preMatriculaPK == null && other.preMatriculaPK != null) || (this.preMatriculaPK != null && !this.preMatriculaPK.equals(other.preMatriculaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spmp.bean.PreMatricula[preMatriculaPK=" + preMatriculaPK + "]";
    }

}
