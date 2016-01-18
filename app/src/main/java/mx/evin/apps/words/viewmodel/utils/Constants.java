package mx.evin.apps.words.viewmodel.utils;

/**
 * Created by evin on 1/15/16.
 */
public class Constants {
    public static final String PREFERENCE_FILE_KEY = "mx.evin.apps.words";
    public static final String LAST_TERM_KEY = "last_term";
    public static final String FRAGMENT_TAG = "fragment_add";
    public static final String GOOGLE_API_KEY_TAG = "GOOGLE_API_KEY";
    public static final String GOOGLE_CUSTOM_SEARCH_KEY_TAG = "GOOGLE_CUSTOM_SEARCH";
    public static final String TITLE_WEB_KEY = "title_to_web_view";
    public static final String URL_WEB_KEY = "url_to_web_view";
    public static final String DEFAULT_WEBSITE_URL = "http://developer.android.com/index.html";
    public static final String TECHNOLOGY_USED_TAG = "technology_tag";
    public static final String DEFAULT_TECHNOLOGY = "Android";

    public enum TYPE_ADD {
        WRITTEN,
        SPOKEN,
        GOOGLED
    }

    public enum TYPE_HTML {
        BODY,
        HIERARCHY
    }
}
