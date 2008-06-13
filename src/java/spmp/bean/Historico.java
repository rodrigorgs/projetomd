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
 * @author Giuseppe
 */
@Entity
@Table(name = "historico")
@NamedQueries({@NamedQuery(name = "Historico.findByIdAluno", query = "SELECT h FROM Historico h WHERE h.historicoPK.idAluno = :idAluno"), @NamedQuery(name = "Historico.findByIdDisciplina", query = "SELECT h FROM Historico h WHERE h.historicoPK.idDisciplina = :idDisciplina"), @NamedQuery(name = "Historico.findByAprovado", query = "SELECT h FROM Historico h WHERE h.aprovado = :aprovado")})
public class Historico implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HistoricoPK historicoPK;
    @Column(name = "aprovado", nullable = false)
    private boolean aprovado;
    @JoinColumn(name = "idAluno", referencedColumnName = "idAluno", insertable = false, updatable = false)
    @ManyToOne
    private Aluno aluno;
    @JoinColumn(name = "idDisciplina", referencedColumnName = "idDisciplina", insertable = false, updatable = false)
    @ManyToOne
    private Disciplina disciplina;

    public Historico() {
    }

    public Historico(HistoricoPK historicoPK) {
        this.historicoPK = historicoPK;
    }

    public Historico(HistoricoPK historicoPK, boolean aprovado) {
        this.historicoPK = historicoPK;
        this.aprovado = aprovado;
    }

    public Historico(String idAluno, String idDisciplina) {
        this.historicoPK = new HistoricoPK(idAluno, idDisciplina);
    }

    public HistoricoPK getHistoricoPK() {
        return historicoPK;
    }

    public void setHistoricoPK(HistoricoPK historicoPK) {
        this.historicoPK = historicoPK;
    }

    public boolean getAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
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
        hash += (historicoPK != null ? historicoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historico)) {
            return false;
        }
        Historico other = (Historico) object;
        if ((this.historicoPK == null && other.historicoPK != null) || (this.historicoPK != null && !this.historicoPK.equals(other.historicoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spmp.bean.Historico[historicoPK=" + historicoPK + "]";
    }

}
