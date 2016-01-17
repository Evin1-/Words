package mx.evin.apps.words.model.entities.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by evin on 1/8/16.
 */
@ParseClassName("TermTerm")
public class TermTerm extends ParseObject {

    public TermTerm() {
    }

    public Term getTerm1(){
        return (Term) getParseObject("term1");
    }

    public void setTerm1(Term term){
        put("term1", term);
    }

    public Term getTerm2(){
        return (Term) getParseObject("term2");
    }

    public void setTerm2(Term term){
        put("term2", term);
    }

    public int getStrength(){
        return getInt("strength");
    }

    public void setStrength(int strength){
        put("strength", strength);
    }

}
