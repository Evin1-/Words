package mx.evin.apps.words.viewmodel.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class DataService extends IntentService {
    private static final String TAG_ = "DataServiceTAG_";
    // TODO Create Cloud Code to only update ids not in the offline mode already.


    public DataService() {
        super(DataService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String[] entities = {
                "Img",
                "Pack",
                "Technology",
                "Term",
                "TermHierarchy",
                "TermImplementation",
                "TermTerm",
                "UserTechnology",
                "UserTerm",
        };

        for (String entity : entities) {
            updateLocalTable(entity);
        }
    }

    private void updateLocalTable(String entity) {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(entity);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    ParseObject.pinAllInBackground(objects);
                }else {
                    Log.e(TAG_, e.toString());
                }
            }
        });
    }
}
