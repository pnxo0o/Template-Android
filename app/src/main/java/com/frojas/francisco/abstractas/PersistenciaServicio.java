package com.frojas.francisco.abstractas;

import android.content.Context;

import com.frojas.francisco.persistencia.DatabaseHelper;

/**
 * Created by Francisco on 10/07/2015.
 */
public interface PersistenciaServicio {

    DatabaseHelper obtenerServicio(final Context mContexto);

    DatabaseHelper obtenerServicio();

    void cerrarServicio();
}
