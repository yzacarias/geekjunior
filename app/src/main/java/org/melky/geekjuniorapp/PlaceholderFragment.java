package org.melky.geekjuniorapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private final String ARG_SECTION_NUMBER = "section_number";

	public PlaceholderFragment() {
	}

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	@SuppressLint("ValidFragment")
	public PlaceholderFragment(int sectionNumber) {
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		this.setArguments(args);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		 ((GeekJunior) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	}	
}
