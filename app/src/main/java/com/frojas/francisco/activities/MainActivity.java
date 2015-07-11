package com.frojas.francisco.activities;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.frojas.francisco.activities.R.layout.activity_main_appbar);

        toolbar = (Toolbar)findViewById(com.frojas.francisco.activities.R.id.app_bars);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawer = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(com.frojas.francisco.activities.R.id.fragment_navigation_drawer);

        drawer.setUp(com.frojas.francisco.activities.R.id.fragment_navigation_drawer,(DrawerLayout) findViewById(com.frojas.francisco.activities.R.id.drawer_layout), toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.frojas.francisco.activities.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == com.frojas.francisco.activities.R.id.action_settings) {
            Toast.makeText(this,"Click"+item.getTitle(),Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
