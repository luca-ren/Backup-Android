package com.example.sly.backupandroid;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.Telephony;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.Xml;
import android.view.TouchDelegate;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.RequiresApi;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.attr.button;
import static android.R.attr.height;
import static android.R.attr.layout_weight;
import static android.R.attr.widgetLayout;
import static com.example.sly.backupandroid.R.layout.tab1;
import static com.example.sly.backupandroid.R.layout.tab3;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static File dossierParent = new File(Environment.getExternalStorageDirectory() + File.separator, "Backup & Restore");
    private static File dossierSave = new File(dossierParent + File.separator, "Sauvegarde");
    private static File dossierSaveSms = new File(dossierSave + File.separator, "SMS");
    private static File dossierSaveContacts = new File(dossierSave + File.separator, "Contacts");
    private static File dossierSaveCallLogs = new File(dossierSave + File.separator, "Journal d'appels");

    private TabRestaurer t;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private Handler handler = new Handler();
    int progressStatus = 0;
    //  private TabRestaurer tt=new TabRestaurer();
    private TabRestaurer z;
    private TextView textView;
    private WebView webView;
    //  int compteursave=0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Activity mainActivity = this;

       /* final int lHeight = 200;
        final int lWidth = 200;
        final LinearLayout s=(LinearLayout) findViewById(R.id.restaurer);
        final Button t=new Button(this);
            t.setText("gooo");
        s.addView(t,new LinearLayout.LayoutParams(lHeight,lWidth));*/

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        final TabLayout.Tab ACCUEIL = tabLayout.newTab();
        final TabLayout.Tab SAUVEGARDER = tabLayout.newTab();
        final TabLayout.Tab RESTAURER = tabLayout.newTab();

        ACCUEIL.setText("ACCUEIL");
        SAUVEGARDER.setText("SAUVEGARDER");
        RESTAURER.setText("RESTAURER");


        //ACCUEIL.setIcon(R.drawable.accueil_white);
        //SAUVEGARDER.setIcon(R.drawable.sauvegarder);
        //RESTAURER.setIcon(R.drawable.restaurer);


        View FaceView = getLayoutInflater().inflate(R.layout.custom_tab, null);
        ImageView face = (ImageView) FaceView.findViewById(R.id.image);
        TextView textface = (TextView) FaceView.findViewById(R.id.text_view);
        face.setImageResource(R.drawable.accueil);
        textface.setText("ACCUEIL");
        textface.setTextColor(Color.parseColor("#FFFFFF"));
        textface.setTextSize(12);


        View FaceView2 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        ImageView face2 = (ImageView) FaceView2.findViewById(R.id.image);
        TextView textface2 = (TextView) FaceView2.findViewById(R.id.text_view);
        face2.setImageResource(R.drawable.sauvegarde);
        textface2.setText("SAUVEGARDER");
        textface2.setTextSize(12);
        textface2.setTextColor(Color.parseColor("#FFFFFF"));


        View FaceView3 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        ImageView face3 = (ImageView) FaceView3.findViewById(R.id.image);
        TextView textface3 = (TextView) FaceView3.findViewById(R.id.text_view);
        face3.setImageResource(R.drawable.restaurer);
        textface3.setText("RESTAURER");
        textface3.setTextColor(Color.parseColor("#FFFFFF"));
        textface3.setTextSize(12);

        tabLayout.addTab(ACCUEIL, 0);
        tabLayout.addTab(SAUVEGARDER, 1);
        tabLayout.addTab(RESTAURER, 2);

        ACCUEIL.setCustomView(FaceView);
        SAUVEGARDER.setCustomView(FaceView2);
        RESTAURER.setCustomView(FaceView3);

        callPermissionBeforeAction(getApplicationContext(), this);


        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                final Button validation = (Button) findViewById(R.id.valider);
                validation.setOnClickListener(new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onClick(View v) {
                        CheckBox messsages = (CheckBox) findViewById(R.id.checkBox5);
                        CheckBox contacts = (CheckBox) findViewById(R.id.checkBox8);
                        CheckBox journalAppels = (CheckBox) findViewById(R.id.checkBox13);
                        final ProgressBar barre = (ProgressBar) findViewById(R.id.progressBar);
                        final TextView textView = (TextView) findViewById(R.id.textView);

                        if (messsages.isChecked() || contacts.isChecked() || journalAppels.isChecked()) {

                            try {
                                creationDossier(dossierParent);
                                creationDossier(dossierSave);
                                creationDossier(dossierSaveSms);
                                creationDossier(dossierSaveContacts);
                                creationDossier(dossierSaveCallLogs);
                                Log.d("CreationDossier", "Création des dossiers de sauvegarde réussie");
//                                Toast.makeText(getApplicationContext(), "Dossiers créer avec succès", Toast.LENGTH_LONG).show();
                            } catch (creationDossierErreurException e) {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                Log.e("CreationExceptionErreur", "Exception lors de la création du dossier de sauvegarde");
                            }

                            if (messsages.isChecked()) {
                                if (callPermissionBeforeAction(getApplicationContext(), mainActivity, "android.permission.READ_SMS")) {
                                    extractionSms(getApplicationContext().getContentResolver(), dossierSaveSms);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Autoriser les permissions SMS pour pouvoir effectuer une sauvegarde", Toast.LENGTH_LONG).show();
                                    Log.d("Permissions", "Permissions non acquise pour sauvegarde");
                                }
                            }

                            if (journalAppels.isChecked()) {
                                if (callPermissionBeforeAction(getApplicationContext(), mainActivity, "android.permission.READ_CALL_LOG")) {
                                    List<CallLogs> lc = arrayCl();
                                    saveCl(lc, getApplicationContext().getContentResolver(), dossierSaveCallLogs);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Autoriser les permissions SMS pour pouvoir effectuer une sauvegarde", Toast.LENGTH_LONG).show();
                                    Log.d("Permissions", "Permissions non acquise pour sauvegarde");
                                }
                            }


                            // compteursave++;
                            Toast.makeText(getApplicationContext(), "Sauvegarde en cours", Toast.LENGTH_LONG).show();
                            barre.setVisibility(View.VISIBLE);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (progressStatus < 100) {

                                        progressStatus += 1;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {

                                                barre.setProgress(progressStatus);

                                                textView.setText("Progression " + progressStatus + "/" + "100");
                                                textView.setTextColor(Color.WHITE);


                                            }
                                        });
                                        try {
                                            sleep(100);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }


                                    }


                                }
                            }).start();

                            /*
                          Intent a=new Intent(MainActivity.this,ChoixSauvegarde.class);
                            startActivity(a);*/
                            //setContentView(R.layout.tab3)
                         /* if(progressStatus==0){
                               // LinearLayout restauration=(LinearLayout) findViewById(tab3);
                               // restauration.addView(validation);
                                //Button validationRestaurer=(Button) findViewById(R.id.validerRestauration);
                                //

                              RelativeLayout test =(RelativeLayout) findViewById(R.id.restaurer);
                              test.addView(validation);
                            }

                            /*if(progressStatus==100){
                                barre.setVisibility(View.INVISIBLE);
                                textView.setVisibility(View.INVISIBLE);
                               progressStatus=0;
                                barre.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.VISIBLE);

                            }*/


                            //}
                            if (progressStatus == 100) {
                                progressStatus = 0;
                                //Object container = new Object();
                                // Bundle savedInstanceState = new Bundle();

                                //     z.onDestroyView();
                                // z.onCreateView(getLayoutInflater(),ViewGroup container,Bundle savedInstanceState);
                                //View a=new View(R.layout.tab3);
                                //viewPager.addView(a);


                              /*  getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.tab_layout,new TabSauvegarde())
                                        .commit();*/


                                // LinearLayout restauration = (LinearLayout) findViewById(R.id.restaurer);
                                //restauration.addView(validation);
                                // RemoteViews a= new RemoteViews(getBaseContext().getPackageName(),R.layout.tab3);

                            }
                        }
                    }

                });

                Button a = (Button) findViewById(R.id.validerRestauration);
                a.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View vv) {

                        RadioButton journalAppels = (RadioButton) findViewById(R.id.radioButton2);

                        //-----------Restauration journal d'appel------------//
                        if (journalAppels.isChecked()) {
                            if (callPermissionBeforeAction(getApplicationContext(), mainActivity, "android.permission.WRITE_CALL_LOG")) {
                                List<CallLogs> lc = loadCl(dossierSaveCallLogs);
                                String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
                                Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, strOrder);
                                restoreCl(lc, managedCursor);
                            } else {
                                Toast.makeText(getApplicationContext(), "Autoriser les permissions du journal d'appel pour pouvoir effectuer une restauration", Toast.LENGTH_LONG).show();
                                Log.d("Permissions", "Permissions non acquise pour restaure");
                            }
                        }
                        //------------------------------------------------//
                        Toast.makeText(getApplicationContext(), "Restauration en cours", Toast.LENGTH_LONG).show();
                        //LinearLayout q=(LinearLayout) findViewById(R.id.restaurer);
                        //q.updateViewLayout(tab3,widgetLayout,height);


                    }


                });

            }

            public void onPageSelected(int position) {

                switch (position) {
                    case 0: {

                        ACCUEIL.setIcon(R.drawable.accueil_white);


                        SAUVEGARDER.setIcon(R.drawable.sauvegarde);
                        RESTAURER.setIcon(R.drawable.restaurer);
                        break;
                    }
                    case 1: {

                        ACCUEIL.setIcon(R.drawable.accueil_white);
                        SAUVEGARDER.setIcon(R.drawable.sauvegarder_white);
                        RESTAURER.setIcon(R.drawable.restaurer);
                        break;

                    }
                    case 2: {
                        ACCUEIL.setIcon(R.drawable.accueil_white);
                        SAUVEGARDER.setIcon(R.drawable.sauvegarde);
                        RESTAURER.setIcon(R.drawable.restaurer_white);
                        break;
                    }
                }
            }


            public void onPageScrollStateChanged(int state) {

            }

        });


    }

    //public int getCompteursave(){return this.compteursave;}

    public void mofiflLayout(Button a) {


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab1, container, false);


    }


    private void initApplyButtonOnClick() {


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent a = new Intent(MainActivity.this, Cloud.class);
            startActivity(a);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent a = new Intent(MainActivity.this, Historical.class);
            startActivity(a);

        } else if (id == R.id.nav_slideshow) {
            Intent a = new Intent(MainActivity.this, ChoixSauvegarde.class);
            startActivity(a);
        } else if (id == R.id.nav_manage) {
            Intent a = new Intent(MainActivity.this, ChoixSauvegarde.class);
            startActivity(a);

        } else if (id == R.id.nav_share) {
            Intent a = new Intent(MainActivity.this, ChoixSauvegarde.class);
            startActivity(a);

        } else if (id == R.id.nav_send) {
            Intent a = new Intent(MainActivity.this, WebActivity.class);
            startActivity(a);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Creer un dossier dont le chemin est passer en parametre.
     *
     * @param dossier
     * @throws creationDossierErreurException
     * @see creationDossierErreurException
     */
    public void creationDossier(File dossier) throws creationDossierErreurException {
        if (!dossier.exists() && !dossier.isDirectory())
            if (dossier.mkdirs())
                Log.d("Dossier créer", dossier.getPath());
            else
                throw new creationDossierErreurException("Erreur lors de la création du dossier");
    }

    /**
     * Effectue toutes les verifications de permissions avant d'effectuer une action
     * @param applicationContext
     * @param activity
     * @return true si toutes les permissions ont été donner, false si au moins une des permissions est manquante
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean callPermissionBeforeAction(Context applicationContext, Activity activity) {
        List<String> listePermissions = checkPermissions(applicationContext);

        if (!listePermissions.isEmpty()) {
            String[] tableauPermissions = listePermissions.toArray(new String[0]);
            Log.d("Permissions", Arrays.toString(tableauPermissions));
            askForPermissions(activity, tableauPermissions);
        }

        if (checkPermissions(applicationContext).isEmpty()) return true;
        else return false;
    }

    /**
     * Vérifie toutes les permissions requises et retourne les permissions non accordées
     * @param myContext
     * @return List<String>
     */
    public static List<String> checkPermissions(Context myContext) {
        List<String> listePerm = new ArrayList<String>();

        if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED)
            listePerm.add("android.permission.WRITE_CONTACTS");

        if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            listePerm.add("android.permission.SEND_SMS");

        if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED)
            listePerm.add("android.permission.WRITE_CALL_LOG");

        if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            listePerm.add("android.permission.WRITE_EXTERNAL_STORAGE");

        if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            listePerm.add("android.permission.READ_EXTERNAL_STORAGE");

        return listePerm;
    }

    /**
     * Demande à l'utilisateur l'accès aux permissions passées en paramètres
     * @param listePermissions
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void askForPermissions(Activity activity, String[] listePermissions) {
        activity.requestPermissions(listePermissions, 001);
    }

    /** Fonction permettant de créer un fichier XML et de sauvegarder les sms directement dans ce fichier XML
     * @param contentResolver
     * @param dossierSaveSms
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void extractionSms(ContentResolver contentResolver, File dossierSaveSms) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss");
        String date = dateFormat.format(Calendar.getInstance().getTime());

        File saveSmsXml = new File(dossierSaveSms + File.separator, "test" /*date.toString()*/ + ".xml");
        try {
            saveSmsXml.createNewFile();
            Log.d("CréationFichier", "Fichier de sauvegarde créer avec succès");
        } catch (IOException e) {
            Log.e("IOException", "Exception lors de la création du fichier de sauvegarde");
        }

        FileOutputStream fileSms = null;

        try {
            fileSms = new FileOutputStream(saveSmsXml);
        } catch (FileNotFoundException e) {
            Log.e("FileNotFoundException", "Exception lors de l'écriture du fichier de sauvegarde");
        }

        XmlSerializer serializer = Xml.newSerializer();

        try {
            serializer.setOutput(fileSms, "UTF-8");
            serializer.startDocument(null, true);
            serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            Log.d("extractionSMS", "Ecriture de l'entete du fichier XML");
        } catch (Exception e) {
            Log.e("extractionSMS", "Erreur lors de l'écriture de l'entete du fichier XML");
        }

        //  int nbConversation = nbConversations(contentResolver);

        //   for(int i = 1; i <= nbConversation; i++){
        String[] projection = new String[]{
                Telephony.TextBasedSmsColumns.THREAD_ID,
                Telephony.TextBasedSmsColumns.ADDRESS,
                Telephony.TextBasedSmsColumns.DATE,
                Telephony.TextBasedSmsColumns.DATE_SENT,
                Telephony.TextBasedSmsColumns.READ,
                Telephony.TextBasedSmsColumns.TYPE,
                Telephony.TextBasedSmsColumns.BODY};

        final Cursor cursorInbox = contentResolver.query(Telephony.Sms.Inbox.CONTENT_URI, projection, null, null, null);
        final Cursor cursorSent = contentResolver.query(Telephony.Sms.Sent.CONTENT_URI, projection, null, null, null);

        try {
            smsSave(cursorSent, serializer);
            smsSave(cursorInbox, serializer);
        } catch (Exception e) {
            Log.e("extractionSms", "Exception lors de l'écriture des données");
        }

        // }

        try {
            serializer.endDocument();
            serializer.flush();
            fileSms.close();
            Log.d("extractionSMS", "Ecriture du pied du fichier XML");
            Toast.makeText(getApplicationContext(), "SMS sauvegardés", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("extractionSms", "Erreur lors de l'écriture du pied du fichier XML");
        }

    }

    /** Fonction permettant de sauvegarder les sms directement dans le fichier XML passé en paramètre
     * @param cursor
     * @param serializer
     */
    public void smsSave(Cursor cursor, XmlSerializer serializer) {
        if (cursor == null) {
            Log.e("smsSave", "Impossible d'extraire les sms");
            return;
        }

        if (cursor.moveToFirst()) {
            try {

                do {
                    serializer.startTag(null, "sms");
                    serializer.attribute(null, "address", cursor.getString(cursor.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.ADDRESS)));
                    serializer.attribute(null, "date", cursor.getString(cursor.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.DATE)));
                    serializer.attribute(null, "date_sent", cursor.getString(cursor.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.DATE_SENT)));
                    serializer.attribute(null, "read", cursor.getString(cursor.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.READ)));
                    serializer.attribute(null, "type", cursor.getString(cursor.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.TYPE)));
                    serializer.attribute(null, "body", cursor.getString(cursor.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.BODY)));
                    serializer.attribute(null, "thread_id", cursor.getString(cursor.getColumnIndexOrThrow(Telephony.TextBasedSmsColumns.THREAD_ID)));
                    serializer.endTag(null, "sms");
                } while (cursor.moveToNext());

                Log.d("smsSave", "Ecriture du flux de données XML");

            } catch (Exception e) {
                Log.e("smsSave", "Exception lors de l'écriture du flux de données XML");
            }

        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
    }

    public List<CallLogs> arrayCl() {

        List<CallLogs> lc = new ArrayList<CallLogs>();
        long i = 1;

        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, strOrder);

        int Name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int cnew = managedCursor.getColumnIndex(CallLog.Calls.NEW);


        while (managedCursor.moveToNext()) {

            String callName = managedCursor.getString(Name);
            String phNum = managedCursor.getString(number);
            int callTypeCode = managedCursor.getInt(type);
            Long callDate = managedCursor.getLong(date);
            //Date callDayTime = new Date(Long.valueOf(callDate));
            int callDuration = managedCursor.getInt(duration);
            int cn = managedCursor.getInt(cnew);
            //String callType = null;

            if (callName == null) {
                callName = "Unknown";
            }

            /*int callcode = Integer.parseInt(callTypeCode);
            switch (callcode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    callType = "Outgoing";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    callType = "Incoming";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    callType = "Missed";
                    break;
            }*/

            CallLogs c = new CallLogs();
            c.setId(i);
            c.setNom(callName);
            c.setNum(phNum);
            c.setDate(callDate);
            c.setDuree(callDuration);
            c.setType(callTypeCode);
            c.setCn(cn);

            lc.add(c);

            i++;
        }
        return lc;

    }

    /**
     * Sauvegarde le journal d'appel du teléphone dans un fichier XML
     *
     * @param lc
     * @param resolver
     * @param saveCallLogs
     */

    public void saveCl(List<CallLogs> lc, ContentResolver resolver, File saveCallLogs) {

        File saveCallLogsXml = new File(dossierSaveCallLogs + File.separator, "Call_Logs" + ".xml");
        /*try {
            saveCallLogsXml.createNewFile();
            Log.d("CréationFichier", "Fichier de sauvegarde créer avec succès");
        } catch (IOException e) {
            Log.e("IOException", "Exception lors de la création du fichier de sauvegarde");
        }*/

        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        //---------------------------Sauvegarde du journal d'appel -----------------------------//
        try {
            xmlSerializer.setOutput(writer);
            xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            xmlSerializer.startDocument("UTF-8", true);
            long i = 0;
            for (CallLogs c : lc) {
                i = c.getId(); //Pour compter le nombre de journal d'appel
            }

            xmlSerializer.startTag(null, "Call_Logs");
            xmlSerializer.attribute("", "count", String.valueOf(i));
            for (CallLogs ca : lc) {
                Date callDayTime = new Date(ca.getDate());
                //Long millis = ca.getDate();

                //---------Insertion des valeurs du journal d'appel dans le fichier XML---------//
                xmlSerializer.startTag(null, "Log");

                xmlSerializer.attribute("", "Id", String.valueOf(ca.getId()));
                xmlSerializer.attribute("", "Nom", ca.getNom());
                xmlSerializer.attribute("", "Numero", ca.getNum());
                xmlSerializer.attribute("", "Date", String.valueOf(ca.getDate()));
                xmlSerializer.attribute("", "Time", String.valueOf(callDayTime));
                xmlSerializer.attribute("", "Duree", String.valueOf(ca.getDuree()));
                xmlSerializer.attribute("", "Type", String.valueOf(ca.getType()));
                xmlSerializer.attribute("", "New", String.valueOf(ca.getCn()));

                xmlSerializer.endTag(null, "Log");
            }
            xmlSerializer.endTag(null, "Call_Logs");
            //----------------------------------------------------------------------------------//
            xmlSerializer.endDocument();
            xmlSerializer.flush();
            String dataWrite = writer.toString();
            writer.flush();
            writer.close();
            FileOutputStream fileCl = null;
            try {
                fileCl = new FileOutputStream(saveCallLogsXml);
            } catch (FileNotFoundException e) {
                Log.e("FileNotFoundException", "Exception lors de l'écriture du fichier de sauvegarde");
            }

            fileCl.write(dataWrite.getBytes());
            fileCl.close();

            Toast.makeText(getApplicationContext(), "Journal d'appel sauvegardé", Toast.LENGTH_SHORT).show();
            //----------------------------------------------------------------------------------//

        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Charge le journal d'appel sauvegarde dans un fichier XML dans une ArrayList de journal d'appel
     *
     * @return List<CallLogs>
     */
    public List<CallLogs> loadCl(File SaveCallLogs) {
        List<CallLogs> lc = new ArrayList<CallLogs>();
        XmlPullParserFactory factory = null;
        File saveSmsXml =new File(dossierSaveCallLogs + File.separator, "Call_Logs" + ".xml");
        //----------------Lecture du fichier de sauvegarde(en XML)----------------//
        try {
            FileInputStream fis = new FileInputStream(saveSmsXml);
            InputStreamReader isr = new InputStreamReader(fis);
            char[] inputBuffer = new char[fis.available()];
            isr.read(inputBuffer);
            String data = new String(inputBuffer);
            isr.close();
            fis.close();
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = null;
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            int eventType = 0;
            //-------Stockage des donnees du fichier xml dans l'arraylist-------//
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:

                        break;

                    case XmlPullParser.END_TAG:
                        if (name.equals("Log")) {
                            long id;
                            String nom;
                            String num;
                            Long date;
                            int duree;
                            int type;
                            int cn;
                            id = Long.parseLong(xpp.getAttributeValue(null, "Id"));
                            nom = xpp.getAttributeValue(null, "Nom");
                            num = xpp.getAttributeValue(null, "Numero");
                            date = Long.valueOf(xpp.getAttributeValue(null, "Date"));
                            duree = Integer.parseInt(xpp.getAttributeValue(null, "Duree"));
                            type = Integer.parseInt(xpp.getAttributeValue(null, "Type"));
                            cn = Integer.parseInt(xpp.getAttributeValue(null, "New"));
                            CallLogs c = new CallLogs(id, nom, num, date, duree, type, cn);
                            lc.add(c);
                        }
                        break;
                }
                eventType = xpp.next();
                //----------------------------------------------------------------------//


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        //-------------------------------------------------------------------------//
        return lc;
    }

    /**
     * Restaure le journal d'appel dans le teléphone grace à l'ArrayList de journal d'appel passer en parametre
     *
     * @param lc
     * @param managedCursor
     */
    public void restoreCl(List<CallLogs> lc, Cursor managedCursor) {

        int i = 0;
        boolean flag = true;
        int nb = 0;
        //------------------Restauration des journaux d'appels------------------//
        for (CallLogs c : lc) {//Parcours les journaux d'appels sauvegarde
            managedCursor.moveToFirst();//A chaque itération on remet le curseur au début
            managedCursor.moveToPrevious();//On s'assure que le curseur est bien au début
            while (managedCursor.moveToNext()) {//Parcours les journaux d'appels contenu dans le telephone
                int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
                Long callDate = managedCursor.getLong(date);

                if (callDate.equals(c.getDate())) {//Si la date du journal d'appel sauvegarde (en milisecondes) est identique a celle d'un journal d'appel qui se trouve dans le telephone on sors de la boucle
                    flag = false;
                    break;
                }
            }

            if (flag) {//Si le flag indique qu'il n'a pas trouve de journal d'appel identique
                //---------Insertion journal d'appel dans le téléphone---------//
                String strNum = c.getNum();
                while (strNum.contains("-")) {
                    strNum = strNum.substring(0, strNum.indexOf('-')) + strNum.substring(strNum.indexOf('-') + 1, strNum.length());
                }

                ContentValues values = new ContentValues();
                int callcode = c.getType();
                values.put(CallLog.Calls.NUMBER, strNum);
                values.put(CallLog.Calls.DATE, c.getDate());
                values.put(CallLog.Calls.DURATION, c.getDuree());
                switch (callcode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        values.put(CallLog.Calls.TYPE, CallLog.Calls.OUTGOING_TYPE);
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        values.put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE);
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        values.put(CallLog.Calls.TYPE, CallLog.Calls.MISSED_TYPE);
                        break;
                }
                values.put(CallLog.Calls.NEW, c.getCn());
                values.put(CallLog.Calls.CACHED_NAME, c.getNom());
                values.put(CallLog.Calls.CACHED_NUMBER_TYPE, 0);
                values.put(CallLog.Calls.CACHED_NUMBER_LABEL, "");


                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                getBaseContext().getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);

                //------------------------------------------------------------//

            } else {
                nb++;//sert à savoir le nombre de journal d'appel non inserer
            }
            flag = true;
            i++;
        }

        //----------------------------------------------------------------------//
        if(i == nb){
            Toast.makeText(getApplicationContext(), "Rien à ajouter", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Nombre d'appel ajoutés = " + (i -nb), Toast.LENGTH_LONG).show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public int nbConversations(ContentResolver contentResolver){
        final Cursor cursorInbox = contentResolver.query(Telephony.Sms.Conversations.CONTENT_URI , null, null, null, null);
        return cursorInbox.getCount();
    }

    /**
     * Verifie si l'application a une permission spécifique
     * @param applicationContext
     * @param activity
     * @param myPermission
     * @return boolean
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean callPermissionBeforeAction(Context applicationContext, Activity activity, String myPermission){
        if (ContextCompat.checkSelfPermission(applicationContext, myPermission) == PackageManager.PERMISSION_GRANTED)
            return true;
        else {
            activity.requestPermissions(new String[]{myPermission}, 001);
            return false;
        }
    }
}



/*final File[] sortedByDate = dossierSaveSms.listFiles();
            if (sortedByDate != null && sortedByDate.length > 1) {
                Arrays.sort(sortedByDate, new Comparator<File>() {
                    @Override
                    public int compare(File object1, File object2) {
                        return (int) ((object1.lastModified() > object2.lastModified()) ? object1.lastModified(): object2.lastModified());
                    }
                });
            }

            String traitementFichierSave = null;
            for(File file : sortedByDate){
                traitementFichierSave = file.getAbsolutePath();
                Log.d("readSms", "Last file saved : " + traitementFichierSave.toString());
                break;
            }*/