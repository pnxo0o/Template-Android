package com.frojas.francisco.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.frojas.francisco.abstractas.PersistenciaFragment;
import com.frojas.francisco.hilos.VolleySingleton;
import com.frojas.francisco.parseadores.ParseadorPelicula;
import com.frojas.francisco.pojo.Pelicula;
import com.frojas.francisco.util.Constantes;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TresFragment extends PersistenciaFragment {

    private RequestQueue requestQueue;
    private ArrayList<Pelicula> listMovies = new ArrayList<>();

    private Adaptador adapterBoxOffice;
    private RecyclerView listMovieHits;

    public TresFragment() {

    }

    //metodos del ciclo de vida
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.frojas.francisco.activities.R.layout.fragment_3, container, false);
        listMovieHits = (RecyclerView) view.findViewById(com.frojas.francisco.activities.R.id.listMovieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice = new Adaptador(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);
        //efectuamos la descarga
        sendJsonRequest();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VolleySingleton volleySingleton;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    private void sendJsonRequest() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                Constantes.getRequestUrl(10),
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

    //metodo para agregar o actualizar datos a la bd
    public void agregarPeliculasBD(List<Pelicula> listaPeliculas){
        for(Pelicula itemPelicula : listaPeliculas){
            RuntimeExceptionDao<Pelicula, Long> dao = obtenerServicio().obtenerPeliculaRuntimeDao();
            dao.createOrUpdate(itemPelicula);
        }
    }

    public ArrayList<Pelicula> obtenerPeliculasBD(){
        ArrayList<Pelicula> listaPeliculas;
        databaseHelper = obtenerServicio();
        RuntimeExceptionDao<Pelicula, Long> dao = obtenerServicio().obtenerPeliculaRuntimeDao();
        listaPeliculas = (ArrayList<Pelicula>)dao.queryForAll();
        return listaPeliculas;
    }

}
