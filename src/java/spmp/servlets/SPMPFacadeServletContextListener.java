/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import spmp.business.prolog.SPMPFacade;

/**
 * 
 * 
 * @author Giuseppe Lima - giuseppeanl@gmail.com
 * @author Stefani Pires - stefani.pires@gmail.com
 * @author Rodrigo Rocha - rodrigorgs@gmail.com
 */
public class SPMPFacadeServletContextListener implements ServletContextListener {
   
    
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext(); 
        context.log("Inicializando "+SPMPFacade.class.toString());
        SPMPFacade.getInstance(context.getRealPath("/regras.pl"));
        context.setAttribute(SPMPFacade.class.toString(), SPMPFacade.getInstance());
    }

    public void contextDestroyed(ServletContextEvent event) {
        ServletContext context = event.getServletContext(); 
        context.log("Encerrando "+SPMPFacade.class.toString());
        context.removeAttribute(SPMPFacade.class.toString());
       
    }

}
