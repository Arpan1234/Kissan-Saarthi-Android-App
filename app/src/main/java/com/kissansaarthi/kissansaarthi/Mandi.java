
package com.kissansaarthi.kissansaarthi;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by sonu1212 on 08-03-2016.
 */
public class Mandi extends Fragment {
    Context context ;
    ListView lv,lv1;
    LinearLayout l1;
    TextView tv;
    String State;
    Spinner sp;
    List<Branchname> brancharray=new ArrayList<Branchname>();
    List<GetMandiData> lfilminfo=new ArrayList<GetMandiData>();
    TreeSet<String> mar= new TreeSet<>();
    List<GetMandiData> mad=new ArrayList<GetMandiData>();

    public static Mandi newInstance(){

        Mandi mandi=new Mandi();
        return mandi;
    }
    public Mandi(){
        //this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.mandi,container,false);
        context=getActivity();

        lv1=(ListView)rootView.findViewById(R.id.listView2);
        brancharray.add(new Branchname("Market", "Commodity", "Variety", "Min Price", "Max Price"));
        CusTitleAdapter adapter1 = new CusTitleAdapter(context, brancharray);
        lv1.setAdapter(adapter1);
        lv=(ListView)rootView.findViewById(R.id.listView);
        l1=(LinearLayout)rootView.findViewById(R.id.layout11);
        State=getArguments().getString("State");
       // State="Gujarat";

       // sp=(Spinner)rootView.findViewById(R.id.spinner);

        new GetXMLData().execute();
       /* CustomAdapter adapter = new CustomAdapter(MainActivity.this, lfilminfo);
        lv.setAdapter(adapter);*/
       /* for (int i=1; i<=5; i++){
            final String tabName = "tab" + Integer.toString(i);
            final TabHost.TabSpec ourSpec = th.newTabSpec(tabName);
            ourSpec.setContent(5);
            ourSpec.setIndicator(tabName);
            th.addTab(ourSpec);
        }*/



        tv=(TextView)rootView.findViewById(R.id.textView4);

       return rootView;

    }

    class GetXMLData extends AsyncTask<Void,Void,Void> {
        String information="";
        String market,commodity,variety,min,max;
        HttpURLConnection httpURLConnection=null;


        @Override
        protected Void doInBackground(Void... params) {

            String xmlString="";



          /*  StringBuilder URL =new StringBuilder(baseURL);
            URL.append(c + "," + s+ key);
            String fullUrl=URL.toString();*/
            InputStream inputStream=null;
            try{
                URL url =new URL("http://servercontrol.site40.net/mandi.xml");
                httpURLConnection =(HttpURLConnection)url.openConnection();
                httpURLConnection.connect();
                inputStream=httpURLConnection.getInputStream();
               // BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"),8);
               // StringBuilder sb=new StringBuilder();
              //  String line=null;
              /*  while((line=reader.readLine())!=null)
                {

                    sb.append(line);
                }*/


              //  xmlString=sb.toString();
                XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp=factory.newPullParser();
              //  while((line=reader.readLine())!=null) {
                    xpp.setInput(inputStream,null);
                    int eventType = xpp.getEventType();
                    String text = null;
                    int flag = 0;

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
                                //information=information+text;
                                if (name.equals("State")) {
                                    if (text.equals(State)) {
                                        flag = 1;
                                    } else {
                                        flag = flag * 2;
                                    }
                                    if (flag == 1)
                                        information = information + text + "  ";
                                }
                                if (name.equals("Market")) {
                                    if (flag == 1) {
                                        market = text;
                                        information = information + text + "  ";
                                    }

                                }
                                if (name.equals("Commodity")) {
                                    if (flag == 1) {
                                        commodity = text;
                                        information = information + text + "  ";
                                    }

                                }
                                if (name.equals("Variety")) {
                                    if (flag == 1) {
                                        variety = text;
                                        information = information + text + "  ";
                                    }

                                }
                                if (name.equals("Min_x0020_Price")) {
                                    if (flag == 1) {
                                        min = text;
                                        information = information + text + "  ";
                                    }

                                }
                                if (name.equals("Max_x0020_Price")) {
                                    if (flag == 1) {
                                        max = text;
                                        information = information + text + "\n";
                                         lfilminfo.add(new GetMandiData(market,commodity,variety,min,max));
                                        mar.add(market);

                                    }

                                }


                        }
                        if (flag == 2)
                            break;
                        eventType = xpp.next();

                    }

               // }
            }catch (Exception e)
            {
                e.printStackTrace();
                Log.i("error", e.toString());
            }

            return null;
        }

        //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(Void aVoid) {
           // ArrayAdapter<String> adapter1=new ArrayAdapter<String> (context,android.R.layout.simple_spinner_dropdown_item,brancharray);
           int l=mar.size();
            int[][] states = new int[][] {
                  //  new int[] { android.R.attr.state_pressed}, // pressed
                    new int[] { android.R.attr.state_focused}, // focused
                    new int[] { android.R.attr.state_enabled} // enabled
            };

            int[] colors = new int[] {
                    Color.parseColor("#14fe14"), //
                    Color.parseColor("#69675b"), //
                   // Color.parseColor("#FF0000")  // red
            };
            ColorStateList list = new ColorStateList(states, colors);

            for(final String k :mar)
            {
                LinearLayout.LayoutParams params = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                }
                params.setMargins(10, 10, 10, 10);
                final TextView tvLeft = new TextView(context);
                // tvLeft.setLayoutParams(lp);
                tvLeft.setBackgroundColor(Color.WHITE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tvLeft.setBackgroundTintList(list);
                }
                tvLeft.setTextColor(Color.WHITE);
                tvLeft.setClickable(true);
                tvLeft.setFocusableInTouchMode(true);

                tvLeft.setText(k);
                tvLeft.setPadding(50, 0, 50, 0);
                tvLeft.setLayoutParams(params);

                l1.addView(tvLeft);
                tvLeft.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        lv.removeAllViewsInLayout();
                        mad.clear();

                        // tvLeft.setTextColor(getResources().getColorStateList(R.color.custom_button));

                        int j = 0;
                        for (GetMandiData gmd : lfilminfo) {

                            if (gmd.market.equals(k)) {
                                mad.add(gmd);
                            }
                        }
                        CustomAdapter adapter = new CustomAdapter(context, mad);
                        adapter.notifyDataSetChanged();
                        lv.setAdapter(adapter);
                    }
                });

             /*   tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //tvLeft.setFocusableInTouchMode(true);




                    }
                });*/

            }
            //CustomAdapter adapter = new CustomAdapter(context, lfilminfo);
          // lv.setAdapter(adapter);

           // tv.setText(information);
        }
    }

}
