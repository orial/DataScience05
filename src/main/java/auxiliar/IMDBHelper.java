package auxiliar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import org.json.*;
/**
 *
 * @author Martin
 */
public class IMDBHelper {
    
    public static String getMovieImage(String IMDBID)
    {
        String image = new String();
        BufferedReader in = null;
        try
        {
           //read from URL
            URL url = new URL("http://www.theimdbapi.org/api/movie?movie_id=tt" + IMDBID);
            URLConnection urlc;
            urlc = url.openConnection();
            urlc.addRequestProperty("user-agent", "Firefox");
            in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String inputLine;

            //Convert the stream into String
            int i=0;
            String line;
            String str = new String();
            while((line = in.readLine()) != null){
                str += line;
                i++; }
        
            //Parse JSON object
            JSONObject movie = new JSONObject(str);
            JSONObject poster = movie.getJSONObject("poster");
            image = poster.getString("thumb");
        }
        catch (IOException | JSONException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                in.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return(image);
    }

}