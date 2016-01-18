package mx.evin.apps.words.viewmodel.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;

/**
 * Created by evin on 1/16/16.
 */
public class VoiceRecognizer {
    private static final String TAG_ = "VoiceRecognizerTAG_";
    private Context context;
    private SpeechRecognizer speechRecognizer;
    private ArrayList<String> speechResults;

    public VoiceRecognizer(Context context) {
        this.context = context;
        speechResults = new ArrayList<>();
    }

    public void prepare(final TextView textView){
        if (context == null){
            Log.e(TAG_, "Make sure you call the context to the constructor");
            return;
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
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
                speechResults = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (speechResults != null && speechResults.size() > 0){
                    String result = speechResults.get(0);
                    textView.setText(WordUtils.capitalize(result));
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

    public void listenNow(View view) {
        view.requestFocus();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        speechRecognizer.startListening(intent);
    }

    public ArrayList<String> getSpeechResults() {
        return speechResults;
    }

    public void setSpeechResults(ArrayList<String> speechResults) {
        this.speechResults = speechResults;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public SpeechRecognizer getSpeechRecognizer() {
        return speechRecognizer;
    }

    public void setSpeechRecognizer(SpeechRecognizer speechRecognizer) {
        this.speechRecognizer = speechRecognizer;
    }
}
