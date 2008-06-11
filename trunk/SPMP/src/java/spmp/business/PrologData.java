/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.business;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import jpl.Compound;
import jpl.JPL;
import jpl.Query;
import jpl.Term;
import jpl.Util;

public class PrologData {
    java.sql.Connection conn;
    
    public PrologData(String fileRegras) {
        try {
            JPL.init();
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spmp", "root", "");
        } catch (Exception e) {
            throw new RuntimeException("Could not connect to database");
        }
        
        fileRegras = fileRegras.replaceAll("\\\\", "\\\\\\\\");
        boolean success = Query.hasSolution(PrologUtil.comp("consult", "'" + fileRegras + "'"));
        if (!success)
            throw new RuntimeException("Could not load prolog file");
    }
    
    public String[] getTables() {
        return new String[] { "aluno", "disciplina", "historico", "horario", 
        "matricula", "pre_matricula", "prerequisito", "semestre_sugerido",
        "turma"};
    }
    
    public void load() throws SQLException {
        Statement stmt = conn.createStatement();
        for (String table : getTables()) {
            System.out.println("Table " + table);
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
            int n = rs.getMetaData().getColumnCount();
            System.out.println("n = " + n);
            Term[] terms = new Term[n];
//            rs.first();
            while (rs.next()) {
                for (int i = 1; i <= n; i++) {
                    terms[i-1] = Util.textToTerm(rs.getString(i));
//                    System.out.println("---" + rs.getString(i));
                }
                Compound fact = new Compound(table, terms);
                assertFactOnProlog(fact);
            }

        }
        
//        System.exit(0);
    }
    
    public static void assertFactOnProlog(Compound fact) {
        Query q = new Query("assert", fact);
        if (!q.hasSolution())
            throw new RuntimeException("ERROR: could not assert");
    }
    
    public static void retractFactOnProlog(Compound fact) {
        Query q = new Query("retract", fact);
        if (!q.hasSolution())
            throw new RuntimeException("ERROR: could not retract");
    }
    
    public static String join(String[] strings, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        boolean first = true;
        for (String s : strings)  {
            if (first)
                first = false;
            else
                buffer.append(delimiter);
            buffer.append(s);
        }
        return buffer.toString();
    }

    // TODO: fazer verificação pra evitar fatos duplicados.
    public void assertFact(Compound fact) throws SQLException {
        Query q = new Query("assert", fact);
        if (!q.hasSolution())
            throw new RuntimeException("ERROR: could not assert");
        
        String table = fact.name();
        int n = fact.arity();
        String[] values = new String[n];
        for (int i = 1; i <= n; i++) {
            Term term = fact.arg(i);
            if (term instanceof jpl.Integer || term instanceof jpl.Float)
                values[i-1] = term.toString();
            else 
                values[i-1] = "'" + term.toString().replaceAll("'", "\\\\'") + "'";
        }
        
        Statement stmt = conn.createStatement();
        String query = String.format("INSERT INTO %s VALUES (%s)", table, join(values, ","));
        System.out.println("---" + query);
        stmt.executeUpdate(query);
    }

    public void retractFact(Compound fact) {
        Query q = new Query("retract", fact);
        if (!q.hasSolution())
            throw new RuntimeException("ERROR: could not retract");
        //TODO: remover do banco de dados. Deve tratar termos do tipo Variable.
    }
}
