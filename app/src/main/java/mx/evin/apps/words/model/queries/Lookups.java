package mx.evin.apps.words.model.queries;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import mx.evin.apps.words.model.entities.Technology;

/**
 * Created by evin on 1/8/16.
 */
public class Lookups {
    public static Technology getTechnology(String name){
        Technology technology = null;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Technology");
        query.whereEqualTo("name", name);
        try {
            technology = (Technology) query.getFirst();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return technology;
    }
}
