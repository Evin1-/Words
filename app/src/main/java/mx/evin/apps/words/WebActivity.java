package mx.evin.apps.words;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import mx.evin.apps.words.model.entities.gsearch.Item;
import mx.evin.apps.words.viewmodel.utils.Constants;

public class WebActivity extends AppCompatActivity {
    //TODO Put drawer logic in a single file (VM)
    //TODO Put info in single layout and not app_bar_web app_bar_main
    //TODO remove mItem and work only with urls

    private static final String TAG_ = "MainActivityTAG_";
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        myWebView = (WebView) findViewById(R.id.a_web_wv);
//        mItem = getIntent().getParcelableExtra(Constants.ITEM_WEB_KEY);
        String receivedTitle = getIntent().getStringExtra(Constants.TITLE_WEB_KEY);
        String receivedURL = getIntent().getStringExtra(Constants.URL_WEB_KEY);

        configureActionBar();
        configureWebView(receivedTitle, receivedURL);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void configureWebView(String title, String url) {
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                updateTitleBar(view.getTitle(), url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        if (title != null && url != null) {
            myWebView.loadUrl(url);
            updateTitleBar(title, url);
        }else {
            myWebView.loadUrl(Constants.DEFAULT_WEBSITE_URL);
            updateTitleBar(getString(R.string.a_web_default_website_title), Constants.DEFAULT_WEBSITE_URL);
        }
    }

    private void configureActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.a_web_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private void updateTitleBar(String title, String url){
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){
            if (title.length() > 0)
                actionBar.setTitle(title);
            if (url.length() > 0)
                actionBar.setSubtitle(url);
        }
    }

}
