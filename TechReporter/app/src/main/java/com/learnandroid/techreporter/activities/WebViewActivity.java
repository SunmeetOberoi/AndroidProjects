package com.learnandroid.techreporter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.learnandroid.techreporter.R;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        final ProgressBar loadPage = (ProgressBar)findViewById(R.id.pbLoadNews);
        //Start ProgressBar
        loadPage.setVisibility(View.VISIBLE);

        WebView webView = (WebView) findViewById(R.id.wvWebView);
        String URL = getIntent().getStringExtra("URL");
        if(URL.equals("https://newsapi.org/"))
            webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //Stop ProgressBar
                loadPage.setVisibility(View.GONE);
            }
        });

        webView.loadUrl(URL);
    }
}
