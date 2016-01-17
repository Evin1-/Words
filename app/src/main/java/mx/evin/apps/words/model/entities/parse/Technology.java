package mx.evin.apps.words.model.entities.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by evin on 1/7/16.
 */
@ParseClassName("Technology")
public class Technology extends ParseObject {

    public Technology() {
    }

    public String getName(){
        return getString("name");
    }

    public void setName(String name){
        put("name", name);
    }
}
