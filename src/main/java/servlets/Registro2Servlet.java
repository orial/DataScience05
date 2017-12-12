/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import auxiliar.DBHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Joaquin
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
        HttpSession sesion = request.getSession();
        Map<Integer,Integer> puntuaciones = new HashMap<Integer,Integer>();
        int suma = 0;
        int grupos = (Integer)sesion.getAttribute("clusters");
        for(int i=1; i<=grupos; i++){
            int aux = Integer.parseInt(request.getParameter("Grupo"+i));
            puntuaciones.put(i, aux);
            suma+=aux;
        }
        
        System.out.println("SUMA -> "+suma);
        if(suma!=3){
            request.setAttribute("error", "No ha dividido correctamente los puntos");
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/Registro2.jsp");
            rd.forward(request, response);
        }else{
            String nick = (String)sesion.getAttribute("nick");
            String password = (String)sesion.getAttribute("password");
            String email = (String)sesion.getAttribute("email");
            
            String clusters = "";
            for(int i=1; i<=grupos; i++){
                int aux = puntuaciones.get(i);
                for(int j=1; j<=aux; j++){
                    if(clusters.equals("")){
                        clusters=String.valueOf(i);
                    }else{
                        clusters+="-"+String.valueOf(i);
                    }
                }
            }
            System.out.println(clusters);
            int res = db.insertUser(nick, password, email, clusters);
            
            if(res==0){
                request.setAttribute("error", "No se pudo crear el usuario");
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/Registro.jsp");
                rd.forward(request, response);
            }else{
                //Usuario creado
                request.setAttribute("ok", "Usuario "+nick+" creado correctamente");
                sesion.invalidate(); //Eliminamos los atributos de sesión
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
