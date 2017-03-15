package com.example.sly.backupandroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Sly on 11/12/2016.
 */
public class TabAccueil extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.tab1, container, false);
        View rootView = inflater.inflate(R.layout.tab2, container, false);

        return rootView;
    }
}
