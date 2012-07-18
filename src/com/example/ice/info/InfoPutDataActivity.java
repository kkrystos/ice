package com.example.ice.info;



import static com.examle.db.Stale.TRESC_URI;
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
import static com.example.sett.db.Stale.ISVIS;
import static com.example.sett.db.Stale.LANGUAGE;
import static com.example.sett.db.Stale.SOSNR;
import static android.provider.BaseColumns._ID;

import com.example.ice.LockScreenService;
import com.example.ice.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

public class InfoPutDataActivity extends Activity implements OnClickListener{
	
	private String name;
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
	
	private String nr;
	private static String languages = "";
	private static String isvis = "";
	private String id;

	EditText nameE;
	EditText dateE;
	EditText bloodE;
	EditText cityE;
	EditText streetE;
	EditText zipE;
	EditText confirmE;
	EditText medicineE;
	EditText allergyE;
	EditText organE;
	EditText doctorE;
	EditText insuranceE;
	EditText insurance_idE;
	
	
	
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
            setContentView(R.layout.info_put_data_pl);
        }
        else {
    		setContentView(R.layout.info_put_data_ang);
        }

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		 nameE = (EditText)findViewById(R.id.info_p_nameE);
		 dateE = (EditText)findViewById(R.id.info_p_DateE);
		 bloodE = (EditText)findViewById(R.id.info_p_bloodE);
		 cityE = (EditText)findViewById(R.id.info_p_cityE);
		 streetE = (EditText)findViewById(R.id.info_p_streetE);
		 zipE = (EditText)findViewById(R.id.info_p_zipE);
		 confirmE = (EditText)findViewById(R.id.info_p_confirmE);
		 medicineE = (EditText)findViewById(R.id.info_p_medicineE);
		 allergyE = (EditText)findViewById(R.id.info_p_allegryE);
		 organE = (EditText)findViewById(R.id.info_p_organE);
		 doctorE = (EditText)findViewById(R.id.info_p_doctorE);
		 insuranceE = (EditText)findViewById(R.id.info_p_insuranceE);
		 insurance_idE = (EditText)findViewById(R.id.info_p_insurance_idE);

		
		Button add = (Button)findViewById(R.id.doneBtn);
		add.setOnClickListener(this);

}

	public void onClick(View arg0) {
		name = nameE.getText().toString();
		date = dateE.getText().toString();
		blood = bloodE.getText().toString();
		city = cityE.getText().toString();
		street= streetE.getText().toString();
		zip= zipE.getText().toString();
		confirm= confirmE.getText().toString();
		medicine= medicineE.getText().toString();
		allergy = allergyE.getText().toString();
		organ= organE.getText().toString();
		doctor= doctorE.getText().toString();
		insurance= insuranceE.getText().toString();
		insurance_id= insurance_idE.getText().toString();
		
		dodajZdarzenie(name, date, blood, city, street, zip, confirm, medicine, allergy, doctor, organ, insurance, insurance_id);
		
		finish();

	}
	
    private void dodajZdarzenie(String nam,String dat,String bloo,String cit, String stree, String zi, String confir, String medic, String allerg, String docto, String orga, String insu, String ins_id) {
        // Wstawia nowy rekord do Ÿród³a danych Zdarzenia.
        // Podobnie wygl¹da proces usuwania i aktualizowania rekordów.
        ContentValues wartosci = new ContentValues();
        wartosci.put(NAME, nam);
        wartosci.put(DATE, dat);
        wartosci.put(BLOOD, bloo);
        wartosci.put(CITY, cit);
        wartosci.put(STREET, stree);
        wartosci.put(ZIP, zi);
        wartosci.put(CONFIRM, confir);
        wartosci.put(MEDICINE, medic);
        wartosci.put(ALLERGY, allerg);
        wartosci.put(DOCTOR, docto);
        wartosci.put(ORGAN, orga);
        wartosci.put(INSURANCE, insu);
        wartosci.put(INSURANCE_ID, ins_id);
        getContentResolver().insert(TRESC_URI, wartosci);
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