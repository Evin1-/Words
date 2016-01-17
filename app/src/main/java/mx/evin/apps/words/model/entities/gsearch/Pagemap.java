
package mx.evin.apps.words.model.entities.gsearch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Pagemap {

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

}
