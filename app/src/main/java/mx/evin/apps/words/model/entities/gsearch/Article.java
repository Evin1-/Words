
package mx.evin.apps.words.model.entities.gsearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Article {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("articlebody")
    @Expose
    private String articlebody;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The articlebody
     */
    public String getArticlebody() {
        return articlebody;
    }

    /**
     * 
     * @param articlebody
     *     The articlebody
     */
    public void setArticlebody(String articlebody) {
        this.articlebody = articlebody;
    }

}
