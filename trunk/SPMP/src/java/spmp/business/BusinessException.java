/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.business;

/**
 *
 * @author Stefani Pires
 */
public class BusinessException extends Exception{

    public BusinessException(String error) {
        super(error);
    }
}
