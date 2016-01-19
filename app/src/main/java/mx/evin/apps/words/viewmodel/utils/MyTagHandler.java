package mx.evin.apps.words.viewmodel.utils;

import android.text.Editable;
import android.text.Html;

import org.xml.sax.XMLReader;

/**
 * StackOverflow
 * http://stackoverflow.com/a/9649408/2503185
 */
public class MyTagHandler implements Html.TagHandler {
    boolean first = true;

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

        // TODO Auto-generated method stub
        if (tag.equals("li")) {
            char lastChar = 0;
            if (output.length() > 0)
                lastChar = output.charAt(output.length() - 1);
            if (first) {
                if (lastChar == '\n')
                    output.append("- ");
                else
                    output.append("\n- ");
                first = false;
            } else {
                first = true;
            }
        }
    }
}