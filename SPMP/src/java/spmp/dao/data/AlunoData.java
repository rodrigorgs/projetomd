/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spmp.dao.data;

import javax.persistence.EntityManager;
import spmp.bean.Aluno;
import spmp.dao.AlunoDAO;
import spmp.dao.DAOException;
import javax.persistence.Query;
/**
 *
 * @author Stefani Pires
 */
public class AlunoData implements AlunoDAO {

    private EntityManager em;

    /**
     * No args constructor. Obtains the JPA EntityManager.
     */
    public AlunoData() {
        em = Connection.getInstance().getEntityManager();
    }

    public void insert(Aluno aluno) throws DAOException {
        try {
            em.getTransaction().begin();
            em.persist(aluno);
            em.getTransaction().commit();

        } catch (Exception exception) {
            em.getTransaction().rollback();
            throw new DAOException("Falha ao cadastrar aluno.");
        }
    }

    public void update(Aluno aluno) throws DAOException {
        try {
            em.getTransaction().begin();
            em.merge(aluno);
            em.getTransaction().commit();

        } catch (Exception exception) {
            throw new DAOException("Falha ao atualizar aluno.");
        }

    }

    public void delete(Aluno aluno) throws DAOException {
        try {
            em.getTransaction().begin();
            em.remove(aluno);
            em.getTransaction().commit();
            
        } catch (Exception exception) {
            //em.getTransaction().rollback();
            throw new DAOException("Falha ao remover aluno");
            
        }
    }

    public Aluno getAluno(String id, String senha) throws DAOException {
        try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Aluno.findByIdAlunoESenha");
            return (Aluno) query.setParameter("id", id).setParameter("senha", senha).getSingleResult();

        } catch (Exception exception) {
            throw new DAOException("Aluno não cadastrado.");

        } finally {
            em.getTransaction().commit();
        }
        
    }
}
