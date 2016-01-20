package mx.evin.apps.words.view.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;

import mx.evin.apps.words.R;
import mx.evin.apps.words.model.entities.parse.Term;
import mx.evin.apps.words.view.decorations.SpacesItemDecoration;
import mx.evin.apps.words.viewmodel.adapters.TermAutoAdapter;
import mx.evin.apps.words.viewmodel.utils.VoiceRecognizer;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchTermVoiceFragment extends DialogFragment {

    private static final String TAG_ = "SpeechRecognitionStuff";
    private TextView mTextView;
    private VoiceRecognizer mVoiceRecognizer;

    public static ArrayList<Term> mTerms;
    public static TermAutoAdapter mAdapter;

    static {
        mTerms = new ArrayList<>();
        mAdapter = new TermAutoAdapter(mTerms);
    }

    public SearchTermVoiceFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mVoiceRecognizer = new VoiceRecognizer(getContext());
        return inflater.inflate(R.layout.fragment_search_term_voice, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (getView() == null)
            return;

        mTextView = (TextView) getView().findViewById(R.id.txtVoice);
        mVoiceRecognizer.prepare(mTextView);

        RecyclerView rvTerms = (RecyclerView) getView().findViewById(R.id.recAutoVoice);

        rvTerms.setAdapter(mAdapter);
        rvTerms.setLayoutManager(new LinearLayoutManager(getContext()));
        SpacesItemDecoration decoration = new SpacesItemDecoration(5);
        rvTerms.addItemDecoration(decoration);

        FloatingActionButton floatingActionButton = (FloatingActionButton) getView().findViewById(R.id.fab1);

        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.color_primary)));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(getString(R.string.f_search_term_speak_now));
                mVoiceRecognizer.listenNow(v);
            }
        });

        mTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(getString(R.string.f_search_term_speak_now)))
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
