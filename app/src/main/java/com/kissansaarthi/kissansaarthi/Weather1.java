package com.kissansaarthi.kissansaarthi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sonu1212 on 18-03-2016.
 */
public class Weather1 extends android.support.v4.app.Fragment{
    SharedPreferences sharedpreferences;
    Context context;
    public static Weather1 newInstance(){
        Weather1 weather=new Weather1();
        return weather;
    }
    public Weather1(){}
    //static final  String baseURL="http://api.openweathermap.org/data/2.5/forecast?q=";
    //static final String key="&mode=xml&APPID=d602798729a91a520ca48c8e8c8f1b34";
    static  final  String baseURL="http://api.previmeteo.com/bd1b004da40c26d0f8ae167b789b775d/ig/api?weather=";

    TextView city,day,temp,weat,tmin,tmax,humidity;
    ImageView iv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.weather1,container,false);

        city=(TextView)view.findViewById(R.id.textView);
        day =(TextView)view.findViewById(R.id.textView4);
        temp=(TextView)view.findViewById(R.id.textView5);
        weat=(TextView)view.findViewById(R.id.textView6);
        tmin=(TextView)view.findViewById(R.id.textView7);
        tmax=(TextView)view.findViewById(R.id.textView8);
        humidity=(TextView)view.findViewById(R.id.textView9);
        iv=(ImageView)view.findViewById(R.id.imageView);
         sharedpreferences = getActivity().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        new GetXMLData().execute();




        return view;

    }

    class GetXMLData extends AsyncTask<Void,Void,Void> {
        String c="Dehli",information;
        String s;
        String scity,sday,stemp,sweat,stmin,stmax,shumidity,simage;
        HttpURLConnection httpURLConnection=null;

        @Override
        protected void onPreExecute() {

            String check = sharedpreferences.getString("passcity", null);
            if(check!=null)
            {
                c=check;
            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            String xmlString="";



            StringBuilder URL =new StringBuilder(baseURL);
            URL.append(c);
            String fullUrl=URL.toString();
            InputStream inputStream=null;
            try{
                java.net.URL url =new URL(fullUrl);
                httpURLConnection =(HttpURLConnection)url.openConnection();
                httpURLConnection.connect();
                inputStream=httpURLConnection.getInputStream();
               /* BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"),8);
                StringBuilder sb=new StringBuilder();
                String line=null;
                while((line=reader.readLine())!=null)
                {

                    sb.append(line);
                }


                xmlString=sb.toString();*/
                XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp=factory.newPullParser();
                xpp.setInput(inputStream,null);
                int eventType=xpp.getEventType();
                String text=null;
                while (eventType!=XmlPullParser.END_DOCUMENT){

                    String name=xpp.getName();
                    String lname="";

                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(xpp.getName().equalsIgnoreCase("city"))
                            {
                              //  information=information+xpp.getName() +" : " + xpp.getAttributeValue(null,"value") + "\n";
                                scity=xpp.getAttributeValue(null,"data");
                            }
                            if(xpp.getName().equalsIgnoreCase("forecast_date"))
                            {
                                //  information=information+xpp.getName() +" : " + xpp.getAttributeValue(null,"value") + "\n";
                                sday=xpp.getAttributeValue(null,"data");
                            }
                            if(xpp.getName().equalsIgnoreCase("condition"))
                            {
                                //  information=information+xpp.getName() +" : " + xpp.getAttributeValue(null,"value") + "\n";
                                simage=xpp.getAttributeValue(null,"data");
                            }
                            if(xpp.getName().equalsIgnoreCase("temp_c"))
                            {
                                //  information=information+xpp.getName() +" : " + xpp.getAttributeValue(null,"value") + "\n";
                                stemp=xpp.getAttributeValue(null,"data");
                            }

                            if(xpp.getName().equalsIgnoreCase("humidity"))
                            {
                                //  information=information+xpp.getName() +" : " + xpp.getAttributeValue(null,"value") + "\n";
                                shumidity=xpp.getAttributeValue(null,"data");
                            }
                            if(xpp.getName().equalsIgnoreCase("low"))
                            {
                                //  information=information+xpp.getName() +" : " + xpp.getAttributeValue(null,"value") + "\n";
                                String mi=xpp.getAttributeValue(null,"data");
                                int min1=(int)(((Integer.parseInt(mi))-32)*5.0/9.0);
                                stmin=String.valueOf(min1);
                            }
                            if(xpp.getName().equalsIgnoreCase("high"))
                            {
                                //  information=information+xpp.getName() +" : " + xpp.getAttributeValue(null,"value") + "\n";
                                String ma=xpp.getAttributeValue(null,"data");
                                int max1=(int)(((Integer.parseInt(ma))-32)*5.0/9.0);
                                stmax=String.valueOf(max1);

                            }
                            break;

                        case XmlPullParser.TEXT:
                            break;

                        case XmlPullParser.END_TAG:
                            lname=xpp.getName();

                    }

                    eventType=xpp.next();
                }

            }catch (Exception e)
            {
                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            city.setText(scity);
            day.setText(sday);
            temp.setText(stemp+" °C");
            weat.setText(simage);
            tmin.setText("↓ "+stmin + " °C");
            tmax.setText("↑ "+stmax+" °C");
            humidity.setText(shumidity);
            context=getActivity();
           String myString = simage.replaceAll(" ", "_").toLowerCase();
            int resId = getResources().getIdentifier(myString.toLowerCase(), "mipmap",context.getPackageName() );
            iv.setImageResource(resId);
        }
    }

}

