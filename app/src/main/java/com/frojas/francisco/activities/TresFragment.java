package com.frojas.francisco.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.frojas.francisco.abstractas.DescargaPeliculas;
import com.frojas.francisco.abstractas.PersistenciaFragment;
import com.frojas.francisco.hilos.TareaDescargasPeliculas;
import com.frojas.francisco.hilos.VolleySingleton;
import com.frojas.francisco.parseadores.ParseadorPelicula;
import com.frojas.francisco.pojo.Pelicula;
import com.frojas.francisco.util.Constantes;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TresFragment extends PersistenciaFragment implements SwipeRefreshLayout.OnRefreshListener,DescargaPeliculas {

    private RequestQueue requestQueue;
    private AdaptadorPelicula mAdaptadoPelicula;
    private RecyclerView recyclerListaPeliculas;
    private SwipeableRecyclerViewTouchListener swipeTouchListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public TresFragment() {

    }

    //metodos del ciclo de vida
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.frojas.francisco.activities.R.layout.fragment_3, container, false);
        recyclerListaPeliculas = (RecyclerView) view.findViewById(com.frojas.francisco.activities.R.id.listMovieHits);
        recyclerListaPeliculas.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeMovieHits);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mAdaptadoPelicula = new AdaptadorPelicula(getActivity());
        recyclerListaPeliculas.setAdapter(mAdaptadoPelicula);
        //efectuamos la descarga
        //sendJsonRequest();

        if (savedInstanceState != null) {
            mAdaptadoPelicula.setPeliculaList(obtenerPeliculasBD());
        } else {
            mAdaptadoPelicula.setPeliculaList(obtenerPeliculasBD());
            if (obtenerPeliculasBD().isEmpty()) {
                new TareaDescargasPeliculas(this).execute();
            }
        }

        //swipe de recyclerview
        swipeTouchListener = new SwipeableRecyclerViewTouchListener(recyclerListaPeliculas,
                new SwipeableRecyclerViewTouchListener.SwipeListener() {
                    @Override
                    public boolean canSwipe(int position) {
                        return true;
                    }

                    @Override
                    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            eliminarPelicula(position);
                        }
                        mAdaptadoPelicula.notifyDataSetChanged();
                    }

                    @Override
                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            eliminarPelicula(position);
                        }
                        mAdaptadoPelicula.notifyDataSetChanged();
                    }
                });

        recyclerListaPeliculas.addOnItemTouchListener(swipeTouchListener);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VolleySingleton volleySingleton;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    //metodo auxiliar para eliminar una pelicula
    public void eliminarPelicula(int posicion){
        eliminarPeliculaDB(mAdaptadoPelicula.getListPeliculas().get(posicion));
        mAdaptadoPelicula.getListPeliculas().remove(posicion);
        mAdaptadoPelicula.notifyItemRemoved(posicion);
    }

    //metodos de la bd
    public void agregarPeliculasBD(List<Pelicula> listaPeliculas){
        for(Pelicula itemPelicula : listaPeliculas){
            RuntimeExceptionDao<Pelicula, Long> dao = obtenerServicio().obtenerPeliculaRuntimeDao();
            dao.createOrUpdate(itemPelicula);
        }
    }

    public ArrayList<Pelicula> obtenerPeliculasBD(){
        ArrayList<Pelicula> listaPeliculas;
        RuntimeExceptionDao<Pelicula, Long> dao = obtenerServicio().obtenerPeliculaRuntimeDao();
        listaPeliculas = (ArrayList<Pelicula>)dao.queryForAll();
        return listaPeliculas;
    }

    public void eliminarPeliculaDB(Pelicula pelicula){
        RuntimeExceptionDao<Pelicula, Long> dao = obtenerServicio().obtenerPeliculaRuntimeDao();
        dao.delete(pelicula);
    }

    @Override
    public void onRefresh() {
        new TareaDescargasPeliculas(this).execute();
    }

    @Override
    public void cuandoPeliculasEstenDescargadas(ArrayList<Pelicula> listMovies) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        agregarPeliculasBD(listMovies);
        mAdaptadoPelicula.setPeliculaList(obtenerPeliculasBD());
    }

}
