package com.frojas.francisco.util;

/**
 * Created by Francisco on 07/07/2015.
 */
public class Constantes {

    public static final String NOMBRE_APLICACION = "Apk";

    //elementos del json que se instancian en pelicula
    public static final String KEY_MOVIES = "movies";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_RELEASE_DATES = "release_dates";
    public static final String KEY_THEATER = "theater";
    public static final String KEY_RATINGS = "ratings";
    public static final String KEY_AUDIENCE_SCORE = "audience_score";
    public static final String KEY_SYNOPSIS = "synopsis";
    public static final String KEY_POSTERS = "posters";
    public static final String KEY_THUMBNAIL = "thumbnail";

    // conexion a la api
    public static final String URL_BOX_OFFICE = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json";
    public static final String URL_CHAR_QUESTION = "?";
    public static final String URL_CHAR_AMEPERSAND = "&";
    public static final String URL_PARAM_API_KEY = "apikey=";
    public static final String URL_PARAM_LIMIT = "limit=";
    public static final String API_KEY_ROTTEN_TOMATOES = "54wzfswsa4qmjg8hjwa64d4c";

    public static String getRequestUrl(int limit) {
        return URL_BOX_OFFICE
                + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMEPERSAND
                + URL_PARAM_LIMIT + limit;
    }

    private Constantes(){

    }
}
