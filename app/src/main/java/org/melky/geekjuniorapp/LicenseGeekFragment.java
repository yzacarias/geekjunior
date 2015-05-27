package org.melky.geekjuniorapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by rromero on 25/05/2015.
 */
public class LicenseGeekFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScrollView scroll = new ScrollView(getActivity());
        TextView license = new TextView(getActivity());
        license.setText(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(getActivity()));
        scroll.addView(license);
        return scroll;
    }
}
