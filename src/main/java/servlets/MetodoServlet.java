/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import auxiliar.DBHelper;
import auxiliar.Movie;
import auxiliar.RHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
public class MetodoServlet extends HttpServlet {
    
    private DBHelper db = new DBHelper();
    private RHelper r = new RHelper();
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
        String userid = (String)sesion.getAttribute("userid");
        String method = request.getParameter("metodo");
        db.modifyMethod(userid, method); //Se modifica el método del usuario
        List<Movie> movies;
        int[] moviesid;
        switch (method){
            case "GR": //Grupos resultantes de la clusterización
                String clusters = db.getUserClusters(userid);
                movies = db.getRecommendationByGenres(clusters);
                sesion.setAttribute("movies", movies);
                break;
            case "CO": //Filtrado basado en contenido
                moviesid = r.BasadoContenido(userid);
                movies = db.getMoviesById(moviesid);
                sesion.setAttribute("movies", movies);
                break;
            case "CU": //Filtrado colaborativo basado en usuario
                //Se comenta porque tarda mucho en tiempo de ejecución
                //r.RecalcularClusterFiltradoColaborativoUser();
                moviesid = r.FiltradoColaborativoBasadoUsuario(userid);
                movies = db.getMoviesById(moviesid);
                sesion.setAttribute("movies", movies);
                break;
            case "CI": //Filtrado colaborativo basado en item
                //Se comenta porque tarda mucho en tiempo de ejecución
                //r.RecalcularClusterFiltradoColaborativoItem();
                moviesid = r.FiltradoColaborativoBasadoItem(userid);
                movies = db.getMoviesById(moviesid);
                sesion.setAttribute("movies", movies);
                break;
            case "FS": //Aprendizaje supervisado
                moviesid = r.AprendizajeSupervisado(userid);
                movies = db.getMoviesById(moviesid);
                sesion.setAttribute("movies", movies);
                break;
            default:
                break;
        }
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/Welcome.jsp");
        rd.forward(request, response);
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
