/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.business.prolog;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import jpl.Compound;
import jpl.Query;
import jpl.Term;
import jpl.Util;
import jpl.Variable;

public class PrologData {
    java.sql.Connection conn;
    
    public PrologData(String fileRegras) {
        try {
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

    public void assertFactOnDatabase(Compound fact) throws SQLException {

        String table = fact.name();
        int n = fact.arity();
        String[] values = new String[n];
        for (int i = 1; i <= n; i++) {
            Term term = fact.arg(i);
            values[i - 1] = termToSqlLiteral(term);
        }

        Statement stmt = conn.createStatement();
        String query = String.format("INSERT INTO %s VALUES (%s)", table, join(values, ","));
        stmt.executeUpdate(query);
    }
    
    public String[] getTables() /*throws SQLException*/ {
//        ArrayList<String> tables = new ArrayList<String>();
//        DatabaseMetaData metadata = conn.getMetaData();
//        ResultSet rs = metadata.getTables(null, null, "%", null);
//        while (rs.next())
//            tables.add(rs.getString("TABLE_NAME"));
//
//        return tables.toArray(new String[] {});
        
        return new String[] { "aluno", "disciplina", "historico", "horario", 
        "matricula", "pre_matricula", "prerequisito", "semestre_sugerido",
        "turma"};
    }
    
    public void load() throws SQLException {
        Statement stmt = conn.createStatement();
        for (String table : getTables()) {
//            System.out.println("Table " + table);
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
            int n = rs.getMetaData().getColumnCount();
//            System.out.println("n = " + n);
            Term[] terms = new Term[n];
            while (rs.next()) {
                for (int i = 1; i <= n; i++)
                    terms[i-1] = Util.textToTerm(rs.getString(i));
                Compound fact = new Compound(table, terms);
                assertFactOnProlog(fact);
            }
        }
    }

    public void retractFactOnDatabase(Compound fact) throws SQLException, SQLException {

        String table = fact.name();
        Statement stmt = conn.createStatement();
        String query = "DELETE FROM " + table;

        ArrayList<String> values = new ArrayList<String>();
        String[] colNames = getColumnNames(table);
        for (int i = 1; i <= fact.arity(); i++) {
            Term term = fact.arg(i);
            if (!(term instanceof Variable)) {
                values.add(String.format("%s = %s", colNames[i - 1], termToSqlLiteral(term)));
            }
        }
        if (!values.isEmpty()) {
            query += " WHERE " + join(values.toArray(), " AND ");
        }
        stmt.executeUpdate(query);
    }
    
    private String[] getColumnNames(String table) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM " + table + " WHERE false");
        ResultSetMetaData metadata = rs.getMetaData();
        int n = metadata.getColumnCount();
        String[] names = new String[n];
        for (int i = 1; i <= n; i++)
            names[i-1] = metadata.getColumnName(i);
        return names;
    }
    
    public static void assertFactOnProlog(Compound fact) {
        Query q = new Query("assert", fact);
        if (!q.hasSolution())
            throw new RuntimeException("ERROR: could not assert");
    }
    
    public static String join(Object[] strings, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        boolean first = true;
        for (Object o : strings)  {
            String s = o.toString();
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
        assertFactOnProlog(fact);
        assertFactOnDatabase(fact);
    }

    private String termToSqlLiteral(Term term) {
        if (term instanceof jpl.Integer || term instanceof jpl.Float)
            return term.toString();
        else if (term.toString().matches("(true|false)"))
            return term.toString();
        else
            return "'" + term.toString().replaceAll("'", "\\\\'") + "'";
    }
    
    public static void retractFactOnProlog(Compound fact) {
        Query q = new Query("retract", fact);
        q.allSolutions();
//        if (!q.hasSolution())
//            throw new RuntimeException("ERROR: could not retract");
    }
    
    public void retractFact(Compound fact) throws SQLException {
        retractFactOnProlog(fact);
        retractFactOnDatabase(fact);
    }
    
//    public static void main(String[] args) throws Throwable {
//        PrologData data = new PrologData("web/regras.pl");
//        System.out.println(data.retractFact(PrologUtil.comp("aluno", "1", "1", "1", "1" )));
//    }
}
