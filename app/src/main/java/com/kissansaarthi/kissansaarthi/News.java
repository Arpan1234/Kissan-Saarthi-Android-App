package com.kissansaarthi.kissansaarthi;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonu1212 on 08-03-2016.
 */
public class News extends Fragment {
    public static News newInstance(){
        News news=new News();
        return news;
    }
    ListView listView;
    ArrayList<String> s=new ArrayList<String>();
    ArrayList<String> s2=new ArrayList<String>();
    ArrayList<String> s3=new ArrayList<String>();
    List<ParseNewsData> ln1=new ArrayList<ParseNewsData>();
    public News(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.news,container,false);
        listView=(ListView)rootView.findViewById(R.id.listView5);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

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
                org.jsoup.nodes.Document document= Jsoup.connect("http://www.businesstoday.in/sectors/agriculture").get();

               Elements e1 = document.select("div[class=boxcont]");
               Elements e2 = document.select("div[class=posrel] > a > img[src]");


               for (Element ex : e1) {
                   String s1 = ex.attr("ctitle");
                   s.add(s1);
                   String url=ex.attr("url");
                   s3.add(url);
               }

               for (Element ex : e2) {
                   String s1 = ex.attr("src");
                   s2.add(s1);
               }




               for (int i = 0; i < s2.size(); i++) {
                   ln1.add(new ParseNewsData(s2.get(i), s.get(i)));
               }








            } catch (IOException e) {
                e.printStackTrace();
            }






            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            CustomNewsAdapter ca1=new CustomNewsAdapter(getActivity(),ln1);
            listView.setAdapter(ca1);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String url=s3.get(position);
                    String title=s.get(position);
                    String img=s2.get(position);


                    Fragment fr=new NewsDetails();
                    FragmentManager fm=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft=fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("News", url);
                    args.putString("Title", title);
                    args.putString("Src", img);
                    fr.setArguments(args);
                    ft.replace(R.id.container, fr);
                    ft.commit();
                    getActivity().setTitle(getString(R.string.title7));
                }
            });
        }
    }



}
