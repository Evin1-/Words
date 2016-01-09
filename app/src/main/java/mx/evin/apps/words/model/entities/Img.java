package mx.evin.apps.words.model.entities;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by evin on 1/8/16.
 */
@ParseClassName("Img")
public class Img extends ParseObject{
    public Img() {
    }

    public String getTitle(){
        return getString("title");
    }

    public void setTitle(String title){
        put("title", title);
    }

    public String getUrl(){
        return getString("url");
    }

    public void setUrl(String url){
        put("url", url);
    }

    public String getDescription(){
        return getString("description");
    }

    public void setDescription(String description){
        put("description", description);
    }

    public int getPriority(){
        return getInt("priority");
    }

    public void setPriority(int priority){
        put("priority", priority);
    }

    public Term getTerm(){
        return (Term) getParseObject("term");
    }

    public void setTerm(Term term){
        put("term", term);
    }
}
