/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spmp.servlets;

import java.io.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import spmp.bean.Aluno;
import spmp.bean.Disciplina;
import spmp.business.BusinessException;
import spmp.business.prolog.SPMPFacade;

/**
 *
 * @author Giuseppe
 */
public class PreMatricula extends HttpServlet {

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

        //coleta a seleção de disciplinas p/ repassar ao JSP
        HashMap<String, Boolean> disciplinasSelecao = new HashMap<String, Boolean>();

        //coleta as disciplinas nas quais o aluno pode efetuar a matrícula
        HashMap<Integer, List<Disciplina>> disciplinasDisponiveis = fachada.getDisciplinasPorSemestre(aluno);
        String[] disciplinasSelecionadas = request.getParameterValues("disciplinasSelecionadas");

        boolean success = false;
        if (disciplinasSelecionadas != null) {
            List<Disciplina> disciplinasSelecionadasList = new LinkedList<Disciplina>();
            for (String disciplina : disciplinasSelecionadas) {
                disciplinasSelecao.put(disciplina, true);
            }            
            try {
                fachada.efetuarPreMatricula(aluno, disciplinasSelecionadas);
                msg = "Pré-Matrícula efetuada com sucesso.";
                success = true;
            } catch (BusinessException e) {
                msg = e.getMessage();
                success = false;
            }
            request.setAttribute("msg", msg);
        } else {
            String[] disciplinasPre = fachada.getDisciplinasPreMatricula(aluno);
            for (String disciplina : disciplinasPre) {
                disciplinasSelecao.put(disciplina, true);
            }           
        }
      
        request.setAttribute("disciplinasSelecao", disciplinasSelecao);
        request.setAttribute("disciplinasPreRequisitos", getMapaPreRequisitos(disciplinasDisponiveis));
        request.setAttribute("disciplinasDisponiveis", disciplinasDisponiveis);
        request.setAttribute("step", "PreMatricula.jsp");
        if (success)        
            request.getRequestDispatcher("Matricula.do").forward(request, response);
        else
            request.getRequestDispatcher("Planejamento.jsp").forward(request, response);
            
    }

    private Disciplina getDisciplina(HashMap<Integer, List<Disciplina>> disciplinasDisponiveis, String disciplinaId) {
        for (List<Disciplina> disciplinasSemestre : disciplinasDisponiveis.values()) {
            for (Disciplina disciplina : disciplinasSemestre) {
                if (disciplina.getIdDisciplina().equals(disciplinaId)) {
                    return disciplina;
                }
            }
        }
        return null;
    }

    private boolean contemTodasAsDisciplinas(List<Disciplina> disciplinasSelecionadas, List<Disciplina> disciplinasPreRequisito) {
        int count = disciplinasSelecionadas.size();
        for (Disciplina disciplina1 : disciplinasSelecionadas) {
            for (Disciplina disciplina2 : disciplinasPreRequisito) {
                if (disciplina1.getIdDisciplina().equals(disciplina2)) {
                    count--;
                }
            }
        }
        return count == 1 ? true : false;
    }

    private List<Disciplina> getDisciplinasASelecionar(List<Disciplina> disciplinasSelecionadas, List<Disciplina> disciplinasPreRequisito) {
        List<Disciplina> disciplinasASelecionar = new LinkedList<Disciplina>(disciplinasPreRequisito);
        for (Disciplina disciplinaSelecionada : disciplinasSelecionadas) {
            for (Disciplina disciplinaPreRequisito : disciplinasPreRequisito) {
                if (disciplinaSelecionada.getIdDisciplina().equals(disciplinaPreRequisito.getIdDisciplina())) {
                    disciplinasASelecionar.remove(disciplinaPreRequisito);
                }
            }
        }
        return disciplinasASelecionar;
    }

    private HashMap<String, List<Disciplina>> getMapaPreRequisitos(HashMap<Integer, List<Disciplina>> disciplinasDisponiveis) {
        SPMPFacade fachada = SPMPFacade.getInstance();
        HashMap<String, List<Disciplina>> disciplinasPreRequisitos = new HashMap<String, List<Disciplina>>();
        for (List<Disciplina> disciplinas : disciplinasDisponiveis.values()) {
            for (Disciplina disciplina : disciplinas) {
                disciplinasPreRequisitos.put(disciplina.getIdDisciplina(), fachada.getPrerequisitos(disciplina));
            }
        }
        return disciplinasPreRequisitos;
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
