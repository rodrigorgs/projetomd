/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Stefani Pires
 */
@Entity
@Table(name = "semestre_sugerido")
@NamedQueries({@NamedQuery(name = "SemestreSugerido.findByIdDisciplina", query = "SELECT s FROM SemestreSugerido s WHERE s.idDisciplina = :idDisciplina"), 
@NamedQuery(name = "SemestreSugerido.findBySemestre", query = "SELECT s FROM SemestreSugerido s WHERE s.semestre = :semestre"),
@NamedQuery(name = "SemestreSugerido.findAll", query = "SELECT s FROM SemestreSugerido s ORDER BY s.semestre")})

public class SemestreSugerido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "idDisciplina", nullable = false)
    private String idDisciplina;
    @Column(name = "semestre", nullable = false)
    private String semestre;
    @JoinColumn(name = "idDisciplina", referencedColumnName = "idDisciplina", insertable = false, updatable = false)
    @OneToOne
    private Disciplina disciplina;

    public SemestreSugerido() {
    }

    public SemestreSugerido(String idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public SemestreSugerido(String idDisciplina, String semestre) {
        this.idDisciplina = idDisciplina;
        this.semestre = semestre;
    }

    public String getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(String idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
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
        hash += (idDisciplina != null ? idDisciplina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SemestreSugerido)) {
            return false;
        }
        SemestreSugerido other = (SemestreSugerido) object;
        if ((this.idDisciplina == null && other.idDisciplina != null) || (this.idDisciplina != null && !this.idDisciplina.equals(other.idDisciplina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spmp.bean.SemestreSugerido[idDisciplina=" + idDisciplina + "]";
    }

}
