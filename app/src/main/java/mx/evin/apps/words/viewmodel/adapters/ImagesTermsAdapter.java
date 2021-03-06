package mx.evin.apps.words.viewmodel.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

import mx.evin.apps.words.R;
import mx.evin.apps.words.model.entities.parse.Img;

/**
 * Created by evin on 1/18/16.
 */
public class ImagesTermsAdapter extends RecyclerView.Adapter<ImagesTermsAdapter.ViewHolder>{
    //TODO Check if well filtered when a lot of items
    private static List<Img> mImages;
    private Context mContext;
    private static final String TAG_ = "ImagesTermsAdapterTAG_";
    private static SharedPreferences mSharedPreferences;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //TODO Open image in new Activity
        public ImageView imageView;
        public TextView txtImage;
        public TextView txtPack;
        public String idImage;

        public ViewHolder(final View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.recycler_images_img);
            txtImage = (TextView) itemView.findViewById(R.id.recycler_images_title_txt);
            txtPack = (TextView) itemView.findViewById(R.id.recycler_images_description_txt);
        }
    }

    public ImagesTermsAdapter(List<Img> images) {
        mImages = images;
    }

    @Override
    public ImagesTermsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View imageView = inflater.inflate(R.layout.recycler_images_term, parent, false);

        return new ViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(ImagesTermsAdapter.ViewHolder viewHolder, int position) {
        //TODO Do not fix image size

        Img image = mImages.get(position);

        ImageView imageView = viewHolder.imageView;
        Picasso.with(mContext).load(image.getUrl()).resize(320, 180).centerCrop().into(imageView);

        TextView textWords = viewHolder.txtImage;
        textWords.setText(WordUtils.capitalize(image.getTitle()));

        TextView textPack = viewHolder.txtPack;
        textPack.setText(WordUtils.capitalize(image.getDescription()))  ;

        viewHolder.idImage = image.getObjectId();
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }
}
