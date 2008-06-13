/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spmp.business;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Giuseppe
 */
public class ValidaUtils {

    public static boolean isFilled(String field) {
        return field != null && !field.equals("");
    }

    public static boolean isValidEmail(String email) {
        email = email.replaceAll("'", "");
        Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher m = p.matcher(email);
        return m.find();   
    }
    public static String quote(String s) {
        return "'" + s + "'";
    }
    /**
     * Tira as aspas simples que envolvem a String passada como parametro.
     * @param o O objeto é convertido para String.
     * @return
     */
    public static String unquote(Object o) {
        String s = o.toString();
        if (s.charAt(0) == '\'')
            return s.substring(1, s.length() - 1);
        else
            return s;
    }
}
