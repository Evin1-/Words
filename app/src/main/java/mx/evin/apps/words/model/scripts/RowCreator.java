package mx.evin.apps.words.model.scripts;

import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.Map;

import mx.evin.apps.words.model.entities.parse.Img;
import mx.evin.apps.words.model.entities.parse.Pack;
import mx.evin.apps.words.model.entities.parse.Technology;
import mx.evin.apps.words.model.entities.parse.Term;
import mx.evin.apps.words.model.entities.parse.TermHierarchy;
import mx.evin.apps.words.model.entities.parse.TermImplementation;
import mx.evin.apps.words.model.entities.parse.TermTerm;
import mx.evin.apps.words.model.entities.parse.UserTechnology;
import mx.evin.apps.words.model.entities.parse.UserTerm;

/**
 * Created by evin on 1/8/16.
 */
public class RowCreator {
    private static final String TAG_ = "CreateObjectTAG_";

    public static ParseObject getCreateTechnology(String name) {
        return getCreateTechnology(name, false);
    }

    public static ParseObject getCreateTechnology(String name, boolean async) {
        Technology technology = new Technology();
        technology.setName(name);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("name", name);

        return getCreateGeneric("Technology", settings, technology, async);
    }

    public static ParseObject getCreateUserTechnology(ParseUser user, Technology technology) {
        return getCreateUserTechnology(user, technology, false);
    }

    public static ParseObject getCreateUserTechnology(ParseUser user, Technology technology, boolean async) {
        UserTechnology userTechnology = new UserTechnology();
        userTechnology.setUser(user);
        userTechnology.setTechnology(technology);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("user", user);
        settings.put("technology", technology);

        return getCreateGeneric("UserTechnology", settings, userTechnology, async);
    }

    public static ParseObject getCreatePack(String name) {
        return getCreatePack(name, false);
    }

    public static ParseObject getCreatePack(String name, boolean async) {
        Pack pack = new Pack();
        pack.setName(name);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("name", name);

        return getCreateGeneric("Pack", settings, pack, async);
    }

    public static ParseObject getCreateTerm(String words, Technology technology, Pack pack, String docs, String hierarchy, String url) {
        return getCreateTerm(words, technology, pack, docs, hierarchy, url, false);
    }

    public static ParseObject getCreateTerm(String words, Technology technology, Pack pack, String docs, String hierarchy, String url, boolean async) {
        Term term = new Term();
        term.setWords(words);
        term.setTechnology(technology);
        term.setPack(pack);
        term.setDocs(docs);
        term.setHierarchy(hierarchy);
        term.setUrl(url);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("words", words);
        settings.put("technology", technology);
        settings.put("pack", pack);

        return getCreateGeneric("Term", settings, term, async);
    }

    public static ParseObject getCreateUserTerm(ParseUser user, Term term, int strength) {
        return getCreateUserTerm(user, term, strength, false);
    }

    public static ParseObject getCreateUserTerm(ParseUser user, Term term, int strength, boolean async) {
        UserTerm userTerm = new UserTerm();
        userTerm.setUser(user);
        userTerm.setTerm(term);
        userTerm.setStrength(strength);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("user", user);
        settings.put("term", term);
        settings.put("strength", strength);

        return getCreateGeneric("UserTerm", settings, userTerm, async);
    }

    public static ParseObject getCreateTermTerm(Term term1, Term term2, int strength) {
        return getCreateTermTerm(term1, term2, strength, false);
    }

    public static ParseObject getCreateTermTerm(Term term1, Term term2, int strength, boolean async) {
        TermTerm termTerm = new TermTerm();
        termTerm.setTerm1(term1);
        termTerm.setTerm2(term2);
        termTerm.setStrength(strength);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("term1", term1);
        settings.put("term2", term2);
        settings.put("strength", strength);

        return getCreateGeneric("TermTerm", settings, termTerm, async);

    }

    public static ParseObject getCreateTermHierarchy(Term parent, Term child){
        return getCreateTermHierarchy(parent, child, false);
    }

    public static ParseObject getCreateTermHierarchy(Term parent, Term child, boolean async){
        TermHierarchy termHierarchy = new TermHierarchy();
        termHierarchy.setParent(parent);
        termHierarchy.setChild(child);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("parent", parent);
        settings.put("child", child);

        return getCreateGeneric("TermHierarchy", settings, termHierarchy, async);
    }

    public static ParseObject getCreateTermImplementation(Term term, Term implementation){
        return getCreateTermImplementation(term, implementation, false);
    }

    public static ParseObject getCreateTermImplementation(Term term, Term implementation, boolean async){
        TermImplementation termImplementation = new TermImplementation();
        termImplementation.setTerm(term);
        termImplementation.setImplementation(implementation);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("term", term);
        settings.put("implementation", implementation);

        return getCreateGeneric("TermImplementation", settings, termImplementation, async);
    }

    public static ParseObject getCreateImg(String title, String url, String description, int priority, Term term) {
        return getCreateImg(title, url, description, priority, term, false);
    }

    public static ParseObject getCreateImg(String title, String url, String description, int priority, Term term, boolean async){
        Img img = new Img();
        img.setTitle(title);
        img.setUrl(url);
        img.setDescription(description);
        img.setPriority(priority);
        img.setTerm(term);

        HashMap<String, Object> settings = new HashMap<>();
        settings.put("url", url);
        settings.put("term", term);

        return getCreateGeneric("Img", settings, img, async);

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
                        Log.d(TAG_, "Created succesfully " + className + " " + objectToSave.getObjectId());
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
