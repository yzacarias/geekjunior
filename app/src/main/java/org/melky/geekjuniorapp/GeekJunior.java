package org.melky.geekjuniorapp;


import android.app.SearchManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class GeekJunior extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    private List<Post> posts;
    private List<Post> categories;

    private  Gson gson;
    private Type tipoListPost;

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(GoogleAnalyticsApp.googleAnalitic) {
            Tracker t = ((GoogleAnalyticsApp) getApplication()).getTracker(GoogleAnalyticsApp.TrackerName.APP_TRACKER);
            t.setScreenName("GeekJunior");
            t.send(new HitBuilders.ScreenViewBuilder().build());
        }

        setContentView(R.layout.activity_geek_junior);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setBackgroundDrawable(new ColorDrawable(0xFF8F00CC));
        //actionBar.setTitle(R.string.ultimas_noticias);

        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeButtonEnabled(true);


        Bundle b = getIntent().getExtras();
        String s = b.getString("posts");

        gson = new Gson();
        tipoListPost = new TypeToken<List<Post>>(){}.getType();
        posts = gson.fromJson(s, tipoListPost);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_drawer);

        //mTitle = getTitle(); // Titulo de la actividad.

        mTitle = getString(R.string._header); //Titulo inicial

        //Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
        		(DrawerLayout) findViewById(R.id.drawer_layout));

        /*
        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, new GeekPostFragment())
                .commit();
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        //Opcion de configuracion sobre la actividad principal
        //getMenuInflater().inflate(R.menu.menu_geek_junior, menu);
        //return true;

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_geek_junior, menu);
            MenuItem searchItem = menu.findItem(R.id.action_example);

            SearchView searchView = (SearchView) MenuItemCompat
                    .getActionView(searchItem);

            SearchManager searchManager = (SearchManager) getSystemService(getApplicationContext().SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String arg0) {
                    Toast.makeText(getApplicationContext(), arg0, Toast.LENGTH_SHORT).show();
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String arg0) {
                    return false;
                }
            });*/

            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            // handles a click on a search suggestion; launches activity to show word

        } else if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // handles a search query
            String query = intent.getStringExtra(SearchManager.QUERY);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF8F00CC));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this,GeekPrefActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Post> getPosts() {
        return posts;
    }
    public List<Post> getCategories() {
        return categories;
    }

    @Override
    public void onBackPressed() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        int i = fragmentManager.getBackStackEntryCount();
        if (i==2) {
            fragmentManager.popBackStackImmediate("PlaceholderFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mTitle = getString(R.string._header);
            restoreActionBar();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        final FragmentManager fragmentManager = getSupportFragmentManager();

        //Consumo el stack de fragments pues navego a traves del Drawer
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new GeekPostFragment(),"post")
                        //.addToBackStack("init")
                        .commit();
                break;
            case 1:
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, new PlusOneGeekFragment())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(null)
                                .commit();
                    }
                },500);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
            case 2:
                getJsonCategoryFragment("actualites");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
            case 3:
                getJsonCategoryFragment("applications");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
            case 4:
                getJsonCategoryFragment("jeux-video");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
            case 5:
                getJsonCategoryFragment("etudes ");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
            case 6:
                getJsonCategoryFragment("astuces");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
            case 7:
                getJsonCategoryFragment("gadgets");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
            case 8:
                getJsonCategoryFragment("videos");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
            case 9:
                getJsonCategoryFragment("android");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
            case 10:
                getJsonCategoryFragment("iphone-ipad");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
            case 11:
                getJsonCategoryFragment("windows");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
            case 12:
                getJsonCategoryFragment("xbox");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
            case 13:
                getJsonCategoryFragment("playstation");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
            case 14:
                getJsonCategoryFragment("wiiu");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
            case 15:
                getJsonCategoryFragment("web");
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;

            case 16:
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, new LicenseGeekFragment())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(null)
                                .commit();
                    }
                }, 500);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new PlaceholderFragment(position))
                        .addToBackStack("PlaceholderFragment")
                        .commit();
                break;
        }
        onSectionAttached(position);
    }

    private void getJsonCategoryFragment(String jsonCategory) {
        StringRequest j = new StringRequest("http://www.geekjunior.fr/wp-json/posts?filter[category_name]="+jsonCategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonArray) {
                gson = new Gson();
                tipoListPost = new TypeToken<List<Post>>(){}.getType();
                categories = gson.fromJson(jsonArray, tipoListPost);
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.container, new GeekCategoriesFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), getText(R.string.volley_error), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(j);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string._header);
                break;
            case 1:
                mTitle = getString(R.string._header2);
                break;
            case 2:
                mTitle = getString(R.string.title_section1);
                break;
            case 3:
                mTitle = getString(R.string.title_section2);
                break;
            case 4:
                mTitle = getString(R.string.title_section3);
                break;
            case 5:
                mTitle = getString(R.string.title_section4);
                break;
            case 6:
                mTitle = getString(R.string.title_section5);
                break;
            case 7:
                mTitle = getString(R.string.title_section6);
                break;
            case 8:
                mTitle = getString(R.string.title_section7);
                break;
            case 9:
                mTitle = getString(R.string.title_section8);
                break;
            case 10:
                mTitle = getString(R.string.title_section9);
                break;
            case 11:
                mTitle = getString(R.string.title_section10);
                break;
            case 12:
                mTitle = getString(R.string.title_section11);
                break;
            case 13:
                mTitle = getString(R.string.title_section12);
                break;
            case 14:
                mTitle = getString(R.string.title_section13);
                break;
            case 15:
                mTitle = getString(R.string.title_section14);
                break;
            case 16:
                mTitle = getString(R.string._footer);
        }
    }
}
