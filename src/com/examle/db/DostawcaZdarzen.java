
package com.examle.db;

import static com.examle.db.Stale.NAZWA_TABELI;
import static com.examle.db.Stale.TRESC_URI;
import static com.examle.db.Stale.URZAD;
import static android.provider.BaseColumns._ID;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class DostawcaZdarzen extends ContentProvider {
   private static final int ZDARZENIA = 1;
   private static final int ID_ZDARZEN = 2;

   /** Typ MIME katalogu zdarze� */
   private static final String TYP_TRESCI
      = "vnd.android.cursor.dir/com.examle.db";

   /** Typ MIME pojedynczego zdarzenia */
   private static final String TYP_TRESCI_ELEMENT
      = "vnd.android.cursor.item/com.examle.db";

   private DaneZdarzen zdarzenia;
   private UriMatcher dopasowanieUri;
   // ...
   

   
   @Override
   public boolean onCreate() {
      dopasowanieUri = new UriMatcher(UriMatcher.NO_MATCH);
      dopasowanieUri.addURI(URZAD, "zdarzenia", ZDARZENIA);
      dopasowanieUri.addURI(URZAD, "zdarzenia/#", ID_ZDARZEN);
      zdarzenia = new DaneZdarzen(getContext());
      return true;
   }
   

   
   @Override
   public Cursor query(Uri uri, String[] projekcja,
         String wybor, String[] argumentyWyboru, String wKolejnosci) {
      if (dopasowanieUri.match(uri) == ID_ZDARZEN) {
         long id = Long.parseLong(uri.getPathSegments().get(1));
         wybor = wyswietlIdWiersza(wybor, id);
      }

      // Pobiera baz� danych i wysy�a zapytanie
      SQLiteDatabase bd = zdarzenia.getReadableDatabase();
      Cursor kursor = bd.query(NAZWA_TABELI, projekcja, wybor,
            argumentyWyboru, null, null, wKolejnosci);

      // Okre�la identyfikator URI, kt�ry ma by� obserwowany przez kursor, dzi�ki czemu
      // wiadomo, kiedy dane �r�d�owe ulegaj� zmianie
      kursor.setNotificationUri(getContext().getContentResolver(),
            uri);
      return kursor;
   }
   

   
   @Override
   public String getType(Uri uri) {
      switch (dopasowanieUri.match(uri)) {
      case ZDARZENIA:
         return TYP_TRESCI;
      case ID_ZDARZEN:
         return TYP_TRESCI_ELEMENT;
      default:
         throw new IllegalArgumentException("Nieznany identyfikator URI " + uri);
      }
   }
   

   
   @Override
   public Uri insert(Uri uri, ContentValues wartosci) {
      SQLiteDatabase bd = zdarzenia.getWritableDatabase();

      // Sprawdza poprawno�� ��danego identyfikatora URI
      if (dopasowanieUri.match(uri) != ZDARZENIA) {
         throw new IllegalArgumentException("Nieznany identyfikator URI " + uri);
      }

      // Operacja wstawiania
      long id = bd.insertOrThrow(NAZWA_TABELI, null, wartosci);

      // Powiadamia wszelkich obserwator�w o zmianie
      Uri nowyUri = ContentUris.withAppendedId(TRESC_URI, id);
      getContext().getContentResolver().notifyChange(nowyUri, null);
      return nowyUri;
   }
   

   
   @Override
   public int delete(Uri uri, String wybor,
         String[] argumentyWyboru) {
      SQLiteDatabase bd = zdarzenia.getWritableDatabase();
      int licz;
      switch (dopasowanieUri.match(uri)) {
      case ZDARZENIA:
         licz = bd.delete(NAZWA_TABELI, wybor, argumentyWyboru);
         break;
      case ID_ZDARZEN:
         long id = Long.parseLong(uri.getPathSegments().get(1));
         licz = bd.delete(NAZWA_TABELI, wyswietlIdWiersza(wybor, id),
               argumentyWyboru);
         break;
      default:
         throw new IllegalArgumentException("Nieznany identyfikator URI " + uri);
      }

      // Powiadamia wszelkich obserwator�w o zmianie
      getContext().getContentResolver().notifyChange(uri, null);
      return licz;
   }
   

   
   @Override
   public int update(Uri uri, ContentValues wartosci,
         String wybor, String[] argumentyWyboru) {
      SQLiteDatabase bd = zdarzenia.getWritableDatabase();
      int licz;
      switch (dopasowanieUri.match(uri)) {
      case ZDARZENIA:
         licz = bd.update(NAZWA_TABELI, wartosci, wybor,
               argumentyWyboru);
         break;
      case ID_ZDARZEN:
         long id = Long.parseLong(uri.getPathSegments().get(1));
         licz = bd.update(NAZWA_TABELI, wartosci, wyswietlIdWiersza(
               wybor, id), argumentyWyboru);
         break;
      default:
         throw new IllegalArgumentException("Nieznay indentyfikator URI " + uri);
      }

      // Powiadamia wszelkich obserwator�w o zmianie
      getContext().getContentResolver().notifyChange(uri, null);
      return licz;
   }
   

   
   /** Dodaje test identyfikatora do wyra�enia wyboru SQL */
   private String wyswietlIdWiersza(String wybor, long id) {
      return _ID + "=" + id
            + (!TextUtils.isEmpty(wybor)
                  ? " AND (" + wybor + ')'
                  : "");
   }
   
   
}

