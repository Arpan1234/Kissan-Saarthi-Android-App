package com.kissansaarthi.kissansaarthi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by sonu1212 on 25-04-2016.
 */
public class NewsDetails extends Fragment {
    StringBuilder stringBuilder=new StringBuilder();
    String text;
    TextView tle,details;
    NetworkImageView thumbNail;
    ImageLoader imageLoader;
    String title,src,url;


    public static NewsDetails newInstance(){
        NewsDetails newsDetails=new NewsDetails();
        return newsDetails;


    }
    public NewsDetails(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.newsrdetails,container,false);

        tle=(TextView)rootView.findViewById(R.id.newstitle);
        details=(TextView)rootView.findViewById(R.id.textDetail);
       thumbNail = (NetworkImageView)rootView
                .findViewById(R.id.imageDetail);

         imageLoader = AppController.getInstance().getImageLoader();
        title=getArguments().getString("Title");
        src=getArguments().getString("Src");
        url=getArguments().getString("News");

        new HtmlData().execute();


        return rootView;
    }
    public class HtmlData extends AsyncTask<Void,Void,Void>
    {
        String result="";
        HttpURLConnection httpURLConnection=null;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                org.jsoup.nodes.Document document= Jsoup.connect(url).get();


                Elements e2 = document.select("div[class=story-right relatedstory] > p");
                int flag=1;


                for (Element ex : e2) {
                    String s1 = ex.text();
                    if(!(s1.contains("ALSO READ")) && flag!=1) {
                        stringBuilder.append(s1);
                        stringBuilder.append(System.getProperty("line.separator"));
                        stringBuilder.append(System.getProperty("line.separator"));
                    }
                    flag=2;

                }

                text=stringBuilder.toString();


            } catch (IOException e) {
                e.printStackTrace();
            }






            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (imageLoader == null)
                imageLoader = AppController.getInstance().getImageLoader();

            tle.setText(title);
            details.setText(text);
            thumbNail.setImageUrl(src,imageLoader);





        }
    }

}
