package mx.evin.apps.words.model.scripts;

import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import mx.evin.apps.words.model.entities.Technology;
import mx.evin.apps.words.model.entities.UserTechnology;

/**
 * Created by evin on 1/8/16.
 */
public class CreateObject {
    private static final String TAG_ = "CreateObjectTAG_";

    public static void getCreateTechnology(String name){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Technology");
        query.whereEqualTo("name", name);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("score", "The getFirst request failed.");
                    Technology technology = new Technology();
                    technology.setName("Android");
                    technology.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                        }
                    });

                } else {
                    Log.d("score", "Retrieved the object.");
                }
            }
        });
    }

    public static void getCreateUserTechnology(ParseUser user, Technology technology){
        UserTechnology userTechnology = new UserTechnology();
        userTechnology.setTechnology(technology);
        userTechnology.setUser(user);
        userTechnology.saveInBackground();
    }
}
