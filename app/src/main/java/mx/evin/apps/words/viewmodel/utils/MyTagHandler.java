package mx.evin.apps.words.viewmodel.utils;

import org.xml.sax.XMLReader;

import android.text.Editable;
import android.text.Html;


public class MyTagHandler implements Html.TagHandler {
    boolean first = true;
    String parent = null;
    int index = 1;

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

        if (tag.equals("ul")) {
            parent = "ul";
        } else {
            if (tag.equals("ol")) {
                parent = "ol";
            }
        }

        if (tag.equals("li")) {
            if (parent.equals("ul")) {
                if (first) {
                    output.append("\n- ");
                    first = false;
                } else {
                    first = true;
                }
            } else {
                if (first) {
                    output.append("\n" + index + ". ");
                    first = false;
                    index++;
                } else {
                    first = true;
                }
            }
        }
    }
}