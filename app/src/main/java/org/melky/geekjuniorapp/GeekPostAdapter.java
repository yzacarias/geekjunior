package org.melky.geekjuniorapp;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrador on 30/04/2015.
 */
public class GeekPostAdapter extends BaseAdapter{

    private LayoutInflater inf;
    private Context context;
    private int resource;
    private List<Post> list;
    private VolleySingleton v;
    private ImageLoader mImageLoader;
    private String s;

    GeekPostAdapter(Context context, int resource, List<Post> objects){
        this.context = context;
        this.resource = resource;
        list = objects;
        inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = VolleySingleton.getInstance(context);
        mImageLoader = v.getImageLoader();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ImageView im;
        final TextView title;
        //

        final TextView time;



        //if (view == null){
        view = inf.inflate(resource,viewGroup,false);
        //}

        time = (TextView) view.findViewById(R.id.textView2);
        doParseDate(i,time);

        title = (TextView) view.findViewById(R.id.textView);
        im = (ImageView) view.findViewById(R.id.imageView);

        title.setText(Html.fromHtml(list.get(i).title));
        title.setTextSize(15);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        //t.setText(list.get(i).title);
        try {
            if (list.get(i).featured_image.attachment_meta.sizes.thumbnail != null) {
               // s = list.get(i).featured_image.attachment_meta.sizes.thumbnail.url;
                //Solo el nombre del *.jpg
               s = URLEncoder2(list.get(i).featured_image.attachment_meta.sizes.thumbnail.url);
            } else {
                s = URLEncoder2(list.get(i).featured_image.source);

            }
           mImageLoader.get(s, ImageLoader.getImageListener(im,
               R.drawable.ic_vacio, R.drawable.ic_problem),80,80);
        }catch (Exception e){
            im.setImageResource(R.drawable.ic_problem);
        }



/*        im.setImageResource(R.drawable.ic_launcher);
        ImageRequest request = new ImageRequest(s,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        //im.setAdjustViewBounds(true);
                        im.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        im.setImageBitmap(bitmap);
                    }
                }, 120, 120, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        im.setImageResource(R.drawable.ic_launcher);
                    }
                });
        v.addToRequestQueue(request);*/




        return view;
    }
    private void doParseDate(int i, TextView text){
        String dtStart = list.get(i).date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        try {
            Date d1 = format.parse(dtStart);

            Long ct = System.currentTimeMillis();
            Date d = new Date(ct);
            long dif = d.getTime() - d1.getTime();

            int diffInSeg = (int) (dif/1000);
            int diffInMin = (int) (diffInSeg/60);
            int diffInHours = (int) (diffInMin/60);
            int diffInDays = (int) (diffInHours/24);

            if (diffInDays != 0){
                text.setText(context.getResources().getString(R.string.hace_ya)+ " " + diffInDays + " " + context.getResources().getString(R.string.dias));
            }else if(diffInHours != 0){
                text.setText(context.getResources().getString(R.string.hace_ya)+ " " + diffInHours + " " + context.getResources().getString(R.string.horas));
            }else if (diffInMin != 0){
                text.setText(context.getResources().getString(R.string.hace_ya)+ " " + diffInMin + " " + context.getResources().getString(R.string.minutos));
            }else{
                text.setText(context.getResources().getString(R.string.hace_ya)+ " " + diffInSeg + " " + context.getResources().getString(R.string.segundos));
            }

        } catch (ParseException e) {
            // Auto-generated catch block

        }
    }

    private String URLEncoder2(String file){
        StringBuilder sb = new StringBuilder();
        try {
            String[] decomp = file.split("/");

            URL ul = new URL(file);
            decomp[decomp.length-1] = URLEncoder.encode(decomp[decomp.length-1],"UTF-8");

            for(int i = 0;i<decomp.length;i++){
                sb.append(decomp[i]);
                if(i < decomp.length-1)
                    sb.append("/");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public void add (Post p){
            list.add(p);
    }
}
