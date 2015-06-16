package org.melky.geekjuniorapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by rromero on 25/05/2015.
 */
public class LicenseGeekFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.license_geekjunior,container,false);

        TextView tv = (TextView)rootView.findViewById(R.id.license_geekjunior);
        //tv.setText(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(getActivity()));
        Spanned myStringSpanned = Html.fromHtml(getText(R.string.license).toString().concat(getText(R.string.license2).toString()), null, null);
        tv.setText(myStringSpanned, TextView.BufferType.SPANNABLE);


        return rootView;
    }
}
