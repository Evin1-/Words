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

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.List;

import mx.evin.apps.words.MainActivity;
import mx.evin.apps.words.R;
import mx.evin.apps.words.model.entities.parse.Pack;
import mx.evin.apps.words.model.entities.parse.Term;
import mx.evin.apps.words.viewmodel.utils.Constants;
import mx.evin.apps.words.viewmodel.utils.FilterHelper;

/**
 * Created by evin on 12/19/15.
 */
public class TermAutoAdapter extends RecyclerView.Adapter<TermAutoAdapter.ViewHolder> implements Filterable{
    //TODO Check if well filtered when a lot of items
    //TODO Refresh RecyclerViews when creating new AddTermFragments

    private List<Term> mOriginalTerms;
    private List<Term> mFilteredTerms;
    private static final String TAG_ = "TermAutoAdapterTAG_";
    private static SharedPreferences mSharedPreferences;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTerm;
        public TextView txtPack;
        public String idTerm;

        public ViewHolder(final View itemView) {
            super(itemView);

            txtTerm = (TextView) itemView.findViewById(R.id.recycler_autocomplete_term_txt);
            txtPack = (TextView) itemView.findViewById(R.id.recycler_autocomplete_pack_txt);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
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
        Term term = mFilteredTerms.get(position);

        TextView textWords = viewHolder.txtTerm;
        textWords.setText(term.getWords());

        final TextView textPack = viewHolder.txtPack;
        try {
            textPack.setText(term.getPack().getName());
        } catch (Exception e) {
            term.getPack().fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null){
                        Pack pack = (Pack) object;
                        textPack.setText(pack.getName());
                    }else {
                        textPack.setText("");
                    }
                }
            });
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
                FilterResults filterResults = new FilterResults();

                FilterHelper filterHelper = new FilterHelper(constraint.toString().trim().toLowerCase(), mOriginalTerms);
                filterResults.values = filterHelper.magicFilter();

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