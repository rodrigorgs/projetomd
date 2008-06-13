/*
 * SPMP Project
 */

package spmp.dao.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import spmp.bean.PreMatricula;
import spmp.dao.DAOException;
import spmp.dao.PreMatriculaDAO;

/**
 * @author Giuseppe Lima - giuseppeanl@gmail.com
 * @author Rodrigo Rocha - rodrigorgs@gmail.com
 * @author Stefani Pires - stefani.pires@gmail.com
 */
public class PreMatriculaData implements PreMatriculaDAO{

    private EntityManager em;

    /**
     * No args constructor. Obtains the JPA EntityManager.
     */
    public PreMatriculaData() {
        em = Connection.getInstance().getEntityManager();
    }

    
    public void insert(PreMatricula preMatricula) throws DAOException {
         try {
            em.getTransaction().begin();
            em.persist(preMatricula);
            em.getTransaction().commit();

        } catch (Exception exception) {
            em.getTransaction().rollback();
            throw new DAOException("Falha ao cadastrar pré-matrícula.");
        }
    }

    public void delete(PreMatricula preMatricula) throws DAOException {
        try {
            em.getTransaction().begin();
            em.remove(preMatricula);
            em.getTransaction().commit();
            
        } catch (Exception exception) {
            //em.getTransaction().rollback();
            throw new DAOException("Falha ao remover pré-matricula");
            
        }
    }

    public List<PreMatricula> getPreMatriculaByAluno(String idAluno) throws DAOException {
       try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("PreMatricula.findByIdAluno");
            List<PreMatricula> list = query.setParameter("idAluno", idAluno).getResultList();
            em.getTransaction().commit();
            return list;

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DAOException(e.getMessage());
        }
    }

}
