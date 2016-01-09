package mx.evin.apps.words.viewmodel;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseObject;

import mx.evin.apps.words.model.entities.Pack;
import mx.evin.apps.words.model.entities.Technology;
import mx.evin.apps.words.model.entities.Term;
import mx.evin.apps.words.model.entities.TermHierarchy;
import mx.evin.apps.words.model.entities.TermImplementation;
import mx.evin.apps.words.model.entities.TermTerm;
import mx.evin.apps.words.model.entities.UserTechnology;
import mx.evin.apps.words.model.entities.UserTerm;

/**
 * Created by evin on 1/8/16.
 */
public class ParseVM {
    public static void parseStart(Context context){
        Parse.enableLocalDatastore(context);

        ParseObject.registerSubclass(Technology.class);
        ParseObject.registerSubclass(UserTechnology.class);
        ParseObject.registerSubclass(Pack.class);
        ParseObject.registerSubclass(Term.class);
        ParseObject.registerSubclass(UserTerm.class);
        ParseObject.registerSubclass(TermTerm.class);
        ParseObject.registerSubclass(TermHierarchy.class);
        ParseObject.registerSubclass(TermImplementation.class);

        Parse.initialize(context);

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }
}
