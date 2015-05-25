package org.melky.geekjuniorapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.plus.PlusOneButton;

/**
 * Created by rromero on 25/05/2015.
 */
public class PlusOneGeekFragment extends Fragment {
    private PlusOneButton p;
    private static final String URL = "https://plus.google.com/+GeekjuniorFr";
    private static final int PLUS_ONE_REQUEST_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.plusonegeek_fragment, container,
                false);

        p = (PlusOneButton) rootView.findViewById(R.id.plus_one_button);
        p.initialize(URL,PLUS_ONE_REQUEST_CODE);

        return rootView;
    }
}
