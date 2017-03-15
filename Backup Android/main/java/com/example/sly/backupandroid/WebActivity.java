package com.example.sly.backupandroid;

/**
 * Created by Sly on 04/01/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.Button;


public class WebActivity extends AppCompatActivity {


    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud);

     //   Button gogoogle=(Button) findViewById(R.id.googledrive);
       // Button godropbox=(Button) findViewById(R.id.dropbox1);

//        if(gogoogle.isSelected()) {


            webView = (WebView) findViewById(R.id.webView1);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("https://www.google.com/drive/");

//     }

  //    else if(godropbox.isSelected()){

          //  webView = (WebView) findViewById(R.id.webView1);
            //webView.getSettings().setJavaScriptEnabled(true);
            //webView.loadUrl("https://www.dropbox.com/");

//     }

    }















}
