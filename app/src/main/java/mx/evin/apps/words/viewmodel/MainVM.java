package mx.evin.apps.words.viewmodel;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import mx.evin.apps.words.model.entities.Term;
import mx.evin.apps.words.view.fragments.AddTermFragment;
import mx.evin.apps.words.view.fragments.MainFragment;

/**
 * Created by evin on 1/10/16.
 */
public class MainVM {
    private static final String TAG_ = "ParseVMTAG_";
    public static ArrayList<String> mTerms;

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
                if (e == null){
                    for (ParseObject term : objects) {
                        mTerms.add(((Term) term).getWords());
                    }
                    AddTermFragment.mTerms = mTerms;
                    AddTermFragment.mAdapter.notifyDataSetChanged();
                    Log.d(TAG_, Integer.toString(mTerms.size()));
                }else {
                    Log.d(TAG_, "Error retrieving terms + " + e.toString());
                }
            }
        });
    }

    public static ArrayList<String> getTerms(){
        return mTerms;
    }

}
