/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Joaquin
 */
public class OMDBHelper {
    
    private String apiKey = "a88469a2";
    
    public String getMovieImage(String link){
        return "http://img.omdbapi.com/?apikey="+apiKey+"&i="+link;
    }
    
    public Movie getMovie(int id, String link){
        Movie m = new Movie(id);
        try {
            JSONObject json = this.readJsonFromUrl("http://www.omdbapi.com/?i="+link+"&apikey="+apiKey+"&plot=full");
            m.setTitle(json.getString("Title"));
            m.setPoster(this.getMovieImage(link));
        } catch (IOException ex) {
            Logger.getLogger(OMDBHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(OMDBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }
    
    public Movie getMovieFullInfo(int id, String link){
        Movie m = new Movie(id);
        try {
            System.out.println(link);
            JSONObject json = this.readJsonFromUrl("http://www.omdbapi.com/?i="+link+"&apikey="+apiKey+"&plot=full");
            m.setTitle(json.getString("Title"));
            m.setPoster(this.getMovieImage(link));
            //FULL INFO
            m.setActors(json.getString("Actors"));
            m.setDirector(json.getString("Director"));
            m.setPlot(json.getString("Plot"));
            m.setRuntime(json.getString("Runtime"));
            m.setYear(json.getString("Year"));
        } catch (IOException ex) {
            Logger.getLogger(OMDBHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(OMDBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }
    
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}
