
package mx.evin.apps.words.model.entities.gsearch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metatag implements Parcelable {

    @SerializedName("viewport")
    @Expose
    private String viewport;

    /**
     *
     * @return
     *     The viewport
     */
    public String getViewport() {
        return viewport;
    }

    /**
     *
     * @param viewport
     *     The viewport
     */
    public void setViewport(String viewport) {
        this.viewport = viewport;
    }


    protected Metatag(Parcel in) {
        viewport = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(viewport);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Metatag> CREATOR = new Parcelable.Creator<Metatag>() {
        @Override
        public Metatag createFromParcel(Parcel in) {
            return new Metatag(in);
        }

        @Override
        public Metatag[] newArray(int size) {
            return new Metatag[size];
        }
    };
}