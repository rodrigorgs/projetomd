/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.dao.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import spmp.bean.Disciplina;
import spmp.bean.SemestreSugerido;
import spmp.dao.DAOException;
import spmp.dao.DisciplinaDAO;

/**
 *
 * @author Stefani Pires
 */
public class DisciplinaData implements DisciplinaDAO{
    private EntityManager em;

    /**
     * No args constructor. Obtains the JPA EntityManager.
     */
    public DisciplinaData() {
        em = Connection.getInstance().getEntityManager();
    }


    public List<Disciplina> getDisciplinas() throws DAOException {
        try {
            em.getTransaction().begin();
            List<Disciplina> list = em.createNamedQuery("Disciplina.findAll").getResultList();
            em.getTransaction().commit();
            return list;

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DAOException(e.getMessage());
        }
    }

    public Disciplina getDiciplina(String id) throws DAOException {
         try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Disciplina.findByIdDisciplina");
            return (Disciplina) query.setParameter("id", id).getSingleResult();

        } catch (Exception exception) {
            throw new DAOException("Disciplina desconhecida");

        } finally {
            em.getTransaction().commit();
        }
    }

    public List<SemestreSugerido> getDisciplinasBySemestre() throws DAOException {
        try {
            em.getTransaction().begin();
            List<SemestreSugerido> list = em.createNamedQuery("SemestreSugerido.findAll").getResultList();
            em.getTransaction().commit();
            return list;

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DAOException(e.getMessage());
        }
    }

    public void insertDisciplina(Disciplina disc) {
        throw new UnsupportedOperationException("Not supported.");
    }

}
