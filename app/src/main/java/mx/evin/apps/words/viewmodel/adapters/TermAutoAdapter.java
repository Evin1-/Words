package mx.evin.apps.words.viewmodel.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.evin.apps.words.MainActivity;
import mx.evin.apps.words.R;
import mx.evin.apps.words.model.entities.Pack;
import mx.evin.apps.words.model.entities.Term;
import mx.evin.apps.words.viewmodel.utils.Constants;

/**
 * Created by evin on 12/19/15.
 */
public class TermAutoAdapter extends RecyclerView.Adapter<TermAutoAdapter.ViewHolder> implements Filterable{
    //TODO Check if well filtered when a lot of items
    //TODO Refresh RecyclerViews when creating new AddTermFragments

    private static List<Term> mOriginalTerms;
    private static List<Term> mFilteredTerms;
    private static final String TAG_ = "TermAutoAdapterTAG_";
    private static SharedPreferences mSharedPreferences;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTerm;
        public TextView txtPack;
        public String idTerm;

        public ViewHolder(final View itemView) {
            super(itemView);

            txtTerm = (TextView) itemView.findViewById(R.id.txtTerm);
            txtPack = (TextView) itemView.findViewById(R.id.txtPack);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //TODO Check if editor.apply finishes on time
                    mSharedPreferences = v.getContext().getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(Constants.LAST_TERM_KEY, idTerm);
                    editor.apply();

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    public TermAutoAdapter(List<Term> terms) {
        mOriginalTerms = terms;
        mFilteredTerms = terms;
    }

    @Override
    public TermAutoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View termView = inflater.inflate(R.layout.recycler_autocomplete_term, parent, false);

        return new ViewHolder(termView);
    }

    @Override
    public void onBindViewHolder(TermAutoAdapter.ViewHolder viewHolder, int position) {
        //TODO Do already fetched check with no try catch
        Term term = mFilteredTerms.get(position);

        TextView textWords = viewHolder.txtTerm;
        textWords.setText(term.getWords());

        TextView textPack = viewHolder.txtPack;
        Pack pack = term.getPack();

        try {
            textPack.setText(pack.getName());
        } catch (IllegalStateException e) {
            textPack.setText("");
        }

        viewHolder.idTerm = term.getObjectId();
    }

    @Override
    public int getItemCount() {
        return mFilteredTerms.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Term> results = new ArrayList<>();

                for (Term term : mOriginalTerms){
                    if (term.getWords().toLowerCase().contains(constraint)){
                        results.add(term);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = results;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredTerms = (List<Term>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}