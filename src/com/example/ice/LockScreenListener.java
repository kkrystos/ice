package com.example.ice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LockScreenListener extends BroadcastReceiver
{

		    public  boolean wasScreenOn = true;
		 
		    @Override
		    public void onReceive(Context context, Intent intent) {
		        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
//		            if(wasScreenOn){
		        	Intent i = new Intent(context, ViewOnLockScreen.class);
//		    		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		    		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        	context.startActivity(i);
//		            wasScreenOn = false;
//		            }
		            
		        	
		        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
//		            if(!wasScreenOn){
//		        	Intent i = new Intent(context, ViewOnLockScreen.class);
//		        	context.startActivity(i);
//		            wasScreenOn = true;
//		            }

		        }
		    }
		 }
