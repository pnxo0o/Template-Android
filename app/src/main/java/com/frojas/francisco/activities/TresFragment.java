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
import com.frojas.francisco.util.Constantes;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class TresFragment extends Fragment {

    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private ArrayList<Pelicula> listMovies = new ArrayList<>();

    private Adaptador adapterBoxOffice;
    private RecyclerView listMovieHits;

    public TresFragment() {

    }

    //metodos del ciclo de vida
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

        View view = inflater.inflate(com.frojas.francisco.activities.R.layout.fragment_3, container, false);
        listMovieHits = (RecyclerView) view.findViewById(com.frojas.francisco.activities.R.id.listMovieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice = new Adaptador(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);
        sendJsonRequest();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    //metodos y objetos auxuliares para mananejar el orm
    private DatabaseHelper databaseHelper = null;

    protected DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

}
