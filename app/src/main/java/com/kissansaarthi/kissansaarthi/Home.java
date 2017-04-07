package com.kissansaarthi.kissansaarthi;

import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.view.View;
import android.view.ViewGroup;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ImageView iv2,iv3,iv4,iv5,iv6;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        iv2=(ImageView)findViewById(R.id.imageView2);
        iv3=(ImageView)findViewById(R.id.imageView3);
        iv4=(ImageView)findViewById(R.id.imageView4);
        iv5=(ImageView)findViewById(R.id.imageView5);
        iv6=(ImageView)findViewById(R.id.imageView6);
        iv2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i =new Intent(Home.this,Zonal.class);

                i.putExtra("message","Mandi");
                startActivity(i);


            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i =new Intent(Home.this,Zonal.class);

                i.putExtra("message","Farmer_Zone");
                startActivity(i);


            }
        });
        iv4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i =new Intent(Home.this,Zonal.class);

                i.putExtra("message","Dealer_Zone");
                startActivity(i);


            }
        });
        iv5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i =new Intent(Home.this,Zonal.class);

                i.putExtra("message","Weather");
                startActivity(i);


            }
        });
        iv6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent i =new Intent(Home.this,Zonal.class);

                i.putExtra("message","News");
                startActivity(i);


            }
        });



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
        getMenuInflater().inflate(R.menu.home, menu);
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


        if (id == R.id.nav_mandi) {

            Intent i =new Intent(Home.this,Zonal.class);

            i.putExtra("message","Mandi");
            startActivity(i);

            // Handle the camera action
        } else if (id == R.id.nav_farmer_zone) {
            Intent i =new Intent(Home.this,Zonal.class);

            i.putExtra("message","Farmer_Zone");
            startActivity(i);


        } else if (id == R.id.nav_dealer_zone) {
            Intent i =new Intent(Home.this,Zonal.class);

            i.putExtra("message","Dealer_Zone");
            startActivity(i);


        } else if (id == R.id.nav_weather) {

            Intent i =new Intent(Home.this,Zonal.class);

            i.putExtra("message","Weather");
            startActivity(i);

        } else if (id == R.id.nav_news) {

            Intent i =new Intent(Home.this,Zonal.class);

            i.putExtra("message","News");
            startActivity(i);

        } else if (id == R.id.nav_profile) {

        }
        else if (id == R.id.nav_contact_us) {

        }
        else if (id == R.id.nav_logout) {
            sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            Intent i= new Intent(Home.this,Login.class);
            startActivity(i);
            finish();


        }

       /* FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container,fragment)
                .commit();*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
