package com.example.sly.backupandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

/**
 * Created by Sly on 04/01/2017.
 */

public class Cloud extends AppCompatActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud);
        final Button dropbox1 = (Button) findViewById(R.id.dropbox1);
        final Button gogoogle = (Button) findViewById(R.id.googledrive);
        gogoogle.setOnClickListener(new View.OnClickListener() {

            public void onClick(View a) {

                Intent intent = new Intent(Cloud.this, WebActivity.class);
                startActivity(intent);

                if(dropbox1.isSelected()){
                    Intent intent2 = new Intent(Cloud.this, WebActivity2.class);
                    startActivity(intent2);


                }


            }

        });


        dropbox1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View a) {

                Intent intent = new Intent(Cloud.this, WebActivity2.class);
                startActivity(intent);



            }

        });


    }

    public void onBackPressed() {

        WebView webView = (WebView) findViewById(R.id.webView1);
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}