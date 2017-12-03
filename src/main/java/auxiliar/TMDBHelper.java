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
import java.util.List;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovies;
import info.movito.themoviedbapi.TmdbMovies.MovieMethod;
import info.movito.themoviedbapi.model.Artwork;
import info.movito.themoviedbapi.model.MovieDb;

public class TMDBHelper {
	public static String getMoviePoster(TmdbMovies movies, int filmurl) {
		
		MovieDb pelicula = movies.getMovie(filmurl, "", MovieMethod.images);
		List<Artwork> images = pelicula.getImages();
		Artwork aw = images.get(0);
		String url = "https://image.tmdb.org/t/p/original"+aw.getFilePath();
		return url;
	}
	public static void main(String[] args) {
		TmdbApi api = new TmdbApi("564bbbc79305ae0ff400251a41278d9b");
		TmdbMovies peliculas = api.getMovies();
		System.out.println("Toy Story --> "+getMoviePoster(peliculas, 862)); //Toy Story
		System.out.println("Women of '69 --> "+getMoviePoster(peliculas, 410803)); //Women of '69
	}

}
