package org.melky.geekjuniorapp;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.plus.PlusOneButton;

/**
 * Created by rromero on 25/05/2015.
 */
public class PlusOneGeekFragment extends Fragment {
    private PlusOneButton p;
    private static final String URL = "https://plus.google.com/+GeekjuniorFr";
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    private GeekJunior act;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setHasOptionsMenu(true);
        act = (GeekJunior) activity;

        if(GoogleAnalyticsApp.googleAnalitic) {
            Tracker t = ((GoogleAnalyticsApp) act.getApplication()).getTracker(GoogleAnalyticsApp.TrackerName.APP_TRACKER);
            t.setScreenName("PlusOneGeekFragment");
            t.send(new HitBuilders.ScreenViewBuilder().build());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.plusonegeek_fragment, container,
                false);
        ImageView fb = (ImageView)rootView.findViewById(R.id.facebookbtm);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GoogleAnalyticsApp.googleAnalitic) {
                    Tracker t = ((GoogleAnalyticsApp) act.getApplication()).getTracker(GoogleAnalyticsApp.TrackerName.APP_TRACKER);
                    t.setScreenName("PlusOneGeekFragment");
                    t.send(new HitBuilders.SocialBuilder()
                            .setNetwork("Facebook")
                            .setAction("click")
                            .setTarget("SOME_URL")
                            .build());
                }
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/geekjunior"));
                startActivity(myIntent);
            }
        });

        ImageView tw = (ImageView)rootView.findViewById(R.id.twitterbtm);
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GoogleAnalyticsApp.googleAnalitic) {
                    Tracker t = ((GoogleAnalyticsApp) act.getApplication()).getTracker(GoogleAnalyticsApp.TrackerName.APP_TRACKER);
                    t.setScreenName("PlusOneGeekFragment");
                    t.send(new HitBuilders.SocialBuilder()
                            .setNetwork("Twitter")
                            .setAction("click")
                            .setTarget("SOME_URL")
                            .build());
                }
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/geekjuniorfr"));
                startActivity(myIntent);
            }
        });

        ImageView gp = (ImageView)rootView.findViewById(R.id.googleplusbtm);
        gp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GoogleAnalyticsApp.googleAnalitic) {
                    Tracker t = ((GoogleAnalyticsApp) act.getApplication()).getTracker(GoogleAnalyticsApp.TrackerName.APP_TRACKER);
                    t.setScreenName("PlusOneGeekFragment");
                    t.send(new HitBuilders.SocialBuilder()
                            .setNetwork("Google+")
                            .setAction("click")
                            .setTarget("SOME_URL")
                            .build());
                }
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/+GeekjuniorFr/posts"));
                startActivity(myIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu.hasVisibleItems())
            menu.removeItem(menu.getItem(0).getItemId());

    }
}
