package com.frojas.francisco.parseadores;

import com.frojas.francisco.pojo.Pelicula;
import com.frojas.francisco.util.Constantes;
import com.frojas.francisco.util.L;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Francisco on 07/07/2015.
 */
public class ParseadorPelicula {

    public static ArrayList<Pelicula> parseJSONResponse(JSONObject response) {
        ArrayList<Pelicula> listMovies=new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                JSONArray arrayMovies = response.getJSONArray(Constantes.KEY_MOVIES);
                for (int i = 0; i < arrayMovies.length(); i++) {
                    JSONObject currentMovie = arrayMovies.getJSONObject(i);
                    //get the id of the current movie
                    long id = currentMovie.getLong(Constantes.KEY_ID);
                    //get the title of the current movie
                    String title = currentMovie.getString(Constantes.KEY_TITLE);

                    //get the date in theaters for the current movie
                    JSONObject objectReleaseDates = currentMovie.getJSONObject(Constantes.KEY_RELEASE_DATES);
                    String releaseDate;
                    if (objectReleaseDates.has(Constantes.KEY_THEATER)) {
                        releaseDate = objectReleaseDates.getString(Constantes.KEY_THEATER);
                    } else {
                        releaseDate = "NA";
                    }

                    //get the audience score for the current movie
                    JSONObject objectRatings = currentMovie.getJSONObject(Constantes.KEY_RATINGS);
                    int audienceScore = -1;
                    if (objectRatings.has(Constantes.KEY_AUDIENCE_SCORE)) {
                        audienceScore = objectRatings.getInt(Constantes.KEY_AUDIENCE_SCORE);
                    }

                    // get the synopsis of the current movie
                    String synopsis = currentMovie.getString(Constantes.KEY_SYNOPSIS);

                    JSONObject objectPosters = currentMovie.getJSONObject(Constantes.KEY_POSTERS);
                    String urlThumbnail = null;
                    if (objectPosters.has(Constantes.KEY_THUMBNAIL)) {
                        urlThumbnail = objectPosters.getString(Constantes.KEY_THUMBNAIL);
                    }


                    Pelicula movie = new Pelicula();
                    movie.setId(id);
                    movie.setTitle(title);
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
                    movie.setReleaseDateTheater(date);
                    movie.setAudienceScore(audienceScore);
                    movie.setSynopsis(synopsis);
                    movie.setUrlThumbnail(urlThumbnail);

                    listMovies.add(movie);
                }

            } catch (JSONException e) {
                L.error(e.toString());
            } catch (ParseException e) {
                L.error(e.toString());
            }
        }
        return listMovies;
    }
}
