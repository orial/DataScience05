/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import auxiliar.DBHelper;
import auxiliar.Movie;
import auxiliar.OMDBHelper;
import auxiliar.TMDBHelper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Joaquin
 */
public class MovieServlet extends HttpServlet {
    
    private DBHelper db = new DBHelper();
    //private TMDBHelper tmdbhelper = new TMDBHelper();
    private OMDBHelper omdb = new OMDBHelper();
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
        String movieid = request.getParameter("movieid");
        String rating = request.getParameter("rating");
        System.out.println(rating);
        
        //Link de la pelicula en tmdb
        String link = db.getIMDBLink(movieid);
        //Recuperamos titulo, poster, resumen y año
        Movie m = omdb.getMovieFullInfo(Integer.parseInt(movieid), link);
        
        request.setAttribute("titulo", m.getTitle());
        request.setAttribute("poster", m.getPoster());
        request.setAttribute("sinopsis", m.getPlot());
        request.setAttribute("year", m.getYear());
        request.setAttribute("duracion", m.getRuntime());
        request.setAttribute("director", m.getDirector());
        request.setAttribute("reparto", m.getActors());
        request.setAttribute("rating", rating);
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/MovieDetails.jsp");
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
