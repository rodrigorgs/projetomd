/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.business;

import java.util.List;
import spmp.bean.Disciplina;

/**
 *
 * @author roden
 */
public class PreMatriculaException extends BusinessException {
    List<Disciplina> validas = null;
    List<Disciplina> invalidas = null;
    
    public PreMatriculaException(String error, List<Disciplina> validas, List<Disciplina> invalidas) {
        super(error);
        this.validas = validas;
        this.invalidas = invalidas;
    }
    
    public List<Disciplina> getDisciplinasValidas() {
        return validas;
    }
    
    public List<Disciplina> getDisciplinasInvalidas() {
        return invalidas;
    }
}
