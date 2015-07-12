package com.frojas.francisco.hilos;

import com.android.volley.RequestQueue;
import com.frojas.francisco.parseadores.ParseadorPelicula;
import com.frojas.francisco.pojo.Pelicula;
import com.frojas.francisco.util.Constantes;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Francisco on 12/07/2015.
 */
public class Descargas {

    public static ArrayList<Pelicula> cargarPeliculas(RequestQueue requestQueue) {
        JSONObject response = Solicitud.solicitud(requestQueue, Constantes.getRequestUrl(15));
        return ParseadorPelicula.parseJSONResponse(response);
    }
}
