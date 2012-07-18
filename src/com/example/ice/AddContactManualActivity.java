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

public class AddContactManualActivity extends Activity implements OnClickListener {
	
	private Button addContactBtn;
	private Button attachBtn;
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
            setContentView(R.layout.add_contact_manual_pl);
        }
        else {
            setContentView(R.layout.add_contact_manual_ang);
        }		

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        Bundle extras = getIntent().getExtras();
        getPhone = extras.getString("phone");       
        addContactBtn = (Button)findViewById(R.id.add_contactBtn);
        addContactBtn.setOnClickListener(this);
        attachBtn = (Button)findViewById(R.id.attachBtn);
        attachBtn.setOnClickListener(this);
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
//    		 fc = new FileChooser();
//    		uri = fc.ClickPath;
//    		uri = fc.getClkPatch();
//    		uri = "lala";
    		
    		if(outImagePath.equalsIgnoreCase("yhym")){
    			dodajZdarzenie(phone, name, "" );

//        		checkPhone = false;
    		}
    		else{
//        		photo = "file:"+ uri;
    			photo = outImagePath;
        		dodajZdarzenie(phone, name, photo);
        		outImagePath = "yhym";
    		}        		

    		break;
    	case R.id.attachBtn:
    		
    		createDirIfNotExists("/IceContactsImgs/");
    		
    		Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), 1); 		

    		break;
	
		}

	}
	
	public static boolean createDirIfNotExists(String path) {
	    boolean ret = true;

	    File file = new File(Environment.getExternalStorageDirectory(), path);
	    if (!file.exists()) {
	        if (!file.mkdirs()) {
	            Log.e("TravellerLog :: ", "Problem creating Image folder");
	            ret = false;
	        }
	    }
	    return ret;
	}

	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == RESULT_OK) {
	        if (requestCode == 1) {
	            Uri selectedImageUri = data.getData();
	            selectedImagePath = getPath(selectedImageUri);

	            String file = selectedImagePath.substring(selectedImagePath.lastIndexOf('/')+1);
	            
	            outImagePath = "/sdcard/IceContactsImgs/"+file;
	            
	            try { 															// copy files
	                File sd = Environment.getExternalStorageDirectory();
	                if (sd.canWrite()) {
	                    File source= new File(selectedImagePath);
	                    File destination= new File(outImagePath);
	                    if (source.exists()) {
	                        FileChannel src = new FileInputStream(source).getChannel();
	                        FileChannel dst = new FileOutputStream(destination).getChannel();
	                        dst.transferFrom(src, 0, src.size());
	                        src.close();
	                        dst.close();
	                    }}
	            } catch (Exception e) {}

	            changePhotoSise(selectedImagePath, outImagePath); // change photo size
	        } 
	    }
	        }
	    


	public String getPath(Uri uri) {
	    String[] projection = { MediaStore.Images.Media.DATA };
	    Cursor cursor = managedQuery(uri, projection, null, null, null);
	    int column_index = cursor
	            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);
	}


	
    public void dodajZdarzenie(String nr, String nazwa, String photo) {
        ContentValues wartosci = new ContentValues();
        wartosci.put(NUMER, nr);
        wartosci.put(NAZWA, nazwa);
        wartosci.put(PHOTO, photo);
        getContentResolver().insert(TRESC_URI, wartosci);
        finish();
    }
    
    public void changePhotoSise(String imputPhotoPath, String outputPhotoPath){
    	
        BitmapFactory.Options options = new BitmapFactory.Options();
        InputStream is = null;
        try {
				is = new FileInputStream(imputPhotoPath);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        BitmapFactory.decodeStream(is,null,options);
        try {
				is.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        try {
				is = new FileInputStream(imputPhotoPath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        int w =140;
        int h =140;
			// here w and h are the desired width and height
        options.inSampleSize = Math.max(options.outWidth/w, options.outHeight/h);
        // bitmap is the resized bitmap
        Bitmap bitmap = BitmapFactory.decodeStream(is,null,options);
        try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
        Matrix m = new Matrix();
        RectF inRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF outRect = new RectF(0, 0, w, h);
        m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
        float[] values = new float[9];
        m.getValues(values);

        // resize bitmap
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * values[0]), (int) (bitmap.getHeight() * values[4]), true);

        
        try
        {
            FileOutputStream out = new FileOutputStream(outputPhotoPath);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        }
        catch (Exception e)
        {
            Log.e("Image", e.getMessage(), e);
        }	                
        Toast.makeText(this, selectedImagePath, Toast.LENGTH_SHORT).show();   	
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