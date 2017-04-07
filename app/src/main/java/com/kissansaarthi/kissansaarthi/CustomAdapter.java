package com.kissansaarthi.kissansaarthi;

/**
 * Created by sonu1212 on 17-03-2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sonu1212 on 16-03-2016.
 */
public class CustomAdapter extends BaseAdapter {
    List<GetMandiData> alist;
    LayoutInflater inflater;


    public CustomAdapter(Context context,List<GetMandiData>lfilminfo)
    {
        alist=lfilminfo;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return alist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v=inflater.inflate(R.layout.customadapter,null);

        TextView market=(TextView)v.findViewById(R.id.textView6);
        TextView title = (TextView)v.findViewById(R.id.textView2);
        TextView director = (TextView)v.findViewById(R.id.textView3);
        TextView year = (TextView)v.findViewById(R.id.textView4);
        TextView rating = (TextView)v.findViewById(R.id.textView5);


        market.setText(alist.get(position).market);
        title.setText(alist.get(position).commodity);
        director.setText(alist.get(position).variety);
        year.setText(alist.get(position).min);
        rating.setText(alist.get(position).max);
        return v;
    }
}

