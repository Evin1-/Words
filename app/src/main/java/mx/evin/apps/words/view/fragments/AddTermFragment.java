package mx.evin.apps.words.view.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.ArrayList;

import mx.evin.apps.words.R;
import mx.evin.apps.words.view.decorations.SpacesItemDecoration;
import mx.evin.apps.words.viewmodel.MainVM;
import mx.evin.apps.words.viewmodel.adapters.TermAutoAdapter;

/**
 * A simple {@link DialogFragment} subclass.
 */
public class AddTermFragment extends DialogFragment {

    private EditText mEditText;

    public AddTermFragment() {

    }

    public static AddTermFragment newInstance(String title) {
        AddTermFragment frag = new AddTermFragment();

        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_term, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditText = (EditText) view.findViewById(R.id.editInputTerm);
        String title = getArguments().getString("title", "Add Term");
        getDialog().setTitle(title);
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        ArrayList<String> terms = MainVM.getTerms();

        final RecyclerView rvTerms = (RecyclerView) getView().findViewById(R.id.recAuto);
        final TermAutoAdapter adapter = new TermAutoAdapter(terms);
        rvTerms.setAdapter(adapter);
        rvTerms.setLayoutManager(new LinearLayoutManager(getContext()));
        SpacesItemDecoration decoration = new SpacesItemDecoration(5);
        rvTerms.addItemDecoration(decoration);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString();
//                if (searchText.trim().length() > 0)
//                    rvTerms.setAdapter(new TermAutoAdapter(Term.getFavTermsList(searchText, 5)));
//                else
//                    rvTerms.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
//        ((MainActivity) getActivity()).refreshRecycler();
    }
}