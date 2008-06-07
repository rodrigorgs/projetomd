/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.dao;

import spmp.dao.data.AlunoData;

/**
 *
 * @author Stefani Pires
 */
public class ManagerDAO implements DAOFactory {

    private static ManagerDAO instance;


    private ManagerDAO() {}

    
    public static ManagerDAO getInstance() {
        if (instance == null) {
            instance = new ManagerDAO();
        }
        return instance;
    }

    public AlunoDAO createAlunoDAO() {
        return new AlunoData();
    }

}
