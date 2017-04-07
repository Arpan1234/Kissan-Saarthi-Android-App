package com.kissansaarthi.kissansaarthi;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.toolbox.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonu1212 on 08-03-2016.
 */
public class Dealer_Zone extends Fragment {
    Context context;
    public static Dealer_Zone newInstance(){
        Dealer_Zone dealer_zone=new Dealer_Zone();
        return dealer_zone;
    }
    public Dealer_Zone(){}
    ArrayList<String> imgurl= new ArrayList<String>();
    ArrayList<GetSellerDetails> getSellerDetailses=new ArrayList<GetSellerDetails>();
    private static final String SERVER_ADDRESS="http://servercontrol.site40.net/";

    ListView Buy;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.dealer_zone, container, false);
        context=getActivity();
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        Buy=(ListView)rootView.findViewById(R.id.listView4);
        new GetJasonData().execute();
       // getSellerDetailses.add(new GetSellerDetails(SERVER_ADDRESS + "images/banana.JPG","Banana", "200 Km", "100 Kg", "Rs 40 Kg", "09690652548"));
       /* getSellerDetailses.add(new GetSellerDetails(SERVER_ADDRESS + "images/geek.JPG","Orange", "200 Km", "100 Kg", "Rs 40 Kg", "09690652548"));
        getSellerDetailses.add(new GetSellerDetails(SERVER_ADDRESS + "images/geek6.JPG","Lemon", "200 Km", "100 Kg", "Rs 40 Kg", "09690652548"));
       */

       // dimageView=(ImageView)rootView.findViewById(R.id.download);


       /* down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // new DownLoadImage(deditText.getText().toString()).execute();


            }
        });*/
        return rootView;
    }

   /* public class DownLoadImage extends AsyncTask<Void,Void,Bitmap>{
        String name;

        public DownLoadImage(String name) {
            this.name = name;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            String url=SERVER_ADDRESS + "images/" + name + ".JPG";
            try{

                URLConnection connection= new URL(url).openConnection();
                connection.setConnectTimeout(1000 * 30);
                connection.setReadTimeout(1000 * 30);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
               // Bitmap preview_bitmap = BitmapFactory.decodeStream(is, null, options);
                return BitmapFactory.decodeStream((InputStream)connection.getContent(),null,options);

            }catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if(bitmap!=null)
            {
                dimageView.setImageBitmap(bitmap);
            }
        }
    }*/
    public class GetJasonData extends AsyncTask<Void,Void,String>
   {

       @Override
       protected String doInBackground(Void... params) {
           String data="";
           int tmp;

           try {
               URL url = new URL("http://servercontrol.site40.net/downimg.php");


               HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
               // httpURLConnection.setDoOutput(true);
               httpURLConnection.setReadTimeout(15000);
               httpURLConnection.setConnectTimeout(15000);
               httpURLConnection.setRequestMethod("POST");
               httpURLConnection.setDoInput(true);
               httpURLConnection.setDoOutput(true);
              // StringBuilder stringBuilder=new StringBuilder();


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
               JSONArray peoples = null;


               try {
                   JSONObject jsonObj = new JSONObject(s);
                   peoples = jsonObj.getJSONArray("result");

                   for (int i = 0; i < peoples.length(); i++) {
                       JSONObject c = peoples.getJSONObject(i);
                       String id = c.getString("id");
                       String name = c.getString("name").toUpperCase();
                       String path = c.getString("path");
                       String quant = c.getString("quantity");
                       String rate = c.getString("rate");
                       String loc = c.getString("location");
                       String no = c.getString("no");
                       getSellerDetailses.add(new GetSellerDetails(SERVER_ADDRESS + path, name, loc, quant + " Kg", "Rs " + rate + " Kg", no));


                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
               CustomListAdapter adapter1 = new CustomListAdapter(context, getSellerDetailses);
               Buy.setAdapter(adapter1);


           }
       }
   }




}
