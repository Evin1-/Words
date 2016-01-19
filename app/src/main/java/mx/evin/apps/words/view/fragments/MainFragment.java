package mx.evin.apps.words.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mx.evin.apps.words.R;
import mx.evin.apps.words.model.entities.parse.Term;
import mx.evin.apps.words.view.decorations.HorizontalSpacesItemDecoration;
import mx.evin.apps.words.view.decorations.SpacesItemDecoration;
import mx.evin.apps.words.viewmodel.MainVM;
import mx.evin.apps.words.viewmodel.adapters.RelatedTermsAdapter;
import mx.evin.apps.words.viewmodel.adapters.TermAutoAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    //TODO Find by package

    public static final String TAG_ = "MainFragmentTAG_";
    public static ArrayList<Term> mTerms;
    public static RelatedTermsAdapter mRelatedTermsAdapter;

    static {
        mTerms = new ArrayList<>();
        mRelatedTermsAdapter = new RelatedTermsAdapter(mTerms);
    }

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.f_main_related_rv);
        recyclerView.setAdapter(mRelatedTermsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        HorizontalSpacesItemDecoration spacesItemDecoration = new HorizontalSpacesItemDecoration(7);
        recyclerView.addItemDecoration(spacesItemDecoration);
    }
}
