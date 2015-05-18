package org.melky.geekjuniorapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrador on 08/05/2015.
 */
public class GeekSplashFragment extends Fragment {
    private GeekJunior act;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        act = (GeekJunior) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.splashscreen_fragment,container,false);
        return rootView;
    }
}
