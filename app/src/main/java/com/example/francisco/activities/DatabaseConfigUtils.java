package com.example.francisco.activities;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Francisco on 30/03/2015.
 */
public class DatabaseConfigUtils extends OrmLiteConfigUtil {

    public static void main(String[] args)throws IOException, SQLException{
        writeConfigFile("ormlite_config.txt");
    }
}
