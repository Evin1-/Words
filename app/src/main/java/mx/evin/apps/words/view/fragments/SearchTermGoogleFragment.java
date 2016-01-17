package mx.evin.apps.words.view.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.evin.apps.words.R;
import mx.evin.apps.words.viewmodel.asynctasks.CustomSearchAsyncTask;
import mx.evin.apps.words.viewmodel.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTermGoogleFragment extends DialogFragment {
    //TODO Remove comments and extra logs
    //TODO Set onRetainInstance to wait for AsyncTask
    private static final String TAG_ = "TermGoogleFragmentTAG_";

    public static SearchTermGoogleFragment newInstance(String GOOGLE_API_KEY, String GOOGLE_CUSTOM_SEARCH_KEY_TAG){
        SearchTermGoogleFragment searchTermGoogleFragment = new SearchTermGoogleFragment();

        Bundle args = new Bundle();
        args.putString(Constants.GOOGLE_API_KEY_TAG, GOOGLE_API_KEY);
        args.putString(Constants.GOOGLE_CUSTOM_SEARCH_KEY_TAG, GOOGLE_CUSTOM_SEARCH_KEY_TAG);

        searchTermGoogleFragment.setArguments(args);

        return searchTermGoogleFragment;
    }


    public SearchTermGoogleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_term_google, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String google_api_key = getArguments().getString(Constants.GOOGLE_API_KEY_TAG);
        String google_custom_search_key = getArguments().getString(Constants.GOOGLE_CUSTOM_SEARCH_KEY_TAG);

        new CustomSearchAsyncTask().execute("RecyclerView", google_api_key, google_custom_search_key);
    }
}
