package com.example.ice;


import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;


public class ViewOnLockScreen extends Activity implements OnClickListener{
	
//
	 KeyguardManager kgm;
	 KeyguardLock kgl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		setContentView(R.layout.view_on_lock_screen);
		

		
         kgm = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);
         kgl = kgm.newKeyguardLock(KEYGUARD_SERVICE);

		
		ImageButton lockScreenBtn = (ImageButton) findViewById(R.id.LockScreenBtn);
		Button exitTolockScreenBtn = (Button) findViewById(R.id.ExitToLockBtn);
		
		lockScreenBtn.setOnClickListener(this);
		exitTolockScreenBtn.setOnClickListener(this);
	}

	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.LockScreenBtn:
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
////	        kgm = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);
////	        kgl = kgm.newKeyguardLock(KEYGUARD_SERVICE);
//	        KeyguardManager kgm = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
//	        KeyguardLock kgl = kgm.newKeyguardLock(KEYGUARD_SERVICE);
//			kgl.reenableKeyguard();
//			kgl = null;
//			finish();
			break;
			
		case R.id.ExitToLockBtn:
//	        KeyguardManager kgm = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
//	        KeyguardLock kgl = kgm.newKeyguardLock(KEYGUARD_SERVICE);
//	        kgl.disableKeyguard();
//			finish();
//			
//	        new Handler().postDelayed(new Runnable() {
//                public void run() {
//        	        kgl.reenableKeyguard();
//                }
//        }, 1000);
			finish();


			break;
			
		default:
				
			finish();
			break;		
		}
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//	     if (keyCode == KeyEvent.KEYCODE_BACK) {
//	     return true;
//	     }
////	     else if (keyCode == KeyEvent.KEYCODE_){
////		     return true;
////	     }
//	     return super.onKeyDown(keyCode, event);    
//	}
}
