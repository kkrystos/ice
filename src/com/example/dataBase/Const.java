package com.example.dataBase;

import android.provider.BaseColumns;

import android.net.Uri;

public interface Const extends BaseColumns {
    public static final String NAZWA_TABELI = "zdarzenia" ;
    
    public static final String URZAD = "com.example.dataBase" ;
    public static final Uri TRESC_URI = Uri.parse("content://"
        + URZAD + "/" + NAZWA_TABELI);


    // Kolumny w bazie danych Zdarzenia
    public static final String NUMER = "numer" ;
    public static final String NAZWA = "nazwa" ;
    public static final String PHOTO = "photo" ;

}