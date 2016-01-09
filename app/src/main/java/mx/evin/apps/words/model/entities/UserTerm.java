package mx.evin.apps.words.model.entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by evin on 1/8/16.
 */
@ParseClassName("UserTerm")
public class UserTerm extends ParseObject {

    public UserTerm() {
    }

    public ParseUser getUser(){
        return getParseUser("user");
    }

    public void setUser(ParseUser user){
        put("user", user);
    }

    public Term getTerm(){
        return (Term) getParseObject("term");
    }

    public void setTerm(Term term){
        put("term", term);
    }

    public int getStrength(){
        return getInt("strength");
    }

    public void setStrength(int strength){
        put("strength", strength);
    }

}
