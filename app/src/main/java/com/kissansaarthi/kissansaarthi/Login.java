package com.kissansaarthi.kissansaarthi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login extends AppCompatActivity {
    EditText lphone,lpassword;
    Button lregister;
    String Phone, Password;
    Context ctx=this;
    String NAME=null, PASSWORD=null, PHONE=null,CITY=null,STATE=null;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Phone1 = "phoneKey";
    public static final String Pass = "passKey";
    public static final String City = "passcity";
    public static final String State = "passstate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lphone=(EditText)findViewById(R.id.editTextUserName);
        lpassword=(EditText)findViewById(R.id.editTextPassword);
        lregister=(Button)findViewById(R.id.register);
        lphone.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(lphone, InputMethodManager.SHOW_IMPLICIT);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        lregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(Login.this,Register.class);

                i.putExtra("message","hi second activity");
                startActivity(i);


            }
        });


    }
    public void login_login(View v){
        Phone = lphone.getText().toString().trim();
        if(TextUtils.isEmpty(Phone)) {
            lphone.setError("Enter phone no.");
            return;
        }
        Password = lpassword.getText().toString().trim();
        if(TextUtils.isEmpty(Password)) {
            lpassword.setError("Enter password");
            return;
        }
        BackGround b = new BackGround();
        b.execute(Phone, Password);
    }

    class BackGround extends AsyncTask<String, String, String> {



        @Override
        protected String doInBackground(String... params) {
            String phone = params[0];
            String password = params[1];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://servercontrol.site40.net/login.php");
                String urlParams = "phone="+phone+"&password="+password;

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
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                String err = null;
                try {
                    JSONObject root = new JSONObject(s);
                    JSONObject user_data = root.getJSONObject("user_data");
                    NAME = user_data.getString("name");
                    PHONE = user_data.getString("phone");
                    PASSWORD = user_data.getString("password");

                    CITY = user_data.getString("city");
                    STATE = user_data.getString("state");
                } catch (JSONException e) {
                    e.printStackTrace();
                    err = "Exception: " + e.getMessage();
                }

                if (err == null) {
                    Intent i = new Intent(ctx, Home.class);
                    i.putExtra("name", NAME);

                    i.putExtra("phone", PHONE);
                    i.putExtra("password", PASSWORD);
                    i.putExtra("city", CITY);
                    i.putExtra("state", STATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString(Name, NAME);
                    editor.putString(Phone1, PHONE);
                    editor.putString(Pass, PASSWORD);
                    editor.putString(City, CITY);
                    editor.putString(State, STATE);
                    editor.commit();

                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(ctx, "Invalid user name or password", Toast.LENGTH_LONG).show();
                }

            }
        }
    }

}
