/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliar;

/**
 *
 * @author Joaquin
 */

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.model.MovieDb;

public class TMDBHelper
{
    private TmdbApi api = new TmdbApi("564bbbc79305ae0ff400251a41278d9b");
    private TmdbMovies movies = api.getMovies();
    
    public String getMovieTitle(String TMDBID){
        int tmdb = Integer.valueOf(TMDBID);
        MovieDb pelicula = movies.getMovie(tmdb, "es");
        if(pelicula!=null){
            return pelicula.getTitle();
        }else{
            pelicula = movies.getMovie(tmdb, "");
        }
        return pelicula.getTitle();
    }
    public String getMovieImage(String TMDBID){
        int tmdb = Integer.valueOf(TMDBID);
        String url="";
        MovieDb pelicula = movies.getMovie(tmdb, "es");
        if(pelicula!=null){
            url = "https://image.tmdb.org/t/p/original"+pelicula.getPosterPath();
            return url;
        }else{
            pelicula = movies.getMovie(tmdb, "");
        }
        url = "https://image.tmdb.org/t/p/original"+pelicula.getPosterPath();
        return url;
    }
    
    public String getMovieOverview(String TMDBID){
        int tmdb = Integer.valueOf(TMDBID);
        MovieDb pelicula = movies.getMovie(tmdb, "es");
        if(pelicula!=null){
            return pelicula.getOverview();
        }else{
            pelicula = movies.getMovie(tmdb, "");
        }
        return pelicula.getOverview();
    }
    
    public String getMovieYear(String TMDBID){
        int tmdb = Integer.valueOf(TMDBID);
        MovieDb pelicula = movies.getMovie(tmdb, "");
        String year = pelicula.getReleaseDate();
        return year.substring(0,4);
    }
}
