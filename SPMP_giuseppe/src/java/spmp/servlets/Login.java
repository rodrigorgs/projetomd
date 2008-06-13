/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spmp.servlets;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import spmp.bean.Aluno;
import spmp.business.AlunoBusiness;
import spmp.business.BusinessException;
import spmp.business.prolog.SPMPFacade;

/**
 *
 * @author Giuseppe
 */
public class Login extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String msg = null;
        String login = null;
        String senha = null;
        Aluno aluno = null;

        try {
            login = request.getParameter("login");
            senha = request.getParameter("senha");
            aluno = SPMPFacade.getInstance().login(login, senha);
        } catch (BusinessException e) {
            msg = e.getMessage();
            request.setAttribute("login", login);
            request.setAttribute("msg", msg);
        }

        if (msg != null) {            
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        } else{
            HttpSession session = request.getSession(true);
            session.setAttribute("aluno", aluno);
            response.sendRedirect("PreMatricula.do");
        }
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
