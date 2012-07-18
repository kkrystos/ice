package com.example.ice.settings;


import static android.provider.BaseColumns._ID;



import static com.example.sett.db.Stale.TRESC_URI;
import static com.example.sett.db.Stale.SOSNR;
import static com.example.sett.db.Stale.LANGUAGE;
import static com.example.sett.db.Stale.ISVIS;

import com.example.ice.MainActivity;
import com.example.ice.R;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity implements OnClickListener{
	
	private EditText sos_nrE;
	private String nr;
	private static String languages = "";
	private static String isvis = "";
	private String id;
	private String sos_nr;
    public static String contactName = "";
    private  String phoneNrPick = "";
    ArrayAdapter aa;
    ArrayAdapter bb;
    
	String[] languages_itm = {  "English","Polish",};
	String[] languages_itm1 = {  "Polski","Angielski",};
	String[] isVis_itm = { "Yes", "No",};
	String[] isVis_itm1 = { "Nie","Tak", };
	String[] isVis_itm11 = { "No","Yes", };
	String[] isVis_itm12 = { "Tak","Nie", };
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        Cursor kursor = wezZdarzenia();
        pokazZdarzenia(kursor);
		
        if(languages.equalsIgnoreCase("Polish")){
            setContentView(R.layout.activity_setting_pl);
        }
        else {
    		setContentView(R.layout.activity_setting_ang);
        }

		
		
		
		
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		Button set_doneBtn =(Button)findViewById(R.id.sett_doneBtn);
		Button setSOSBTN =(Button)findViewById(R.id.sett_SOsNrBtn);
		set_doneBtn.setOnClickListener(this);
		setSOSBTN.setOnClickListener(this);

		
		sos_nrE =(EditText)findViewById(R.id.setting_sos_nrE);
		
		Spinner spin = (Spinner) findViewById(R.id.spinner_language);
		Spinner spin2 = (Spinner) findViewById(R.id.spinner_isvis);
		spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View v, int position,
					long id) {
				if(languages.equalsIgnoreCase("Polish") || languages.equalsIgnoreCase("Polski") ){
			String selection1 = new String(languages_itm1[position]);
				if(selection1.equalsIgnoreCase("English") || selection1.equalsIgnoreCase("Angielski") ){
					languages = "English";
				}
				else if(selection1.equalsIgnoreCase("Polish")  || languages.equalsIgnoreCase("Polski")){
					languages = "Polish";
				}}
				else{
					String selection1 = new String(languages_itm[position]);
					if(selection1.equalsIgnoreCase("English")){
						languages = "English";
					}
					else if(selection1.equalsIgnoreCase("Polish")){
						languages = "Polish";
					}}
				
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		});
		spin2.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View v, int position,
					long id) {
				if(isvis.equalsIgnoreCase("No")){
					String selection2 = new String(isVis_itm11[position]);
					String selection22 = new String(isVis_itm1[position]);
					if(selection2.equals("No") ||selection22.equals("Nie") ){
						isvis = "No";
					}
					else if(selection2.equals("Yes") ||selection2.equals("Tak") ){
						isvis = "Yes";
					}
				}
				else if (isvis.equalsIgnoreCase("Yes")){
					String selection2 = new String(isVis_itm[position]);
					String selection22 = new String(isVis_itm1[position]);
					if(selection2.equals("No")||selection22.equals("Nie") ){
						isvis = "No";
					}
					else if(selection2.equals("Yes")||selection22.equals("Tak")){
						isvis = "Yes";
					}
				}

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		});
		if(languages.equalsIgnoreCase("Polish")||languages.equalsIgnoreCase("Polski")){
			aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, languages_itm1);
			if (isvis.equalsIgnoreCase("No")){ 
				 bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item, isVis_itm1);
			}
			else{
				bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item, isVis_itm12);
			}
			}
		else{
			
			aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, languages_itm);
			if (isvis.equalsIgnoreCase("No")){ 
				 bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item, isVis_itm11);
			}
			else{
				bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item, isVis_itm);
			}
		}
		

//		else if (isvis.equalsIgnoreCase("No") && languages.equalsIgnoreCase("Polish")){
//			 bb = new ArrayAdapter(this,android.R.layout.simple_spinner_item, isVis_itm1);
//		}

		aa.setDropDownViewResource(
		android.R.layout.simple_spinner_dropdown_item);
		spin.setAdapter(aa); 
		bb.setDropDownViewResource(
		android.R.layout.simple_spinner_dropdown_item);
		spin2.setAdapter(bb); 	
	}

	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.sett_doneBtn :
			sos_nr = sos_nrE.getText().toString();
			dodajZdarzenie(sos_nr, languages, isvis);	
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			finish();
			break;
		case R.id.sett_SOsNrBtn:
 	       Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
 	       startActivityForResult(intent, 1);
		}
	}
	
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if(phoneNrPick.length() ==0){
			sos_nrE.setText(nr);
		}
		else{
			sos_nrE.setText(phoneNrPick);
			}
	}

	public void dodajZdarzenie(String sos_nr, String lang, String isvis) {
        ContentValues wartosci = new ContentValues();
        wartosci.put(SOSNR, sos_nr);
        wartosci.put(LANGUAGE, lang);
        wartosci.put(ISVIS, isvis);
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
	                        phoneNrPick = phoneCur.getString(phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	                    }
	                }
	                contect_resolver = null;
	                cur = null;
	            }
	        } catch (IllegalArgumentException e) {
	            e.printStackTrace();
	            Log.e("IllegalArgumentException :: ", e.toString());
	        } catch (Exception e) {
	            e.printStackTrace();
	            Log.e("Error :: ", e.toString());
	        }}
}
