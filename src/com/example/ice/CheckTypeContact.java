package com.example.ice;

import static android.provider.BaseColumns._ID;
import static com.example.dataBase.Const.TRESC_URI;

import static com.example.dataBase.Const.NUMER;
import static com.example.dataBase.Const.NAZWA;
import static com.example.sett.db.Stale.ISVIS;
import static com.example.sett.db.Stale.LANGUAGE;
import static com.example.sett.db.Stale.SOSNR;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class CheckTypeContact extends Activity implements android.view.View.OnClickListener{

	private Button addFromContactsBtn;
	private Button addManualy;
    public static String contactName = "";
    private  String phoneNrPick = "";
	private String nr;
	private static String languages = "";
	private static String isvis = "";
	private String id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        Cursor kursor = wezZdarzenia();
        pokazZdarzenia(kursor);
        
        if(isvis.equalsIgnoreCase("No")){
        }
        
        else {
            startService(new Intent(this, LockScreenService.class));        
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        }
		
        if(languages.equalsIgnoreCase("Polish")){
            setContentView(R.layout.check_type_contact_pl);
        }
        else {
            setContentView(R.layout.check_type_contact_ang);
        }
        
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		addFromContactsBtn = (Button)findViewById(R.id.addFromContactBtn);
		addFromContactsBtn.setOnClickListener(this);
		addManualy = (Button)findViewById(R.id.addManualyBtn);
		addManualy.setOnClickListener(this);
		
	}
	public void onClick(View v) {
    	switch(v.getId()){
    	case R.id.addFromContactBtn:
    	       Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    	       startActivityForResult(intent, 1);
    	       break;
    	case R.id.addManualyBtn:
	    		Intent i = new Intent(this, AddContactManualActivity.class);
	    		i.putExtra("phone", "");
	    		i.setType("text");
	    		startActivity(i);
	    		finish();
	    		break;
    		}
		
	}
	
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        try {
            if (resultCode == Activity.RESULT_OK) {
                Uri contactData = data.getData();
                
                Cursor cur = managedQuery(contactData, null, null, null, null);
                ContentResolver contect_resolver = getContentResolver();

                if (cur.moveToFirst()) {
                    String id = cur.getString(cur.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                    Cursor phoneCur = contect_resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);

                    if (phoneCur.moveToFirst()) {
//                        contactName = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        phoneNrPick = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }

                    Intent i = new Intent(this, AddContactManualActivity.class);
                    i.putExtra("phone", phoneNrPick);
                    i.setType("text");
                    startActivity(i);

                }
                contect_resolver = null;
                cur = null;
                finish();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Log.e("IllegalArgumentException :: ", e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error :: ", e.toString());
        }}
     public String getContactName(){
    	
    	return contactName;
    }
     public String getContactNr(){
    	
    	return phoneNrPick;
    }
    public void setContactName(String name){
    	contactName = name;
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
    
     public void dodajZdarzenie(String nr, String nazwa) {
        ContentValues wartosci = new ContentValues();
        wartosci.put(NUMER, nr);
        wartosci.put(NAZWA, nazwa);
        getContentResolver().insert(TRESC_URI, wartosci);
    }
}
