package com.example.ice;

import static android.provider.BaseColumns._ID;
import static com.example.dataBase.Const.NAZWA;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import static com.example.dataBase.Const.NUMER;
import static com.example.dataBase.Const.PHOTO;
import static com.example.dataBase.Const.TRESC_URI;
import static com.example.sett.db.Stale.ISVIS;
import static com.example.sett.db.Stale.LANGUAGE;
import static com.example.sett.db.Stale.SOSNR;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactFromContactsActivity extends Activity implements OnClickListener {
	
	private Button addContactBtn;
	private EditText nameEdit;
	private EditText phoneEdit;
	private String name;
	private String phone;
	private String photo;
	private CheckTypeContact ctc;
	private String getPhone;
	private static String uri="";
	private FileChooser fc;
//	private PhotoListener photoListener = new PhotoListener();
	private  boolean checkPhone = false ;
	private String selectedImagePath;
	private String outImagePath = "";
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
            setContentView(R.layout.add_contact_from_cont_pl);
        }
        else {
            setContentView(R.layout.add_contact_from_cont_ang);
        }		

        Bundle extras = getIntent().getExtras();
        getPhone = extras.getString("phone");       
        outImagePath = extras.getString("photo");       
        addContactBtn = (Button)findViewById(R.id.add_contactBtn);
        addContactBtn.setOnClickListener(this);
        nameEdit = (EditText)findViewById(R.id.nameEdit);
        phoneEdit = (EditText)findViewById(R.id.phoneEdit);
        

        
        if(getPhone.length()!= 0){
        phoneEdit.setText(getPhone);
        }

	}

	public void onClick(View v) {
		
		
		switch(v.getId()){
    	case R.id.add_contactBtn:
    		
    		name = nameEdit.getText().toString();
    		phone = phoneEdit.getText().toString();	
    		
    		if(outImagePath.equalsIgnoreCase("yhym")){
    			dodajZdarzenie(phone, name, "" );

    		}
    		else{
    			photo = outImagePath;
        		dodajZdarzenie(phone, name, photo);
        		outImagePath = "yhym";
    		}        		

    		break;
	
		}

	}   
    public void dodajZdarzenie(String nr, String nazwa, String photo) {
        ContentValues wartosci = new ContentValues();
        wartosci.put(NUMER, nr);
        wartosci.put(NAZWA, nazwa);
        wartosci.put(PHOTO, photo);
        getContentResolver().insert(TRESC_URI, wartosci);
        finish();
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