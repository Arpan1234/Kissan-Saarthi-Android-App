package com.kissansaarthi.kissansaarthi;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sonu1212 on 08-03-2016.
 */
public class Farmer_Zone extends Fragment {
    SharedPreferences sharedpreferences;
    InputStream in = null;
    Context context;
    private Button button;
    private String encoded_string, image_name;
    private Bitmap bitmap;
    private File file;
    private Uri file_uri;
    String qua1,rat1,loc1,no1;

    public static Farmer_Zone newInstance(){
        Farmer_Zone farmer_zone=new Farmer_Zone();
        return farmer_zone;
    }
    public Farmer_Zone(){}

    Button bUploadImage;
    EditText product,quantity,rate;
    TextView location;
    ImageView imageToUpload;
    public static final int RESULT_LOAD_IMAGE=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.farmer_zone,container,false);
        context=getActivity();
        sharedpreferences = getActivity().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);

        bUploadImage=(Button)rootView.findViewById(R.id.Button);
        imageToUpload=(ImageView)rootView.findViewById(R.id.imageView7);

        product=(EditText)rootView.findViewById(R.id.editText);
        quantity=(EditText)rootView.findViewById(R.id.editText1);
        rate=(EditText)rootView.findViewById(R.id.editText2);

        location=(TextView)rootView.findViewById(R.id.textView14);

        bUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              // Bitmap image=((BitmapDrawable)imageToUpload.getDrawable()).getBitmap();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                 Bitmap image = BitmapFactory.decodeStream(in, null, options);
                String naam =product.getText().toString().trim();
                if(TextUtils.isEmpty(naam)) {
                    product.setError("Enter the product name");
                    return;
                }

                String quant=quantity.getText().toString().trim();

                if(TextUtils.isEmpty(quant)) {
                    quantity.setError("Enter your name");
                    return;
                }
                String ra=rate.getText().toString().trim();
                if(TextUtils.isEmpty(ra)) {
                    rate.setError("Enter your name");
                    return;
                }
                String loc="Dehradun";
                String check = sharedpreferences.getString("passcity", null);
                if(check!=null)
                {
                    loc=check;
                    location.setText(loc);
                }

                String no="9690652548";
                String check1 = sharedpreferences.getString("phoneKey", null);
                if(check1!=null)
                {
                    no=check1;
                }
                new Encode_image(image,naam,quant,ra,loc,no).execute();
              /*  Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                getFileUri();
                i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
                startActivityForResult(i, 10);*/


            }
        });
        imageToUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);

            }
        });

        return rootView;
    }

   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_LOAD_IMAGE && resultCode== getActivity().RESULT_OK && data!=null)
        {
            Uri selectedImage=data.getData();

            try {
                in =getActivity().getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageToUpload.setImageURI(selectedImage);

        }

    }
   /* private void getFileUri() {
        image_name = "testing123";
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + image_name
        );

        file_uri = Uri.fromFile(file);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 10 && resultCode == getActivity().RESULT_OK) {
            new Encode_image().execute();
        }
    }*/

    private class Encode_image extends AsyncTask<Void, Void, Void> {
        Bitmap bitmap;
        String name;



        public Encode_image(Bitmap image,String name,String qua,String rat,String loc,String no)
        {
            this.bitmap=image;
            this.name=name;
            qua1=qua;
            rat1=rat;
            loc1=loc;
            no1=no;
            image_name = name;
        }
        @Override
        protected Void doInBackground(Void... voids) {


            //bitmap = BitmapFactory.decodeFile(file_uri.getPath());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            makeRequest();
            Toast.makeText(context,"image uploaded",Toast.LENGTH_SHORT).show();
        }
    }

    private void makeRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, "http://servercontrol.site40.net/imgupload.php",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("image",encoded_string);
                map.put("name",image_name);
                map.put("quantity",qua1);
                map.put("rate",rat1);
                map.put("location",loc1);
                map.put("no",no1);

                return map;
            }
        };
        Toast.makeText(context,"connection done",Toast.LENGTH_SHORT).show();
        requestQueue.add(request);
    }


}
