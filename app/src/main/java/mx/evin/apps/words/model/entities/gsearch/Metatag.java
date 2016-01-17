
package mx.evin.apps.words.model.entities.gsearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metatag {

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

}
