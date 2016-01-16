package mx.evin.apps.words.view.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mx.evin.apps.words.R;
import mx.evin.apps.words.model.entities.Term;
import mx.evin.apps.words.view.decorations.SpacesItemDecoration;
import mx.evin.apps.words.viewmodel.MainVM;
import mx.evin.apps.words.viewmodel.adapters.TermAutoAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTermVoiceFragment extends DialogFragment {

    private static final String TAG_ = "SpeechRecognitionStuff";
    private SpeechRecognizer mSpeechRecognizer;
    private TextView mTextView;

    public static ArrayList<Term> mTerms;
    public static TermAutoAdapter mAdapter;

    static {
        mTerms = MainVM.getTerms();
        mAdapter = new TermAutoAdapter(mTerms);
    }

    public AddTermVoiceFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_term_voice, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getView() != null){
            mTextView = (TextView) getView().findViewById(R.id.txtVoice);

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
                    mTextView.setText(getString(R.string.f_add_term_speak_now));
                    log_stuff(v);
                }
            });

            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
            mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    if (data != null && data.size() > 0) {
                        String str_temp = data.get(0).toString();
                        for (int i = 1; i < data.size(); i++) {
                            str_temp = str_temp + "@" + data.get(i);
                        }
                        mTextView.setText((data.get(0).toString()));
                    }
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }


    public void log_stuff(View view) {
        view.requestFocus();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        mSpeechRecognizer.startListening(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
