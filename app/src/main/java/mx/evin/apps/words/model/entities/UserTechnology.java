package mx.evin.apps.words.model.entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by evin on 1/7/16.
 */
@ParseClassName("UserTechnology")
public class UserTechnology extends ParseObject {

    public UserTechnology() {
    }

    public Technology getTechnology(){
        return (Technology) getParseObject("technology");
    }

    public void setTechnology(Technology technology){
        put("technology", technology);
    }

    public ParseUser getUser(){
        return getParseUser("user");
    }

    public void setUser(ParseUser user){
        put("user", user);
    }
}
