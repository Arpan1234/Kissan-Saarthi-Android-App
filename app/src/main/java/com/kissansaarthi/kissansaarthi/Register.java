package com.kissansaarthi.kissansaarthi;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Register extends AppCompatActivity {
    EditText rname,rphone,rpassword,rcity,rstate;

    Context ctx=this;
    String rName, rPassword,rPhone,rCity,rState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rname=(EditText)findViewById(R.id.editTextUserName1);
        rphone=(EditText)findViewById(R.id.editTextPhone);
        rpassword=(EditText)findViewById(R.id.editTextPassword1);
        rcity=(EditText)findViewById(R.id.editTextCity);
        rstate=(EditText)findViewById(R.id.editTextState);
        rname.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(rname, InputMethodManager.SHOW_IMPLICIT);

    }

    public void register_register(View v){
        rName = rname.getText().toString().trim();
        if(TextUtils.isEmpty(rName)) {
            rname.setError("Enter your name");
            return;
        }
        rPassword = rpassword.getText().toString().trim();
        if(TextUtils.isEmpty(rPassword)) {
            rpassword.setError("Enter password");
            return;
        }
        rPhone = rphone.getText().toString().trim();
        if(TextUtils.isEmpty(rPhone)) {
            rphone.setError("Enter your phone no.");
            return;
        }
        rCity=rcity.getText().toString().trim();
        if(TextUtils.isEmpty(rCity)) {
            rcity.setError("Enter your city");
            return;
        }
        rState=rstate.getText().toString().trim();
        if(TextUtils.isEmpty(rState)) {
            rstate.setError("Enter your state");
            return;
        }
        BackGround b = new BackGround();
        b.execute(rName,rPhone,rPassword,rCity,rState);
    }

    class BackGround extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String phone = params[1];
            String password = params[2];
            String city = params[3];
            String state = params[4];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://servercontrol.site40.net/registerk.php");
                //http://192.168.1.103:80/registerk.php
                String urlParams = "name="+name+"&phone="+phone+"&password="+password+"&city="+city+"&state="+state;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
               // httpURLConnection.setDoOutput(true);
                httpURLConnection.setReadTimeout(15000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                }
                is.close();
                httpURLConnection.disconnect();

                return data;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                String check = s.substring(0, 7);
                if (check.equalsIgnoreCase("success")) {
                    s = "Data saved successfully.";
                    Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Register.this, Home.class);

                    i.putExtra("name", rName);

                    i.putExtra("phone", rPhone);
                    i.putExtra("password", rPassword);
                    i.putExtra("city", rCity);
                    i.putExtra("state", rState);
                    startActivity(i);
                    finish();
                }
                Toast.makeText(ctx, s, Toast.LENGTH_LONG).show();

            }
        }
    }

}
