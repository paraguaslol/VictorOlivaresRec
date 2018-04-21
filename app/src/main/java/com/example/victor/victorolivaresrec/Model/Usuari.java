package com.example.victor.victorolivaresrec.Model;

import java.io.Serializable;

/**
 * Created by victo on 15/04/2018.
 */

public class Usuari implements Serializable {

    private String nomusuari,nom,cognoms,email,adreça,auth;

    public Usuari(String nomusuari, String nom, String cognoms, String email, String adreça, String auth) {
        this.nomusuari = nomusuari;
        this.nom = nom;
        this.cognoms = cognoms;
        this.email = email;
        this.adreça = adreça;
        this.auth = auth;
    }

    public Usuari(){

    }

    public String getNomusuari() {
        return nomusuari;
    }

    public void setNomusuari(String nomusuari) {
        this.nomusuari = nomusuari;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdreça() {
        return adreça;
    }

    public void setAdreça(String adreça) {
        this.adreça = adreça;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

}
