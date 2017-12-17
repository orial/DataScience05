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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

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
    
    public int insertUser(String name, String pass, String email, String clusters){
        int res = 0;
        try {
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            
            PreparedStatement stmt = c.prepareStatement("INSERT INTO USER (NAME, PASSWORD, EMAIL, CLUSTERS, TIPORECOM) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, pass);
            stmt.setString(3, email);
            stmt.setString(4, clusters);
            stmt.setString(5, "GR"); //Por defecto siempre se le recomendarán películas de los grupos que elegió en el registro
            
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
                res = rs.getString("ID");
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
            //Obtenemos las 8 peliculas con mejor rating medio que tengan 200 o más valoraciones
            PreparedStatement stmt = c.prepareStatement("SELECT MOVIEID, COUNT(*) AS num_ratings, sum(RATING.RATING) AS sum_ratings, sum(RATING.RATING)/COUNT(*) AS mean_ratings FROM RATING GROUP BY RATING.MOVIEID HAVING num_ratings>=200 ORDER BY mean_ratings DESC LIMIT 12");
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
                if(media.length()>4){
                    media = media.substring(0, 4);
                }
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
    
    public String getIMDBLink(String id){
        String link="";
        try{
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            
            PreparedStatement stmt = c.prepareStatement("SELECT IMDBID FROM LINK WHERE MOVIEID = ?");
            stmt.setString(1, id);
            
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                link = rs.getString("IMDBID");
                //Hay que completar con ceros a la izquierda hasta que el id tenga 7 dígitos y delante 'tt'
                switch (link.length()){
                    case 1:
                        link = "tt000000"+link;
                        break;
                    case 2:
                        link = "tt00000"+link;
                        break;
                    case 3:
                        link = "tt0000"+link;
                        break;
                    case 4:
                        link = "tt000"+link;
                        break;
                    case 5:
                        link = "tt00"+link;
                        break;
                    case 6:
                        link = "tt0"+link;
                        break;
                    case 7:
                        link = "tt"+link;
                        break;
                }
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
                if(media.length()>4){
                    media = media.substring(0, 4);
                }
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
    
    public int getNumberClusters(){
        int clusters=0;
        try{
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            
            PreparedStatement stmt = c.prepareStatement("SELECT COUNT(DISTINCT clusterId) AS clusters FROM inicluster");
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                clusters = rs.getInt("clusters");
            }
            
            rs.close();
            stmt.close();
        }catch (Exception e){
            System.err.println("Exception: "+ e.getMessage());
        }
        return clusters;
    }
    
    public List<Movie> getMainMoviesFromCluster(int cluster){
        List <Movie> movies = new ArrayList<Movie>();
        Movie aux;
        try{
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            PreparedStatement stmt = c.prepareStatement("SELECT ini.movieId, ini.clusterId, COUNT(*) as valoraciones, sum(rat.RATING)/COUNT(*) as media FROM inicluster AS ini JOIN rating AS rat ON (ini.movieId = rat.MOVIEID) GROUP BY ini.movieId HAVING ini.clusterId = ? AND valoraciones >= 20 ORDER BY media DESC LIMIT 5;");
            stmt.setString(1,String.valueOf(cluster));
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                //Id de la pelicula
                String id = rs.getString("ini.movieId");
                //Titulo de la pelicula
                String title = tmdbhelper.getMovieTitle(this.getMovieLink(id));
                //Poster de la pelicula
                String poster = this.getMoviePoster(id);
                //Media de las valoraciones
                String media = rs.getString("media");
                //Solo dos decimales
                if(media.length()>4){
                    media = media.substring(0, 4);
                }
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
    
    public List<Movie> getRecommendationByGenres(String clusters){
        Map<String,Integer> puntuaciones = new HashMap<String,Integer>();
        StringTokenizer st = new StringTokenizer(clusters,"-");
        String c1=st.nextToken(),c2=st.nextToken(),c3=st.nextToken();
            puntuaciones.put(c1, 4);
        if(puntuaciones.get(c2)!=null) {
        	int aux=puntuaciones.get(c2)+4;
        	puntuaciones.put(c2, aux);
        }else {
        	puntuaciones.put(c2, 4);
        }
        if(puntuaciones.get(c3)!=null) {
        	int aux=puntuaciones.get(c3)+4;
        	puntuaciones.put(c3, aux);
        }else {
        	puntuaciones.put(c3, 4);
        }
        List <Movie> movies = new ArrayList<Movie>();
        Movie aux;
        
        try{
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            
            for(Entry<String,Integer> e: puntuaciones.entrySet()){
                PreparedStatement stmt = c.prepareStatement("SELECT ini.movieId, ini.clusterId, COUNT(*) as valoraciones, sum(rat.RATING)/COUNT(*) as media FROM inicluster AS ini JOIN rating AS rat ON (ini.movieId = rat.MOVIEID) GROUP BY ini.movieId HAVING ini.clusterId = ? AND valoraciones >= 20 ORDER BY media DESC;");
                stmt.setString(1,e.getKey()); //Buscamos en ese cluster
                ResultSet rs = stmt.executeQuery();
                int cont=1;
                while(rs.next() && cont<=e.getValue()){
                    //Id de la pelicula
                    String id = rs.getString("ini.movieId");
                    //Titulo de la pelicula
                    String title = tmdbhelper.getMovieTitle(this.getMovieLink(id));
                    //Poster de la pelicula
                    String poster = this.getMoviePoster(id);
                    //Media de las valoraciones
                    String media = rs.getString("media");
                    //Solo dos decimales
                    if(media.length()>4){
                        media = media.substring(0, 4);
                    }
                    //Creamos la pelicula
                    aux = new Movie(Integer.parseInt(id), title, poster, Float.parseFloat(media));
                    //Y la añadimos al set
                    movies.add(aux);
                    cont++;
                }

                rs.close();
                stmt.close();
            }
        }catch (Exception e){
            System.err.println("Exception: "+ e.getMessage());
        }
        return movies;
    }
    
    public Float getMovieRatedByUser(String userid, String movieid){
        try{
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            PreparedStatement stmt = c.prepareStatement("SELECT * FROM RATING WHERE USERID = ? AND MOVIEID = ?");
            stmt.setString(1, userid);
            stmt.setString(2, movieid);
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                return rs.getFloat("RATING");
            }
            
            rs.close();
            stmt.close();
        }catch (Exception e){
            System.err.println("Exception: "+ e.getMessage());
        }
        return 0f;
    }
    
    public String getUserClusters(String id){
        String clusters="";
        try{
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            PreparedStatement stmt = c.prepareStatement("SELECT CLUSTERS FROM USER WHERE ID = ?");
            stmt.setString(1, id);
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                clusters = rs.getString("CLUSTERS");
            }
            
            rs.close();
            stmt.close();
        }catch (Exception e){
            System.err.println("Exception: "+ e.getMessage());
        }
        return clusters;
    }
    
    public String getUserMethod(String id){
        String method="";
        try{
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            PreparedStatement stmt = c.prepareStatement("SELECT TIPORECOM FROM USER WHERE ID = ?");
            stmt.setString(1, id);
            
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                method = rs.getString("TIPORECOM");
            }
            
            rs.close();
            stmt.close();
        }catch (Exception e){
            System.err.println("Exception: "+ e.getMessage());
        }
        return method;
    }
    
    public String getMovieMeanVal(String movieid){
        String res = "0";
        try{
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            PreparedStatement stmt = c.prepareStatement("SELECT sum(RATING.RATING)/COUNT(*) AS mean_ratings FROM RATING WHERE RATING.MOVIEID = ? GROUP BY RATING.MOVIEID");
            stmt.setString(1,movieid);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                res = rs.getString("mean_ratings");
                if(res.length()>4){
                    res = res.substring(0, 4);
                }
            }
            
            rs.close();
            stmt.close();
        }catch (Exception e){
            System.err.println("Exception: "+ e.getMessage());
        }
        return res;
    }
    
    public void modifyValoration(String userid, String movieid, String val){
        try {
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            PreparedStatement stmt;
            if(this.getMovieRatedByUser(userid, movieid)==0f){ //Si no había valoración, insertar
                stmt = c.prepareStatement("INSERT INTO RATING (USERID, MOVIEID, RATING, FECHAVALORACION) VALUES (?, ?, ?, ?)");
                stmt.setString(1, userid);
                stmt.setString(2, movieid);
                stmt.setString(3, val);
                Date date = new Date();
                String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
                System.out.println(modifiedDate);
                stmt.setString(4, modifiedDate);
                stmt.executeUpdate();
            }else{
                stmt = c.prepareStatement("UPDATE RATING SET RATING = ?, FECHAVALORACION = ? WHERE USERID = ? AND MOVIEID = ?");
                stmt.setString(1, val);
                Date date = new Date();
                String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
                stmt.setString(2, modifiedDate);
                stmt.setString(3, userid);
                stmt.setString(4, movieid);
                stmt.executeUpdate();
            }
            
            
            stmt.close();
        }catch (Exception e) {
            System.err.println("Exception: "+ e.getMessage());
        }
    }
    
    public void modifyMethod(String userid, String method){
        try {
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            PreparedStatement stmt = c.prepareStatement("UPDATE USER SET TIPORECOM = ? WHERE ID = ?");
            stmt.setString(1,method);
            stmt.setString(2, userid);
            stmt.executeUpdate();
            
            stmt.close();
        }catch (Exception e) {
            System.err.println("Exception: "+ e.getMessage());
        }
    }
    
    public int getUserValorations(String userid){
        int res = 0;
        try {
            Class.forName(driver).newInstance();
            Connection c = DriverManager.getConnection(url + dbName, userName, password);
            PreparedStatement stmt = c.prepareStatement("SELECT COUNT(*) AS Vals FROM RATING WHERE USERID = ?");
            stmt.setString(1, userid);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                res = rs.getInt("Vals");
            }
            
            rs.close();
            stmt.close();
        }catch (Exception e) {
            System.err.println("Exception: "+ e.getMessage());
        }
        return res;
    }
}
