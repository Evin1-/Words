package mx.evin.apps.words.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import mx.evin.apps.words.R;
import mx.evin.apps.words.model.entities.parse.Img;
import mx.evin.apps.words.model.entities.parse.Term;
import mx.evin.apps.words.view.decorations.HorizontalSpacesItemDecoration;
import mx.evin.apps.words.viewmodel.adapters.ImagesTermsAdapter;
import mx.evin.apps.words.viewmodel.adapters.RelatedTermsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    //TODO Find by package

    public static final String TAG_ = "MainFragmentTAG_";
    public static ArrayList<Term> mTerms;
    public static RelatedTermsAdapter mRelatedTermsAdapter;
    public static ArrayList<Img> mImgs;
    public static ImagesTermsAdapter mImagesTermsAdapter;

    static {
        mTerms = new ArrayList<>();
        mRelatedTermsAdapter = new RelatedTermsAdapter(mTerms);
        
        mImgs = new ArrayList<>();
        mImagesTermsAdapter = new ImagesTermsAdapter(mImgs);
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
        
        RecyclerView relatedRecyclerView = (RecyclerView) view.findViewById(R.id.f_main_related_rv);
        RecyclerView imagesRecyclerView = (RecyclerView) view.findViewById(R.id.f_main_images_rv);
        HorizontalSpacesItemDecoration spacesItemDecoration = new HorizontalSpacesItemDecoration(7);

        relatedRecyclerView.setAdapter(mRelatedTermsAdapter);
        relatedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        relatedRecyclerView.addItemDecoration(spacesItemDecoration);

        imagesRecyclerView.setAdapter(mImagesTermsAdapter);
        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        imagesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.HORIZONTAL));
        imagesRecyclerView.addItemDecoration(spacesItemDecoration);
    }
}
