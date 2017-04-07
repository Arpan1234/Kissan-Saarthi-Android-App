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
public class CustomAdapter1 extends BaseAdapter {
    List<GetNewsData> alist;
    LayoutInflater inflater;
    Context context;


    public CustomAdapter1(Context context,List<GetNewsData>ln1)
    {
        this.context=context;
        alist=ln1;
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

        View v=inflater.inflate(R.layout.customadapterfor,null);
        ImageView iv=(ImageView)v.findViewById(R.id.icon);
        TextView day = (TextView)v.findViewById(R.id.textView);
        TextView low = (TextView)v.findViewById(R.id.textView3);
        TextView high = (TextView)v.findViewById(R.id.textView4);
        TextView condition = (TextView)v.findViewById(R.id.textView5);


        day.setText(alist.get(position).day);
        low.setText(alist.get(position).low);
        high.setText(alist.get(position).high);
        String simage=alist.get(position).codition;
        condition.setText(simage);
        String myString = simage.replaceAll(" ", "_").toLowerCase();
        int resId = context.getResources().getIdentifier(myString.toLowerCase(), "mipmap",context.getPackageName() );
        iv.setImageResource(resId);


        return v;


    }
}

