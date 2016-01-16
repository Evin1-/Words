package mx.evin.apps.words.view.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
    private SpeechRecognizer speechRecognizer;
    private TextView textView;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = (TextView) getView().findViewById(R.id.txtVoice);

        RecyclerView rvTerms = (RecyclerView) getView().findViewById(R.id.recAutoVoice);
        TermAutoAdapter adapter = new TermAutoAdapter(mTerms);
        rvTerms.setAdapter(adapter);
        rvTerms.setLayoutManager(new LinearLayoutManager(getContext()));
        SpacesItemDecoration decoration = new SpacesItemDecoration(5);
        rvTerms.addItemDecoration(decoration);

        FloatingActionButton floatingActionButton = (FloatingActionButton) getView().findViewById(R.id.fab1);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Speak now\u2026");
                log_stuff(v);
            }
        });

        TextView textView = (TextView) getView().findViewById(R.id.txtVoiceTemp);
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

//                if (!s.toString().equals("Press the button\u2026") && s.length() > 0){
//                    List<Term> new_terms = Term.getSmartFavList(s.toString(), 5);
//                    if (new_terms.size() == 1){
//                        presenter.add_word(new_terms.get(0).getWords(), null);
//                    }
//                    rvTerms.setAdapter(new TermAutoAdapter(new_terms));
//                }else{
//                    rvTerms.setAdapter(adapter);
//                }

                Log.d(TAG_, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        speechRecognizer.setRecognitionListener(new listener());


    }


    public void log_stuff(View view) {
        view.requestFocus();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        speechRecognizer.startListening(intent);
    }


    class listener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {

        }

        public void onBeginningOfSpeech() {

        }

        public void onRmsChanged(float rmsdB) {

        }

        public void onBufferReceived(byte[] buffer) {

        }

        public void onEndOfSpeech() {

        }

        public void onError(int error) {

        }

        public void onResults(Bundle results) {
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String str_temp = data.get(0).toString();
            for (int i = 1; i < data.size() ; i++){
                str_temp = str_temp + "@" + (String) data.get(i);
            }
            textView.setText(capitalize(data.get(0).toString()));
            TextView textViewTemp = (TextView) getView().findViewById(R.id.txtVoiceTemp);
            textViewTemp.setText(str_temp);
        }

        public void onPartialResults(Bundle partialResults) {

        }

        public void onEvent(int eventType, Bundle params) {

        }

        private String capitalize(final String line) {
            return Character.toUpperCase(line.charAt(0)) + line.substring(1);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
