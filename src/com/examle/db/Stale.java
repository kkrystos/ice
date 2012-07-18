
package com.examle.db;

import android.provider.BaseColumns;


import android.net.Uri;

public interface Stale extends BaseColumns {
    public static final String NAZWA_TABELI = "zdarzenia" ;
    
    public static final String URZAD = "com.examle.db" ;
    public static final Uri TRESC_URI = Uri.parse("content://"
        + URZAD + "/" + NAZWA_TABELI);
    public static final Uri TRESC_URI2 = Uri.parse("content://"
            + URZAD + "/" + "tabela");


    // Kolumny w bazie danych Zdarzenia
    public static final String NAME = "name" ;
    public static final String DATE = "date" ;
    public static final String BLOOD = "blood" ;
    public static final String CITY = "city" ;
    public static final String STREET = "street" ;
    public static final String ZIP = "zip" ;
    public static final String CONFIRM = "confirm" ;
    public static final String MEDICINE = "medicine" ;
    public static final String ALLERGY = "allergy" ;
    public static final String DOCTOR = "doctor" ;
    public static final String ORGAN = "organ" ;
    public static final String INSURANCE = "insurance" ;
    public static final String INSURANCE_ID = "insurance_id" ;

}

