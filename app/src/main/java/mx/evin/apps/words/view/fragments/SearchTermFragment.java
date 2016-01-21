package mx.evin.apps.words.view.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import mx.evin.apps.words.R;
import mx.evin.apps.words.view.decorations.SpacesItemDecoration;
import mx.evin.apps.words.viewmodel.MainVM;
import mx.evin.apps.words.viewmodel.adapters.TermAutoAdapter;

/**
 * A simple {@link DialogFragment} subclass.
 */
public class SearchTermFragment extends DialogFragment {

    private EditText mEditText;
    public static TermAutoAdapter mAdapter;

    static {
        mAdapter = new TermAutoAdapter(MainVM.mTerms);
    }

    public SearchTermFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_term, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        mEditText = (EditText) view.findViewById(R.id.f_search_term_input_et);
        mEditText.requestFocus();

        RecyclerView rvTerms = (RecyclerView) view.findViewById(R.id.f_add_term_terms_rv);

        rvTerms.setAdapter(mAdapter);
        rvTerms.setLayoutManager(new LinearLayoutManager(getContext()));
        SpacesItemDecoration decoration = new SpacesItemDecoration(Integer.valueOf(getString(R.string.f_search_recycler_space_decoration)));
        rvTerms.addItemDecoration(decoration);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}