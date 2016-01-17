
package mx.evin.apps.words.model.entities.gsearch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Pagemap implements Parcelable {

    @SerializedName("article")
    @Expose
    private List<Article> article = new ArrayList<Article>();
    @SerializedName("metatags")
    @Expose
    private List<Metatag> metatags = new ArrayList<Metatag>();

    /**
     *
     * @return
     *     The article
     */
    public List<Article> getArticle() {
        return article;
    }

    /**
     *
     * @param article
     *     The article
     */
    public void setArticle(List<Article> article) {
        this.article = article;
    }

    /**
     *
     * @return
     *     The metatags
     */
    public List<Metatag> getMetatags() {
        return metatags;
    }

    /**
     *
     * @param metatags
     *     The metatags
     */
    public void setMetatags(List<Metatag> metatags) {
        this.metatags = metatags;
    }


    protected Pagemap(Parcel in) {
        if (in.readByte() == 0x01) {
            article = new ArrayList<Article>();
            in.readList(article, Article.class.getClassLoader());
        } else {
            article = null;
        }
        if (in.readByte() == 0x01) {
            metatags = new ArrayList<Metatag>();
            in.readList(metatags, Metatag.class.getClassLoader());
        } else {
            metatags = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (article == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(article);
        }
        if (metatags == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(metatags);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Pagemap> CREATOR = new Parcelable.Creator<Pagemap>() {
        @Override
        public Pagemap createFromParcel(Parcel in) {
            return new Pagemap(in);
        }

        @Override
        public Pagemap[] newArray(int size) {
            return new Pagemap[size];
        }
    };
}
