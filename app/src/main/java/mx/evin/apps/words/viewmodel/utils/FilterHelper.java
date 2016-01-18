package mx.evin.apps.words.viewmodel.utils;

import java.util.ArrayList;
import java.util.List;

import mx.evin.apps.words.model.entities.parse.Term;

/**
 * Created by evin on 1/16/16.
 */
public class FilterHelper {

    private String constraint;
    private List<Term> original;
    private ArrayList<Term> results;
    private String[] splitWords;

    public FilterHelper(String constraint, List<Term> original) {
        this.constraint = constraint;
        this.original = original;
        results = new ArrayList<>();
        splitWords = constraint.split(" ");
    }

    public ArrayList<Term> magicFilter() {
        //TODO Check complexity when a lot of terms
        quickFilter();

        if (results.size() < 1)
            firstLettersFilter(3);

        if (results.size() < 1)
            wordByWordFilter();

        if (results.size() < 1)
            firstLettersFilter(2);

        if (results.size() < 1)
            firstLettersFilter(1);

        return results;
    }

    private void firstLettersFilter(int precision) {
        String smallConstraint = constraint.substring(0, Math.min(precision, constraint.length()));
        for (Term term : original) {
            if (term.getWords().toLowerCase().contains(smallConstraint)) {
                results.add(term);
            }
        }
    }

    private void wordByWordFilter() {
        for (Term term : original) {
            for(String word : splitWords){
                if (term.getWords().toLowerCase().contains(word)) {
                    results.add(term);
                }
            }
        }
    }

    private void quickFilter() {
        for (Term term : original) {
            if (term.getWords().toLowerCase().contains(constraint)) {
                results.add(term);
            }
        }
    }
}
