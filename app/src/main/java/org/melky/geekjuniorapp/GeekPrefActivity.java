package org.melky.geekjuniorapp;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by rromero on 19/05/2015.
 */
public class GeekPrefActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
