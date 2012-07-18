package com.example.ice.info;

import static android.provider.BaseColumns._ID;
import static com.example.sett.db.Stale.ISVIS;
import static com.example.sett.db.Stale.LANGUAGE;
import static com.example.sett.db.Stale.SOSNR;



import com.example.ice.ContactsActivity;
import com.example.ice.LockScreenService;
import com.example.ice.MainActivity;
import com.example.ice.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends Activity implements OnClickListener{
	
	private String name = "";
	private String date;
	private String blood;
	private String city;
	private String street;
	private String zip;
	private String confirm;
	private String medicine;
	private String allergy;
	private String organ;
	private String doctor;
	private String insurance;
	private String insurance_id;
	private String id;
	private Toast mToast;
	private static String[] FROM2 = { _ID, SOSNR, LANGUAGE, ISVIS  };
	private static String ORDER_BY2 = _ID + " DESC LIMIT 1" ;
	private String nr;
	private static String languages = "";
	private static String isvis = "";
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Cursor kursor1 = managedQuery(Uri.parse("content://com.example.sett.db/zdarzenia"), FROM2, null, null, ORDER_BY2);
		pokazZdarzenia2(kursor1);
		
        if(isvis.equalsIgnoreCase("No")){

        }
        
        else {
            startService(new Intent(this, LockScreenService.class));        
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        }
        
        if(languages.equalsIgnoreCase("Polish")){
            setContentView(R.layout.info_main_pl);
        }
        else {
    		setContentView(R.layout.info_main_ang);
        }
		
		

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		
        Cursor kursor = wezZdarzenia();
        pokazZdarzenia(kursor);
		
		Button enterBtn = (Button)findViewById(R.id.enterDateBtn);
		enterBtn.setOnClickListener(this);
		
		Button okBtn = (Button)findViewById(R.id.infoOkBtn);
		okBtn.setOnClickListener(this);
		
		TextView nameT = (TextView)findViewById(R.id.i_main_nameT);
		TextView dateT = (TextView)findViewById(R.id.i_main_dateT);
		TextView bloodT = (TextView)findViewById(R.id.i_main_bloodT);
		TextView cityT = (TextView)findViewById(R.id.i_main_cityT);
		TextView streetT = (TextView)findViewById(R.id.i_main_streetT);
		TextView zipT = (TextView)findViewById(R.id.i_main_zipT);
		TextView confirmT = (TextView)findViewById(R.id.i_main_confT);
		TextView medicineT = (TextView)findViewById(R.id.i_main_medicineT);
		TextView allergyT = (TextView)findViewById(R.id.i_main_allergyT);
		TextView organT = (TextView)findViewById(R.id.i_main_organT);
		TextView doctorT = (TextView)findViewById(R.id.i_main_doctorT);
		TextView insuranceT = (TextView)findViewById(R.id.i_main_insuranceT);
		TextView insurance_idT = (TextView)findViewById(R.id.i_main_insurance_idT);
				
		
		nameT.setText(name);
		dateT.setText(date);
		bloodT.setText(blood);
		cityT.setText(city);
		streetT.setText(street);
		zipT.setText(zip);
		confirmT.setText(confirm);
		medicineT.setText(medicine);
		allergyT.setText(allergy);
		organT.setText(organ);
		doctorT.setText(doctor);
		insuranceT.setText(insurance);
		insurance_idT.setText(insurance_id);
		
		if(name.equalsIgnoreCase("")){

	        if(languages.equalsIgnoreCase("Polish")){
				mToast = Toast.makeText(this, "Puste pola! Wprowadz dane", Toast.LENGTH_LONG);
				mToast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
				mToast.show();
	        }
	        else {
				mToast = Toast.makeText(this, "Empty fields! Please Enter data", Toast.LENGTH_LONG);
				mToast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
				mToast.show();
	        }

		}
	}

	public void onClick(View v) {
    	switch(v.getId()){
    	case R.id.enterDateBtn:
    		Intent i = new Intent(this, InfoPutDataActivity.class);
    		startActivity(i);
    		finish();
    		break;
    	case R.id.infoOkBtn:
    		Intent ii = new Intent(this, MainActivity.class);
    		startActivity(ii);
    		finish();
    		break;
    	}
		

		
	}
	
	
	  private static String[] FROM = { _ID, "NAME", "DATE", "BLOOD","CITY","STREET","ZIP","CONFIRM", "MEDICINE", "ALLERGY","DOCTOR","ORGAN","INSURANCE","INSURANCE_ID"  };
	  private static String ORDER_BY = _ID + " DESC LIMIT 1" ;
	  
	  private Cursor wezZdarzenia() {
	      return managedQuery(Uri.parse("content://com.examle.db/zdarzenia"), FROM, null, null, ORDER_BY);
	  }
	  
	  private void pokazZdarzenia(Cursor kursor) {
	      StringBuilder konstruktor = new StringBuilder(
	          "Zapisane zdarzenia:\n" );
	      while (kursor.moveToNext()) {
	          long idd = kursor.getLong(0);
	          name = kursor.getString(1);
	          date = kursor.getString(2);
	          blood = kursor.getString(3);
	          city = kursor.getString(4);
	          street = kursor.getString(5);
	          zip = kursor.getString(6);
	          confirm = kursor.getString(7);
	          medicine = kursor.getString(8);
	          allergy = kursor.getString(9);
	          organ = kursor.getString(10);
	          doctor = kursor.getString(11);
	          insurance = kursor.getString(12);
	          insurance_id = kursor.getString(13);
	           
	        id =  konstruktor.append(idd).toString();
	      }
	  }
	  
	  private void pokazZdarzenia2(Cursor kursor) {
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

