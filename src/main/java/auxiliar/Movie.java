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
public class Movie {
    private int id;
    private String title;
    private String poster;
    private float rating;
    private String year;
    private String runtime;
    private String director;
    private String actors;
    private String plot;
    
    public Movie(int id){
        this.id = id;
    }
    public Movie(int id, float rating){
        this.id = id;
        this.rating=rating;
    }

    public Movie(int id, String title, String poster, float rating){
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.rating = rating;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the poster
     */
    public String getPoster() {
        return poster;
    }

    /**
     * @param poster the poster to set
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }

    /**
     * @return the rating
     */
    public float getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the runtime
     */
    public String getRuntime() {
        return runtime;
    }

    /**
     * @param runtime the runtime to set
     */
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    /**
     * @return the director
     */
    public String getDirector() {
        return director;
    }

    /**
     * @param director the director to set
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * @param actors the actors to set
     */
    public void setActors(String actors) {
        this.actors = actors;
    }

    /**
     * @return the plot
     */
    public String getPlot() {
        return plot;
    }

    /**
     * @param plot the plot to set
     */
    public void setPlot(String plot) {
        this.plot = plot;
    }

    /**
     * @return the actors
     */
    public String getActors() {
        return actors;
    }
}
