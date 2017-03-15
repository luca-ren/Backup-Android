package com.example.sly.backupandroid;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import java.util.Date;

/**
 * Created by CSMLJ on 15/11/2016.
 */

public class CallLogs {

    private long id; //Emplacement du journal d'appel(pas nécessaire à l'insertion)
    private String nom; //Nom fdu contact
    private String num; //Numéro du contact
    private Long date; //Date de l'appel en millisecondes(pouvant etre converti en format date h:m:s)
    private int duree; //Duree de l'appel en millisecondes
    private int type; //Type d'appel(Entrant/Sortant/Annule)
    private int cn; //variable nécessaire à l'insertion

    public CallLogs() { //Constructeur par défaut
    }

    //Constructeur champs à champs
    public CallLogs(long id, String nom, String num, Long date, int duree, int type,int cn) {
        super();
        this.id = id;
        this.nom = nom;
        this.num = num;
        this.date = date;
        this.duree = duree;
        this.type = type;
        this.cn = cn;
    }


    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    public String getNom() {

        return nom;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {

        this.num = num;
    }

    public Long getDate() {

        return date;
    }

    public void setDate(Long date) {

        this.date = date;
    }

    public int getDuree() {

        return duree;
    }

    public void setDuree(int duree) {

        this.duree = duree;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {

        this.type = type;
    }

    public int getCn() {
        return cn;
    }

    public void setCn(int cn) {
        this.cn = cn;
    }

}