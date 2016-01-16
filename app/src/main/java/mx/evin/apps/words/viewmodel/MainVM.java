package mx.evin.apps.words.viewmodel;

import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import mx.evin.apps.words.model.entities.Term;
import mx.evin.apps.words.view.fragments.AddTermFragment;
import mx.evin.apps.words.view.fragments.MainFragment;
import mx.evin.apps.words.viewmodel.utils.Constants;

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

    public static void initializeMain(){
        initializeTerms();
    }

    private static void initializeTerms(){
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Term");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject term : objects) {
                        term.pinInBackground();
                        mTerms.add((Term) term);
                        term.getParseObject("pack").fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                object.pinInBackground();
                                AddTermFragment.mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                    AddTermFragment.mTerms = mTerms;
                    AddTermFragment.mAdapter.notifyDataSetChanged();
                    Log.d(TAG_, Integer.toString(mTerms.size()));
                } else {
                    Log.d(TAG_, "Error retrieving terms + " + e.toString());
                }
            }
        });
    }

    public static ArrayList<Term> getTerms(){
        return mTerms;
    }

    public static void refreshMainFragment(Context context) {
        //TODO Code else currenTerm not updated
    }

    public static void refreshCurrentTerm(final String last_term) {
        ParseObject object = ParseObject.createWithoutData("Term", last_term);
        object.fetchFromLocalDatastoreInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your game score
                    currentTerm = (Term) object;
                    Log.d(TAG_, "GOOD TERM " + object.toString());
                } else {
                    // something went wrong
                    Log.d(TAG_, "BAD TERM " + e.toString());
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Term");
                    query.fromLocalDatastore();
                    query.getInBackground(last_term, new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                currentTerm = (Term) object;
                                Log.d(TAG_, "GOOD TERM " + object.toString());
                                // object will be your game score
                            } else {
                                // something went wrong
                                Log.d(TAG_, "BAD TERM " + e.toString());
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("Term");
                                query.getInBackground(last_term, new GetCallback<ParseObject>() {
                                    public void done(ParseObject object, ParseException e) {
                                        if (e == null) {
                                            currentTerm = (Term) object;
                                            Log.d(TAG_, "GOOD TERM " + object.toString());
                                            // object will be your game score
                                        } else {
                                            Log.d(TAG_, "BAD TERM " + e.toString());
                                            // something went wrong
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}
