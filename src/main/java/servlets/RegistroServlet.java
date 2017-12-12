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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Joaquin
 */
@WebServlet(name = "RegistroServlet", urlPatterns = {"/RegistroServlet"})
public class RegistroServlet extends HttpServlet {
    
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
        HttpSession sesion = request.getSession();
        String nickName = request.getParameter("nickNameInput");
        String email = request.getParameter("emailInput");
        String password = request.getParameter("passwordInput");
        String rePassword = request.getParameter("rePasswordInput");
        
        String error = null;
        
        if(!nickName.isEmpty() && !email.isEmpty() && !password.isEmpty() && !rePassword.isEmpty()){
            if(!password.equals(rePassword)){
                error = "Las contraseñas deben ser iguales";
            }else if(db.existsNickname(nickName)){
                error = "Nick ya existente";
            }else if(db.existsEmail(email)){
                error = "El email proporcionado ya está en uso";
            }else{
                //Realizar el registro en Registro2Servlet.java
            }
        }else{
            error = "Complete todos los campos obligatorios";
        }
        
        if(error!=null){
            request.setAttribute("error", error);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/Registro.jsp");
            rd.forward(request, response);
        }else{
            //Siguiente paso del registro
            sesion.setAttribute("nick", nickName);
            sesion.setAttribute("email", email);
            sesion.setAttribute("password", password);
            //Hay que obtener las 5 películas con mejor valoración media de cada
            //clúster, que tengan como mínimo
            int clusters = db.getNumberClusters(); //Número de clústers
            sesion.setAttribute("clusters", clusters);
            //5 películas de cada clúster
            for(int i=1; i<=clusters; i++){
                System.out.println("");
                sesion.setAttribute("grupo"+i, db.getMainMoviesFromCluster(i));
            }
            
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/Registro2.jsp");
            rd.forward(request, response);
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
