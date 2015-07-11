package com.frojas.francisco.abstractas;

import android.content.Context;

import com.frojas.francisco.persistencia.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by Francisco on 10/07/2015.
 */
public class PersistenciaServicioImpl implements PersistenciaServicio{

    protected DatabaseHelper databaseHelper = null;

    @Override
    public DatabaseHelper obtenerServicio(final Context mContexto) {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(mContexto, DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public DatabaseHelper obtenerServicio() {
        return null;
    }

    @Override
    public void cerrarServicio() {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
