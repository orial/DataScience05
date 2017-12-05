/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import auxiliar.DBHelper;
import auxiliar.IMDBHelper;
import auxiliar.TMDBHelper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

/**
 *
 * @author Martin
 */
public class ViewGuestServlet extends HttpServlet {

    private DBHelper db = new DBHelper();
    private IMDBHelper imdb = new IMDBHelper();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    private void HTML_header_jspf(PrintWriter out)
    {
        out.println("\"<link type=\"text/css\" href=\"css/search.css\"/>");
        out.println("<nav class=\"navbar navbar-inverse\">");
        out.println("<div class=\"container-fluid\">");
        out.println("<div class=\"navbar-header\">");
        out.println("<a href=\"Home.jsp\" class=\"navbar-brand\">Movie Recommender</a>");
        out.println("</div>");
        out.println("<ul class=\"nav navbar-nav navbar-right\">");
        out.println("<li><a href=\"Registro.jsp\"><span class=\"glyphicon glyphicon-user\"></span> Registrarse</a></li>");
        out.println("<li><a href=\"Login.jsp\"><span class=\"glyphicon glyphicon-log-in\"></span> Iniciar sesión</a></li>");
        out.println("</ul>");
        out.println("</div>");
        out.println("</nav>");
    }
    
    
    private void HTML_heading(PrintWriter out)
    {
        //out.println("<%@taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>");
        //out.println("<%@page contentType=\"text/html\" pageEncoding=\"UTF-8\"%>");
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">");
        out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>");
        out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>");
        out.println("<title>Movie Recommender</title>");
        out.println("</head>");
        out.println("<body>");
    }
    
    
    private void HTML_closing(PrintWriter out)
    {
        out.println("</body>");
        out.println("</html>");
    }
    

    private void showRating(PrintWriter out, float rating)
    {
        out.println("<div class=\"panel-body\">");
        int stars = (int)(rating + 0.5);
        for (int i=0; i<stars; i++)
        {
            out.println("<img src=\"https://cdn2.iconfinder.com/data/icons/crystalproject/crystal_project_256x256/apps/keditbookmarks.png\" style=\"width:24px; height:24px\">");
        }
        out.println("</div>");
    }
    
    private void showMovie(PrintWriter out, String title, String TMDBID, float rating)
    {
        String poster = TMDBHelper.getMovieImage(TMDBID);
        //String poster = imdb.getMovieImage(IMDBID);
        String image = "<img src=\"" + poster + "\" style=\"width:170px; height:200px\">";
        out.println("<div style=\"width:220px; align:center; float:left; padding-left:20px\">");
        out.println("<div class=\"panel panel-primary\" style=\"width:200px\">");
        out.println("<div class=\"panel-footer\" style=\"height:80px\"><h4>" + title + "</h4></div>");
        out.println("<div class=\"panel-body\">" + image + "</div>");
        showRating(out, rating);
        out.println("</div>");
        out.println("</div>");
    }
    
    
    private void showMovies(PrintWriter out)
    {
        try
        {
        ResultSet rs=db.getRecommendedMovies();
            while (rs.next())
            {
                String title=rs.getString("TITLE");
                String tmdb=rs.getString("TMDBID");
                float rating=rs.getFloat("RATING");
                showMovie(out, title, tmdb, rating);
            }
           
        db.closeDB();
        
        } catch (Exception e) {
            System.err.println("Exception: "+ e.getMessage());
        }
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter())
        {
            HTML_heading(out);
            HTML_header_jspf(out);
            showMovies(out);
            HTML_closing(out);
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
