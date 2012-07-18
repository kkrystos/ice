package com.example.dataBase;


import static com.example.dataBase.Const.NUMER;

import static com.example.dataBase.Const.NAZWA_TABELI;
import static com.example.dataBase.Const.NAZWA;
import static com.example.dataBase.Const.PHOTO;
import static android.provider.BaseColumns._ID;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataEvent extends SQLiteOpenHelper {
    private static final String NAZWA_BAZY_DANYCH = "zdarzenia.db" ;
    private static final int WERSJA_BAZY_DANYCH = 2;

    /** Tworzy obiekt pomocniczy dla bazy Zdarzenia */
    public DataEvent(Context ktks) {
        super(ktks, NAZWA_BAZY_DANYCH, null, WERSJA_BAZY_DANYCH);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE " + NAZWA_TABELI + " (" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NUMER
            + " TEXT," + NAZWA + " TEXT," + PHOTO + " TEXT );" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int staraWersja,
            int nowaWersja) {
        bd.execSQL("DROP TABLE IF EXISTS " + NAZWA_TABELI);
        onCreate(bd);
    }
    
    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
    	// TODO Auto-generated method stub
    	return super.getWritableDatabase();
    }
    
}
