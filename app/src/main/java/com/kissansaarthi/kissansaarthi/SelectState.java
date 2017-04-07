package com.kissansaarthi.kissansaarthi;


import android.content.Context;

import android.graphics.Color;
import android.os.AsyncTask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import java.util.TreeSet;

import javax.net.ssl.HttpsURLConnection;

public class SelectState extends Fragment {
    ListView llv;
    Context context;
    TextView tv;

    TreeSet<String> sta=new TreeSet<>();

    public static SelectState newInstance() {
        SelectState selectState = new SelectState();
        return selectState;
    }

    public SelectState() {
    }

    private View view;
    private FragmentActivity myContext;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.selectstate, container, false);
       llv=(ListView)view.findViewById(R.id.listView3);
       // tv=(TextView)view.findViewById(R.id.textView10);

        context=getActivity();
        new GetState().execute();




        return view;
    }

    class GetState extends AsyncTask<Void,Void,Void> {
        String information = "";
        String state;
        HttpURLConnection httpURLConnection = null;


        @Override
        protected Void doInBackground(Void... params) {

            String xmlString = "";



          /*  StringBuilder URL =new StringBuilder(baseURL);
            URL.append(c + "," + s+ key);
            String fullUrl=URL.toString();*/
            InputStream inputStream = null;
            try {
                URL url = new URL("http://servercontrol.site40.net/mandi.xml");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                inputStream = httpURLConnection.getInputStream();
                // BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"),8);
                // StringBuilder sb=new StringBuilder();
                //  String line=null;
              /*  while((line=reader.readLine())!=null)
                {

                    sb.append(line);
                }*/


                //  xmlString=sb.toString();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                //  while((line=reader.readLine())!=null) {
                xpp.setInput(inputStream, null);
                int eventType = xpp.getEventType();
                String text = null;
                int flag = 1;

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    String name = xpp.getName();

                    switch (eventType) {
                        case XmlPullParser.START_TAG:

                            break;

                        case XmlPullParser.TEXT:
                            text = xpp.getText();

                            //c=c+text+"/n";

                            break;

                        case XmlPullParser.END_TAG:


                            if (name.equalsIgnoreCase("State")) {

                                sta.add(text);
                            }




                    }

                    eventType = xpp.next();

                }

                // }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("error", e.toString());
                information=information+e.toString();
            }

            return null;
        }

        //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(Void aVoid) {
           /* tv.setTextColor(Color.WHITE);
            tv.setText(information+"hi");*/


            // ArrayAdapter<String> adapter1=new ArrayAdapter<String> (context,android.R.layout.simple_spinner_dropdown_item,brancharray);
           final ArrayList<String> arr=new ArrayList<String>(sta);

            ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,R.layout.custom_textview , arr);
            llv.setAdapter(adapter);
            llv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String state = arr.get(position);
                  /*  final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container, new Mandi().newInstance(), state);
                    ft.commit();*/
                    Fragment fr = new Mandi();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("State", state);
                    fr.setArguments(args);
                    ft.replace(R.id.container, fr);
                    ft.commit();
                    getActivity().setTitle(getString(R.string.title6));

                }
            });



        }


    }
}