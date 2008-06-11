/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.oldbusiness;

/**
 *
 * @author Stefani Pires
 */
public class MatriculaBusiness {

    private static MatriculaBusiness instance;
    
    public static MatriculaBusiness getInstance() {
        if (instance == null) {
            instance = new MatriculaBusiness();
        }
        return instance;
    }
}
