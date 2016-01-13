package mx.evin.apps.words.viewmodel.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import mx.evin.apps.words.R;

/**
 * Created by evin on 12/19/15.
 */
public class TermAutoAdapter extends RecyclerView.Adapter<TermAutoAdapter.ViewHolder> {

    private static List<String> mTerms;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTerm;
        public TextView txtNumber;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTerm = (TextView) itemView.findViewById(R.id.txtTerm);
            txtNumber = (TextView) itemView.findViewById(R.id.txtNumber);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    EditText editText = (EditText) v.getRootView().findViewById(R.id.editInputTerm);
//                    TextView textView = (TextView) v.getRootView().findViewById(R.id.txtVoiceTemp);
                    if (editText != null){
                        String editTextString = editText.getText().toString();
                        editText.setText(editTextString);
                        editText.setSelection(editTextString.length());
                    }
//                    if (textView != null){
//                        String textViewString = textView.getText().toString();
//                        textView.setText(textViewString);
//                    }

//                    Presenter presenter = new Presenter();
//                    presenter.add_word(txtTerm.getText().toString(), null);
                }
            });
        }
    }

    public TermAutoAdapter(List<String> terms) {
        mTerms = terms;
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
        String term = mTerms.get(position);

        TextView textRelated = viewHolder.txtTerm;
        textRelated.setText(term);

//        TextView textNumber = viewHolder.txtNumber;
//        int importance = User_term.getImportance(term, null);
//        textNumber.setText(importance == 0 ? "-" : importance + "");
    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }
}