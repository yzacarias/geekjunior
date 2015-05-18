package org.melky.geekjuniorapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.net.URI;

public class WebFragment extends Fragment {

	private View fragmentview;
	private String mUrl;
	private String uURL;
	private WebView w;
	private String title;
	private ShareActionProvider shareAction;
	private Activity a;

/*	private final String[] INTENT_FILTER = new String[] {
			"com.twitter.android",
			"com.facebook.katana"
	};*/

	public WebFragment(){

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		setHasOptionsMenu(true);
		a = activity;

		Tracker t = ((GoogleAnalyticsApp) a.getApplication()).getTracker(GoogleAnalyticsApp.TrackerName.APP_TRACKER);
		t.setScreenName("WebFranment");
		t.send(new HitBuilders.ScreenViewBuilder().build());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main_menu,menu);
		MenuItem shareItem = menu.findItem(R.id.menu_share);
        shareAction = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		// Get the string resource and bundle it as an intent extra
		intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.text_share) + "\n" + getArguments().getString("uURL"));
		//intent.putExtra(Intent.EXTRA_TEXT, "http://www.google.es");

		shareAction.setShareIntent(intent);

		//shareAction.setShareIntent(splashScreen.openInChooser);
		shareItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				//Tracker t = ((GoogleAnalyticsApp) a.getApplication()).getTracker(GoogleAnalyticsApp.TrackerName.APP_TRACKER);
				//int i = 0;
				return false;
			}
		});

		shareAction.setOnShareTargetSelectedListener(new ShareActionProvider.OnShareTargetSelectedListener() {
			@Override
			public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
				Tracker t = ((GoogleAnalyticsApp) a.getApplication())
						.getTracker(GoogleAnalyticsApp
                                .TrackerName.APP_TRACKER);
				t.setScreenName("WebFranment");
				CharSequence s = intent.getExtras().getCharSequence(Intent.EXTRA_TEXT);
				/*
				t.send(new HitBuilders.EventBuilder()
						.setCategory(intent.getComponent().getPackageName())
						.setAction(s.toString()).build());
				*/
                t.send(new HitBuilders.SocialBuilder()
                        .setNetwork(intent.getComponent().getPackageName())
                        .setAction("SHARE")
                        .setTarget(s.toString())
                        .build());
				return false;
			}
		});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mUrl = getArguments().getString("url_string");
		title = getArguments().getString("title");
		fragmentview = (View) inflater.inflate(R.layout.web_fragment,
				container, false);

		w = (WebView) fragmentview.findViewById(R.id.webviewfragment);
		WebSettings ws = w.getSettings();
		ws.setJavaScriptEnabled(true);
//		ws.setAllowFileAccess(true);
//		if (Build.VERSION.SDK_INT > 7) {
//			ws.setPluginState(WebSettings.PluginState.ON);
//
//		} else {
//			//ws.setPluginsEnabled(true);
//		}
		//ws. setUseWideViewPort(true);
		//ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//		ws.setCacheMode(WebSettings.LOAD_DEFAULT);
		//ws.setBackgroundColor(Color.argb(172,143,0,204));
//		w.setWebViewClient(new WebViewClient() {
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				//view.loadUrl(url);
//				return false;
//			}
//		});

		w.setWebChromeClient(new WebChromeClient());

		String dp = getArguments().getString("detail_post");

		if(!dp.equals("1"))
			w.loadUrl(mUrl);
		else{
			String content =
					"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
							"<html><head>"+
							"<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />" +
							"<style>iframe,img {max-width: 100%; width:auto; height: auto; align: middle}</style>" +
							"</head><body><h3>"+title+"</h3>";
			content += mUrl + "</body></html>";
			w.loadData(content,"text/html; charset=utf-8", null);
		}
		return fragmentview;
	}

}
