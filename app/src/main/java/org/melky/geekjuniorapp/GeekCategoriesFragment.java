package org.melky.geekjuniorapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Administrador on 30/04/2015.
 */

public class GeekCategoriesFragment extends Fragment {
    GeekJunior a;
    List<Post> lp;

    public GeekCategoriesFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        a = (GeekJunior) activity;
        //lp = a.getCategories();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lp = a.getCategories();
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView l = (ListView) rootView.findViewById(R.id.list_view);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = a.getSupportFragmentManager();
                Bundle b = new Bundle();
                b.putString("url_string",lp.get(position).content);
                b.putString("detail_post","1");
                b.putString("title",lp.get(position).title);
                b.putString("uURL",lp.get(position).link);
                WebFragment wf = new WebFragment();
                wf.setArguments(b);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, wf)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });
        l.setAdapter(new GeekPostAdapter(a.getApplicationContext(),R.layout.geekpost,lp));
        return rootView;
    }
}
