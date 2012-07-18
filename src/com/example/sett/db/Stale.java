
package com.example.sett.db;

import android.provider.BaseColumns;


import android.net.Uri;

public interface Stale extends BaseColumns {
    public static final String NAZWA_TABELI = "zdarzenia" ;
    
    public static final String URZAD = "com.example.sett.db" ;
    public static final Uri TRESC_URI = Uri.parse("content://"
        + URZAD + "/" + NAZWA_TABELI);


    // Kolumny w bazie danych Zdarzenia
    public static final String SOSNR = "sos_nr" ;
    public static final String LANGUAGE = "language" ;
    public static final String ISVIS = "is_vis" ;


}

