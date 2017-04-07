package com.kissansaarthi.kissansaarthi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonu1212 on 18-03-2016.
 */
public class Weather2 extends android.support.v4.app.Fragment{
    ListView lv1;
    List<GetNewsData> ln1=new ArrayList<GetNewsData>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.weather2,container,false);

        lv1=(ListView)view.findViewById(R.id.forlist);
        GetXMLData xmld=new GetXMLData();
        xmld.execute();

        return view;

    }
    class GetXMLData extends AsyncTask<Void,Void,Void> {

        static  final  String baseURL="http://api.previmeteo.com/bd1b004da40c26d0f8ae167b789b775d/ig/api?weather=";

        String c="Delhi",information;
        String s;
        HttpURLConnection httpURLConnection=null;



        @Override
        protected Void doInBackground(Void... params) {

            String xmlString="";



            StringBuilder URL =new StringBuilder(baseURL);
            URL.append(c);
            String fullUrl=URL.toString();
            InputStream inputStream=null;
            try{
                java.net.URL url =new java.net.URL(fullUrl);
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

                int flag=0;
                String dow=null,l=null,h=null,cond=null;

                while (eventType!=XmlPullParser.END_DOCUMENT) {

                    String name = xpp.getName();

                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if(name.equalsIgnoreCase("day_of_week"))
                            {
                                dow=xpp.getAttributeValue(null,"data");
                                if(!dow.equalsIgnoreCase("Today"))
                                {
                                    flag=1;
                                }
                            }
                            if (flag == 1) {
                                if (name.equalsIgnoreCase("low")) {
                                    //  information=information+xpp.getName() +" : " + xpp.getAttributeValue(null,"value") + "\n";
                                    String mi = xpp.getAttributeValue(null, "data");
                                    int min1 = (int) (((Integer.parseInt(mi)) - 32) * 5.0 / 9.0);
                                    l = String.valueOf(min1);
                                }

                                if (name.equalsIgnoreCase("high")) {
                                    //  information=information+xpp.getName() +" : " + xpp.getAttributeValue(null,"value") + "\n";
                                    String ma = xpp.getAttributeValue(null, "data");
                                    int max1 = (int) (((Integer.parseInt(ma)) - 32) * 5.0 / 9.0);
                                    h = String.valueOf(max1);

                                }



                                if (name.equalsIgnoreCase("condition")) {
                                    //  information=information+xpp.getName() +" : " + xpp.getAttributeValue(null,"value") + "\n";
                                    cond = xpp.getAttributeValue(null, "data");
                                    ln1.add(new GetNewsData(dow,l,h,cond));

                                }
                            }

                            break;

                        case XmlPullParser.TEXT:
                            break;

                        case XmlPullParser.END_TAG:
                            break;



                    }

                    eventType = xpp.next();

                }
            }catch (Exception e)
            {
                e.printStackTrace();

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            CustomAdapter1 ca1=new CustomAdapter1(getActivity(),ln1);
            lv1.setAdapter(ca1);


            //context=getActivity();
            //int resId = getResources().getIdentifier(simage.toLowerCase(), "mipmap",context.getPackageName() );
            //iv.setImageResource(resId);
        }
    }
}
