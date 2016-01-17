
package mx.evin.apps.words.model.entities.gsearch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Context implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;

    /**
     *
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }


    protected Context(Parcel in) {
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Context> CREATOR = new Parcelable.Creator<Context>() {
        @Override
        public Context createFromParcel(Parcel in) {
            return new Context(in);
        }

        @Override
        public Context[] newArray(int size) {
            return new Context[size];
        }
    };
}