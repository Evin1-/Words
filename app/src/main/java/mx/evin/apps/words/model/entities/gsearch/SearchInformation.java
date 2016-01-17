
package mx.evin.apps.words.model.entities.gsearch;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchInformation implements Parcelable {

    @SerializedName("searchTime")
    @Expose
    private Double searchTime;
    @SerializedName("formattedSearchTime")
    @Expose
    private String formattedSearchTime;
    @SerializedName("totalResults")
    @Expose
    private String totalResults;
    @SerializedName("formattedTotalResults")
    @Expose
    private String formattedTotalResults;

    /**
     *
     * @return
     *     The searchTime
     */
    public Double getSearchTime() {
        return searchTime;
    }

    /**
     *
     * @param searchTime
     *     The searchTime
     */
    public void setSearchTime(Double searchTime) {
        this.searchTime = searchTime;
    }

    /**
     *
     * @return
     *     The formattedSearchTime
     */
    public String getFormattedSearchTime() {
        return formattedSearchTime;
    }

    /**
     *
     * @param formattedSearchTime
     *     The formattedSearchTime
     */
    public void setFormattedSearchTime(String formattedSearchTime) {
        this.formattedSearchTime = formattedSearchTime;
    }

    /**
     *
     * @return
     *     The totalResults
     */
    public String getTotalResults() {
        return totalResults;
    }

    /**
     *
     * @param totalResults
     *     The totalResults
     */
    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    /**
     *
     * @return
     *     The formattedTotalResults
     */
    public String getFormattedTotalResults() {
        return formattedTotalResults;
    }

    /**
     *
     * @param formattedTotalResults
     *     The formattedTotalResults
     */
    public void setFormattedTotalResults(String formattedTotalResults) {
        this.formattedTotalResults = formattedTotalResults;
    }


    protected SearchInformation(Parcel in) {
        searchTime = in.readByte() == 0x00 ? null : in.readDouble();
        formattedSearchTime = in.readString();
        totalResults = in.readString();
        formattedTotalResults = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (searchTime == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(searchTime);
        }
        dest.writeString(formattedSearchTime);
        dest.writeString(totalResults);
        dest.writeString(formattedTotalResults);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SearchInformation> CREATOR = new Parcelable.Creator<SearchInformation>() {
        @Override
        public SearchInformation createFromParcel(Parcel in) {
            return new SearchInformation(in);
        }

        @Override
        public SearchInformation[] newArray(int size) {
            return new SearchInformation[size];
        }
    };
}