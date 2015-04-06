package com.frojas.francisco.activities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.frojas.francisco.pojo.Pelicula;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Francisco on 30/03/2015.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    private static final String NOMBRE_BD = "peliculas.db";
    public static final int VERSION_BD = 1;

    private Dao<Pelicula, Long> peliculaDAO=null;
    private RuntimeExceptionDao<Pelicula, Long> peliculaRuntimeDAO=null;

    public DatabaseHelper(Context context){
        //super(context, NOMBRE_BD, null, VERSION_BD, com.frojas.francisco.activities.R.raw.ormlite_config);
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, Pelicula.class);
        }catch(SQLException e){
            Log.e(DatabaseHelper.class.getSimpleName(), "Imposible crear la bd");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try{
            TableUtils.dropTable(connectionSource, Pelicula.class, true);
            onCreate(database,connectionSource);
        }catch(SQLException e){
            Log.e(DatabaseHelper.class.getSimpleName(), "Imposible eliminar la bd");
            throw new RuntimeException(e);
        }
    }

    public Dao<Pelicula, Long> obtenerPeliculaDao() throws  SQLException{
        if(peliculaDAO == null) peliculaDAO = getDao(Pelicula.class);
        return peliculaDAO;
    }

    public RuntimeExceptionDao<Pelicula, Long> obtenerPeliculaRuntimeDao(){
        if(peliculaRuntimeDAO == null) peliculaRuntimeDAO = getRuntimeExceptionDao(Pelicula.class);
        return peliculaRuntimeDAO;
    }

    @Override
    public void close(){
        super.close();
        peliculaDAO=null;
    }
}
