
package com.example.sett.db;

import static com.example.sett.db.Stale.NAZWA_TABELI;
import static com.example.sett.db.Stale.SOSNR;
import static com.example.sett.db.Stale.LANGUAGE;
import static com.example.sett.db.Stale.ISVIS;
import static android.provider.BaseColumns._ID;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DaneZdarzen extends SQLiteOpenHelper {
    private static final String NAZWA_BAZY_DANYCH = "settings.db" ;
    private static final int WERSJA_BAZY_DANYCH = 1;

    /** Tworzy obiekt pomocniczy dla bazy Zdarzenia */
    public DaneZdarzen(Context ktks) {
        super(ktks, NAZWA_BAZY_DANYCH, null, WERSJA_BAZY_DANYCH);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE " + NAZWA_TABELI + " (" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SOSNR
            + " TEXT," + LANGUAGE + " TEXT," + ISVIS + " TEXT );" );
    }
    
    

    @Override
    public void onUpgrade(SQLiteDatabase bd, int staraWersja,
            int nowaWersja) {
        bd.execSQL("DROP TABLE IF EXISTS " + NAZWA_TABELI);
        onCreate(bd);
    }
}
