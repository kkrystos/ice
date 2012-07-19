package com.example.ice;


import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.app.KeyguardManager.KeyguardLock;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.Toast;

public class LockScreenService extends Service {


	private LockScreenListener lockScreenListener;
	private Toast myToast;
    IntentFilter filter1 = new IntentFilter(Intent.ACTION_SCREEN_ON);
    IntentFilter filter2 = new IntentFilter(Intent.ACTION_SCREEN_OFF);
    
    
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
		@Override
		public void onCreate() {
		    super.onCreate();
		    lockScreenListener = new LockScreenListener();
		    registerReceiver(lockScreenListener, filter1);
		    registerReceiver(lockScreenListener, filter2);
		    
//		    myToast = Toast.makeText(getApplicationContext(), 
//		                             "Jestem w Servisie", 
//		                             Toast.LENGTH_SHORT);
//		    myToast.show();
		}
		
		@Override
		public void onDestroy() {
			
			
//			KeyguardManager kgm = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
//			KeyguardLock kgl = kgm.newKeyguardLock(KEYGUARD_SERVICE);
//		    kgl.reenableKeyguard();
		    
//		    myToast.setText("Koniec Servisu");
//		    myToast.show();
		    unregisterReceiver(this.lockScreenListener);

		    super.onDestroy();
		}
	
}
