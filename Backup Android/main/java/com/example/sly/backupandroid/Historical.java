package com.example.sly.backupandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Sly on 05/01/2017.
 */

public class Historical extends AppCompatActivity{
private Button validersave=null;
    private TextView t1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historique);


         validersave=(Button) findViewById(R.id.valider);
        this.validersave=validersave;
       ScrollView scroll=(ScrollView) findViewById(R.id.ascenseur);


    //  if(this.validersave.isEnabled()){
            Toast.makeText(getApplicationContext(),"Restauration en cours",Toast.LENGTH_LONG).show();
            t1=new TextView(this);

            t1.setText("Sauvegarde");
            scroll.addView(t1);

      // }









    }

}
