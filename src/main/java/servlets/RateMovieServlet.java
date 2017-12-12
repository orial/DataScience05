/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import auxiliar.DBHelper;
import auxiliar.TMDBHelper;
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
 * @author Joaquin
 */
public class RateMovieServlet extends HttpServlet {

    private DBHelper db = new DBHelper();
    private TMDBHelper tmdbhelper = new TMDBHelper();
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
        //Link de la pelicula en tmdb
        String link = db.getMovieLink(movieid);
        //Recuperamos titulo, poster, resumen y año
        String titulo = tmdbhelper.getMovieTitle(link);
        String poster = tmdbhelper.getMovieImage(link);
        String resumen = tmdbhelper.getMovieOverview(link);
        String year = tmdbhelper.getMovieYear(link);
        
        request.setAttribute("titulo", titulo);
        request.setAttribute("poster", poster);
        request.setAttribute("resumen", resumen);
        request.setAttribute("year", year);
        request.setAttribute("movieid", movieid);
        //request.setAttribute("rating",db.getMovieMeanVal(movieid));
        //request.setAttribute("myrating", db.getMovieRatedByUser(userid, movieid));
        RequestDispatcher rd;
        rd = getServletContext().getRequestDispatcher("/RateMovie.jsp");
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