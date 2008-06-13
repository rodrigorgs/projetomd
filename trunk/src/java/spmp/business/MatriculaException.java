/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.business;

import java.util.List;
import spmp.bean.Turma;

/**
 *
 * @author roden
 */
public class MatriculaException extends BusinessException {
    List<Turma> validas = null;
    List<Turma> invalidas = null;
    
    public MatriculaException(String error, List<Turma> validas, List<Turma> invalidas) {
        super(error);
        this.validas = validas;
        this.invalidas = invalidas;
    }
    
    public List<Turma> getTurmasValidas() {
        return validas;
    }
    
    public List<Turma> getTurmasInvalidas() {
        return invalidas;
    }
}
