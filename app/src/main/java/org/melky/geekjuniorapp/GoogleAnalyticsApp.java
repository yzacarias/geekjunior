package org.melky.geekjuniorapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.lang.reflect.Array;
import java.util.HashMap;

/**
 * Created by Administrador on 14/05/2015.
 */
public class GoogleAnalyticsApp extends Application {
    //putero de paginacion
    public static Integer paginacion_post =  new Integer(1);
    public static Integer paginacion_categorias =  new Integer(1);
    public static Integer paginacion_busquedas = new Integer(1);

    public static boolean googleAnalitic;
    // change the following line
    private static final String PROPERTY_ID = "XX-XXXXXXXX-X";

    public static int GENERAL_TRACKER = 0;

    public enum TrackerName {
        APP_TRACKER,
        GLOBAL_TRACKER,
        ECOMMERCE_TRACKER,
    }

    public HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public GoogleAnalyticsApp() {
        super();
    }

    public synchronized Tracker getTracker(TrackerName appTracker) {
        if (!mTrackers.containsKey(appTracker)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (appTracker == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
                    : (appTracker == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
                    : analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(appTracker, t);
        }
        return mTrackers.get(appTracker);
    }
}
