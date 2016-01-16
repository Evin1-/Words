package mx.evin.apps.words.viewmodel.utils;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import mx.evin.apps.words.model.entities.Term;

/**
 * Created by evin on 1/16/16.
 */
public class FilterHelper {

    private String constraint;
    private List<Term> original;
    private ArrayList<Term> results;

    public FilterHelper(String constraint, List<Term> original) {
        this.constraint = constraint;
        this.original = original;
        results = new ArrayList<>();
    }

    public ArrayList<Term> quickFilter() {
        for (Term term : original) {
            if (term.getWords().toLowerCase().contains(constraint)) {
                results.add(term);
            }
        }

        return results;
    }
}
