package mx.evin.apps.words;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import mx.evin.apps.words.model.entities.gsearch.Item;
import mx.evin.apps.words.viewmodel.utils.Constants;

public class WebActivity extends AppCompatActivity {
    //TODO Put drawer logic in a single file (VM)
    //TODO Put info in single layout and not app_bar_web app_bar_main

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView myWebView = (WebView) findViewById(R.id.a_web_wv);

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
}
