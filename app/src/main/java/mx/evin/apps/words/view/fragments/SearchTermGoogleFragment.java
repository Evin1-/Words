package mx.evin.apps.words.view.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.evin.apps.words.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTermGoogleFragment extends DialogFragment {


    public SearchTermGoogleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_term_google, container, false);
    }

}
