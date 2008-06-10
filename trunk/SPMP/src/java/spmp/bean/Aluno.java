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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Stefani Pires
 */
@Entity
@Table(name = "aluno")
@NamedQueries({@NamedQuery(name = "Aluno.findByIdAluno", query = "SELECT a FROM Aluno a WHERE a.idAluno = :idAluno"), 
@NamedQuery(name = "Aluno.findByNome", query = "SELECT a FROM Aluno a WHERE a.nome = :nome"), 
@NamedQuery(name = "Aluno.findByEmail", query = "SELECT a FROM Aluno a WHERE a.email = :email"), 
@NamedQuery(name = "Aluno.findBySenha", query = "SELECT a FROM Aluno a WHERE a.senha = :senha"),
@NamedQuery(name = "Aluno.findByIdAlunoESenha", query = "SELECT a FROM Aluno a WHERE a.idAluno = :idAluno AND a.senha = :senha")})

public class Aluno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "idAluno", nullable = false)
    private String idAluno;
    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "senha", nullable = false)
    private String senha;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
    private Collection<PreMatricula> preMatriculaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
    private Collection<Historico> historicoCollection;

    public Aluno() {
    }

    public Aluno(String idAluno) {
        this.idAluno = idAluno;
    }

    public Aluno(String idAluno, String nome, String email, String senha) {
        this.idAluno = idAluno;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAluno != null ? idAluno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aluno)) {
            return false;
        }
        Aluno other = (Aluno) object;
        if ((this.idAluno == null && other.idAluno != null) || (this.idAluno != null && !this.idAluno.equals(other.idAluno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "spmp.bean.Aluno[idAluno=" + idAluno + "]";
    }

}
