package mx.evin.apps.words.model.scripts;

import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import mx.evin.apps.words.model.entities.Pack;
import mx.evin.apps.words.model.entities.Technology;
import mx.evin.apps.words.model.entities.Term;
import mx.evin.apps.words.model.entities.UserTechnology;

/**
 * Created by evin on 1/8/16.
 */
public class Creator {
    //TODO Design a builder pattern for getCreateStuff
    private static final String TAG_ = "CreateObjectTAG_";

    public static void getCreateTechnology(final String name) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Technology");
        query.whereEqualTo("name", name);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Technology technology = new Technology();
                    technology.setName(name);
                    technology.saveInBackground();
                } else {
                    Log.d(TAG_, "Retrieved the object.");
                }
            }
        });
    }

    public static void getCreateUserTechnology(final ParseUser user, final Technology technology) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserTechnology");
        query.whereEqualTo("user", user);
        query.whereEqualTo("technology", technology);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    UserTechnology userTechnology = new UserTechnology();
                    userTechnology.setTechnology(technology);
                    userTechnology.setUser(user);
                    userTechnology.saveInBackground();
                } else {
                    Log.d(TAG_, "Retrieved the object.");
                }
            }
        });
    }

    public static void getCreatePack(final String name) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Pack");
        query.whereEqualTo("name", name);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Pack pack = new Pack();
                    pack.setName(name);
                    pack.saveInBackground();
                } else {
                    Log.d(TAG_, "Retrieved the object.");
                }
            }
        });
    }

    public static void getCreateTerm(final String words, final Technology technology, final Pack pack, final String docs, final String url) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Term");
        query.whereEqualTo("words", words);
        query.whereEqualTo("technology", technology);
        query.whereEqualTo("pack", pack);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Term term = new Term();
                    term.setWords(words);
                    term.setTechnology(technology);
                    term.setPack(pack);
                    term.setDocs(docs);
                    term.setUrl(url);
                    term.saveInBackground();
                } else {
                    Log.d(TAG_, "Retrieved the object.");
                }
            }
        });
    }
}
