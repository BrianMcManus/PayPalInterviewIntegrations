/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;


import Commands.Command;
import Factory.CommandFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 *
 * @author brian
 */

@WebServlet(urlPatterns={"/processRequest"}) 
public class ProcessRequest extends HttpServlet {

    private String forwardToJsp;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     */
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessRequest() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // TODO Auto-generated method stub
         processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        processRequest(request, response);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action;
        //Check action
         action = request.getParameter("action");
        if ( action != null)
        {
            
            System.out.println("Action: " + action);
       
             CommandFactory factory = new CommandFactory();
             Command command = factory.createCommand(request.getParameter("action").toLowerCase());
             
             //System.out.println("Request Id: " + request.getSession().getId());
             
 
             forwardToJsp = command.execute(request, response);
             System.out.println(forwardToJsp);
             HttpSession session = request.getSession();
             //forwardToJsp = (String) session.getAttribute("forwardToJsp");
        }
      
        
        System.out.println(forwardToJsp);
        
         RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/" + forwardToJsp);
        try {
                      
                requestDispatcher.forward(request, response);

        } catch (ServletException | IOException ex) {
            Logger.getLogger(ProcessRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }

}
