
package mx.evin.apps.words.model.entities.gsearch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Queries implements Parcelable {

    @SerializedName("nextPage")
    @Expose
    private List<NextPage> nextPage = new ArrayList<NextPage>();
    @SerializedName("request")
    @Expose
    private List<Request> request = new ArrayList<Request>();

    /**
     *
     * @return
     *     The nextPage
     */
    public List<NextPage> getNextPage() {
        return nextPage;
    }

    /**
     *
     * @param nextPage
     *     The nextPage
     */
    public void setNextPage(List<NextPage> nextPage) {
        this.nextPage = nextPage;
    }

    /**
     *
     * @return
     *     The request
     */
    public List<Request> getRequest() {
        return request;
    }

    /**
     *
     * @param request
     *     The request
     */
    public void setRequest(List<Request> request) {
        this.request = request;
    }


    protected Queries(Parcel in) {
        if (in.readByte() == 0x01) {
            nextPage = new ArrayList<NextPage>();
            in.readList(nextPage, NextPage.class.getClassLoader());
        } else {
            nextPage = null;
        }
        if (in.readByte() == 0x01) {
            request = new ArrayList<Request>();
            in.readList(request, Request.class.getClassLoader());
        } else {
            request = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (nextPage == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(nextPage);
        }
        if (request == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(request);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Queries> CREATOR = new Parcelable.Creator<Queries>() {
        @Override
        public Queries createFromParcel(Parcel in) {
            return new Queries(in);
        }

        @Override
        public Queries[] newArray(int size) {
            return new Queries[size];
        }
    };
}