/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.oldbusiness;

/**
 *
 * @author Stefani Pires
 */
public class TurmaBusiness {

    private static TurmaBusiness instance;
    
    public static TurmaBusiness getInstance() {
        if (instance == null) {
            instance = new TurmaBusiness();
        }
        return instance;
    }
    
    
}
