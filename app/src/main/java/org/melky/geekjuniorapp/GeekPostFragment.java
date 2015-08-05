package org.melky.geekjuniorapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrador on 30/04/2015.
 */

public class GeekPostFragment extends Fragment {
    GeekJunior a;
    List<Post> lp;
    ListView l;
    View footerView;
    boolean loading=false;
    GeekPostAdapter gpa;
    Gson gson;
    Type tipoListPost;
    List<Post> posts;

    public GeekPostFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        a = (GeekJunior) activity;
        //lp = a.getPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lp = a.getPosts();
        footerView = ((LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer, null, false);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        l = (ListView) rootView.findViewById(R.id.list_view);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = a.getSupportFragmentManager();
                Bundle b = new Bundle();
                b.putString("url_string", lp.get(position).content);
                b.putString("detail_post", "1");
                b.putString("title", lp.get(position).title);
                b.putString("uURL", lp.get(position).link);
                WebFragment wf = new WebFragment();
                wf.setArguments(b);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, wf)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });
        l.addFooterView(footerView, null, false);
        gpa = new GeekPostAdapter(a.getApplicationContext(), R.layout.geekpost, lp);
        l.setAdapter(gpa);
        l.removeFooterView(footerView);
        l.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
                // nothing here
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (load(firstVisibleItem, visibleItemCount, totalItemCount)) {
                    loading = true;
                    l.addFooterView(footerView, null, false);
                    StringRequest j = new StringRequest("http://www.geekjunior.fr/wp-json/posts?filter[posts_per_page]=10 & page=" + GoogleAnalyticsApp.paginacion_post.toString(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String jsonArray) {
                            //doPost();
                            gson = new Gson();
                            tipoListPost = new TypeToken<List<Post>>(){}.getType();
                            posts = gson.fromJson(jsonArray, tipoListPost);

                            //doAdd();
                            for (Post value : posts){
                                gpa.add(value);
                            }

                            //Notify
                            gpa.notifyDataSetChanged();
                            l.removeFooterView(footerView);
                            loading = false;
                            GoogleAnalyticsApp.paginacion_post = GoogleAnalyticsApp.paginacion_post + 1;
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            l.removeFooterView(footerView);
                        }
                    });
                    VolleySingleton.getInstance(a.getApplicationContext()).addToRequestQueue(j);
                    /*new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000,0);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            a.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    l.removeFooterView(footerView);
                                }
                            });
                            loading = false;
                        }
                    }).start();*/
                    //(new LoadNextPage()).execute("");
                }
            }
        });
        return rootView;
    }

    private boolean load(int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
        boolean lastItem = firstVisibleItem + visibleItemCount == totalItemCount &&
                l.getChildAt(visibleItemCount -1) != null &&
                l.getChildAt(visibleItemCount-1).getBottom() <= l.getHeight();
        /*boolean moreRows = l.getAdapter().getCount() < 50;
        return moreRows && lastItem && !loading;     */
        return lastItem && !loading;
    }
}
