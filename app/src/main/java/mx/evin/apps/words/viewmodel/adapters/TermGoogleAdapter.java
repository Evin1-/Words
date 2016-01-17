package mx.evin.apps.words.viewmodel.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mx.evin.apps.words.R;
import mx.evin.apps.words.model.entities.gsearch.Article;
import mx.evin.apps.words.model.entities.gsearch.Item;
import mx.evin.apps.words.model.entities.parse.Pack;

/**
 * Created by evin on 12/19/15.
 */
public class TermGoogleAdapter extends RecyclerView.Adapter<TermGoogleAdapter.ViewHolder>{

    private static List<Item> mItems;
    private static final String TAG_ = "TermGoogleAdapterTAG_";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtURL;

        public ViewHolder(final View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.recycler_google_search_title_txt);
            txtDescription = (TextView) itemView.findViewById(R.id.recycler_google_search_description_txt);
            txtURL = (TextView) itemView.findViewById(R.id.recycler_google_search_url_txt);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
//                    mSharedPreferences = v.getContext().getSharedPreferences(Constants.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = mSharedPreferences.edit();
//                    editor.putString(Constants.LAST_TERM_KEY, idTerm);
//                    editor.apply();
//
//                    Intent intent = new Intent(v.getContext(), MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    v.getContext().startActivity(intent);
                    Log.d(TAG_, v.toString());
                }
            });
        }
    }

    public TermGoogleAdapter(List<Item> items) {
        mItems = items;
    }

    @Override
    public TermGoogleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View termView = inflater.inflate(R.layout.recycler_google_search_term, parent, false);

        return new ViewHolder(termView);
    }

    @Override
    public void onBindViewHolder(TermGoogleAdapter.ViewHolder viewHolder, int position) {
        Item term = mItems.get(position);
        List<Article> article = term.getPagemap().getArticle();

        if (article.size() > 0){
            TextView textWords = viewHolder.txtTitle;
            textWords.setText(article.get(0).getName());

            TextView textPack = viewHolder.txtDescription;
            textPack.setText(article.get(0).getArticlebody());
        }

        TextView textURL = viewHolder.txtURL;
        textURL.setText(term.getLink());

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}