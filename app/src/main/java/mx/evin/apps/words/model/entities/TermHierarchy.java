package mx.evin.apps.words.model.entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by evin on 1/8/16.
 */
@ParseClassName("TermHierarchy")
public class TermHierarchy extends ParseObject {

    public TermHierarchy() {
    }

    public Term getParent(){
        return (Term) getParseObject("parent");
    }

    public void setParent(Term term){
        put("parent", term);
    }

    public Term getChild(){
        return (Term) getParseObject("child");
    }

    public void setChild(Term term){
        put("child", term);
    }
}
