
package com.examle.db;

import static com.examle.db.Stale.NAZWA_TABELI;

import static com.examle.db.Stale.NAME;
import static com.examle.db.Stale.DATE;
import static com.examle.db.Stale.BLOOD;
import static com.examle.db.Stale.CITY;
import static com.examle.db.Stale.STREET;
import static com.examle.db.Stale.ZIP;
import static com.examle.db.Stale.CONFIRM;
import static com.examle.db.Stale.MEDICINE;
import static com.examle.db.Stale.ALLERGY;
import static com.examle.db.Stale.DOCTOR;
import static com.examle.db.Stale.ORGAN;
import static com.examle.db.Stale.INSURANCE;
import static com.examle.db.Stale.INSURANCE_ID;
import static android.provider.BaseColumns._ID;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DaneZdarzen extends SQLiteOpenHelper {
    private static final String NAZWA_BAZY_DANYCH = "information.db" ;
    private static final int WERSJA_BAZY_DANYCH = 5;

    /** Tworzy obiekt pomocniczy dla bazy Zdarzenia */
    public DaneZdarzen(Context ktks) {
        super(ktks, NAZWA_BAZY_DANYCH, null, WERSJA_BAZY_DANYCH);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE " + NAZWA_TABELI + " (" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME
            + " TEXT," + DATE + " TEXT," + BLOOD + " TEXT," + CITY + " TEXT," + STREET + " TEXT," + ZIP + " TEXT," + CONFIRM 
            + " TEXT," + MEDICINE + " TEXT," + ALLERGY + " TEXT," + DOCTOR + " TEXT," + ORGAN + " TEXT," + INSURANCE + " TEXT," + INSURANCE_ID + " TEXT );" );
    }
    
    

    @Override
    public void onUpgrade(SQLiteDatabase bd, int staraWersja,
            int nowaWersja) {
        bd.execSQL("DROP TABLE IF EXISTS " + NAZWA_TABELI);
        onCreate(bd);
    }
    
    public void insertSosNr(String nr) {
        ContentValues cv=new ContentValues();
        cv.put("sos_nr", nr);   
        getWritableDatabase().insert("tabela", null, cv);
    }

}
