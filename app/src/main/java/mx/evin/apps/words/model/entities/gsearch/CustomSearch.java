
package mx.evin.apps.words.model.entities.gsearch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CustomSearch implements Parcelable {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("url")
    @Expose
    private Url url;
    @SerializedName("queries")
    @Expose
    private Queries queries;
    @SerializedName("context")
    @Expose
    private Context context;
    @SerializedName("searchInformation")
    @Expose
    private SearchInformation searchInformation;
    @SerializedName("items")
    @Expose
    private List<Item> items = new ArrayList<Item>();

    /**
     *
     * @return
     *     The kind
     */
    public String getKind() {
        return kind;
    }

    /**
     *
     * @param kind
     *     The kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     *
     * @return
     *     The url
     */
    public Url getUrl() {
        return url;
    }

    /**
     *
     * @param url
     *     The url
     */
    public void setUrl(Url url) {
        this.url = url;
    }

    /**
     *
     * @return
     *     The queries
     */
    public Queries getQueries() {
        return queries;
    }

    /**
     *
     * @param queries
     *     The queries
     */
    public void setQueries(Queries queries) {
        this.queries = queries;
    }

    /**
     *
     * @return
     *     The context
     */
    public Context getContext() {
        return context;
    }

    /**
     *
     * @param context
     *     The context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     *
     * @return
     *     The searchInformation
     */
    public SearchInformation getSearchInformation() {
        return searchInformation;
    }

    /**
     *
     * @param searchInformation
     *     The searchInformation
     */
    public void setSearchInformation(SearchInformation searchInformation) {
        this.searchInformation = searchInformation;
    }

    /**
     *
     * @return
     *     The items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     *
     * @param items
     *     The items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }


    protected CustomSearch(Parcel in) {
        kind = in.readString();
        url = (Url) in.readValue(Url.class.getClassLoader());
        queries = (Queries) in.readValue(Queries.class.getClassLoader());
        context = (Context) in.readValue(Context.class.getClassLoader());
        searchInformation = (SearchInformation) in.readValue(SearchInformation.class.getClassLoader());
        if (in.readByte() == 0x01) {
            items = new ArrayList<Item>();
            in.readList(items, Item.class.getClassLoader());
        } else {
            items = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeValue(url);
        dest.writeValue(queries);
        dest.writeValue(context);
        dest.writeValue(searchInformation);
        if (items == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(items);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CustomSearch> CREATOR = new Parcelable.Creator<CustomSearch>() {
        @Override
        public CustomSearch createFromParcel(Parcel in) {
            return new CustomSearch(in);
        }

        @Override
        public CustomSearch[] newArray(int size) {
            return new CustomSearch[size];
        }
    };
}
