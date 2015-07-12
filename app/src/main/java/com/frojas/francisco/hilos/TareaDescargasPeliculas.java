package com.frojas.francisco.hilos;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.frojas.francisco.abstractas.DescargaPeliculas;
import com.frojas.francisco.pojo.Pelicula;

import java.util.ArrayList;

/**
 * Created by Francisco on 12/07/2015.
 */
public class TareaDescargasPeliculas extends AsyncTask<Void, Void, ArrayList<Pelicula>> {

    private DescargaPeliculas mDescargaPeliculas;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TareaDescargasPeliculas(DescargaPeliculas mDescargaPeliculas) {
        this.mDescargaPeliculas = mDescargaPeliculas;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<Pelicula> doInBackground(Void... params) {
        return Descargas.cargarPeliculas(requestQueue);
    }

    @Override
    protected void onPostExecute(ArrayList<Pelicula> listaPeliculas) {
        if (mDescargaPeliculas != null) {
            mDescargaPeliculas.cuandoPeliculasEstenDescargadas(listaPeliculas);
        }
    }
}
