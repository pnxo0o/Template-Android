package com.frojas.francisco.hilos;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.frojas.francisco.logger.L;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Francisco on 12/07/2015.
 */
public class Solicitud {

    public static JSONObject solicitud(RequestQueue requestQueue, String url) {
        JSONObject response = null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url,
                (String)null, requestFuture, requestFuture);

        requestQueue.add(request);
        try {
            response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            L.depuracion(e + "");
        } catch (ExecutionException e) {
            L.depuracion(e + "");
        } catch (TimeoutException e) {
            L.depuracion(e + "");
        }
        return response;
    }
}
