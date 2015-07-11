package com.frojas.francisco.util;

import android.util.Log;

/**
 * Created by Francisco on 10/07/2015.
 */
public class L {

    public static void depuracion(String message) {
        Log.d(Constantes.NOMBRE_APLICACION, "" + message);
    }

    public static void error(String message) {
        Log.e(Constantes.NOMBRE_APLICACION, "" + message);
    }

    private L(){

    }
}
