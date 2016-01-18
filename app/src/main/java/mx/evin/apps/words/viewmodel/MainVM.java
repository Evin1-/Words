package mx.evin.apps.words.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import mx.evin.apps.words.R;
import mx.evin.apps.words.model.entities.parse.Term;
import mx.evin.apps.words.view.fragments.SearchTermFragment;
import mx.evin.apps.words.view.fragments.SearchTermVoiceFragment;

/**
 * Created by evin on 1/10/16.
 */
public class MainVM {
    //TODO Offline mode first
    //TODO Check that 2 offline calls are not at the same time
    private static final String TAG_ = "ParseVMTAG_";
    public static ArrayList<Term> mTerms;
    public static Term currentTerm;

    static {
        mTerms = new ArrayList<>();
    }

    public static void initializeMain() {
        initializeTerms();
    }

    private static void initializeTerms() {
        SearchTermFragment.mTerms = mTerms;
        SearchTermVoiceFragment.mTerms = mTerms;

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Term");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject term : objects) {
                        SearchTermFragment.mAdapter.notifyDataSetChanged();
                        SearchTermVoiceFragment.mAdapter.notifyDataSetChanged();
                        term.pinInBackground();
                        mTerms.add((Term) term);
                        term.getParseObject("pack").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                object.pinInBackground();
                                SearchTermFragment.mAdapter.notifyDataSetChanged();
                                SearchTermVoiceFragment.mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    SearchTermFragment.mAdapter.notifyDataSetChanged();
                    SearchTermVoiceFragment.mAdapter.notifyDataSetChanged();
//                    Log.d(TAG_, Integer.toString(mTerms.size()));
                } else {
                    Log.d(TAG_, "Error retrieving terms + " + e.toString());
                }
            }
        });
    }

    public static ArrayList<Term> getTerms() {
        return mTerms;
    }

    public static void refreshMainFragment(Activity activity) {
        //TODO Update hierarchy, related terms and blablabal
        //TODO Remove try/catch and use a better practice
        TextView textViewDoc = (TextView) activity.findViewById(R.id.f_main_doc_txt);
        TextView textViewPack = (TextView) activity.findViewById(R.id.f_main_pack_txt);
        TextView textViewTitle = (TextView) activity.findViewById(R.id.f_main_title_txt);
        TextView textViewTechnology = (TextView) activity.findViewById(R.id.f_main_technology_txt);

        textViewDoc.setText(Html.fromHtml(currentTerm.getDocs()));
        try {
            textViewPack.setText(currentTerm.getPack().getName());
        } catch (Exception e) {
            textViewPack.setText(activity.getString(R.string.f_placeholder));
        }
        textViewTitle.setText(currentTerm.getWords());
//        textViewTechnology.setText(currentTerm.getTechnology().getName());


    }

    public static void refreshCurrentTerm(final String last_term, final Context context) {
        ParseObject query = ParseObject.createWithoutData("Term", last_term);
        query.fetchFromLocalDatastoreInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    currentTerm = (Term) object;
                    refreshMainFragment((Activity) context);
//                    Log.d(TAG_, "GOOD TERM " + object.toString());
                } else {
//                    Log.d(TAG_, "BAD TERM " + e.toString());
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Term");
                    query.getInBackground(last_term, new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                object.pinInBackground();
                                currentTerm = (Term) object;
                                refreshMainFragment((Activity) context);
//                                Log.d(TAG_, "GOOD TERM " + object.toString());
                            } else {
//                                Log.d(TAG_, "BAD TERM " + e.toString());
                            }
                        }
                    });

                }
            }
        });
    }
}
