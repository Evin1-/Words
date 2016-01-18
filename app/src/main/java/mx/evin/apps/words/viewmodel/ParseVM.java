package mx.evin.apps.words.viewmodel;

import android.content.Context;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseObject;

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
public class ParseVM {
    private static final String TAG_ = "ParseVMTAG_";

    public static void parseStart(Context context){
        try {
            Parse.enableLocalDatastore(context);

            ParseObject.registerSubclass(Technology.class);
            ParseObject.registerSubclass(UserTechnology.class);
            ParseObject.registerSubclass(Pack.class);
            ParseObject.registerSubclass(Term.class);
            ParseObject.registerSubclass(UserTerm.class);
            ParseObject.registerSubclass(TermTerm.class);
            ParseObject.registerSubclass(TermHierarchy.class);
            ParseObject.registerSubclass(TermImplementation.class);
            ParseObject.registerSubclass(Img.class);

            Parse.initialize(context);
        } catch (Exception e) {
            Log.e(TAG_, e.toString());
        }

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }
}
