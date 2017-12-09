/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import auxiliar.DBHelper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Programar
 */
public class Registro2Servlet extends HttpServlet {
    
    private DBHelper db = new DBHelper();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int g1 = Integer.parseInt(request.getParameter("Grupo1"));
        int g2 = Integer.parseInt(request.getParameter("Grupo2"));
        int g3 = Integer.parseInt(request.getParameter("Grupo3"));
        int g4 = Integer.parseInt(request.getParameter("Grupo4"));
        int g5 = Integer.parseInt(request.getParameter("Grupo5"));
        int g6 = Integer.parseInt(request.getParameter("Grupo6"));
        
        int suma = g1 + g2 + g3 + g4 + g5 + g6;
        System.out.println("SUMA -> "+suma);
        if(suma!=3){
            request.setAttribute("error", "No ha dividido correctamente los puntos");
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/Registro2.jsp");
            rd.forward(request, response);
        }else{
            HttpSession sesion = request.getSession();
            String nick = (String)sesion.getAttribute("nick");
            String password = (String)sesion.getAttribute("password");
            String email = (String)sesion.getAttribute("email");
            
            int res = db.insertUser(nick, password, email);
            if(res==0){
                request.setAttribute("error", "No se pudo crear el usuario");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/Registro2.jsp");
                rd.forward(request, response);
            }else{
                //Usuario creado
                request.setAttribute("ok", "Usuario "+nick+" creado correctamente");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/Login.jsp");
                rd.forward(request, response);
            }
            
            
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
