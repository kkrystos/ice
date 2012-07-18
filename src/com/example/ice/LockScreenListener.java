package com.example.ice;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LockScreenListener extends BroadcastReceiver
{

		    public  boolean wasScreenOn = true;
		 
		    @Override
		    public void onReceive(Context context, Intent intent) {
		    	
		    	KeyguardManager myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
		    	
		        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
//		            if(wasScreenOn){
		        	if( myKM.inKeyguardRestrictedInputMode()) {
		        	
			        	Intent i = new Intent(context, ViewOnLockScreen.class);
			    		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			        	context.startActivity(i);
		        	}

		        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
//		            if(!wasScreenOn){
//		        	Intent i = new Intent(context, ViewOnLockScreen.class);
//		        	context.startActivity(i);
//		            wasScreenOn = true;
//		            }

		        }
		    }
		 }
