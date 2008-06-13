
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.servlets;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import spmp.business.BusinessException;
import spmp.business.prolog.SPMPFacade;

/**
 *
 * @author Giuseppe
 */
public class CadastrarAluno extends HttpServlet {
   
    /** 
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String login = request.getParameter("login");
        String nome = request.getParameter("nome");
        String senha = request.getParameter("senha");
        String email = request.getParameter("email");
        String csenha = request.getParameter("csenha");

        try {
           login = request.getParameter("login");
           senha = request.getParameter("senha");
           SPMPFacade.getInstance().cadastrarAluno(login, nome, email, senha, csenha);
           request.setAttribute("msg", "Cadastro realizado com sucesso.");
           request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (BusinessException e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("login", login);
            request.setAttribute("nome", nome);
            request.setAttribute("email", email);
            request.getRequestDispatcher("CadastroAluno.jsp").forward(request, response);
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
