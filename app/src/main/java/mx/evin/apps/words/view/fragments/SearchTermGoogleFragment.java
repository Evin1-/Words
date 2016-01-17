package mx.evin.apps.words.view.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import mx.evin.apps.words.R;
import mx.evin.apps.words.model.entities.gsearch.Item;
import mx.evin.apps.words.view.decorations.SpacesItemDecoration;
import mx.evin.apps.words.viewmodel.adapters.TermGoogleAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTermGoogleFragment extends DialogFragment {
    //TODO Remove comments and extra logs
    //TODO Set onRetainInstance to wait for AsyncTask
    //TODO Create newInstance
    private static final String TAG_ = "TermGoogleFragmentTAG_";
    public static TermGoogleAdapter mAdapter;
    public static ArrayList<Item> mItems;
    public static String searchTerm;

    static {
        mItems = new ArrayList<>();
        mAdapter = new TermGoogleAdapter(mItems);
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

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.f_search_google_items_rv);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SpacesItemDecoration decoration = new SpacesItemDecoration(5);
        recyclerView.addItemDecoration(decoration);

        EditText editText = (EditText) view.findViewById(R.id.f_search_term_google_input_et);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchTerm = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
