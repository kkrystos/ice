package com.example.ice.sos;

import static android.provider.BaseColumns._ID;

import static com.example.sett.db.Stale.ISVIS;
import static com.example.sett.db.Stale.LANGUAGE;
import static com.example.sett.db.Stale.SOSNR;

import com.example.ice.R;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class AlertActivity extends Activity{

	
	private String nr;
	private String languages;
	private String isvis;
	private String name;
	private String id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sos_main);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        Cursor kursor = wezZdarzenia();
        pokazZdarzenia(kursor);
        
		TextView tv = (TextView)findViewById(R.id.sos_tv);
		
		
		tv.setText(nr);
		
	}
	
	  private static String[] FROM = { _ID, SOSNR, LANGUAGE, ISVIS  };
	  private static String ORDER_BY = _ID + " DESC LIMIT 1" ;
	  
	  private Cursor wezZdarzenia() {
	      return managedQuery(Uri.parse("content://com.example.sett.db/zdarzenia"), FROM, null, null, ORDER_BY);
	  }
	  
	  private void pokazZdarzenia(Cursor kursor) {
	      StringBuilder konstruktor = new StringBuilder(
	          "Zapisane zdarzenia:\n" );
	      while (kursor.moveToNext()) {
	          long idd = kursor.getLong(0);
	          nr = kursor.getString(1);
	          languages = kursor.getString(2);
	          isvis = kursor.getString(3);

	           
	        id =  konstruktor.append(idd).toString();
	      }
	  }
}

