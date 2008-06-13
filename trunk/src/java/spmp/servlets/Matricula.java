/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spmp.servlets;

import java.io.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
import spmp.bean.Aluno;
import spmp.bean.Disciplina;
import spmp.bean.Turma;
import spmp.business.BusinessException;
import spmp.business.prolog.SPMPFacade;

/**
 *
 * @author Giuseppe
 */
public class Matricula extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Aluno aluno = (Aluno) request.getSession().getAttribute("aluno");
        SPMPFacade fachada = SPMPFacade.getInstance();
        String msg = "";
        HashMap<Disciplina, List<Turma>> turmasDisponiveis = fachada.getTurmasParaAluno(aluno);
        HashMap<String, Boolean> turmasSelecao = new HashMap<String, Boolean>();
        List<String> turmasSelecionadas = new LinkedList<String>();
        for (Disciplina disciplina : turmasDisponiveis.keySet()) {

            String idTurma = request.getParameter(disciplina.getIdDisciplina());
            if (idTurma != null) {
                if (!idTurma.equals("none")) {
                    turmasSelecionadas.add(idTurma);
                    turmasSelecao.put(idTurma, true);
                } else {
                    turmasSelecao.put(disciplina.getIdDisciplina(), true);
                }
            }            
        }

        if (!turmasSelecionadas.isEmpty()) {
            try {
                fachada.efetuarMatricula(aluno, turmasSelecionadas.toArray(new String[0]));
                msg = "Matricula efetuada com sucesso.";
            } catch (BusinessException ex) {
                msg = ex.getMessage();
            }
            request.setAttribute("msg", msg);
        }
        request.setAttribute("turmasSelecao", turmasSelecao);
        request.setAttribute("turmasDisponiveis", turmasDisponiveis);
        request.setAttribute("step", "Matricula.jsp");
        request.getRequestDispatcher("Planejamento.jsp").forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
