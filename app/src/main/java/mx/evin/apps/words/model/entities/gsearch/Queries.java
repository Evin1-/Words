
package mx.evin.apps.words.model.entities.gsearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Queries {

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

}
