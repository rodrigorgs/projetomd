/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.bean;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Stefani Pires
 */
@Entity
@Table(name = "disciplina")
@NamedQueries({@NamedQuery(name = "Disciplina.findByIdDisciplina", query = "SELECT d FROM Disciplina d WHERE d.idDisciplina = :idDisciplina"), 
@NamedQuery(name = "Disciplina.findByNome", query = "SELECT d FROM Disciplina d WHERE d.nome = :nome"), 
@NamedQuery(name = "Disciplina.findBySemestre", query = "SELECT d FROM Disciplina d WHERE d.semestre = :semestre")})

public class Disciplina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "idDisciplina", nullable = false)
    private String idDisciplina;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "semestre", nullable = false)
    private int semestre;
    @JoinTable(name = "prerequisito", joinColumns = {@JoinColumn(name = "idDisciplinaPre", referencedColumnName = "idDisciplina")}, inverseJoinColumns = {@JoinColumn(name = "idDisciplinaPos", referencedColumnName = "idDisciplina")})
    @ManyToMany
    private Collection<Disciplina> idDisciplinaPosCollection;
    @ManyToMany(mappedBy = "idDisciplinaPosCollection")
    private Collection<Disciplina> idDisciplinaPreCollection;
    @ManyToMany(mappedBy = "idDisciplinaCollection")
    private Collection<Aluno> idAlunoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplina")
    private Collection<Turma> turmaCollection;

    public Disciplina() {
    }

    public Disciplina(String idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Disciplina(String idDisciplina, String nome, int semestre) {
        this.idDisciplina = idDisciplina;
        this.nome = nome;
        this.semestre = semestre;
    }

    public String getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(String idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public Collection<Disciplina> getIdDisciplinaPosCollection() {
        return idDisciplinaPosCollection;
    }

    public void setIdDisciplinaPosCollection(Collection<Disciplina> idDisciplinaPosCollection) {
        this.idDisciplinaPosCollection = idDisciplinaPosCollection;
    }

    public Collection<Disciplina> getIdDisciplinaPreCollection() {
        return idDisciplinaPreCollection;
    }

    public void setIdDisciplinaPreCollection(Collection<Disciplina> idDisciplinaPreCollection) {
        this.idDisciplinaPreCollection = idDisciplinaPreCollection;
    }

    public Collection<Aluno> getIdAlunoCollection() {
        return idAlunoCollection;
    }

    public void setIdAlunoCollection(Collection<Aluno> idAlunoCollection) {
        this.idAlunoCollection = idAlunoCollection;
    }

    public Collection<Turma> getTurmaCollection() {
        return turmaCollection;
    }

    public void setTurmaCollection(Collection<Turma> turmaCollection) {
        this.turmaCollection = turmaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDisciplina != null ? idDisciplina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Disciplina)) {
            return false;
        }
        Disciplina other = (Disciplina) object;
        if ((this.idDisciplina == null && other.idDisciplina != null) || (this.idDisciplina != null && !this.idDisciplina.equals(other.idDisciplina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spmp.bean.Disciplina[idDisciplina=" + idDisciplina + "]";
    }

}
