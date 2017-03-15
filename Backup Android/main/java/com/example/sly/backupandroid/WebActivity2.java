package com.example.sly.backupandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by Sly on 04/01/2017.
 */

public class WebActivity2 extends AppCompatActivity {

    private WebView webView2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud);

        webView2 = (WebView) findViewById(R.id.webView1);
        webView2.getSettings().setJavaScriptEnabled(true);
        webView2.loadUrl("https://www.dropbox.com/");


    }
}