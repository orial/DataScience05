/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliar;

import auxiliar.TMDBHelper;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joaquin
 */
public class DBHelper {
    final String url = "jdbc:mysql://localhost:3306/";
    final String dbName = "recomendadordb";
    final String driver = "com.mysql.jdbc.Driver";
    final String userName = "root";
    final String password = "root";
    
    private TMDBHelper tmdbhelper = new TMDBHelper();
    //USERS
    
    public boolean existsNickname(String nickname){
        boolean res=false;
        try {
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM USER WHERE NAME = ?");
            stmt.setString(1, nickname);
            
            ResultSet rs = stmt.executeQuery();
            res=rs.next();
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("Exception: "+ e.getMessage());
        }
        return res;
    }
    
    public boolean existsEmail(String email){
        boolean res=false;
        try {
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM USER WHERE EMAIL = ?");
            stmt.setString(1, email);
            
            ResultSet rs = stmt.executeQuery();
            res=rs.next();
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("Exception: "+ e.getMessage());
        }
        return res;
    }
    
    public int insertUser(String name, String pass, String email){
        int res = 0;
        try {
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            
            PreparedStatement stmt = c.prepareStatement("INSERT INTO USER (NAME, PASSWORD, EMAIL) VALUES (?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, pass);
            stmt.setString(3, email);
            
            res = stmt.executeUpdate();
            
            stmt.close();
        }catch (Exception e) {
            System.err.println("Exception: "+ e.getMessage());
        }
        return res;
    }
    
     public String getIDUsuario(String name){
        String res="";
        try {
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            
            PreparedStatement stmt = c.prepareStatement("SELECT ID FROM USER WHERE NAME = ?");
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getString("ID");
            }
            
            rs.close();
            stmt.close();

        } catch (Exception e) {
            System.err.println("Exception: "+ e.getMessage());
        }
        return res;
    }
    public String checkLogin(String pass, String text){
        String res="";
        try {
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM USER WHERE PASSWORD = ? AND EMAIL = ?");
            stmt.setString(1, pass);
            stmt.setString(2, text);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getString("NAME");
            }
            
            rs.close();
            stmt.close();
            
            stmt = c.prepareStatement("SELECT * FROM USER WHERE PASSWORD = ? AND NAME = ?");
            stmt.setString(1, pass);
            stmt.setString(2, text);
            
            rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getString("NAME");
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("Exception: "+ e.getMessage());
        }
        return res;
    }
    
    private String getMoviePoster(String id){
        String poster = "https://i5.walmartimages.com/asr/f752abb3-1b49-4f99-b68a-7c4d77b45b40_1.39d6c524f6033c7c58bd073db1b99786.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF";
        try {
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            
            PreparedStatement stmt = c.prepareStatement("SELECT TMDBID FROM LINK WHERE MOVIEID = ?");
            stmt.setString(1, id);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                String tmdb = rs.getString("TMDBID");
                poster = tmdbhelper.getMovieImage(tmdb);
            }
            
            rs.close();
            stmt.close();
        }catch (Exception e) {
            System.err.println("Exception: "+ e.getMessage());
        }
        return poster;
    }
    
    public List<Movie> getMainMovies(){
        List <Movie> movies = new ArrayList<Movie>();
        Movie aux;
        try{
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            //Obtenemos las 8 peliculas con mejor rating medio que tengan mas de 200 valoraciones
            PreparedStatement stmt = c.prepareStatement("SELECT MOVIEID, COUNT(*) AS num_ratings, sum(RATING.RATING) AS sum_ratings, sum(RATING.RATING)/COUNT(*) AS mean_ratings FROM RATING GROUP BY RATING.MOVIEID HAVING num_ratings>200 ORDER BY mean_ratings DESC LIMIT 12");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                //Id de la pelicula
                String id = rs.getString("MOVIEID");
                //Titulo de la pelicula
                String title = tmdbhelper.getMovieTitle(this.getMovieLink(id));
                //Poster de la pelicula
                String poster = this.getMoviePoster(id);
                //Media de las valoraciones
                String media = rs.getString("mean_ratings");
                //Solo dos decimales
                media = media.substring(0, 4);
                //Creamos la pelicula
                aux = new Movie(Integer.parseInt(id), title, poster, Float.parseFloat(media));
                //Y la añadimos al set
                movies.add(aux);
            }
            
            rs.close();
            stmt.close();
        }catch (Exception e){
            System.err.println("Exception: "+ e.getMessage());
        }
        return movies;
    }
    
    public String getMovieLink(String id){
        String link="";
        try{
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            
            PreparedStatement stmt = c.prepareStatement("SELECT TMDBID FROM LINK WHERE MOVIEID = ?");
            stmt.setString(1, id);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                link = rs.getString("TMDBID");
            }
            
            rs.close();
            stmt.close();
        }catch (Exception e){
            System.err.println("Exception: "+ e.getMessage());
        }
        return link;
    }
     public Movie getMovieRanking(String movieid){
         Movie movie = new Movie(Integer.parseInt(movieid));
        try{
             Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            PreparedStatement stmt = c.prepareStatement("SELECT sum(RATING.RATING)/COUNT(*) AS mean_ratings FROM RATING  WHERE MOVIEID = ? GROUP BY RATING.MOVIEID  ORDER BY mean_ratings");
            stmt.setString(1,movieid );
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                //Id de la pelicula
                String id = movieid;
                //Media de las valoraciones
                String media = rs.getString("mean_ratings");
                //Solo dos decimales
                media = media.substring(0, 4);
                //Creamos la pelicula
                movie = new Movie(Integer.parseInt(id), Float.parseFloat(media));
            }
            rs.close();
            stmt.close();
        }catch (Exception e){
            System.err.println("Exception: "+ e.getMessage());
        }
        return movie;
    }
    public  List <String> getMoviesIDValoradasUsuario(String userid){
        List <String> IDmovies = new ArrayList<String>();
        try{
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            PreparedStatement stmt = c.prepareStatement("SELECT MOVIEID FROM RATING WHERE USERID = ?");
            stmt.setString(1,userid );
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String id = rs.getString("MOVIEID");
                IDmovies.add(id);
            }
            rs.close();
            stmt.close();
        }catch (Exception e){
            System.err.println("Exception: "+ e.getMessage());
        }
        return IDmovies;
    }
}
