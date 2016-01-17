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

    private static final String TAG_ = "MainActivityTAG_";
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        myWebView = (WebView) findViewById(R.id.a_web_wv);

        configureWebView();
        configureActionBar();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void configureWebView() {
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        Item item = getIntent().getParcelableExtra(Constants.ITEM_WEB_KEY);

        if (item != null)
            myWebView.loadUrl(item.getLink());
        else
            myWebView.loadUrl(Constants.DEFAULT_WEBSITE_URL);
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

}
