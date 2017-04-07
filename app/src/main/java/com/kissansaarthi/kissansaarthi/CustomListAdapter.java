package com.kissansaarthi.kissansaarthi;

/**
 * Created by sonu1212 on 17-03-2016.
 */
import com.android.volley.toolbox.ImageLoader;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by sonu1212 on 16-03-2016.
 */
public class CustomListAdapter extends BaseAdapter {
    List<GetSellerDetails> alist;
    LayoutInflater inflater;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();



    public CustomListAdapter(Context context,List<GetSellerDetails>lfilminfo)
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
        View v=inflater.inflate(R.layout.custom_product,null);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView)v
                .findViewById(R.id.image1);
        TextView name=(TextView)v.findViewById(R.id.name);
        TextView dist = (TextView)v.findViewById(R.id.distance);
        TextView quant = (TextView)v.findViewById(R.id.quant);
        TextView price = (TextView)v.findViewById(R.id.price);
        TextView call = (TextView)v.findViewById(R.id.call);


        thumbNail.setImageUrl(alist.get(position).Url,imageLoader);
        name.setText(alist.get(position).name);
        dist.setText(alist.get(position).dist);
        quant.setText(alist.get(position).quant);
        price.setText(alist.get(position).price);
        call.setText(alist.get(position).call);
        return v;
    }
}

