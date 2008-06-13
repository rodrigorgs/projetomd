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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Giuseppe
 */
@Entity
@Table(name = "disciplina")
@NamedQueries({@NamedQuery(name = "Disciplina.findByIdDisciplina", query = "SELECT d FROM Disciplina d WHERE d.idDisciplina = :idDisciplina"), @NamedQuery(name = "Disciplina.findByCodDisciplina", query = "SELECT d FROM Disciplina d WHERE d.codDisciplina = :codDisciplina"), @NamedQuery(name = "Disciplina.findByNome", query = "SELECT d FROM Disciplina d WHERE d.nome = :nome")})
public class Disciplina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "idDisciplina", nullable = false)
    private String idDisciplina;
    @Column(name = "codDisciplina", nullable = false)
    private String codDisciplina;
    @Column(name = "nome", nullable = false)
    private String nome;
    @JoinTable(name = "prerequisito", joinColumns = {@JoinColumn(name = "idDisciplinaPre", referencedColumnName = "idDisciplina")}, inverseJoinColumns = {@JoinColumn(name = "idDisciplinaPos", referencedColumnName = "idDisciplina")})
    @ManyToMany
    private Collection<Disciplina> idDisciplinaPosCollection;
    @ManyToMany(mappedBy = "idDisciplinaPosCollection")
    private Collection<Disciplina> idDisciplinaPreCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDisciplina")
    private Collection<Turma> turmaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplina")
    private Collection<PreMatricula> preMatriculaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "disciplina")
    private Collection<Historico> historicoCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "disciplina")
    private SemestreSugerido semestreSugerido;

    public Disciplina() {
    }

    public Disciplina(String idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Disciplina(String idDisciplina, String codDisciplina, String nome) {
        this.idDisciplina = idDisciplina;
        this.codDisciplina = codDisciplina;
        this.nome = nome;
    }

    public String getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(String idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getCodDisciplina() {
        return codDisciplina;
    }

    public void setCodDisciplina(String codDisciplina) {
        this.codDisciplina = codDisciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Collection<Turma> getTurmaCollection() {
        return turmaCollection;
    }

    public void setTurmaCollection(Collection<Turma> turmaCollection) {
        this.turmaCollection = turmaCollection;
    }

    public Collection<PreMatricula> getPreMatriculaCollection() {
        return preMatriculaCollection;
    }

    public void setPreMatriculaCollection(Collection<PreMatricula> preMatriculaCollection) {
        this.preMatriculaCollection = preMatriculaCollection;
    }

    public Collection<Historico> getHistoricoCollection() {
        return historicoCollection;
    }

    public void setHistoricoCollection(Collection<Historico> historicoCollection) {
        this.historicoCollection = historicoCollection;
    }

    public SemestreSugerido getSemestreSugerido() {
        return semestreSugerido;
    }

    public void setSemestreSugerido(SemestreSugerido semestreSugerido) {
        this.semestreSugerido = semestreSugerido;
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
