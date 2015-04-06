package com.frojas.francisco.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.frojas.francisco.pojo.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Francisco on 24/03/2015.
 */
public class FragmentBoxOffice extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private AdapterBoxOffice adapterBoxOffice;
    private RecyclerView listMovieHits;

    public static final String KEY_MOVIES="movies";
    public static final String KEY_ID="id";
    public static final String KEY_TITLE="title";
    public static final String KEY_RELEASE_DATES="release_dates";
    public static final String KEY_THEATER="theater";
    public static final String KEY_RATINGS="ratings";
    public static final String KEY_AUDIENCE_SCORE="audience_score";
    public static final String KEY_SYNOPSIS="synopsis";
    public static final String KEY_POSTERS="posters";
    public static final String KEY_THUMBNAIL="thumbnail";
    public static final String KEY_LINKS="links";
    public static final String KEY_SELF="self";
    public static final String KEY_CAST="cast";
    public static final String KEY_REVIEWS="reviews";
    public static final String KEY_SIMILAR="similar";
    public static final String URL_BOX_OFFICE = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json";
    public static final String URL_CHAR_QUESTION = "?";
    public static final String URL_CHAR_AMEPERSAND = "&";
    public static final String URL_PARAM_API_KEY = "apikey=";
    public static final String URL_PARAM_LIMIT = "limit=";
    public static final String API_KEY_ROTTEN_TOMATOES="54wzfswsa4qmjg8hjwa64d4c";

    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public static String getRequestUrl(int limit) {

        return URL_BOX_OFFICE
                + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + API_KEY_ROTTEN_TOMATOES
                + URL_CHAR_AMEPERSAND
                + URL_PARAM_LIMIT + limit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
      //  peliculaDao = getHelper().getRuntimeExceptionDao(Pelicula.class);
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();


    }

    private void sendJsonRequest() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getRequestUrl(10),
                (String)null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listMovies=parseJSONResponse(response);
                        adapterBoxOffice.setMovieList(listMovies);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    private ArrayList<Movie> parseJSONResponse(JSONObject response) {
        ArrayList<Movie> listMovies=new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {

                JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);
                for (int i = 0; i < arrayMovies.length(); i++) {
                    JSONObject currentMovie = arrayMovies.getJSONObject(i);
                    //get the id of the current movie
                    long id = currentMovie.getLong(KEY_ID);
                    //get the title of the current movie
                    String title = currentMovie.getString(KEY_TITLE);

                    //get the date in theaters for the current movie
                    JSONObject objectReleaseDates = currentMovie.getJSONObject(KEY_RELEASE_DATES);
                    String releaseDate = null;
                    if (objectReleaseDates.has(KEY_THEATER)) {
                        releaseDate = objectReleaseDates.getString(KEY_THEATER);
                    } else {
                        releaseDate = "NA";
                    }

                    //get the audience score for the current movie
                    JSONObject objectRatings = currentMovie.getJSONObject(KEY_RATINGS);
                    int audienceScore = -1;
                    if (objectRatings.has(KEY_AUDIENCE_SCORE)) {
                        audienceScore = objectRatings.getInt(KEY_AUDIENCE_SCORE);
                    }

                    // get the synopsis of the current movie
                    String synopsis = currentMovie.getString(KEY_SYNOPSIS);

                    JSONObject objectPosters = currentMovie.getJSONObject(KEY_POSTERS);
                    String urlThumbnail = null;
                    if (objectPosters.has(KEY_THUMBNAIL)) {
                        urlThumbnail = objectPosters.getString(KEY_THUMBNAIL);
                    }
                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);
                    Date date = dateFormat.parse(releaseDate);
                    movie.setReleaseDateTheater(date);
                    movie.setAudienceScore(audienceScore);
                    movie.setSynopsis(synopsis);
                    movie.setUrlThumbnail(urlThumbnail);

                    listMovies.add(movie);

                }


            } catch (JSONException e) {

            } catch (ParseException e) {

            }
        }
        return listMovies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(com.frojas.francisco.activities.R.layout.fragment_box_office, container, false);
        listMovieHits = (RecyclerView) view.findViewById(com.frojas.francisco.activities.R.id.listMovieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);
        sendJsonRequest();
        return view;
    }

}
