package com.example.project1;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Person implements Parcelable {
    private String imie;
    private String nazwisko;
    private double dlug;
    public static List<Person> personList = new ArrayList<>();


    public Person(String imie, String nazwisko, double dlug) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.dlug = dlug;
    }
    public Person(Parcel in){
        String[] data = new String[3];
        in.readStringArray(data);
        imie = data[0];
        nazwisko = data[1];
        dlug = Double.parseDouble(data[2]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{imie,nazwisko, String.valueOf(dlug)});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Person createFromParcel(Parcel in){
            return new Person(in);
        }
        @Override
        public Person[] newArray(int size){
            return new Person[size];
        }
    };
    public static void addPerson(Person person){
        personList.add(person);
    }

    public String getImie() {
        return imie;
    }
    public void setImie(String imie) {
        this.imie = imie;
    }
    public String getNazwisko() {
        return nazwisko;
    }
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    public double getDlug() {
        return dlug;
    }
    public void setDlug(double dlug) {
        this.dlug = dlug;
    }

    @Override
    public String toString() {
        return imie + " " + nazwisko + " : " + dlug;
    }
}