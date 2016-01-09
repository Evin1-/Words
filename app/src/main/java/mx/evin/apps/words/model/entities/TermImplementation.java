package mx.evin.apps.words.model.entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by evin on 1/8/16.
 */
@ParseClassName("TermImplementation")
public class TermImplementation extends ParseObject {

    public TermImplementation() {
    }

    public Term getTerm(){
        return (Term) getParseObject("term");
    }

    public void setTerm(Term term){
        put("term", term);
    }

    public Term getImplementation(){
        return (Term) getParseObject("implementation");
    }

    public void setImplementation(Term term){
        put("implementation", term);
    }
}
