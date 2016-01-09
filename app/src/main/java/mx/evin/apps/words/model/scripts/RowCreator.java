package mx.evin.apps.words.model.scripts;

import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.Map;

import mx.evin.apps.words.model.entities.Pack;
import mx.evin.apps.words.model.entities.Technology;
import mx.evin.apps.words.model.entities.Term;
import mx.evin.apps.words.model.entities.TermTerm;
import mx.evin.apps.words.model.entities.UserTechnology;
import mx.evin.apps.words.model.entities.UserTerm;

/**
 * Created by evin on 1/8/16.
 */
public class RowCreator {
    private static final String TAG_ = "CreateObjectTAG_";

    public static void getCreateTechnology(String name) {
        getCreateTechnology(name, false);
    }

    public static void getCreateTechnology(String name, boolean async) {
        Technology technology = new Technology();
        technology.setName(name);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("name", name);

        getCreateGeneric("Technology", settings, technology, async);
    }

    public static void getCreateUserTechnology(ParseUser user, Technology technology) {
        getCreateUserTechnology(user, technology, false);
    }

    public static void getCreateUserTechnology(ParseUser user, Technology technology, boolean async) {
        UserTechnology userTechnology = new UserTechnology();
        userTechnology.setUser(user);
        userTechnology.setTechnology(technology);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("user", user);
        settings.put("technology", technology);

        getCreateGeneric("UserTechnology", settings, userTechnology, async);
    }

    public static void getCreatePack(String name) {
        getCreatePack(name, false);
    }

    public static void getCreatePack(String name, boolean async) {
        Pack pack = new Pack();
        pack.setName(name);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("name", name);

        getCreateGeneric("Pack", settings, pack, async);
    }

    public static void getCreateTerm(String words, Technology technology, Pack pack, String docs, String url) {
        getCreateTerm(words, technology, pack, docs, url, false);
    }

    public static void getCreateTerm(String words, Technology technology, Pack pack, String docs, String url, boolean async) {
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
//        settings.put("docs", docs);
//        settings.put("url", url);

        getCreateGeneric("Term", settings, term, async);
    }

    public static void getCreateUserTerm(ParseUser user, Term term, int strength){
        getCreateUserTerm(user, term, strength, false);
    }

    public static void getCreateUserTerm(ParseUser user, Term term, int strength, boolean async){
        UserTerm userTerm = new UserTerm();
        userTerm.setUser(user);
        userTerm.setTerm(term);
        userTerm.setStrength(strength);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("user", user);
        settings.put("term", term);
        settings.put("strength", strength);

        getCreateGeneric("UserTerm", settings, userTerm, async);
    }

    public static void getCreateTermTerm(Term term1, Term term2, int strength){
        getCreateTermTerm(term1, term2, strength, false);
    }

    public static void getCreateTermTerm(Term term1, Term term2, int strength, boolean async){
        TermTerm termTerm = new TermTerm();
        termTerm.setTerm1(term1);
        termTerm.setTerm2(term2);
        termTerm.setStrength(strength);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("term1", term1);
        settings.put("term2", term2);
        settings.put("strength", strength);

        getCreateGeneric("TermTerm", settings, termTerm, async);

    }

    public static ParseObject getCreateGeneric(final String className, HashMap<String, Object> settings, final ParseObject objectToSave, boolean async) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
        ParseObject parseObjectAux = null;

        for (Map.Entry<String, Object> entry : settings.entrySet()) {
            query.whereEqualTo(entry.getKey(), entry.getValue());
        }

        if (async) {
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (object == null) {
                        objectToSave.saveEventually();
                    } else {
                        Log.d(TAG_, "Retrieved the object " + className + " " + object.getObjectId());
                    }
                }
            });
        } else {
            try {
                parseObjectAux = query.getFirst();
            } catch (ParseException e) {
                Log.d(TAG_, "Not found, trying to create...");
            } finally {
                if (parseObjectAux == null) {
                    try {
                        objectToSave.save();
                        Log.d(TAG_, "Created succesfully." + className + " " + objectToSave.getObjectId());
                    } catch (ParseException e) {
                        Log.d(TAG_, "Failed creation " + e.toString());
                    }
                } else {
                    Log.d(TAG_, "Retrieved the object " + className + " " + parseObjectAux.getObjectId());
                }
            }
        }

        return parseObjectAux;
    }
}
