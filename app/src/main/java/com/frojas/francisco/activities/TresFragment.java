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
import com.frojas.francisco.hilos.VolleySingleton;
import com.frojas.francisco.parseadores.ParseadorPelicula;
import com.frojas.francisco.persistencia.DatabaseHelper;
import com.frojas.francisco.pojo.Pelicula;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TresFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private ArrayList<Pelicula> listMovies = new ArrayList<>();

    private Adaptador adapterBoxOffice;
    private RecyclerView listMovieHits;


    public static final String URL_BOX_OFFICE = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json";
    public static final String URL_CHAR_QUESTION = "?";
    public static final String URL_CHAR_AMEPERSAND = "&";
    public static final String URL_PARAM_API_KEY = "apikey=";
    public static final String URL_PARAM_LIMIT = "limit=";
    public static final String API_KEY_ROTTEN_TOMATOES = "54wzfswsa4qmjg8hjwa64d4c";

    public TresFragment() {

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
                        listMovies= ParseadorPelicula.parseJSONResponse(response);
                        agregarPeliculasBD(listMovies);
                        adapterBoxOffice.setPeliculaList(listMovies);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                adapterBoxOffice.setPeliculaList(obtenerPeliculasBD());
            }
        });
        requestQueue.add(request);
    }

    //metodo para agregar datos a la bd
    public void agregarPeliculasBD(List<Pelicula> listaPeliculas){
        for(Pelicula itemPelicula : listaPeliculas){
            RuntimeExceptionDao<Pelicula, Long> dao = getHelper().obtenerPeliculaRuntimeDao();
            dao.createOrUpdate(itemPelicula);
        }
    }

    public ArrayList<Pelicula> obtenerPeliculasBD(){
        ArrayList<Pelicula> listaPeliculas = null;
        databaseHelper = getHelper();
        RuntimeExceptionDao<Pelicula, Long> dao = getHelper().obtenerPeliculaRuntimeDao();
        listaPeliculas = (ArrayList<Pelicula>)dao.queryForAll();
        return listaPeliculas;
    }

    private DatabaseHelper databaseHelper = null;

    protected DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(com.frojas.francisco.activities.R.layout.fragment_box_office, container, false);
        listMovieHits = (RecyclerView) view.findViewById(com.frojas.francisco.activities.R.id.listMovieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice = new Adaptador(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);
        sendJsonRequest();
        return view;
    }

}
