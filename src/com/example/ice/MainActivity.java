package com.example.ice;

import static android.provider.BaseColumns._ID;
import static com.example.sett.db.Stale.ISVIS;
import static com.example.sett.db.Stale.LANGUAGE;
import static com.example.sett.db.Stale.SOSNR;

import com.example.ice.info.InfoActivity;
import com.example.ice.settings.SettingsActivity;
import com.example.ice.sos.AlertActivity;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RemoteViews.ActionException;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity implements OnClickListener{

	private ImageButton contactBtn;
	private ImageButton infoBtn;
	private ImageButton alertBtn;
	private ImageButton settingBtn;
	
	private String nr = "";
	private String languages = "";
	private String isvis = "";
	private String name;
	private String id;
	private Toast mToast;
//	KeyguardManager kgm;
//	KeyguardLock kgl;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cursor kursor = wezZdarzenia();
        pokazZdarzenia(kursor);
        
    	KeyguardManager myKM = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        
        if(isvis.equalsIgnoreCase("No")){
        	stopService(new Intent(this, LockScreenService.class));  

        }
        
        else {
            startService(new Intent(this, LockScreenService.class));
//      	     kgm = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
//       	     kgl = kgm.newKeyguardLock(this.getClass().getSimpleName());
//            	kgl.disableKeyguard();
//    	    kgl.disableKeyguard();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        }
        


        if(languages.equalsIgnoreCase("Polish")){
            setContentView(R.layout.activity_main_pl);
        }
        else {
            setContentView(R.layout.activity_main_ang);
        }
        
    	if( myKM.inKeyguardRestrictedInputMode()) {
    		
            contactBtn =(ImageButton)findViewById(R.id.contactsBtn);
            infoBtn =(ImageButton)findViewById(R.id.infoBtn);
            alertBtn =(ImageButton)findViewById(R.id.alertBtn);
            contactBtn.setOnClickListener(this);
            infoBtn.setOnClickListener(this);
            alertBtn.setOnClickListener(this);
    		
    	}
    	else {       
    		contactBtn =(ImageButton)findViewById(R.id.contactsBtn);
        infoBtn =(ImageButton)findViewById(R.id.infoBtn);
        alertBtn =(ImageButton)findViewById(R.id.alertBtn);
        contactBtn.setOnClickListener(this);
        infoBtn.setOnClickListener(this);
        alertBtn.setOnClickListener(this);

        
        settingBtn =(ImageButton)findViewById(R.id.settingBtn);
        settingBtn.setOnClickListener(this);}



    }

	public void onClick(View v) {
    	switch(v.getId()){
    	case R.id.contactsBtn:
    		Intent c = new Intent(this, ContactsActivity.class);
    		startActivity(c);
    		break;
    	case R.id.infoBtn:
    		Intent i = new Intent(this, InfoActivity.class);
    		startActivity(i);
    		break;
    	case R.id.alertBtn:
    		if (nr.length() == 0){
    			mToast = Toast.makeText(this, "Please SET SOS NUMBER", Toast.LENGTH_SHORT);
    			mToast.show();
    		}   		
    		Uri number = Uri.parse("tel:"+ nr);
    		Intent sos = new Intent(Intent.ACTION_CALL, number);
    		startActivity(sos);
    		break;
    	case R.id.settingBtn:
    		Intent s = new Intent(this, SettingsActivity.class);
    		startActivity(s);
    		finish();
    		break;
    		default:
    			finish();
    			break;
    		}
		
	}
    	
	  @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
