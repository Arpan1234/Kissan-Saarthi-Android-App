package com.kissansaarthi.kissansaarthi;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Zonal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SharedPreferences sharedpreferences;

    String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zonal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String message=getIntent().getExtras().getString("message");
        FragmentManager fragmentManager=getSupportFragmentManager();
        if(message.equals("Mandi"))
        {
            mTitle=getString(R.string.title1);
            fragmentManager.beginTransaction()
                    .replace(R.id.container,SelectState.newInstance())
                    .commit();
        }
        else if(message.equals("Farmer_Zone"))
        {
            mTitle=getString(R.string.title2);
            setTitle("Farmer_Zone");
            fragmentManager.beginTransaction()
                    .replace(R.id.container,Farmer_Zone.newInstance())
                    .commit();
        }
        else if(message.equals("Dealer_Zone"))
        {
            mTitle=getString(R.string.title3);
            fragmentManager.beginTransaction()
                    .replace(R.id.container,Dealer_Zone.newInstance())
                    .commit();
        }
        else if(message.equals("Weather"))
        {
            mTitle=getString(R.string.title4);
            fragmentManager.beginTransaction()
                    .replace(R.id.container,Weather.newInstance())
                    .commit();
        }
        else if(message.equals("News"))
        {
            mTitle=getString(R.string.title5);
            fragmentManager.beginTransaction()
                    .replace(R.id.container,News.newInstance())
                    .commit();
        }
        else {

        }

        setTitle(mTitle);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.zonal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=null;
        if (id == R.id.nav_mandi) {
            mTitle=getString(R.string.title1);
            fragment=new SelectState();
            fragmentManager.beginTransaction()
                    .replace(R.id.container,fragment)
                    .commit();
            setTitle(mTitle);
            // Handle the camera action
        } else if (id == R.id.nav_farmer_zone) {
            mTitle=getString(R.string.title2);
            fragment =new Farmer_Zone();
            fragmentManager.beginTransaction()
                    .replace(R.id.container,fragment)
                    .commit();
            setTitle(mTitle);

        } else if (id == R.id.nav_dealer_zone) {
            mTitle=getString(R.string.title3);
            fragment=new Dealer_Zone();
            fragmentManager.beginTransaction()
                    .replace(R.id.container,fragment)
                    .commit();
            setTitle(mTitle);

        } else if (id == R.id.nav_weather) {
            Intent i =new Intent(Zonal.this,Zonal.class);

            i.putExtra("message","Weather");
            startActivity(i);

            fragment=new Weather();
        } else if (id == R.id.nav_news) {
            mTitle=getString(R.string.title5);

            fragment=new News();

            fragmentManager.beginTransaction()
                    .replace(R.id.container,fragment)
                    .commit();
            setTitle(mTitle);
        } else if (id == R.id.nav_profile) {

        }
        else if (id == R.id.nav_contact_us) {

        }
        else if (id == R.id.nav_logout) {

            sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            Intent i= new Intent(Zonal.this,Login.class);
            startActivity(i);
            finish();

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
