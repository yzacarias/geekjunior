package org.melky.geekjuniorapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;


public class splashScreen extends Activity {

    private String sJson;

    @Override
    public void onCreate(Bundle icicle) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        GoogleAnalyticsApp.googleAnalitic = sp.getBoolean("pref_analytic",true);

        if(GoogleAnalyticsApp.googleAnalitic) {
            Tracker t = ((GoogleAnalyticsApp) getApplication()).getTracker(GoogleAnalyticsApp.TrackerName.APP_TRACKER);
            t.setScreenName("GeekJunior");
            t.send(new HitBuilders.ScreenViewBuilder().build());
        }

        super.onCreate(icicle);


        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

        setContentView(R.layout.splashscreen);

        //Display the current version number
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo("org.melky.geekjuniorapp", 0);
            TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
            versionNumber.setText("Version " + pi.versionName);
        } catch (NameNotFoundException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        //http://www.geekjunior.fr/wp-json/posts?filter[category_name]=jeux-video
        //http://www.geekjunior.fr/wp-json/posts?filter[tag]=jeu-iphone
        //http://www.geekjunior.fr/wp-json/posts/?filter[year]=2015&filter[monthnum]=5&filter[day]=17&filter[posts_per_page]=10
        //http://www.geekjunior.fr/wp-json/posts/?filter[posts_per_page]=1&page=6
        StringRequest j = new StringRequest("https://www.geekjunior.fr/wp-json/posts?filter[posts_per_page]=10 & page=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonArray) {
                GoogleAnalyticsApp.paginacion_post = GoogleAnalyticsApp.paginacion_post + 1;
                save_file(jsonArray);
                gotoMain(jsonArray);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), getText(R.string.volley_error),Toast.LENGTH_SHORT).show();
                splashScreen.this.finish();
                //TODO mejor ir a la página principal con datos o bien vacío o bien los últimos guardados
            }
        });

        if ((sJson = read_file())=="") {
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(j);
        }else {
            GoogleAnalyticsApp.paginacion_post = GoogleAnalyticsApp.paginacion_post + 1;
            gotoMain(sJson);
        }
    }


    private String read_file() {
        String contents;
        String path = getApplicationContext().getFilesDir().getAbsolutePath();
        File file = new File(path + "/my-file-name.txt");

        int length = (int) file.length();
        long l = file.lastModified();
        long sc = System.currentTimeMillis();
        long d = sc-l;
        if (sc - l < 15*60*1000) {     //15 min for developr porpouse
            return read_file_stream(file,length);
        }
        return "";
    }

    private String read_file_stream(File file, int length) {
        byte[] bytes = new byte[length];

        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(bytes);
            in.close();
            return new String(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void save_file(String sf) {

        String path = getApplicationContext().getFilesDir().getAbsolutePath();
        File file = new File(path + "/my-file-name.txt");
        int length = (int) file.length();

        write_file_stream(file,sf);
    }

    private void write_file_stream(File file,String sf) {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream.write(sf.getBytes());
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void gotoMain(String jsonArray){
        final String ss = jsonArray;
        new Handler().postDelayed(new Runnable(){
            public void run() {
                 /* Create an Intent that will start the Main WordPress Activity. */
                Intent mainIntent = new Intent(splashScreen.this,GeekJunior.class);
                mainIntent.putExtra("posts", ss);
                splashScreen.this.startActivity(mainIntent);
                splashScreen.this.finish();
            }
        }, 500); //2900 for release

    }
}



