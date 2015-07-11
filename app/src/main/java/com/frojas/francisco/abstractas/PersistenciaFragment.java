package com.frojas.francisco.abstractas;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.frojas.francisco.persistencia.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created by Francisco on 10/07/2015.
 * Clase Extendida de Fragment que encapsula el acceso al ORM
 */
public class PersistenciaFragment extends Fragment implements PersistenciaServicio {

    protected DatabaseHelper databaseHelper = null;

    @Override
    public void onDestroy() {
        super.onDestroy();
        cerrarServicio();
    }

    @Override
    public DatabaseHelper obtenerServicio(Context mContexto) {
        return null;
    }

    @Override
    public DatabaseHelper obtenerServicio() {
        if (databaseHelper == null) {
            databaseHelper =
                    OpenHelperManager.getHelper(getActivity(), DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void cerrarServicio() {
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
