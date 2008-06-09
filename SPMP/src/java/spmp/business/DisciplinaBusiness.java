/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.business;

/**
 *
 * @author Stefani Pires
 */
public class DisciplinaBusiness {

    private static DisciplinaBusiness instance;
    
    public static DisciplinaBusiness getInstance() {
        if (instance == null) {
            instance = new DisciplinaBusiness();
        }
        return instance;
    }
    
    // adicionar métodos
}
