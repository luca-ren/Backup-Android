package com.example.sly.backupandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

/**
 * Created by Sly on 11/12/2016.
 */

public class TabRestaurer extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.tab3, container, false);


        return rootView;
    }

    public void creationvue(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int i) {


        View rootView = inflater.inflate(R.layout.tab3, container, false);


    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button premier=(RadioButton) view.findViewById(R.id.valider);



    }

    public static class WebActivity {
    }
}
