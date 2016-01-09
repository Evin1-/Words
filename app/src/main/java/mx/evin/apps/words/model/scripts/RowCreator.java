package mx.evin.apps.words.model.scripts;

import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import mx.evin.apps.words.model.entities.Pack;
import mx.evin.apps.words.model.entities.Technology;
import mx.evin.apps.words.model.entities.Term;
import mx.evin.apps.words.model.entities.UserTechnology;

/**
 * Created by evin on 1/8/16.
 */
public class RowCreator {
    private static final String TAG_ = "CreateObjectTAG_";

    public static void getCreateTechnology(String name) {
        Technology technology = new Technology();
        technology.setName(name);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("name", name);

        getCreateGeneric("Technology", settings, technology, false);
    }

    public static void getCreateUserTechnology(ParseUser user, Technology technology) {
        UserTechnology userTechnology = new UserTechnology();
        userTechnology.setUser(user);
        userTechnology.setTechnology(technology);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("user", user);
        settings.put("technology", technology);

        getCreateGeneric("UserTechnology", settings, userTechnology, false);
    }

    public static void getCreatePack(String name) {
        Pack pack = new Pack();
        pack.setName(name);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("name", name);

        getCreateGeneric("Pack", settings, pack, false);
    }

    public static void getCreateTerm(String words, Technology technology, Pack pack, String docs, String url) {
        Term term = new Term();
        term.setWords(words);
        term.setTechnology(technology);
        term.setPack(pack);
        term.setDocs(docs);
        term.setUrl(url);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("words", words);
        settings.put("technology", technology);
        settings.put("pack", pack);
        settings.put("docs", docs);
        settings.put("url", url);

        getCreateGeneric("Term", settings, term, false);
    }

    public static void getCreateGeneric(String className, HashMap<String, Object> settings, final ParseObject objectToSave, boolean async){
        ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
        for (Map.Entry<String, Object> entry : settings.entrySet()) {
            query.whereEqualTo(entry.getKey(), entry.getValue());
        }

        if (async){
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (object == null) {
                        objectToSave.saveEventually();
                    } else {
                        Log.d(TAG_, "Retrieved the object." + object.getObjectId());
                    }
                }
            });
        }else{
            try {
                ParseObject object = query.getFirst();
                if (object == null){
                    objectToSave.save();
                }else {
                    Log.d(TAG_, "Retrieved the object." + object.getObjectId());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
