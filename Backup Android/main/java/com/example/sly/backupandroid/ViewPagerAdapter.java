package com.example.sly.backupandroid;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabAccueil tab1=new TabAccueil(); // création du premier fragment Accueil
                return tab1;
            case 1:
                TabSauvegarde tab2=new TabSauvegarde();  // création du deuxième fragment Sauvegarde
                return tab2;
            case 2:
                TabRestaurer tab3=new TabRestaurer();// création du troisième fragment Restaurer
                return tab3;
            default: return  null;

        }
    }

    public  CharSequence getPageTitle(int position){

        switch (position){

            case 0:

                    return "ACCUEIL";
            case 1:
                return "SAUVEGARDE";
            case 2:
                return "RESTAURER";

        }
        return "Default Text";
    }
    @Override
    public int getCount() {
        return 3;
    }
}
