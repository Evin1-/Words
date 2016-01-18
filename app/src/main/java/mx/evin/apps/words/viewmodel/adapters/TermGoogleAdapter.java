package mx.evin.apps.words.viewmodel.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mx.evin.apps.words.R;
import mx.evin.apps.words.WebActivity;
import mx.evin.apps.words.model.entities.gsearch.Article;
import mx.evin.apps.words.model.entities.gsearch.Item;
import mx.evin.apps.words.viewmodel.utils.Constants;

/**
 * Created by evin on 12/19/15.
 */
public class TermGoogleAdapter extends RecyclerView.Adapter<TermGoogleAdapter.ViewHolder>{
    //TODO If clicked refresh MainActivity layout too

    private static List<Item> mItems;
    private static final String TAG_ = "TermGoogleAdapterTAG_";

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtDescription;
        public TextView txtURL;
        public Item item;

        public ViewHolder(final View itemView) {
            super(itemView);

            txtTitle = (TextView) itemView.findViewById(R.id.recycler_google_search_title_txt);
            txtDescription = (TextView) itemView.findViewById(R.id.recycler_google_search_description_txt);
            txtURL = (TextView) itemView.findViewById(R.id.recycler_google_search_url_txt);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), WebActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(Constants.TITLE_WEB_KEY, item.getTitle());
                    intent.putExtra(Constants.URL_WEB_KEY, item.getLink());

                    v.getContext().startActivity(intent);

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
        Item item = mItems.get(position);
        List<Article> article = item.getPagemap().getArticle();

        TextView textWords = viewHolder.txtTitle;
        TextView textPack = viewHolder.txtDescription;
        TextView textURL = viewHolder.txtURL;

        if (article != null && article.size() > 0){
            String name = article.get(0).getName();
            if (name == null || name.length() < 1)
                textWords.setText(item.getTitle());
            else
                textWords.setText(name);

            String articleBody = article.get(0).getArticlebody();
            if (articleBody == null || articleBody.length() < 1)
                textPack.setText(item.getSnippet());
            else
                textPack.setText(articleBody);
        }else {
            textWords.setText(item.getTitle());
            textPack.setText(item.getSnippet());
        }

        textURL.setText(item.getLink());

        viewHolder.item = item;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}