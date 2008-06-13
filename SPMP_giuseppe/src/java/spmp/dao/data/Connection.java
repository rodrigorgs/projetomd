/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spmp.dao.data;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Stefani Pires
 */
public class Connection {

    private static Connection instance;
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private String dbName = "SPMPPU";

    private Connection() {
        this.init();
    }

    public static Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    /**
     * Obtain the JPA EntityManager instance
     * 
     * @return
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Initialize the database connection with the dbName value
     */
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory(dbName);
        entityManager = entityManagerFactory.createEntityManager();
     }
}
