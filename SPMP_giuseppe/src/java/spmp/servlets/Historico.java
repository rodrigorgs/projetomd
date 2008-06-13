/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spmp.servlets;

import java.io.*;

import java.util.HashMap;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import spmp.bean.Aluno;
import spmp.bean.Disciplina;
import spmp.business.BusinessException;
import spmp.business.prolog.SPMPFacade;
import spmp.business.prolog.SPMPFacade.SituacaoEnum;

/**
 *
 * @author Giuseppe
 */
public class Historico extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Aluno aluno = (Aluno) request.getSession().getAttribute("aluno");
        SPMPFacade facade = SPMPFacade.getInstance();
        String msg = "";
        HashMap<SituacaoEnum, List<Disciplina>> historico = facade.getHistorico(aluno);
        List<Disciplina> disciplinasAprovadas = historico.get(SituacaoEnum.APROVADO);
        List<Disciplina> disciplinasDesconhecido = historico.get(SituacaoEnum.DESCONHECIDO);
        List<Disciplina> disciplinasAndamento = historico.get(SituacaoEnum.EM_ANDAMENTO);


        String[] selecionadasAprovadas = request.getParameterValues("selecionadasAprovadas");
        if (selecionadasAprovadas != null && selecionadasAprovadas.length > 0) {
            try {
                facade.setResultado(aluno, selecionadasAprovadas);
                msg += "Seleção efetuada com sucesso.";
                historico = facade.getHistorico(aluno);
                disciplinasAprovadas = historico.get(SituacaoEnum.APROVADO);
                disciplinasDesconhecido = historico.get(SituacaoEnum.DESCONHECIDO);
                disciplinasAndamento = historico.get(SituacaoEnum.EM_ANDAMENTO);
         
            } catch (BusinessException ex) {
                msg = ex.getMessage();
            }
            request.setAttribute("msg", msg);
        }

        request.setAttribute("disciplinasAprovadas", disciplinasAprovadas);
        request.setAttribute("disciplinasDesconhecido", disciplinasDesconhecido);
        request.setAttribute("disciplinasAndamento", disciplinasAndamento);
        request.setAttribute("step", "Historico.jsp");
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
