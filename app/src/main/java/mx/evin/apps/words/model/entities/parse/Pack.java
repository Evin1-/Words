package mx.evin.apps.words.model.entities.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by evin on 1/8/16.
 */
@ParseClassName("Pack")
public class Pack extends ParseObject{

    public Pack() {
    }

    public String getName(){
        return getString("name");
    }

    public void setName(String name){
        put("name", name);
    }
}
