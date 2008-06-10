/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.business.prolog;

import jpl.Compound;
import jpl.Query;
import jpl.Term;
import jpl.Util;

/**
 *
 * @author roden
 */
public class Prolog {
    /**
     * Compoe o predicado fornecido com os termos fornecidos e retorna um
     * predicado do prolog.
     * Exemplo: comp("filho", "joao", "maria") retorna o predicado
     * filho(joao, maria).
     * @param pred
     * @param attr
     * @return
     */
    public static Compound comp(String pred, String ... attr) {
        int n = attr.length;
        Term terms[] = new Term[n];
        for (int i = 0; i < n; i++)
            terms[i] = Util.textToTerm(attr[i]);
        
        return new Compound(pred, terms);
    }
    
    public static void assertFactOnProlog(Compound fact) {
        Query q = new Query("assert", fact);
        if (!q.hasSolution())
            throw new RuntimeException("ERROR: could not assert");
    }
}