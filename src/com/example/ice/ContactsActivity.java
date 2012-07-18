package com.example.ice;

import static com.example.dataBase.Const.NAZWA;
import static com.example.dataBase.Const.NAZWA_TABELI;


import com.example.dataBase.DataEvent;
import com.example.dataBase.EventProvider;
import com.example.ice.info.InfoActivity;
import static com.example.dataBase.Const.TRESC_URI;
import static com.example.dataBase.Const.NUMER;
import static com.example.dataBase.Const.PHOTO;
import static com.example.sett.db.Stale.ISVIS;
import static com.example.sett.db.Stale.LANGUAGE;
import static com.example.sett.db.Stale.SOSNR;
import static android.provider.BaseColumns._ID;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ContactsActivity extends ListActivity implements OnClickListener, OnItemLongClickListener {
	


	private ImageButton addContact;
	
    private static String[] FROM = {  NAZWA , PHOTO, NUMER, _ID, };
    private static String ORDER_BY = NAZWA;
    private static int[] DO = { R.id.nazwa, R.id.img_contact};
    EventProvider ep = new EventProvider();
    Cursor mycursor;
    Integer myName;
    String myId = "";
	private String nr;
	private static String languages = "";
	private static String isvis = "";


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

        
        Cursor kursor = wezZdarzenia();
        pokazZdarzenia(kursor);
        
        if(isvis.equalsIgnoreCase("No")){
        	stopService(new Intent(this, LockScreenService.class));  
        }
        
        else {
            startService(new Intent(this, LockScreenService.class));        
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        }
		
        if(languages.equalsIgnoreCase("Polish")){
            setContentView(R.layout.contacts_main_pl);
        }
        else {
            setContentView(R.layout.contacts_main_ang);
        }
        
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        
        ImageView iv = (ImageView) findViewById(R.id.img_contact);
		addContact = (ImageButton)findViewById(R.id.addContact);
		addContact.setOnClickListener(this);
        this.getListView().setLongClickable(true);
        this.getListView().setOnItemLongClickListener(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
        Cursor kursor = managedQuery(TRESC_URI, FROM, null, null, ORDER_BY);
        startManagingCursor(kursor);
        ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.element, kursor, FROM, DO);
        setListAdapter(adapter);
	}

	public void onClick(View v) {

		switch(v.getId()){
    	case R.id.addContact:
    		Intent i = new Intent(this, CheckTypeContact.class);
    		startActivity(i);
    		break;
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Cursor mycursor = (Cursor) getListView().getItemAtPosition(position); 
		String myPhone = mycursor.getString(2);
		Uri number = Uri.parse("tel:"+ myPhone);
		Intent i = new Intent(Intent.ACTION_CALL, number);
		startActivity(i);
	}

	public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

		Cursor mycursor = (Cursor) getListView().getItemAtPosition(position); 
		 final String myIdd = mycursor.getString(3);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		
        if(languages.equalsIgnoreCase("Polish")){
    		builder.setTitle("Usun kontakt").setMessage("Jestes pewien?").setPositiveButton("Tak", new DialogInterface.OnClickListener() {
 	           public void onClick(DialogInterface dialog, int id) {
 	        	   delete(myIdd);
 	        	   finish();
 	        	   Intent i = new Intent(getApplicationContext(), ContactsActivity.class);
 	        	   startActivity(i);
 	           }
 	       }).setNegativeButton("Nie", new DialogInterface.OnClickListener() {
 	           public void onClick(DialogInterface dialog, int id) {
 	                dialog.cancel();
 	           }
 	       }).show();
        }
        else {
    		builder.setTitle("Delete Item").setMessage("Are you sure?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
 	           public void onClick(DialogInterface dialog, int id) {
 	        	   delete(myIdd);
 	        	   finish();
 	        	   Intent i = new Intent(getApplicationContext(), ContactsActivity.class);
 	        	   startActivity(i);
 	           }
 	       }).setNegativeButton("No", new DialogInterface.OnClickListener() {
 	           public void onClick(DialogInterface dialog, int id) {
 	                dialog.cancel();
 	           }
 	       }).show();
        }
		
		

		
		return false;
	}
	
	public void delete(String myId)
	{
		DataEvent de = new DataEvent(this);
		SQLiteDatabase db = de.getWritableDatabase();
		db.delete(NAZWA_TABELI, "_id=?", new String[]{ 
				myId
		});
	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:

	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	           dialog.cancel();
	            break;
	        }
	    }
	};
	
	  private static String[] FROM2 = { _ID, SOSNR, LANGUAGE, ISVIS  };
	  private static String ORDER_BY2 = _ID + " DESC LIMIT 1" ;
	
	  private Cursor wezZdarzenia() {
	      return managedQuery(Uri.parse("content://com.example.sett.db/zdarzenia"), FROM2, null, null, ORDER_BY2);
	  }
	  
	  private void pokazZdarzenia(Cursor kursor) {
	      StringBuilder konstruktor = new StringBuilder(
	          "Zapisane zdarzenia:\n" );
	      while (kursor.moveToNext()) {
	          long idd = kursor.getLong(0);
	          nr = kursor.getString(1);
	          languages = kursor.getString(2);
	          isvis = kursor.getString(3);
	           
	      }
	  }

}
