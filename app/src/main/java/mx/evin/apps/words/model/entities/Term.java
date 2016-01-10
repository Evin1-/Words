package mx.evin.apps.words.model.entities;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by evin on 1/8/16.
 */
@ParseClassName("Term")
public class Term extends ParseObject {
    //TODO Add video url

    public Term() {
    }

    public String getWords(){
        return getString("words");
    }

    public void setWords(String words){
        put("words", words);
    }

    public Technology getTechnology(){
        return (Technology) getParseObject("technology");
    }

    public void setTechnology(Technology technology){
        put("technology", technology);
    }

    public Pack getPack(){
        return (Pack) getParseObject("pack");
    }

    public void setPack(Pack pack){
        put("pack", pack);
    }

    public String getDocs(){
        return getString("docs");
    }

    public void setDocs(String docs){
        put("docs", docs);
    }

    public String getUrl(){
        return getString("url");
    }

    public void setUrl(String url){
        put("url", url);
    }

}
