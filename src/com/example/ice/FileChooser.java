package com.example.ice;

import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import com.example.ice.AddContactManualActivity.PhotoListener;
import com.example.ice.settings.SettingsActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class FileChooser extends ListActivity {
    
    private File currentDir;
    private FileArrayAdapter adapter;
    ContactsActivity ca;
    public static  String ClickPath ="";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDir = new File("/sdcard/DCIM/Camera/");
        fill(currentDir);
         ca = new ContactsActivity();
        
        
    }
    private void fill(File f)
    {
        File[]dirs = f.listFiles();
         this.setTitle("Current Dir: "+f.getName());
         List<Option>dir = new ArrayList<Option>();
         List<Option>fls = new ArrayList<Option>();
         try{
             for(File ff: dirs)
             {
                if(ff.isDirectory())
                    dir.add(new Option(ff.getName(),"Folder",ff.getAbsolutePath()));
                else
                {
                    fls.add(new Option(ff.getName(),"File Size: "+ff.length(),ff.getAbsolutePath()));
                }
             }
         }catch(Exception e)
         {
             
         }
         Collections.sort(dir);
         Collections.sort(fls);
         dir.addAll(fls);
         if(!f.getName().equalsIgnoreCase("sdcard"))
             dir.add(0,new Option("..","Parent Directory",f.getParent()));
         adapter = new FileArrayAdapter(FileChooser.this,R.layout.file_view,dir);
         this.setListAdapter(adapter);
    }
    

    
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Option o = adapter.getItem(position);
        
        if(o.getData().equalsIgnoreCase("folder")||o.getData().equalsIgnoreCase("parent directory")){
                currentDir = new File(o.getPath());
                fill(currentDir);
        }
        else
        {
            

        	 BitmapFactory.Options options = new BitmapFactory.Options();
             InputStream is = null;
             try {
 				is = new FileInputStream(o.getPath());
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
 				is = new FileInputStream(o.getPath());
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
                 FileOutputStream out = new FileOutputStream(o.getPath());
                 resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
             }
             catch (Exception e)
             {
                 Log.e("Image", e.getMessage(), e);
             }

            onFileClick(o);
            
//            broadcastMsg(getClkPatch());
            finish();

        }

    }
    
    
    private void onFileClick(Option o)
    {
    	this.ClickPath = o.getPath();
        Toast.makeText(this, "File Clicked: "+getClkPatch(), Toast.LENGTH_SHORT).show();
//       Toast.makeText(this, "File Clicked: " + ClickPath, Toast.LENGTH_SHORT).show();
        
    }
    
    public void setClkPath(Option o){
    	this.ClickPath = o.getPath();
    }
    
    public String getClkPatch (){
    	return ClickPath;
    }
    
//	private void broadcastMsg(String photo) {
//		Intent intent = new Intent(PhotoListener.PHOTO_CHANGED);
//		intent.putExtra("photo", photo);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
//		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//		this.sendBroadcast(intent);
//	}

}
