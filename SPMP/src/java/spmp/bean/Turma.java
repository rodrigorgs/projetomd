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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Stefani Pires
 */
@Entity
@Table(name = "turma")
@NamedQueries({@NamedQuery(name = "Turma.findByIdTurma", query = "SELECT t FROM Turma t WHERE t.idTurma = :idTurma"), 
@NamedQuery(name = "Turma.findByCodTurma", query = "SELECT t FROM Turma t WHERE t.codTurma = :codTurma"),
@NamedQuery(name = "Turma.findByIdDisciplina", query = "SELECT t FROM Turma t WHERE t.idDisciplina = :idDisciplina")})

public class Turma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "idTurma", nullable = false)
    private String idTurma;
    @Column(name = "codTurma", nullable = false)
    private String codTurma;
    @JoinColumn(name = "idDisciplina", referencedColumnName = "idDisciplina")
    @ManyToOne
    private Disciplina idDisciplina;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "turma")
    private Collection<Horario> horarioCollection;

    public Turma() {
    }

    public Turma(String idTurma) {
        this.idTurma = idTurma;
    }

    public Turma(String idTurma, String codTurma) {
        this.idTurma = idTurma;
        this.codTurma = codTurma;
    }

    public String getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(String idTurma) {
        this.idTurma = idTurma;
    }

    public String getCodTurma() {
        return codTurma;
    }

    public void setCodTurma(String codTurma) {
        this.codTurma = codTurma;
    }

    public Disciplina getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Disciplina idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Collection<Horario> getHorarioCollection() {
        return horarioCollection;
    }

    public void setHorarioCollection(Collection<Horario> horarioCollection) {
        this.horarioCollection = horarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTurma != null ? idTurma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turma)) {
            return false;
        }
        Turma other = (Turma) object;
        if ((this.idTurma == null && other.idTurma != null) || (this.idTurma != null && !this.idTurma.equals(other.idTurma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spmp.bean.Turma[idTurma=" + idTurma + "]";
    }

}
