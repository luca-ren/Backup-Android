package com.example.sly.backupandroid;

/**
 * Created by Sly on 18/01/2017.
 */


public class creationDossierErreurException extends Exception {
    String erreur;

    /**
     * Constructeur par defaut.
     *
     * @param erreur
     */
    public creationDossierErreurException(String erreur){
        this.erreur = erreur;
    }

    /**
     * Retourne un chaine de caractere representant l'erreur
     * @return erreur
     */
    public String toString(){
        return this.erreur;
    }
}
